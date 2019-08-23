package illager.savageandravage.entity;

import illager.savageandravage.entity.ai.FollowPlayerAndIllagerGoal;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.PandaEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;


public class SavagelingEntity extends AnimalEntity implements IMob {
    private static final Ingredient TEMPTATION_ITEMS = Ingredient.fromItems(Items.CHICKEN);
    public float wingRotation;
    public float destPos;
    public float oFlapSpeed;
    public float oFlap;
    public float wingRotDelta = 1.0F;
    public boolean chickenJockey;
    public int timeUntilNextSneeze = 6000 + world.rand.nextInt(300);

    public SavagelingEntity(EntityType<? extends SavagelingEntity> type, World worldIn) {
        super(type, worldIn);
        this.setPathPriority(PathNodeType.WATER, 0.0F);
        this.experienceValue = 4;
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(3, new LeapAtTargetGoal(this, 0.4F));
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.15D, false));
        this.goalSelector.addGoal(5, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new TemptGoal(this, 1.0D, false, TEMPTATION_ITEMS));
        this.goalSelector.addGoal(7, new FollowPlayerAndIllagerGoal(this, 1.0D, 2.5F, 12.0F));
        this.goalSelector.addGoal(7, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(9, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(10, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setCallsForHelp());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, ChickenEntity.class, true));
    }

    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return this.isChild() ? sizeIn.height * 0.85F : sizeIn.height * 0.92F;
    }

    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(16.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D);
    }

    public void tick() {
        super.tick();
        if (!this.world.isRemote && this.isAlive() && !this.isChild() && !this.isChickenJockey() && --this.timeUntilNextSneeze <= 0) {
            Vec3d vec3d = this.getMotion();
            this.world.addParticle(ParticleTypes.SNEEZE, this.posX - (double) (this.getWidth() + 1.0F) * 0.5D * (double) MathHelper.sin(this.renderYawOffset * ((float) Math.PI / 180F)), this.posY + (double) this.getEyeHeight() - (double) 0.1F, this.posZ + (double) (this.getWidth() + 1.0F) * 0.5D * (double) MathHelper.cos(this.renderYawOffset * ((float) Math.PI / 180F)), vec3d.x, 0.0D, vec3d.z);
            this.playSound(SoundEvents.ENTITY_PANDA_SNEEZE, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
            this.timeUntilNextSneeze = 6000 + world.rand.nextInt(300);

            for (PandaEntity pandaentity : this.world.getEntitiesWithinAABB(PandaEntity.class, this.getBoundingBox().grow(10.0D))) {
                if (!pandaentity.isChild() && pandaentity.onGround && !pandaentity.isInWater() && pandaentity.func_213537_eq()) {
                    pandaentity.getJumpController().setJumping();
                }
            }

            if (!this.world.isRemote() && this.rand.nextInt(700) == 0 && this.world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT)) {
                this.entityDropItem(Items.SLIME_BALL);
            }
        }

    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_CHICKEN_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_CHICKEN_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_CHICKEN_DEATH;
    }

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.ENTITY_CHICKEN_STEP, 0.15F, 1.0F);
    }

    public ChickenEntity createChild(AgeableEntity ageable) {
        return EntityType.CHICKEN.create(this.world);
    }

    /**
     * Checks if the parameter is an item which this animal can be fed to breed it (wheat, carrots or seeds depending on
     * the animal type)
     */
    public boolean isBreedingItem(ItemStack stack) {
        return TEMPTATION_ITEMS.test(stack);
    }

    /**
     * Get the experience points the entity currently has.
     */
    protected int getExperiencePoints(PlayerEntity player) {
        return this.isChickenJockey() ? 10 : super.getExperiencePoints(player);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.chickenJockey = compound.getBoolean("IsChickenJockey");
        if (compound.contains("EggLayTime")) {
            //this.timeUntilNextEgg = compound.getInt("EggLayTime");
        }

    }

    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putBoolean("IsChickenJockey", this.chickenJockey);
        //compound.putInt("EggLayTime", this.timeUntilNextEgg);
    }

    public boolean canDespawn(double distanceToClosestPlayer) {
        return this.isChickenJockey() && !this.isBeingRidden();
    }

    public void updatePassenger(Entity passenger) {
        super.updatePassenger(passenger);
        float f = MathHelper.sin(this.renderYawOffset * ((float) Math.PI / 180F));
        float f1 = MathHelper.cos(this.renderYawOffset * ((float) Math.PI / 180F));
        float f2 = 0.1F;
        float f3 = 0.0F;
        passenger.setPosition(this.posX + (double) (0.1F * f), this.posY + (double) (this.getHeight() * 0.5F) + passenger.getYOffset() + 0.0D, this.posZ - (double) (0.1F * f1));
        if (passenger instanceof LivingEntity) {
            ((LivingEntity) passenger).renderYawOffset = this.renderYawOffset;
        }

    }

    /**
     * Determines if this chicken is a jokey with a zombie riding it.
     */
    public boolean isChickenJockey() {
        return this.chickenJockey;
    }

    /**
     * Sets whether this chicken is a jockey or not.
     */
    public void setChickenJockey(boolean jockey) {
        this.chickenJockey = jockey;
    }

    @Override
    public boolean isOnSameTeam(Entity entityIn) {
        if (super.isOnSameTeam(entityIn)) {
            return true;
        } else if (entityIn instanceof LivingEntity && ((LivingEntity) entityIn).getCreatureAttribute() == CreatureAttribute.ILLAGER) {
            return this.getTeam() == null && entityIn.getTeam() == null;
        } else {
            return false;
        }
    }

    public CreatureAttribute getCreatureAttribute() {
        return CreatureAttribute.ILLAGER;
    }
}