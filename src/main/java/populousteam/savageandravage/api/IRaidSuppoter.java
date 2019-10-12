package populousteam.savageandravage.api;

import populousteam.savageandravage.world.RevampRaid;

public interface IRaidSuppoter {
    boolean isEnemy();

    void initRaidSpawn(int wave, RevampRaid raid);
}
