package illager.savageandravage.entity.ai;

import illager.savageandravage.entity.illager.PoultryFarmerIllagerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.RangedAttackGoal;
import net.minecraft.util.Hand;

public class RangedAttackWithMeleeGoal extends RangedAttackGoal {
    public final PoultryFarmerIllagerEntity illager;
    protected int attackTick;

    public RangedAttackWithMeleeGoal(PoultryFarmerIllagerEntity illager, double movespeed, int maxAttackTime, float maxAttackDistanceIn) {
        super(illager, movespeed, maxAttackTime, maxAttackDistanceIn);
        this.illager = illager;
    }

    @Override
    public void startExecuting() {
        super.startExecuting();
        this.illager.setAggroed(true);
    }

    @Override
    public void resetTask() {
        super.resetTask();
        this.illager.setAggroed(false);
    }

    public void tick() {
        super.tick();
        LivingEntity livingentity = this.illager.getAttackTarget();
        double d0 = this.illager.getDistanceSq(livingentity.posX, livingentity.getBoundingBox().minY, livingentity.posZ);
        this.attackTick = Math.max(this.attackTick - 1, 0);
        this.checkAndPerformAttack(livingentity, d0);
    }

    protected void checkAndPerformAttack(LivingEntity enemy, double distToEnemySqr) {
        double d0 = this.getAttackReachSqr(enemy);
        if (distToEnemySqr <= d0 && this.attackTick <= 0) {
            this.attackTick = 24;
            this.illager.swingArm(Hand.MAIN_HAND);
            this.illager.attackEntityAsMob(enemy);
        }
    }

    protected double getAttackReachSqr(LivingEntity attackTarget) {
        return (double) (this.illager.getWidth() * 2.6F * this.illager.getWidth() * 2.6F + attackTarget.getWidth());
    }
}
