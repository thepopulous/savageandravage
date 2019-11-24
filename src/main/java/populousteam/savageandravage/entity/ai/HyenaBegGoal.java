package populousteam.savageandravage.entity.ai;

import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import populousteam.savageandravage.entity.HyenaEntity;

import java.util.EnumSet;

public class HyenaBegGoal extends Goal {
    private final HyenaEntity hyena;
    private PlayerEntity player;
    private final World world;
    private final float minPlayerDistance;
    private int timeoutCounter;
    private final EntityPredicate field_220688_f;

    public HyenaBegGoal(HyenaEntity hyena, float minDistance) {
        this.hyena = hyena;
        this.world = hyena.world;
        this.minPlayerDistance = minDistance;
        this.field_220688_f = (new EntityPredicate()).setDistance((double) minDistance).allowInvulnerable().allowFriendlyFire().setSkipAttackChecks();
        this.setMutexFlags(EnumSet.of(Flag.LOOK));
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute() {
        if (this.hyena.isTamed()) {
            this.player = this.world.getClosestPlayer(this.field_220688_f, this.hyena);
        }
        return this.player == null ? false : this.shouldLookingPlayer(this.player);
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting() {
        if (!this.player.isAlive()) {
            return false;
        } else if (this.hyena.getDistanceSq(this.player) > (double) (this.minPlayerDistance * this.minPlayerDistance)) {
            return false;
        } else {
            return this.shouldLookingPlayer(this.player);
        }
    }


    private boolean shouldLookingPlayer(PlayerEntity player) {

        Vec3d vec3d = player.getLook(1.0F).normalize();
        Vec3d vec3d1 = new Vec3d(this.hyena.posX - player.posX, this.hyena.getBoundingBox().minY + (double) this.hyena.getEyeHeight() - (player.posY + (double) player.getEyeHeight()), this.hyena.posZ - player.posZ);
        double d0 = vec3d1.length();
        vec3d1 = vec3d1.normalize();
        double d1 = vec3d.dotProduct(vec3d1);
        return d1 > 1.0D - 0.045D / d0 ? player.canEntityBeSeen(this.hyena) : false;

    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting() {
        this.hyena.setBegging(true);
        this.timeoutCounter = 40 + this.hyena.getRNG().nextInt(40);
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void resetTask() {
        this.hyena.setBegging(false);
        this.player = null;
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void tick() {
        this.hyena.getLookController().setLookPosition(this.player.posX, this.player.posY + (double) this.player.getEyeHeight(), this.player.posZ, 10.0F, (float) this.hyena.getVerticalFaceSpeed());
        --this.timeoutCounter;
    }

}