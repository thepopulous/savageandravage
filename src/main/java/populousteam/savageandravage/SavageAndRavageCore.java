package populousteam.savageandravage;

import populousteam.savageandravage.client.IllagerEntityRender;
import populousteam.savageandravage.event.EntityEventHandler;
import populousteam.savageandravage.init.SavageEntityRegistry;
import populousteam.savageandravage.message.SavagePacketHandler;
import populousteam.savageandravage.world.RevampRaid;
import populousteam.savageandravage.world.RevampRaidManager;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
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

    public static SavageAndRavageCore instance;

    @Nullable
    public RevampRaidManager revampRaid = null;

    public SavageAndRavageCore() {
        instance = this;

        /*ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_CONFIG);
        //ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_CONFIG);

        Config.loadConfig(Config.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve("savageandravage-common.toml"));
        //Config.loadConfig(Config.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve("savageandravage-client.toml"));*/
        /**Config loading code currently disabled due to unfinishedness*/

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

        SavageEntityRegistry.addEntitySpawn();
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        IllagerEntityRender.entityRender();


    }

    @SubscribeEvent
    public void onWorldTick(TickEvent.WorldTickEvent event) {
        if (this.revampRaid != null) {
            this.revampRaid.tick();
        } else {
            this.revampRaid = new RevampRaidManager(event.world.getServer().getWorld(event.world.dimension.getType()));
        }
    }

    @Nullable
    public RevampRaid findRaid(BlockPos pos) {
        if (this.revampRaid != null) {
            return this.revampRaid.findRaid(pos, 9216);
        } else {
            return null;
        }
    }

    public boolean hasRaid(BlockPos pos) {
        return this.findRaid(pos) != null;
    }

    public RevampRaidManager getRaids() {
        return this.revampRaid;
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
        // some example code to dispatch IMC to another mod
    }

    private void processIMC(final InterModProcessEvent event) {
        // some example code to receive and process InterModComms from other mods

    }

}
