package illager.savageandravage.client.render.layer;

import illager.savageandravage.SavageAndRavageCore;
import illager.savageandravage.client.model.FriendlyRavagerModel;
import illager.savageandravage.entity.FriendlyRavagerEntity;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RavagerSnowClothLayer extends LayerRenderer<FriendlyRavagerEntity, FriendlyRavagerModel> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(SavageAndRavageCore.MODID, "textures/entity/friendly_ravager/friendly_ravager_snow.png");
    private final FriendlyRavagerModel model = new FriendlyRavagerModel();

    public RavagerSnowClothLayer(IEntityRenderer<FriendlyRavagerEntity, FriendlyRavagerModel> p_i50927_1_) {
        super(p_i50927_1_);
    }


    @Override
    public void render(FriendlyRavagerEntity entityIn, float p_212842_2_, float p_212842_3_, float p_212842_4_, float p_212842_5_, float p_212842_6_, float p_212842_7_, float p_212842_8_) {
        if (entityIn.isSnowType()) {
            this.bindTexture(TEXTURE);
            this.getEntityModel().setModelAttributes(this.model);
            this.model.setRotationAngles(entityIn, p_212842_2_, p_212842_3_, p_212842_5_, p_212842_6_, p_212842_7_, p_212842_8_);
            this.model.setLivingAnimations(entityIn, p_212842_2_, p_212842_3_, p_212842_4_);
            this.model.render(entityIn, p_212842_2_, p_212842_3_, p_212842_5_, p_212842_6_, p_212842_7_, p_212842_8_);
        }

    }

    public boolean shouldCombineTextures() {
        return true;
    }
}