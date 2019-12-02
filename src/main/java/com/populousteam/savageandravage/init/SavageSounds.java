package com.populousteam.savageandravage.init;

import com.google.common.collect.Lists;
import com.populousteam.savageandravage.SavageAndRavage;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

import java.util.List;

@Mod.EventBusSubscriber(modid = SavageAndRavage.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(SavageAndRavage.MOD_ID)
public class SavageSounds {
    private static List<SoundEvent> sounds = Lists.newArrayList();
    public static final SoundEvent GUARDILLAGER_AMBIENT = create("mob.defender.ambient");
    public static final SoundEvent GUARDILLAGER_ANGRY = create("mob.defender.angry");
    public static final SoundEvent GUARDILLAGER_HURT = create("mob.defender.hurt");
    public static final SoundEvent GUARDILLAGER_DIE = create("mob.defender.die");
    public static final SoundEvent GUARDILLAGER_STEP = create("mob.defender.step");
    public static final SoundEvent HYENA_AMBIENT = create("mob.hyena.ambient");
    public static final SoundEvent HYENA_HURT = create("mob.hyena.hurt");
    public static final SoundEvent HYENA_DEATH = create("mob.hyena.death");
    public static final SoundEvent RUNE_ACTIVATION = SoundEvents.ENTITY_EVOKER_PREPARE_SUMMON;

    private static SoundEvent create(String name) {

        ResourceLocation id = new ResourceLocation(SavageAndRavage.MOD_ID, name);
        SoundEvent sound = new SoundEvent(id);

        sound.setRegistryName(id);

        sounds.add(sound);
        return sound;
    }

    @SubscribeEvent
    public static void registerSounds(RegistryEvent.Register<SoundEvent> registry) {
        for (SoundEvent sound : sounds) {

            registry.getRegistry().register(sound);
        }
    }
}