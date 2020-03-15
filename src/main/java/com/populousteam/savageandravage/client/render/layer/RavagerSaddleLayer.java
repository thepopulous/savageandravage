package com.populousteam.savageandravage.client.render.layer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.populousteam.savageandravage.SavageAndRavage;
import com.populousteam.savageandravage.client.model.FriendlyRavagerModel;
import com.populousteam.savageandravage.entity.FriendlyRavagerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RavagerSaddleLayer extends LayerRenderer<FriendlyRavagerEntity, FriendlyRavagerModel> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(SavageAndRavage.MODID, "textures/entity/friendly_ravager/friendly_ravager_saddle.png");
    private final FriendlyRavagerModel model = new FriendlyRavagerModel();

    public RavagerSaddleLayer(IEntityRenderer<FriendlyRavagerEntity, FriendlyRavagerModel> p_i50927_1_) {
        super(p_i50927_1_);
    }


    @Override
    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, FriendlyRavagerEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entitylivingbaseIn.isSaddled()) {
            renderCutoutModel(this.getEntityModel(), TEXTURE, matrixStackIn, bufferIn, packedLightIn, entitylivingbaseIn, 1.0F, 1.0F, 1.0F);
        }

    }

    public boolean shouldCombineTextures() {
        return false;
    }
}