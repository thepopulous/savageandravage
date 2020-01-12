package com.populousteam.client.render.layer;

import com.populousteam.client.model.FriendlyRavagerModel;
import com.populousteam.savageandravage.SavageAndRavage;
import com.populousteam.savageandravage.entity.FriendlyRavagerEntity;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RavagerSaddleLayer extends LayerRenderer<FriendlyRavagerEntity, FriendlyRavagerModel> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(SavageAndRavage.MOD_ID, "textures/entity/friendly_ravager/friendly_ravager_saddle.png");
    private final FriendlyRavagerModel model = new FriendlyRavagerModel();

    public RavagerSaddleLayer(IEntityRenderer<FriendlyRavagerEntity, FriendlyRavagerModel> p_i50927_1_) {
        super(p_i50927_1_);
    }


    @Override
    public void render(FriendlyRavagerEntity entityIn, float p_212842_2_, float p_212842_3_, float p_212842_4_, float p_212842_5_, float p_212842_6_, float p_212842_7_, float p_212842_8_) {
      /*  if (((FriendlyRavagerEntity) entityIn).isSaddled()) {
            this.bindTexture(TEXTURE);
            this.getEntityModel().setModelAttributes(this.model);
            this.model.render(entityIn, p_212842_2_, p_212842_3_, p_212842_5_, p_212842_6_, p_212842_7_, p_212842_8_);
        }*/

    }

    public boolean shouldCombineTextures() {
        return false;
    }
}