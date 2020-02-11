package com.populousteam.savageandravage.entity;

import com.populousteam.savageandravage.api.entity.EntityArmPose;
import com.populousteam.savageandravage.api.entity.IEntityArm;
import com.populousteam.savageandravage.init.SavageLootTables;
import net.minecraft.client.renderer.Quaternion;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.RangedBowAttackGoal;
import net.minecraft.entity.ai.goal.RangedCrossbowAttackGoal;
import net.minecraft.entity.monster.AbstractSkeletonEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SkeletonVillagerEntity extends AbstractSkeletonEntity implements ICrossbowUser, IEntityArm {
    private static final DataParameter<Boolean> DATA_CHARGING_STATE = EntityDataManager.createKey(SkeletonVillagerEntity.class, DataSerializers.BOOLEAN);
    private final RangedBowAttackGoal<SkeletonVillagerEntity> aiArrowAttack = new RangedBowAttackGoal<>(this, 1.0D, 20, 16.0F);
    private final MeleeAttackGoal aiAttackOnCollide = new MeleeAttackGoal(this, 1.2D, false) {
        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void resetTask() {
            super.resetTask();
            SkeletonVillagerEntity.this.setAggroed(false);
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting() {
            super.startExecuting();
            SkeletonVillagerEntity.this.setAggroed(true);
        }
    };
    private final RangedCrossbowAttackGoal<SkeletonVillagerEntity> aiCrossBowAttack = new RangedCrossbowAttackGoal<SkeletonVillagerEntity>(this, 1.0D, 12.0F);

    public SkeletonVillagerEntity(EntityType<? extends SkeletonVillagerEntity> type, World world) {
        super(type, world);
        this.setCombatTask();
        this.experienceValue = 5;
    }

    protected void registerData() {
        super.registerData();
        this.dataManager.register(DATA_CHARGING_STATE, false);
    }

    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(24.0D);
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_SKELETON_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_SKELETON_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SKELETON_DEATH;
    }

    protected SoundEvent getStepSound() {
        return SoundEvents.ENTITY_SKELETON_STEP;
    }

    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {
        super.setEquipmentBasedOnDifficulty(difficulty);
        this.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.CROSSBOW));
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isCharging() {
        return this.dataManager.get(DATA_CHARGING_STATE);
    }

    public void setCharging(boolean p_213671_1_) {
        this.dataManager.set(DATA_CHARGING_STATE, p_213671_1_);
    }

    @Override
    public void setCombatTask() {
        if (this.world != null && !this.world.isRemote) {
            this.goalSelector.removeGoal(this.aiAttackOnCollide);
            this.goalSelector.removeGoal(this.aiArrowAttack);
            this.goalSelector.removeGoal(this.aiCrossBowAttack);


            ItemStack itemstack2 = this.getHeldItem(ProjectileHelper.getHandWith(this, Items.CROSSBOW));
            if (itemstack2.getItem() instanceof net.minecraft.item.CrossbowItem) {
                this.goalSelector.addGoal(4, this.aiCrossBowAttack);
            } else {
                super.setCombatTask();
            }

        }
    }

    @Override
    public void shoot(LivingEntity p_213670_1_, ItemStack p_213670_2_, IProjectile p_213670_3_, float p_213670_4_) {
        Entity entity = (Entity) p_213670_3_;
        double d0 = p_213670_1_.posX - this.posX;
        double d1 = p_213670_1_.posZ - this.posZ;
        double d2 = MathHelper.sqrt(d0 * d0 + d1 * d1);
        double d3 = p_213670_1_.getBoundingBox().minY + (double) (p_213670_1_.getHeight() / 3.0F) - entity.posY + d2 * (double) 0.2F;
        Vector3f vector3f = this.func_213673_a(new Vec3d(d0, d3, d1), p_213670_4_);
        p_213670_3_.shoot(vector3f.getX(), vector3f.getY(), vector3f.getZ(), 1.6F, (float) (14 - this.world.getDifficulty().getId() * 4));
        this.playSound(SoundEvents.ITEM_CROSSBOW_SHOOT, 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
    }

    @Override
    protected ResourceLocation getLootTable() {
        return SavageLootTables.SKELETON_VILLAGER;
    }

    private Vector3f func_213673_a(Vec3d p_213673_1_, float p_213673_2_) {
        Vec3d vec3d = p_213673_1_.normalize();
        Vec3d vec3d1 = vec3d.crossProduct(new Vec3d(0.0D, 1.0D, 0.0D));
        if (vec3d1.lengthSquared() <= 1.0E-7D) {
            vec3d1 = vec3d.crossProduct(this.func_213286_i(1.0F));
        }

        Quaternion quaternion = new Quaternion(new Vector3f(vec3d1), 90.0F, true);
        Vector3f vector3f = new Vector3f(vec3d);
        vector3f.func_214905_a(quaternion);
        Quaternion quaternion1 = new Quaternion(vector3f, p_213673_2_, true);
        Vector3f vector3f1 = new Vector3f(vec3d);
        vector3f1.func_214905_a(quaternion1);
        return vector3f1;
    }

    @Override
    public EntityArmPose getEntityArmPose() {
        if (this.isCharging()) {
            return EntityArmPose.CROSSBOW_CHARGE;
        } else if (this.isHolding(Items.CROSSBOW)) {
            return EntityArmPose.CROSSBOW_HOLD;
        } else if (this.isHolding(Items.BOW)) {
            return this.isAggressive() ? EntityArmPose.BOW_AND_ARROW : EntityArmPose.CROSSED;
        } else {
            return this.isAggressive() ? EntityArmPose.ATTACKING : EntityArmPose.CROSSED;
        }
    }
}
