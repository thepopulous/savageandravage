package populousteam.savageandravage;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

import java.nio.file.Path;

@Mod.EventBusSubscriber
public class Config {
    public static final String CATEGORY_IMPROVEMENTS = "improvements";
    public static final String CATEGORY_ADDITIONS = "additions";
    public static final String CATEGORY_EXPERIMENTAL = "experimental";
    public static final String SUBCATEGORY_IMPROVEMENTS_PILLAGER = "pillager_improvements";
    public static final String SUBCATEGORY_IMPROVEMENTS_PATROL = "patrol_improvements";
    public static final String SUBCATEGORY_ADDITIONS_DEFENDER = "defender";
    public static final String SUBCATEGORY_ADDITIONS_POULTRY_FARMER = "poultry_farmer";
    public static final String SUBCATEGORY_ADDITIONS_GRIEFER = "griefer";
    public static final String SUBCATEGORY_ADDITIONS_SCAVENGER = "scavenger";
    public static final String SUBCATEGORY_ADDITIONS_FRIENDLY_RAVAGER = "friendly_ravager";
    public static final String SUBCATEGORY_ADDITIONS_SAVAGELING = "savageling";
    public static final String SUBCATEGORY_ADDITIONS_SKELETON_VILLAGER = "skeleton_villager";
    public static final String SUBCATEGORY_ADDITIONS_HYENA = "hyena";
    public static final String SUBCATEGORY_EXPERIMENTAL_CREEPER_REWORK = "creeper_rework";
    public static final String SUBCATEGORY_EXPERIMENTAL_ILLAGERS_UNDEAD = "illagers_fight_undead_mobs";

    private static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
    private static final ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();

    public static ForgeConfigSpec COMMON_CONFIG;
    public static ForgeConfigSpec CLIENT_CONFIG;

    public static ForgeConfigSpec.BooleanValue VINDICATOR_REWORK;
    public static ForgeConfigSpec.BooleanValue PILLAGERS_USE_FIREWORKS;
    public static ForgeConfigSpec.BooleanValue PILLAGERS_DROP_ARROWS;
    public static ForgeConfigSpec.BooleanValue GUARDS_IN_PATROLS;
    public static ForgeConfigSpec.BooleanValue SCAVENGERS_ALWAYS_LEADER;
    public static ForgeConfigSpec.BooleanValue DEFENDERS;
    public static ForgeConfigSpec.BooleanValue GUARD_HAT;
    public static ForgeConfigSpec.BooleanValue POULTRY_FARMERS;
    public static ForgeConfigSpec.BooleanValue POULTRY_FARMER_HAT;
    public static ForgeConfigSpec.BooleanValue POULTRY_FARMER_HOUSE;
    public static ForgeConfigSpec.BooleanValue GRIEFERS;
    public static ForgeConfigSpec.BooleanValue CREEPER_SPORES;
    public static ForgeConfigSpec.BooleanValue GRIEFERS_DROP_SPORES;
    public static ForgeConfigSpec.BooleanValue CREEPIES;
    public static ForgeConfigSpec.BooleanValue SCAVENGERS;
    public static ForgeConfigSpec.BooleanValue SINISTER_HORN;
    public static ForgeConfigSpec.BooleanValue TENACITY;
    public static ForgeConfigSpec.IntValue EFFECT_DURATION;
    public static ForgeConfigSpec.BooleanValue FRIENDLY_RAVAGERS;
    public static ForgeConfigSpec.BooleanValue BREW_OF_THE_BEAST;
    public static ForgeConfigSpec.BooleanValue SAVAGELINGS;
    public static ForgeConfigSpec.BooleanValue SAVAGELINGS_ATTACK_CHICKEN;
    public static ForgeConfigSpec.BooleanValue SKELETON_VILLAGERS;
    public static ForgeConfigSpec.BooleanValue SKELETON_VILLAGERS_NATURAL;
    public static ForgeConfigSpec.BooleanValue SKELETON_VILLAGERS_ZOMBIE_VILLAGE;
    public static ForgeConfigSpec.BooleanValue HYENAS;
    public static ForgeConfigSpec.BooleanValue HYENAS_ATTACK_COWS;
    public static ForgeConfigSpec.BooleanValue CREEPER_REWORK;
    public static ForgeConfigSpec.BooleanValue CREEPERS_ALWAYS_DROP_SPORES;
    public static ForgeConfigSpec.BooleanValue UNDEAD_ATTACK_ILLAGERS;
    public static ForgeConfigSpec.BooleanValue ILLAGERS_ATTACK_UNDEAD;

