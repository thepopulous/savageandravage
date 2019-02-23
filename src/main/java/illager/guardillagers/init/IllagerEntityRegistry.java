package illager.guardillagers.init;

import illager.guardillagers.GuardIllagers;
import illager.guardillagers.entity.EntityGuardIllager;
import net.minecraft.entity.EntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(modid = GuardIllagers.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(GuardIllagers.MODID)
public class IllagerEntityRegistry extends ForgeRegistryEntry<IllagerEntityRegistry> {

    public static final EntityType<EntityGuardIllager> GUARD_ILLAGER = EntityType.register("guard_ilager", EntityType.Builder.create(EntityGuardIllager.class, EntityGuardIllager::new));

    @SubscribeEvent
    public static void registerEntity(RegistryEvent.Register<EntityType<?>> event) {
        event.getRegistry().register(GUARD_ILLAGER);

    }
}