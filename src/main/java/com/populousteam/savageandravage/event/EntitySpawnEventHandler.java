package com.populousteam.savageandravage.event;

import com.populousteam.savageandravage.SavageConfig;
import com.populousteam.savageandravage.init.SavageEntityRegistry;
import net.minecraft.entity.EntityClassification;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.PointOfInterest;
import net.minecraft.village.PointOfInterestManager;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;
import java.util.stream.Collectors;

public class EntitySpawnEventHandler {
    @SubscribeEvent
    public void onSpawnList(WorldEvent.PotentialSpawns event) {
        World world = event.getWorld().getWorld();
        BlockPos pos = event.getPos();

        if (event.getType() == EntityClassification.MONSTER) {
            List<PointOfInterest> list = world.getServer().getWorld(DimensionType.OVERWORLD).func_217443_B().func_219146_b(PointOfInterestType.field_221053_a, pos, 64, PointOfInterestManager.Status.IS_OCCUPIED).collect(Collectors.toList());

            if (Feature.VILLAGE.isPositionInStructure(world, pos) && list.isEmpty() && SavageConfig.SKELETON_VILLAGERS_ZOMBIE_VILLAGE.get()) {
                event.getList().add(new Biome.SpawnListEntry(SavageEntityRegistry.SKELETONVILLAGER, 160, 2, 3));
            }
        }
    }
}
