package illager.savageandravage.entity.illager;

import illager.savageandravage.entity.ai.*;
import illager.savageandravage.entity.path.GroundFencePathNavigator;
import illager.savageandravage.entity.projectile.FakeThrownRiderEntity;
import illager.savageandravage.init.SavageLootTables;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.monster.AbstractRaiderEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.IFlyingAnimal;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.EggEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.*;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class PoultryFarmerIllagerEntity extends AbstractHouseIllagerEntity implements IRangedAttackMob {

    private final Inventory inventory = new Inventory(5);

    public PoultryFarmerIllagerEntity(EntityType<? extends PoultryFarmerIllagerEntity> type, World worldIn) {
        super(type, worldIn);
        ((GroundFencePathNavigator) this.getNavigator()).setBreakDoors(true);
        this.experienceValue = 6;
        this.setCanPickUpLoot(true);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new OpenDoorGoal(this, true));
        this.goalSelector.addGoal(1, new OpenGateGoal(this, true));
        this.goalSelector.addGoal(2, new MoveToHomeAndAtNightGoal(this, 18.0D, 0.7D));
        this.goalSelector.addGoal(3, new RangedAttackWithChickenGoal(this, 0.75D, 60, 16.0F));
        this.goalSelector.addGoal(4, new WakeUpGoal(this));
        this.goalSelector.addGoal(5, new GotoBedGoal(this, 0.7D));
        this.goalSelector.addGoal(6, new CropHarvestGoal(this, 0.7D));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomWalkingGoal(this, 0.7D));
        this.goalSelector.addGoal(9, new LookAtGoal(this, PlayerEntity.class, 3.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtGoal(this, MobEntity.class, 8.0F));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, AbstractRaiderEntity.class)).setCallsForHelp());
        this.targetSelector.addGoal(2, (new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true, true)).setUnseenMemoryTicks(300));
        this.targetSelector.addGoal(3, (new NearestAttackableTargetGoal<>(this, AbstractVillagerEntity.class, true, true)).setUnseenMemoryTicks(300));
        this.targetSelector.addGoal(3, (new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true, true)).setUnseenMemoryTicks(300));
    }

    protected PathNavigator createNavigator(World worldIn) {
        return new GroundFencePathNavigator(this, worldIn);
    }

    @Override
    public void func_213660_a(int p_213660_1_, boolean p_213660_2_) {

    }

    @Override
    public SoundEvent getRaidLossSound() {
        return SoundEvents.ENTITY_VINDICATOR_CELEBRATE;
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
    protected ResourceLocation getLootTable() {
        return SavageLootTables.POULTRY_FARMER;
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();

        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double) 0.348F);
        this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16.0D);
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(24.0D);
        this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0D);
    }

 /*   @Override
    public void tick() {
        super.tick();
        if((this.world.isDaytime() || this.getAttackTarget() != null) && this.isSleeping()){
            this.wakeUp();
        }
    }*/

    @Nullable
    @Override
    public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        /*ChickenEntity chickenentity1 = EntityType.CHICKEN.create(this.world);
        chickenentity1.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
        chickenentity1.onInitialSpawn(worldIn, difficultyIn, SpawnReason.JOCKEY, (ILivingEntityData)null, (CompoundNBT)null);
        worldIn.addEntity(chickenentity1);
        chickenentity1.startRiding(this);*/
        this.setEquipmentBasedOnDifficulty(difficultyIn);
        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {
        this.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.EGG));
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);

        ListNBT listnbt = new ListNBT();

        for (int i = 0; i < this.inventory.getSizeInventory(); ++i) {
            ItemStack itemstack = this.inventory.getStackInSlot(i);
            if (!itemstack.isEmpty()) {
                listnbt.add(itemstack.write(new CompoundNBT()));
            }
        }

        compound.put("Inventory", listnbt);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);

        ListNBT listnbt = compound.getList("Inventory", 10);

        for (int i = 0; i < listnbt.size(); ++i) {
            ItemStack itemstack = ItemStack.read(listnbt.getCompound(i));
            if (!itemstack.isEmpty()) {
                this.inventory.addItem(itemstack);
            }
        }

        this.setCanPickUpLoot(true);
    }

    public Inventory getInventory() {
        return inventory;
    }

    protected void updateEquipmentIfNeeded(ItemEntity itemEntity) {
        ItemStack itemstack = itemEntity.getItem();
        Item item = itemstack.getItem();
        if (this.isFoodsOrCrop(item)) {
            ItemStack itemstack1 = this.inventory.addItem(itemstack);
            if (itemstack1.isEmpty()) {
                itemEntity.remove();
            } else {
                itemstack.setCount(itemstack1.getCount());
            }
        } else {
            super.updateEquipmentIfNeeded(itemEntity);
        }
    }

    private boolean isFoodsOrCrop(Item item) {
        return item == Items.BREAD || item == Items.WHEAT || item == Items.WHEAT_SEEDS;
    }


    public double getMountedYOffset() {
        return 1.95D;
    }


    @Override
    public void updatePassenger(Entity passenger) {
        super.updatePassenger(passenger);

        if (passenger instanceof ChickenEntity || passenger instanceof IFlyingAnimal) {
            Vec3d vec3d = this.getMotion();
            if (!this.onGround && vec3d.y < 0.0D) {
                this.setMotion(vec3d.mul(1.0D, 0.6D, 1.0D));
                this.fallDistance = 0.0F;
            }

        }

    }

    @Override
    public boolean isOnSameTeam(Entity entityIn) {
        if (super.isOnSameTeam(entityIn)) {
            return true;
        } else if (entityIn instanceof LivingEntity && ((LivingEntity) entityIn).getCreatureAttribute() == CreatureAttribute.ILLAGER) {
            return this.getTeam() == null && entityIn.getTeam() == null;
        } else {
            return false;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public ArmPose getArmPose() {
        if (this.isAggressive()) {
            return ArmPose.ATTACKING;
        } else {
            return this.func_213656_en() ? ArmPose.CELEBRATING : ArmPose.CROSSED;
        }
    }

    @Override
    public void attackEntityWithRangedAttack(LivingEntity target, float distanceFactor) {
        //Use projectile to make it look like you threw a Rider
        if (!this.getPassengers().isEmpty() && this.getPassengers().get(0) != null) {
            this.swingArm(Hand.MAIN_HAND);

            this.world.playSound((PlayerEntity) null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_EGG_THROW, SoundCategory.PLAYERS, 0.5F, 0.4F / (this.world.rand.nextFloat() * 0.4F + 0.8F));

            FakeThrownRiderEntity fakethrown = new FakeThrownRiderEntity(this.world, this);

            double d0 = target.posY + (double) target.getEyeHeight() - (double) 1.1F;
            double d1 = target.posX - this.posX;
            double d2 = d0 - fakethrown.posY;
            double d3 = target.posZ - this.posZ;

            float f = MathHelper.sqrt(d1 * d1 + d3 * d3) * 0.05F;
            fakethrown.shoot(d1, d2 + (double) f, d3, 1.3F, 10.0F);
            this.getPassengers().get(0).startRiding(fakethrown);
            this.world.addEntity(fakethrown);

        } else {
            this.swingArm(Hand.MAIN_HAND);

            this.world.playSound((PlayerEntity) null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_EGG_THROW, SoundCategory.PLAYERS, 0.5F, 0.4F / (this.world.rand.nextFloat() * 0.4F + 0.8F));

            EggEntity eggEntity = new EggEntity(this.world, this);

            double d0 = target.posY + (double) target.getEyeHeight() - (double) 1.1F;
            double d1 = target.posX - this.posX;
            double d2 = d0 - eggEntity.posY;
            double d3 = target.posZ - this.posZ;

            float f = MathHelper.sqrt(d1 * d1 + d3 * d3) * 0.03F;
            eggEntity.shoot(d1, d2 + (double) f, d3, 1.6F, 12.0F);

            this.world.addEntity(eggEntity);
        }
    }
}
