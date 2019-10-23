package populousteam.savageandravage.entity.illager;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.VindicatorEntity;
import net.minecraft.world.World;
import populousteam.savageandravage.api.entity.EntityArmPose;
import populousteam.savageandravage.api.entity.IEntityArm;

public class RevampVindicatorEntity extends VindicatorEntity implements IEntityArm {
    public RevampVindicatorEntity(EntityType<? extends RevampVindicatorEntity> p_i50189_1_, World p_i50189_2_) {
        super(p_i50189_1_, p_i50189_2_);
    }

    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.ATTACK_KNOCKBACK).setBaseValue((double) 0.5F);
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        return super.attackEntityAsMob(entityIn);
    }

    public EntityArmPose getEntityArmPose() {
        if (this.isAggressive()) {
            return EntityArmPose.ATTACKING;
        } else {
            return this.func_213656_en() ? EntityArmPose.CELEBRATING : EntityArmPose.CROSSED;
        }
    }
}
