package com.populousteam.savageandravage.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.populousteam.savageandravage.entity.illager.DefenderEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.model.IHasArm;
import net.minecraft.client.renderer.entity.model.IHasHead;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DefenderModel<T extends DefenderEntity> extends SegmentedModel<T> implements IHasArm, IHasHead, IHasCrossArm {
    public ModelRenderer Head;
    public ModelRenderer Nose;
    public ModelRenderer HatLayer;
    public ModelRenderer HatFlap;
    public ModelRenderer Body;
    public ModelRenderer ChestPlate;
    public ModelRenderer RightLeg;
    public ModelRenderer LeftLeg;
    public ModelRenderer RightOpenArm;
    public ModelRenderer LeftOpenArm;
    public ModelRenderer MiddleClosedArm;
    public ModelRenderer Cape;
    public ModelRenderer RightClosedArm;
    public ModelRenderer LeftClosedArm;

    public DefenderModel() {
        this.textureWidth = 64;
        this.textureHeight = 128;
        this.MiddleClosedArm = new ModelRenderer(this, 0, 56);
        this.MiddleClosedArm.setRotationPoint(0.0F, 3.0F, -1.0F);
        this.MiddleClosedArm.addBox(-4.0F, 2.0F, -2.0F, 8, 4, 4, 0.0F);
        this.setRotateAngle(MiddleClosedArm, -0.7853981633974483F, 0.0F, 0.0F);
        this.LeftLeg = new ModelRenderer(this, 0, 18);
        this.LeftLeg.mirror = true;
        this.LeftLeg.setRotationPoint(2.0F, 12.0F, 0.0F);
        this.LeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.RightClosedArm = new ModelRenderer(this, 48, 36);
        this.RightClosedArm.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.RightClosedArm.addBox(-8.0F, -2.0F, -2.0F, 4, 8, 4, 0.0F);
        this.Nose = new ModelRenderer(this, 24, 0);
        this.Nose.setRotationPoint(0.0F, -3.0F, -4.0F);
        this.Nose.addBox(-1.0F, 0.0F, -2.0F, 2, 4, 2, 0.0F);
        this.LeftOpenArm = new ModelRenderer(this, 44, 18);
        this.LeftOpenArm.mirror = true;
        this.LeftOpenArm.setRotationPoint(5.0F, 2.0F, 0.0F);
        this.LeftOpenArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
        this.RightLeg = new ModelRenderer(this, 0, 18);
        this.RightLeg.setRotationPoint(-2.0F, 12.0F, 0.0F);
        this.RightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.ChestPlate = new ModelRenderer(this, 0, 36);
        this.ChestPlate.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.ChestPlate.addBox(-4.0F, 0.0F, -3.0F, 8, 14, 6, 0.5F);
        this.Body = new ModelRenderer(this, 16, 18);
        this.Body.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Body.addBox(-4.0F, 0.0F, -3.0F, 8, 12, 6, 0.0F);
        this.LeftClosedArm = new ModelRenderer(this, 48, 36);
        this.LeftClosedArm.mirror = true;
        this.LeftClosedArm.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.LeftClosedArm.addBox(4.0F, -2.0F, -2.0F, 4, 8, 4, 0.0F);
        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Head.addBox(-4.0F, -10.0F, -4.0F, 8, 10, 8, 0.0F);
        this.HatFlap = new ModelRenderer(this, 16, 48);
        this.HatFlap.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.HatFlap.addBox(-8.0F, -4.9F, -8.0F, 16, 0, 16, 0.0F);
        this.RightOpenArm = new ModelRenderer(this, 44, 18);
        this.RightOpenArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
        this.RightOpenArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
        this.Cape = new ModelRenderer(this, 0, 64);
        this.Cape.setRotationPoint(0.0F, 0.0F, 3.6F);
        this.Cape.addBox(-4.0F, 0.0F, -1.0F, 8, 14, 1, 0.0F);
        this.setRotateAngle(Cape, 0.17453292519943295F, 0.0F, 0.0F);
        this.HatLayer = new ModelRenderer(this, 32, 0);
        this.HatLayer.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.HatLayer.addBox(-4.0F, -9.9F, -4.0F, 8, 10, 8, 0.5F);
        this.MiddleClosedArm.addChild(this.RightClosedArm);
        this.MiddleClosedArm.addChild(this.LeftClosedArm);
        this.Head.addChild(this.HatLayer);
        this.Head.addChild(this.HatFlap);
        this.Head.addChild(this.Nose);
    }

    @Override
    public Iterable<ModelRenderer> getParts() {
        return ImmutableList.of(this.Body, this.Head, this.LeftLeg, this.RightLeg, this.ChestPlate, this.Cape, this.LeftOpenArm, this.RightOpenArm, this.MiddleClosedArm);
    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.Head.rotateAngleY = (float) Math.toRadians(netHeadYaw);
        this.Head.rotateAngleX = (float) Math.toRadians(headPitch);
        this.MiddleClosedArm.rotationPointY = 3.0F;
        this.MiddleClosedArm.rotationPointZ = -1.0F;
        this.MiddleClosedArm.rotateAngleX = -0.75F;
        this.RightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F;
        this.LeftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount * 0.5F;
        this.RightLeg.rotateAngleY = 0.0F;
        this.LeftLeg.rotateAngleY = 0.0F;

        if (this.isSitting) {
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

        DefenderEntity guard = entityIn;
        AbstractIllagerEntity.ArmPose armPose = guard.getArmPose();
        if (armPose == AbstractIllagerEntity.ArmPose.ATTACKING) {
            float f = MathHelper.sin(this.swingProgress * (float) Math.PI);
            float f1 = MathHelper.sin((1.0F - (1.0F - this.swingProgress) * (1.0F - this.swingProgress)) * (float) Math.PI);
            this.RightOpenArm.rotateAngleZ = 0.0F;
            this.LeftOpenArm.rotateAngleZ = 0.0F;
            this.RightOpenArm.rotateAngleY = 0.15707964F;
            this.LeftOpenArm.rotateAngleY = -0.15707964F;
            if (guard.getPrimaryHand() == HandSide.RIGHT) {
                if (guard.isDrinkingPotion()) {
                    this.RightOpenArm.rotateAngleX = -1.8849558F + MathHelper.cos(ageInTicks * 0.09F) * 0.15F;
                    this.LeftOpenArm.rotateAngleX = -1.0F;
                    this.LeftOpenArm.rotateAngleZ = -0.6F;
                    this.RightOpenArm.rotateAngleX += f * 2.2F - f1 * 0.4F;
                    this.LeftOpenArm.rotateAngleX += f * 1.2F - f1 * 0.4F;
                } else {
                    this.RightOpenArm.rotateAngleX = -1.8849558F + MathHelper.cos(ageInTicks * 0.09F) * 0.15F;
                    this.LeftOpenArm.rotateAngleX = -0.0F + MathHelper.cos(ageInTicks * 0.19F) * 0.5F;
                    this.RightOpenArm.rotateAngleX += f * 2.2F - f1 * 0.4F;
                    this.LeftOpenArm.rotateAngleX += f * 1.2F - f1 * 0.4F;
                }
            } else {
                if (guard.isDrinkingPotion()) {
                    this.RightOpenArm.rotateAngleZ = 0.6F;
                    this.RightOpenArm.rotateAngleX = 1.0F;
                    this.LeftOpenArm.rotateAngleX = -1.8849558F + MathHelper.cos(ageInTicks * 0.09F) * 0.15F;
                    this.RightOpenArm.rotateAngleX += f * 1.2F - f1 * 0.4F;
                    this.LeftOpenArm.rotateAngleX += f * 2.2F - f1 * 0.4F;
                }
                this.RightOpenArm.rotateAngleX = -0.0F + MathHelper.cos(ageInTicks * 0.19F) * 0.5F;
                this.LeftOpenArm.rotateAngleX = -1.8849558F + MathHelper.cos(ageInTicks * 0.09F) * 0.15F;
                this.RightOpenArm.rotateAngleX += f * 1.2F - f1 * 0.4F;
                this.LeftOpenArm.rotateAngleX += f * 2.2F - f1 * 0.4F;
            }

            this.RightOpenArm.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
            this.LeftOpenArm.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
            this.RightOpenArm.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
            this.LeftOpenArm.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
        } else if (armPose == AbstractIllagerEntity.ArmPose.BOW_AND_ARROW) {
            this.RightOpenArm.rotateAngleY = -0.1F + this.Head.rotateAngleY;
            this.RightOpenArm.rotateAngleX = (-(float) Math.PI / 2F) + this.Head.rotateAngleX;
            this.LeftOpenArm.rotateAngleX = -0.9424779F + this.Head.rotateAngleX;
            this.LeftOpenArm.rotateAngleY = this.Head.rotateAngleY - 0.4F;
            this.LeftOpenArm.rotateAngleZ = ((float) Math.PI / 2F);
        }

        if (guard.getArmPose() == AbstractIllagerEntity.ArmPose.CROSSED) {
            this.MiddleClosedArm.showModel = true;
            this.RightOpenArm.showModel = false;
            this.LeftOpenArm.showModel = false;
        } else {
            this.MiddleClosedArm.showModel = false;
            this.RightOpenArm.showModel = true;
            this.LeftOpenArm.showModel = true;
        }

        float partialTicks = Minecraft.getInstance().getRenderPartialTicks();
        double capeX = guard.prevCapeX + (guard.capeX - guard.prevCapeX) * partialTicks;
        double capeY = guard.prevCapeY + (guard.capeY - guard.prevCapeY) * partialTicks;
        double capeZ = guard.prevCapeZ + (guard.capeZ - guard.prevCapeZ) * partialTicks;
        double guardX = guard.prevPosX + (guard.getPosX() - guard.prevPosX) * partialTicks;
        double guardY = guard.prevPosY + (guard.getPosY() - guard.prevPosY) * partialTicks;
        double guardZ = guard.prevPosZ + (guard.getPosZ() - guard.prevPosZ) * partialTicks;

        double deltaX = capeX - guardX;
        double deltaY = guardY - capeY;
        double deltaZ = capeZ - guardZ;

        deltaY = MathHelper.sqrt(deltaY * deltaY);

        double deltaXZ = MathHelper.sqrt(deltaX * deltaX + deltaZ * deltaZ);
        this.Cape.rotateAngleX = (float) (Math.PI / 2 - MathHelper.atan2(deltaY, deltaXZ));
    }

    @Override
    public ModelRenderer getModelHead() {
        return this.Head;
    }

    public ModelRenderer getArm(HandSide handSide) {
        return handSide == HandSide.LEFT ? this.LeftOpenArm : this.RightOpenArm;
    }

    @Override
    public void translateHand(HandSide handSide, MatrixStack matrixStack) {
        this.getArm(handSide).translateRotate(matrixStack);
    }


    public ModelRenderer crossHand() {
        return this.MiddleClosedArm;
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
