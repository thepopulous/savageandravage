package illager.savageandravage.message;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageScavengerProp {
    private double posX, posY, posZ;

    public MessageScavengerProp() {
    }

    public MessageScavengerProp(double posX, double posY, double posZ) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public static MessageScavengerProp readPacketData(PacketBuffer buffer) {
        double posX = buffer.readDouble();
        double posY = buffer.readDouble();
        double posZ = buffer.readDouble();

        return new MessageScavengerProp(posX, posY, posZ);
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void writePacketData(PacketBuffer buffer) {
        buffer.writeDouble(this.posX);
        buffer.writeDouble(this.posY);
        buffer.writeDouble(this.posZ);
    }


    public static class Handler {
        public static void handle(MessageScavengerProp message, Supplier<NetworkEvent.Context> ctx) {
            NetworkEvent.Context context = ctx.get();

            if (context.getDirection().getReceptionSide() == LogicalSide.CLIENT) {
                context.enqueueWork(() -> {
                    ClientPlayerEntity player = Minecraft.getInstance().player;
                    if (player.world.isBlockLoaded(new BlockPos(message.posX, message.posY, message.posZ))) {
                        for (int k = 0; k < 20; ++k) {
                            double d2 = player.world.rand.nextGaussian() * 0.02D;
                            double d0 = player.world.rand.nextGaussian() * 0.02D;
                            double d1 = player.world.rand.nextGaussian() * 0.02D;
                            player.world.addParticle(ParticleTypes.POOF, message.posX + (double) (player.world.rand.nextFloat() * player.getWidth() * 2.0F) - (double) player.getWidth(), message.posY + (double) (player.world.rand.nextFloat() * player.getHeight()), message.posZ + (double) (player.world.rand.nextFloat() * player.getWidth() * 2.0F) - (double) player.getWidth(), d2, d0, d1);
                        }
                    }
                });
            }


            context.setPacketHandled(true);
        }
    }
}