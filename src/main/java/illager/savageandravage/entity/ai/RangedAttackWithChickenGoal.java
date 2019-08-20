package illager.savageandravage.entity.ai;

import illager.savageandravage.entity.illager.PoultryFarmerIllagerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.RangedAttackGoal;
import net.minecraft.entity.passive.ChickenEntity;

import java.util.List;

public class RangedAttackWithChickenGoal extends RangedAttackGoal {
    public final PoultryFarmerIllagerEntity illager;

    public RangedAttackWithChickenGoal(PoultryFarmerIllagerEntity attacker, double movespeed, int maxAttackTime, float maxAttackDistanceIn) {
        super(attacker, movespeed, maxAttackTime, maxAttackDistanceIn);
        this.illager = attacker;
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
        List<Entity> list = this.illager.world.getEntitiesInAABBexcluding(this.illager, this.illager.getBoundingBox().grow(2.0D, 4.0D, 2.0D), (p_220719_0_) -> {
            EntityType<?> entitytype = p_220719_0_.getType();
            return p_220719_0_ instanceof ChickenEntity;
        });
        if (this.illager.getPassengers().isEmpty()) {
            for (Entity entity : list) {
                entity.startRiding(this.illager);
            }
        }
    }
}
