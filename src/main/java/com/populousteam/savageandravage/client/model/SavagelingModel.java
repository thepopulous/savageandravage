package com.populousteam.savageandravage.client.model;//Made with Blockbench
//Paste this code into your mod.

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.populousteam.savageandravage.entity.SavagelingEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SavagelingModel<T extends SavagelingEntity> extends EntityModel<T> {
    private final ModelRenderer head;
    private final ModelRenderer body;
    private final ModelRenderer leg_right;
    private final ModelRenderer leg_left;
    private final ModelRenderer jaw;
    private final ModelRenderer wing_right;
    private final ModelRenderer wing_left;

    public SavagelingModel() {
        textureWidth = 40;
        textureHeight = 40;

        head = new ModelRenderer(this);
        head.setRotationPoint(0.0F, 15.0F, -2.0F);
        head.setTextureOffset(0, 0).addBox(-3.0F, -6.0F, -6.0F, 6, 8, 6, 0.0F, false);
        head.setTextureOffset(24, 0).addBox(-1.0F, -1.0F, -8.0F, 2, 4, 2, 0.0F, false);

        body = new ModelRenderer(this);
        body.setRotationPoint(0.0F, 24.0F, 0.0F);
        body.setTextureOffset(0, 26).addBox(-4.0F, -10.0F, -3.0F, 8, 6, 8, 0.0F, false);
        body.setTextureOffset(13, 19).addBox(-3.0F, -8.0F, 5.0F, 6, 0, 5, 0.0F, false);

        leg_right = new ModelRenderer(this);
        leg_right.setRotationPoint(-2.0F, 20.0F, 2.0F);
        leg_right.setTextureOffset(28, 8).addBox(-1.0F, 0.0F, -1.0F, 1, 4, 1, 0.0F, false);
        leg_right.setTextureOffset(22, 6).addBox(-2.0F, 4.0F, -2.0F, 3, 0, 2, 0.0F, false);
        leg_left = new ModelRenderer(this);
        leg_left.setRotationPoint(2.0F, 20.0F, 2.0F);
        leg_left.setTextureOffset(28, 8).addBox(0.0F, 0.0F, -1.0F, 1, 4, 1, 0.0F, false);
        leg_left.setTextureOffset(22, 6).addBox(-1.0F, 4.0F, -2.0F, 3, 0, 2, 0.0F, false);

        jaw = new ModelRenderer(this);
        jaw.setRotationPoint(0.0F, 17.0F, -2.0F);
        setRotationAngle(jaw, 0.0873F, 0.0F, 0.0F);
        jaw.setTextureOffset(0, 19).addBox(-3.0F, -1.0F, -6.0F, 6, 2, 5, 0.0F, false);

        wing_right = new ModelRenderer(this);
        wing_right.setRotationPoint(-4.0F, 14.0F, 1.0F);
        wing_right.setTextureOffset(18, 8).addBox(-1.0F, 0.0F, -3.0F, 1, 5, 6, 0.0F, false);

        wing_left = new ModelRenderer(this);
        wing_left.setRotationPoint(4.0F, 14.0F, 0.0F);
        wing_left.setTextureOffset(18, 8).addBox(0.0F, 0.0F, -2.0F, 1, 5, 6, 0.0F, false);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        if (isChild) {
            matrixStackIn.push();
            matrixStackIn.scale(1.35F, 1.35F, 1.35F);
            matrixStackIn.translate(0.0F, -0.12F, 0.0F);
            head.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            jaw.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            matrixStackIn.pop();
        } else {
            head.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            jaw.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        }
        body.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        leg_right.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        leg_left.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        wing_right.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        wing_left.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.rotateAngleX = headPitch * ((float) Math.PI / 180F);
        this.head.rotateAngleY = netHeadYaw * ((float) Math.PI / 180F);

        this.jaw.rotateAngleX = headPitch * ((float) Math.PI / 180F) * 0.7F + 0.4F;
        this.jaw.rotateAngleY = netHeadYaw * ((float) Math.PI / 180F);

        this.leg_right.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.leg_left.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;

        this.wing_right.rotateAngleZ = ageInTicks;
        this.wing_left.rotateAngleZ = -ageInTicks;
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}