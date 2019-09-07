package illager.savageandravage.item;

import illager.savageandravage.init.SavageEffectRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class SinisterHornItem extends Item {
    public SinisterHornItem(Properties group) {
        super(group);
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        playerIn.setActiveHand(handIn);

        return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
    }

    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
        if (!(entityLiving instanceof PlayerEntity)) {
            if (entityLiving instanceof AbstractIllagerEntity) {
                for (LivingEntity aroundEntity : entityLiving.world.getEntitiesWithinAABB(AbstractIllagerEntity.class, entityLiving.getBoundingBox().grow(10.0D))) {
                    aroundEntity.addPotionEffect(new EffectInstance(Effects.STRENGTH, 1200, 0));
                    aroundEntity.addPotionEffect(new EffectInstance(Effects.SPEED, 1200, 0));
                }

                worldIn.playSound((PlayerEntity) null, entityLiving.posX, entityLiving.posY, entityLiving.posZ, SoundEvents.EVENT_RAID_HORN, SoundCategory.PLAYERS, 64.0F, 1.0F);
            } else {
                for (LivingEntity aroundEntity : entityLiving.world.getEntitiesWithinAABB(entityLiving.getClass(), entityLiving.getBoundingBox().grow(10.0D))) {
                    aroundEntity.addPotionEffect(new EffectInstance(Effects.STRENGTH, 1200, 0));
                    aroundEntity.addPotionEffect(new EffectInstance(Effects.SPEED, 1200, 0));
                }

                worldIn.playSound((PlayerEntity) null, entityLiving.posX, entityLiving.posY, entityLiving.posZ, SoundEvents.EVENT_RAID_HORN, SoundCategory.PLAYERS, 64.0F, 1.0F);

            }
        } else {
            PlayerEntity playerIn = ((PlayerEntity) entityLiving);

            if (!playerIn.abilities.isCreativeMode && worldIn.rand.nextFloat() < 0.25F) {
                stack.shrink(1);
            }

            worldIn.playSound((PlayerEntity) null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.EVENT_RAID_HORN, SoundCategory.PLAYERS, 64.0F, 1.0F);

            for (PlayerEntity aroundPlayer : playerIn.world.getEntitiesWithinAABB(PlayerEntity.class, playerIn.getBoundingBox().grow(10.0D))) {
                aroundPlayer.addPotionEffect(new EffectInstance(SavageEffectRegistry.TENACITY, 4800, 0));
            }

            playerIn.addStat(Stats.ITEM_USED.get(this));
        }


        return stack;
    }

    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }

    public int getUseDuration(ItemStack stack) {
        return 32;
    }
}
