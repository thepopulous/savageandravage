package illager.guardillagers.init;

import illager.guardillagers.entity.EntityGuardIllager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

import static illager.guardillagers.GuardIllagers.MODID;

public class IllagerEntityRegistry {

    public static final EntityType<EntityGuardIllager> GUARD_ILLAGER = register("guard_ilager", EntityType.Builder.create(EntityGuardIllager.class, EntityGuardIllager::new));


    public static <T extends Entity> EntityType<T> register(String id, EntityType.Builder<T> builder) {

        EntityType<T> entitytype = builder.build(id);
        ResourceLocation name = new ResourceLocation(MODID, id);

        ForgeRegistries.ENTITIES.getSlaveMap(name, EntityType.class);

        return entitytype;
    }

    public static void register(EntityType entity, String name, IForgeRegistry<EntityType<?>> event) {
        entity.setRegistryName(new ResourceLocation(MODID,name));
        event.register(entity);

    }

    public static void registerEntity(IForgeRegistry<EntityType<?>> event) {
        register(GUARD_ILLAGER, "guard_illager", event);

    }
}