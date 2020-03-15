package com.populousteam.savageandravage.client.render.layer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.populousteam.savageandravage.client.model.IHasCrossArm;
import com.populousteam.savageandravage.init.SavageItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.CrossedArmsItemLayer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.IHasHead;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShieldItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HeldCrossOffHandItemLayer<T extends AbstractIllagerEntity, M extends EntityModel<T> & IHasCrossArm & IHasHead> extends CrossedArmsItemLayer<T, M> {
    public HeldCrossOffHandItemLayer(IEntityRenderer<T, M> p_i50934_1_) {
        super(p_i50934_1_);
    }

    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        ItemStack itemstack = entitylivingbaseIn.getHeldItemOffhand();
        AbstractIllagerEntity.ArmPose abstractillager$illagerarmpose = entitylivingbaseIn.getArmPose();

        Item item = itemstack.getItem();
        Minecraft minecraft = Minecraft.getInstance();

        if (!(itemstack.getItem() instanceof ShieldItem)) {
            if (!itemstack.isEmpty()) {
                matrixStackIn.push();
                if (itemstack.getItem() == SavageItems.SINISTERHORN) {
                    this.getEntityModel().getModelHead().translateRotate(matrixStackIn);
                    this.getEntityModel().crossHand().translateRotate(matrixStackIn);
                    matrixStackIn.translate(0.0625D, 0.25D, 0.0D);
                    matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(180.0F));
                    matrixStackIn.rotate(Vector3f.XP.rotationDegrees(140.0F));
                    matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(10.0F));
                    matrixStackIn.translate(0.0D, (double) -0.4F, (double) 0.4F);

                } else if (itemstack.getItem() == Items.POTION) {
                    this.getEntityModel().getModelHead().translateRotate(matrixStackIn);
                    this.getEntityModel().crossHand().translateRotate(matrixStackIn);
                    matrixStackIn.translate(0.0625D, 0.25D, 0.0D);
                    matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(180.0F));
                    matrixStackIn.rotate(Vector3f.XP.rotationDegrees(140.0F));
                    matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(10.0F));
                    matrixStackIn.translate(0.0D, (double) -0.4F, (double) 0.4F);
                }

                super.render(matrixStackIn, bufferIn, packedLightIn, entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch);
                matrixStackIn.pop();
            }
        }
    }

    public boolean shouldCombineTextures() {
        return false;
    }
}