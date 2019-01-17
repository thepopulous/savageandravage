package illager.illagersthanvillagers.init;

import javafx.geometry.Side;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.NonNullList;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.Collections;
import java.util.List;

public class IllagerItems {
    private static final NonNullList<Item> ITEMS = NonNullList.create();


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

    }

    @OnlyIn(Dist.CLIENT)
    public static void registerModels() {

    }
}
