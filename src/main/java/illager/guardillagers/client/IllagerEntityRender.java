package illager.guardillagers.client;

import illager.guardillagers.client.render.RenderGuardIllager;
import illager.guardillagers.entity.EntityGuardIllager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

@OnlyIn(Dist.CLIENT)
public class IllagerEntityRender {
    public static void entityRender() {
        RenderingRegistry.registerEntityRenderingHandler(EntityGuardIllager.class,RenderGuardIllager::new);
    }
}
