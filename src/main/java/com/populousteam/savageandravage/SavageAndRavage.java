package com.populousteam.savageandravage;

import com.populousteam.client.ClientRegistrar;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(SavageAndRavage.MOD_ID)
public class SavageAndRavage {

    public static final String MOD_ID = "savageandravage";
    public SavageAndRavage instance;

    public SavageAndRavage(){

        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientRegistrar::setup));
    }

}
