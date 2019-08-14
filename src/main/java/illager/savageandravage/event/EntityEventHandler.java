package illager.savageandravage.event;

import illager.savageandravage.entity.GuardIllagerEntity;
import illager.savageandravage.entity.illager.GrieferIllagerEntity;
import illager.savageandravage.init.SavageEntityRegistry;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EntityEventHandler {
    //エンティティのAI関係
    @SubscribeEvent
    public void onEntityJoin(EntityJoinWorldEvent event) {
        World world = event.getWorld();

        if (event.getEntity() instanceof AbstractVillagerEntity) {
            AbstractVillagerEntity villager = (AbstractVillagerEntity) event.getEntity();
            villager.goalSelector.addGoal(1, new AvoidEntityGoal<>(villager, GrieferIllagerEntity.class, 16.0F, 0.7D, 0.8D));
            villager.goalSelector.addGoal(1, new AvoidEntityGoal<>(villager, GuardIllagerEntity.class, 16.0F, 0.7D, 0.8D));
        }

        if (event.getEntity() instanceof AbstractIllagerEntity) {

            AbstractIllagerEntity pillager = (AbstractIllagerEntity) event.getEntity();

            if (pillager.getRaid() != null && !pillager.isLeader() && world.rand.nextInt(8) == 0) {
                for (int i = 0; i < 1 + world.rand.nextInt(1); i++) {
                    GuardIllagerEntity guardilalger = SavageEntityRegistry.GUARD_ILLAGER.create(world);

                    pillager.getRaid().func_221317_a(pillager.getRaid().getWaves(world.getDifficulty()), guardilalger, pillager.getPosition(), false);
                }
            }

            if (pillager.getRaid() != null && !pillager.isLeader() && world.rand.nextInt(6) == 0) {
                for (int i = 0; i < 1 + world.rand.nextInt(1); i++) {
                    GrieferIllagerEntity griferEntity = SavageEntityRegistry.GRIEFER_ILLAGER.create(world);

                    pillager.getRaid().func_221317_a(pillager.getRaid().getWaves(world.getDifficulty()), griferEntity, pillager.getPosition(), false);
                }
            }
        }
    }
}
