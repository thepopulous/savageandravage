package illager.guardillagers.client;

import illager.guardillagers.client.render.GuardIllagerRender;
import illager.guardillagers.entity.GuardIllagerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

@OnlyIn(Dist.CLIENT)
public class IllagerEntityRender {
    public static void entityRender() {
	    RenderingRegistry.registerEntityRenderingHandler(GuardIllagerEntity.class, GuardIllagerRender::new);
    }
}
