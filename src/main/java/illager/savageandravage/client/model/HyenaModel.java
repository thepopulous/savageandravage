package illager.savageandravage.client.model;

import com.mojang.blaze3d.platform.GlStateManager;
import illager.savageandravage.entity.HyenaEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.IHasHead;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HyenaModel<T extends HyenaEntity> extends EntityModel<T> implements IHasHead {
    public RendererModel Body;
    public RendererModel RightFrontLeg;
    public RendererModel LeftFrontLeg;
    public RendererModel RightBackLeg;
    public RendererModel LeftBackLeg;
    public RendererModel Head;
    public RendererModel Tail;
    public RendererModel Fluff;
    public RendererModel Snout;
    public RendererModel RightEar;
    public RendererModel LeftEar;

    public HyenaModel() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.LeftFrontLeg = new RendererModel(this, 0, 0);
        this.LeftFrontLeg.setRotationPoint(2.0F, 0.0F, 2.0F);
        this.LeftFrontLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 10, 3, 0.0F);
        this.RightBackLeg = new RendererModel(this, 0, 0);
        this.RightBackLeg.setRotationPoint(-2.0F, 0.0F, 11.0F);
        this.RightBackLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 10, 3, 0.0F);
        this.LeftEar = new RendererModel(this, 44, 0);
        this.LeftEar.mirror = true;
        this.LeftEar.setRotationPoint(3.0F, -2.0F, 1.0F);
        this.LeftEar.addBox(-1.0F, -4.0F, 0.0F, 2, 4, 1, 0.0F);
        this.setRotateAngle(LeftEar, -0.3490658503988659F, -0.17453292519943295F, 0.0F);
        this.Head = new RendererModel(this, 38, 8);
        this.Head.setRotationPoint(0.0F, 10.0F, -5.0F);
        this.Head.addBox(-3.5F, -3.0F, -3.0F, 7, 6, 6, 0.0F);
        this.Fluff = new RendererModel(this, 10, -14);
        this.Fluff.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Fluff.addBox(0.0F, -5.0F, 0.0F, 0, 2, 14, 0.0F);
        this.RightEar = new RendererModel(this, 44, 0);
        this.RightEar.setRotationPoint(-3.0F, -2.0F, 1.0F);
        this.RightEar.addBox(-1.0F, -4.0F, 0.0F, 2, 4, 1, 0.0F);
        this.setRotateAngle(RightEar, -0.3490658503988659F, 0.17453292519943295F, 0.0F);
        this.RightFrontLeg = new RendererModel(this, 0, 0);
        this.RightFrontLeg.setRotationPoint(-2.0F, 0.0F, 2.0F);
        this.RightFrontLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 10, 3, 0.0F);
        this.LeftBackLeg = new RendererModel(this, 0, 0);
        this.LeftBackLeg.setRotationPoint(2.0F, 0.0F, 11.0F);
        this.LeftBackLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 10, 3, 0.0F);
        this.Body = new RendererModel(this, 0, 12);
        this.Body.setRotationPoint(0.0F, 13.0F, -5.0F);
        this.Body.addBox(-3.5F, -3.0F, 0.0F, 7, 6, 14, 0.0F);
        this.Tail = new RendererModel(this, 0, 14);
        this.Tail.setRotationPoint(0.0F, 0.0F, 13.0F);
        this.Tail.addBox(-1.0F, 0.0F, -0.5F, 2, 10, 2, 0.0F);
        this.Snout = new RendererModel(this, 50, 0);
        this.Snout.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Snout.addBox(-2.0F, 0.0F, -6.0F, 4, 3, 3, 0.0F);
        this.Body.addChild(this.Fluff);
        this.Head.addChild(this.RightEar);
        this.Head.addChild(this.Snout);
        this.Head.addChild(this.LeftEar);
        this.Body.addChild(this.Tail);

        this.Body.addChild(this.RightFrontLeg);
        this.Body.addChild(this.RightBackLeg);
        this.Body.addChild(this.LeftFrontLeg);
        this.Body.addChild(this.LeftBackLeg);
    }

    @Override
    public void render(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        super.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        this.setRotationAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        if (this.isChild) {
            float f = 2.0F;
            GlStateManager.pushMatrix();
            GlStateManager.translatef(0.0F, 7.0F * scale, 2.0F * scale);
            this.Head.render(scale);
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.scalef(0.5F, 0.5F, 0.5F);
            GlStateManager.translatef(0.0F, 24.0F * scale, 0.0F);
            this.Body.render(scale);

            GlStateManager.popMatrix();
        } else {
            this.Body.render(scale);
            this.Head.render(scale);
        }
    }

    @Override
    public void setLivingAnimations(T entityIn, float limbSwing, float limbSwingAmount, float partialTickTime) {
        if (entityIn.isAngry()) {
            this.Tail.rotateAngleY = 0.0F;
        } else {
            this.Tail.rotateAngleY = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        }


        this.RightEar.rotateAngleZ = entityIn.getInterestedAngle(partialTickTime);
        this.LeftEar.rotateAngleZ = entityIn.getInterestedAngle(partialTickTime);

        this.Head.rotateAngleZ = entityIn.getInterestedAngle(partialTickTime) + entityIn.getShakeAngle(partialTickTime, 0.0F);
        this.Body.rotateAngleZ = entityIn.getShakeAngle(partialTickTime, -0.16F);

        this.Tail.rotateAngleZ = entityIn.getShakeAngle(partialTickTime, -0.2F);
    }

    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
        super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
        float f = ageInTicks - (float) entityIn.ticksExisted;
        float f1 = entityIn.getSittingAnimationScale(f);
        f1 = f1 * f1;
        float f2 = 1.0F - f1;


        if (entityIn.isTamed() && entityIn.isBegging()) {
            this.Tail.rotateAngleY += MathHelper.sin(ageInTicks * 0.88F) * 0.2F;
        }

        float f3 = netHeadYaw * ((float) Math.PI / 180F);

        this.RightEar.rotateAngleY = f3 - 0.2617994F;
        this.LeftEar.rotateAngleY = f3 + 0.2617994F;

        this.LeftBackLeg.setRotationPoint(2.0F, f1 * 1.0F, 11.0F + 2.0F * f1);
        this.RightBackLeg.setRotationPoint(-2.0F, f1 * 1.0F, 11.0F + 2.0F * f1);
        this.LeftFrontLeg.setRotationPoint(2.0F, 0.0F, 2.0F + 2.0F * f1);
        this.RightFrontLeg.setRotationPoint(-2.0F, 0.0F, 2.0F + 2.0F * f1);


        this.Body.rotateAngleX = 0.0F - (f1 * (float) Math.PI * 0.45F);
        if (!entityIn.isSwimming()) {
            this.RightFrontLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
            this.RightBackLeg.rotateAngleX = -(f1 * (float) Math.PI * 0.45F) + (MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount);
            this.LeftFrontLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
            this.LeftBackLeg.rotateAngleX = -(f1 * (float) Math.PI * 0.45F) + (MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount);
        } else {
            this.RightFrontLeg.rotateAngleX = -0.4F + -0.54F * MathHelper.sin(0.3F * ageInTicks);
            this.LeftFrontLeg.rotateAngleX = -0.4F + 0.54F * MathHelper.sin(0.3F * ageInTicks);
            this.RightBackLeg.rotateAngleX = 0.4F + -0.54F * MathHelper.sin(0.3F * ageInTicks);
            this.LeftBackLeg.rotateAngleX = 0.4F + 0.54F * MathHelper.sin(0.3F * ageInTicks);
        }
        this.LeftBackLeg.rotateAngleZ = -(f1 * (float) Math.PI * 0.45F) / 2;
        this.RightBackLeg.rotateAngleZ = (f1 * (float) Math.PI * 0.45F) / 2;

        this.Head.rotateAngleX = headPitch * 0.017453292F;
        this.Head.rotateAngleY = netHeadYaw * 0.017453292F;
        this.Tail.rotateAngleX = entityIn.getTailRotation() + (f1 * (float) Math.PI * 0.45F);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(RendererModel modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    @Override
    public RendererModel func_205072_a() {
        return this.Head;
    }
}
