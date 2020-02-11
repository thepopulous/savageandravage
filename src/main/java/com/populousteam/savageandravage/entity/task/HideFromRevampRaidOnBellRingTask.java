package com.populousteam.savageandravage.entity.task;

import com.google.common.collect.ImmutableMap;
import com.populousteam.savageandravage.SavageAndRavageCore;
import com.populousteam.savageandravage.world.RevampRaid;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.schedule.Activity;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

public class HideFromRevampRaidOnBellRingTask extends Task<LivingEntity> {
    public HideFromRevampRaidOnBellRingTask() {
        super(ImmutableMap.of(MemoryModuleType.HEARD_BELL_TIME, MemoryModuleStatus.VALUE_PRESENT));
    }

    protected void startExecuting(ServerWorld worldIn, LivingEntity entityIn, long gameTimeIn) {
        Brain<?> brain = entityIn.getBrain();
        RevampRaid raid = SavageAndRavageCore.instance.findRaid(new BlockPos(entityIn));
        if (raid == null) {
            brain.switchTo(Activity.HIDE);
        }

    }
}