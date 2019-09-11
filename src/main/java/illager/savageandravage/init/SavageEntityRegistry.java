package illager.savageandravage.init;

import illager.savageandravage.entity.*;
import illager.savageandravage.entity.illager.DefenderEntity;
import illager.savageandravage.entity.illager.GrieferIllagerEntity;
import illager.savageandravage.entity.illager.PoultryFarmerIllagerEntity;
import illager.savageandravage.entity.illager.ScavengersEntity;
import illager.savageandravage.entity.projectile.BeastBrewEntity;
import illager.savageandravage.entity.projectile.CreeperSporeEntity;
import illager.savageandravage.entity.projectile.FakeThrownRiderEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static illager.savageandravage.SavageAndRavageCore.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SavageEntityRegistry {

    public static final EntityType<CreepieEntity> CREEPIES = EntityType.Builder.create(CreepieEntity::new, EntityClassification.MONSTER).setTrackingRange(80).setUpdateInterval(3).setShouldReceiveVelocityUpdates(true).size(0.6F, 1.7F).build(prefix("creepie"));
    public static final EntityType<GrieferIllagerEntity> GRIEFER_ILLAGER = EntityType.Builder.create(GrieferIllagerEntity::new, EntityClassification.MONSTER).setTrackingRange(80).setUpdateInterval(3).setShouldReceiveVelocityUpdates(true).size(0.6F, 2.05F).build(prefix("griefer_illager"));
    public static final EntityType<DefenderEntity> DEFENDER = EntityType.Builder.create(DefenderEntity::new, EntityClassification.MONSTER).setTrackingRange(80).setUpdateInterval(3).setShouldReceiveVelocityUpdates(true).size(0.6F, 1.95F).build(prefix("defender"));
    public static final EntityType<CreeperSporeEntity> CREEPER_SPORE = EntityType.Builder.<CreeperSporeEntity>create(CreeperSporeEntity::new, EntityClassification.MISC).setTrackingRange(100).setUpdateInterval(1).setShouldReceiveVelocityUpdates(true).setCustomClientFactory(CreeperSporeEntity::new).size(0.25F, 0.25F).build(prefix("creeper_spore"));
    public static final EntityType<PoultryFarmerIllagerEntity> POULTRY_FARMER = EntityType.Builder.create(PoultryFarmerIllagerEntity::new, EntityClassification.MONSTER).setTrackingRange(80).setUpdateInterval(3).setShouldReceiveVelocityUpdates(true).size(0.6F, 1.95F).build(prefix("poultry_farmer"));
    public static final EntityType<FakeThrownRiderEntity> FAKE_THROWN_RIDER = EntityType.Builder.<FakeThrownRiderEntity>create(FakeThrownRiderEntity::new, EntityClassification.MISC).setTrackingRange(100).setUpdateInterval(1).setShouldReceiveVelocityUpdates(true).size(0.45F, 0.45F).build(prefix("fakethrown_rider"));
    public static final EntityType<SavagelingEntity> SAVAGELING = EntityType.Builder.create(SavagelingEntity::new, EntityClassification.MONSTER).setTrackingRange(80).setUpdateInterval(3).setShouldReceiveVelocityUpdates(true).size(0.6F, 0.75F).build(prefix("savageling"));
    public static final EntityType<SkeletonVillagerEntity> SKELETONVILLAGER = EntityType.Builder.create(SkeletonVillagerEntity::new, EntityClassification.MONSTER).setTrackingRange(80).setUpdateInterval(3).setShouldReceiveVelocityUpdates(true).size(0.6F, 1.95F).build(prefix("skeleton_villager"));
    public static final EntityType<ScavengersEntity> SCAVENGER = EntityType.Builder.create(ScavengersEntity::new, EntityClassification.MONSTER).setTrackingRange(80).setUpdateInterval(3).setShouldReceiveVelocityUpdates(true).size(0.6F, 1.95F).build(prefix("scavenger"));
    public static final EntityType<FriendlyRavagerEntity> FRIENDLYRAVAGER = EntityType.Builder.create(FriendlyRavagerEntity:: new, EntityClassification.CREATURE).setTrackingRange(80).setUpdateInterval(3).setShouldReceiveVelocityUpdates(true).size(1.95F,2.2F).build(prefix("friendly_ravager"));
    public static final EntityType<BeastBrewEntity> BEAST_BREW = EntityType.Builder.<BeastBrewEntity>create(BeastBrewEntity::new, EntityClassification.MISC).setTrackingRange(100).setUpdateInterval(3).setShouldReceiveVelocityUpdates(true).setCustomClientFactory(BeastBrewEntity::new).size(0.3F, 0.3F).build(prefix("beast_brew"));
    public static final EntityType<HyenaEntity> HYENA = EntityType.Builder.create(HyenaEntity::new, EntityClassification.CREATURE).setTrackingRange(80).setUpdateInterval(3).setShouldReceiveVelocityUpdates(true).size(0.8F, 1.1F).build(prefix("hyena"));


    private static String prefix(String path) {
		return MODID + "." + path;
	}

    @SubscribeEvent
    public static void registerEntity(RegistryEvent.Register<EntityType<?>> event) {
        event.getRegistry().register(DEFENDER.setRegistryName("defender"));
        event.getRegistry().register(CREEPIES.setRegistryName("creepie"));
        event.getRegistry().register(GRIEFER_ILLAGER.setRegistryName("griefer_illager"));
        event.getRegistry().register(CREEPER_SPORE.setRegistryName("creeper_spore"));
        event.getRegistry().register(POULTRY_FARMER.setRegistryName("poultry_farmer"));
        event.getRegistry().register(FAKE_THROWN_RIDER.setRegistryName("fakethrown_rider"));
        event.getRegistry().register(SAVAGELING.setRegistryName("savageling"));
        event.getRegistry().register(SKELETONVILLAGER.setRegistryName("skeleton_villager"));
        event.getRegistry().register(SCAVENGER.setRegistryName("scavenger"));
        event.getRegistry().register(FRIENDLYRAVAGER.setRegistryName("friendly_ravager"));
        event.getRegistry().register(BEAST_BREW.setRegistryName("beast_brew"));
        event.getRegistry().register(HYENA.setRegistryName("hyena"));
    }

    public static void addEntitySpawn() {
    }
}