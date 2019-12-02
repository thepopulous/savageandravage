package populousteam.savageandravage.utils;

import com.google.common.collect.Lists;
import net.minecraft.item.DyeColor;
import net.minecraft.item.FireworkRocketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.raid.Raid;

import java.util.List;

public class MiscUtil {

    public static ItemStack getIllagerShield() {
        ItemStack banner = Raid.createIllagerBanner();

        ItemStack shield = new ItemStack(Items.SHIELD, 1);

        applyBanner(banner, shield);

        shield.setDisplayName((new TranslationTextComponent("block.savageandravage.ominous_shield")).applyTextStyle(TextFormatting.GOLD));

        return shield;
    }


    private static void applyBanner(ItemStack banner, ItemStack shield) {
        CompoundNBT bannerNBT = banner.getChildTag("BlockEntityTag");

        CompoundNBT shieldNBT = bannerNBT == null ? new CompoundNBT() : bannerNBT.copy();

        shield.setTagInfo("BlockEntityTag", shieldNBT);
    }

    public static ItemStack makeFirework(DyeColor color, int flightTime) {
        ItemStack itemstack = new ItemStack(Items.FIREWORK_ROCKET, 1);
        ItemStack itemstack1 = new ItemStack(Items.FIREWORK_STAR);
        CompoundNBT compoundnbt = itemstack1.getOrCreateChildTag("Explosion");
        List<Integer> list = Lists.newArrayList();
        list.add(color.getFireworkColor());
        compoundnbt.putIntArray("Colors", list);
        compoundnbt.putByte("Type", (byte) FireworkRocketItem.Shape.CREEPER.func_196071_a());
        CompoundNBT compoundnbt1 = itemstack.getOrCreateChildTag("Fireworks");
        ListNBT listnbt = new ListNBT();
        CompoundNBT compoundnbt2 = itemstack1.getChildTag("Explosion");
        if (compoundnbt2 != null) {
            listnbt.add(compoundnbt2);
        }

        compoundnbt1.putByte("Flight", (byte) flightTime);
        if (!listnbt.isEmpty()) {
            compoundnbt1.put("Explosions", listnbt);
        }

        return itemstack;
    }
}