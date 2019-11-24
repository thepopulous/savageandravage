package populousteam.savageandravage.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import populousteam.savageandravage.client.render.*;
import populousteam.savageandravage.entity.*;
import populousteam.savageandravage.entity.illager.DefenderEntity;
import populousteam.savageandravage.entity.illager.GrieferIllagerEntity;
import populousteam.savageandravage.entity.illager.PoultryFarmerIllagerEntity;
import populousteam.savageandravage.entity.illager.ScavengersEntity;
import populousteam.savageandravage.entity.projectile.BestialBrewEntity;
import populousteam.savageandravage.entity.projectile.CreeperSporeEntity;

@OnlyIn(Dist.CLIENT)
public class IllagerEntityRender {
    private static final Minecraft MC = Minecraft.getInstance();

    public static void entityRender() {
        RenderingRegistry.registerEntityRenderingHandler(DefenderEntity.class, DefenderRender::new);
        RenderingRegistry.registerEntityRenderingHandler(CreepieEntity.class, CreepiesRender::new);
        RenderingRegistry.registerEntityRenderingHandler(GrieferIllagerEntity.class, GrieferIllagerRender::new);
        RenderingRegistry.registerEntityRenderingHandler(CreeperSporeEntity.class, EmptyRender::new);
        RenderingRegistry.registerEntityRenderingHandler(PoultryFarmerIllagerEntity.class, PoultryFarmerIllagerRender::new);
        RenderingRegistry.registerEntityRenderingHandler(SavagelingEntity.class, SavagelingRender::new);
        RenderingRegistry.registerEntityRenderingHandler(SkeletonVillagerEntity.class, SkeletonVillagerRender::new);
        RenderingRegistry.registerEntityRenderingHandler(ScavengersEntity.class, ScavengersRender::new);
        RenderingRegistry.registerEntityRenderingHandler(FriendlyRavagerEntity.class, FriendlyRavagerRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BestialBrewEntity.class, manager -> new SpriteRenderer(MC.getRenderManager(), MC.getItemRenderer()));
        RenderingRegistry.registerEntityRenderingHandler(HyenaEntity.class, HyenaRender::new);
    }
}
