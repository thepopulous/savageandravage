package illager.guardillagers.init;

import illager.guardillagers.GuardIllagers;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.minecraftforge.registries.IForgeRegistry;

public class IllagerSoundsRegister extends ForgeRegistryEntry<IllagerSoundsRegister> {


    public static final SoundEvent GUARDILLAGER_AMBIENT = createEvent("mob.guardillager.ambient");

    public static final SoundEvent GUARDILLAGER_HURT = createEvent("mob.guardillager.hurt");

    public static final SoundEvent GUARDILLAGER_DIE = createEvent("mob.guardillager.die");


    private static SoundEvent createEvent(String soundname) {
        ResourceLocation name = new ResourceLocation(GuardIllagers.MODID + soundname);

        ForgeRegistries.SOUND_EVENTS.getSlaveMap(name, SoundEvent.class);
        return new SoundEvent(name).setRegistryName(name);

    }

    public static void registerSounds(IForgeRegistry<SoundEvent> registry) {
        registry.register(GUARDILLAGER_AMBIENT);
        registry.register(GUARDILLAGER_HURT);
        registry.register(GUARDILLAGER_DIE);

    }
}