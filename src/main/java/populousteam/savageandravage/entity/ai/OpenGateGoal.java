package populousteam.savageandravage.entity.ai;

import net.minecraft.entity.MobEntity;

public class OpenGateGoal extends InteractFenceGateGoal {
    private final boolean closeDoor;
    private int closeDoorTemporisation;

    public OpenGateGoal(MobEntity entitylivingIn, boolean shouldClose) {
        super(entitylivingIn);
        this.entity = entitylivingIn;
        this.closeDoor = shouldClose;
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting() {
        return this.closeDoor && this.closeDoorTemporisation > 0 && super.shouldContinueExecuting();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting() {
        this.closeDoorTemporisation = 24;
        this.toggleGate(true);
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void resetTask() {
        this.toggleGate(false);
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void tick() {
        --this.closeDoorTemporisation;
        super.tick();
    }
}