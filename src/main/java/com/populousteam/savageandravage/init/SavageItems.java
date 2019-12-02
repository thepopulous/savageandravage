package com.populousteam.savageandravage.init;

import com.populousteam.savageandravage.SavageAndRavage;
import com.populousteam.savageandravage.item.GuardHelmItem;
import com.populousteam.savageandravage.item.PoultryFarmerHatItem;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SavageAndRavage.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SavageItems {

    public static final Item GUARD_HELM = new GuardHelmItem(SavageArmorMaterial.GUARD_HELM, EquipmentSlotType.HEAD, ((new Item.Properties()).group(ItemGroup.COMBAT)));
    public static final Item CREEPER_SPORES = new Item /*CreeperSporeItem*/((new Item.Properties()).group(ItemGroup.MISC));
    public static final Item POULTRY_FARMER_HAT = new PoultryFarmerHatItem(SavageArmorMaterial.POULTRY_FARMER_HAT, EquipmentSlotType.HEAD, (new Item.Properties()).group(ItemGroup.TOOLS));
    public static final Item SINISTER_HORN = new Item /*SinisterHornItem*/((new Item.Properties()).group(ItemGroup.COMBAT).maxStackSize(1).maxDamage(1));
    public static final Item BESTIAL_BREW = new Item /*BestialBrewItem*/((new Item.Properties()).group(ItemGroup.MISC).maxStackSize(16));
    public static final Item DEFENDER_SPAWN_EGG = new SpawnEggItem(SavageEntities.DEFENDER, 9804699, 0x879C9B, (new Item.Properties()).group(ItemGroup.MISC));

    public static void register(RegistryEvent.Register<Item> registry, Item item, String id) {
        if (item instanceof BlockItem){
            Item.BLOCK_TO_ITEM.put(((BlockItem) item).getBlock(), item);
        }

        item.setRegistryName(new ResourceLocation(SavageAndRavage.MOD_ID, id));

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
        register(registry, GUARD_HELM, "guard_helm");
        register(registry, POULTRY_FARMER_HAT, "poultry_farmer_hat");
        register(registry, CREEPER_SPORES, "creeper_spores");
        register(registry, SINISTER_HORN, "sinister_horn");
        register(registry, BESTIAL_BREW, "bestial_brew");
        register(registry, DEFENDER_SPAWN_EGG, "defender_spawn_egg");
    }

}