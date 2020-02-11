package populousteam.savageandravage.init;

import com.google.common.collect.Lists;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;
import populousteam.savageandravage.SavageAndRavageCore;

import java.util.List;

@Mod.EventBusSubscriber(modid = SavageAndRavageCore.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(SavageAndRavageCore.MODID)
public class SavageSoundsRegister {
    private static List<SoundEvent> sounds = Lists.newArrayList();
    public static final SoundEvent RUNE_ACTIVATION = SoundEvents.ENTITY_EVOKER_PREPARE_SUMMON;

    private static SoundEvent create(String name) {

        ResourceLocation id = new ResourceLocation(SavageAndRavageCore.MODID, name);
        SoundEvent sound = new SoundEvent(id);

        sound.setRegistryName(id);

        sounds.add(sound);
        return sound;
    }

    @SubscribeEvent
    public static void registerSounds(RegistryEvent.Register<SoundEvent> registry) {
    }
}