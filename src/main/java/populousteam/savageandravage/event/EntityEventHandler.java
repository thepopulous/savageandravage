package populousteam.savageandravage.event;

import populousteam.savageandravage.entity.SavagelingEntity;
import populousteam.savageandravage.entity.SkeletonVillagerEntity;
import populousteam.savageandravage.entity.ai.FollowHeldHatPlayer;
import populousteam.savageandravage.entity.illager.DefenderEntity;
import populousteam.savageandravage.entity.illager.GrieferIllagerEntity;
import populousteam.savageandravage.entity.illager.PoultryFarmerIllagerEntity;
import populousteam.savageandravage.entity.illager.ScavengersEntity;
import populousteam.savageandravage.entity.task.RevampVillagerTasks;
import populousteam.savageandravage.init.SavageEffectRegistry;
import populousteam.savageandravage.init.SavageEntityRegistry;
import populousteam.savageandravage.init.SavageItems;
import populousteam.savageandravage.utils.MiscUtil;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.schedule.Activity;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.EggEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EntityEventHandler {
    //エンティティのAI関係
    @SubscribeEvent
    public void onEntityJoin(EntityJoinWorldEvent event) {
        World world = event.getWorld();
        if (event.getEntity() instanceof AbstractVillagerEntity) {
            AbstractVillagerEntity villager = (AbstractVillagerEntity) event.getEntity();
            villager.goalSelector.addGoal(1, new AvoidEntityGoal<>(villager, GrieferIllagerEntity.class, 16.0F, 0.7D, 0.8D));
            villager.goalSelector.addGoal(1, new AvoidEntityGoal<>(villager, DefenderEntity.class, 16.0F, 0.7D, 0.8D));
            villager.goalSelector.addGoal(1, new AvoidEntityGoal<>(villager, PoultryFarmerIllagerEntity.class, 16.0F, 0.65D, 0.75D));
            villager.goalSelector.addGoal(1, new AvoidEntityGoal<>(villager, ScavengersEntity.class, 16.0F, 0.6D, 0.7D));
        }

        if (event.getEntity() instanceof VillagerEntity) {
            VillagerEntity villager = (VillagerEntity) event.getEntity();
            villager.getBrain().registerActivity(Activity.CORE, RevampVillagerTasks.core(villager.getVillagerData().getProfession(), (float) villager.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getValue()));
            villager.getBrain().registerActivity(Activity.RAID, RevampVillagerTasks.raid(villager.getVillagerData().getProfession(), (float) villager.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getValue()));
        }

        if (event.getEntity() instanceof ChickenEntity) {
            ChickenEntity chicken = (ChickenEntity) event.getEntity();
            chicken.goalSelector.addGoal(1, new FollowHeldHatPlayer(chicken, 1.0D, 2.5F, 7.0F));
        }

       /* if (event.getEntity() instanceof IronGolemEntity) {
            IronGolemEntity ironGolem = (IronGolemEntity) event.getEntity();
            ironGolem.targetSelector.removeGoal(new NearestAttackableTargetGoal<>(ironGolem, MobEntity.class, 5, false, false, (p_213619_0_) -> {
                return p_213619_0_ instanceof IMob && !(p_213619_0_ instanceof CreeperEntity);
            }));
            ironGolem.targetSelector.addGoal(3,new NearestAttackableTargetGoal<>(ironGolem, MobEntity.class, 5, false, false, (p_213619_0_) -> {
                return p_213619_0_ instanceof IMob && !(p_213619_0_ instanceof CreeperEntity);
            }));
        }*/

        if (event.getEntity() instanceof AbstractIllagerEntity && !(event.getEntity() instanceof EvokerEntity)) {

            AbstractIllagerEntity pillager = (AbstractIllagerEntity) event.getEntity();

            BlockPos pos = pillager.getPosition();

            /*if (pillager.isLeader() && pillager.getRaid() == null) {
                PatrollerEntity patrollerentity = SavageEntityRegistry.SCAVENGERS.create(world);

                patrollerentity.setLeader(true);
                patrollerentity.resetPatrolTarget();

                for (int i = 0; i < 1 + world.rand.nextInt(2); i++) {
                    GuardIllagerEntity guardilalger = SavageEntityRegistry.GUARD_ILLAGER.create(world);

                    guardilalger.setPosition((double) pillager.getPosition().getX(), (double) pillager.getPosition().getY(), (double) pillager.getPosition().getZ());
                    guardilalger.onInitialSpawn(world, world.getDifficultyForLocation(pillager.getPosition()), SpawnReason.PATROL, (ILivingEntityData) null, (CompoundNBT) null);
                    world.addEntity(guardilalger);
                }

                patrollerentity.setPosition((double) pillager.getPosition().getX(), (double) pillager.getPosition().getY(), (double) pillager.getPosition().getZ());
                patrollerentity.onInitialSpawn(world, world.getDifficultyForLocation(pillager.getPosition()), SpawnReason.PATROL, (ILivingEntityData) null, (CompoundNBT) null);
                world.addEntity(patrollerentity);
                pillager.remove();
            }*/

            if (world.rand.nextFloat() < 0.01F && pillager instanceof PillagerEntity) {
                DyeColor dyecolor = DyeColor.values()[pillager.world.rand.nextInt(DyeColor.values().length)];
                int i = pillager.world.rand.nextInt(3);

                ((PillagerEntity) pillager).setHeldItem(Hand.OFF_HAND, MiscUtil.makeFirework(dyecolor, i));
            } else {

                if (pillager.getRaid() == null && pillager.isLeader() && event.getEntity().getType() != SavageEntityRegistry.SCAVENGER) {
                    pillager.remove();

                    ScavengersEntity scavenger = SavageEntityRegistry.SCAVENGER.create(world);

                    scavenger.setLeader(true);
                    scavenger.resetPatrolTarget();

                    scavenger.setPosition((double) pos.getX(), (double) pos.getY(), (double) pos.getZ());
                    scavenger.onInitialSpawn(world, world.getDifficultyForLocation(pos), SpawnReason.PATROL, (ILivingEntityData) null, (CompoundNBT) null);
                    world.addEntity(scavenger);


                    for (int i = 0; i <= 1 + world.rand.nextInt(1); i++) {
                        DefenderEntity defenderEntity = SavageEntityRegistry.DEFENDER.create(world);

                        defenderEntity.setLeader(false);
                        defenderEntity.resetPatrolTarget();
                        defenderEntity.setPosition((double) pos.getX(), (double) pos.getY(), (double) pos.getZ());
                        defenderEntity.onInitialSpawn(world, world.getDifficultyForLocation(pos), SpawnReason.PATROL, (ILivingEntityData) null, (CompoundNBT) null);
                        world.addEntity(defenderEntity);
                    }

                    for (int i = 0; i <= 3 + world.rand.nextInt(4); i++) {
                        PillagerEntity pillager2 = EntityType.PILLAGER.create(world);

                        pillager2.setLeader(false);
                        pillager2.resetPatrolTarget();
                        pillager2.setPosition((double) pos.getX(), (double) pos.getY(), (double) pos.getZ());
                        pillager2.onInitialSpawn(world, world.getDifficultyForLocation(pos), SpawnReason.PATROL, (ILivingEntityData) null, (CompoundNBT) null);
                        world.addEntity(pillager2);
                    }
                }
            }
        }

        if (event.getEntity().getType() == EntityType.SKELETON && (double) world.getRandom().nextFloat() < 0.02D) {
            SkeletonEntity skeleton = (SkeletonEntity) event.getEntity();

            BlockPos pos = skeleton.getPosition();

            skeleton.remove();

            SkeletonVillagerEntity skeletonVillager = SavageEntityRegistry.SKELETONVILLAGER.create(world);

            skeletonVillager.setLocationAndAngles(pos.getX(), (double) pos.getY(), (double) pos.getZ(), 0.0F, 0.0F);
            skeletonVillager.onInitialSpawn(world, world.getDifficultyForLocation(new BlockPos(pos)), SpawnReason.NATURAL, (ILivingEntityData) null, (CompoundNBT) null);
            world.addEntity(skeletonVillager);
        }
    }

    @SubscribeEvent
    public void onEntityHurt(LivingHurtEvent event) {
        LivingEntity livingEntity = event.getEntityLiving();


        if (livingEntity.isAlive() && event.getSource().getImmediateSource() instanceof EggEntity && !(livingEntity instanceof SavagelingEntity)) {
            for (SavagelingEntity savageling : livingEntity.world.getEntitiesWithinAABB(SavagelingEntity.class, livingEntity.getBoundingBox().grow(20.0D))) {
                savageling.setAttackTarget(livingEntity);
            }
        }

        if (event.getSource().getImmediateSource() instanceof LivingEntity) {
            LivingEntity attacker = (LivingEntity) event.getSource().getImmediateSource();

            if (attacker.isAlive() && attacker.getActivePotionEffect(SavageEffectRegistry.TENACITY) != null) {
                if (attacker.getPersistentData().contains("Tenacity")) {
                    attacker.getPersistentData().putFloat("Tenacity", attacker.getPersistentData().getFloat("Tenacity") + event.getAmount());
                }
            }
        }
    }

    @SubscribeEvent
    public void onEntityUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity livingEntity = event.getEntityLiving();

        if (livingEntity.getActivePotionEffect(Effects.BAD_OMEN) != null && livingEntity.ticksExisted % 2 == 0) {
            EffectInstance effectinstance1 = livingEntity.getActivePotionEffect(SavageEffectRegistry.BADOMEN);
            int i = 1;
            if (effectinstance1 != null) {
                i += effectinstance1.getAmplifier();
                livingEntity.removeActivePotionEffect(SavageEffectRegistry.BADOMEN);
            } else {
                --i;
            }

            i = MathHelper.clamp(i, 0, 5);

            livingEntity.removeActivePotionEffect(Effects.BAD_OMEN);
            livingEntity.addPotionEffect(new EffectInstance(SavageEffectRegistry.BADOMEN, 120000, i));
        }


        if (livingEntity.isAlive() && livingEntity.getActivePotionEffect(SavageEffectRegistry.TENACITY) != null) {
            if (!livingEntity.getPersistentData().contains("Tenacity")) {
                livingEntity.getPersistentData().putFloat("Tenacity", 0);
            }
        }

        if (livingEntity.isAlive() && livingEntity.getActivePotionEffect(SavageEffectRegistry.TENACITY) == null) {
            if (livingEntity.getPersistentData().contains("Tenacity")) {
                livingEntity.getPersistentData().remove("Tenacity");
            }
        }
    }

    @SubscribeEvent
    public void onEntityDrop(LivingDropsEvent event) {
        LivingEntity livingEntity = event.getEntityLiving();
        if (livingEntity instanceof CreeperEntity) {

            if (event.getSource().isExplosion()) {
                livingEntity.entityDropItem(new ItemStack(SavageItems.CREEPER_SPORES, 1 + livingEntity.world.rand.nextInt(11)));
            }
        }
    }


    @SubscribeEvent
    public void onEntityAttack(AttackEntityEvent event) {
        PlayerEntity livingEntity = event.getEntityPlayer();

        if (livingEntity.isAlive() && livingEntity.getActivePotionEffect(SavageEffectRegistry.TENACITY) != null) {
            if (livingEntity.getPersistentData().contains("Tenacity")) {
                EffectInstance effectinstance1 = livingEntity.getActivePotionEffect(SavageEffectRegistry.TENACITY);
                if (effectinstance1 != null && livingEntity.getPersistentData().getFloat("Tenacity") >= 35 + 5 * effectinstance1.getAmplifier()) {
                    int i = 1;
                    int i2 = 30;
                    if (effectinstance1 != null) {
                        i += effectinstance1.getAmplifier();
                        i2 = effectinstance1.getDuration();
                        livingEntity.removeActivePotionEffect(SavageEffectRegistry.TENACITY);
                    } else {
                        --i;
                    }

                    i = MathHelper.clamp(i, 0, 7);

                    livingEntity.addPotionEffect(new EffectInstance(SavageEffectRegistry.TENACITY, i2, i));
                    livingEntity.getPersistentData().putFloat("Tenacity", 0);
                }
            }
        }
    }
}
