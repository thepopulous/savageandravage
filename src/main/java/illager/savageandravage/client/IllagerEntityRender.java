package illager.savageandravage.client;

import illager.savageandravage.client.render.*;
import illager.savageandravage.entity.CreepiesEntity;
import illager.savageandravage.entity.illager.GrieferIllagerEntity;
import illager.savageandravage.entity.illager.GuardIllagerEntity;
import illager.savageandravage.entity.illager.PoultryFarmerIllagerEntity;
import illager.savageandravage.entity.projectile.CreeperSporeEntity;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

@OnlyIn(Dist.CLIENT)
public class IllagerEntityRender {
    private static final Minecraft MC = Minecraft.getInstance();
    public static void entityRender() {
	    RenderingRegistry.registerEntityRenderingHandler(GuardIllagerEntity.class, GuardIllagerRender::new);
        RenderingRegistry.registerEntityRenderingHandler(CreepiesEntity.class, CreepiesRender::new);
        RenderingRegistry.registerEntityRenderingHandler(GrieferIllagerEntity.class, GrieferIllagerRender::new);
        RenderingRegistry.registerEntityRenderingHandler(CreeperSporeEntity.class, EmptyRender::new);
        RenderingRegistry.registerEntityRenderingHandler(PoultryFarmerIllagerEntity.class, PoultryFarmerIllagerRender::new);
    }
}
