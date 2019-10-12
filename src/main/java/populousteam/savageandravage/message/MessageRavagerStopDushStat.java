package populousteam.savageandravage.message;

import populousteam.savageandravage.entity.FriendlyRavagerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageRavagerStopDushStat {
    private int entityID;

    public MessageRavagerStopDushStat() {
    }

    public MessageRavagerStopDushStat(int entityID) {
        this.entityID = entityID;
    }

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
    public void writePacketData(PacketBuffer buffer) {
        buffer.writeVarInt(this.entityID);
    }


    public static class Handler {
        public static void handle(MessageRavagerStopDushStat message, Supplier<NetworkEvent.Context> ctx) {
            NetworkEvent.Context context = ctx.get();
            ctx.get().enqueueWork(() -> {

                Entity entity = ctx.get().getSender().getServerWorld().getEntityByID(message.entityID);
                if (entity instanceof FriendlyRavagerEntity) {
                    FriendlyRavagerEntity friendlyRavager = (FriendlyRavagerEntity) entity;

                    friendlyRavager.setBoosting(false);

                }
            });
            context.setPacketHandled(true);
        }
    }
}