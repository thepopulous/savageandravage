package com.populousteam.savageandravage.entity.projectile;

import com.populousteam.savageandravage.init.SavageEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRendersAsItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.IPacket;
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
public class BestialBrewEntity extends ProjectileItemEntity {
    public BestialBrewEntity(EntityType<? extends BestialBrewEntity> p_i50159_1_, World p_i50159_2_) {
        super(p_i50159_1_, p_i50159_2_);
    }

    public BestialBrewEntity(World worldIn, LivingEntity throwerIn) {
        super(SavageEntities.BESTIAL_BREW, throwerIn, worldIn);
    }

    public BestialBrewEntity(World worldIn, double x, double y, double z) {
        super(SavageEntities.BESTIAL_BREW, x, y, z, worldIn);
    }

    public BestialBrewEntity(FMLPlayMessages.SpawnEntity spawnEntity, World world) {
        this(SavageEntities.BESTIAL_BREW, world);
    }

   /* @OnlyIn(Dist.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 3) {
            double d0 = 0.08D;

            for (int i = 0; i < 3; ++i) {
                SavageParticles.BREWGLASS.spawn(world, this.posX, this.posY, this.posZ, ((double) this.rand.nextFloat() - 0.5D) * 0.08D, ((double) this.rand.nextFloat() - 0.5D) * 0.08D, ((double) this.rand.nextFloat() - 0.5D) * 0.08D);
            }

            for (int i = 0; i < 5; ++i) {
                SavageParticles.BREWSPLASH.spawn(world, this.posX + rand.nextInt(3) - rand.nextInt(3), this.posY, this.posZ + rand.nextInt(3) - rand.nextInt(3), ((double) this.rand.nextFloat() - 0.5D) * 0.08D, ((double) this.rand.nextFloat() - 0.5D) * 0.08D, ((double) this.rand.nextFloat() - 0.5D) * 0.08D);
            }
        }
    }

    @Override
    protected Item func_213885_i() {
        return SavageItems.BESTIAL_BREW;
    }

    */

    /**
     * Called when this EntityThrowable hits a block or entity.
     *//*
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
                        SavagelingEntity savagelingEntity = SavageEntities.SAVAGELING.create(this.world);
                        savagelingEntity.setLocationAndAngles(entity.getPosition().getX() + 0.5F, entity.getPosition().getY(), entity.getPosition().getZ() + 0.5F, 0.0F, 0.0F);

                        this.world.addEntity(savagelingEntity);

                        entity.remove();
                    }
                } else {
                    if (!this.world.isRemote) {
                        VillagerData villagerdata = ((IVillagerDataHolder) entity).getVillagerData();
                        IVillagerType ivillagertype = villagerdata.getType();

                        spawnRavager(entity.getPosition(), villagerdata.getType());

                        entity.remove();
                    }
                }
                this.playSound(SoundEvents.ENTITY_ZOMBIE_VILLAGER_CONVERTED, 2.0F, 1.0F);
            } else if (entity instanceof AbstractIllagerEntity) {
                RavagerEntity ravagerEntity = EntityType.RAVAGER.create(this.world);
                ravagerEntity.setLocationAndAngles(entity.getPosition().getX() + 0.5F, entity.getPosition().getY(), entity.getPosition().getZ() + 0.5F, 0.0F, 0.0F);


                this.world.addEntity(ravagerEntity);

                entity.remove();
            }
        }
        else if(result.getType()== RayTraceResult.Type.BLOCK){
            BlockRayTraceResult blockResult = (BlockRayTraceResult) result;
            //Block block = blockResult.getClass().getTypeName();
            //TODO: how to do this?
        }

        this.playSound(SoundEvents.BLOCK_GLASS_BREAK, 0.8F, 1.0F);
        this.world.setEntityState(this, (byte) 3);
        this.remove();
    }

    private void spawnRavager(BlockPos pos, IVillagerType type) {
        FriendlyRavagerEntity ravagerEntity = SavageEntities.FRIENDLY_RAVAGER.create(this.world);
        ravagerEntity.setLocationAndAngles(pos.getX(), pos.getY(), pos.getZ(), 0.0F, 0.0F);
        if (type == IVillagerType.SNOW) {
            ravagerEntity.setRavagerType(FriendlyRavagerEntity.RavagerType.SNOW);
        } else if (type == IVillagerType.DESERT) {
            ravagerEntity.setRavagerType(FriendlyRavagerEntity.RavagerType.DESERT);
        }

        this.world.addEntity(ravagerEntity);
    }

    @Override
    public void tick() {
        super.tick();

    }*/
    @OnlyIn(Dist.CLIENT)
    @Override
    public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
        this.setPosition(x, y, z);
        this.setRotation(yaw, pitch);
    }

    protected float getGravityVelocity() {
        return 0.05F;
    }

    @Override
    protected void onImpact(RayTraceResult result) {

    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected Item func_213885_i() {
        return null;
    }
}