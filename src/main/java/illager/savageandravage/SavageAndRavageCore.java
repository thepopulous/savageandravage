package illager.savageandravage;

import illager.savageandravage.client.IllagerEntityRender;
import illager.savageandravage.event.EntityEventHandler;
import illager.savageandravage.init.SavageEntityRegistry;
import illager.savageandravage.init.SavageFeatures;
import illager.savageandravage.message.MessageRavagerAttackStat;
import illager.savageandravage.message.MessageRavagerDushStat;
import illager.savageandravage.message.MessageRavagerStopDushStat;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.raid.Raid;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import javax.annotation.Nullable;

@Mod("savageandravage")
public class SavageAndRavageCore {
    public static final String MODID = "savageandravage";

    public static final String NETWORK_PROTOCOL = "1";

    @Nullable
    private Raid raid = null;

    public static final SimpleChannel CHANNEL = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(SavageAndRavageCore.MODID, "net"))

            .networkProtocolVersion(() -> NETWORK_PROTOCOL)
            .clientAcceptedVersions(NETWORK_PROTOCOL::equals)
            .serverAcceptedVersions(NETWORK_PROTOCOL::equals)
            .simpleChannel();

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
        // some preinit code
        MinecraftForge.EVENT_BUS.register(new EntityEventHandler());

        SavageFeatures.addStructureFeature();

        SavageEntityRegistry.addEntitySpawn();

        CHANNEL.registerMessage(0, MessageRavagerAttackStat.class, MessageRavagerAttackStat::writePacketData, MessageRavagerAttackStat::readPacketData, MessageRavagerAttackStat.Handler::handle);
        CHANNEL.registerMessage(1, MessageRavagerDushStat.class, MessageRavagerDushStat::writePacketData, MessageRavagerDushStat::readPacketData, MessageRavagerDushStat.Handler::handle);
        CHANNEL.registerMessage(2, MessageRavagerStopDushStat.class, MessageRavagerStopDushStat::writePacketData, MessageRavagerStopDushStat::readPacketData, MessageRavagerStopDushStat.Handler::handle);

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

    @SubscribeEvent
    public void onServerSetUp(FMLServerStartingEvent event) {

    }

    private void serverStart(FMLServerStartedEvent event) {
    }

}
