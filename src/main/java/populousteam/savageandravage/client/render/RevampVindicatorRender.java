package populousteam.savageandravage.client.render;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.IllagerRenderer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.client.renderer.entity.model.IllagerModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import populousteam.savageandravage.client.model.VindicatorIllagerModel;
import populousteam.savageandravage.entity.illager.RevampVindicatorEntity;

@OnlyIn(Dist.CLIENT)
public class RevampVindicatorRender extends IllagerRenderer<RevampVindicatorEntity> {
    private static final ResourceLocation VINDICATOR_TEXTURE = new ResourceLocation("textures/entity/illager/vindicator.png");

    public RevampVindicatorRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new VindicatorIllagerModel<>(0.0F, 0.0F, 64, 64), 0.5F);
        this.addLayer(new HeldItemLayer<RevampVindicatorEntity, IllagerModel<RevampVindicatorEntity>>(this) {
            public void render(RevampVindicatorEntity entityIn, float p_212842_2_, float p_212842_3_, float p_212842_4_, float p_212842_5_, float p_212842_6_, float p_212842_7_, float p_212842_8_) {
                if (entityIn.isAggressive()) {
                    super.render(entityIn, p_212842_2_, p_212842_3_, p_212842_4_, p_212842_5_, p_212842_6_, p_212842_7_, p_212842_8_);
                }

            }
        });
    }

    protected ResourceLocation getEntityTexture(RevampVindicatorEntity entity) {
        return VINDICATOR_TEXTURE;
    }
}