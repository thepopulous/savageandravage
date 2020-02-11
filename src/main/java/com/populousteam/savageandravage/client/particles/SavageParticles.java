package com.populousteam.savageandravage.client.particles;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public enum SavageParticles {
    BREWGLASS,
    BREWSPLASH,
    SINISTERNOTE,
    SINISTERSOUND;

    SavageParticles() {
    }

    public static SavageParticles getDefaultParticle() {
        return BREWGLASS;
    }

    public static SavageParticles fromId(int id) {
        return values()[MathHelper.clamp(id, 0, values().length - 1)];
    }

    @OnlyIn(Dist.CLIENT)
    public Particle create(World world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, int... params) {
        return getFactory().makeParticle(world, x, y, z, velocityX, velocityY, velocityZ, params);
    }

    @OnlyIn(Dist.CLIENT)
    public ISavageParticle getFactory() {
        switch (this) {
            case BREWGLASS:
                return new BrewGlassParticle.Factory();
            case BREWSPLASH:
                return new BrewSplashParticle.Factory();
            case SINISTERNOTE:
                return new SinisterNoteParticle.Factory();
            case SINISTERSOUND:
                return new SinisterSoundParticle.Factory();
        }
        return new BrewGlassParticle.Factory();
    }

    public void spawn(World world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, int... parameters) {
        if (world.isRemote) {
            spawn(create(world, x, y, z, velocityX, velocityY, velocityZ, parameters));
        }
    }

    @OnlyIn(Dist.CLIENT)
    private void spawn(Particle particle) {
        Minecraft.getInstance().particles.addEffect(particle);
    }
}