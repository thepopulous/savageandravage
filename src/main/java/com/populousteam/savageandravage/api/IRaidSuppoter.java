package com.populousteam.savageandravage.api;

import com.populousteam.savageandravage.world.RevampRaid;

public interface IRaidSuppoter {
    boolean isEnemy();

    void initRaidSpawn(int wave, RevampRaid raid);
}
