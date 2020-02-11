package com.populousteam.savageandravage.client.particles;

import com.populousteam.savageandravage.SavageAndRavage;
import net.minecraft.client.particle.Particle;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SinisterNoteParticle extends SavageParticle {
    private int lastTick = 0;

    public SinisterNoteParticle(World world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, int i) {
        super(world, x, y, z, xSpeed, ySpeed, zSpeed);

        this.motionX += xSpeed;
        this.motionY += ySpeed;
        this.motionZ += zSpeed;
        this.posX = x;
        this.posY = y;
        this.posZ = z;

        float f1 = 1.0F - (float) (Math.random() * (double) 0.3F);

        this.particleRed = f1;
        this.particleGreen = f1;
        this.particleBlue = f1;
        this.particleAlpha = 1f;

        this.motionY *= 0.2F;
        if (xSpeed == 0.0D && zSpeed == 0.0D) {
            this.motionX *= 0.1F;
            this.motionZ *= 0.1F;
        }

        this.particleScale *= 0.95F;
        this.maxAge = (int) (8.0D / (Math.random() * 0.8D + 0.2D));
        this.canCollide = false;

        this.particleGravity = 0.0f;
    }

    public int getBrightnessForRender(float partialTick) {
        int i = super.getBrightnessForRender(partialTick);
        float f = (float) this.age / (float) this.maxAge;
        f = f * f;
        f = f * f;
        int j = i & 255;
        int k = i >> 16 & 255;
        k = k + (int) (f * 15.0F * 16.0F);
        if (k > 240) {
            k = 240;
        }

        return j | k << 16;
    }

    public void tick() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.age++ >= this.maxAge) {
            this.setExpired();
        } else {
            this.move(this.motionX, this.motionY, this.motionZ);

            this.motionX *= 0.96F;
            this.motionY *= 0.96F;
            this.motionZ *= 0.96F;
            if (this.onGround) {
                this.motionX *= 0.7F;
                this.motionZ *= 0.7F;
            }

        }
    }

    @Override
    ResourceLocation getTexture() {
        return new ResourceLocation(SavageAndRavage.MODID, "textures/particles/sinister_note.png");
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements ISavageParticle {

        @Override
        public Particle makeParticle(World world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, int... params) {
            return new BrewSplashParticle(world, x, y, z, xSpeed, ySpeed, zSpeed, params.length > 0 ? params[0] : 0xffffff);
        }

    }
}