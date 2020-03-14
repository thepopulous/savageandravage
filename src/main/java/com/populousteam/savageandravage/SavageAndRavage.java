package com.populousteam.savageandravage;

import com.populousteam.savageandravage.client.IllagerEntityRender;
import com.populousteam.savageandravage.event.EntityEventHandler;
import com.populousteam.savageandravage.init.SavageEntities;
import com.populousteam.savageandravage.message.SavagePacketHandler;
import com.populousteam.savageandravage.world.RaidOverrideHandler;
import com.populousteam.savageandravage.world.RevampRaid;
import com.populousteam.savageandravage.world.RevampRaidManager;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;

import javax.annotation.Nullable;

@Mod("savage")
public class SavageAndRavage {
    public static final String MODID = "savage";

    public static SavageAndRavage instance;

    public SavageAndRavage() {
        instance = this;

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, SavageConfig.COMMON_CONFIG);
        //ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_CONFIG);

        SavageConfig.loadConfig(SavageConfig.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve("savage-common.toml"));
        //Config.loadConfig(Config.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve("savageandravage-client.toml"));
        /**Config loading code currently disabled due to unfinishedness*/

        SavagePacketHandler.register();

        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        // Register ourselves for server, registry and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

    }

    private void setup(final FMLCommonSetupEvent event) {
        // some preinit code

        MinecraftForge.EVENT_BUS.register(new EntityEventHandler());

        SavageEntities.addEntitySpawn();
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        IllagerEntityRender.entityRender();
    }

    @SubscribeEvent
    public void onWorldTick(TickEvent.WorldTickEvent event) {

        RaidOverrideHandler.overrideRaidFactory(event.world.getServer().getWorld(event.world.dimension.getType()));
    }

  /*  public static void addWaveMenber() {
        EnumUtils.getEnumList(Raid.class).add(EntityType.RAVAGER, new int[]{0, 0, 0, 1, 0, 1, 0, 2})
    }*/

    private void enqueueIMC(final InterModEnqueueEvent event) {
        // some example code to dispatch IMC to another mod
    }

    private void processIMC(final InterModProcessEvent event) {
        // some example code to receive and process InterModComms from other mods

    }

}
