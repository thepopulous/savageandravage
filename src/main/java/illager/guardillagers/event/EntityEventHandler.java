package illager.guardillagers.event;

import illager.guardillagers.entity.GuardIllagerEntity;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EntityEventHandler {
    //エンティティのAI関係
    @SubscribeEvent
    public void onEntityJoin(EntityJoinWorldEvent event) {
	    if (event.getEntity() instanceof AbstractVillagerEntity) {
		    AbstractVillagerEntity villager = (AbstractVillagerEntity) event.getEntity();
		    villager.goalSelector.addGoal(1, new AvoidEntityGoal<>(villager, GuardIllagerEntity.class, 16.0F, 0.8D, 0.8D));
        }
    }
}
