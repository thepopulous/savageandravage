package illager.savageandravage.message;

import illager.savageandravage.entity.FriendlyRavagerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.ICustomPacket;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageRavagerStopDushStat implements ICustomPacket {
    private int entityID;

    public MessageRavagerStopDushStat() {
    }

    @OnlyIn(Dist.CLIENT)
    public MessageRavagerStopDushStat(int entityID) {
        this.entityID = entityID;
    }

    @OnlyIn(Dist.CLIENT)
    public MessageRavagerStopDushStat(FriendlyRavagerEntity ravagerEntityIn) {
        this.entityID = ravagerEntityIn.getEntityId();
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public static MessageRavagerStopDushStat readPacketData(PacketBuffer buf) {
        return new MessageRavagerStopDushStat(buf.readVarInt());
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public static void writePacketData(MessageRavagerStopDushStat stat, PacketBuffer buf) {
        buf.writeVarInt(stat.entityID);
    }


    public static class Handler {
        public static void handle(MessageRavagerStopDushStat message, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {

                Entity entity = ctx.get().getSender().getServerWorld().getEntityByID(message.entityID);
                if (entity instanceof FriendlyRavagerEntity) {
                    FriendlyRavagerEntity friendlyRavager = (FriendlyRavagerEntity) entity;

                    friendlyRavager.setBoosting(false);

                }
            });
        }
    }
}