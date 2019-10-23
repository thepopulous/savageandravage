package populousteam.savageandravage.client.model;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.IHasArm;
import net.minecraft.client.renderer.entity.model.IHasHead;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import populousteam.savageandravage.api.entity.EntityArmPose;
import populousteam.savageandravage.entity.SkeletonVillagerEntity;

@OnlyIn(Dist.CLIENT)
public class SkeletonVillagerModel<T extends SkeletonVillagerEntity> extends EntityModel<T> implements IHasArm, IHasHead {
    public RendererModel Head;
    public RendererModel Body;
    public RendererModel RightArm;
    public RendererModel LeftArm;
    public RendererModel RightLeg;
    public RendererModel LeftLeg;
    public RendererModel MiddleClosedArm;
    public RendererModel Nose;
    public RendererModel RightClosedArm;
    public RendererModel LeftClosedArm;
    private float field_217145_m;

    public SkeletonVillagerModel() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.RightLeg = new RendererModel(this, 0, 18);
        this.RightLeg.setRotationPoint(-2.0F, 12.0F, 0.0F);
        this.RightLeg.addBox(-1.5F, 0.0F, -1.5F, 3, 12, 3, 0.0F);
        this.RightClosedArm = new RendererModel(this, 32, 0);
        this.RightClosedArm.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.RightClosedArm.addBox(-7.0F, -2.0F, -1.0F, 3, 8, 3, 0.0F);
        this.LeftClosedArm = new RendererModel(this, 32, 0);
        this.LeftClosedArm.mirror = true;
        this.LeftClosedArm.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.LeftClosedArm.addBox(4.0F, -2.0F, -1.0F, 3, 8, 3, 0.0F);
        this.LeftLeg = new RendererModel(this, 0, 18);
        this.LeftLeg.mirror = true;
        this.LeftLeg.setRotationPoint(2.0F, 12.0F, 0.0F);
        this.LeftLeg.addBox(-1.5F, 0.0F, -1.5F, 3, 12, 3, 0.0F);
        this.Head = new RendererModel(this, 0, 0);
        this.Head.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Head.addBox(-4.0F, -10.0F, -4.0F, 8, 10, 8, 0.0F);
        this.LeftArm = new RendererModel(this, 40, 19);
        this.LeftArm.mirror = true;
        this.LeftArm.setRotationPoint(5.5F, 2.0F, 0.0F);
        this.LeftArm.addBox(-1.5F, -2.0F, -1.5F, 3, 12, 3, 0.0F);
        this.RightArm = new RendererModel(this, 40, 19);
        this.RightArm.setRotationPoint(-5.5F, 2.0F, 0.0F);
        this.RightArm.addBox(-1.5F, -2.0F, -1.5F, 3, 12, 3, 0.0F);
        this.Body = new RendererModel(this, 12, 18);
        this.Body.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Body.addBox(-4.0F, 0.0F, -3.0F, 8, 12, 6, 0.0F);
        this.MiddleClosedArm = new RendererModel(this, 32, 11);
        this.MiddleClosedArm.setRotationPoint(0.0F, 3.0F, -1.0F);
        this.MiddleClosedArm.addBox(-4.0F, 3.0F, -1.0F, 8, 3, 3, 0.0F);
        this.setRotateAngle(MiddleClosedArm, -0.7853981633974483F, 0.0F, 0.0F);
        this.Nose = new RendererModel(this, 24, 0);
        this.Nose.setRotationPoint(0.0F, -3.0F, -4.0F);
        this.Nose.addBox(-1.0F, 0.0F, -2.0F, 2, 4, 2, 0.0F);
        this.MiddleClosedArm.addChild(this.RightClosedArm);
        this.MiddleClosedArm.addChild(this.LeftClosedArm);
        this.Head.addChild(this.Nose);
    }

    @Override
    public void render(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float HeadPitch, float scale) {
        this.setRotationAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, HeadPitch, scale);
        this.RightLeg.render(scale);
        this.LeftLeg.render(scale);
        this.Head.render(scale);

        this.Body.render(scale);
        if (entity.getEntityArmPose() == EntityArmPose.CROSSED) {
            this.MiddleClosedArm.render(scale);
        } else {
            this.LeftArm.render(scale);
            this.RightArm.render(scale);
        }
    }

    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float HeadPitch, float scaleFactor) {
        this.Head.rotateAngleY = netHeadYaw * ((float) Math.PI / 180F);
        this.Head.rotateAngleX = HeadPitch * ((float) Math.PI / 180F);
        this.MiddleClosedArm.rotationPointY = 3.0F;
        this.MiddleClosedArm.rotationPointZ = -1.0F;
        this.MiddleClosedArm.rotateAngleX = -0.75F;
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
            this.RightArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount * 0.5F;
            this.RightArm.rotateAngleY = 0.0F;
            this.RightArm.rotateAngleZ = 0.0F;
            this.LeftArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F;
            this.LeftArm.rotateAngleY = 0.0F;
            this.LeftArm.rotateAngleZ = 0.0F;
            this.RightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F;
            this.RightLeg.rotateAngleY = 0.0F;
            this.RightLeg.rotateAngleZ = 0.0F;
            this.LeftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount * 0.5F;
            this.LeftLeg.rotateAngleY = 0.0F;
            this.LeftLeg.rotateAngleZ = 0.0F;
        }

        EntityArmPose abstractillagerentity$armpose = entityIn.getEntityArmPose();
        if (abstractillagerentity$armpose == EntityArmPose.ATTACKING) {
            float f = MathHelper.sin(this.swingProgress * (float) Math.PI);
            float f1 = MathHelper.sin((1.0F - (1.0F - this.swingProgress) * (1.0F - this.swingProgress)) * (float) Math.PI);
            this.RightArm.rotateAngleZ = 0.0F;
            this.LeftArm.rotateAngleZ = 0.0F;
            this.RightArm.rotateAngleY = -(0.1F - f * 0.6F);
            this.LeftArm.rotateAngleY = 0.1F - f * 0.6F;
            this.RightArm.rotateAngleX = (-(float) Math.PI / 2F);
            this.LeftArm.rotateAngleX = (-(float) Math.PI / 2F);
            this.RightArm.rotateAngleX -= f * 1.2F - f1 * 0.4F;
            this.LeftArm.rotateAngleX -= f * 1.2F - f1 * 0.4F;
            this.RightArm.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
            this.LeftArm.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
            this.RightArm.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
            this.LeftArm.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
        } else if (abstractillagerentity$armpose == EntityArmPose.SPELLCASTING) {
            this.RightArm.rotationPointZ = 0.0F;
            this.RightArm.rotationPointX = -5.0F;
            this.LeftArm.rotationPointZ = 0.0F;
            this.LeftArm.rotationPointX = 5.0F;
            this.RightArm.rotateAngleX = MathHelper.cos(ageInTicks * 0.6662F) * 0.25F;
            this.LeftArm.rotateAngleX = MathHelper.cos(ageInTicks * 0.6662F) * 0.25F;
            this.RightArm.rotateAngleZ = 2.3561945F;
            this.LeftArm.rotateAngleZ = -2.3561945F;
            this.RightArm.rotateAngleY = 0.0F;
            this.LeftArm.rotateAngleY = 0.0F;
        } else if (abstractillagerentity$armpose == EntityArmPose.BOW_AND_ARROW) {
            this.RightArm.rotateAngleY = -0.1F + this.Head.rotateAngleY;
            this.RightArm.rotateAngleX = (-(float) Math.PI / 2F) + this.Head.rotateAngleX;
            this.LeftArm.rotateAngleX = -0.9424779F + this.Head.rotateAngleX;
            this.LeftArm.rotateAngleY = this.Head.rotateAngleY - 0.4F;
            this.LeftArm.rotateAngleZ = ((float) Math.PI / 2F);
        } else if (abstractillagerentity$armpose == EntityArmPose.CROSSBOW_HOLD) {
            this.RightArm.rotateAngleY = -0.3F + this.Head.rotateAngleY;
            this.LeftArm.rotateAngleY = 0.6F + this.Head.rotateAngleY;
            this.RightArm.rotateAngleX = (-(float) Math.PI / 2F) + this.Head.rotateAngleX + 0.1F;
            this.LeftArm.rotateAngleX = -1.5F + this.Head.rotateAngleX;
        } else if (abstractillagerentity$armpose == EntityArmPose.CROSSBOW_CHARGE) {
            this.RightArm.rotateAngleY = -0.8F;
            this.RightArm.rotateAngleX = -0.97079635F;
            this.LeftArm.rotateAngleX = -0.97079635F;
            float f2 = MathHelper.clamp(this.field_217145_m, 0.0F, 25.0F);
            this.LeftArm.rotateAngleY = MathHelper.lerp(f2 / 25.0F, 0.4F, 0.85F);
            this.LeftArm.rotateAngleX = MathHelper.lerp(f2 / 25.0F, this.LeftArm.rotateAngleX, (-(float) Math.PI / 2F));
        } else if (abstractillagerentity$armpose == EntityArmPose.CELEBRATING) {
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

    }

    public void setLivingAnimations(T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
        this.field_217145_m = (float) entityIn.getItemInUseMaxCount();
        super.setLivingAnimations(entityIn, limbSwing, limbSwingAmount, partialTick);
    }

    @Override
    public RendererModel func_205072_a() {
        return this.Head;
    }

    public RendererModel getArm(HandSide handSide) {
        return handSide == HandSide.LEFT ? this.LeftArm : this.RightArm;
    }

    @Override
    public void postRenderArm(float scale, HandSide side) {
        this.getArm(side).postRender(0.0625F);
    }

    public RendererModel crossHand() {
        return this.MiddleClosedArm;
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(RendererModel modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
