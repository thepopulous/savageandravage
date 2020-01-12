package com.populousteam.client.render.layer;

import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HeldCrossOffHandItemLayer<T extends AbstractIllagerEntity, M extends EntityModel<T>> extends LayerRenderer<T, M> {
    public HeldCrossOffHandItemLayer(IEntityRenderer<T, M> p_i50934_1_) {
        super(p_i50934_1_);
    }

    @Override
    public void render(T entityIn, float p_212842_2_, float p_212842_3_, float p_212842_4_, float p_212842_5_, float p_212842_6_, float p_212842_7_, float p_212842_8_) {

    }

    /*public void render(T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        ItemStack itemstack = entitylivingbaseIn.getHeldItemOffhand();
        AbstractIllagerEntity.ArmPose abstractillager$illagerarmpose = ((AbstractIllagerEntity) entitylivingbaseIn).getArmPose();

        Item item = itemstack.getItem();
        Minecraft minecraft = Minecraft.getInstance();

        if (!(itemstack.getItem() instanceof ShieldItem)) {
            if (!itemstack.isEmpty()) {
                if (itemstack.getItem() == SavageItems.SINISTER_HORN) {
                    GlStateManager.color3f(1.0F, 1.0F, 1.0F);
                    GlStateManager.pushMatrix();
                    if (this.getEntityModel().isChild) {
                        GlStateManager.translatef(0.0F, 0.625F, 0.0F);
                        GlStateManager.rotatef(-20.0F, -1.0F, 0.0F, 0.0F);
                        float f = 0.5F;
                        GlStateManager.scalef(0.5F, 0.5F, 0.5F);
                    }
                    GlStateManager.rotatef(90.0F, 0.0F, 0.0F, -1.0F);
                    GlStateManager.rotatef(180.0F, 0.0F, 1.0F, 0.0F);

                    GlStateManager.translatef(0.2625F, 0.53125F, 0.73875F);

                    this.getEntityModel().crossHand().postRender(0.0625F);

                } else {
                    GlStateManager.color3f(1.0F, 1.0F, 1.0F);
                    GlStateManager.pushMatrix();
                    if (this.getEntityModel().isChild) {
                        GlStateManager.translatef(0.0F, 0.625F, 0.0F);
                        GlStateManager.rotatef(-20.0F, -1.0F, 0.0F, 0.0F);
                        float f = 0.5F;
                        GlStateManager.scalef(0.5F, 0.5F, 0.5F);
                    }

                    this.getEntityModel().crossHand().postRender(0.0625F);
                    GlStateManager.translatef(-0.0625F, 0.53125F, 0.21875F);

                }
		    if (Block.getBlockFromItem(item).getDefaultState().getRenderType() == BlockRenderType.ENTITYBLOCK_ANIMATED) {
                    GlStateManager.translatef(0.0F, -0.2875F, -0.46F);
                    GlStateManager.rotatef(30.0F, 1.0F, 0.0F, 0.0F);
                    GlStateManager.rotatef(-5.0F, 0.0F, 1.0F, 0.0F);
                    float f1 = 0.375F;
                    GlStateManager.scalef(0.375F, -0.375F, 0.375F);
                } else if (item == Items.BOW) {
                    GlStateManager.translatef(0.0F, -0.2875F, -0.46F);
                    GlStateManager.rotatef(-45.0F, 0.0F, 1.0F, 0.0F);
                    float f2 = 0.625F;
                    GlStateManager.scalef(0.625F, -0.625F, 0.625F);
                    GlStateManager.rotatef(-100.0F, 1.0F, 0.0F, 0.0F);
                    GlStateManager.rotatef(-20.0F, 0.0F, 1.0F, 0.0F);
                } else {
                    GlStateManager.translatef(0.0F, -0.2875F, -0.46F);
                    float f3 = 0.875F;
                    GlStateManager.scalef(0.875F, 0.875F, 0.95F);
                    GlStateManager.rotatef(-30.0F, 0.0F, 1.0F, 0.0F);
                    GlStateManager.rotatef(-60.0F, 1.0F, 0.0F, 0.0F);
                }

                GlStateManager.rotatef(-15.0F, 1.0F, 0.0F, 0.0F);
                GlStateManager.rotatef(40.0F, 0.0F, 0.0F, 1.0F);
                minecraft.getItemRenderer().renderItem(itemstack, entitylivingbaseIn, ItemCameraTransforms.TransformType.GROUND, false);
                GlStateManager.popMatrix();
            }
        }
    }*/

    public boolean shouldCombineTextures() {
        return false;
    }
}