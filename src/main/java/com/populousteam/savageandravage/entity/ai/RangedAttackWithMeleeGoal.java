package com.populousteam.savageandravage.entity.ai;

import com.populousteam.savageandravage.entity.illager.PoultryFarmerIllagerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.RangedAttackGoal;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
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

    @Override
    public boolean shouldContinueExecuting() {
        return super.shouldContinueExecuting() && !this.illager.isDidAttack();
    }

    public void tick() {
        super.tick();
        LivingEntity livingentity = this.illager.getAttackTarget();
        double d0;
        if (livingentity != null) {
            d0 = this.illager.getDistanceSq(livingentity.getPosX(), livingentity.getBoundingBox().minY, livingentity.getPosZ());

            this.attackTick = Math.max(this.attackTick - 1, 0);
            this.checkAndPerformAttack(livingentity, d0);

            double d1 = this.getAttackReachSqr(livingentity);
            if (d0 <= d1 * 1.25F && this.attackTick <= 0 && this.illager.getHeldItem(Hand.MAIN_HAND).getItem() == Items.EGG) {
                this.illager.setHeldItem(Hand.MAIN_HAND, new ItemStack(Items.IRON_HOE));
                this.illager.setHeldItem(Hand.OFF_HAND, new ItemStack(Items.EGG));
            } else if (d0 >= d1 * 1.25F && this.illager.getHeldItem(Hand.MAIN_HAND).getItem() == Items.IRON_HOE) {
                this.illager.setHeldItem(Hand.MAIN_HAND, new ItemStack(Items.EGG));
                this.illager.setHeldItem(Hand.OFF_HAND, new ItemStack(Items.IRON_HOE));
            }
        }
    }

    protected void checkAndPerformAttack(LivingEntity enemy, double distToEnemySqr) {
        if (this.illager.getHeldItem(Hand.MAIN_HAND).getItem() == Items.IRON_HOE) {
            double d0 = this.getAttackReachSqr(enemy);
            if (distToEnemySqr <= d0 && this.attackTick <= 0) {
                this.attackTick = 24;
                this.illager.swingArm(Hand.MAIN_HAND);
                this.illager.attackEntityAsMob(enemy);
            }
        }
    }

    protected double getAttackReachSqr(LivingEntity attackTarget) {
        return this.illager.getWidth() * 2.6F * this.illager.getWidth() * 2.6F + attackTarget.getWidth();
    }
}
