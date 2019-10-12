package illager.savageandravage.entity.ai;

import illager.savageandravage.entity.HyenaEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public class HyenaRaidingTargetGoal<T extends HyenaEntity> extends NearestAttackableTargetGoal<T> {
    private final HyenaEntity tameable;

    public HyenaRaidingTargetGoal(HyenaEntity p_i48571_1_, Class<T> p_i48571_2_, boolean p_i48571_3_, @Nullable Predicate<LivingEntity> p_i48571_4_) {
        super(p_i48571_1_, p_i48571_2_, 10, p_i48571_3_, false, p_i48571_4_);
        this.tameable = p_i48571_1_;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute() {
        return this.tameable.isRaiding() && super.shouldExecute();
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting() {
        return this.targetEntitySelector != null ? this.targetEntitySelector.canTarget(this.goalOwner, this.nearestTarget) : super.shouldContinueExecuting();
    }
}