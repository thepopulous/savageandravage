package com.populousteam.savageandravage.api.entity;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface IEntityArm {
    @OnlyIn(Dist.CLIENT)
    EntityArmPose getEntityArmPose();
}
