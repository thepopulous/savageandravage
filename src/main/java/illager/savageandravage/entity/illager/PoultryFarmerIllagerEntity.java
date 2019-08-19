package illager.savageandravage.entity.illager;

import illager.savageandravage.entity.ai.RangedStrafeAttackGoal;
import illager.savageandravage.init.SavageLootTables;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.monster.AbstractRaiderEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.EggEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.util.*;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class PoultryFarmerIllagerEntity extends AbstractHouseIllagerEntity implements IRangedAttackMob {


    public PoultryFarmerIllagerEntity(EntityType<? extends PoultryFarmerIllagerEntity> type, World worldIn) {
        super(type, worldIn);
        ((GroundPathNavigator) this.getNavigator()).setBreakDoors(true);
        this.experienceValue = 6;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(2, new OpenDoorGoal(this, true));
        this.goalSelector.addGoal(3, new MoveToHomeGoal(this, 5.0D, 0.85D));
        this.goalSelector.addGoal(4, new RangedStrafeAttackGoal<>(this, 0.75D, 60, 20.0F));
        this.goalSelector.addGoal(8, new RandomWalkingGoal(this, 0.75D));
        this.goalSelector.addGoal(9, new LookAtGoal(this, PlayerEntity.class, 3.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtGoal(this, MobEntity.class, 8.0F));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, AbstractRaiderEntity.class)).setCallsForHelp());
        this.targetSelector.addGoal(2, (new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true, true)).setUnseenMemoryTicks(300));
        this.targetSelector.addGoal(3, (new NearestAttackableTargetGoal<>(this, AbstractVillagerEntity.class, true, true)).setUnseenMemoryTicks(300));
        this.targetSelector.addGoal(3, (new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true, true)).setUnseenMemoryTicks(300));
    }

    @Override
    public void func_213660_a(int p_213660_1_, boolean p_213660_2_) {

    }

    @Override
    public SoundEvent getRaidLossSound() {
        return SoundEvents.ENTITY_VINDICATOR_CELEBRATE;
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


    @Override
    protected ResourceLocation getLootTable() {
        return SavageLootTables.POULTRY_FARMER;
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();

        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double) 0.348F);
        this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16.0D);
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(24.0D);
        this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0D);
    }

    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {
        this.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.EGG));
    }

    @Override
    public void setLeader(boolean p_213635_1_) {
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

    @OnlyIn(Dist.CLIENT)
    public ArmPose getArmPose() {
        if (this.isAggressive()) {
            return ArmPose.ATTACKING;
        } else {
            return this.func_213656_en() ? ArmPose.CELEBRATING : ArmPose.CROSSED;
        }
    }

    @Override
    public void attackEntityWithRangedAttack(LivingEntity target, float distanceFactor) {
        this.swingArm(Hand.MAIN_HAND);

        this.world.playSound((PlayerEntity) null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_EGG_THROW, SoundCategory.PLAYERS, 0.5F, 0.4F / (this.world.rand.nextFloat() * 0.4F + 0.8F));

        EggEntity eggEntity = new EggEntity(this.world, this);

        double d0 = target.posY + (double) target.getEyeHeight() - (double) 1.1F;
        double d1 = target.posX - this.posX;
        double d2 = d0 - eggEntity.posY;
        double d3 = target.posZ - this.posZ;

        float f = MathHelper.sqrt(d1 * d1 + d3 * d3) * 0.03F;
        eggEntity.shoot(d1, d2 + (double) f, d3, 1.6F, 12.0F);

        this.world.addEntity(eggEntity);
    }
}
