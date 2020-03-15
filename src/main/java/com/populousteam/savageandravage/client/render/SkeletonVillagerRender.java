package com.populousteam.savageandravage.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.populousteam.savageandravage.SavageAndRavage;
import com.populousteam.savageandravage.client.model.SkeletonVillagerModel;
import com.populousteam.savageandravage.entity.SkeletonVillagerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.layers.HeadLayer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.client.renderer.entity.model.ZombieVillagerModel;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SkeletonVillagerRender<T extends SkeletonVillagerEntity> extends MobRenderer<T, SkeletonVillagerModel<T>> {
    private static final ResourceLocation TEXTURES = new ResourceLocation(SavageAndRavage.MODID, "textures/entity/skeletonvillager.png");

    public SkeletonVillagerRender(EntityRendererManager p_i50959_1_) {
        super(p_i50959_1_, new SkeletonVillagerModel<>(), 0.5F);
        this.addLayer(new HeldItemLayer<T, SkeletonVillagerModel<T>>(this) {
            @Override
            public void render(MatrixStack p_225628_1_, IRenderTypeBuffer p_225628_2_, int p_225628_3_, T entityIn, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {
                if (entityIn.isAggressive() || entityIn.isCharging() || entityIn.isHolding(Items.CROSSBOW)) {
                    super.render(p_225628_1_, p_225628_2_, p_225628_3_, entityIn, p_225628_5_, p_225628_6_, p_225628_7_, p_225628_8_, p_225628_9_, p_225628_10_);
                }
            }
        });
        this.addLayer(new HeadLayer<>(this));
        this.addLayer(new BipedArmorLayer(this, new ZombieVillagerModel(0.5F, true), new ZombieVillagerModel(1.0F, true)));
    }

    public ResourceLocation getEntityTexture(SkeletonVillagerEntity entity) {
        return TEXTURES;
    }
}