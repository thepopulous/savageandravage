package illager.guardillagers.item;

import illager.guardillagers.GuardIllagers;
import illager.guardillagers.client.model.ModelGuardHat;
import net.minecraft.client.renderer.entity.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ItemGuardHelm extends ItemArmor {
    public ItemGuardHelm(IArmorMaterial materialIn, EntityEquipmentSlot slots, Builder builder) {
        super(materialIn, slots, builder);
    }


    @OnlyIn(Dist.CLIENT)
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped _default) {
        return new ModelGuardHat();
    }

    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
        return GuardIllagers.MODID + ":" + "textures/armor/guard_hat.png";
    }

}