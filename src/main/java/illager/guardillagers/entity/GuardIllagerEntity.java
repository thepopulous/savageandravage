package illager.guardillagers.entity;

import com.google.common.collect.Maps;
import illager.guardillagers.GuardIllagers;
import illager.guardillagers.init.IllagerSoundsRegister;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.OpenDoorGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.entity.monster.AbstractRaiderEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.potion.EffectInstance;
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
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.raid.Raid;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class GuardIllagerEntity extends AbstractIllagerEntity {


    private static final UUID MODIFIER_UUID = UUID.fromString("5CD17E52-A79A-43D3-A529-90FDE04B181E");
	private static final AttributeModifier MODIFIER = (new AttributeModifier(MODIFIER_UUID, "Drinking speed penalty", -0.25D, AttributeModifier.Operation.ADDITION)).setSaved(false);
	private static final DataParameter<Boolean> IS_DRINKING = EntityDataManager.createKey(GuardIllagerEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Integer> GUARD_LEVEL = EntityDataManager.createKey(GuardIllagerEntity.class, DataSerializers.VARINT);

    private int potionUseTimer;

    public double prevCapeX, prevCapeY, prevCapeZ;
    public double capeX, capeY, capeZ;

	public GuardIllagerEntity(EntityType<? extends GuardIllagerEntity> type, World worldIn) {
		super(type, worldIn);
		this.setDropChance(EquipmentSlotType.OFFHAND, 0.4F);
		((GroundPathNavigator) this.getNavigator()).setBreakDoors(true);
		this.experienceValue = 6;
    }

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(0, new SwimGoal(this));
		this.goalSelector.addGoal(2, new OpenDoorGoal(this, true));
		this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0D, true));
		this.goalSelector.addGoal(8, new RandomWalkingGoal(this, 0.75D));
		this.goalSelector.addGoal(9, new LookAtGoal(this, PlayerEntity.class, 3.0F, 1.0F));
		this.goalSelector.addGoal(10, new LookAtGoal(this, MobEntity.class, 8.0F));
		this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, AbstractRaiderEntity.class)).setCallsForHelp());
		this.targetSelector.addGoal(2, (new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true)).setUnseenMemoryTicks(300));
		this.targetSelector.addGoal(3, (new NearestAttackableTargetGoal<>(this, AbstractVillagerEntity.class, false)).setUnseenMemoryTicks(300));
		this.targetSelector.addGoal(3, (new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, false)).setUnseenMemoryTicks(300));
    }

    protected void registerAttributes() {
        super.registerAttributes();

        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double) 0.348F);
        this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(22.0D);
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(24.0D);
        this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0D);
        this.getAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(6.0D);
    }

    protected void registerData() {

        super.registerData();

        this.getDataManager().register(IS_DRINKING, false);

        this.getDataManager().register(GUARD_LEVEL, 1);

    }

    public void setDrinkingPotion(boolean drinkingPotion) {
        this.getDataManager().set(IS_DRINKING, drinkingPotion);
    }

    public boolean isDrinkingPotion() {
        return this.getDataManager().get(IS_DRINKING);
    }

    public int getGuardLevel() {

        return this.dataManager.get(GUARD_LEVEL).intValue();

    }

    public void setGuardLevel(int level) {
        this.dataManager.set(GUARD_LEVEL, level);
        if (level > 0) {
            this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(23.0D + level * 4);
            this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0D + level);
            this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(22.0D + level);
            this.getAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(7.0D);
        }

        if (level == 2) {
            this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double) 0.368F);
        }
    }

	public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);

		compound.putInt("GuardLevel", this.getGuardLevel());
    }


	public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);

        this.setGuardLevel(compound.getInt("GuardLevel"));
    }

    public void livingTick() {
        if (!this.world.isRemote) {
            if (this.getGuardLevel() >= 3) {

                if (this.isDrinkingPotion()) {
                    if (this.potionUseTimer-- <= 0) {
                        this.setDrinkingPotion(false);
                        ItemStack itemstack = this.getHeldItemOffhand();
			    this.setItemStackToSlot(EquipmentSlotType.OFFHAND, new ItemStack(Items.SHIELD));
                        if (itemstack.getItem() == Items.POTION) {
				List<EffectInstance> list = PotionUtils.getEffectsFromStack(itemstack);
                            if (list != null) {
				    for (EffectInstance potioneffect : list) {
					    this.addPotionEffect(new EffectInstance(potioneffect));
                                }
                            }
                        }

                        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).removeModifier(MODIFIER);
                    }
                } else {
			Potion potiontype = null;

                    if (this.rand.nextFloat() < 0.004F && this.getHealth() < this.getMaxHealth()) {
			    potiontype = Potions.HEALING;
		    } else if (this.rand.nextFloat() < 0.008F && this.getAttackTarget() != null && !this.isPotionActive(Effects.SPEED) && this.getAttackTarget().getDistanceSq(this) > 121.0D) {
			    potiontype = Potions.SWIFTNESS;
                    }

                    if (potiontype != null) {
			    this.setItemStackToSlot(EquipmentSlotType.OFFHAND, PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), potiontype));
                        this.potionUseTimer = this.getHeldItemOffhand().getUseDuration();
                        this.setDrinkingPotion(true);
                        this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_WITCH_DRINK, this.getSoundCategory(), 1.0F, 0.8F + this.rand.nextFloat() * 0.4F);
                        IAttributeInstance iattributeinstance = this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
                        iattributeinstance.removeModifier(MODIFIER);
                        iattributeinstance.applyModifier(MODIFIER);
                    }
                }
            }
        }
        super.livingTick();
    }

    @Override
    public void tick() {
        super.tick();
        this.updateCape();
    }


    private void updateCape() {
        double elasticity = 0.25;
        double gravity = -0.1;

        this.prevCapeX = this.capeX;
        this.prevCapeY = this.capeY;
        this.prevCapeZ = this.capeZ;

        this.capeY += gravity;

        this.capeX += (this.posX - this.capeX) * elasticity;
        this.capeY += (this.posY - this.capeY) * elasticity;
        this.capeZ += (this.posZ - this.capeZ) * elasticity;
    }

	public void setRevengeTarget(@Nullable LivingEntity livingBase) {
        super.setRevengeTarget(livingBase);
        if (livingBase != null) {
		if (livingBase instanceof PlayerEntity) {

			if (!((PlayerEntity) livingBase).isCreative() && this.isAlive()) {
                    this.world.setEntityState(this, (byte) 13);
                }
            }
        }

    }

    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else if (source.isProjectile()) {
            return super.attackEntityFrom(source, amount * 0.95F);
        } else {
            return super.attackEntityFrom(source, amount);
        }
    }

    @Nullable
    @Override
    protected ResourceLocation getLootTable() {
        return GuardIllagers.LOOT_TABLE;
    }

    @OnlyIn(Dist.CLIENT)
    public AbstractIllagerEntity.ArmPose getArmPose() {
	    return this.isAggressive() ? AbstractIllagerEntity.ArmPose.ATTACKING : AbstractIllagerEntity.ArmPose.CROSSED;
    }


    public void setHomePos() {
        this.setHomePosAndDistance(new BlockPos(this), 26);
    }

    @Nullable
    @Override
    public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
	    ILivingEntityData ientitylivingdata = super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
	    this.setEquipmentBasedOnDifficulty(difficultyIn);
	    this.setEnchantmentBasedOnDifficulty(difficultyIn);
        return ientitylivingdata;
    }

	@Override
	public SoundEvent getRaidLossSound() {
		return SoundEvents.ENTITY_VINDICATOR_CELEBRATE;
	}

    /**
     * Gives armor or weapon for entity based on given DifficultyInstance
     */
    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {
        if (getGuardLevel() >= 3) {
		this.setItemStackToSlot(EquipmentSlotType.OFFHAND, getIllagerShield());
	}
	    this.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.IRON_SWORD));
    }

	@Override
	public void func_213660_a(int p_213660_1_, boolean p_213660_2_) {
		ItemStack itemstack = new ItemStack(Items.IRON_SWORD);
		Raid raid = this.getRaid();
		int i = 1;
		if (p_213660_1_ > raid.getWaves(Difficulty.NORMAL)) {
			i = 2;
		}

		boolean flag = this.rand.nextFloat() <= raid.func_221308_w();
		boolean flag2 = this.rand.nextFloat() <= 0.25;
		boolean flag3 = this.rand.nextFloat() <= 0.15;
		if (flag) {
			Map<Enchantment, Integer> map = Maps.newHashMap();
			map.put(Enchantments.SHARPNESS, i);
			EnchantmentHelper.setEnchantments(map, itemstack);
			if (flag2) {
				ItemStack stack2 = getIllagerShield();
				Map<Enchantment, Integer> map2 = Maps.newHashMap();
				map2.put(Enchantments.UNBREAKING, i);
				EnchantmentHelper.setEnchantments(map2, stack2);

				this.setItemStackToSlot(EquipmentSlotType.OFFHAND, stack2);
			}

        }

		this.setItemStackToSlot(EquipmentSlotType.MAINHAND, itemstack);
	}

	public static ItemStack getIllagerShield() {
		ItemStack banner = Raid.createIllagerBanner();

		ItemStack shield = new ItemStack(Items.SHIELD, 1);

		applyBanner(banner, shield);

		return shield;
	}


	private static void applyBanner(ItemStack banner, ItemStack shield) {
		CompoundNBT bannerNBT = banner.getChildTag("BlockEntityTag");

		CompoundNBT shieldNBT = bannerNBT == null ? new CompoundNBT() : bannerNBT.copy();

		shield.setTagInfo("BlockEntityTag", shieldNBT);
    }

    protected void updateAITasks() {
        super.updateAITasks();
    }


    @Override
    public boolean canSpawn(IWorld worldIn, SpawnReason spawnReasonIn) {
        int i = MathHelper.floor(this.posX);
        int j = MathHelper.floor(this.getBoundingBox().minY);
        int k = MathHelper.floor(this.posZ);
        BlockPos blockpos = new BlockPos(i, j, k);
	    return this.world.getDifficulty() != Difficulty.PEACEFUL && !this.world.canBlockSeeSky(new BlockPos(this.posX, this.posY + (double) this.getEyeHeight(), this.posZ)) && this.world.getLight(blockpos) > 6;
    }

    /**
     * Returns whether this Entity is on the same team as the given Entity.
     */
    public boolean isOnSameTeam(Entity entityIn) {
        if (super.isOnSameTeam(entityIn)) {
            return true;
	} else if (entityIn instanceof LivingEntity && ((LivingEntity) entityIn).getCreatureAttribute() == CreatureAttribute.ILLAGER) {
            return this.getTeam() == null && entityIn.getTeam() == null;
        } else {
            return false;
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        if (this.isAggressive()) {
            return IllagerSoundsRegister.GUARDILLAGER_ANGRY;
        } else {
            return IllagerSoundsRegister.GUARDILLAGER_AMBIENT;
        }
    }

    @Override
    protected SoundEvent getDeathSound() {
        return IllagerSoundsRegister.GUARDILLAGER_DIE;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return IllagerSoundsRegister.GUARDILLAGER_HURT;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState blockState) {

        SoundType soundtype = blockState.getSoundType(world, pos, this);

        SoundEvent soundEvent = IllagerSoundsRegister.GUARDILLAGER_STEP;

	    if (soundtype == SoundType.SNOW) {
            this.playSound(soundtype.getStepSound(), soundtype.getVolume() * 0.15F, soundtype.getPitch());
		    this.playSound(soundEvent, 0.2F, soundtype.getPitch());
        } else if (!blockState.getMaterial().isLiquid()) {
            this.playSound(soundtype.getStepSound(), soundtype.getVolume() * 0.15F, soundtype.getPitch());
            this.playSound(soundEvent, 0.2F, soundtype.getPitch());
        }
    }
}