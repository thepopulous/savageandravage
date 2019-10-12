package populousteam.savageandravage.message;

import populousteam.savageandravage.entity.FriendlyRavagerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.network.NetworkEvent;

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
                    float f1 = (float) friendlyRavager.getAttribute(SharedMonsterAttributes.ATTACK_KNOCKBACK).getValue();

                    if (friendlyRavager.func_213683_l() == 0) {
                        if (friendlyRavager.isBoosting()) {
                            for (LivingEntity livingentity : friendlyRavager.world.getEntitiesWithinAABB(LivingEntity.class, friendlyRavager.getBoundingBox().grow(0.75D, 0.0D, 0.75D))) {
                                if (livingentity != friendlyRavager && (friendlyRavager.getControllingPassenger() == null || friendlyRavager.getControllingPassenger() != null && livingentity != friendlyRavager.getControllingPassenger()) && !friendlyRavager.isOnSameTeam(livingentity) && (!(livingentity instanceof ArmorStandEntity) || !((ArmorStandEntity) livingentity).hasMarker()) && friendlyRavager.getDistanceSq(livingentity) < 26.0D) {
                                    livingentity.attackEntityFrom(DamageSource.causeMobDamage(friendlyRavager), (float) ((int) friendlyRavager.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getValue() * 0.6F));
                                    livingentity.knockBack(friendlyRavager, f1 * 0.7F, (double) MathHelper.sin(friendlyRavager.rotationYaw * ((float) Math.PI / 180F)), (double) (-MathHelper.cos(friendlyRavager.rotationYaw * ((float) Math.PI / 180F))));
                                }
                            }
                        }
                        friendlyRavager.setBoosting(true);
                    }
                }
            });
            context.setPacketHandled(true);
        }
    }
}