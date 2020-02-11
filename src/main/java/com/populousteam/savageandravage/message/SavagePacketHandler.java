package com.populousteam.savageandravage.message;

import com.populousteam.savageandravage.SavageAndRavage;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class SavagePacketHandler {
    public static final String NETWORK_PROTOCOL = "2";

    public static final SimpleChannel CHANNEL = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(SavageAndRavage.MODID, "net"))
            .networkProtocolVersion(() -> NETWORK_PROTOCOL)
            .clientAcceptedVersions(NETWORK_PROTOCOL::equals)
            .serverAcceptedVersions(NETWORK_PROTOCOL::equals)
            .simpleChannel();

    public static void register() {
        CHANNEL.messageBuilder(MessageRavagerAttackStat.class, 0)
                .encoder(MessageRavagerAttackStat::writePacketData).decoder(MessageRavagerAttackStat::readPacketData)
                .consumer(MessageRavagerAttackStat.Handler::handle)
                .add();
        CHANNEL.messageBuilder(MessageRavagerDushStat.class, 1)
                .encoder(MessageRavagerDushStat::writePacketData).decoder(MessageRavagerDushStat::readPacketData)
                .consumer(MessageRavagerDushStat.Handler::handle)
                .add();
        CHANNEL.messageBuilder(MessageRavagerStopDushStat.class, 2)
                .encoder(MessageRavagerStopDushStat::writePacketData).decoder(MessageRavagerStopDushStat::readPacketData)
                .consumer(MessageRavagerStopDushStat.Handler::handle)
                .add();
        CHANNEL.messageBuilder(MessageScavengerProp.class, 3)
                .encoder(MessageScavengerProp::writePacketData).decoder(MessageScavengerProp::readPacketData)
                .consumer(MessageScavengerProp.Handler::handle)
                .add();

    }
}
