package populousteam.savageandravage.client.model;

import populousteam.savageandravage.entity.illager.GrieferIllagerEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.IHasArm;
import net.minecraft.client.renderer.entity.model.IHasHead;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.ModelBox;
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
    private final RendererModel right_leg;
    private final RendererModel left_leg;
    private final RendererModel body;
    private final RendererModel right_arm;
    private final RendererModel left_arm;
    private final RendererModel head;

    public GrieferIllagerModel() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        right_leg = new RendererModel(this);
        right_leg.setRotationPoint(-2.0F, 12.0F, 2.0F);
        right_leg.cubeList.add(new ModelBox(right_leg, 0, 18, -2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F, false));

        left_leg = new RendererModel(this);
        left_leg.setRotationPoint(2.0F, 12.0F, 2.0F);
        left_leg.cubeList.add(new ModelBox(left_leg, 0, 18, -2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F, true));

        body = new RendererModel(this);
        body.setRotationPoint(0.0F, 5.0F, 2.0F);
        body.cubeList.add(new ModelBox(body, 36, 0, -4.0F, -5.0F, -3.0F, 8, 12, 6, 0.0F, false));
        body.cubeList.add(new ModelBox(body, 36, 18, -4.0F, -5.0F, -3.0F, 8, 12, 6, 0.3F, false));
        body.cubeList.add(new ModelBox(body, 50, 45, 0.0F, 1.0F, -6.0F, 4, 4, 3, 0.0F, false));
        body.cubeList.add(new ModelBox(body, 46, 36, -3.0F, -4.0F, 3.0F, 6, 6, 3, 0.0F, false));

        right_arm = new RendererModel(this);
        right_arm.setRotationPoint(-4.0F, 0.0F, 2.0F);
        right_arm.cubeList.add(new ModelBox(right_arm, 16, 34, -4.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F, false));
        right_arm.cubeList.add(new ModelBox(right_arm, 11, 51, -5.0F, -1.0F, -3.0F, 5, 6, 6, 0.0F, false));

        left_arm = new RendererModel(this);
        left_arm.setRotationPoint(4.0F, 0.0F, 2.0F);
        left_arm.cubeList.add(new ModelBox(left_arm, 16, 18, 0.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F, false));

        head = new RendererModel(this);
        head.setRotationPoint(0.0F, 0.0F, 2.0F);
        head.cubeList.add(new ModelBox(head, 0, 0, -4.0F, -10.0F, -4.0F, 8, 10, 8, 0.0F, false));
        head.cubeList.add(new ModelBox(head, 24, 0, -1.0F, -3.0F, -6.0F, 2, 4, 2, 0.0F, false));
    }

    @Override
    public void render(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        this.left_leg.render(scale);
        this.right_arm.render(scale);
        this.right_leg.render(scale);
        this.head.render(scale);
        this.body.render(scale);
        this.left_arm.render(scale);
    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
        this.head.rotateAngleY = netHeadYaw * ((float) Math.PI / 180F);
        this.head.rotateAngleX = headPitch * ((float) Math.PI / 180F);

        GrieferIllagerEntity.ArmPose abstractillagerentity$armpose = entityIn.getArmPose();

        if (Entity.func_213296_b(entityIn.getMotion()) > 0.0D) {
            this.right_arm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount * 0.2F;
            this.left_arm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.2F;
            this.right_arm.rotateAngleY = 0.0F;
            this.right_arm.rotateAngleZ = 0.0F;
            this.left_arm.rotateAngleY = 0.0F;
            this.left_arm.rotateAngleZ = 0.0F;
        } else {
            this.right_arm.rotateAngleX = 0.08726646259971647F;
            this.right_arm.rotateAngleY = -0.4363323129985824F;
            this.right_arm.rotateAngleZ = -0.4363323129985824F;
            this.left_arm.rotateAngleX = 0.08726646259971647F;
            this.left_arm.rotateAngleY = 0.4363323129985824F;
            this.left_arm.rotateAngleZ = 0.4363323129985824F;
        }

        if (this.isSitting) {

            this.right_arm.rotateAngleX = (-(float) Math.PI / 5F);
            this.right_arm.rotateAngleY = 0.0F;
            this.right_arm.rotateAngleZ = 0.0F;
            this.left_arm.rotateAngleX = (-(float) Math.PI / 5F);
            this.left_arm.rotateAngleY = 0.0F;
            this.left_arm.rotateAngleZ = 0.0F;

            this.right_leg.rotateAngleX = -1.4137167F;
            this.right_leg.rotateAngleY = ((float) Math.PI / 10F);
            this.right_leg.rotateAngleZ = 0.07853982F;
            this.left_leg.rotateAngleX = -1.4137167F;
            this.left_leg.rotateAngleY = (-(float) Math.PI / 10F);
            this.left_leg.rotateAngleZ = -0.07853982F;
        } else {
            this.right_leg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F;
            this.right_leg.rotateAngleY = 0.0F;
            this.right_leg.rotateAngleZ = 0.0F;
            this.left_leg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount * 0.5F;
            this.left_leg.rotateAngleY = 0.0F;
            this.left_leg.rotateAngleZ = 0.0F;

        }

        if (abstractillagerentity$armpose == GrieferIllagerEntity.ArmPose.ATTACKING) {
            float f = MathHelper.sin(this.swingProgress * (float) Math.PI);
            float f1 = MathHelper.sin((1.0F - (1.0F - this.swingProgress) * (1.0F - this.swingProgress)) * (float) Math.PI);
            this.right_arm.rotateAngleZ = 0.0F;
            this.left_arm.rotateAngleZ = 0.0F;
            this.right_arm.rotateAngleY = 0.15707964F;
            this.left_arm.rotateAngleY = -0.15707964F;
            if (entityIn.getPrimaryHand() == HandSide.RIGHT) {
                this.right_arm.rotateAngleX = -1.68F - MathHelper.cos(ageInTicks * 0.1F) * 0.3F;
                this.left_arm.rotateAngleX = -0.0F + MathHelper.cos(ageInTicks * 0.1F) * 0.3F;
                this.right_arm.rotateAngleX += f * 2.2F - f1 * 0.4F;
                this.left_arm.rotateAngleX += f * 1.2F - f1 * 0.4F;
            } else {
                this.right_arm.rotateAngleX = -0.0F - MathHelper.cos(ageInTicks * 0.1F) * 0.3F;
                this.left_arm.rotateAngleX = -1.68F + MathHelper.cos(ageInTicks * 0.1F) * 0.3F;
                this.right_arm.rotateAngleX += f * 1.2F - f1 * 0.4F;
                this.left_arm.rotateAngleX += f * 2.2F - f1 * 0.4F;
            }

            this.right_arm.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
            this.left_arm.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
            this.right_arm.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
            this.left_arm.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
        } else if (abstractillagerentity$armpose == GrieferIllagerEntity.ArmPose.CELEBRATING) {
            this.right_arm.rotationPointZ = 0.0F;
            this.right_arm.rotationPointX = -5.0F;
            this.right_arm.rotateAngleX = MathHelper.cos(ageInTicks * 0.6662F) * 0.05F;
            this.right_arm.rotateAngleZ = 2.670354F;
            this.right_arm.rotateAngleY = 0.0F;
            this.left_arm.rotationPointZ = 0.0F;
            this.left_arm.rotationPointX = 5.0F;
            this.left_arm.rotateAngleX = MathHelper.cos(ageInTicks * 0.6662F) * 0.05F;
            this.left_arm.rotateAngleZ = -2.3561945F;
            this.left_arm.rotateAngleY = 0.0F;
        }

    }

    private RendererModel getArm(HandSide p_191216_1_) {
        return p_191216_1_ == HandSide.LEFT ? this.left_arm : this.right_arm;
    }

    public RendererModel func_205072_a() {
        return this.head;
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
