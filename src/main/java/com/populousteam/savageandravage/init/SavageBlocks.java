package com.populousteam.savageandravage.init;

import com.populousteam.savageandravage.SavageAndRavage;
import com.populousteam.savageandravage.block.SavageStairsBlock;
import com.populousteam.savageandravage.block.SavageVerticalSlabBlock;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SavageAndRavage.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SavageBlocks {

    public static final Block GLOOMY_TILES = new Block(Block.Properties.from(Blocks.STONE_BRICKS));
    public static final Block GLOOMY_TILE_SLAB = new SlabBlock(Block.Properties.from(Blocks.STONE_BRICKS));
    public static final Block GLOOMY_TILE_STAIRS = new SavageStairsBlock(Blocks.STONE_BRICKS.getDefaultState(), Block.Properties.from(Blocks.STONE_BRICK_STAIRS));
    public static final Block GLOOMY_TILE_WALL = new WallBlock(Block.Properties.from(Blocks.STONE_BRICKS));
    public static final Block GLOOMY_TILE_VERTICAL_SLAB = new SavageVerticalSlabBlock(Block.Properties.from(Blocks.STONE_BRICKS));
    public static final Block CHISELED_GLOOMY_TILES = new Block(Block.Properties.from(Blocks.STONE_BRICKS));
    //public static final Block RUNED_GLOOMY_TILES = new RunedGloomyTileBlock(Block.Properties.from(Blocks.STONE_BRICKS));
    //TODO: Add this back when implementing entities
    public static final Block CREEPER_SPORE_SACK = new Block(Block.Properties.create(Material.WOOL).hardnessAndResistance(0.5F).sound(SoundType.CLOTH));
    @SubscribeEvent
        public static void registerBlocks(RegistryEvent.Register<Block> registry) {
        registry.getRegistry().register(GLOOMY_TILES.setRegistryName("gloomy_tiles"));
        registry.getRegistry().register(GLOOMY_TILE_SLAB.setRegistryName("gloomy_tile_slab"));
        registry.getRegistry().register(GLOOMY_TILE_STAIRS.setRegistryName("gloomy_tile_stairs"));
        registry.getRegistry().register(GLOOMY_TILE_WALL.setRegistryName("gloomy_tile_wall"));
        if(ModList.get().isLoaded("quark")) {
            registry.getRegistry().register(GLOOMY_TILE_VERTICAL_SLAB.setRegistryName("gloomy_tile_vertical_slab"));
        }
        registry.getRegistry().register(CHISELED_GLOOMY_TILES.setRegistryName("chiseled_gloomy_tiles"));
        if(ModList.get().isLoaded("quark")) {
            registry.getRegistry().register(CREEPER_SPORE_SACK.setRegistryName("creeper_spore_sack"));
        }
    }

    @SubscribeEvent
    public static void registerItemBlocks(RegistryEvent.Register<Item> registry) {
        SavageItems.register(registry, new BlockItem(GLOOMY_TILES, (new Item.Properties()).group(ItemGroup.BUILDING_BLOCKS)));
        SavageItems.register(registry, new BlockItem(GLOOMY_TILE_SLAB, (new Item.Properties()).group(ItemGroup.BUILDING_BLOCKS)));
        SavageItems.register(registry, new BlockItem(GLOOMY_TILE_STAIRS, (new Item.Properties()).group(ItemGroup.BUILDING_BLOCKS)));
        SavageItems.register(registry, new BlockItem(GLOOMY_TILE_WALL, (new Item.Properties()).group(ItemGroup.BUILDING_BLOCKS)));
        if(ModList.get().isLoaded("quark")) {
            SavageItems.register(registry, new BlockItem(GLOOMY_TILE_VERTICAL_SLAB, (new Item.Properties().group(ItemGroup.BUILDING_BLOCKS))));
        }
        SavageItems.register(registry, new BlockItem(CHISELED_GLOOMY_TILES, (new Item.Properties()).group(ItemGroup.BUILDING_BLOCKS)));
        if(ModList.get().isLoaded("quark")) {
            SavageItems.register(registry, new BlockItem(CREEPER_SPORE_SACK, (new Item.Properties().group(ItemGroup.DECORATIONS))));
        }
    }
}