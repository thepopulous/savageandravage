package illager.illagersthanvillagers.init;

import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.types.Type;
import illager.illagersthanvillagers.entity.EntityGuardIllager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.datafix.DataFixesManager;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraft.util.registry.RegistryNamespaced;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.GameData;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.function.Function;

import static illager.illagersthanvillagers.IllagersThanVillagers.MODID;

public class IllagerEntityRegistry {

    public static final EntityType<EntityGuardIllager> GUARD_ILLAGER = register("guard_ilager", EntityType.Builder.create(EntityGuardIllager.class, EntityGuardIllager::new));

    public static <T extends Entity> EntityType<T> register(String id, EntityType.Builder<T> builder) {
        EntityType<T> entitytype = builder.build(id);


        ForgeRegistries.ENTITIES.getSlaveMap(new ResourceLocation(id), builder.getClass());
        return entitytype;
    }

    public static void register(EntityType entity, String name, IForgeRegistry<EntityType<?>> event) {
        entity.setRegistryName(new ResourceLocation(MODID,name));
        ForgeRegistries.ENTITIES.register(entity);
    }

    public static void registerEntity(IForgeRegistry<EntityType<?>> event) {
        register(GUARD_ILLAGER, "guard_illager", event);
    }
}