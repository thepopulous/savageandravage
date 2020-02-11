package com.populousteam.savageandravage.client.render.layer;

import com.mojang.blaze3d.platform.GlStateManager;
import com.populousteam.savageandravage.client.model.CreepiesModel;
import com.populousteam.savageandravage.entity.CreepieEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CreepiesChargeLayer extends LayerRenderer<CreepieEntity, CreepiesModel<CreepieEntity>> {
    private static final ResourceLocation LIGHTNING_TEXTURE = new ResourceLocation("textures/entity/creeper/creeper_armor.png");
    private final CreepiesModel<CreepieEntity> creeperModel = new CreepiesModel<>(2.0F);

    public CreepiesChargeLayer(IEntityRenderer<CreepieEntity, CreepiesModel<CreepieEntity>> p_i50947_1_) {
        super(p_i50947_1_);
    }

    public void render(CreepieEntity entityIn, float p_212842_2_, float p_212842_3_, float p_212842_4_, float p_212842_5_, float p_212842_6_, float p_212842_7_, float p_212842_8_) {
        if (entityIn.getPowered()) {
            boolean flag = entityIn.isInvisible();
            GlStateManager.depthMask(!flag);
            this.bindTexture(LIGHTNING_TEXTURE);
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            float f = (float) entityIn.ticksExisted + p_212842_4_;
            GlStateManager.translatef(f * 0.01F, f * 0.01F, 0.0F);
            GlStateManager.matrixMode(5888);
            GlStateManager.enableBlend();
            float f1 = 0.5F;
            GlStateManager.color4f(0.5F, 0.5F, 0.5F, 1.0F);
            GlStateManager.disableLighting();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
            this.getEntityModel().setModelAttributes(this.creeperModel);
            GameRenderer gamerenderer = Minecraft.getInstance().gameRenderer;
            gamerenderer.setupFogColor(true);
            this.creeperModel.render(entityIn, p_212842_2_, p_212842_3_, p_212842_5_, p_212842_6_, p_212842_7_, p_212842_8_);
            gamerenderer.setupFogColor(false);
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            GlStateManager.matrixMode(5888);
            GlStateManager.enableLighting();
            GlStateManager.disableBlend();
            GlStateManager.depthMask(true);
        }
    }

    public boolean shouldCombineTextures() {
        return false;
    }
}