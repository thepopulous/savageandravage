package illager.savageandravage.entity.ai;

import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.MathHelper;

import java.util.EnumSet;

public class RangedStrafeAttackGoal extends Goal {
    private final MobEntity attacker;
    private final IRangedAttackMob rangedAttackEntityHost;
    private LivingEntity field_75323_c;
    private int rangedAttackTime = -1;
    private final double entityMoveSpeed;
    private int seeTime;
    private final int attackIntervalMin;
    private final int maxRangedAttackTime;
    private final float attackRadius;
    private final float maxAttackDistance;
    private boolean strafingClockwise;
    private boolean strafingBackwards;
    private int strafingTime = -1;

    public RangedStrafeAttackGoal(IRangedAttackMob attacker, double movespeed, int maxAttackTime, float maxAttackDistanceIn) {
        this(attacker, movespeed, maxAttackTime, maxAttackTime, maxAttackDistanceIn);
    }

    public RangedStrafeAttackGoal(IRangedAttackMob attacker, double movespeed, int p_i1650_4_, int maxAttackTime, float maxAttackDistanceIn) {
        if (!(attacker instanceof LivingEntity)) {
            throw new IllegalArgumentException("ArrowAttackGoal requires Mob implements RangedAttackMob");
        } else {
            this.rangedAttackEntityHost = attacker;
            this.attacker = (MobEntity) attacker;
            this.entityMoveSpeed = movespeed;
            this.attackIntervalMin = p_i1650_4_;
            this.maxRangedAttackTime = maxAttackTime;
            this.attackRadius = maxAttackDistanceIn;
            this.maxAttackDistance = maxAttackDistanceIn * maxAttackDistanceIn;
        }
        this.setMutexFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute() {
        LivingEntity livingentity = this.attacker.getAttackTarget();
        if (livingentity != null && livingentity.isAlive()) {
            this.field_75323_c = livingentity;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void startExecuting() {
        super.startExecuting();
        this.attacker.setAggroed(true);
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting() {
        return this.shouldExecute() || !this.attacker.getNavigator().noPath();
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void resetTask() {
        this.field_75323_c = null;
        this.seeTime = 0;
        this.rangedAttackTime = -1;
        this.attacker.setAggroed(false);
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void tick() {
        double d0 = this.attacker.getDistanceSq(this.field_75323_c.posX, this.field_75323_c.getBoundingBox().minY, this.field_75323_c.posZ);
        boolean flag = this.attacker.getEntitySenses().canSee(this.field_75323_c);
        if (flag) {
            ++this.seeTime;
        } else {
            this.seeTime = 0;
        }

        if (!(d0 > (double) this.maxAttackDistance) && this.seeTime >= 5) {
            this.attacker.getNavigator().clearPath();

            if (this.strafingTime >= 20) {
                if ((double) this.attacker.getRNG().nextFloat() < 0.3D) {
                    this.strafingClockwise = !this.strafingClockwise;
                }

                if ((double) this.attacker.getRNG().nextFloat() < 0.3D) {
                    this.strafingBackwards = !this.strafingBackwards;
                }

                this.strafingTime = 0;
            }

            if (d0 > (double) this.maxAttackDistance * 0.85F) {
                this.strafingBackwards = false;
            } else if (d0 < (double) this.maxAttackDistance * 0.65F) {
                this.strafingBackwards = true;
            }


            this.attacker.getMoveHelper().strafe(this.strafingBackwards ? -0.3F : 0.3F, this.strafingClockwise ? 0.4F : -0.4F);
        } else {
            this.attacker.getNavigator().tryMoveToEntityLiving(this.field_75323_c, this.entityMoveSpeed);
        }

        this.attacker.getLookController().setLookPositionWithEntity(this.field_75323_c, 10.0F, (float) this.attacker.getVerticalFaceSpeed());
        if (--this.rangedAttackTime == 0) {
            if (!flag) {
                return;
            }

            float f = MathHelper.sqrt(d0) / this.attackRadius;
            float lvt_5_1_ = MathHelper.clamp(f, 0.1F, 1.0F);
            this.rangedAttackEntityHost.attackEntityWithRangedAttack(this.field_75323_c, lvt_5_1_);
            this.rangedAttackTime = MathHelper.floor(f * (float) (this.maxRangedAttackTime - this.attackIntervalMin) + (float) this.attackIntervalMin);
        } else if (this.rangedAttackTime < 0) {
            float f2 = MathHelper.sqrt(d0) / this.attackRadius;
            this.rangedAttackTime = MathHelper.floor(f2 * (float) (this.maxRangedAttackTime - this.attackIntervalMin) + (float) this.attackIntervalMin);
        }

    }
}