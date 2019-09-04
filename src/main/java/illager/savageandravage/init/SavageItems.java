package illager.savageandravage.init;

import com.google.common.base.Preconditions;
import illager.savageandravage.SavageAndRavageCore;
import illager.savageandravage.item.CreeperSporeItem;
import illager.savageandravage.item.ItemGuardHelm;
import illager.savageandravage.item.PoultryFarmerHatItem;
import illager.savageandravage.item.SinisterHornItem;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Collections;
import java.util.List;

@Mod.EventBusSubscriber(modid = SavageAndRavageCore.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SavageItems {
    private static final NonNullList<Item> ITEMS = NonNullList.create();


    public static final Item DEFENDER_SPAWNEGG = new SpawnEggItem(SavageEntityRegistry.DEFENDER, 9804699, 0x879C9B, (new Item.Properties()).group(ItemGroup.MISC));
    public static final Item GUARD_HELM = new ItemGuardHelm(SavageArmorMaterial.GUARD_HELM, EquipmentSlotType.HEAD, (new Item.Properties()).group(ItemGroup.COMBAT));
    public static final Item CREEPER_SPORES = new CreeperSporeItem((new Item.Properties()).group(ItemGroup.MISC));
    public static final Item POULTRY_FARMER_HAT = new PoultryFarmerHatItem(SavageArmorMaterial.POULTRY_FARMER_HAT, EquipmentSlotType.HEAD, (new Item.Properties()).group(ItemGroup.TOOLS));
    public static final Item SINISTERHORN = new SinisterHornItem((new Item.Properties()).group(ItemGroup.COMBAT).maxStackSize(1));
    public static final Item BEASTBREW = new BeastBrewItem((new Item.Properties()).group(ItemGroup.MISC).maxStackSize(16));

    public static final Item GRIEFER_SPAWNEGG = new SpawnEggItem(SavageEntityRegistry.GRIEFER_ILLAGER, 9804699, 0x403e43, (new Item.Properties()).group(ItemGroup.MISC));
    public static final Item POULTRYFARMER_SPAWNEGG = new SpawnEggItem(SavageEntityRegistry.POULTRY_FARMER, 9804699, 0x70794e, (new Item.Properties()).group(ItemGroup.MISC));
    public static final Item SAVAGELING_SPAWNEGG = new SpawnEggItem(SavageEntityRegistry.SAVAGELING, 9804699, 9804690, (new Item.Properties()).group(ItemGroup.MISC));
    public static final Item SKELETONVILLAGER_SPAWNEGG = new SpawnEggItem(SavageEntityRegistry.SKELETONVILLAGER, 12698049, 4802889, (new Item.Properties()).group(ItemGroup.MISC));
    public static final Item SCAVENGER_SPAWNEGG = new SpawnEggItem(SavageEntityRegistry.SCAVENGER, 9804699, 0xc2c0b7, (new Item.Properties()).group(ItemGroup.MISC));
    public static final Item FRIENDLYRAVAGER_SPAWNEGG = new SpawnEggItem(SavageEntityRegistry.FRIENDLYRAVAGER, 12698049, 0xc2c0b7, (new Item.Properties()).group(ItemGroup.MISC));


    public static List<Item> getItems() {
        return Collections.unmodifiableList(ITEMS);
    }


    public static void register(RegistryEvent.Register<Item> registry, Item item, String id) {
        ITEMS.add(item);

	    if (item instanceof BlockItem && item.getRegistryName() == null) {
		    item.setRegistryName(((BlockItem) item).getBlock().getRegistryName());
        }

        item.setRegistryName(new ResourceLocation(SavageAndRavageCore.MODID, id));
        Preconditions.checkNotNull(item, "registryName");
        registry.getRegistry().register(item);
    }


    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> registry) {
        register(registry, DEFENDER_SPAWNEGG, "defender_spawn_egg");
        register(registry, GUARD_HELM, "guard_helm");
        register(registry, CREEPER_SPORES, "creeper_spores");
        register(registry, POULTRY_FARMER_HAT, "poultry_farmer_hat");
        register(registry, SINISTERHORN, "sinister_horn");
        register(registry, BEASTBREW, "brew_of_the_beast");
        register(registry, GRIEFER_SPAWNEGG, "griefer_spawnegg");
        register(registry, POULTRYFARMER_SPAWNEGG, "poultryfarmer_spawnegg");
        register(registry, SCAVENGER_SPAWNEGG, "scavenger_spawnegg");
        register(registry, SAVAGELING_SPAWNEGG, "savageling_spawnegg");
        register(registry, SKELETONVILLAGER_SPAWNEGG, "skeletonvillager_spawnegg");
        register(registry, FRIENDLYRAVAGER_SPAWNEGG, "friendlyravager_spawnegg");
    }

}
