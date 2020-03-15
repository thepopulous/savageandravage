package com.populousteam.savageandravage.client;

import com.populousteam.savageandravage.client.render.*;
import com.populousteam.savageandravage.init.SavageEntities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

@OnlyIn(Dist.CLIENT)
public class IllagerEntityRender {
    private static final Minecraft MC = Minecraft.getInstance();

    public static void entityRender() {
        RenderingRegistry.registerEntityRenderingHandler(SavageEntities.DEFENDER, DefenderRender::new);
        RenderingRegistry.registerEntityRenderingHandler(SavageEntities.CREEPIES, CreepiesRender::new);
        RenderingRegistry.registerEntityRenderingHandler(SavageEntities.GRIEFER_ILLAGER, GrieferIllagerRender::new);
        RenderingRegistry.registerEntityRenderingHandler(SavageEntities.CREEPER_SPORE, EmptyRender::new);
        RenderingRegistry.registerEntityRenderingHandler(SavageEntities.POULTRY_FARMER, PoultryFarmerIllagerRender::new);
        RenderingRegistry.registerEntityRenderingHandler(SavageEntities.SAVAGELING, SavagelingRender::new);
        RenderingRegistry.registerEntityRenderingHandler(SavageEntities.SKELETONVILLAGER, SkeletonVillagerRender::new);
        RenderingRegistry.registerEntityRenderingHandler(SavageEntities.SCAVENGER, ScavengersRender::new);
        RenderingRegistry.registerEntityRenderingHandler(SavageEntities.FRIENDLY_RAVAGER, FriendlyRavagerRender::new);
        RenderingRegistry.registerEntityRenderingHandler(SavageEntities.BESTIAL_BREW, manager -> new SpriteRenderer(MC.getRenderManager(), MC.getItemRenderer()));
        RenderingRegistry.registerEntityRenderingHandler(SavageEntities.HYENA, HyenaRender::new);
    }
}
