package com.populousteam.savageandravage.client.model;

import com.populousteam.savageandravage.entity.illager.ScavengersEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.IHasHead;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.ModelBox;
import net.minecraft.util.math.MathHelper;

public class ScavengersModel<T extends ScavengersEntity> extends EntityModel<T> implements IHasHead, IHasCrossArm {
    private final RendererModel head;
    private final RendererModel body;
    private final RendererModel arms_closed;
    private final RendererModel leg_left;
    private final RendererModel leg_right;

    public ScavengersModel() {
        textureWidth = 64;
        textureHeight = 64;

        head = new RendererModel(this);
        head.setRotationPoint(-1.0F, 0.0F, 0.0F);
        head.cubeList.add(new ModelBox(head, 0, 0, -3.0F, -10.0F, -4.0F, 8, 10, 8, 0.0F, false));
        head.cubeList.add(new ModelBox(head, 32, 0, -3.0F, -10.0F, -4.0F, 8, 11, 8, 0.3F, false));
        head.cubeList.add(new ModelBox(head, 24, 0, 0.0F, -3.0F, -6.0F, 2, 4, 2, 0.0F, false));

        body = new RendererModel(this);
        body.setRotationPoint(-1.0F, 2.0F, 0.0F);
        body.cubeList.add(new ModelBox(body, 16, 20, -3.0F, -2.0F, -3.0F, 8, 12, 6, 0.0F, false));
        body.cubeList.add(new ModelBox(body, 0, 38, -3.0F, -2.0F, -3.0F, 8, 19, 6, 0.35F, false));

        arms_closed = new RendererModel(this);
        arms_closed.setRotationPoint(-1.0F, 1.0F, 0.0F);
        setRotationAngle(arms_closed, -0.6981F, 0.0F, 0.0F);
        arms_closed.cubeList.add(new ModelBox(arms_closed, 44, 22, -7.0F, 0.0F, -2.0F, 4, 8, 4, 0.0F, false));
        arms_closed.cubeList.add(new ModelBox(arms_closed, 44, 22, 5.0F, 0.0F, -2.0F, 4, 8, 4, 0.0F, true));
        arms_closed.cubeList.add(new ModelBox(arms_closed, 40, 38, -3.0F, 4.0F, -2.0F, 8, 4, 4, 0.0F, false));

        leg_left = new RendererModel(this);
        leg_left.setRotationPoint(2.0F, 12.0F, 0.0F);
        leg_left.cubeList.add(new ModelBox(leg_left, 0, 22, -2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F, false));

        leg_right = new RendererModel(this);
        leg_right.setRotationPoint(-2.0F, 12.0F, 0.0F);
        leg_right.cubeList.add(new ModelBox(leg_right, 0, 22, -2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F, false));
    }

    @Override
    public void render(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.setRotationAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        head.render(scale);
        body.render(scale);
        arms_closed.render(scale);
        leg_left.render(scale);
        leg_right.render(scale);
    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
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

    public RendererModel func_205072_a() {
        return this.head;
    }

    @Override
    public RendererModel crossHand() {
        return this.arms_closed;
    }

    public void setRotationAngle(RendererModel modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
