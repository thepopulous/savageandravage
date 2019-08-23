package illager.savageandravage.item;

import illager.savageandravage.SavageAndRavageCore;
import illager.savageandravage.client.model.PoultryFarmerHatModel;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class PoultryFarmerHatItem extends ArmorItem {
    public PoultryFarmerHatItem(IArmorMaterial materialIn, EquipmentSlotType slots, Item.Properties properties) {
        super(materialIn, slots, properties);
    }


    @OnlyIn(Dist.CLIENT)
    public BipedModel getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, BipedModel _default) {
        return new PoultryFarmerHatModel();
    }

    @Nullable
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
        return SavageAndRavageCore.MODID + ":textures/models/armor/poultry_farmer_hat_model.png";
    }

}