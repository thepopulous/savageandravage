package com.populousteam.savageandravage.entity;

import com.populousteam.savageandravage.SavageConfig;
import com.populousteam.savageandravage.api.IRaidSuppoter;
import com.populousteam.savageandravage.entity.ai.*;
import com.populousteam.savageandravage.init.SavageEntities;
import com.populousteam.savageandravage.init.SavageSounds;
import com.populousteam.savageandravage.world.RevampRaid;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.AbstractRaiderEntity;
import net.minecraft.entity.monster.AbstractSkeletonEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.UUID;
import java.util.function.Predicate;

public class HyenaEntity extends TameableEntity implements IRaidSuppoter {
    public static final Predicate<LivingEntity> field_213441_bD = (p_213440_0_) -> {
        EntityType<?> entitytype = p_213440_0_.getType();
        return !(p_213440_0_ instanceof AbstractHorseEntity) && entitytype != SavageEntities.HYENA && entitytype != EntityType.PANDA && (!(p_213440_0_ instanceof TameableEntity) || entitytype == EntityType.WOLF || entitytype == EntityType.CAT || entitytype == EntityType.OCELOT);
    };
    private static final DataParameter<Boolean> LEADER = EntityDataManager.createKey(HyenaEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> BEGGING = EntityDataManager.createKey(HyenaEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Float> DATA_HEALTH_ID = EntityDataManager.createKey(HyenaEntity.class, DataSerializers.FLOAT);
    private static final DataParameter<Integer> COLLAR_COLOR = EntityDataManager.createKey(HyenaEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> RAID = EntityDataManager.createKey(HyenaEntity.class, DataSerializers.BOOLEAN);
    private float headRotationCourse;
    private float headRotationCourseOld;

    private float clientSideSitAnimation0;
    private float clientSideSitAnimation;
    /**
     * true is the hyena is wet else false
     */
    private boolean isWet;
    /**
     * True if the hyena is shaking else False
     */
    private boolean isShaking;
    /**
     * This time increases while hyena is shaking and emitting water particles.
     */
    private float timeWolfIsShaking;
    private float prevTimeWolfIsShaking;

    @Nullable
    private HyenaEntity menberHead;
    @Nullable
    private HyenaEntity menberTail;

    @Nullable
    private HyenaEntity menberLeader;

    public HyenaEntity(EntityType<? extends HyenaEntity> type, World worldIn) {
        super(type, worldIn);
        this.setTamed(false);
    }

    @Override
    protected void registerGoals() {
        this.sitGoal = new SitGoal(this);
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(2, this.sitGoal);
        this.goalSelector.addGoal(4, new LeapAtTargetGoal(this, 0.4F));
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(6, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F));
        this.goalSelector.addGoal(6, new FollowIllagerGoal(this, 1.2D, 2.5F, 15.0F) {
            @Override
            public boolean shouldExecute() {
                return !isTamed() && isRaiding() && super.shouldExecute();
            }
        });
        this.goalSelector.addGoal(6, new FollowTamedHyenaGoal(this, 1.2D));
        this.goalSelector.addGoal(6, new FollowWildLeaderGoal(this, 1.05D));
        this.goalSelector.addGoal(7, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(9, new HyenaBegGoal(this, 8.0F));
        this.goalSelector.addGoal(10, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(10, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new OwnerHyenaHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, (new HurtByTargetGoal(this)).setCallsForHelp());
        if (SavageConfig.HYENAS_ATTACK_COWS.get()) {
            this.targetSelector.addGoal(4, new NonTamedTargetGoal(this, CowEntity.class, false, field_213441_bD) {
                @Override
                public boolean shouldExecute() {
                    return !isRaiding() && super.shouldExecute();
                }
            });
        }
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, AbstractSkeletonEntity.class, false));
    }

    protected void updateAITasks() {
        this.dataManager.set(DATA_HEALTH_ID, Float.valueOf(this.getHealth()));
    }

    protected void registerData() {
        super.registerData();
        this.dataManager.register(DATA_HEALTH_ID, Float.valueOf(this.getHealth()));
        this.dataManager.register(BEGGING, false);
        this.dataManager.register(LEADER, Boolean.valueOf(false));
        this.dataManager.register(RAID, Boolean.valueOf(false));
        this.dataManager.register(COLLAR_COLOR, DyeColor.RED.getId());
    }

    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.30000001192092896D);

        if (this.isTamed()) {
            this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(12.0D);
        } else {
            this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(6.0D);
        }


        this.getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0D);
    }

    public void setAttackTarget(@Nullable LivingEntity entitylivingbaseIn) {
        super.setAttackTarget(entitylivingbaseIn);

        if (entitylivingbaseIn == null) {
            this.setAngry(false);
        } else {
            this.setAngry(true);
        }
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putBoolean("Leader", this.isLeader());
        compound.putBoolean("Raiding", this.isRaiding());
        compound.putByte("CollarColor", (byte) this.getCollarColor().getId());
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);

        this.setLeader(compound.getBoolean("Leader"));
        this.setRaiding(compound.getBoolean("Raiding"));
        if (compound.contains("CollarColor", 99)) {
            this.setCollarColor(DyeColor.byId(compound.getInt("CollarColor")));
        }
    }

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.ENTITY_WOLF_STEP, 0.15F, 1.0F);
    }

    protected SoundEvent getAmbientSound() {
        if (this.isAngry()) {
            return SoundEvents.ENTITY_WOLF_GROWL;
        } else {
            return SavageSounds.HYENA_AMBIENT;
        }
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SavageSounds.HYENA_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SavageSounds.HYENA_DEATH;
    }

    protected float getSoundVolume() {
        return 0.5F;
    }

    public void leaveMenber() {
        if (this.menberHead != null) {
            this.menberHead.menberTail = null;
        }

        this.menberHead = null;

    }

    public void joinMenber(HyenaEntity menberHeadIn) {
        this.menberHead = menberHeadIn;
        this.menberHead.menberTail = this;
    }

    public boolean hasMenberTrail() {
        return this.menberTail != null;
    }

    public boolean inMenber() {
        return this.menberHead != null;
    }

    @Nullable
    public HyenaEntity getMenberHead() {
        return this.menberHead;
    }

    public boolean isLeader() {
        return this.dataManager.get(LEADER).booleanValue();
    }

    public void setLeader(boolean leader) {
        this.dataManager.set(LEADER, leader);
    }

    public boolean isRaiding() {
        return this.dataManager.get(RAID).booleanValue();
    }

    public void setRaiding(boolean leader) {
        this.dataManager.set(RAID, leader);
    }

    public boolean hasLeader() {
        return this.menberLeader != null && this.menberLeader.isAlive();
    }

    public HyenaEntity getLeaderHyena() {
        return this.menberLeader;
    }

    public void setLeaderHyena(HyenaEntity leader) {
        this.menberLeader = leader;
    }

    public void tick() {
        super.tick();

        this.headRotationCourseOld = this.headRotationCourse;
        if (this.isBegging()) {
            this.headRotationCourse += (1.0F - this.headRotationCourse) * 0.4F;
        } else {
            this.headRotationCourse += (0.0F - this.headRotationCourse) * 0.4F;
        }

        if (this.world.isRemote) {
            this.clientSideSitAnimation0 = this.clientSideSitAnimation;
            if (this.isSitting()) {
                this.clientSideSitAnimation = MathHelper.clamp(this.clientSideSitAnimation + 1.0F, 0.0F, 4.5F);
            } else {
                this.clientSideSitAnimation = MathHelper.clamp(this.clientSideSitAnimation - 1.0F, 0.0F, 4.5F);
            }
        }

        if (this.isWet()) {
            this.isWet = true;
            this.isShaking = false;
            this.timeWolfIsShaking = 0.0F;
            this.prevTimeWolfIsShaking = 0.0F;
        } else if ((this.isWet || this.isShaking) && this.isShaking) {
            if (this.timeWolfIsShaking == 0.0F) {
                this.playSound(SoundEvents.ENTITY_WOLF_SHAKE, this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
            }

            this.prevTimeWolfIsShaking = this.timeWolfIsShaking;
            this.timeWolfIsShaking += 0.05F;

            if (this.prevTimeWolfIsShaking >= 2.0F) {
                this.isWet = false;
                this.isShaking = false;
                this.prevTimeWolfIsShaking = 0.0F;
                this.timeWolfIsShaking = 0.0F;
            }

            if (this.timeWolfIsShaking > 0.4F) {
                float f = (float) this.getBoundingBox().minY;
                int i = (int) (MathHelper.sin((this.timeWolfIsShaking - 0.4F) * (float) Math.PI) * 7.0F);

                for (int j = 0; j < i; ++j) {
                    Vec3d vec3d = this.getMotion();

                    float f1 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.getWidth() * 0.5F;
                    float f2 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.getWidth() * 0.5F;
                    this.world.addParticle(ParticleTypes.SPLASH, this.posX + (double) f1, f + 0.8F, this.posZ + (double) f2, vec3d.x, vec3d.y, vec3d.z);
                }
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public float getSittingAnimationScale(float p_189795_1_) {
        return (this.clientSideSitAnimation0 + (this.clientSideSitAnimation - this.clientSideSitAnimation0) * p_189795_1_) / 6.0F;
    }

    protected double followLeashSpeed() {
        return 1.2D;
    }

    public void livingTick() {
        super.livingTick();

        if (!this.world.isRemote && this.isWet && !this.isShaking && !this.hasPath() && this.onGround) {
            this.isShaking = true;
            this.timeWolfIsShaking = 0.0F;
            this.prevTimeWolfIsShaking = 0.0F;
            this.world.setEntityState(this, (byte) 8);
        }

        if (!this.world.isRemote && this.getAttackTarget() == null && this.isAngry()) {
            this.setAngry(false);
        }
    }

    public void setTamed(boolean tamed) {
        super.setTamed(tamed);

        if (tamed) {
            this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(16.0D);
        } else {
            this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(6.0D);
        }

        this.setLeader(false);

        this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5.0D);
    }

    public void updateSwimming() {
        if (!this.world.isRemote) {
            if (this.isServerWorld() && this.isInWater()) {
                this.setSwimming(true);
            } else {
                this.setSwimming(false);
            }
        }

    }

    protected float getWaterSlowDown() {
        return 0.86F;
    }

    public boolean processInteract(PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getHeldItem(hand);

        if (this.isTamed()) {
            if (!itemstack.isEmpty()) {
                if (itemstack.getItem().isFood()) {
                    if (itemstack.getItem().getFood().isMeat() && this.getHealth() < this.getMaxHealth()) {
                        if (!player.isCreative()) {
                            itemstack.shrink(1);
                        }

                        this.heal((float) itemstack.getItem().getFood().getHealing());
                        return true;
                    }
                } else if (itemstack.getItem() instanceof DyeItem) {
                    DyeColor enumdyecolor = ((DyeItem) itemstack.getItem()).getDyeColor();
                    if (enumdyecolor != this.getCollarColor()) {
                        this.setCollarColor(enumdyecolor);
                        if (!player.abilities.isCreativeMode) {
                            itemstack.shrink(1);
                        }

                        return true;
                    }
                }
            }

            if (this.isOwner(player) && !this.world.isRemote && !this.isBreedingItem(itemstack)) {
                this.sitGoal.setSitting(!this.isSitting());
                this.isJumping = false;
                this.navigator.clearPath();
                this.setAttackTarget(null);
            }
        } else if (itemstack.getItem() == Items.BONE && !this.isAngry() && !this.isRaiding()) {
            if (!player.isCreative()) {
                itemstack.shrink(1);
            }

            if (!this.world.isRemote) {
                if (this.rand.nextInt(3) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player)) {
                    this.setTamedBy(player);
                    this.navigator.clearPath();
                    this.setAttackTarget(null);
                    this.sitGoal.setSitting(true);
                    this.setHealth(this.getMaxHealth());
                    this.playTameEffect(true);
                    this.world.setEntityState(this, (byte) 7);
                } else {
                    this.playTameEffect(false);
                    this.world.setEntityState(this, (byte) 6);
                }
            }

            return true;
        }

        return super.processInteract(player, hand);
    }

    public boolean isBegging() {
        return this.dataManager.get(BEGGING);
    }

    public void setBegging(boolean beg) {
        this.dataManager.set(BEGGING, beg);
    }

    /**
     * True if the hyena is wet
     */
    @OnlyIn(Dist.CLIENT)
    public boolean isWolfWet() {
        return this.isWet;
    }

    @OnlyIn(Dist.CLIENT)
    public float getTailRotation() {
        if (this.isAngry()) {
            return 1.5393804F;
        } else {
            return -((this.getMaxHealth() - this.dataManager.get(DATA_HEALTH_ID).floatValue()) - 28.0F) * 0.02F * (float) Math.PI;
        }
    }

    /**
     * Used when calculating the amount of shading to apply while the hyena is wet.
     */
    @OnlyIn(Dist.CLIENT)
    public float getShadingWhileWet(float p_70915_1_) {
        return 0.75F + (this.prevTimeWolfIsShaking + (this.timeWolfIsShaking - this.prevTimeWolfIsShaking) * p_70915_1_) / 2.0F * 0.25F;
    }

    @OnlyIn(Dist.CLIENT)
    public float getShakeAngle(float p_70923_1_, float p_70923_2_) {
        float f = (this.prevTimeWolfIsShaking + (this.timeWolfIsShaking - this.prevTimeWolfIsShaking) * p_70923_1_ + p_70923_2_) / 1.8F;

        if (f < 0.0F) {
            f = 0.0F;
        } else if (f > 1.0F) {
            f = 1.0F;
        }

        return MathHelper.sin(f * (float) Math.PI) * MathHelper.sin(f * (float) Math.PI * 11.0F) * 0.15F * (float) Math.PI;
    }

    @OnlyIn(Dist.CLIENT)
    public float getInterestedAngle(float p_70917_1_) {
        return (this.headRotationCourseOld + (this.headRotationCourse - this.headRotationCourseOld) * p_70917_1_) * 0.15F * (float) Math.PI;
    }

    @OnlyIn(Dist.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 8) {
            this.isShaking = true;
            this.timeWolfIsShaking = 0.0F;
            this.prevTimeWolfIsShaking = 0.0F;
        } else {
            super.handleStatusUpdate(id);
        }
    }

    public DyeColor getCollarColor() {
        return DyeColor.byId(this.dataManager.get(COLLAR_COLOR));
    }

    public void setCollarColor(DyeColor collarcolor) {
        this.dataManager.set(COLLAR_COLOR, collarcolor.getId());
    }

    @Override
    public float getEyeHeight(Pose p_213307_1_) {
        return super.getEyeHeight(p_213307_1_) * 0.8F;
    }


    /**
     * The speed it takes to move the entityliving's rotationPitch through the faceEntity method. This is only currently
     * use in wolves.
     */
    public int getVerticalFaceSpeed() {
        return this.isSitting() ? 20 : super.getVerticalFaceSpeed();
    }

    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (this.isInvulnerable()) {
            return false;
        } else {
            Entity entity = source.getTrueSource();

            if (this.sitGoal != null) {
                this.sitGoal.setSitting(false);
            }

            if (entity != null && !(entity instanceof PlayerEntity) && !(entity instanceof ArrowEntity)) {
                amount = (amount + 1.0F) / 2.0F;
            }

            if (!this.hasCustomName() && entity == getOwner() && entity instanceof PlayerEntity) {
                this.setTamed(false);
                this.setOwnerId(null);
            }

            return super.attackEntityFrom(source, amount);
        }
    }

    public boolean attackEntityAsMob(Entity entityIn) {
        boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float) ((int) this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getValue()));

        if (flag) {
            this.applyEnchantments(this, entityIn);
        }

        return flag;
    }

    @Nullable
    @Override
    public HyenaEntity createChild(AgeableEntity ageable) {
        HyenaEntity entityhyena;
        entityhyena = SavageEntities.HYENA.create(this.world);
        UUID uuid = this.getOwnerId();

        if (uuid != null) {
            entityhyena.setOwnerId(uuid);
            entityhyena.setTamed(true);
        }

        return entityhyena;
    }

    public boolean canMateWith(AnimalEntity otherAnimal) {
        if (otherAnimal == this) {
            return false;
        } else if (!(otherAnimal instanceof HyenaEntity)) {
            return false;
        } else {
            HyenaEntity entityhyena = (HyenaEntity) otherAnimal;

            if (entityhyena.isSitting()) {
                return false;
            } else {
                return this.isInLove() && entityhyena.isInLove();
            }
        }
    }

    public boolean isBreedingItem(ItemStack stack) {
        return this.isTamed() && stack.getItem().isFood() && stack.getItem().getFood().isMeat();
    }

    /**
     * Will return how many at most can spawn in a chunk at once.
     */
    public int getMaxSpawnedInChunk() {
        return 6;
    }

    /**
     * Determines whether this hyena is angry or not.
     */
    public boolean isAngry() {
        return (this.dataManager.get(TAMED).byteValue() & 2) != 0;
    }

    public void setAngry(boolean angry) {
        byte b0 = this.dataManager.get(TAMED).byteValue();

        if (angry) {
            this.dataManager.set(TAMED, Byte.valueOf((byte) (b0 | 2)));
        } else {
            this.dataManager.set(TAMED, Byte.valueOf((byte) (b0 & -3)));
        }
    }

    public boolean shouldAttackEntity(LivingEntity target, LivingEntity owner) {
        if (!(target instanceof CreeperEntity)) {
            if (target instanceof HyenaEntity) {
                HyenaEntity entityhyena = (HyenaEntity) target;

                return false;
            }

            if (target instanceof PlayerEntity && owner instanceof PlayerEntity && !((PlayerEntity) owner).canAttackPlayer((PlayerEntity) target)) {
                return false;
            } else {
                return !(target instanceof AbstractHorseEntity) || !((AbstractHorseEntity) target).isTame();
            }
        } else {
            return false;
        }
    }


    public boolean canBeLeashedTo(PlayerEntity player) {
        return !this.isAngry() && super.canBeLeashedTo(player);
    }

    @Override
    public boolean isEnemy() {
        return getOwner() != null && getOwner() instanceof AbstractRaiderEntity;
    }

    @Override
    public void initRaidSpawn(int wave, RevampRaid raid) {
        this.setRaiding(true);
    }

    @Override
    public boolean isOnSameTeam(Entity entityIn) {
        if (super.isOnSameTeam(entityIn)) {
            return true;
        } else if (this.isRaiding() && entityIn instanceof LivingEntity && ((LivingEntity) entityIn).getCreatureAttribute() == CreatureAttribute.ILLAGER) {
            return this.getTeam() == null && entityIn.getTeam() == null;
        } else {
            return false;
        }
    }

    @Nullable
    @Override
    public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        if (spawnDataIn instanceof GroupData) {
            if (((GroupData) spawnDataIn).madeMenber) {
                this.setLeader(false);
            }
        } else {
            GroupData entityHyena = new GroupData();
            entityHyena.madeMenber = true;
            spawnDataIn = entityHyena;
            this.setLeader(true);
        }

        return spawnDataIn;
    }


    static class GroupData implements ILivingEntityData {
        public boolean madeMenber;

        private GroupData() {
        }
    }
}