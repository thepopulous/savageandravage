package illager.savageandravage.item;

import illager.savageandravage.init.SavageEffectRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class SinisterHornItem extends Item {
    public SinisterHornItem(Properties group) {
        super(group);
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        if (!playerIn.abilities.isCreativeMode && worldIn.rand.nextFloat() < 0.25F) {
            itemstack.shrink(1);
        }

        worldIn.playSound((PlayerEntity) null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.EVENT_RAID_HORN, SoundCategory.PLAYERS, 64.0F, 1.0F);

        for (PlayerEntity aroundPlayer : playerIn.world.getEntitiesWithinAABB(PlayerEntity.class, playerIn.getBoundingBox().grow(8.0D))) {
            aroundPlayer.addPotionEffect(new EffectInstance(SavageEffectRegistry.TENACITY, 1200, 0));
        }

        playerIn.addStat(Stats.ITEM_USED.get(this));
        return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
    }
}
