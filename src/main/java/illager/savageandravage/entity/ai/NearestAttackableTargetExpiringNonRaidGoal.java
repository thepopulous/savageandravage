package illager.savageandravage.entity.ai;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.AbstractRaiderEntity;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public class NearestAttackableTargetExpiringNonRaidGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
    private int field_220782_i = 0;

    public NearestAttackableTargetExpiringNonRaidGoal(AbstractRaiderEntity p_i50311_1_, Class<T> p_i50311_2_, boolean p_i50311_3_, @Nullable Predicate<LivingEntity> p_i50311_4_) {
        super(p_i50311_1_, p_i50311_2_, 500, p_i50311_3_, false, p_i50311_4_);
    }

    public int func_220781_h() {
        return this.field_220782_i;
    }

    public void func_220780_j() {
        --this.field_220782_i;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute() {
        if (this.field_220782_i <= 0 && this.goalOwner.getRNG().nextBoolean()) {

            this.findNearestTarget();
            return this.nearestTarget != null;

        } else {
            return false;
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting() {
        this.field_220782_i = 200;
        super.startExecuting();
    }
}