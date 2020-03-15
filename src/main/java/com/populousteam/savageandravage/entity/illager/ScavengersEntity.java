package com.populousteam.savageandravage.entity.illager;

import com.populousteam.savageandravage.entity.ai.UseItemOnLeftHandGoal;
import com.populousteam.savageandravage.init.SavageItems;
import com.populousteam.savageandravage.init.SavageLootTables;
import com.populousteam.savageandravage.message.MessageScavengerProp;
import com.populousteam.savageandravage.message.SavagePacketHandler;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.entity.monster.AbstractRaiderEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PotionEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.potion.Effects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.List;

public class ScavengersEntity extends AbstractIllagerEntity implements IRangedAttackMob {

    public ScavengersEntity(EntityType<? extends ScavengersEntity> type, World worldIn) {
        super(type, worldIn);
        ((GroundPathNavigator) this.getNavigator()).setBreakDoors(true);
        this.experienceValue = 8;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new UseItemOnLeftHandGoal<>(this, new ItemStack(SavageItems.SINISTERHORN), SoundEvents.ENTITY_VINDICATOR_CELEBRATE, (p_213736_1_) -> {
            List<AbstractIllagerEntity> list = world.getEntitiesWithinAABB(AbstractIllagerEntity.class, getBoundingBox().grow(22.0D));
            return list.size() >= 1 && this.world.rand.nextInt(240) == 0;
        }, true));
        this.goalSelector.addGoal(2, new OpenDoorGoal(this, true));
        this.goalSelector.addGoal(3, new AvoidEntityGoal(this, PlayerEntity.class, 28.0F, 0.82D, 1.0D) {

            @Override
            public boolean shouldExecute() {
                List<AbstractIllagerEntity> list = world.getEntitiesWithinAABB(AbstractIllagerEntity.class, getBoundingBox().grow(22.0D));
                return list.size() <= 1 && super.shouldExecute();
            }

            @Override
            public void tick() {
                super.tick();

                List<PlayerEntity> list = world.getEntitiesWithinAABB(PlayerEntity.class, getBoundingBox().grow(25.0D));
                if (list.size() == 0) {

                    addDespawnAndParticle();
                }
            }
        });
        this.goalSelector.addGoal(8, new WaterAvoidingRandomWalkingGoal(this, 0.75D));
        this.goalSelector.addGoal(9, new LookAtGoal(this, PlayerEntity.class, 3.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtGoal(this, MobEntity.class, 8.0F));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, AbstractRaiderEntity.class)).setCallsForHelp());
        this.targetSelector.addGoal(4, (new NearestAttackableTargetGoal<>(this, AbstractVillagerEntity.class, false)).setUnseenMemoryTicks(300));
        this.targetSelector.addGoal(4, (new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, false)).setUnseenMemoryTicks(300));
    }

    private void addDespawnAndParticle() {
        SavagePacketHandler.CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(() -> world.getChunkAt(new BlockPos(this.getPosX(), this.getPosY(), this.getPosZ()))), new MessageScavengerProp(this.getPosX(), this.getPosY(), this.getPosZ()));

        this.remove();
    }

    protected void registerAttributes() {
        super.registerAttributes();

        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.335F);
        this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(22.0D);
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);
    }

    @Override
    public void func_213660_a(int i, boolean b) {

    }

    public void livingTick() {
        super.livingTick();
    }

    @Override
    protected SoundEvent getAmbientSound() {

        return SoundEvents.ENTITY_VINDICATOR_AMBIENT;

    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_VINDICATOR_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_VINDICATOR_HURT;
    }

    @Override
    public SoundEvent getRaidLossSound() {
        return SoundEvents.ENTITY_VINDICATOR_CELEBRATE;
    }

    @Override
    protected ResourceLocation getLootTable() {
        return SavageLootTables.SCAVENGER;
    }

    public boolean isOnSameTeam(Entity entityIn) {
        if (super.isOnSameTeam(entityIn)) {
            return true;
        } else if (entityIn instanceof LivingEntity && ((LivingEntity) entityIn).getCreatureAttribute() == CreatureAttribute.ILLAGER) {
            return this.getTeam() == null && entityIn.getTeam() == null;
        } else {
            return false;
        }
    }

    protected float applyPotionDamageCalculations(DamageSource source, float damage) {
        damage = super.applyPotionDamageCalculations(source, damage);
        if (source.getTrueSource() == this) {
            damage = 0.0F;
        }

        if (source.isMagicDamage()) {
            damage = (float) ((double) damage * 0.65D);
        }

        return damage;
    }

    public void attackEntityWithRangedAttack(LivingEntity target, float distanceFactor) {
        Vec3d vec3d = target.getMotion();
        double d0 = target.getPosX() + vec3d.x - this.getPosX();
        double d1 = target.getPosY() + (double) target.getEyeHeight() - (double) 1.1F - this.getPosY();
        double d2 = target.getPosZ() + vec3d.z - this.getPosZ();
        float f = MathHelper.sqrt(d0 * d0 + d2 * d2);
        Potion potion = Potions.SLOWNESS;
        if (target instanceof AbstractRaiderEntity) {
            if (target.getHealth() <= 4.0F) {
                potion = Potions.HEALING;
            } else {
                potion = Potions.REGENERATION;
            }

            this.setAttackTarget(null);
        } else if (f >= 8.0F && !target.isPotionActive(Effects.SLOWNESS)) {
            potion = Potions.SLOWNESS;
        }

        PotionEntity potionentity = new PotionEntity(this.world, this);
        potionentity.setItem(PotionUtils.addPotionToItemStack(new ItemStack(Items.SPLASH_POTION), potion));
        potionentity.rotationPitch -= -20.0F;
        potionentity.shoot(d0, d1 + (double) (f * 0.2F), d2, 0.75F, 9.0F);
        this.world.playSound(null, this.getPosX(), this.getPosY(), this.getPosZ(), SoundEvents.ENTITY_WITCH_THROW, this.getSoundCategory(), 1.0F, 0.8F + this.rand.nextFloat() * 0.4F);
        this.world.addEntity(potionentity);

    }

}