    static{
        COMMON_BUILDER.push(CATEGORY_IMPROVEMENTS);
            VINDICATOR_REWORK = COMMON_BUILDER.define("Enable Vindicator Rework",true);
            COMMON_BUILDER.push(SUBCATEGORY_IMPROVEMENTS_PILLAGER);
                PILLAGERS_USE_FIREWORKS = COMMON_BUILDER.define("Pillagers can spawn with fireworks", true);
                PILLAGERS_DROP_ARROWS = COMMON_BUILDER.define("Pillagers can drop arrows on death",true);
            COMMON_BUILDER.pop();
            COMMON_BUILDER.push(SUBCATEGORY_IMPROVEMENTS_PATROL);
                GUARDS_IN_PATROLS = COMMON_BUILDER.define("Guards spawn with pillagers in patrols", true);
                SCAVENGERS_ALWAYS_LEADER = COMMON_BUILDER.define("Scavengers are always the leader of the patrol",true);
            COMMON_BUILDER.pop();
        COMMON_BUILDER.pop();

        COMMON_BUILDER.push(CATEGORY_ADDITIONS);
            COMMON_BUILDER.push(SUBCATEGORY_ADDITIONS_DEFENDER);
                DEFENDERS = COMMON_BUILDER.define("Enable Defenders", true);
                GUARD_HAT = COMMON_BUILDER.define("Guard Hat", true);
            COMMON_BUILDER.pop();
            COMMON_BUILDER.push(SUBCATEGORY_ADDITIONS_POULTRY_FARMER);
                POULTRY_FARMERS = COMMON_BUILDER.define("Enable Poultry Farmers", true);
                POULTRY_FARMER_HAT = COMMON_BUILDER.define("Poultry Farmer's Hat", true);
                POULTRY_FARMER_HOUSE = COMMON_BUILDER.define("Poultry Farmer's House", true);
            COMMON_BUILDER.pop();
            COMMON_BUILDER.push(SUBCATEGORY_ADDITIONS_GRIEFER);
                GRIEFERS = COMMON_BUILDER.define("Enable Griefers",true);
                CREEPER_SPORES = COMMON_BUILDER.define("Creeper Spores",true);
                GRIEFERS_DROP_SPORES = COMMON_BUILDER.define("Griefers drop spores",true);
                CREEPIES = COMMON_BUILDER
                        .comment("Disabling Creepies will cause Creeper Spores to spawn Creepers instead.")
                        .define("Creepies", true);
            COMMON_BUILDER.pop();
            COMMON_BUILDER.push(SUBCATEGORY_ADDITIONS_SCAVENGER);
                SCAVENGERS = COMMON_BUILDER.define("Enable Scavengers", true);
                SINISTER_HORN = COMMON_BUILDER.define("Sinister Horn", true);
                TENACITY = COMMON_BUILDER
                        .comment("Disabling Tenacity will cause the Sinister Horn to give Strength and Speed instead.")
                        .define("Tenacity Effect", true);
                EFFECT_DURATION = COMMON_BUILDER.defineInRange("Effect duration",120,1,Integer.MAX_VALUE);
            COMMON_BUILDER.pop();
            COMMON_BUILDER.push(SUBCATEGORY_ADDITIONS_FRIENDLY_RAVAGER);
                FRIENDLY_RAVAGERS = COMMON_BUILDER.define("Enable Friendly Ravagers", true);
                BREW_OF_THE_BEAST = COMMON_BUILDER
                        .comment("Disabling Brew Of The Beast will mean friendly ravagers can only be spawned with a command.")
                        .define("Brew Of The Beast", true);
            COMMON_BUILDER.pop();
            COMMON_BUILDER.push(SUBCATEGORY_ADDITIONS_SAVAGELING);
                SAVAGELINGS = COMMON_BUILDER.define("Enable Savagelings", true);
                SAVAGELINGS_ATTACK_CHICKEN = COMMON_BUILDER.define("Savagelings attack chicken", true);
            COMMON_BUILDER.pop();
            COMMON_BUILDER.push(SUBCATEGORY_ADDITIONS_SKELETON_VILLAGER);
                SKELETON_VILLAGERS = COMMON_BUILDER.define("Enable Skeleton Villagers", true);
                SKELETON_VILLAGERS_NATURAL = COMMON_BUILDER.define("Skeleton Villagers spawn naturally", true);
                SKELETON_VILLAGERS_ZOMBIE_VILLAGE = COMMON_BUILDER.define("Skeleton Villagers spawn in zombie villages", true);
            COMMON_BUILDER.pop();
            COMMON_BUILDER.push(SUBCATEGORY_ADDITIONS_HYENA);
                HYENAS = COMMON_BUILDER.define("Enable Hyenas", true);
                HYENAS_ATTACK_COWS = COMMON_BUILDER.define("Hyenas attack cows", true);
            COMMON_BUILDER.pop();
        COMMON_BUILDER.pop();

        COMMON_BUILDER.push(CATEGORY_EXPERIMENTAL)
                .comment("Experimental features are turned off by default");
        COMMON_BUILDER
                    .push(SUBCATEGORY_EXPERIMENTAL_CREEPER_REWORK);
                CREEPER_REWORK = COMMON_BUILDER.define("Enable Creeper Rework", false);
                CREEPERS_ALWAYS_DROP_SPORES = COMMON_BUILDER.define("Creepers always drop Creeper Spores", false);
            COMMON_BUILDER.pop();
            COMMON_BUILDER.push(SUBCATEGORY_EXPERIMENTAL_ILLAGERS_UNDEAD);
                UNDEAD_ATTACK_ILLAGERS = COMMON_BUILDER.define("Undead mobs attack Illagers", false);
                ILLAGERS_ATTACK_UNDEAD = COMMON_BUILDER.define("Illagers attack Undead mobs", false);
            COMMON_BUILDER.pop();
        COMMON_BUILDER.pop();

        COMMON_CONFIG = COMMON_BUILDER.build();
        CLIENT_CONFIG = CLIENT_BUILDER.build();
    }

    //Obligatory config loading file, copied from McJty
    public static void loadConfig(ForgeConfigSpec spec, Path path){
        final CommentedFileConfig configData = CommentedFileConfig.builder(path)
                .sync()
                .autosave()
                .writingMode(WritingMode.REPLACE)
                .build();

        configData.load();
        spec.setConfig(configData);
    }
}
