package com.populousteam.savageandravage.init;

import com.populousteam.savageandravage.SavageAndRavageCore;
import com.populousteam.savageandravage.effect.BestialBrewEffect;
import com.populousteam.savageandravage.effect.TenacityEffect;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SavageAndRavageCore.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SavageEffectRegistry {
    public static final Effect TENACITY = new TenacityEffect(EffectType.BENEFICIAL, 0xe92f2f).addAttributesModifier(SharedMonsterAttributes.ATTACK_DAMAGE, "648D7064-6A60-4F59-8ABE-C2C23A6DD7A9", 0.0D, AttributeModifier.Operation.ADDITION).addAttributesModifier(SharedMonsterAttributes.ARMOR_TOUGHNESS, "7E0292F2-9434-48D5-A29F-9583AF7DF27F", 0.0D, AttributeModifier.Operation.ADDITION);
    public static final Effect BEAST = new BestialBrewEffect(EffectType.NEUTRAL, 0x84199);
    public static final Effect BADOMEN = new Effect(EffectType.NEUTRAL, 745784) {
        /**
         * checks if Potion effect is ready to be applied this tick.
         */
        public boolean isReady(int duration, int amplifier) {
            return true;
        }

        public void performEffect(LivingEntity entityLivingBaseIn, int amplifier) {
            if (entityLivingBaseIn instanceof ServerPlayerEntity && !entityLivingBaseIn.isSpectator()) {
                ServerPlayerEntity serverplayerentity = (ServerPlayerEntity) entityLivingBaseIn;
                ServerWorld serverworld = serverplayerentity.getServerWorld();
                if (serverworld.getDifficulty() == Difficulty.PEACEFUL) {
                    return;
                }

                if (serverworld.func_217483_b_(new BlockPos(entityLivingBaseIn))) {
                    SavageAndRavageCore.instance.revampRaid.badOmenTick(serverplayerentity);
                }
            }

        }
    };

    @SubscribeEvent
    public static void registerEffect(RegistryEvent.Register<Effect> event) {
        event.getRegistry().register(BADOMEN.setRegistryName("bad_omen"));

        event.getRegistry().register(TENACITY.setRegistryName("tenacity"));
        event.getRegistry().register(BEAST.setRegistryName("beastial"));
    }
}
