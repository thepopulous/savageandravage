package populousteam.savageandravage.init;

import populousteam.savageandravage.SavageAndRavageCore;
import populousteam.savageandravage.world.structure.PoultryHousePieces;
import populousteam.savageandravage.world.structure.PoultryHouseStructure;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = SavageAndRavageCore.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SavageFeatures {
    public static final Structure<NoFeatureConfig> POULTRYHOUSE = new PoultryHouseStructure(NoFeatureConfig::deserialize);

    public static final IStructurePieceType POULTRYHOUSE_STRUCTURE = IStructurePieceType.register(PoultryHousePieces.Piece::new, "PHS");

    @SubscribeEvent
    public static void registerStructure(RegistryEvent.Register<Feature<?>> event) {
        event.getRegistry().register(POULTRYHOUSE.setRegistryName(SavageAndRavageCore.MODID, "poultry_house"));
    }

    public static void addStructureFeature() {
        ForgeRegistries.BIOMES.getValues().stream().forEach((biome -> {
            biome.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Biome.createDecoratedFeature(SavageFeatures.POULTRYHOUSE, IFeatureConfig.NO_FEATURE_CONFIG, Placement.NOPE, IPlacementConfig.NO_PLACEMENT_CONFIG));

            if (biome.getCategory() == Biome.Category.SAVANNA || biome.getCategory() == Biome.Category.PLAINS) {
                biome.addStructure(SavageFeatures.POULTRYHOUSE, IFeatureConfig.NO_FEATURE_CONFIG);
            }
            if (biome.hasStructure(Feature.PILLAGER_OUTPOST)) {
                Feature.PILLAGER_OUTPOST.getSpawnList().add(new Biome.SpawnListEntry(SavageEntityRegistry.DEFENDER, 1, 1, 1));
            }

        }));
    }
}