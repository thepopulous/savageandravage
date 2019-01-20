package illager.guardillagers.init;

import illager.guardillagers.GuardIllagers;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.registry.RegistryNamespaced;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.minecraftforge.registries.IForgeRegistry;

public class IllagerSoundsRegister extends ForgeRegistryEntry<IllagerSoundsRegister> {
    public static final RegistryNamespaced<ResourceLocation, SoundEvent> REGISTRY = new RegistryNamespaced<ResourceLocation, SoundEvent>();
    public static final SoundEvent GUARDILLAGER_AMBIENT = create("mob.guardillager.ambient");
    public static final SoundEvent GUARDILLAGER_HURT = create("mob.guardillager.hurt");
    public static final SoundEvent GUARDILLAGER_DIE = create("mob.guardillager.die");


    private static SoundEvent create(String name) {

        ResourceLocation id = new ResourceLocation(GuardIllagers.MODID, name);

        return new SoundEvent(id).setRegistryName(id);
    }

    public static void registerSounds(IForgeRegistry<SoundEvent> registry) {
        registry.register(GUARDILLAGER_AMBIENT);
        registry.register(GUARDILLAGER_HURT);
        registry.register(GUARDILLAGER_DIE);
    }
}