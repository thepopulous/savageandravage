package com.populousteam.savageandravage.client.model;

import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface IHasCrossArm {
    RendererModel crossHand();
}
