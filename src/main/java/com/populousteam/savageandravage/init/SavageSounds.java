package com.populousteam.savageandravage.init;

import com.populousteam.savageandravage.SavageAndRavage;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = SavageAndRavage.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SavageSounds {

    private static final List<SoundEvent> SOUNDS = new ArrayList<>();
    public static final SoundEvent RUNE_ACTIVATION = SoundEvents.ENTITY_EVOKER_PREPARE_SUMMON;
    public static final SoundEvent GUARDILLAGER_AMBIENT = register("mob.guardillager.ambient");
    public static final SoundEvent GUARDILLAGER_ANGRY = register("mob.guardillager.angry");
    public static final SoundEvent GUARDILLAGER_HURT = register("mob.guardillager.hurt");
    public static final SoundEvent GUARDILLAGER_DIE = register("mob.guardillager.die");
    public static final SoundEvent GUARDILLAGER_STEP = register("mob.guardillager.step");
    public static final SoundEvent HYENA_AMBIENT = register("mob.hyena.ambient");
    public static final SoundEvent HYENA_HURT = register("mob.hyena.hurt");
    public static final SoundEvent HYENA_DEATH = register( "mob.hyena.death");

    private static SoundEvent register(String name)
    {
        ResourceLocation resource = new ResourceLocation(SavageAndRavage.MODID, name);
        SoundEvent sound = new SoundEvent(resource).setRegistryName(resource.toString());
        SOUNDS.add(sound);
        return sound;
    }

    @SubscribeEvent
    @SuppressWarnings("unused")
    public static void registerSounds(final RegistryEvent.Register<SoundEvent> event)
    {
        SOUNDS.forEach(soundEvent -> event.getRegistry().register(soundEvent));
        SOUNDS.clear();
    }
}