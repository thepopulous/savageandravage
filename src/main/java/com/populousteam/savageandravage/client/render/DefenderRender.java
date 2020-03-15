package com.populousteam.savageandravage.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.populousteam.savageandravage.SavageAndRavage;
import com.populousteam.savageandravage.client.model.DefenderModel;
import com.populousteam.savageandravage.client.render.layer.HeldCrossOffHandItemLayer;
import com.populousteam.savageandravage.entity.illager.DefenderEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.HeadLayer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DefenderRender<T extends DefenderEntity> extends MobRenderer<T, DefenderModel<T>> {
    private static final ResourceLocation ILLAGER_TEXTURE = new ResourceLocation(SavageAndRavage.MODID, "textures/entity/illager/guardillager.png");

    public DefenderRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new DefenderModel<>(), 0.5F);
        this.addLayer(new HeadLayer<>(this));
        this.addLayer(new HeldItemLayer<T, DefenderModel<T>>(this) {

            @Override
            public void render(MatrixStack p_225628_1_, IRenderTypeBuffer p_225628_2_, int p_225628_3_, T p_225628_4_, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {
                if (((DefenderEntity) p_225628_4_).isAggressive()) {
                    super.render(p_225628_1_, p_225628_2_, p_225628_3_, p_225628_4_, p_225628_5_, p_225628_6_, p_225628_7_, p_225628_8_, p_225628_9_, p_225628_10_);
                }
            }
        });
        this.addLayer(new HeldCrossOffHandItemLayer<T, DefenderModel<T>>(this) {
            @Override
            public void render(MatrixStack p_225628_1_, IRenderTypeBuffer p_225628_2_, int p_225628_3_, T p_225628_4_, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {
                if (!((DefenderEntity) p_225628_4_).isAggressive()) {
                    super.render(p_225628_1_, p_225628_2_, p_225628_3_, p_225628_4_, p_225628_5_, p_225628_6_, p_225628_7_, p_225628_8_, p_225628_9_, p_225628_10_);
                }
            }
        });
    }

    public ResourceLocation getEntityTexture(DefenderEntity entity) {
        return ILLAGER_TEXTURE;
    }

}