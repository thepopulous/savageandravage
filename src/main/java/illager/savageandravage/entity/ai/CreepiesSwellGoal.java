package illager.savageandravage.entity.ai;

import illager.savageandravage.entity.CreepiesEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;

import java.util.EnumSet;

public class CreepiesSwellGoal extends Goal {
    private final CreepiesEntity field_75269_a;
    private LivingEntity field_75268_b;

    public CreepiesSwellGoal(CreepiesEntity entitycreeperIn) {
        this.field_75269_a = entitycreeperIn;
        this.setMutexFlags(EnumSet.of(Flag.MOVE));
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute() {
        LivingEntity livingentity = this.field_75269_a.getAttackTarget();
        return this.field_75269_a.getCreeperState() > 0 || livingentity != null && this.field_75269_a.getDistanceSq(livingentity) < 2.5D + this.field_75269_a.getGrowSize() * 0.5F;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting() {
        this.field_75269_a.getNavigator().clearPath();
        this.field_75268_b = this.field_75269_a.getAttackTarget();
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void resetTask() {
        this.field_75268_b = null;
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void tick() {
        if (this.field_75268_b == null) {
            this.field_75269_a.setCreeperState(-1);
        } else if (this.field_75269_a.getDistanceSq(this.field_75268_b) > 25.0D + this.field_75269_a.getGrowSize() * 2.0F) {
            this.field_75269_a.setCreeperState(-1);
        } else if (!this.field_75269_a.getEntitySenses().canSee(this.field_75268_b)) {
            this.field_75269_a.setCreeperState(-1);
        } else {
            this.field_75269_a.setCreeperState(1);
        }
    }
}