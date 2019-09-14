package illager.savageandravage.message;

import illager.savageandravage.SavageAndRavageCore;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class SavagePacketHandler {
    public static final String NETWORK_PROTOCOL = Integer.toString(2);

    public static final SimpleChannel CHANNEL = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(SavageAndRavageCore.MODID, "net"))

            .networkProtocolVersion(() -> NETWORK_PROTOCOL)
            .clientAcceptedVersions(NETWORK_PROTOCOL::equals)
            .serverAcceptedVersions(NETWORK_PROTOCOL::equals)
            .simpleChannel();

    public static void register() {
        CHANNEL.registerMessage(0, MessageRavagerAttackStat.class, MessageRavagerAttackStat::writePacketData, MessageRavagerAttackStat::readPacketData, MessageRavagerAttackStat.Handler::handle);
        CHANNEL.registerMessage(1, MessageRavagerDushStat.class, MessageRavagerDushStat::writePacketData, MessageRavagerDushStat::readPacketData, MessageRavagerDushStat.Handler::handle);
        CHANNEL.registerMessage(2, MessageRavagerStopDushStat.class, MessageRavagerStopDushStat::writePacketData, MessageRavagerStopDushStat::readPacketData, MessageRavagerStopDushStat.Handler::handle);

    }
}
