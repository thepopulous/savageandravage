package com.populousteam.savageandravage.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.populousteam.savageandravage.SavageAndRavage;
import com.populousteam.savageandravage.client.model.PoultryFarmerIllagerModel;
import com.populousteam.savageandravage.entity.illager.PoultryFarmerIllagerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.HeadLayer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PoultryFarmerIllagerRender<T extends PoultryFarmerIllagerEntity> extends MobRenderer<T, PoultryFarmerIllagerModel<T>> {
    private static final ResourceLocation TEXTURES = new ResourceLocation(SavageAndRavage.MODID, "textures/entity/illager/poultryfarmer_illager.png");

    public PoultryFarmerIllagerRender(EntityRendererManager p_i50959_1_) {
        super(p_i50959_1_, new PoultryFarmerIllagerModel<>(), 0.5F);
        this.addLayer(new HeldItemLayer<T, PoultryFarmerIllagerModel<T>>(this) {
            @Override
            public void render(MatrixStack p_225628_1_, IRenderTypeBuffer p_225628_2_, int p_225628_3_, T p_225628_4_, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {
                if (p_225628_4_.isAggressive()) {
                    super.render(p_225628_1_, p_225628_2_, p_225628_3_, p_225628_4_, p_225628_5_, p_225628_6_, p_225628_7_, p_225628_8_, p_225628_9_, p_225628_10_);
                }
            }
        });
        this.addLayer(new HeadLayer<>(this));
    }

    public ResourceLocation getEntityTexture(PoultryFarmerIllagerEntity entity) {
        return TEXTURES;
    }
}