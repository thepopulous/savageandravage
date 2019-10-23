package populousteam.savageandravage.client.model;

import net.minecraft.client.renderer.entity.model.IllagerModel;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;
import populousteam.savageandravage.api.entity.EntityArmPose;
import populousteam.savageandravage.entity.illager.RevampVindicatorEntity;

public class VindicatorIllagerModel<T extends RevampVindicatorEntity> extends IllagerModel<T> {
    public VindicatorIllagerModel(float scale, float v1, int width, int height) {
        super(scale, v1, width, height);
    }

    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
        this.head.rotateAngleY = netHeadYaw * ((float) Math.PI / 180F);
        this.head.rotateAngleX = headPitch * ((float) Math.PI / 180F);
        this.arms.rotationPointY = 3.0F;
        this.arms.rotationPointZ = -1.0F;
        this.arms.rotateAngleX = -0.75F;
        if (this.isSitting) {
            this.rightArm.rotateAngleX = (-(float) Math.PI / 5F);
            this.rightArm.rotateAngleY = 0.0F;
            this.rightArm.rotateAngleZ = 0.0F;
            this.leftArm.rotateAngleX = (-(float) Math.PI / 5F);
            this.leftArm.rotateAngleY = 0.0F;
            this.leftArm.rotateAngleZ = 0.0F;
            this.field_217143_g.rotateAngleX = -1.4137167F;
            this.field_217143_g.rotateAngleY = ((float) Math.PI / 10F);
            this.field_217143_g.rotateAngleZ = 0.07853982F;
            this.field_217144_h.rotateAngleX = -1.4137167F;
            this.field_217144_h.rotateAngleY = (-(float) Math.PI / 10F);
            this.field_217144_h.rotateAngleZ = -0.07853982F;
        } else {
            this.rightArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount * 0.5F;
            this.rightArm.rotateAngleY = 0.0F;
            this.rightArm.rotateAngleZ = 0.0F;
            this.leftArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F;
            this.leftArm.rotateAngleY = 0.0F;
            this.leftArm.rotateAngleZ = 0.0F;
            this.field_217143_g.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F;
            this.field_217143_g.rotateAngleY = 0.0F;
            this.field_217143_g.rotateAngleZ = 0.0F;
            this.field_217144_h.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount * 0.5F;
            this.field_217144_h.rotateAngleY = 0.0F;
            this.field_217144_h.rotateAngleZ = 0.0F;
        }

        EntityArmPose abstractillagerentity$armpose = entityIn.getEntityArmPose();
        if (abstractillagerentity$armpose == EntityArmPose.ANGRY_ATTACK) {
            float f = MathHelper.sin(this.swingProgress * (float) Math.PI);
            float f1 = MathHelper.sin((1.0F - (1.0F - this.swingProgress) * (1.0F - this.swingProgress)) * (float) Math.PI);
            this.rightArm.rotateAngleZ = 0.0F;
            this.leftArm.rotateAngleZ = 0.0F;
            this.rightArm.rotateAngleY = 0.15707964F;
            this.leftArm.rotateAngleY = -0.15707964F;
            if (entityIn.getPrimaryHand() == HandSide.RIGHT) {
                this.rightArm.rotateAngleX = -2.0849558F;
                this.leftArm.rotateAngleX = 0.1F + MathHelper.cos(ageInTicks * 0.19F) * 0.15F;
                this.rightArm.rotateAngleX += f * 2.2F - f1 * 0.4F;
                this.leftArm.rotateAngleX += f * 1.2F - f1 * 0.4F;
            } else {
                this.rightArm.rotateAngleX = 0.1F + MathHelper.cos(ageInTicks * 0.19F) * 0.15F;
                this.leftArm.rotateAngleX = -2.0849558F;
                this.rightArm.rotateAngleX += f * 1.2F - f1 * 0.4F;
                this.leftArm.rotateAngleX += f * 2.2F - f1 * 0.4F;
            }

            this.rightArm.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
            this.leftArm.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
            this.rightArm.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
            this.leftArm.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
        } else if (abstractillagerentity$armpose == EntityArmPose.ATTACKING) {
            float f = MathHelper.sin(this.swingProgress * (float) Math.PI);
            float f1 = MathHelper.sin((1.0F - (1.0F - this.swingProgress) * (1.0F - this.swingProgress)) * (float) Math.PI);
            this.rightArm.rotateAngleZ = -0.42F;
            this.leftArm.rotateAngleZ = 0.35F;
            this.rightArm.rotateAngleY = 0.0F;
            this.leftArm.rotateAngleY = 0.0F;
            if (entityIn.getPrimaryHand() == HandSide.RIGHT) {
                this.rightArm.rotateAngleX = -0.85F + MathHelper.cos(ageInTicks * 0.12F) * 0.15F;

                this.leftArm.rotateAngleX = -0.9F + MathHelper.cos(ageInTicks * 0.09F) * 0.1F;

                this.rightArm.rotateAngleX += f * 2.2F - f1 * 0.4F;
            } else {
                this.rightArm.rotateAngleX = -0.75F + MathHelper.cos(ageInTicks * 0.09F) * 0.1F;
                this.leftArm.rotateAngleX = -0.75F + MathHelper.cos(ageInTicks * 0.12F) * 0.15F;
                this.leftArm.rotateAngleX += f * 2.2F - f1 * 0.4F;
            }

            this.rightArm.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
            this.leftArm.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
            this.rightArm.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
            this.leftArm.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
        } else if (abstractillagerentity$armpose == EntityArmPose.SPELLCASTING) {
            this.rightArm.rotationPointZ = 0.0F;
            this.rightArm.rotationPointX = -5.0F;
            this.leftArm.rotationPointZ = 0.0F;
            this.leftArm.rotationPointX = 5.0F;
            this.rightArm.rotateAngleX = MathHelper.cos(ageInTicks * 0.6662F) * 0.25F;
            this.leftArm.rotateAngleX = MathHelper.cos(ageInTicks * 0.6662F) * 0.25F;
            this.rightArm.rotateAngleZ = 2.3561945F;
            this.leftArm.rotateAngleZ = -2.3561945F;
            this.rightArm.rotateAngleY = 0.0F;
            this.leftArm.rotateAngleY = 0.0F;
        } else if (abstractillagerentity$armpose == EntityArmPose.BOW_AND_ARROW) {
            this.rightArm.rotateAngleY = -0.1F + this.head.rotateAngleY;
            this.rightArm.rotateAngleX = (-(float) Math.PI / 2F) + this.head.rotateAngleX;
            this.leftArm.rotateAngleX = -0.9424779F + this.head.rotateAngleX;
            this.leftArm.rotateAngleY = this.head.rotateAngleY - 0.4F;
            this.leftArm.rotateAngleZ = ((float) Math.PI / 2F);
        } else if (abstractillagerentity$armpose == EntityArmPose.CELEBRATING) {
            this.rightArm.rotationPointZ = 0.0F;
            this.rightArm.rotationPointX = -5.0F;
            this.rightArm.rotateAngleX = MathHelper.cos(ageInTicks * 0.6662F) * 0.05F;
            this.rightArm.rotateAngleZ = 2.670354F;
            this.rightArm.rotateAngleY = 0.0F;
            this.leftArm.rotationPointZ = 0.0F;
            this.leftArm.rotationPointX = 5.0F;
            this.leftArm.rotateAngleX = MathHelper.cos(ageInTicks * 0.6662F) * 0.05F;
            this.leftArm.rotateAngleZ = -2.3561945F;
            this.leftArm.rotateAngleY = 0.0F;
        }

    }
}
