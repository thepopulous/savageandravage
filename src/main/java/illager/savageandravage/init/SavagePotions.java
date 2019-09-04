package illager.savageandravage.init;

import net.minecraft.potion.Potion;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static illager.savageandravage.SavageAndRavageCore.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SavagePotions {

    @SubscribeEvent
    public static void  registerPotion(RegistryEvent.Register<Potion> event){
    }
}
