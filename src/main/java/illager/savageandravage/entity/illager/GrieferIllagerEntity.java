package illager.savageandravage.entity.illager;

import com.google.common.collect.Lists;
import illager.savageandravage.entity.CreeperSporeEntity;
import illager.savageandravage.entity.ai.RangedStrafeAttackGoal;
import illager.savageandravage.init.SavageItems;
import illager.savageandravage.init.SavageLootTables;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.item.FireworkRocketEntity;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.entity.monster.AbstractRaiderEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.DyeColor;
import net.minecraft.item.FireworkRocketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.raid.Raid;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;

public class GrieferIllagerEntity extends AbstractIllagerEntity implements IRangedAttackMob {

    public GrieferIllagerEntity(EntityType<? extends GrieferIllagerEntity> type, World worldIn) {
        super(type, worldIn);
        ((GroundPathNavigator) this.getNavigator()).setBreakDoors(true);
        this.experienceValue = 6;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new OpenDoorGoal(this, true));
        this.goalSelector.addGoal(1, new AbstractRaiderEntity.PromoteLeaderGoal<>(this));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, PlayerEntity.class, 4.0F, 0.82D, 1.0D));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, IronGolemEntity.class, 5.5F, 0.9D, 1.1D));
        this.goalSelector.addGoal(3, new MoveTowardsRaidGoal<>(this));
        this.goalSelector.addGoal(4, new RangedStrafeAttackGoal(this, 0.75D, 60, 20.0F));
        this.goalSelector.addGoal(5, new GrieferIllagerEntity.CelebrateRaidLossFireWorkGoal(this));
        this.goalSelector.addGoal(8, new RandomWalkingGoal(this, 0.75D));
        this.goalSelector.addGoal(9, new LookAtGoal(this, PlayerEntity.class, 3.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtGoal(this, MobEntity.class, 8.0F));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, GrieferIllagerEntity.class)).setCallsForHelp());
        this.targetSelector.addGoal(2, (new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true)).setUnseenMemoryTicks(300));
        this.targetSelector.addGoal(3, (new NearestAttackableTargetGoal<>(this, AbstractVillagerEntity.class, false)).setUnseenMemoryTicks(300));
        this.targetSelector.addGoal(3, (new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, false)).setUnseenMemoryTicks(300));
    }

    protected void registerAttributes() {
        super.registerAttributes();

        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double) 0.35F);
        this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(22.0D);
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(24.0D);
        this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);
    }

    protected void registerData() {
        super.registerData();

    }

    @Override
    public void func_213660_a(int p_213660_1_, boolean p_213660_2_) {

    }


    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);

    }


    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);

    }

    public void setRevengeTarget(@Nullable LivingEntity livingBase) {
        super.setRevengeTarget(livingBase);
        if (livingBase != null) {
            if (livingBase instanceof PlayerEntity) {

                if (!((PlayerEntity) livingBase).isCreative() && this.isAlive()) {
                    this.world.setEntityState(this, (byte) 13);
                }
            }
        }

    }

    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            return super.attackEntityFrom(source, amount);

        }
    }


    @OnlyIn(Dist.CLIENT)
    public ArmPose getArmPose() {
        if (this.isAggressive()) {
            return ArmPose.ATTACKING;
        } else {
            return this.func_213656_en() ? ArmPose.CELEBRATING : ArmPose.CROSSED;
        }
    }

    @Nullable
    @Override
    public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        ILivingEntityData ientitylivingdata = super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
        this.setEquipmentBasedOnDifficulty(difficultyIn);
        this.setEnchantmentBasedOnDifficulty(difficultyIn);
        return ientitylivingdata;
    }

    @Override
    public SoundEvent getRaidLossSound() {
        return SoundEvents.ENTITY_VINDICATOR_CELEBRATE;
    }

    /**
     * Gives armor or weapon for entity based on given DifficultyInstance
     */
    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {
        this.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(SavageItems.CREEPER_SPORES));
    }


    /**
     * Returns whether this Entity is on the same team as the given Entity.
     */
    public boolean isOnSameTeam(Entity entityIn) {
        if (super.isOnSameTeam(entityIn)) {
            return true;
        } else if (entityIn instanceof LivingEntity && ((LivingEntity) entityIn).getCreatureAttribute() == CreatureAttribute.ILLAGER) {
            return this.getTeam() == null && entityIn.getTeam() == null;
        } else {
            return false;
        }
    }

    @Override
    public void attackEntityWithRangedAttack(LivingEntity target, float distanceFactor) {
        this.swingArm(Hand.MAIN_HAND);

        this.world.playSound((PlayerEntity) null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_EGG_THROW, SoundCategory.PLAYERS, 0.5F, 0.4F / (this.world.rand.nextFloat() * 0.4F + 0.8F));

        CreeperSporeEntity snowballentity = new CreeperSporeEntity(this.world, this);

        double d0 = target.posY + (double) target.getEyeHeight() - (double) 1.1F;
        double d1 = target.posX - this.posX;
        double d2 = d0 - snowballentity.posY;
        double d3 = target.posZ - this.posZ;

        float f = MathHelper.sqrt(d1 * d1 + d3 * d3) * 0.05F;
        snowballentity.shoot(d1, d2 + (double) f, d3, 1.6F, 12.0F);

        this.playSound(SoundEvents.ENTITY_SNOW_GOLEM_SHOOT, 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
        this.world.addEntity(snowballentity);
    }


    @Override
    protected SoundEvent getAmbientSound() {

        return SoundEvents.ENTITY_VINDICATOR_AMBIENT;

    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_VINDICATOR_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_VINDICATOR_HURT;
    }

    @Nullable
    @Override
    protected ResourceLocation getLootTable() {
        return SavageLootTables.GRIFER_ILLAGER;
    }

    public class CelebrateRaidLossFireWorkGoal extends Goal {
        private final GrieferIllagerEntity raider;

        CelebrateRaidLossFireWorkGoal(GrieferIllagerEntity p_i50571_2_) {
            this.raider = p_i50571_2_;
            this.setMutexFlags(EnumSet.of(Flag.MOVE));
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute() {
            Raid raid = this.raider.getRaid();
            return this.raider.isAlive() && this.raider.getAttackTarget() == null && raid != null && raid.isLoss();
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting() {
            this.raider.func_213655_u(true);
            super.startExecuting();
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void resetTask() {
            this.raider.func_213655_u(false);
            super.resetTask();
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            if (!this.raider.isSilent() && this.raider.rand.nextInt(100) == 0) {
                raider.playSound(raider.getRaidLossSound(), raider.getSoundVolume(), raider.getSoundPitch());
            }

            if (!this.raider.isPassenger() && this.raider.rand.nextInt(50) == 0) {
                this.raider.getJumpController().setJumping();
            }

            if (canSeeSky((ServerWorld) this.raider.world, this.raider) && this.raider.rand.nextInt(80) == 0) {
                DyeColor dyecolor = DyeColor.values()[this.raider.rand.nextInt(DyeColor.values().length)];
                int i = this.raider.rand.nextInt(3);
                ItemStack itemstack = this.makeFirework(dyecolor, i);
                FireworkRocketEntity fireworkrocketentity = new FireworkRocketEntity(this.raider.world, this.raider.posX, this.raider.posY + (double) this.raider.getEyeHeight(), this.raider.posZ, itemstack);
                this.raider.world.addEntity(fireworkrocketentity);
            }


            super.tick();
        }

        public boolean canSeeSky(ServerWorld p_223015_0_, LivingEntity p_223015_1_) {
            return p_223015_0_.isSkyLightMax(new BlockPos(p_223015_1_)) && (double) p_223015_0_.getHeight(Heightmap.Type.MOTION_BLOCKING, new BlockPos(p_223015_1_)).getY() <= p_223015_1_.posY;
        }

        private ItemStack makeFirework(DyeColor color, int flightTime) {
            ItemStack itemstack = new ItemStack(Items.FIREWORK_ROCKET, 1);
            ItemStack itemstack1 = new ItemStack(Items.FIREWORK_STAR);
            CompoundNBT compoundnbt = itemstack1.getOrCreateChildTag("Explosion");
            List<Integer> list = Lists.newArrayList();
            list.add(color.getFireworkColor());
            compoundnbt.putIntArray("Colors", list);
            compoundnbt.putByte("Type", (byte) FireworkRocketItem.Shape.CREEPER.func_196071_a());
            CompoundNBT compoundnbt1 = itemstack.getOrCreateChildTag("Fireworks");
            ListNBT listnbt = new ListNBT();
            CompoundNBT compoundnbt2 = itemstack1.getChildTag("Explosion");
            if (compoundnbt2 != null) {
                listnbt.add(compoundnbt2);
            }

            compoundnbt1.putByte("Flight", (byte) flightTime);
            if (!listnbt.isEmpty()) {
                compoundnbt1.put("Explosions", listnbt);
            }

            return itemstack;
        }
    }
}