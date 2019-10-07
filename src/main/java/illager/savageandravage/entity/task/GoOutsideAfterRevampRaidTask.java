package illager.savageandravage.entity.task;

import illager.savageandravage.SavageAndRavageCore;
import illager.savageandravage.world.RevampRaid;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.task.MoveToSkylightTask;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

public class GoOutsideAfterRevampRaidTask extends MoveToSkylightTask {
    public GoOutsideAfterRevampRaidTask(float p_i50365_1_) {
        super(p_i50365_1_);
    }

    protected boolean shouldExecute(ServerWorld worldIn, LivingEntity owner) {
        RevampRaid raid = SavageAndRavageCore.instance.findRaid(new BlockPos(owner));
        return raid != null && raid.isVictory() && super.shouldExecute(worldIn, owner);
    }
}