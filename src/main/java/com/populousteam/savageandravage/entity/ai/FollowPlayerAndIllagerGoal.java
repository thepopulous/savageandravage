package com.populousteam.savageandravage.entity.ai;

import com.populousteam.savageandravage.entity.illager.DefenderEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

public class FollowPlayerAndIllagerGoal extends Goal {
    private final MobEntity entity;
    private final Predicate<LivingEntity> followPredicate;
    private final double speedModifier;
    private final PathNavigator navigation;
    private final float stopDistance;
    private final float areaSize;
    private LivingEntity followingEntity;
    private int timeToRecalcPath;
    private float oldWaterCost;

    public FollowPlayerAndIllagerGoal(MobEntity p_i47417_1_, double p_i47417_2_, float p_i47417_4_, float p_i47417_5_) {
        this.entity = p_i47417_1_;
        this.followPredicate = (p_210291_1_) -> {
            return p_210291_1_ != null && p_i47417_1_.getClass() != p_210291_1_.getClass() && (p_210291_1_ instanceof AbstractIllagerEntity && !(p_210291_1_ instanceof DefenderEntity) || p_210291_1_ instanceof PlayerEntity);
        };
        this.speedModifier = p_i47417_2_;
        this.navigation = p_i47417_1_.getNavigator();
        this.stopDistance = p_i47417_4_;
        this.areaSize = p_i47417_5_;
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        if (!(p_i47417_1_.getNavigator() instanceof GroundPathNavigator) && !(p_i47417_1_.getNavigator() instanceof FlyingPathNavigator)) {
            throw new IllegalArgumentException("Unsupported mob type for FollowMobGoal");
        }
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute() {
        List<MobEntity> list = this.entity.world.getEntitiesWithinAABB(MobEntity.class, this.entity.getBoundingBox().grow(this.areaSize), this.followPredicate);
        if (!list.isEmpty()) {
            for (MobEntity mobentity : list) {
                if (!mobentity.isInvisible()) {
                    this.followingEntity = mobentity;
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting() {
        return this.followingEntity != null && !this.navigation.noPath() && this.entity.getDistanceSq(this.followingEntity) > (double) (this.stopDistance * this.stopDistance);
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting() {
        this.timeToRecalcPath = 0;
        this.oldWaterCost = this.entity.getPathPriority(PathNodeType.WATER);
        this.entity.setPathPriority(PathNodeType.WATER, 0.0F);
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void resetTask() {
        this.followingEntity = null;
        this.navigation.clearPath();
        this.entity.setPathPriority(PathNodeType.WATER, this.oldWaterCost);
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void tick() {
        if (this.followingEntity != null && !this.entity.getLeashed()) {
            this.entity.getLookController().setLookPositionWithEntity(this.followingEntity, 10.0F, (float) this.entity.getVerticalFaceSpeed());
            if (--this.timeToRecalcPath <= 0) {
                this.timeToRecalcPath = 10;
                double d0 = this.entity.getPosX() - this.followingEntity.getPosX();
                double d1 = this.entity.getPosY() - this.followingEntity.getPosY();
                double d2 = this.entity.getPosZ() - this.followingEntity.getPosZ();
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;
                if (!(d3 <= (double) (this.stopDistance * this.stopDistance))) {
                    this.navigation.tryMoveToEntityLiving(this.followingEntity, this.speedModifier);
                } else {
                    this.navigation.clearPath();
                    if (d3 <= (double) this.stopDistance) {
                        double d4 = this.followingEntity.getPosX() - this.entity.getPosX();
                        double d5 = this.followingEntity.getPosZ() - this.entity.getPosZ();
                        this.navigation.tryMoveToXYZ(this.entity.getPosX() - d4, this.entity.getPosY(), this.entity.getPosZ() - d5, this.speedModifier);
                    }
                }
            }
        }
    }
}