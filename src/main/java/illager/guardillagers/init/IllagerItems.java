package illager.guardillagers.init;

import com.google.common.base.Preconditions;
import illager.guardillagers.GuardIllagers;
import illager.guardillagers.item.ItemGuardHelm;
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
import net.minecraftforge.registries.ObjectHolder;

import java.util.Collections;
import java.util.List;

@Mod.EventBusSubscriber(modid = GuardIllagers.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(GuardIllagers.MODID)
public class IllagerItems {
    private static final NonNullList<Item> ITEMS = NonNullList.create();


	public static final Item GUARD_ILLAGER_EGG = new SpawnEggItem(IllagerEntityRegistry.GUARD_ILLAGER, 9804699, 0x879C9B, (new Item.Properties()).group(ItemGroup.MISC));
	public static final Item GUARD_HELM = new ItemGuardHelm(IllagersArmorMaterial.GUARD_HELM, EquipmentSlotType.HEAD, (new Item.Properties()).group(ItemGroup.COMBAT));

    public static List<Item> getItems() {
        return Collections.unmodifiableList(ITEMS);
    }


    public static void register(RegistryEvent.Register<Item> registry, Item item, String id) {
        ITEMS.add(item);

	    if (item instanceof BlockItem && item.getRegistryName() == null) {
		    item.setRegistryName(((BlockItem) item).getBlock().getRegistryName());
        }

        item.setRegistryName(new ResourceLocation(GuardIllagers.MODID, id));
        Preconditions.checkNotNull(item, "registryName");
        registry.getRegistry().register(item);
    }


    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> registry) {

        register(registry, GUARD_ILLAGER_EGG, "guard_illager_egg");
        register(registry, GUARD_HELM, "guard_helm");
    }

}
