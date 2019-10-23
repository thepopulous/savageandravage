package populousteam.savageandravage.init;

import com.google.common.base.Preconditions;
import net.minecraftforge.registries.ObjectHolder;
import populousteam.savageandravage.SavageAndRavageCore;
import populousteam.savageandravage.entity.projectile.CreeperSporeEntity;
import populousteam.savageandravage.item.*;
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
import net.minecraftforge.event.RegistryEvent.Register;
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
    public static final Item BEASTBREW = new BeastBrewItem((new Item.Properties()).group(ItemGroup.MISC).maxStackSize(16));

    public static final Item GRIEFER_SPAWN_EGG = new SpawnEggItem(SavageEntityRegistry.GRIEFER_ILLAGER, 9804699, 0x403e43, (new Item.Properties()).group(ItemGroup.MISC));
    public static final Item POULTRY_FARMER_SPAWN_EGG = new SpawnEggItem(SavageEntityRegistry.POULTRY_FARMER, 9804699, 0x70794e, (new Item.Properties()).group(ItemGroup.MISC));
    public static final Item SAVAGELING_SPAWN_EGG = new SpawnEggItem(SavageEntityRegistry.SAVAGELING, 9804699, 9804690, (new Item.Properties()).group(ItemGroup.MISC));
    public static final Item SKELETON_VILLAGER_SPAWN_EGG = new SpawnEggItem(SavageEntityRegistry.SKELETONVILLAGER, 12698049, 4802889, (new Item.Properties()).group(ItemGroup.MISC));
    public static final Item SCAVENGER_SPAWN_EGG = new SpawnEggItem(SavageEntityRegistry.SCAVENGER, 9804699, 0xc2c0b7, (new Item.Properties()).group(ItemGroup.MISC));
    public static final Item FRIENDLYRAVAGER_SPAWN_EGG = new SpawnEggItem(SavageEntityRegistry.FRIENDLYRAVAGER, 12698049, 0xc2c0b7, (new Item.Properties()).group(ItemGroup.MISC));
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
        register(registry, DEFENDER_SPAWN_EGG, "defender_spawn_egg");
        register(registry, GUARD_HELM, "guard_helm");
        register(registry, CREEPER_SPORES, "creeper_spores");
        register(registry, POULTRY_FARMER_HAT, "poultry_farmer_hat");
        register(registry, SINISTERHORN, "sinister_horn");
        register(registry, BEASTBREW, "brew_of_the_beast");
        register(registry, GRIEFER_SPAWN_EGG, "griefer_spawn_egg");
        register(registry, POULTRY_FARMER_SPAWN_EGG, "poultry_farmer_spawn_egg");
        register(registry, SCAVENGER_SPAWN_EGG, "scavenger_spawn_egg");
        register(registry, SAVAGELING_SPAWN_EGG, "savageling_spawn_egg");
        register(registry, SKELETON_VILLAGER_SPAWN_EGG, "skeleton_villager_spawn_egg");
        register(registry, FRIENDLYRAVAGER_SPAWN_EGG, "friendly_ravager_spawn_egg");
        register(registry, HYENA_SPAWN_EGG, "hyena_spawn_egg");

        DispenserBlock.registerDispenseBehavior(CREEPER_SPORES, new ProjectileDispenseBehavior() {
            protected IProjectile getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn) {
                return Util.make(new CreeperSporeEntity(worldIn, position.getX(), position.getY(), position.getZ()), (p_218409_1_) -> {
                });
            }
        });
    }

}
