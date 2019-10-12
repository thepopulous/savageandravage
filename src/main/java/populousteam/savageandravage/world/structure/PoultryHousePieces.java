package populousteam.savageandravage.world.structure;

import com.google.common.collect.ImmutableMap;
import populousteam.savageandravage.SavageAndRavageCore;
import populousteam.savageandravage.entity.SavagelingEntity;
import populousteam.savageandravage.entity.illager.PoultryFarmerIllagerEntity;
import populousteam.savageandravage.init.SavageEntityRegistry;
import populousteam.savageandravage.init.SavageFeatures;
import populousteam.savageandravage.init.SavageLootTables;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.BarrelTileEntity;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.structure.TemplateStructurePiece;
import net.minecraft.world.gen.feature.template.BlockIgnoreStructureProcessor;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class PoultryHousePieces {
    private static final ResourceLocation poultry_house = new ResourceLocation(SavageAndRavageCore.MODID, "poultry_house/illager_farmer_house");
    private static final ResourceLocation poultry_house_savageling = new ResourceLocation(SavageAndRavageCore.MODID, "poultry_house/illager_farmer_house_savageling");
    private static final ResourceLocation poultry_house_savanna = new ResourceLocation(SavageAndRavageCore.MODID, "poultry_house/illager_farmer_house_savanna");
    private static final ResourceLocation poultry_house_savageling_savanna = new ResourceLocation(SavageAndRavageCore.MODID, "poultry_house/illager_farmer_house_savageling_savanna");

    private static final ResourceLocation bigpoutry_farm = new ResourceLocation(SavageAndRavageCore.MODID, "poultry_house/illager_farmer_crops");

    private static final Map<ResourceLocation, BlockPos> structurePos = ImmutableMap.of(poultry_house, BlockPos.ZERO, poultry_house_savageling, new BlockPos(0, -5, 0), poultry_house_savanna, BlockPos.ZERO, poultry_house_savageling_savanna, new BlockPos(0, -5, 0), bigpoutry_farm, new BlockPos(-4, 0, 14));

    public static void addStructure(TemplateManager p_207617_0_, BlockPos p_207617_1_, Rotation p_207617_2_, List<StructurePiece> p_207617_3_, Random p_207617_4_, Biome biome) {
        if (biome.getCategory() == Biome.Category.SAVANNA) {
            if (p_207617_4_.nextDouble() < 0.3D) {
                p_207617_3_.add(new PoultryHousePieces.Piece(p_207617_0_, poultry_house_savageling_savanna, p_207617_1_, p_207617_2_, 0));
            } else {
                p_207617_3_.add(new PoultryHousePieces.Piece(p_207617_0_, poultry_house_savanna, p_207617_1_, p_207617_2_, 0));
            }
        } else {
            if (p_207617_4_.nextDouble() < 0.3D) {
                p_207617_3_.add(new PoultryHousePieces.Piece(p_207617_0_, poultry_house_savageling, p_207617_1_, p_207617_2_, 0));
            } else {
                p_207617_3_.add(new PoultryHousePieces.Piece(p_207617_0_, poultry_house, p_207617_1_, p_207617_2_, 0));
            }
        }
        p_207617_3_.add(new PoultryHousePieces.Piece(p_207617_0_, bigpoutry_farm, p_207617_1_, p_207617_2_, 0));
    }


    public static class Piece extends TemplateStructurePiece {
        private final ResourceLocation field_207615_d;
        private final Rotation field_207616_e;

        public Piece(TemplateManager p_i49313_1_, ResourceLocation p_i49313_2_, BlockPos p_i49313_3_, Rotation p_i49313_4_, int p_i49313_5_) {
            super(SavageFeatures.POULTRYHOUSE_STRUCTURE, 0);
            this.field_207615_d = p_i49313_2_;
            BlockPos blockpos = PoultryHousePieces.structurePos.get(p_i49313_2_);
            this.templatePosition = p_i49313_3_.add(blockpos.getX(), blockpos.getY() - p_i49313_5_, blockpos.getZ());
            this.field_207616_e = p_i49313_4_;
            this.func_207614_a(p_i49313_1_);
        }

        public Piece(TemplateManager p_i50566_1_, CompoundNBT p_i50566_2_) {
            super(SavageFeatures.POULTRYHOUSE_STRUCTURE, p_i50566_2_);
            this.field_207615_d = new ResourceLocation(p_i50566_2_.getString("Template"));
            this.field_207616_e = Rotation.valueOf(p_i50566_2_.getString("Rot"));
            this.func_207614_a(p_i50566_1_);
        }

        private void func_207614_a(TemplateManager p_207614_1_) {
            Template template = p_207614_1_.getTemplateDefaulted(this.field_207615_d);
            PlacementSettings placementsettings = (new PlacementSettings()).setRotation(this.field_207616_e).setMirror(Mirror.NONE).setCenterOffset(PoultryHousePieces.structurePos.get(this.field_207615_d)).addProcessor(BlockIgnoreStructureProcessor.STRUCTURE_BLOCK);
            this.setup(template, this.templatePosition, placementsettings);
        }

        /**
         * (abstract) Helper method to read subclass data from NBT
         */
        protected void readAdditional(CompoundNBT tagCompound) {
            super.readAdditional(tagCompound);
            tagCompound.putString("Template", this.field_207615_d.toString());
            tagCompound.putString("Rot", this.field_207616_e.name());
        }

        protected void handleDataMarker(String function, BlockPos pos, IWorld worldIn, Random rand, MutableBoundingBox sbb) {
            if ("Illager".equals(function)) {
                worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
                PoultryFarmerIllagerEntity illager = SavageEntityRegistry.POULTRY_FARMER.create(worldIn.getWorld());
                illager.setPosition((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D);
                illager.enablePersistence();
                illager.setIllagerHome(pos);
                illager.onInitialSpawn(worldIn, worldIn.getDifficultyForLocation(pos), SpawnReason.STRUCTURE, (ILivingEntityData) null, (CompoundNBT) null);
                worldIn.addEntity(illager);
            } else if ("Chicken".equals(function)) {
                worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
                ChickenEntity chicken = EntityType.CHICKEN.create(worldIn.getWorld());
                chicken.setPosition((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D);
                chicken.enablePersistence();
                chicken.onInitialSpawn(worldIn, worldIn.getDifficultyForLocation(pos), SpawnReason.STRUCTURE, (ILivingEntityData) null, (CompoundNBT) null);
                worldIn.addEntity(chicken);
            } else if ("Savageling".equals(function)) {
                worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
                SavagelingEntity savageling = SavageEntityRegistry.SAVAGELING.create(worldIn.getWorld());
                savageling.setPosition((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D);
                savageling.enablePersistence();
                savageling.onInitialSpawn(worldIn, worldIn.getDifficultyForLocation(pos), SpawnReason.STRUCTURE, (ILivingEntityData) null, (CompoundNBT) null);
                worldIn.addEntity(savageling);
            } else if ("CropChest".equals(function)) {
                worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
                TileEntity tileentity = worldIn.getTileEntity(pos.down());
                if (tileentity instanceof ChestTileEntity) {
                    ((ChestTileEntity) tileentity).setLootTable(SavageLootTables.POULTRY_FARMER_CROP_CHEST, rand.nextLong());
                }
            } else if ("FoodBarrel".equals(function)) {
                worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
                TileEntity tileentity = worldIn.getTileEntity(pos.down());
                if (tileentity instanceof BarrelTileEntity) {
                    ((BarrelTileEntity) tileentity).setLootTable(SavageLootTables.POULTRY_HOUSE_FOOD_CHEST, rand.nextLong());
                }
            } else if ("PreciousChest".equals(function)) {
                worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
                TileEntity tileentity = worldIn.getTileEntity(pos.down());
                if (tileentity instanceof ChestTileEntity) {
                    ((ChestTileEntity) tileentity).setLootTable(SavageLootTables.POULTRY_HOUSE_PRECIOUS_CHEST, rand.nextLong());
                }
            }
        }

        /**
         * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes Mineshafts at
         * the end, it adds Fences...
         */
        public boolean addComponentParts(IWorld worldIn, Random randomIn, MutableBoundingBox structureBoundingBoxIn, ChunkPos p_74875_4_) {
            PlacementSettings placementsettings = (new PlacementSettings()).setRotation(this.field_207616_e).setMirror(Mirror.NONE).setCenterOffset(PoultryHousePieces.structurePos.get(this.field_207615_d)).addProcessor(BlockIgnoreStructureProcessor.STRUCTURE_BLOCK);
            BlockPos blockpos = PoultryHousePieces.structurePos.get(this.field_207615_d);
            BlockPos blockpos1 = this.templatePosition.add(Template.transformedBlockPos(placementsettings, new BlockPos(blockpos.getX(), 0, blockpos.getX())));
            int i = worldIn.getHeight(Heightmap.Type.WORLD_SURFACE_WG, blockpos1.getX(), blockpos1.getZ());
            BlockPos blockpos2 = this.templatePosition;
            this.templatePosition = this.templatePosition.add(0, i - 90 - 1, 0);
            boolean flag = super.addComponentParts(worldIn, randomIn, structureBoundingBoxIn, p_74875_4_);

            this.templatePosition = blockpos2;
            return flag;
        }
    }
}