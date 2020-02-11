package populousteam.savageandravage.entity.ai;

import populousteam.savageandravage.entity.HyenaEntity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.TargetGoal;

import java.util.EnumSet;

public class OwnerHyenaHurtByTargetGoal extends TargetGoal {
    private final HyenaEntity tameable;
    private LivingEntity attacker;
    private int timestamp;

    public OwnerHyenaHurtByTargetGoal(HyenaEntity theDefendingTameableIn) {
        super(theDefendingTameableIn, false);
        this.tameable = theDefendingTameableIn;
        this.setMutexFlags(EnumSet.of(Goal.Flag.TARGET));
    }

    public boolean shouldExecute() {
        if (!this.tameable.isTamed()) {

            HyenaEntity entitylivingbase2 = this.tameable.getLeaderHyena();

            if (entitylivingbase2 == null) {
                return false;
            } else {
                if (!entitylivingbase2.isTamed()) {
                    this.attacker = entitylivingbase2.getRevengeTarget();
                    int i = entitylivingbase2.getRevengeTimer();
                    return i != this.timestamp && this.isSuitableTarget(this.attacker, EntityPredicate.DEFAULT) && this.tameable.shouldAttackEntity(this.attacker, entitylivingbase2);
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting() {
        this.tameable.setAttackTarget(this.attacker);
        LivingEntity entitylivingbase = this.tameable.getOwner();

        if (entitylivingbase != null) {
            this.timestamp = entitylivingbase.getRevengeTimer();
        }

        super.startExecuting();
    }
}