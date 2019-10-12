package illager.savageandravage.api;

import illager.savageandravage.world.RevampRaid;

public interface IRaidSuppoter {
    boolean isEnemy();

    void initRaidSpawn(int wave, RevampRaid raid);
}
