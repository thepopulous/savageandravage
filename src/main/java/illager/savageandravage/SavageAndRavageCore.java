package illager.savageandravage;

import illager.savageandravage.client.IllagerEntityRender;
import illager.savageandravage.event.EntityEventHandler;
import illager.savageandravage.init.SavageEntityRegistry;
import illager.savageandravage.init.SavageFeatures;
import illager.savageandravage.message.SavagePacketHandler;
import net.minecraft.world.raid.Raid;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import javax.annotation.Nullable;

@Mod("savageandravage")
public class SavageAndRavageCore {
    public static final String MODID = "savageandravage";

    @Nullable
    private Raid raid = null;



    public SavageAndRavageCore() {
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
        SavagePacketHandler.register();
        // some preinit code
        MinecraftForge.EVENT_BUS.register(new EntityEventHandler());

        SavageFeatures.addStructureFeature();

        SavageEntityRegistry.addEntitySpawn();
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        IllagerEntityRender.entityRender();
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
        // some example code to dispatch IMC to another mod
    }

    private void processIMC(final InterModProcessEvent event) {
        // some example code to receive and process InterModComms from other mods

    }

}
