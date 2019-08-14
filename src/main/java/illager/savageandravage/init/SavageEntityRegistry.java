package illager.savageandravage.init;

import illager.savageandravage.entity.CreepiesEntity;
import illager.savageandravage.entity.GuardIllagerEntity;
import illager.savageandravage.entity.illager.GrieferIllagerEntity;
import illager.savageandravage.entity.projectile.CreeperSporeEntity;
import illager.savageandravage.entity.projectile.SporeCloudEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistryEntry;

import static illager.savageandravage.SavageAndRavageCore.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SavageEntityRegistry extends ForgeRegistryEntry<SavageEntityRegistry> {

    public static final EntityType<CreepiesEntity> CREEPIES = EntityType.Builder.create(CreepiesEntity::new, EntityClassification.MONSTER).setTrackingRange(80).setUpdateInterval(3).setShouldReceiveVelocityUpdates(true).size(0.25F, 0.85F).build(prefix("creepies"));
    public static final EntityType<GrieferIllagerEntity> GRIEFER_ILLAGER = EntityType.Builder.create(GrieferIllagerEntity::new, EntityClassification.MONSTER).setTrackingRange(80).setUpdateInterval(3).setShouldReceiveVelocityUpdates(true).size(0.6F, 2.05F).build(prefix("griefer_illager"));
    public static final EntityType<CreeperSporeEntity> CREEPER_SPORE = EntityType.Builder.<CreeperSporeEntity>create(CreeperSporeEntity::new, EntityClassification.MISC).setTrackingRange(64).setUpdateInterval(1).setShouldReceiveVelocityUpdates(true).setCustomClientFactory(CreeperSporeEntity::new).size(0.25F, 0.25F).build(prefix("creeper_spore"));
    public static final EntityType<SporeCloudEntity> SPORE_CLOUD = EntityType.Builder.<SporeCloudEntity>create(SporeCloudEntity::new, EntityClassification.MISC).setTrackingRange(64).setUpdateInterval(Integer.MAX_VALUE).setShouldReceiveVelocityUpdates(true).immuneToFire().size(6.0F, 0.5F).build(prefix("spore_cloud"));
	public static final EntityType<GuardIllagerEntity> GUARD_ILLAGER = EntityType.Builder.create(GuardIllagerEntity::new, EntityClassification.MONSTER).setTrackingRange(80).setUpdateInterval(3).setShouldReceiveVelocityUpdates(true).size(0.6F, 1.95F).build(prefix("guard_ilager"));

	private static String prefix(String path) {
		return MODID + "." + path;
	}

    @SubscribeEvent
    public static void registerEntity(RegistryEvent.Register<EntityType<?>> event) {
		event.getRegistry().register(GUARD_ILLAGER.setRegistryName("guard_illager"));
        event.getRegistry().register(CREEPIES.setRegistryName("creepies"));
        event.getRegistry().register(GRIEFER_ILLAGER.setRegistryName("griefer_illager"));
        event.getRegistry().register(CREEPER_SPORE.setRegistryName("creeper_spore"));
        event.getRegistry().register(SPORE_CLOUD.setRegistryName("spore_cloud"));
    }
}