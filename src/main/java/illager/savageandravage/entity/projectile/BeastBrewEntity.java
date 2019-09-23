package illager.savageandravage.entity.projectile;

import illager.savageandravage.client.particles.SavageParticles;
import illager.savageandravage.entity.FriendlyRavagerEntity;
import illager.savageandravage.entity.SavagelingEntity;
import illager.savageandravage.init.SavageEntityRegistry;
import illager.savageandravage.init.SavageItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRendersAsItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.merchant.villager.VillagerData;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.entity.villager.IVillagerDataHolder;
import net.minecraft.entity.villager.IVillagerType;
import net.minecraft.item.Item;
import net.minecraft.network.IPacket;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

@OnlyIn(
        value = Dist.CLIENT,
        _interface = IRendersAsItem.class
)
public class BeastBrewEntity extends ProjectileItemEntity {
    public BeastBrewEntity(EntityType<? extends BeastBrewEntity> p_i50159_1_, World p_i50159_2_) {
        super(p_i50159_1_, p_i50159_2_);
    }

    public BeastBrewEntity(World worldIn, LivingEntity throwerIn) {
        super(SavageEntityRegistry.BEAST_BREW, throwerIn, worldIn);
    }

    public BeastBrewEntity(World worldIn, double x, double y, double z) {
        super(SavageEntityRegistry.BEAST_BREW, x, y, z, worldIn);
    }

    public BeastBrewEntity(FMLPlayMessages.SpawnEntity spawnEntity, World world) {
        this(SavageEntityRegistry.BEAST_BREW, world);
    }

    @OnlyIn(Dist.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 3) {
            double d0 = 0.08D;

            for (int i = 0; i < 3; ++i) {
                SavageParticles.BREWGLASS.spawn(world, this.posX, this.posY, this.posZ, ((double) this.rand.nextFloat() - 0.5D) * 0.08D, ((double) this.rand.nextFloat() - 0.5D) * 0.08D, ((double) this.rand.nextFloat() - 0.5D) * 0.08D);
            }

            for (int i = 0; i < 10; ++i) {
                SavageParticles.BREWSPLASH.spawn(world, this.posX + rand.nextInt(3) - rand.nextInt(3), this.posY, this.posZ + rand.nextInt(3) - rand.nextInt(3), ((double) this.rand.nextFloat() - 0.5D) * 0.08D, ((double) this.rand.nextFloat() - 0.5D) * 0.08D, ((double) this.rand.nextFloat() - 0.5D) * 0.08D);
            }
        }
    }

    @Override
    protected Item func_213885_i() {
        return SavageItems.BEASTBREW;
    }

    /**
     * Called when this EntityThrowable hits a block or entity.
     */
    @Override
    protected void onImpact(RayTraceResult result) {

        if (result.getType() == RayTraceResult.Type.ENTITY) {
            Entity entity = ((EntityRayTraceResult) result).getEntity();

            if (this.getThrower() == entity && this.ticksExisted < 4) {
                return;
            }

            if (entity instanceof VillagerEntity) {
                if (((VillagerEntity) entity).isChild()) {
                    if (!this.world.isRemote) {
                        SavagelingEntity savagelingEntity = SavageEntityRegistry.SAVAGELING.create(this.world);
                        savagelingEntity.setLocationAndAngles(entity.getPosition().getX() + 0.5F, entity.getPosition().getY(), entity.getPosition().getZ() + 0.5F, 0.0F, 0.0F);

                        this.world.addEntity(savagelingEntity);

                        entity.remove();
                    }
                } else {
                    if (!this.world.isRemote) {
                        VillagerData villagerdata = ((IVillagerDataHolder) entity).getVillagerData();
                        IVillagerType ivillagertype = villagerdata.getType();

                        spawnRavager(entity.getPosition(), villagerdata.getType() == IVillagerType.SNOW);

                        entity.remove();
                    }
                }
                this.playSound(SoundEvents.ENTITY_ZOMBIE_VILLAGER_CONVERTED, 2.0F, 1.0F);
            }
        }

        this.playSound(SoundEvents.BLOCK_GLASS_BREAK, 0.8F, 1.0F);
        this.world.setEntityState(this, (byte) 3);
        this.remove();
    }

    private void spawnRavager(BlockPos pos, boolean snowType) {
        FriendlyRavagerEntity ravagerEntity = SavageEntityRegistry.FRIENDLYRAVAGER.create(this.world);
        ravagerEntity.setLocationAndAngles(pos.getX(), pos.getY(), pos.getZ(), 0.0F, 0.0F);
        ravagerEntity.setSnowType(snowType);

        this.world.addEntity(ravagerEntity);
    }

    @Override
    public void tick() {
        super.tick();

    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
        this.setPosition(x, y, z);
        this.setRotation(yaw, pitch);
    }


    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}