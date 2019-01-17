package illager.illagersthanvillagers.client;

import illager.illagersthanvillagers.client.render.RenderGuardIllager;
import illager.illagersthanvillagers.entity.EntityGuardIllager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

@OnlyIn(Dist.CLIENT)
public class IllagerEntityRender {
    public static void entityRender() {
        RenderingRegistry.registerEntityRenderingHandler(EntityGuardIllager.class,RenderGuardIllager::new);
    }
}
