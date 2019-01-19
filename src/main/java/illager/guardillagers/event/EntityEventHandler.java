package illager.guardillagers.event;

import illager.guardillagers.entity.EntityGuardIllager;
import illager.guardillagers.init.IllagerEntityRegistry;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
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

    public static void addSpawn() {

        Feature.WOODLAND_MANSION.getSpawnList().add(new Biome.SpawnListEntry(IllagerEntityRegistry.GUARD_ILLAGER, 1, 1, 1));

    }

}
