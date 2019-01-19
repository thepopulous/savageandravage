package illager.guardillagers.init;

import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemSpawnEgg;
import net.minecraft.util.NonNullList;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.Collections;
import java.util.List;

public class IllagerItems {

    private static final NonNullList<Item> ITEMS = NonNullList.create();

    public static final Item GUARD_ILLAGER_EGG = new ItemSpawnEgg(IllagerEntityRegistry.GUARD_ILLAGER, 9804699, 2580065, (new Item.Builder()).group(ItemGroup.MISC));

    public static List<Item> getItems() {
        return Collections.unmodifiableList(ITEMS);
    }


    public static void register(IForgeRegistry<Item> registry, Item item) {
        ITEMS.add(item);

        if (item instanceof ItemBlock && item.getRegistryName() == null) {
            item.setRegistryName(((ItemBlock) item).getBlock().getRegistryName());
        }

        registry.register(item);
    }

    public static void registerItems(IForgeRegistry<Item> registry) {
        register(registry, GUARD_ILLAGER_EGG.setRegistryName("guard_illager_egg"));
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerModels() {

    }

}
