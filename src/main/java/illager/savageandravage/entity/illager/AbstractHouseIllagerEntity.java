package illager.savageandravage.entity.illager;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.EnumSet;

public abstract class AbstractHouseIllagerEntity extends AbstractIllagerEntity {
    @Nullable
    private BlockPos illagerHome;

    protected AbstractHouseIllagerEntity(EntityType<? extends AbstractIllagerEntity> type, World worldIn) {
        super(type, worldIn);
    }

    protected void registerGoals() {
        super.registerGoals();
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        if (this.illagerHome != null) {

            compound.put("IllagerHome", NBTUtil.writeBlockPos(this.illagerHome));

        }
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        if (compound.contains("IllagerHome")) {
            this.illagerHome = NBTUtil.readBlockPos(compound.getCompound("IllagerHome"));
        }
    }

    public void setIllagerHome(@Nullable BlockPos p_213726_1_) {
        this.illagerHome = p_213726_1_;
    }

    @Nullable
    public BlockPos getIllagerHome() {
        return this.illagerHome;
    }

    class MoveToHomeGoal extends Goal {
        final AbstractHouseIllagerEntity illager;
        final double distance;
        final double speed;

        MoveToHomeGoal(AbstractHouseIllagerEntity houseIllagerEntity, double distance, double speed) {
            this.illager = houseIllagerEntity;
            this.distance = distance;
            this.speed = speed;
            this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void resetTask() {
            AbstractHouseIllagerEntity.this.navigator.clearPath();
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute() {
            BlockPos blockpos = this.illager.getIllagerHome();
            return blockpos != null && this.func_220846_a(blockpos, this.distance);
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            BlockPos blockpos = this.illager.getIllagerHome();
            if (blockpos != null && AbstractHouseIllagerEntity.this.navigator.noPath()) {
                if (this.func_220846_a(blockpos, 6.0D)) {
                    Vec3d vec3d = (new Vec3d((double) blockpos.getX() - this.illager.posX, (double) blockpos.getY() - this.illager.posY, (double) blockpos.getZ() - this.illager.posZ)).normalize();
                    Vec3d vec3d1 = vec3d.scale(10.0D).add(this.illager.posX, this.illager.posY, this.illager.posZ);
                    AbstractHouseIllagerEntity.this.navigator.tryMoveToXYZ(vec3d1.x, vec3d1.y, vec3d1.z, this.speed);
                } else {
                    AbstractHouseIllagerEntity.this.navigator.tryMoveToXYZ((double) blockpos.getX(), (double) blockpos.getY(), (double) blockpos.getZ(), this.speed);
                }
            }

        }

        private boolean func_220846_a(BlockPos p_220846_1_, double p_220846_2_) {
            return !p_220846_1_.withinDistance(this.illager.getPositionVec(), p_220846_2_);
        }
    }
}
