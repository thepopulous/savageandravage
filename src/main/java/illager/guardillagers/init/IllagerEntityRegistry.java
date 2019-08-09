package illager.guardillagers.init;

import illager.guardillagers.entity.GuardIllagerEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistryEntry;

import static illager.guardillagers.GuardIllagers.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class IllagerEntityRegistry extends ForgeRegistryEntry<IllagerEntityRegistry> {

	public static final EntityType<GuardIllagerEntity> GUARD_ILLAGER = EntityType.Builder.create(GuardIllagerEntity::new, EntityClassification.MONSTER).setTrackingRange(80).setUpdateInterval(3).setShouldReceiveVelocityUpdates(true).size(0.6F, 1.95F).build(prefix("guard_ilager"));

	private static String prefix(String path) {
		return MODID + "." + path;
	}

    @SubscribeEvent
    public static void registerEntity(RegistryEvent.Register<EntityType<?>> event) {
	    event.getRegistry().register(GUARD_ILLAGER.setRegistryName("guard_ilager"));

    }
}