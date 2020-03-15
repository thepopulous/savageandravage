package com.populousteam.savageandravage.client.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ArmorStandEntity;

/**
 * ModelGuardHat - Undefined
 * Created using Tabula 7.0.0
 */
public class GuardHatModel<T extends LivingEntity> extends BipedModel<T> {
    public ModelRenderer Hat1;
    public ModelRenderer Hat2;
    public ModelRenderer Hat3;

    public GuardHatModel() {
        super(0.0F);
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.Hat1 = new ModelRenderer(this, 0, 0);
        this.Hat1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Hat1.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
        this.Hat3 = new ModelRenderer(this, -16, 16);
        this.Hat3.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Hat3.addBox(-8.0F, -3.0F, -8.0F, 16, 0, 16, 0.0F);
        this.Hat2 = new ModelRenderer(this, 32, 0);
        this.Hat2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Hat2.addBox(-4.0F, -8.14F, -4.0F, 8, 8, 8, 0.6F);
        this.Hat1.addChild(Hat2);
        this.Hat1.addChild(Hat3);
    }

    @Override
    protected Iterable<ModelRenderer> getHeadParts() {
        return ImmutableList.of(this.Hat1);
    }

    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        setAngle(Hat1, this.bipedHead);
        if (entityIn instanceof ArmorStandEntity) {
            ArmorStandEntity entityarmorstand = (ArmorStandEntity) entityIn;
            this.Hat1.rotateAngleX = ((float) Math.PI / 180F) * entityarmorstand.getHeadRotation().getX();
            this.Hat1.rotateAngleY = ((float) Math.PI / 180F) * entityarmorstand.getHeadRotation().getY();
            this.Hat1.rotateAngleZ = ((float) Math.PI / 180F) * entityarmorstand.getHeadRotation().getZ();
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


    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
