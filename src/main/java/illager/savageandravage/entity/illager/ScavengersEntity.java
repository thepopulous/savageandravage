package illager.savageandravage.entity.illager;

import illager.savageandravage.entity.ai.NearestAttackableTargetExpiringNonRaidGoal;
import illager.savageandravage.init.SavageEntityRegistry;
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
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.Predicate;

public class ScavengersEntity extends AbstractIllagerEntity implements IRangedAttackMob {
    private NearestAttackableTargetExpiringNonRaidGoal<AbstractRaiderEntity> field_213694_bC;
    private ToggleableNearestAttackableTargetGoal<PlayerEntity> field_213695_bD;

    public ScavengersEntity(EntityType<? extends ScavengersEntity> type, World worldIn) {
        super(type, worldIn);
        ((GroundPathNavigator) this.getNavigator()).setBreakDoors(true);
        this.experienceValue = 8;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.field_213694_bC = new NearestAttackableTargetExpiringNonRaidGoal<>(this, AbstractRaiderEntity.class, true, (p_213693_1_) -> {
            return p_213693_1_ != null && p_213693_1_.getType() != SavageEntityRegistry.SCAVENGER;
        });
        this.field_213695_bD = new ToggleableNearestAttackableTargetGoal<>(this, PlayerEntity.class, 10, true, false, (Predicate<LivingEntity>) null);
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(2, new OpenDoorGoal(this, true));
        this.goalSelector.addGoal(2, new AvoidEntityGoal(this, PlayerEntity.class, 4.0F, 0.82D, 1.0D) {

            @Override
            public boolean shouldExecute() {
                List<AbstractIllagerEntity> list = world.getEntitiesWithinAABB(AbstractIllagerEntity.class, getBoundingBox().grow(30.0D));
                return list.size() <= 1 && super.shouldExecute();
            }
        });
        this.goalSelector.addGoal(4, new RangedAttackGoal(this, 0.75D, 80, 14.5F));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomWalkingGoal(this, 0.75D));
        this.goalSelector.addGoal(9, new LookAtGoal(this, PlayerEntity.class, 3.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtGoal(this, MobEntity.class, 8.0F));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, AbstractRaiderEntity.class)).setCallsForHelp());
        this.targetSelector.addGoal(2, this.field_213694_bC);
        this.targetSelector.addGoal(3, this.field_213695_bD);
        this.targetSelector.addGoal(4, (new NearestAttackableTargetGoal<>(this, AbstractVillagerEntity.class, false)).setUnseenMemoryTicks(300));
        this.targetSelector.addGoal(4, (new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, false)).setUnseenMemoryTicks(300));
    }

    protected void registerAttributes() {
        super.registerAttributes();

        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double) 0.335F);
        this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(22.0D);
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);
    }

    @Override
    public void func_213660_a(int i, boolean b) {

    }

    public void livingTick() {
        super.livingTick();
        if (!this.world.isRemote && this.isAlive()) {
            this.field_213694_bC.func_220780_j();
            if (this.field_213694_bC.func_220781_h() <= 0) {
                this.field_213695_bD.func_220783_a(true);
            } else {
                this.field_213695_bD.func_220783_a(false);
            }
        }
    }

    @Override
    public SoundEvent getRaidLossSound() {
        return null;
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
        double d0 = target.posX + vec3d.x - this.posX;
        double d1 = target.posY + (double) target.getEyeHeight() - (double) 1.1F - this.posY;
        double d2 = target.posZ + vec3d.z - this.posZ;
        float f = MathHelper.sqrt(d0 * d0 + d2 * d2);
        Potion potion = Potions.SLOWNESS;
        if (target instanceof AbstractRaiderEntity) {
            if (target.getHealth() <= 4.0F) {
                potion = Potions.HEALING;
            } else {
                potion = Potions.REGENERATION;
            }

            this.setAttackTarget((LivingEntity) null);
        } else if (f >= 8.0F && !target.isPotionActive(Effects.SLOWNESS)) {
            potion = Potions.SLOWNESS;
        }

        PotionEntity potionentity = new PotionEntity(this.world, this);
        potionentity.setItem(PotionUtils.addPotionToItemStack(new ItemStack(Items.SPLASH_POTION), potion));
        potionentity.rotationPitch -= -20.0F;
        potionentity.shoot(d0, d1 + (double) (f * 0.2F), d2, 0.75F, 9.0F);
        this.world.playSound((PlayerEntity) null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_WITCH_THROW, this.getSoundCategory(), 1.0F, 0.8F + this.rand.nextFloat() * 0.4F);
        this.world.addEntity(potionentity);

    }

}
