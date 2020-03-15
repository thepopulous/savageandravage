package com.populousteam.savageandravage.client.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CreepiesModel<T extends Entity> extends SegmentedModel<T> {
    private final ModelRenderer field_78135_a;
    private final ModelRenderer creeperArmor;
    private final ModelRenderer field_78134_c;
    private final ModelRenderer field_78131_d;
    private final ModelRenderer field_78132_e;
    private final ModelRenderer field_78129_f;
    private final ModelRenderer field_78130_g;


    public CreepiesModel() {
        this(0.0F);
    }

    public CreepiesModel(float p_i46366_1_) {
        int i = 6;
        this.field_78135_a = new ModelRenderer(this, 0, 0);
        this.field_78135_a.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, p_i46366_1_ + 1.0F);
        this.field_78135_a.setRotationPoint(0.0F, 6.0F, 0.0F);
        this.creeperArmor = new ModelRenderer(this, 32, 0);
        this.creeperArmor.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, p_i46366_1_ + 0.5F);
        this.creeperArmor.setRotationPoint(0.0F, 6.0F, 0.0F);
        this.field_78134_c = new ModelRenderer(this, 16, 16);
        this.field_78134_c.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, p_i46366_1_);
        this.field_78134_c.setRotationPoint(0.0F, 6.0F, 0.0F);
        this.field_78131_d = new ModelRenderer(this, 0, 16);
        this.field_78131_d.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, p_i46366_1_);
        this.field_78131_d.setRotationPoint(-2.0F, 18.0F, 4.0F);
        this.field_78132_e = new ModelRenderer(this, 0, 16);
        this.field_78132_e.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, p_i46366_1_);
        this.field_78132_e.setRotationPoint(2.0F, 18.0F, 4.0F);
        this.field_78129_f = new ModelRenderer(this, 0, 16);
        this.field_78129_f.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, p_i46366_1_);
        this.field_78129_f.setRotationPoint(-2.0F, 18.0F, -4.0F);
        this.field_78130_g = new ModelRenderer(this, 0, 16);
        this.field_78130_g.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, p_i46366_1_);
        this.field_78130_g.setRotationPoint(2.0F, 18.0F, -4.0F);
    }


    @Override
    public Iterable<ModelRenderer> getParts() {
        return ImmutableList.of(this.field_78135_a, this.field_78134_c, this.field_78131_d, this.field_78132_e, this.field_78129_f, this.field_78130_g);
    }

    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.field_78135_a.rotateAngleY = netHeadYaw * ((float) Math.PI / 180F);
        this.field_78135_a.rotateAngleX = headPitch * ((float) Math.PI / 180F);
        this.field_78131_d.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.field_78132_e.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.field_78129_f.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.field_78130_g.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
    }
}
