package com.populousteam.savageandravage.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.populousteam.savageandravage.entity.illager.GrieferIllagerEntity;
import net.minecraft.client.renderer.entity.model.IHasArm;
import net.minecraft.client.renderer.entity.model.IHasHead;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * ModelGrieferIllager - Undefined
 * Created using Tabula 7.0.0
 */

@OnlyIn(Dist.CLIENT)
public class GrieferIllagerModel<T extends GrieferIllagerEntity> extends SegmentedModel<T> implements IHasArm, IHasHead {
    public ModelRenderer right_leg;
    public ModelRenderer left_leg;
    public ModelRenderer body;
    public ModelRenderer right_arm;
    public ModelRenderer left_arm;
    public ModelRenderer head;

    public GrieferIllagerModel() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        right_leg = new ModelRenderer(this);
        right_leg.setRotationPoint(-2.0F, 12.0F, 2.0F);
        right_leg.setTextureOffset(0, 18).addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F, false);

        left_leg = new ModelRenderer(this);
        left_leg.setRotationPoint(2.0F, 12.0F, 2.0F);
        left_leg.setTextureOffset(0, 18).addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F, true);

        body = new ModelRenderer(this);
        body.setRotationPoint(0.0F, 5.0F, 2.0F);
        body.setTextureOffset(36, 0).addBox(-4.0F, -5.0F, -3.0F, 8, 12, 6, 0.0F, false);
        body.setTextureOffset(36, 18).addBox(-4.0F, -5.0F, -3.0F, 8, 12, 6, 0.3F, false);
        body.setTextureOffset(50, 45).addBox(0.0F, 1.0F, -6.0F, 4, 4, 3, 0.0F, false);
        body.setTextureOffset(46, 36).addBox(-3.0F, -4.0F, 3.0F, 6, 6, 3, 0.0F, false);

        right_arm = new ModelRenderer(this);
        right_arm.setRotationPoint(-4.0F, 0.0F, 2.0F);
        right_arm.setTextureOffset(16, 34).addBox(-4.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F, false);
        right_arm.setTextureOffset(11, 51).addBox(-5.0F, -1.0F, -3.0F, 5, 6, 6, 0.0F, false);

        left_arm = new ModelRenderer(this);
        left_arm.setRotationPoint(4.0F, 0.0F, 2.0F);
        left_arm.setTextureOffset(16, 18).addBox(0.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F, false);

        head = new ModelRenderer(this);
        head.setRotationPoint(0.0F, 0.0F, 2.0F);
        head.setTextureOffset(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8, 10, 8, 0.0F, false);
        head.setTextureOffset(24, 0).addBox(-1.0F, -3.0F, -6.0F, 2, 4, 2, 0.0F, false);
    }

    @Override
    public Iterable<ModelRenderer> getParts() {
        return ImmutableList.of(this.left_leg, this.right_arm, this.right_leg, this.head, this.body, this.left_arm);
    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.rotateAngleY = netHeadYaw * ((float) Math.PI / 180F);
        this.head.rotateAngleX = headPitch * ((float) Math.PI / 180F);

        GrieferIllagerEntity.ArmPose abstractillagerentity$armpose = entityIn.getArmPose();

        if (Entity.horizontalMag(entityIn.getMotion()) > 0.0D) {
            this.right_arm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount * 0.2F;
            this.left_arm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.2F;
            this.right_arm.rotateAngleY = 0.0F;
            this.right_arm.rotateAngleZ = 0.0F;
            this.left_arm.rotateAngleY = 0.0F;
            this.left_arm.rotateAngleZ = 0.0F;
        } else {
            this.right_arm.rotateAngleX = 0.08726646259971647F;
            this.right_arm.rotateAngleY = -0.4363323129985824F;
            this.right_arm.rotateAngleZ = -0.4363323129985824F;
            this.left_arm.rotateAngleX = 0.08726646259971647F;
            this.left_arm.rotateAngleY = 0.4363323129985824F;
            this.left_arm.rotateAngleZ = 0.4363323129985824F;
        }

        if (this.isSitting) {

            this.right_arm.rotateAngleX = (-(float) Math.PI / 5F);
            this.right_arm.rotateAngleY = 0.0F;
            this.right_arm.rotateAngleZ = 0.0F;
            this.left_arm.rotateAngleX = (-(float) Math.PI / 5F);
            this.left_arm.rotateAngleY = 0.0F;
            this.left_arm.rotateAngleZ = 0.0F;

            this.right_leg.rotateAngleX = -1.4137167F;
            this.right_leg.rotateAngleY = ((float) Math.PI / 10F);
            this.right_leg.rotateAngleZ = 0.07853982F;
            this.left_leg.rotateAngleX = -1.4137167F;
            this.left_leg.rotateAngleY = (-(float) Math.PI / 10F);
            this.left_leg.rotateAngleZ = -0.07853982F;
        } else {
            this.right_leg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F;
            this.right_leg.rotateAngleY = 0.0F;
            this.right_leg.rotateAngleZ = 0.0F;
            this.left_leg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount * 0.5F;
            this.left_leg.rotateAngleY = 0.0F;
            this.left_leg.rotateAngleZ = 0.0F;

        }

        if (abstractillagerentity$armpose == GrieferIllagerEntity.ArmPose.ATTACKING) {
            float f = MathHelper.sin(this.swingProgress * (float) Math.PI);
            float f1 = MathHelper.sin((1.0F - (1.0F - this.swingProgress) * (1.0F - this.swingProgress)) * (float) Math.PI);
            this.right_arm.rotateAngleZ = 0.0F;
            this.left_arm.rotateAngleZ = 0.0F;
            this.right_arm.rotateAngleY = 0.15707964F;
            this.left_arm.rotateAngleY = -0.15707964F;
            if (entityIn.getPrimaryHand() == HandSide.RIGHT) {
                this.right_arm.rotateAngleX = -1.68F - MathHelper.cos(ageInTicks * 0.1F) * 0.3F;
                this.left_arm.rotateAngleX = -0.0F + MathHelper.cos(ageInTicks * 0.1F) * 0.3F;
                this.right_arm.rotateAngleX += f * 2.2F - f1 * 0.4F;
                this.left_arm.rotateAngleX += f * 1.2F - f1 * 0.4F;
            } else {
                this.right_arm.rotateAngleX = -0.0F - MathHelper.cos(ageInTicks * 0.1F) * 0.3F;
                this.left_arm.rotateAngleX = -1.68F + MathHelper.cos(ageInTicks * 0.1F) * 0.3F;
                this.right_arm.rotateAngleX += f * 1.2F - f1 * 0.4F;
                this.left_arm.rotateAngleX += f * 2.2F - f1 * 0.4F;
            }

            this.right_arm.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
            this.left_arm.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
            this.right_arm.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
            this.left_arm.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
        } else if (abstractillagerentity$armpose == GrieferIllagerEntity.ArmPose.CELEBRATING) {
            this.right_arm.rotationPointZ = 0.0F;
            this.right_arm.rotationPointX = -5.0F;
            this.right_arm.rotateAngleX = MathHelper.cos(ageInTicks * 0.6662F) * 0.05F;
            this.right_arm.rotateAngleZ = 2.670354F;
            this.right_arm.rotateAngleY = 0.0F;
            this.left_arm.rotationPointZ = 0.0F;
            this.left_arm.rotationPointX = 5.0F;
            this.left_arm.rotateAngleX = MathHelper.cos(ageInTicks * 0.6662F) * 0.05F;
            this.left_arm.rotateAngleZ = -2.3561945F;
            this.left_arm.rotateAngleY = 0.0F;
        }

    }

    private ModelRenderer getArm(HandSide p_191216_1_) {
        return p_191216_1_ == HandSide.LEFT ? this.left_arm : this.right_arm;
    }

    public ModelRenderer getModelHead() {
        return this.head;
    }

    @Override
    public void translateHand(HandSide handSide, MatrixStack matrixStack) {
        this.getArm(handSide).translateRotate(matrixStack);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
