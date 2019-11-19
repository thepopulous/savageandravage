package populousteam.savageandravage.init;

import populousteam.savageandravage.SavageAndRavage;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

public class SavageArmorMaterial {
    private static final int[] DURABILITY = new int[]{13, 15, 16, 11};
    public static final IArmorMaterial GUARD_HELM = new Builder()
            .withName(SavageAndRavage.MOD_ID + ":guard_hat")
            .withDurabilityFactor(15)
            .withDamageReductionAmounts(new int[]{3, 4, 4, 3})
            .withEnchantability(12)
            .withRepairMaterial(Ingredient.fromItems(Items.IRON_INGOT))
            .build();
    public static final IArmorMaterial POULTRY_FARMER_HAT = new Builder()
            .withName(SavageAndRavage.MOD_ID + ":poultry_farmer_hat")
            .withDurabilityFactor(9)
            .withDamageReductionAmounts(new int[]{1, 2, 2, 1})
            .withEnchantability(16)
            .withRepairMaterial(Ingredient.fromItems(Items.GRASS))
            .build();

    private static class Builder {
        private String name;
        private int durabilityFactor;
        private int[] damageReductionAmounts;
        private int enchantability;
        private SoundEvent sound = SoundEvents.ITEM_ARMOR_EQUIP_GENERIC;
        private Ingredient repairMaterial;
        private float toughness;

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withDurabilityFactor(int durabilityFactor) {
            this.durabilityFactor = durabilityFactor;
            return this;
        }

        public Builder withDamageReductionAmounts(int[] damageReductionAmount) {
            this.damageReductionAmounts = damageReductionAmount;
            return this;
        }

        public Builder withEnchantability(int enchantability) {
            this.enchantability = enchantability;
            return this;
        }

        public Builder withSound(SoundEvent sound) {
            this.sound = sound;
            return this;
        }

        public Builder withRepairMaterial(Ingredient repairMaterial) {
            this.repairMaterial = repairMaterial;
            return this;
        }

        public Builder withToughness(float toughness) {
            this.toughness = toughness;
            return this;
        }

        public IArmorMaterial build() {
            return new IArmorMaterial() {
                @Override
                public int getDurability(EquipmentSlotType slot) {
                    return DURABILITY[slot.getIndex()] * Builder.this.durabilityFactor;
                }

                @Override
                public int getDamageReductionAmount(EquipmentSlotType slot) {
                    return Builder.this.damageReductionAmounts[slot.getIndex()];
                }

                @Override
                public int getEnchantability() {
                    return Builder.this.enchantability;
                }

                @Override
                public SoundEvent getSoundEvent() {
                    return Builder.this.sound;
                }

                @Override
                public Ingredient getRepairMaterial() {
                    return Builder.this.repairMaterial;
                }

                @Override
                public String getName() {
                    return Builder.this.name;
                }

                @Override
                public float getToughness() {
                    return Builder.this.toughness;
                }
            };
        }
    }
}