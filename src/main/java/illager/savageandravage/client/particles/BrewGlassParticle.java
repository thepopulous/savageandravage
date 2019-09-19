package illager.savageandravage.client.particles;

import illager.savageandravage.SavageAndRavageCore;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BrewGlassParticle extends SavageParticle {
    private int lastTick = 0;

    public BrewGlassParticle(World world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, int i) {
        super(world, x, y, z, xSpeed, ySpeed, zSpeed);

        this.motionX = xSpeed;
        this.motionY = ySpeed;
        this.motionZ = zSpeed;
        this.posX = x;
        this.posY = y;
        this.posZ = z;

        float f1 = 1.0F - (float) (Math.random() * (double) 0.3F);

        this.particleRed = f1;
        this.particleGreen = f1;
        this.particleBlue = f1;
        this.particleAlpha = 1f;

        this.particleScale *= 0.75F;
        int i2 = (int) (8.0D / (Math.random() * 0.8D + 0.3D));

        this.maxAge = (int) (Math.random() * 10.0D) + 40;
        this.particleGravity = 1f;
    }

    @Override
    public void onPreRender(BufferBuilder buffer, ActiveRenderInfo activeInfo, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
        Entity entity = activeInfo.getRenderViewEntity();
        if (entity.ticksExisted >= this.lastTick + 12) {

            this.lastTick = entity.ticksExisted;
        }
    }


    @Override
    ResourceLocation getTexture() {
        return new ResourceLocation(SavageAndRavageCore.MODID, "textures/particles/brew_glass" + ".png");
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements ISavageParticle {

        @Override
        public Particle makeParticle(World world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, int... params) {
            return new BrewGlassParticle(world, x, y, z, xSpeed, ySpeed, zSpeed, params.length > 0 ? params[0] : 0xffffff);
        }

    }
}