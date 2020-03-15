package com.populousteam.savageandravage.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.populousteam.savageandravage.entity.illager.PoultryFarmerIllagerEntity;
import net.minecraft.client.renderer.entity.model.IHasArm;
import net.minecraft.client.renderer.entity.model.IHasHead;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * ModelPoultryFarmerIllager - Undefined
 * Created using Tabula 7.0.0
 */
@OnlyIn(Dist.CLIENT)
public class PoultryFarmerIllagerModel<T extends PoultryFarmerIllagerEntity> extends SegmentedModel<T> implements IHasArm, IHasHead {
    public ModelRenderer Head;
    public ModelRenderer HatFlap;
    public ModelRenderer Body;
    public ModelRenderer RightArm;
    public ModelRenderer LeftArm;
    public ModelRenderer RightLeg;
    public ModelRenderer LeftLeg;
    public ModelRenderer HatLayer;
    public ModelRenderer Cloak;
    public ModelRenderer Nose;
    public ModelRenderer Wheat;

    public PoultryFarmerIllagerModel() {
        this.textureWidth = 64;
        this.textureHeight = 128;
        this.RightArm = new ModelRenderer(this, 16, 38);
        this.RightArm.setRotationPoint(-5.0F, 4.0F, 0.0F);
        this.RightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
        this.setRotateAngle(RightArm, 0.0F, 0.0F, 1.3962634015954636F);
        this.Wheat = new ModelRenderer(this, -4, 0);
        this.Wheat.setRotationPoint(-2.0F, -1.0F, -3.5F);
        this.Wheat.addBox(-0.5F, 0.0F, -4.0F, 1, 0, 4, 0.0F);
        this.setRotateAngle(Wheat, 0.3490658503988659F, 0.2617993877991494F, 0.0F);
        this.HatLayer = new ModelRenderer(this, 32, 0);
        this.HatLayer.setRotationPoint(0.0F, 2.0F, 0.0F);
        this.HatLayer.addBox(-4.0F, -9.9F, -4.0F, 8, 10, 8, 0.5F);
        this.Cloak = new ModelRenderer(this, 0, 54);
        this.Cloak.setRotationPoint(0.0F, 2.0F, 0.0F);
        this.Cloak.addBox(-4.0F, 0.0F, -3.0F, 8, 19, 6, 0.5F);
        this.Nose = new ModelRenderer(this, 24, 0);
        this.Nose.setRotationPoint(0.0F, -3.0F, -4.0F);
        this.Nose.addBox(-1.0F, 0.0F, -2.0F, 2, 4, 2, 0.0F);
        this.LeftArm = new ModelRenderer(this, 16, 38);
        this.LeftArm.mirror = true;
        this.LeftArm.setRotationPoint(5.0F, 4.0F, 0.0F);
        this.LeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
        this.setRotateAngle(LeftArm, 0.0F, 0.0F, -1.3962634015954636F);
        this.Body = new ModelRenderer(this, 16, 20);
        this.Body.setRotationPoint(0.0F, 2.0F, 0.0F);
        this.Body.addBox(-4.0F, 0.0F, -3.0F, 8, 12, 6, 0.0F);
        this.HatFlap = new ModelRenderer(this, 16, 38);
        this.HatFlap.setRotationPoint(0.0F, 2.0F, 0.0F);
        this.HatFlap.addBox(-8.0F, -6.0F, -8.0F, 16, 0, 16, 0.0F);
        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.setRotationPoint(0.0F, 2.0F, 0.0F);
        this.Head.addBox(-4.0F, -10.0F, -4.0F, 8, 10, 8, 0.0F);
        this.RightLeg = new ModelRenderer(this, 0, 22);
        this.RightLeg.setRotationPoint(-2.0F, 12.0F, 0.0F);
        this.RightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.setRotateAngle(RightLeg, -0.17453292519943295F, 0.5235987755982988F, 0.0F);
        this.LeftLeg = new ModelRenderer(this, 0, 38);
        this.LeftLeg.setRotationPoint(2.0F, 12.0F, 0.0F);
        this.LeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.setRotateAngle(LeftLeg, 0.3490658503988659F, 0.17453292519943295F, 0.0F);
        this.Head.addChild(this.Wheat);
        this.Head.addChild(this.Nose);
    }

