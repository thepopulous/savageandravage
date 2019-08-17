package illager.savageandravage.client.model;

import illager.savageandravage.entity.illager.GrieferIllagerEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.IHasArm;
import net.minecraft.client.renderer.entity.model.IHasHead;
import net.minecraft.client.renderer.entity.model.RendererModel;
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
public class GrieferIllagerModel<T extends GrieferIllagerEntity> extends EntityModel<T> implements IHasArm, IHasHead {
    public RendererModel Head;
    public RendererModel Body;
    public RendererModel RightArm;
    public RendererModel LeftArm;
    public RendererModel RightLeg;
    public RendererModel LeftLeg;
    public RendererModel BodyLayer;
    public RendererModel Nose;
    public RendererModel Bag;
    public RendererModel TNT;
    public RendererModel RightShoe;
    public RendererModel LeftShoe;

    public GrieferIllagerModel() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.LeftLeg = new RendererModel(this, 0, 18);
        this.LeftLeg.mirror = true;
        this.LeftLeg.setRotationPoint(1.9F, 12.0F, 0.0F);
        this.LeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.setRotateAngle(LeftLeg, 0.0F, -0.08726646259971647F, -0.03490658503988659F);
        this.RightArm = new RendererModel(this, 16, 18);
        this.RightArm.setRotationPoint(-5.0F, 1.0F, 0.0F);
        this.RightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
        this.setRotateAngle(RightArm, 0.08726646259971647F, -0.4363323129985824F, -0.4363323129985824F);
        this.RightLeg = new RendererModel(this, 0, 18);
        this.RightLeg.setRotationPoint(-1.9F, 12.0F, 0.0F);
        this.RightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.setRotateAngle(RightLeg, 0.0F, 0.08726646259971647F, 0.03490658503988659F);
        this.LeftShoe = new RendererModel(this, 0, 34);
        this.LeftShoe.mirror = true;
        this.LeftShoe.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.LeftShoe.addBox(-2.0F, 8.0F, -4.0F, 4, 4, 2, 0.0F);
        this.Head = new RendererModel(this, 0, 0);
        this.Head.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Head.addBox(-4.0F, -10.0F, -4.0F, 8, 10, 8, 0.0F);
        this.Bag = new RendererModel(this, 46, 36);
        this.Bag.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Bag.addBox(-3.0F, 2.0F, 3.0F, 6, 6, 3, 0.0F);
        this.RightShoe = new RendererModel(this, 0, 34);
        this.RightShoe.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.RightShoe.addBox(-2.0F, 8.0F, -4.0F, 4, 4, 2, 0.0F);
        this.Nose = new RendererModel(this, 24, 0);
        this.Nose.setRotationPoint(0.0F, -3.0F, -4.0F);
        this.Nose.addBox(-1.0F, 0.0F, -2.0F, 2, 4, 2, 0.0F);
        this.Body = new RendererModel(this, 36, 0);
        this.Body.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Body.addBox(-4.0F, 0.0F, -3.0F, 8, 12, 6, 0.0F);
        this.BodyLayer = new RendererModel(this, 36, 18);
        this.BodyLayer.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.BodyLayer.addBox(-4.0F, 0.0F, -3.0F, 8, 12, 6, 0.25F);
        this.LeftArm = new RendererModel(this, 16, 18);
        this.LeftArm.mirror = true;
        this.LeftArm.setRotationPoint(5.0F, 1.0F, 0.0F);
        this.LeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
        this.setRotateAngle(LeftArm, 0.08726646259971647F, 0.4363323129985824F, 0.4363323129985824F);
        this.TNT = new RendererModel(this, 50, 45);
        this.TNT.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.TNT.addBox(0.0F, 6.0F, -6.0F, 4, 4, 3, 0.0F);
        this.LeftLeg.addChild(this.LeftShoe);
        this.Body.addChild(this.Bag);
        this.RightLeg.addChild(this.RightShoe);
        this.Head.addChild(this.Nose);
        this.Body.addChild(this.TNT);
    }

    @Override
    public void render(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        this.LeftLeg.render(scale);
        this.RightArm.render(scale);
        this.RightLeg.render(scale);
        this.Head.render(scale);
        this.Body.render(scale);
        this.BodyLayer.render(scale);
        this.LeftArm.render(scale);
    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
        this.Head.rotateAngleY = netHeadYaw * ((float) Math.PI / 180F);
        this.Head.rotateAngleX = headPitch * ((float) Math.PI / 180F);

        GrieferIllagerEntity.ArmPose abstractillagerentity$armpose = entityIn.getArmPose();

        if (Entity.func_213296_b(entityIn.getMotion()) > 0.0D) {
            this.RightArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount * 0.2F;
            this.LeftArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.2F;
            this.RightArm.rotateAngleY = 0.0F;
            this.RightArm.rotateAngleZ = 0.0F;
            this.LeftArm.rotateAngleY = 0.0F;
            this.LeftArm.rotateAngleZ = 0.0F;
        } else {
            this.RightArm.rotateAngleX = 0.08726646259971647F;
            this.RightArm.rotateAngleY = -0.4363323129985824F;
            this.RightArm.rotateAngleZ = -0.4363323129985824F;
            this.LeftArm.rotateAngleX = 0.08726646259971647F;
            this.LeftArm.rotateAngleY = 0.4363323129985824F;
            this.LeftArm.rotateAngleZ = 0.4363323129985824F;
        }

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

        if (abstractillagerentity$armpose == GrieferIllagerEntity.ArmPose.ATTACKING) {
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
        } else if (abstractillagerentity$armpose == GrieferIllagerEntity.ArmPose.CELEBRATING) {
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

    private RendererModel getArm(HandSide p_191216_1_) {
        return p_191216_1_ == HandSide.LEFT ? this.LeftArm : this.RightArm;
    }

    public RendererModel func_205072_a() {
        return this.Head;
    }

    public void postRenderArm(float scale, HandSide side) {
        this.getArm(side).postRender(0.0625F);
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
