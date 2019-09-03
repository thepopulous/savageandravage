package illager.savageandravage.effect;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

import javax.annotation.Nullable;

public class TenacityEffect extends Effect {
    protected final double bonusPerLevel = 1.0D;

    public TenacityEffect(EffectType p_i50395_1_, int p_i50395_2_) {
        super(p_i50395_1_, p_i50395_2_);
    }

    public double getAttributeModifierAmount(int amplifier, AttributeModifier modifier) {
        return this.bonusPerLevel * (double) (amplifier + 1);
    }


    @Override
    public void affectEntity(@Nullable Entity source, @Nullable Entity indirectSource, LivingEntity entityLivingBaseIn, int amplifier, double health) {
        super.affectEntity(source, indirectSource, entityLivingBaseIn, amplifier, health);
    }
}