package com.populousteam.savageandravage.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.populousteam.savageandravage.entity.HyenaEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.IHasHead;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HyenaModel<T extends HyenaEntity> extends EntityModel<T> implements IHasHead {
    public ModelRenderer Body;
    public ModelRenderer RightFrontLeg;
    public ModelRenderer LeftFrontLeg;
    public ModelRenderer RightBackLeg;
    public ModelRenderer LeftBackLeg;
    public ModelRenderer Head;
    public ModelRenderer Tail;
    public ModelRenderer Fluff;
    public ModelRenderer Snout;
    public ModelRenderer RightEar;
    public ModelRenderer LeftEar;

    public HyenaModel() {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.LeftFrontLeg = new ModelRenderer(this, 0, 0);
        this.LeftFrontLeg.setRotationPoint(2.0F, 0.0F, 2.0F);
        this.LeftFrontLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 10, 3, 0.0F);
        this.RightBackLeg = new ModelRenderer(this, 0, 0);
        this.RightBackLeg.setRotationPoint(-2.0F, 0.0F, 11.0F);
        this.RightBackLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 10, 3, 0.0F);
        this.LeftEar = new ModelRenderer(this, 44, 0);
        this.LeftEar.mirror = true;
        this.LeftEar.setRotationPoint(3.0F, -2.0F, 1.0F);
        this.LeftEar.addBox(-1.0F, -4.0F, 0.0F, 2, 4, 1, 0.0F);
        this.setRotateAngle(LeftEar, -0.3490658503988659F, -0.17453292519943295F, 0.0F);
        this.Head = new ModelRenderer(this, 38, 8);
        this.Head.setRotationPoint(0.0F, 10.0F, -5.0F);
        this.Head.addBox(-3.5F, -3.0F, -3.0F, 7, 6, 6, 0.0F);
        this.Fluff = new ModelRenderer(this, 10, -14);
        this.Fluff.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Fluff.addBox(0.0F, -5.0F, 0.0F, 0, 2, 14, 0.0F);
        this.RightEar = new ModelRenderer(this, 44, 0);
        this.RightEar.setRotationPoint(-3.0F, -2.0F, 1.0F);
        this.RightEar.addBox(-1.0F, -4.0F, 0.0F, 2, 4, 1, 0.0F);
        this.setRotateAngle(RightEar, -0.3490658503988659F, 0.17453292519943295F, 0.0F);
        this.RightFrontLeg = new ModelRenderer(this, 0, 0);
        this.RightFrontLeg.setRotationPoint(-2.0F, 0.0F, 2.0F);
        this.RightFrontLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 10, 3, 0.0F);
        this.LeftBackLeg = new ModelRenderer(this, 0, 0);
        this.LeftBackLeg.setRotationPoint(2.0F, 0.0F, 11.0F);
        this.LeftBackLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 10, 3, 0.0F);
        this.Body = new ModelRenderer(this, 0, 12);
        this.Body.setRotationPoint(0.0F, 13.0F, -5.0F);
        this.Body.addBox(-3.5F, -3.0F, 0.0F, 7, 6, 14, 0.0F);
        this.Tail = new ModelRenderer(this, 0, 14);
        this.Tail.setRotationPoint(0.0F, 0.0F, 13.0F);
        this.Tail.addBox(-1.0F, 0.0F, -0.5F, 2, 10, 2, 0.0F);
        this.Snout = new ModelRenderer(this, 50, 0);
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
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        if (this.isChild) {
            float f = 2.0F;
            matrixStackIn.push();
            matrixStackIn.translate(0.0F, 7.0F / 16.0F, 2.0F / 16.0F);
            this.Head.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            matrixStackIn.pop();
            matrixStackIn.push();
            matrixStackIn.scale(0.5F, 0.5F, 0.5F);
            matrixStackIn.translate(0.0F, 24.0F / 16.0F, 0.0F);
            this.Body.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);

            matrixStackIn.pop();
        } else {
            this.Body.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.Head.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
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

    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
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
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    @Override
    public ModelRenderer getModelHead() {
        return this.Head;
    }
}
