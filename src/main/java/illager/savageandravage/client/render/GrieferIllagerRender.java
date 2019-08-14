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
        this.addLayer(new HeldItemLayer<>(this));
        this.addLayer(new HeadLayer<>(this));
    }

    protected ResourceLocation getEntityTexture(GrieferIllagerEntity entity) {
        return TEXTURES;
    }
}