package com.populousteam.savageandravage.entity.projectile;

import com.populousteam.savageandravage.SavageConfig;
import com.populousteam.savageandravage.entity.CreepieEntity;
import com.populousteam.savageandravage.init.SavageEntityRegistry;
import com.populousteam.savageandravage.init.SavageItems;
import net.minecraft.entity.*;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.IPacket;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
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
public class CreeperSporeEntity extends ProjectileItemEntity {
    public CreeperSporeEntity(EntityType<? extends CreeperSporeEntity> p_i50159_1_, World p_i50159_2_) {
        super(p_i50159_1_, p_i50159_2_);
    }

    public CreeperSporeEntity(World worldIn, LivingEntity throwerIn) {
        super(SavageEntityRegistry.CREEPER_SPORE, throwerIn, worldIn);
    }

    public CreeperSporeEntity(World worldIn, double x, double y, double z) {
        super(SavageEntityRegistry.CREEPER_SPORE, x, y, z, worldIn);
    }

    public CreeperSporeEntity(FMLPlayMessages.SpawnEntity spawnEntity, World world) {
        this(SavageEntityRegistry.CREEPER_SPORE, world);
    }

    @OnlyIn(Dist.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 3) {
            double d0 = 0.08D;

            for (int i = 0; i < 6; ++i) {
                this.world.addParticle(new ItemParticleData(ParticleTypes.ITEM, this.getItem()), this.posX, this.posY, this.posZ, ((double) this.rand.nextFloat() - 0.5D) * 0.08D, ((double) this.rand.nextFloat() - 0.5D) * 0.08D, ((double) this.rand.nextFloat() - 0.5D) * 0.08D);
            }
        }
    }

    @Override
    protected Item func_213885_i() {
        return SavageItems.CREEPER_SPORES;
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

            entity.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), (float) 2);
            if(SavageConfig.CREEPIES.get()) {
                spawnCreepies();
            }
            else{
                spawnCreepers();
            }
        } else if (result.getType() == RayTraceResult.Type.BLOCK) {
            if(SavageConfig.CREEPIES.get()) {
                spawnCreepies();
            }
            else{
                spawnCreepers();
            }
        }
        this.world.setEntityState(this, (byte) 3);
        this.remove();
    }

    //TODO: Fix these rendering a pig
    private void spawnCreepies() {
        if (!this.world.isRemote) {

            for (int i = 0; i < 1 + this.world.rand.nextInt(2); i++) {
                CreepieEntity creepieEntity = SavageEntityRegistry.CREEPIES.create(this.world);
                creepieEntity.setLocationAndAngles(this.getPosition().getX() + 0.5F, this.getPosition().getY(), this.getPosition().getZ() + 0.5F, 0.0F, 0.0F);
                creepieEntity.setOwner(this.getThrower());

                this.world.addEntity(creepieEntity);
            }

            AreaEffectCloudEntity areaeffectcloudentity = new AreaEffectCloudEntity(world, this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getZ());

            areaeffectcloudentity.setOwner(this.getThrower());
            areaeffectcloudentity.setParticleData(ParticleTypes.SNEEZE);
            areaeffectcloudentity.setRadius(0.75F);
            areaeffectcloudentity.setRadiusOnUse(-0.05F);
            areaeffectcloudentity.setDuration(100);
            areaeffectcloudentity.setRadiusPerTick(-areaeffectcloudentity.getRadius() / (float) areaeffectcloudentity.getDuration());

            world.addEntity(areaeffectcloudentity);
        }

        if (this.world.isRemote) {
            IParticleData iparticledata = ParticleTypes.SNEEZE;
            for (int i = 0; i < 6; ++i) {
                float f1 = this.world.rand.nextFloat() * ((float) Math.PI * 2F);
                float f2 = MathHelper.sqrt(this.world.rand.nextFloat()) * 0.2F;
                float f3 = MathHelper.cos(f1) * f2;
                float f4 = MathHelper.sin(f1) * f2;

                this.world.addOptionalParticle(iparticledata, this.getPosition().getX() + (double) f3, this.getPosition().getY() + 1.0F, this.getPosition().getZ() + (double) f4, 0.0D, 0.0D, 0.0D);

            }
        }
    }

    private void spawnCreepers() {
        if (!this.world.isRemote) {
            CreeperEntity creeperEntity = EntityType.CREEPER.create(this.world);
            creeperEntity.setLocationAndAngles(this.getPosition().getX() + 0.5F, this.getPosition().getY(), this.getPosition().getZ() + 0.5F, 0.0F, 0.0F);
            this.world.addEntity(creeperEntity);

            AreaEffectCloudEntity areaeffectcloudentity = new AreaEffectCloudEntity(world, this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getZ());

            areaeffectcloudentity.setOwner(this.getThrower());
            areaeffectcloudentity.setParticleData(ParticleTypes.SNEEZE);
            areaeffectcloudentity.setRadius(0.75F);
            areaeffectcloudentity.setRadiusOnUse(-0.05F);
            areaeffectcloudentity.setDuration(100);
            areaeffectcloudentity.setRadiusPerTick(-areaeffectcloudentity.getRadius() / (float) areaeffectcloudentity.getDuration());
            world.addEntity(areaeffectcloudentity);
        }

        if (this.world.isRemote) {
            IParticleData iparticledata = ParticleTypes.SNEEZE;
            for (int i = 0; i < 6; ++i) {
                float f1 = this.world.rand.nextFloat() * ((float) Math.PI * 2F);
                float f2 = MathHelper.sqrt(this.world.rand.nextFloat()) * 0.2F;
                float f3 = MathHelper.cos(f1) * f2;
                float f4 = MathHelper.sin(f1) * f2;

                this.world.addOptionalParticle(iparticledata, this.getPosition().getX() + (double) f3, this.getPosition().getY() + 1.0F, this.getPosition().getZ() + (double) f4, 0.0D, 0.0D, 0.0D);

            }
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (this.world.isRemote) {
            for (int i = 0; i < 2; i++) {
                this.world.addParticle(ParticleTypes.SNEEZE, this.getPosition().getX() + this.world.rand.nextFloat() - 0.5F, this.getPosition().getY() + this.world.rand.nextFloat() - 0.5F, this.getPosition().getZ() + this.world.rand.nextFloat() - 0.5F, 0.0D, 0.0D, 0.0D);
            }
        }
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