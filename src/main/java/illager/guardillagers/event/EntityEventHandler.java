package illager.guardillagers.event;

import illager.guardillagers.entity.EntityGuardIllager;
import illager.guardillagers.init.IllagerEntityRegistry;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkGeneratorOverworld;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EntityEventHandler {
    //エンティティのAI関係
    @SubscribeEvent
    public void onEntityJoin(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof EntityVillager) {
            EntityVillager villager = (EntityVillager) event.getEntity();

            villager.tasks.addTask(1, new EntityAIAvoidEntity<>(villager, EntityGuardIllager.class, 12.0F, 0.8D, 0.8D));
        }
    }

    @SubscribeEvent
    public void getPotentialSpawns(WorldEvent.PotentialSpawns event) {
        BlockPos pos = event.getPos();
        IWorld world = event.getWorld();
        IChunkProvider prov = world.getChunkProvider();

        if (event.getType() == EnumCreatureType.MONSTER && prov instanceof ChunkGeneratorOverworld) {

            ChunkGeneratorOverworld serverProv = (ChunkGeneratorOverworld) prov;

            if (Feature.WOODLAND_MANSION.isPositionInStructure(world, pos)) {

                serverProv.getPossibleCreatures(EnumCreatureType.MONSTER, pos).add(new Biome.SpawnListEntry(IllagerEntityRegistry.GUARD_ILLAGER, 100, 1, 2));

            }
        }
    }
}
