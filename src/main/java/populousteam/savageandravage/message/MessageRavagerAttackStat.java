package populousteam.savageandravage.message;

import populousteam.savageandravage.entity.FriendlyRavagerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageRavagerAttackStat {
    private int entityID;

    public MessageRavagerAttackStat() {
    }

    public MessageRavagerAttackStat(int entityID) {
        this.entityID = entityID;
    }

    public MessageRavagerAttackStat(FriendlyRavagerEntity ravagerEntityIn) {
        this.entityID = ravagerEntityIn.getEntityId();
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public static MessageRavagerAttackStat readPacketData(PacketBuffer buf) {
        return new MessageRavagerAttackStat(buf.readVarInt());
    }

    public void writePacketData(PacketBuffer buffer) {
        buffer.writeVarInt(this.entityID);
    }


    public static class Handler {
        public static void handle(MessageRavagerAttackStat message, Supplier<NetworkEvent.Context> ctx) {
            NetworkEvent.Context context = ctx.get();
            ctx.get().enqueueWork(() -> {

                Entity entity = ctx.get().getSender().getServerWorld().getEntityByID(message.entityID);
                if (entity instanceof FriendlyRavagerEntity) {
                    FriendlyRavagerEntity friendlyRavager = (FriendlyRavagerEntity) entity;
                    Vec3d vec3d = friendlyRavager.getLook(1.0F);

                    if (friendlyRavager.func_213683_l() == 0) {
                        for (LivingEntity livingentity : friendlyRavager.world.getEntitiesWithinAABB(LivingEntity.class, friendlyRavager.getBoundingBox().grow(0.25D, 0.0D, 0.25D).offset(vec3d.x * 1.2D, vec3d.y * 1.0D, vec3d.z * 1.2D))) {
                            if (livingentity != friendlyRavager && (friendlyRavager.getControllingPassenger() == null || friendlyRavager.getControllingPassenger() != null && livingentity != friendlyRavager.getControllingPassenger()) && !friendlyRavager.isOnSameTeam(livingentity) && (!(livingentity instanceof ArmorStandEntity) || !((ArmorStandEntity) livingentity).hasMarker()) && friendlyRavager.getDistanceSq(livingentity) < 26.0D) {
                                friendlyRavager.attackEntityAsMob(livingentity);
                            }
                        }
                        friendlyRavager.world.setEntityState(friendlyRavager, (byte) 4);
                        friendlyRavager.playSound(SoundEvents.ENTITY_RAVAGER_ATTACK, 1.0F, 1.0F);
                    }
                }
            });
            context.setPacketHandled(true);
        }
    }
}