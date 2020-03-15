package com.populousteam.savageandravage.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ArmorStandEntity;

public class PoultryFarmerHatModel<T extends LivingEntity> extends BipedModel<T> {
    private final ModelRenderer hat;

    public PoultryFarmerHatModel() {
        super(0.0F);
        textureWidth = 32;
        textureHeight = 32;

        hat = new ModelRenderer(this);
        hat.setRotationPoint(0.0F, 24.0F, 0.0F);
        hat.setTextureOffset(0, 0).addBox(-4.0F, -9.61F, -4.0F, 8, 4, 8, 0.6F, false);
        hat.setTextureOffset(0, 16).addBox(-8.0F, -5.0F, -8.0F, 16, 0, 16, 0.0F, false);
    }

    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {

        hat.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        setAngle(hat, this.bipedHead);
        if (entityIn instanceof ArmorStandEntity) {
            ArmorStandEntity entityarmorstand = (ArmorStandEntity) entityIn;
            this.hat.rotateAngleX = ((float) Math.PI / 180F) * entityarmorstand.getHeadRotation().getX();
            this.hat.rotateAngleY = ((float) Math.PI / 180F) * entityarmorstand.getHeadRotation().getY();
            this.hat.rotateAngleZ = ((float) Math.PI / 180F) * entityarmorstand.getHeadRotation().getZ();
        }
    }

    protected void setAngle(ModelRenderer modelRenderer, ModelRenderer modelRenderer2) {
        modelRenderer.rotationPointX = modelRenderer2.rotationPointX;
        modelRenderer.rotationPointY = modelRenderer2.rotationPointY;
        modelRenderer.rotationPointZ = modelRenderer2.rotationPointZ;

        modelRenderer.rotateAngleX = modelRenderer2.rotateAngleX;
        modelRenderer.rotateAngleY = modelRenderer2.rotateAngleY;
        modelRenderer.rotateAngleZ = modelRenderer2.rotateAngleZ;
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
