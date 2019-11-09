package populousteam.savageandravage.entity.ai;

import populousteam.savageandravage.entity.HyenaEntity;
import net.minecraft.entity.ai.goal.Goal;

import java.util.EnumSet;
import java.util.List;

public class FollowWildLeaderGoal extends Goal {
    public HyenaEntity owner;
    public double spead;
    private int moveDelay;

    public FollowWildLeaderGoal(HyenaEntity hyenaEntity, double spead) {
        this.owner = hyenaEntity;
        this.spead = spead;
        this.setMutexFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean shouldExecute() {
        if (this.owner.isTamed() || this.owner.isRaiding()) {
            return false;
        } else if (!this.owner.isAlive()) {
            return false;
        } else if (this.owner.hasLeader()) {
            return true;
        } else {
            List<HyenaEntity> list = this.owner.world.<HyenaEntity>getEntitiesWithinAABB(this.owner.getClass(), this.owner.getBoundingBox().grow(10.0D, 10.0D, 10.0D));
            HyenaEntity entityhyena = null;
            double d0 = Double.MAX_VALUE;

            for (HyenaEntity entityhyena1 : list) {
                if (entityhyena1.isLeader()) {
                    double d1 = this.owner.getDistanceSq(entityhyena1);

                    if (!(d1 > d0)) {
                        d0 = d1;
                        entityhyena = entityhyena1;
                    }
                }
            }

            if (entityhyena == null) {
                for (HyenaEntity entityhyena2 : list) {
                    if (!this.owner.isLeader() && !entityhyena2.isLeader()) {
                        double d1 = this.owner.getDistanceSq(entityhyena2);

                        if (!(d1 > d0)) {
                            d0 = d1;
                            entityhyena = entityhyena2;
                        }
                    }
                }
            }

            /*
             * Allocate a random number to avoid confusion with other Hyena leaders when deciding on a leader
             */
            if (entityhyena != null && this.owner.world.rand.nextInt(10) == 0) {
                if (entityhyena.isLeader() && this.owner.isLeader()) {
                    this.owner.setLeader(false);
                }

                if (!entityhyena.isLeader() && !this.owner.isLeader()) {
                    this.owner.setLeader(true);
                }
                this.owner.setLeaderHyena(entityhyena);
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public boolean shouldContinueExecuting() {
        return this.owner.isAlive() && this.owner.getLeaderHyena().isAlive();
    }

    public void startExecuting() {
        this.moveDelay = 0;

    }

    public void resetTask() {
        this.owner.setLeaderHyena(null);
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void tick() {
        double d1 = this.owner.getDistanceSq(this.owner.getLeaderHyena());

        if (this.moveDelay++ >= 10) {
            this.moveDelay = 0;
            if (d1 > 16.0F) {
                this.owner.getNavigator().tryMoveToEntityLiving(this.owner.getLeaderHyena(), 1.0D);
            }
        }
    }


}