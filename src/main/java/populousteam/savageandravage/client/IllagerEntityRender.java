package populousteam.savageandravage.client;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import populousteam.savageandravage.client.render.DefenderRender;
import populousteam.savageandravage.entity.illager.DefenderEntity;

@OnlyIn(Dist.CLIENT)
public class IllagerEntityRender {
    private static final Minecraft MC = Minecraft.getInstance();

    public static void entityRender() {
        RenderingRegistry.registerEntityRenderingHandler(DefenderEntity.class, DefenderRender::new);
    }
}