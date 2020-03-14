package com.populousteam.savageandravage.world;

import net.minecraft.world.server.ServerWorld;

public class RaidOverrideHandler {
    public static void overrideRaidFactory(ServerWorld world) {
        if(!(world.raids instanceof RevampRaidManager)) {
            world.raids = world.getSavedData().getOrCreate(() -> {
                return new RevampRaidManager(world);
            }, RevampRaidManager.func_215172_a(world.dimension));

        }
    }
}
