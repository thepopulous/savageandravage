package populousteam.savageandravage.message;

import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.network.NetworkEvent;
import populousteam.savageandravage.entity.FriendlyRavagerEntity;

import java.util.function.Supplier;

public class MessageRavagerDushStat {
    private int entityID;

    public MessageRavagerDushStat() {
    }

    public MessageRavagerDushStat(int entityID) {
        this.entityID = entityID;
    }

    public MessageRavagerDushStat(FriendlyRavagerEntity ravagerEntityIn) {
        this.entityID = ravagerEntityIn.getEntityId();
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public static MessageRavagerDushStat readPacketData(PacketBuffer buf) {
        return new MessageRavagerDushStat(buf.readVarInt());
    }

    public void writePacketData(PacketBuffer buffer) {
        buffer.writeVarInt(this.entityID);
    }


    public static class Handler {
        public static void handle(MessageRavagerDushStat message, Supplier<NetworkEvent.Context> ctx) {
            NetworkEvent.Context context = ctx.get();
            ctx.get().enqueueWork(() -> {

                Entity entity = ctx.get().getSender().getServerWorld().getEntityByID(message.entityID);
                if (entity instanceof FriendlyRavagerEntity) {
                    FriendlyRavagerEntity friendlyRavager = (FriendlyRavagerEntity) entity;
                    Vec3d vec3d = friendlyRavager.getLook(1.0F);

                    if (friendlyRavager.func_213683_l() == 0) {

                        friendlyRavager.setBoosting(true);
                    }
                }
            });
            context.setPacketHandled(true);
        }
    }
}