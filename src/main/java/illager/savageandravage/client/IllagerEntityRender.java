package illager.savageandravage.client;

import illager.savageandravage.client.render.*;
import illager.savageandravage.entity.CreepieEntity;
import illager.savageandravage.entity.FriendlyRavagerEntity;
import illager.savageandravage.entity.SavagelingEntity;
import illager.savageandravage.entity.SkeletonVillagerEntity;
import illager.savageandravage.entity.illager.DefenderEntity;
import illager.savageandravage.entity.illager.GrieferIllagerEntity;
import illager.savageandravage.entity.illager.PoultryFarmerIllagerEntity;
import illager.savageandravage.entity.illager.ScavengersEntity;
import illager.savageandravage.entity.projectile.BeastBrewEntity;
import illager.savageandravage.entity.projectile.CreeperSporeEntity;
import illager.savageandravage.entity.projectile.FakeThrownRiderEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

@OnlyIn(Dist.CLIENT)
public class IllagerEntityRender {
    private static final Minecraft MC = Minecraft.getInstance();
    public static void entityRender() {
        RenderingRegistry.registerEntityRenderingHandler(DefenderEntity.class, DefenderRender::new);
        RenderingRegistry.registerEntityRenderingHandler(CreepieEntity.class, CreepiesRender::new);
        RenderingRegistry.registerEntityRenderingHandler(GrieferIllagerEntity.class, GrieferIllagerRender::new);
        RenderingRegistry.registerEntityRenderingHandler(CreeperSporeEntity.class, EmptyRender::new);
        RenderingRegistry.registerEntityRenderingHandler(FakeThrownRiderEntity.class, EmptyRender::new);
        RenderingRegistry.registerEntityRenderingHandler(PoultryFarmerIllagerEntity.class, PoultryFarmerIllagerRender::new);
        RenderingRegistry.registerEntityRenderingHandler(SavagelingEntity.class, SavagelingRender::new);
        RenderingRegistry.registerEntityRenderingHandler(SkeletonVillagerEntity.class, SkeletonVillagerRender::new);
        RenderingRegistry.registerEntityRenderingHandler(ScavengersEntity.class, ScavengersRender::new);
        RenderingRegistry.registerEntityRenderingHandler(FriendlyRavagerEntity.class, FriendlyRavagerRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BeastBrewEntity.class, manager -> new SpriteRenderer(MC.getRenderManager(), MC.getItemRenderer()));

    }
}
