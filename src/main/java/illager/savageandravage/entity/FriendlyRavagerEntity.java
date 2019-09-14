package illager.savageandravage.entity;

import illager.savageandravage.SavageAndRavageCore;
import illager.savageandravage.message.MessageRavagerAttackStat;
import illager.savageandravage.message.MessageRavagerDushStat;
import illager.savageandravage.message.MessageRavagerStopDushStat;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.SoundType;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.entity.monster.AbstractRaiderEntity;
import net.minecraft.entity.monster.RavagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SaddleItem;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effects;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public class FriendlyRavagerEntity extends CreatureEntity {
    private static final Predicate<Entity> field_213690_b = (p_213685_0_) -> {
        return p_213685_0_.isAlive() && !(p_213685_0_ instanceof FriendlyRavagerEntity);
    };

    protected static final IAttribute JUMP_STRENGTH = (new RangedAttribute((IAttribute) null, "horse.jumpStrength", 0.7D, 0.0D, 2.0D)).setDescription("Jump Strength").setShouldWatch(true);
    private static final DataParameter<Boolean> BOOST = EntityDataManager.createKey(FriendlyRavagerEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> SADDLED = EntityDataManager.createKey(FriendlyRavagerEntity.class, DataSerializers.BOOLEAN);

    protected boolean horseJumping;
    protected float jumpPower;
    protected boolean allowStandSliding;
    protected int gallopTime;
    private int attackTick;
    private int stunTick;
    private int roarTick;


    public void setHorseJumping(boolean jumping) {
        this.horseJumping = jumping;
    }

    public boolean isHorseJumping() {
        return this.horseJumping;
    }

    public double getHorseJumpStrength() {
        return this.getAttribute(JUMP_STRENGTH).getValue();
    }

    @SuppressWarnings("unchecked")
    public FriendlyRavagerEntity(EntityType<? extends FriendlyRavagerEntity> type, World worldIn) {
        super(type, worldIn);
        this.stepHeight = 1.0F;
        this.experienceValue = 10;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new FriendlyRavagerEntity.AttackGoal());
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, RavagerEntity.class, 16.0F, 1.0D, 1.2D));
        this.goalSelector.addGoal(2, new PanicGoal(this, 1D));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomWalkingGoal(this, 0.45D));
        this.goalSelector.addGoal(4, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(10, new LookAtGoal(this, AbstractRaiderEntity.class, 8.0F));
    }

    protected void func_213385_F() {
        boolean flag = !(this.getControllingPassenger() instanceof MobEntity) || this.getControllingPassenger().getType().isContained(EntityTypeTags.RAIDERS);
        boolean flag1 = !(this.getRidingEntity() instanceof BoatEntity);
        this.goalSelector.setFlag(Goal.Flag.MOVE, flag);
        this.goalSelector.setFlag(Goal.Flag.JUMP, flag && flag1);
        this.goalSelector.setFlag(Goal.Flag.LOOK, flag);
        this.goalSelector.setFlag(Goal.Flag.TARGET, flag);
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttributes().registerAttribute(JUMP_STRENGTH);
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100.0d);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3d);
        this.getAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.5D);
        this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(12.0D);
        this.getAttribute(SharedMonsterAttributes.ATTACK_KNOCKBACK).setBaseValue(1.5D);
        this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(32.0D);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(BOOST, false);
        this.dataManager.register(SADDLED, false);
    }


    public int getHorizontalFaceSpeed() {
        return 45;
    }

    /**
     * Returns the Y offset from the entity's position for any entity riding this one.
     */
    public double getMountedYOffset() {
        return 2.1D;
    }

    /*
    Attacking properties
     */
    class AttackGoal extends MeleeAttackGoal {
        public AttackGoal() {
            super(FriendlyRavagerEntity.this, 1.0D, true);
        }

        protected double getAttackReachSqr(LivingEntity attackTarget) {
            float f = FriendlyRavagerEntity.this.getWidth() - 0.1F;
            return (double)(f * 2.0F * f * 2.0F + attackTarget.getWidth());
        }
    }

    public void livingTick() {
        super.livingTick();
        if (this.isAlive()) {
            if (this.isMovementBlocked()) {
                this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.0D);
            } else {
                double d0 = this.getAttackTarget() != null ? 0.35D : 0.3D;
                double d1 = this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getBaseValue();
                this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(MathHelper.lerp(0.1D, d1, d0));
            }

            if (this.collidedHorizontally && net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.world, this)) {
                boolean flag = false;
                AxisAlignedBB axisalignedbb = this.getBoundingBox().grow(0.2D);

                if (this.isBoosting()) {
                    for (BlockPos blockpos : BlockPos.getAllInBoxMutable(MathHelper.floor(axisalignedbb.minX), MathHelper.floor(axisalignedbb.minY), MathHelper.floor(axisalignedbb.minZ), MathHelper.floor(axisalignedbb.maxX), MathHelper.floor(axisalignedbb.maxY), MathHelper.floor(axisalignedbb.maxZ))) {
                        BlockState blockstate = this.world.getBlockState(blockpos);
                        Block block = blockstate.getBlock();
                        if (block instanceof LeavesBlock) {
                            flag = this.world.destroyBlock(blockpos, true) || flag;
                        }
                    }
                }

                if (!flag && this.onGround) {
                    this.jump();
                }
            }

            if (this.roarTick > 0) {
                --this.roarTick;
                if (this.roarTick == 10) {
                    this.roar();
                }
            }

            if (this.attackTick > 0) {
                --this.attackTick;
            }

            if (this.stunTick > 0) {
                --this.stunTick;
                this.func_213682_eh();
                if (this.stunTick == 0) {
                    this.playSound(SoundEvents.ENTITY_RAVAGER_ROAR, 1.0F, 1.0F);
                    this.roarTick = 20;
                }
            }

        }
    }

    private void func_213682_eh() {
        if (this.rand.nextInt(6) == 0) {
            double d0 = this.posX - (double) this.getWidth() * Math.sin((double) (this.renderYawOffset * ((float) Math.PI / 180F))) + (this.rand.nextDouble() * 0.6D - 0.3D);
            double d1 = this.posY + (double) this.getHeight() - 0.3D;
            double d2 = this.posZ + (double) this.getWidth() * Math.cos((double) (this.renderYawOffset * ((float) Math.PI / 180F))) + (this.rand.nextDouble() * 0.6D - 0.3D);
            this.world.addParticle(ParticleTypes.ENTITY_EFFECT, d0, d1, d2, 0.4980392156862745D, 0.5137254901960784D, 0.5725490196078431D);
        }

    }

    /**
     * returns true if the entity provided in the argument can be seen. (Raytrace)
     */
    public boolean canEntityBeSeen(Entity entityIn) {
        return this.stunTick <= 0 && this.roarTick <= 0 ? super.canEntityBeSeen(entityIn) : false;
    }

    protected void func_213371_e(LivingEntity p_213371_1_) {
        if (this.roarTick == 0) {
            if (this.rand.nextDouble() < 0.5D) {
                this.stunTick = 40;
                this.playSound(SoundEvents.ENTITY_RAVAGER_STUNNED, 1.0F, 1.0F);
                this.world.setEntityState(this, (byte) 39);
                p_213371_1_.applyEntityCollision(this);
            } else {
                this.launch(p_213371_1_);
            }

            p_213371_1_.velocityChanged = true;
        }

    }

    private void roar() {
        if (this.isAlive()) {
            for (Entity entity : this.world.getEntitiesWithinAABB(LivingEntity.class, this.getBoundingBox().grow(4.0D), field_213690_b)) {
                if (!(entity instanceof AbstractIllagerEntity)) {
                    entity.attackEntityFrom(DamageSource.causeMobDamage(this), 6.0F);
                }

                this.launch(entity);
            }

            Vec3d vec3d = this.getBoundingBox().getCenter();

            for (int i = 0; i < 40; ++i) {
                double d0 = this.rand.nextGaussian() * 0.2D;
                double d1 = this.rand.nextGaussian() * 0.2D;
                double d2 = this.rand.nextGaussian() * 0.2D;
                this.world.addParticle(ParticleTypes.POOF, vec3d.x, vec3d.y, vec3d.z, d0, d1, d2);
            }
        }

    }

    private void launch(Entity p_213688_1_) {
        double d0 = p_213688_1_.posX - this.posX;
        double d1 = p_213688_1_.posZ - this.posZ;
        double d2 = Math.max(d0 * d0 + d1 * d1, 0.001D);
        p_213688_1_.addVelocity(d0 / d2 * 4.0D, 0.2D, d1 / d2 * 4.0D);
    }

    /**
     * Handler for {@link World#setEntityState}
     */
    @OnlyIn(Dist.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 4) {
            this.attackTick = 10;
            this.playSound(SoundEvents.ENTITY_RAVAGER_ATTACK, 1.0F, 1.0F);
        } else if (id == 39) {
            this.stunTick = 40;
        }

        super.handleStatusUpdate(id);
    }

    @OnlyIn(Dist.CLIENT)
    public int func_213683_l() {
        return this.attackTick;
    }

    @OnlyIn(Dist.CLIENT)
    public int func_213684_dX() {
        return this.stunTick;
    }

    @OnlyIn(Dist.CLIENT)
    public int func_213687_eg() {
        return this.roarTick;
    }

    public boolean attackEntityAsMob(Entity entityIn) {
        this.attackTick = 10;
        this.world.setEntityState(this, (byte) 4);
        this.playSound(SoundEvents.ENTITY_RAVAGER_ATTACK, 1.0F, 1.0F);
        return super.attackEntityAsMob(entityIn);
    }

    @Nullable
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_RAVAGER_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_RAVAGER_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_RAVAGER_DEATH;
    }

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.ENTITY_RAVAGER_STEP, 0.15F, 1.0F);
    }

    public boolean isNotColliding(IWorldReader worldIn) {
        return !worldIn.containsAnyLiquid(this.getBoundingBox());
    }



    @Override
    public void tick() {
        super.tick();
        if (world.isRemote) {
            this.updateClientControls();
        }
    }


    @OnlyIn(Dist.CLIENT)
    protected void updateClientControls() {
        Minecraft mc = Minecraft.getInstance();

        if (this.isRidingPlayer(mc.player)) {

            if (mc.gameSettings.keyBindAttack.isKeyDown()) {
                if (this.func_213683_l() == 0) {
                    attackingStart();
                }
            }

            if (mc.gameSettings.keyBindJump.isKeyDown() && Entity.func_213296_b(this.getMotion()) > (double) 2.5000003E-7F) {
                dushStart();
            } else if (this.isBoosting()) {
                dushFinish();
            }
        }
    }

    public boolean isBoosting() {
        return this.getDataManager().get(BOOST);
    }

    public void setBoosting(boolean boost) {
        this.getDataManager().set(BOOST, boost);
    }

    public boolean isSaddled() {
        return this.getDataManager().get(SADDLED);
    }

    public void setSaddled(boolean boost) {
        this.getDataManager().set(SADDLED, boost);
    }

    private void dushFinish() {
        SavageAndRavageCore.CHANNEL.sendToServer(new MessageRavagerStopDushStat(this));
    }

    private void dushStart() {
        SavageAndRavageCore.CHANNEL.sendToServer(new MessageRavagerDushStat(this));
    }

    private void attackingStart() {
        SavageAndRavageCore.CHANNEL.sendToServer(new MessageRavagerAttackStat(this));
    }

    public boolean isRidingPlayer(PlayerEntity player) {
        return this.getControllingPassenger() != null && this.getControllingPassenger() instanceof PlayerEntity && this.getControllingPassenger().getUniqueID().equals(player.getUniqueID());
    }


    /**
     * returns true if all the conditions for steering the entity are met. For pigs, this is true if it is being ridden
     * by a player and the player is holding a carrot-on-a-stick
     */


    @Override
    public boolean canBeSteered() {
        return isSaddled() && this.getControllingPassenger() instanceof LivingEntity;
    }

    protected void mountTo(PlayerEntity player) {
        if (!this.world.isRemote) {
            player.rotationYaw = this.rotationYaw;
            player.rotationPitch = this.rotationPitch;
            player.startRiding(this);
        }

    }

    public boolean processInteract(PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getHeldItem(hand);
        boolean flag = !itemstack.isEmpty();
        if (flag && itemstack.getItem() instanceof SpawnEggItem) {
            return super.processInteract(player, hand);
        } else if (flag && !isSaddled() && itemstack.getItem() instanceof SaddleItem) {
            this.playSound(SoundEvents.ENTITY_HORSE_SADDLE, 1.0F, 0.75F);
            this.setSaddled(true);

            return true;
        } else {
            if (this.isChild()) {
                    return super.processInteract(player, hand);
            } else {
                    this.mountTo(player);
                    return true;
            }

        }
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);

        compound.putBoolean("Saddled", isSaddled());
        compound.putInt("AttackTick", this.attackTick);
        compound.putInt("StunTick", this.stunTick);
        compound.putInt("RoarTick", this.roarTick);
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.setSaddled(compound.getBoolean("Saddled"));
        this.attackTick = compound.getInt("AttackTick");
        this.stunTick = compound.getInt("StunTick");
        this.roarTick = compound.getInt("RoarTick");
    }

    /**
     * Returns true if this entity should push and be pushed by other entities when colliding.
     */

    @Override
    public boolean canBePushed() {
        return !this.isBeingRidden();
    }

    /**
     * Dead and sleeping entities cannot move
     */

    @Override
    protected boolean isMovementBlocked() {
        return super.isMovementBlocked() && this.isBeingRidden() || this.attackTick > 0 || this.stunTick > 0 || this.roarTick > 0;
    }

    /**
     * For vehicles, the first passenger is generally considered the controller and "drives" the vehicle. For example,
     * Pigs, Horses, and Boats are generally "steered" by the controlling passenger.
     */
    @Nullable
    public Entity getControllingPassenger() {
        return this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
    }

    @Override
    public void updatePassenger(Entity passenger) {
        super.updatePassenger(passenger);
        if (passenger instanceof MobEntity) {
            MobEntity mobentity = (MobEntity) passenger;
            this.renderYawOffset = mobentity.renderYawOffset;
        }

    }

    @Override
    public void travel(Vec3d vector) {
        if (this.isAlive()) {

            if (this.isBeingRidden() && this.canBeSteered()) {
                LivingEntity livingentity = (LivingEntity) this.getControllingPassenger();
                this.rotationYaw = livingentity.rotationYaw;
                this.prevRotationYaw = this.rotationYaw;
                this.rotationPitch = livingentity.rotationPitch * 0.5F;
                this.setRotation(this.rotationYaw, this.rotationPitch);
                this.renderYawOffset = this.rotationYaw;
                this.rotationYawHead = this.renderYawOffset;
                float strafe = livingentity.moveStrafing * 0.5F;
                float forward = livingentity.moveForward;
                if (forward <= 0.0F) {
                    forward *= 0.25F;
                    this.gallopTime = 0;
                }

                if (this.onGround && this.jumpPower == 0.0F && !this.allowStandSliding) {
                    strafe = livingentity.moveStrafing / 3F;
                    forward = livingentity.moveForward / 2F;
                }

                if (this.jumpPower > 0.0F && !this.isHorseJumping() && this.onGround) {
                    double d0 = this.getHorseJumpStrength() * (double) this.jumpPower;
                    double d1;
                    if (this.isPotionActive(Effects.JUMP_BOOST)) {
                        d1 = d0 + (double) ((float) (this.getActivePotionEffect(Effects.JUMP_BOOST).getAmplifier() + 1) * 0.1F);
                    } else {
                        d1 = d0;
                    }

                    Vec3d vec3d = this.getMotion();
                    this.setMotion(vec3d.x, d1, vec3d.z);
                    this.setHorseJumping(true);
                    this.isAirBorne = true;
                    if (forward > 0.0F) {
                        float f2 = MathHelper.sin(this.rotationYaw * ((float) Math.PI / 180F));
                        float f3 = MathHelper.cos(this.rotationYaw * ((float) Math.PI / 180F));
                        this.setMotion(this.getMotion().add((double) (-0.4F * f2 * this.jumpPower), 0.0D, (double) (0.4F * f3 * this.jumpPower)));
                    }

                    this.jumpPower = 0.0F;
                }

                this.jumpMovementFactor = this.getAIMoveSpeed() * 0.1F;
                if (this.canPassengerSteer()) {
                    if (this.isBoosting()) {
                        this.setAIMoveSpeed((float) this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getValue() * 1.75F);
                    } else {
                        this.setAIMoveSpeed((float) this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getValue() * 1.0F);
                    }
                    super.travel(new Vec3d((double) strafe, vector.y, getAIMoveSpeed() * forward * 2.0F));
                } else if (livingentity instanceof PlayerEntity) {
                    this.setMotion(Vec3d.ZERO);
                }

                if (this.onGround) {
                    this.jumpPower = 0.0F;
                    this.setHorseJumping(false);
                }

            } else {
                this.jumpMovementFactor = 0.02F;
                super.travel(vector);
            }
        }
    }


    @OnlyIn(Dist.CLIENT)
    public void setJumpPower(int jumpPowerIn) {
        if (jumpPowerIn < 0) {
            jumpPowerIn = 0;
        } else {
            this.allowStandSliding = true;
        }

        if (jumpPowerIn >= 90) {
            this.jumpPower = 1.0F;
        } else {
            this.jumpPower = 0.4F + 0.4F * (float) jumpPowerIn / 90.0F;
        }

    }

    public void fall(float distance, float damageMultiplier) {
        if (distance > 1.0F) {
            this.playSound(SoundEvents.ENTITY_RAVAGER_STEP, 0.4F, 1.0F);
        }

        int i = MathHelper.ceil((distance * 0.5F - 3.0F) * damageMultiplier);
        if (i > 0) {
            this.attackEntityFrom(DamageSource.FALL, (float) i);
            if (this.isBeingRidden()) {
                for (Entity entity : this.getRecursivePassengers()) {
                    entity.attackEntityFrom(DamageSource.FALL, (float) i);
                }
            }

            BlockState blockstate = this.world.getBlockState(new BlockPos(this.posX, this.posY - 0.2D - (double) this.prevRotationYaw, this.posZ));
            if (!blockstate.isAir() && !this.isSilent()) {
                SoundType soundtype = blockstate.getSoundType();
                this.world.playSound((PlayerEntity) null, this.posX, this.posY, this.posZ, soundtype.getStepSound(), this.getSoundCategory(), soundtype.getVolume() * 0.5F, soundtype.getPitch() * 0.75F);
            }

        }
    }

    public boolean preventDespawn() {
        return false;
    }


    public boolean canDespawn(double distanceToClosestPlayer) {
        return false;
    }
}
