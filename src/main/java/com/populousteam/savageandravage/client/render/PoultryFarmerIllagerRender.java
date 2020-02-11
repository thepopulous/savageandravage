package com.populousteam.savageandravage.client.render;

import com.populousteam.savageandravage.SavageAndRavage;
import com.populousteam.savageandravage.client.model.PoultryFarmerIllagerModel;
import com.populousteam.savageandravage.entity.illager.PoultryFarmerIllagerEntity;
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
            public void render(T entityIn, float p_212842_2_, float p_212842_3_, float p_212842_4_, float p_212842_5_, float p_212842_6_, float p_212842_7_, float p_212842_8_) {
                if (entityIn.isAggressive()) {
                    super.render(entityIn, p_212842_2_, p_212842_3_, p_212842_4_, p_212842_5_, p_212842_6_, p_212842_7_, p_212842_8_);
                }
            }
        });
        this.addLayer(new HeadLayer<>(this));
    }

    protected ResourceLocation getEntityTexture(PoultryFarmerIllagerEntity entity) {
        return TEXTURES;
    }
}