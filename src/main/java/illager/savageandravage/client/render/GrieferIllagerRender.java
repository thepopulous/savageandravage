package illager.savageandravage.client.render;

import illager.savageandravage.SavageAndRavageCore;
import illager.savageandravage.client.model.GrieferIllagerModel;
import illager.savageandravage.entity.illager.GrieferIllagerEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.HeadLayer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GrieferIllagerRender<T extends GrieferIllagerEntity> extends MobRenderer<T, GrieferIllagerModel<T>> {
    private static final ResourceLocation TEXTURES = new ResourceLocation(SavageAndRavageCore.MODID, "textures/entity/illager/grieferillager.png");

    public GrieferIllagerRender(EntityRendererManager p_i50959_1_) {
        super(p_i50959_1_, new GrieferIllagerModel<>(), 0.5F);
        this.addLayer(new HeldItemLayer<T, GrieferIllagerModel<T>>(this) {
            public void render(T entityIn, float p_212842_2_, float p_212842_3_, float p_212842_4_, float p_212842_5_, float p_212842_6_, float p_212842_7_, float p_212842_8_) {
                if (entityIn.isAggressive()) {
                    super.render(entityIn, p_212842_2_, p_212842_3_, p_212842_4_, p_212842_5_, p_212842_6_, p_212842_7_, p_212842_8_);
                }
            }
        });
        this.addLayer(new HeadLayer<>(this));
    }

    protected ResourceLocation getEntityTexture(GrieferIllagerEntity entity) {
        return TEXTURES;
    }
}