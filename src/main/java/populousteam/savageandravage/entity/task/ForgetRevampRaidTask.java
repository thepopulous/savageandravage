package populousteam.savageandravage.entity.task;

import com.google.common.collect.ImmutableMap;
import populousteam.savageandravage.SavageAndRavageCore;
import populousteam.savageandravage.world.RevampRaid;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.schedule.Activity;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

public class ForgetRevampRaidTask extends Task<LivingEntity> {
    public ForgetRevampRaidTask() {
        super(ImmutableMap.of());
    }

    protected boolean shouldExecute(ServerWorld worldIn, LivingEntity owner) {
        return worldIn.rand.nextInt(20) == 0;
    }

    protected void startExecuting(ServerWorld worldIn, LivingEntity entityIn, long gameTimeIn) {
        Brain<?> brain = entityIn.getBrain();
        RevampRaid raid = SavageAndRavageCore.instance.findRaid(new BlockPos(entityIn));
        if (raid == null || raid.isStopped() || raid.isLoss()) {
            brain.setFallbackActivity(Activity.IDLE);
            brain.updateActivity(worldIn.getDayTime(), worldIn.getGameTime());
        }

    }
}