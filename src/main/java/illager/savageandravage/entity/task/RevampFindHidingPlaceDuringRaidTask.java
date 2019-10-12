package illager.savageandravage.entity.task;

import illager.savageandravage.SavageAndRavageCore;
import illager.savageandravage.world.RevampRaid;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.task.FindHidingPlaceTask;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

public class RevampFindHidingPlaceDuringRaidTask extends FindHidingPlaceTask {
    public RevampFindHidingPlaceDuringRaidTask(int p_i50360_1_, float p_i50360_2_) {
        super(p_i50360_1_, p_i50360_2_, 1);
    }

    protected boolean shouldExecute(ServerWorld worldIn, LivingEntity owner) {
        RevampRaid raid = SavageAndRavageCore.instance.findRaid(new BlockPos(owner));
        return super.shouldExecute(worldIn, owner) && raid != null && raid.isActive() && !raid.isVictory() && !raid.isLoss();
    }
}