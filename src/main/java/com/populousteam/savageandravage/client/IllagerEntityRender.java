package com.populousteam.savageandravage.client;

import com.populousteam.savageandravage.client.render.*;
import com.populousteam.savageandravage.entity.*;
import com.populousteam.savageandravage.entity.illager.DefenderEntity;
import com.populousteam.savageandravage.entity.illager.GrieferIllagerEntity;
import com.populousteam.savageandravage.entity.illager.PoultryFarmerIllagerEntity;
import com.populousteam.savageandravage.entity.illager.ScavengersEntity;
import com.populousteam.savageandravage.entity.projectile.BestialBrewEntity;
import com.populousteam.savageandravage.entity.projectile.CreeperSporeEntity;
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
        RenderingRegistry.registerEntityRenderingHandler(PoultryFarmerIllagerEntity.class, PoultryFarmerIllagerRender::new);
        RenderingRegistry.registerEntityRenderingHandler(SavagelingEntity.class, SavagelingRender::new);
        RenderingRegistry.registerEntityRenderingHandler(SkeletonVillagerEntity.class, SkeletonVillagerRender::new);
        RenderingRegistry.registerEntityRenderingHandler(ScavengersEntity.class, ScavengersRender::new);
        RenderingRegistry.registerEntityRenderingHandler(FriendlyRavagerEntity.class, FriendlyRavagerRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BestialBrewEntity.class, manager -> new SpriteRenderer(MC.getRenderManager(), MC.getItemRenderer()));
        RenderingRegistry.registerEntityRenderingHandler(HyenaEntity.class, HyenaRender::new);
    }
}
