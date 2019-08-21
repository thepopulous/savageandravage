package illager.savageandravage.entity.ai;

import illager.savageandravage.entity.illager.AbstractHouseIllagerEntity;
import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.state.properties.BedPart;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

public class GotoBedGoal extends MoveToBlockGoal {
    private final AbstractHouseIllagerEntity illager;
    private int sleepTick;

    public GotoBedGoal(AbstractHouseIllagerEntity houseillager, double speed) {
        super(houseillager, speed, 14);
        this.illager = houseillager;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute() {
        return !this.illager.isBeingRidden() && !this.illager.world.isDaytime() && this.illager.getAttackTarget() == null && super.shouldExecute();
    }

    @Override
    public boolean shouldContinueExecuting() {
        BlockPos blockpos = new BlockPos(destinationBlock.getX(), destinationBlock.getY(), destinationBlock.getZ());

        return !this.illager.world.isDaytime() && (!this.illager.isSleeping() || this.sleepTick < 10 && this.illager.isSleeping() || illager.posY > (double) blockpos.getY() + 0.4D && blockpos.withinDistance(illager.getPositionVec(), 1.14D) && this.illager.isSleeping());
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting() {
        super.startExecuting();
        this.sleepTick = 0;
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void resetTask() {
        super.resetTask();
        this.illager.wakeUp();
        this.sleepTick = 0;
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.illager.isSleeping()) {
            BlockPos pos = this.illager.getPosition();
            BlockState blockstate = this.illager.world.getBlockState(pos);

            if (this.getIsAboveDestination()) {
                this.illager.startSleeping(this.destinationBlock);
            }


            this.sleepTick = 0;

        } else {
            if (this.sleepTick <= 10) {
                this.sleepTick++;
            }
        }
    }

    public double getTargetDistanceSq() {
        return 1.5D;
    }

    /**
     * Keep ticking a continuous task that has already been started
     */

    /**
     * Return true to set given position as destination
     */
    protected boolean shouldMoveTo(IWorldReader worldIn, BlockPos pos) {
        if (!worldIn.isAirBlock(pos.up())) {
            return false;
        } else {
            BlockState blockstate = worldIn.getBlockState(pos);
            Block block = blockstate.getBlock();

            return block.isIn(BlockTags.BEDS) && blockstate.get(BedBlock.PART) == BedPart.HEAD && !blockstate.get(BedBlock.OCCUPIED);

        }
    }
}