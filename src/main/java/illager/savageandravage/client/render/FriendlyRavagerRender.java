package illager.savageandravage.client.render;

import illager.savageandravage.SavageAndRavageCore;
import illager.savageandravage.entity.FriendlyRavagerEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.RavagerModel;
import net.minecraft.entity.monster.RavagerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;

@OnlyIn(Dist.CLIENT)
public class FriendlyRavagerRender extends MobRenderer<RavagerEntity, RavagerModel> {
    private static final ResourceLocation FRIENDLYRAVAGER_TEXTURE = new ResourceLocation(SavageAndRavageCore.MODID, "textures/entity/friendlyravager.png");

    public FriendlyRavagerRender(EntityRendererManager p_i50958_1_) {
        super(p_i50958_1_, new RavagerModel(), 1.1F);
    }

    protected ResourceLocation getEntityTexture(RavagerEntity entity) {
        return FRIENDLYRAVAGER_TEXTURE;
    }

    public static class RenderFactory implements IRenderFactory<FriendlyRavagerEntity>
    {
        @Override
        public EntityRenderer<? super FriendlyRavagerEntity> createRenderFor(EntityRendererManager manager)
        {
            return new FriendlyRavagerRender(manager);
        }

    }
}