    @Override
    public Iterable<ModelRenderer> getParts() {
        return ImmutableList.of(this.RightArm, this.HatLayer, this.Cloak, this.LeftArm, this.Body, this.HatFlap, this.Head, this.RightLeg, this.LeftLeg);
    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.Head.rotateAngleY = netHeadYaw * ((float) Math.PI / 180F);
        this.Head.rotateAngleX = headPitch * ((float) Math.PI / 180F);

        this.HatFlap.rotateAngleY = netHeadYaw * ((float) Math.PI / 180F);
        this.HatFlap.rotateAngleX = headPitch * ((float) Math.PI / 180F);
        this.HatLayer.rotateAngleY = netHeadYaw * ((float) Math.PI / 180F);
        this.HatLayer.rotateAngleX = headPitch * ((float) Math.PI / 180F);

        PoultryFarmerIllagerEntity.ArmPose abstractillagerentity$armpose = entityIn.getArmPose();


        this.RightArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount * 0.2F;
        this.LeftArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.2F;
        this.RightArm.rotateAngleY = 0.0F;
        this.RightArm.rotateAngleZ = 0.0F;
        this.LeftArm.rotateAngleY = 0.0F;
        this.LeftArm.rotateAngleZ = 0.0F;


        if (this.isSitting) {

            this.RightArm.rotateAngleX = (-(float) Math.PI / 5F);
            this.RightArm.rotateAngleY = 0.0F;
            this.RightArm.rotateAngleZ = 0.0F;
            this.LeftArm.rotateAngleX = (-(float) Math.PI / 5F);
            this.LeftArm.rotateAngleY = 0.0F;
            this.LeftArm.rotateAngleZ = 0.0F;

            this.RightLeg.rotateAngleX = -1.4137167F;
            this.RightLeg.rotateAngleY = ((float) Math.PI / 10F);
            this.RightLeg.rotateAngleZ = 0.07853982F;
            this.LeftLeg.rotateAngleX = -1.4137167F;
            this.LeftLeg.rotateAngleY = (-(float) Math.PI / 10F);
            this.LeftLeg.rotateAngleZ = -0.07853982F;
        } else {
            this.RightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F;
            this.RightLeg.rotateAngleY = 0.0F;
            this.RightLeg.rotateAngleZ = 0.0F;
            this.LeftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount * 0.5F;
            this.LeftLeg.rotateAngleY = 0.0F;
            this.LeftLeg.rotateAngleZ = 0.0F;

        }

        if (abstractillagerentity$armpose == PoultryFarmerIllagerEntity.ArmPose.ATTACKING) {
            float f = MathHelper.sin(this.swingProgress * (float) Math.PI);
            float f1 = MathHelper.sin((1.0F - (1.0F - this.swingProgress) * (1.0F - this.swingProgress)) * (float) Math.PI);
            this.RightArm.rotateAngleZ = 0.0F;
            this.LeftArm.rotateAngleZ = 0.0F;
            this.RightArm.rotateAngleY = 0.15707964F;
            this.LeftArm.rotateAngleY = -0.15707964F;
            if (entityIn.getPrimaryHand() == HandSide.RIGHT) {
                this.RightArm.rotateAngleX = -1.68F - MathHelper.cos(ageInTicks * 0.1F) * 0.3F;
                this.LeftArm.rotateAngleX = -0.0F + MathHelper.cos(ageInTicks * 0.1F) * 0.3F;
                this.RightArm.rotateAngleX += f * 2.2F - f1 * 0.4F;
                this.LeftArm.rotateAngleX += f * 1.2F - f1 * 0.4F;
            } else {
                this.RightArm.rotateAngleX = -0.0F - MathHelper.cos(ageInTicks * 0.1F) * 0.3F;
                this.LeftArm.rotateAngleX = -1.68F + MathHelper.cos(ageInTicks * 0.1F) * 0.3F;
                this.RightArm.rotateAngleX += f * 1.2F - f1 * 0.4F;
                this.LeftArm.rotateAngleX += f * 2.2F - f1 * 0.4F;
            }

            this.RightArm.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
            this.LeftArm.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
            this.RightArm.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
            this.LeftArm.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
        } else if (abstractillagerentity$armpose == PoultryFarmerIllagerEntity.ArmPose.CELEBRATING) {
            this.RightArm.rotationPointZ = 0.0F;
            this.RightArm.rotationPointX = -5.0F;
            this.RightArm.rotateAngleX = MathHelper.cos(ageInTicks * 0.6662F) * 0.05F;
            this.RightArm.rotateAngleZ = 2.670354F;
            this.RightArm.rotateAngleY = 0.0F;
            this.LeftArm.rotationPointZ = 0.0F;
            this.LeftArm.rotationPointX = 5.0F;
            this.LeftArm.rotateAngleX = MathHelper.cos(ageInTicks * 0.6662F) * 0.05F;
            this.LeftArm.rotateAngleZ = -2.3561945F;
            this.LeftArm.rotateAngleY = 0.0F;
        }

        if (!entityIn.getPassengers().isEmpty() && entityIn.getPassengers().get(0) != null) {
            this.RightArm.rotateAngleX = (float) Math.PI;
            this.LeftArm.rotateAngleX = (float) Math.PI;
            this.RightArm.rotateAngleY = 0.0F;
            this.RightArm.rotateAngleZ = 0.0F;
            this.LeftArm.rotateAngleY = 0.0F;
            this.LeftArm.rotateAngleZ = 0.0F;

        }

    }

    private ModelRenderer getArm(HandSide p_191216_1_) {
        return p_191216_1_ == HandSide.LEFT ? this.LeftArm : this.RightArm;
    }

    public ModelRenderer getModelHead() {
        return this.Head;
    }

    @Override
    public void translateHand(HandSide handSide, MatrixStack matrixStack) {
        this.getArm(handSide).translateRotate(matrixStack);
    }


    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
