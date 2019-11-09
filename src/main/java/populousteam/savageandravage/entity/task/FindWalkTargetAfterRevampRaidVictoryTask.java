package populousteam.savageandravage.entity.task;

import populousteam.savageandravage.SavageAndRavageCore;
import populousteam.savageandravage.world.RevampRaid;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.brain.task.FindWalkTargetTask;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

public class FindWalkTargetAfterRevampRaidVictoryTask extends FindWalkTargetTask {
    public FindWalkTargetAfterRevampRaidVictoryTask(float p_i50337_1_) {
        super(p_i50337_1_);
    }

    protected boolean shouldExecute(ServerWorld worldIn, CreatureEntity owner) {
        RevampRaid raid = SavageAndRavageCore.instance.findRaid(new BlockPos(owner));
        return raid != null && raid.isVictory() && super.shouldExecute(worldIn, owner);
    }
}