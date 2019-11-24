package populousteam.savageandravage;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import populousteam.savageandravage.client.IllagerEntityRender;

@Mod(SavageAndRavage.MOD_ID)
@Mod.EventBusSubscriber(modid=SavageAndRavage.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SavageAndRavage {

    public static final String MOD_ID = "savageandravage";
    public SavageAndRavage instance;

    public SavageAndRavage(){
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        IllagerEntityRender.entityRender();
    }
}
