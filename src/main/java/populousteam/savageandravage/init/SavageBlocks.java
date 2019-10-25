package populousteam.savageandravage.init;

import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import populousteam.savageandravage.SavageAndRavageCore;
import populousteam.savageandravage.SavageConfig;
import populousteam.savageandravage.block.SavageStairsBlock;

@Mod.EventBusSubscriber(modid = SavageAndRavageCore.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SavageBlocks {

    public static final Block GLOOMY_TILES = new Block(Block.Properties.from(Blocks.STONE_BRICKS));
    public static final Block GLOOMY_TILE_SLAB = new SlabBlock(Block.Properties.from(Blocks.STONE_BRICK_SLAB));
    public static final Block GLOOMY_TILE_STAIRS = new SavageStairsBlock(Blocks.STONE_BRICK_STAIRS.getDefaultState(), Block.Properties.from(Blocks.STONE_BRICK_STAIRS));
    public static final Block GLOOMY_TILE_WALL = new WallBlock(Block.Properties.from(Blocks.STONE_BRICK_WALL));
    //public static final Block GLOOMY_TILE_VERTICAL_SLAB = new vazkii.quark.building.block.VerticalSlabBlock(Blocks.STONE_BRICKS.getDefaultState(), Block.Properties.from(Blocks.STONE_BRICKS));
    //TODO: Work out how to properly implement this class as I don't think you can do it from mod jars
    public static final Block CHISELED_GLOOMY_TILES = new Block(Block.Properties.from(Blocks.CHISELED_STONE_BRICKS));

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> registry){
        if(SavageConfig.GLOOMY_TILES.get()) {
            registry.getRegistry().register(GLOOMY_TILES.setRegistryName("gloomy_tiles"));
            registry.getRegistry().register(GLOOMY_TILE_SLAB.setRegistryName("gloomy_tile_slab"));
            registry.getRegistry().register(GLOOMY_TILE_STAIRS.setRegistryName("gloomy_tile_stairs"));
            registry.getRegistry().register(GLOOMY_TILE_WALL.setRegistryName("gloomy_tile_wall"));
            registry.getRegistry().register(CHISELED_GLOOMY_TILES.setRegistryName("chiseled_gloomy_tiles"));
        }

    }

    @SubscribeEvent
    public static void registerItemBlocks(RegistryEvent.Register<Item> registry){
        if(SavageConfig.GLOOMY_TILES.get()) {
            SavageItems.register(registry, new BlockItem(GLOOMY_TILES, (new Item.Properties()).group(ItemGroup.BUILDING_BLOCKS)));
            SavageItems.register(registry, new BlockItem(GLOOMY_TILE_SLAB, (new Item.Properties()).group(ItemGroup.BUILDING_BLOCKS)));
            SavageItems.register(registry, new BlockItem(GLOOMY_TILE_STAIRS, (new Item.Properties()).group(ItemGroup.BUILDING_BLOCKS)));
            SavageItems.register(registry, new BlockItem(GLOOMY_TILE_WALL, (new Item.Properties()).group(ItemGroup.BUILDING_BLOCKS)));
            SavageItems.register(registry, new BlockItem(CHISELED_GLOOMY_TILES, (new Item.Properties()).group(ItemGroup.BUILDING_BLOCKS)));
        }
    }
}