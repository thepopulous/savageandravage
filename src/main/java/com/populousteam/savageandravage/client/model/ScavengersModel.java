package com.populousteam.savageandravage.client.model;

import com.google.common.collect.ImmutableList;
import com.populousteam.savageandravage.entity.illager.ScavengersEntity;
import net.minecraft.client.renderer.entity.model.IHasHead;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

public class ScavengersModel<T extends ScavengersEntity> extends SegmentedModel<T> implements IHasHead, IHasCrossArm {
    public ModelRenderer head;
    public ModelRenderer body;
    public ModelRenderer arms_closed;
    public ModelRenderer leg_left;
    public ModelRenderer leg_right;

    public ScavengersModel() {
        textureWidth = 64;
        textureHeight = 64;

        head = new ModelRenderer(this);
        head.setRotationPoint(-1.0F, 0.0F, 0.0F);
        head.setTextureOffset(0, 0).addBox(-3.0F, -10.0F, -4.0F, 8, 10, 8, 0.0F, false);
        head.setTextureOffset(32, 0).addBox(-3.0F, -10.0F, -4.0F, 8, 11, 8, 0.3F, false);
        head.setTextureOffset(24, 0).addBox(0.0F, -3.0F, -6.0F, 2, 4, 2, 0.0F, false);

        body = new ModelRenderer(this);
        body.setRotationPoint(-1.0F, 2.0F, 0.0F);
        body.setTextureOffset(16, 20).addBox(-3.0F, -2.0F, -3.0F, 8, 12, 6, 0.0F, false);
        body.setTextureOffset(0, 38).addBox(-3.0F, -2.0F, -3.0F, 8, 19, 6, 0.35F, false);

        arms_closed = new ModelRenderer(this);
        arms_closed.setRotationPoint(-1.0F, 1.0F, 0.0F);
        setRotationAngle(arms_closed, -0.6981F, 0.0F, 0.0F);
        arms_closed.setTextureOffset(44, 22).addBox(-7.0F, 0.0F, -2.0F, 4, 8, 4, 0.0F, false);
        arms_closed.setTextureOffset(44, 22).addBox(5.0F, 0.0F, -2.0F, 4, 8, 4, 0.0F, true);
        arms_closed.setTextureOffset(40, 38).addBox(-3.0F, 4.0F, -2.0F, 8, 4, 4, 0.0F, false);

        leg_left = new ModelRenderer(this);
        leg_left.setRotationPoint(2.0F, 12.0F, 0.0F);
        leg_left.setTextureOffset(0, 22).addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F, false);

        leg_right = new ModelRenderer(this);
        leg_right.setRotationPoint(-2.0F, 12.0F, 0.0F);
        leg_right.setTextureOffset(0, 22).addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F, false);
    }

    @Override
    public Iterable<ModelRenderer> getParts() {
        return ImmutableList.of(this.head, this.body, this.arms_closed, this.leg_left, this.leg_right);
    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.rotateAngleY = (float) Math.toRadians(netHeadYaw);
        this.head.rotateAngleX = (float) Math.toRadians(headPitch);
        this.leg_right.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F;
        this.leg_left.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount * 0.5F;
        this.leg_right.rotateAngleY = 0.0F;
        this.leg_left.rotateAngleY = 0.0F;

        if (this.isSitting) {
            this.leg_right.rotateAngleX = -1.4137167F;
            this.leg_right.rotateAngleY = ((float) Math.PI / 10F);
            this.leg_right.rotateAngleZ = 0.07853982F;
            this.leg_left.rotateAngleX = -1.4137167F;
            this.leg_left.rotateAngleY = (-(float) Math.PI / 10F);
            this.leg_left.rotateAngleZ = -0.07853982F;
        } else {
            this.leg_right.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F;
            this.leg_right.rotateAngleY = 0.0F;
            this.leg_right.rotateAngleZ = 0.0F;
            this.leg_left.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount * 0.5F;
            this.leg_left.rotateAngleY = 0.0F;
            this.leg_left.rotateAngleZ = 0.0F;

        }
    }

    public ModelRenderer getModelHead() {
        return this.head;
    }

    @Override
    public ModelRenderer crossHand() {
        return this.arms_closed;
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
