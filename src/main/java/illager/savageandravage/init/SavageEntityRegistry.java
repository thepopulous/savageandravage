package illager.savageandravage.init;

import illager.savageandravage.entity.CreepiesEntity;
import illager.savageandravage.entity.GuardIllagerEntity;
import illager.savageandravage.entity.illager.GrieferIllagerEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistryEntry;

import static illager.savageandravage.SavageAndRavageCore.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SavageEntityRegistry extends ForgeRegistryEntry<SavageEntityRegistry> {

    public static final EntityType<CreepiesEntity> CREEPIES = EntityType.Builder.create(CreepiesEntity::new, EntityClassification.MONSTER).setTrackingRange(80).setUpdateInterval(3).setShouldReceiveVelocityUpdates(true).size(0.6F, 1.7F).build(prefix("creepies"));
    public static final EntityType<GrieferIllagerEntity> GRIEFER_ILLAGER = EntityType.Builder.create(GrieferIllagerEntity::new, EntityClassification.MONSTER).setTrackingRange(80).setUpdateInterval(3).setShouldReceiveVelocityUpdates(true).size(0.6F, 2.05F).build(prefix("griefer_illager"));
    public static final EntityType<GuardIllagerEntity> GUARD_ILLAGER = EntityType.Builder.create(GuardIllagerEntity::new, EntityClassification.MONSTER).setTrackingRange(80).setUpdateInterval(3).setShouldReceiveVelocityUpdates(true).size(0.6F, 1.95F).build(prefix("guard_ilager"));

	private static String prefix(String path) {
		return MODID + "." + path;
	}

    @SubscribeEvent
    public static void registerEntity(RegistryEvent.Register<EntityType<?>> event) {
		event.getRegistry().register(GUARD_ILLAGER.setRegistryName("guard_illager"));
        event.getRegistry().register(CREEPIES.setRegistryName("creepies"));
        event.getRegistry().register(GRIEFER_ILLAGER.setRegistryName("griefer_illager"));
    }
}