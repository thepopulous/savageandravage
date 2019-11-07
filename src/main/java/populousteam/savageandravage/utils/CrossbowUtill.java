package populousteam.savageandravage.utils;

import com.google.common.collect.Lists;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;

import java.util.List;

public class CrossbowUtill {
    public static boolean hasChargedProjectile(ItemStack stack, Item ammoItem) {
        return getChargedProjectiles(stack).stream().anyMatch((p_220010_1_) -> {
            return p_220010_1_.getItem() == ammoItem;
        });
    }

    private static List<ItemStack> getChargedProjectiles(ItemStack stack) {
        List<ItemStack> list = Lists.newArrayList();
        CompoundNBT compoundnbt = stack.getTag();
        if (compoundnbt != null && compoundnbt.contains("ChargedProjectiles", 9)) {
            ListNBT listnbt = compoundnbt.getList("ChargedProjectiles", 10);
            if (listnbt != null) {
                for (int i = 0; i < listnbt.size(); ++i) {
                    CompoundNBT compoundnbt1 = listnbt.getCompound(i);
                    list.add(ItemStack.read(compoundnbt1));
                }
            }
        }

        return list;
    }
}
