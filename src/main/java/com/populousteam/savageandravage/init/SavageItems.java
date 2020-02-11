package com.populousteam.savageandravage.init;

import com.populousteam.savageandravage.SavageAndRavageCore;
import com.populousteam.savageandravage.SavageConfig;
import com.populousteam.savageandravage.entity.projectile.CreeperSporeEntity;
import com.populousteam.savageandravage.item.*;
import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.IPosition;
import net.minecraft.dispenser.ProjectileDispenseBehavior;
import net.minecraft.entity.IProjectile;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Collections;
import java.util.List;

@Mod.EventBusSubscriber(modid = SavageAndRavageCore.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SavageItems {
    private static final NonNullList<Item> ITEMS = NonNullList.create();
    //TODO delete this and code that uses it, but only after making sure it doesn't break everything

    public static final Item DEFENDER_SPAWN_EGG = new SpawnEggItem(SavageEntityRegistry.DEFENDER, 9804699, 0x879C9B, (new Item.Properties()).group(ItemGroup.MISC));
    public static final Item GUARD_HELM = new ItemGuardHelm(SavageArmorMaterial.GUARD_HELM, EquipmentSlotType.HEAD, (new Item.Properties()).group(ItemGroup.COMBAT));
    public static final Item CREEPER_SPORES = new CreeperSporeItem((new Item.Properties()).group(ItemGroup.MISC));
    public static final Item POULTRY_FARMER_HAT = new PoultryFarmerHatItem(SavageArmorMaterial.POULTRY_FARMER_HAT, EquipmentSlotType.HEAD, (new Item.Properties()).group(ItemGroup.TOOLS));
    public static final Item SINISTERHORN = new SinisterHornItem((new Item.Properties()).group(ItemGroup.COMBAT).maxStackSize(1).maxDamage(1));
    public static final Item BESTIAL_BREW = new BestialBrewItem((new Item.Properties()).group(ItemGroup.MISC).maxStackSize(16));

    public static final Item GRIEFER_SPAWN_EGG = new SpawnEggItem(SavageEntityRegistry.GRIEFER_ILLAGER, 9804699, 0x403e43, (new Item.Properties()).group(ItemGroup.MISC));
    public static final Item POULTRY_FARMER_SPAWN_EGG = new SpawnEggItem(SavageEntityRegistry.POULTRY_FARMER, 9804699, 0x70794e, (new Item.Properties()).group(ItemGroup.MISC));
    public static final Item SAVAGELING_SPAWN_EGG = new SpawnEggItem(SavageEntityRegistry.SAVAGELING, 9804699, 9804690, (new Item.Properties()).group(ItemGroup.MISC));
    public static final Item SKELETON_VILLAGER_SPAWN_EGG = new SpawnEggItem(SavageEntityRegistry.SKELETONVILLAGER, 12698049, 4802889, (new Item.Properties()).group(ItemGroup.MISC));
    public static final Item SCAVENGER_SPAWN_EGG = new SpawnEggItem(SavageEntityRegistry.SCAVENGER, 9804699, 0xc2c0b7, (new Item.Properties()).group(ItemGroup.MISC));
    public static final Item FRIENDLY_RAVAGER_SPAWN_EGG = new SpawnEggItem(SavageEntityRegistry.FRIENDLY_RAVAGER, 12698049, 0xc2c0b7, (new Item.Properties()).group(ItemGroup.MISC));
    public static final Item HYENA_SPAWN_EGG = new SpawnEggItem(SavageEntityRegistry.HYENA, 0x4f453d, 0x64453d, (new Item.Properties()).group(ItemGroup.MISC));

    public static List<Item> getItems() {
        return Collections.unmodifiableList(ITEMS);
    }


    public static void register(RegistryEvent.Register<Item> registry, Item item, String id) {
        if (item instanceof BlockItem){
            Item.BLOCK_TO_ITEM.put(((BlockItem) item).getBlock(), item);
        }

        item.setRegistryName(new ResourceLocation(SavageAndRavageCore.MODID, id));

        registry.getRegistry().register(item);
    }

    public static void register(RegistryEvent.Register<Item> registry, Item item) {

        if (item instanceof BlockItem && item.getRegistryName() == null) {
            item.setRegistryName(((BlockItem) item).getBlock().getRegistryName());

            Item.BLOCK_TO_ITEM.put(((BlockItem) item).getBlock(), item);
        }

        registry.getRegistry().register(item);
    }


    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> registry) {
        if (SavageConfig.DEFENDERS.get()) {
            register(registry, DEFENDER_SPAWN_EGG, "defender_spawn_egg");
        }
        if (SavageConfig.GUARD_HAT.get()) {
            register(registry, GUARD_HELM, "guard_helm");
        }
        if (SavageConfig.CREEPER_SPORES.get()) {
            register(registry, CREEPER_SPORES, "creeper_spores");
        }
        if (SavageConfig.SINISTER_HORN.get()) {
            register(registry, SINISTERHORN, "sinister_horn");
        }
        if (SavageConfig.BESTIAL_BREW.get()) {
            register(registry, BESTIAL_BREW, "bestial_brew");
        }
        if (SavageConfig.GRIEFERS.get()) {
            register(registry, GRIEFER_SPAWN_EGG, "griefer_spawn_egg");
        }
        if (SavageConfig.POULTRY_FARMERS.get()) {
            register(registry, POULTRY_FARMER_SPAWN_EGG, "poultry_farmer_spawn_egg");
        }
        if (SavageConfig.POULTRY_FARMER_HAT.get()) {
            register(registry, POULTRY_FARMER_HAT, "poultry_farmer_hat");
        }
        if (SavageConfig.SCAVENGERS.get()) {
            register(registry, SCAVENGER_SPAWN_EGG, "scavenger_spawn_egg");
        }
        if (SavageConfig.SAVAGELINGS.get()) {
            register(registry, SAVAGELING_SPAWN_EGG, "savageling_spawn_egg");
        }
        if (SavageConfig.SKELETON_VILLAGERS.get()) {
            register(registry, SKELETON_VILLAGER_SPAWN_EGG, "skeleton_villager_spawn_egg");
        }
        if (SavageConfig.FRIENDLY_RAVAGERS.get()) {
            register(registry, FRIENDLY_RAVAGER_SPAWN_EGG, "friendly_ravager_spawn_egg");
        }
        if (SavageConfig.HYENAS.get()) {
            register(registry, HYENA_SPAWN_EGG, "hyena_spawn_egg");
        }

        DispenserBlock.registerDispenseBehavior(CREEPER_SPORES, new ProjectileDispenseBehavior() {
            protected IProjectile getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn) {
                return Util.make(new CreeperSporeEntity(worldIn, position.getX(), position.getY(), position.getZ()), (p_218409_1_) -> {
                });
            }
        });
    }

}
