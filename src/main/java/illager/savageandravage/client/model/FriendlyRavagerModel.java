package illager.savageandravage.client.model;

import illager.savageandravage.entity.FriendlyRavagerEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FriendlyRavagerModel extends EntityModel<FriendlyRavagerEntity> {
    public RendererModel Head;
    public RendererModel RightFrontLeg;
    public RendererModel LeftFrontLeg;
    public RendererModel RightBackLeg;
    public RendererModel LeftBackLeg;
    public RendererModel TorsoFront;
    public RendererModel Neck;
    public RendererModel Mouth;
    public RendererModel Nose;
    public RendererModel RightHorn;
    public RendererModel LeftHorn;
    public RendererModel Hat;
    public RendererModel TorsoBack;
    public RendererModel RightCloth;
    public RendererModel LeftCloth;

    public FriendlyRavagerModel() {
        this.textureWidth = 128;
        this.textureHeight = 128;
        this.LeftCloth = new RendererModel(this, 0, 85);
        this.LeftCloth.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.LeftCloth.addBox(6.0F, 6.0F, -16.0F, 0, 18, 6, 0.0F);
        this.Nose = new RendererModel(this, 0, 0);
        this.Nose.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Nose.addBox(-2.0F, 4.0F, -20.0F, 4, 8, 4, 0.0F);
        this.RightBackLeg = new RendererModel(this, 96, 0);
        this.RightBackLeg.setRotationPoint(-8.0F, -13.0F, 27.0F);
        this.RightBackLeg.addBox(-4.0F, 0.0F, -4.0F, 8, 37, 8, 0.0F);
        this.RightCloth = new RendererModel(this, 0, 50);
        this.RightCloth.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.RightCloth.addBox(-6.0F, 6.0F, -16.0F, 0, 18, 6, 0.0F);
        this.RightFrontLeg = new RendererModel(this, 64, 0);
        this.RightFrontLeg.setRotationPoint(-8.0F, -13.0F, 4.0F);
        this.RightFrontLeg.addBox(-4.0F, 0.0F, -4.0F, 8, 37, 8, 0.0F);
        this.Hat = new RendererModel(this, 104, 45);
        this.Hat.setRotationPoint(-5.0F, -8.5F, -5.0F);
        this.Hat.addBox(-4.0F, -4.0F, -4.0F, 6, 4, 6, 0.0F);
        this.setRotateAngle(Hat, 0.0F, 0.0F, -0.7853981633974483F);
        this.LeftFrontLeg = new RendererModel(this, 64, 0);
        this.LeftFrontLeg.mirror = true;
        this.LeftFrontLeg.setRotationPoint(8.0F, -13.0F, 4.0F);
        this.LeftFrontLeg.addBox(-4.0F, 0.0F, -4.0F, 8, 37, 8, 0.0F);
        this.RightHorn = new RendererModel(this, 74, 55);
        this.RightHorn.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.RightHorn.addBox(-10.0F, -25.0F, -2.75F, 2, 14, 4, 0.0F);
        this.setRotateAngle(RightHorn, 1.117010721276371F, 0.0F, 0.0F);
        this.LeftHorn = new RendererModel(this, 74, 55);
        this.LeftHorn.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.LeftHorn.addBox(8.0F, -25.0F, -2.75F, 2, 14, 4, 0.0F);
        this.setRotateAngle(LeftHorn, 1.117010721276371F, 0.0F, 0.0F);
        this.Neck = new RendererModel(this, 68, 73);
        this.Neck.setRotationPoint(0.0F, 0.0F, 16.0F);
        this.Neck.addBox(-5.0F, -5.0F, -18.0F, 10, 10, 18, 0.0F);
        this.Head = new RendererModel(this, 0, 0);
        this.Head.setRotationPoint(0.0F, -1.0F, 0.0F);
        this.Head.addBox(-8.0F, -10.0F, -16.0F, 16, 20, 16, 0.0F);
        this.TorsoFront = new RendererModel(this, 0, 55);
        this.TorsoFront.setRotationPoint(0.0F, -2.0F, 1.0F);
        this.TorsoFront.addBox(-7.0F, 0.0F, -10.0F, 14, 16, 20, 0.0F);
        this.setRotateAngle(TorsoFront, 1.5707963267948966F, 0.0F, 0.0F);
        this.LeftBackLeg = new RendererModel(this, 96, 0);
        this.LeftBackLeg.mirror = true;
        this.LeftBackLeg.setRotationPoint(8.0F, -13.0F, 27.0F);
        this.LeftBackLeg.addBox(-4.0F, 0.0F, -4.0F, 8, 37, 8, 0.0F);
        this.Mouth = new RendererModel(this, 0, 36);
        this.Mouth.setRotationPoint(0.0F, 8.0F, 0.0F);
        this.Mouth.addBox(-8.0F, 0.0F, -16.0F, 16, 3, 16, 0.0F);
        this.setRotateAngle(Mouth, 0.030543261909900768F, 0.0F, 0.0F);
        this.TorsoBack = new RendererModel(this, 0, 91);
        this.TorsoBack.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.TorsoBack.addBox(-6.0F, 16.0F, -10.0F, 12, 13, 18, 0.0F);
        this.TorsoFront.addChild(this.LeftCloth);
        this.Head.addChild(this.Nose);
        this.TorsoFront.addChild(this.RightCloth);
        this.Head.addChild(this.Hat);
        this.Head.addChild(this.RightHorn);
        this.Head.addChild(this.LeftHorn);
        this.Head.addChild(this.Mouth);
        this.Head.addChild(this.Neck);
        this.TorsoFront.addChild(this.TorsoBack);
    }

    public void render(FriendlyRavagerEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        this.Head.render(scale);
        this.TorsoFront.render(scale);
        this.RightFrontLeg.render(scale);
        this.RightBackLeg.render(scale);
        this.LeftFrontLeg.render(scale);
        this.LeftBackLeg.render(scale);
    }

    public void setRotationAngles(FriendlyRavagerEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
        this.Head.rotateAngleX = headPitch * ((float) Math.PI / 180F);
        this.Head.rotateAngleY = netHeadYaw * ((float) Math.PI / 180F);
        /*
        this.field_217170_f.rotateAngleX = ((float) Math.PI / 2F);
        */
        float f = 0.4F * limbSwingAmount;
        this.RightFrontLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * f;
        this.LeftFrontLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * f;
        this.RightBackLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * f;
        this.LeftBackLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * f;
    }

    public void setLivingAnimations(FriendlyRavagerEntity entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
        super.setLivingAnimations(entityIn, limbSwing, limbSwingAmount, partialTick);
        int i = entityIn.func_213684_dX();
        int j = entityIn.func_213687_eg();
        int k = 20;
        int l = entityIn.func_213683_l();
        int i1 = 10;
        if (l > 0) {
            float f = this.func_217167_a((float) l - partialTick, 10.0F);
            float f1 = (1.0F + f) * 0.5F;
            float f2 = f1 * f1 * f1 * 12.0F;
            float f3 = f2 * MathHelper.sin(this.Neck.rotateAngleX);
            this.Neck.rotationPointZ = -6.5F + f2;
            this.Neck.rotationPointY = -7.0F - f3;
            float f4 = MathHelper.sin(((float) l - partialTick) / 10.0F * (float) Math.PI * 0.25F);
            this.Mouth.rotateAngleX = ((float) Math.PI / 2F) * f4;
            if (l > 5) {
                this.Mouth.rotateAngleX = MathHelper.sin(((float) (-4 + l) - partialTick) / 4.0F) * (float) Math.PI * 0.4F;
            } else {
                this.Mouth.rotateAngleX = 0.15707964F * MathHelper.sin((float) Math.PI * ((float) l - partialTick) / 10.0F);
            }
        } else {
            float f5 = -1.0F;
            float f6 = -1.0F * MathHelper.sin(this.Neck.rotateAngleX);
            this.Neck.rotationPointX = 0.0F;
            this.Neck.rotationPointY = -7.0F - f6;
            this.Neck.rotationPointZ = 5.5F;
            boolean flag = i > 0;
            this.Neck.rotateAngleX = flag ? 0.21991149F : 0.0F;
            this.Mouth.rotateAngleX = (float) Math.PI * (flag ? 0.05F : 0.01F);
            if (flag) {
                double d0 = (double) i / 40.0D;
                this.Neck.rotationPointX = (float) Math.sin(d0 * 10.0D) * 3.0F;
            } else if (j > 0) {
                float f7 = MathHelper.sin(((float) (20 - j) - partialTick) / 20.0F * (float) Math.PI * 0.25F);
                this.Mouth.rotateAngleX = ((float) Math.PI / 2F) * f7;
            }
        }

    }

    private float func_217167_a(float p_217167_1_, float p_217167_2_) {
        return (Math.abs(p_217167_1_ % p_217167_2_ - p_217167_2_ * 0.5F) - p_217167_2_ * 0.25F) / (p_217167_2_ * 0.25F);
    }

    public void setRotateAngle(RendererModel modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}