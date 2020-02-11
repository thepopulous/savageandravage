package com.populousteam.savageandravage.entity.task;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.village.PointOfInterestType;

public class RevampVillagerTasks {
    public static ImmutableList<Pair<Integer, ? extends Task<? super VillagerEntity>>> core(VillagerProfession p_220638_0_, float p_220638_1_) {
        return ImmutableList.of(Pair.of(0, new SwimTask(0.4F, 0.8F)), Pair.of(0, new InteractWithDoorTask()), Pair.of(0, new LookTask(45, 90)), Pair.of(0, new PanicTask()), Pair.of(0, new WakeUpTask()), Pair.of(0, new HideFromRevampRaidOnBellRingTask()), Pair.of(0, new BeginRevampRaidTask()), Pair.of(1, new WalkToTargetTask(200)), Pair.of(2, new TradeTask(p_220638_1_)), Pair.of(5, new PickupFoodTask()), Pair.of(10, new GatherPOITask(p_220638_0_.getPointOfInterest(), MemoryModuleType.JOB_SITE, true)), Pair.of(10, new GatherPOITask(PointOfInterestType.HOME, MemoryModuleType.HOME, false)), Pair.of(10, new GatherPOITask(PointOfInterestType.MEETING, MemoryModuleType.MEETING_POINT, true)), Pair.of(10, new AssignProfessionTask()), Pair.of(10, new ChangeJobTask()));
    }

    public static ImmutableList<Pair<Integer, ? extends Task<? super VillagerEntity>>> preRaid(VillagerProfession p_220642_0_, float p_220642_1_) {
        return ImmutableList.of(Pair.of(0, new RingBellTask()), Pair.of(0, new FirstShuffledTask<>(ImmutableList.of(Pair.of(new StayNearPointTask(MemoryModuleType.MEETING_POINT, p_220642_1_ * 1.5F, 2, 150, 200), 6), Pair.of(new FindWalkTargetTask(p_220642_1_ * 1.5F), 2)))), func_220646_b(), Pair.of(99, new ForgetRevampRaidTask()));
    }

    public static ImmutableList<Pair<Integer, ? extends Task<? super VillagerEntity>>> raid(VillagerProfession p_220640_0_, float p_220640_1_) {
        return ImmutableList.of(Pair.of(0, new FirstShuffledTask<>(ImmutableList.of(Pair.of(new GoOutsideAfterRevampRaidTask(p_220640_1_), 5), Pair.of(new FindWalkTargetAfterRevampRaidVictoryTask(p_220640_1_ * 1.1F), 2)))), Pair.of(0, new CelebrateRevampRaidVictoryTask(600, 600)), Pair.of(2, new RevampFindHidingPlaceDuringRaidTask(24, p_220640_1_ * 1.4F)), func_220646_b(), Pair.of(99, new ForgetRevampRaidTask()));
    }

    private static Pair<Integer, Task<LivingEntity>> func_220646_b() {
        return Pair.of(5, new FirstShuffledTask<>(ImmutableList.of(Pair.of(new LookAtEntityTask(EntityType.VILLAGER, 8.0F), 2), Pair.of(new LookAtEntityTask(EntityType.PLAYER, 8.0F), 2), Pair.of(new DummyTask(30, 60), 8))));
    }
}
