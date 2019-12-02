package com.populousteam.savageandravage.item;

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
import com.populousteam.savageandravage.SavageAndRavage;
import com.populousteam.savageandravage.client.model.GuardHatModel;

import javax.annotation.Nullable;

public class GuardHelmItem extends ArmorItem {
    public GuardHelmItem(IArmorMaterial materialIn, EquipmentSlotType slots, Item.Properties properties) {
        super(materialIn, slots, properties);
    }


    @OnlyIn(Dist.CLIENT)
    public BipedModel getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, BipedModel _default) {
        return new GuardHatModel();
    }

    @Nullable
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
        return SavageAndRavage.MOD_ID + ":textures/models/armor/guard_hat.png";
    }

}
