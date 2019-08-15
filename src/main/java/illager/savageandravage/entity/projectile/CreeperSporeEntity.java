package illager.savageandravage.entity.projectile;

import illager.savageandravage.init.SavageEntityRegistry;
import illager.savageandravage.init.SavageItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRendersAsItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.List;

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

            for (int i = 0; i < 12; ++i) {
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
        }

        if (!this.world.isRemote) {

            List<LivingEntity> list = this.world.getEntitiesWithinAABB(LivingEntity.class, this.getBoundingBox().grow(1.0D, 2.0D, 1.0D));
            SporeCloudEntity areaeffectcloudentity = new SporeCloudEntity(this.world, this.posX, this.posY, this.posZ);
            areaeffectcloudentity.setOwner(this.getThrower());
            areaeffectcloudentity.setParticleData(ParticleTypes.SNEEZE);
            areaeffectcloudentity.setRadius(2.0F);
            areaeffectcloudentity.setRadiusOnUse(-0.05F);
            areaeffectcloudentity.setDuration(200);
            areaeffectcloudentity.setRadiusPerTick(-areaeffectcloudentity.getRadius() / (float) areaeffectcloudentity.getDuration());
            areaeffectcloudentity.addEffect(new EffectInstance(Effects.NAUSEA, 100, 0));

            if (!list.isEmpty()) {
                for (LivingEntity livingentity : list) {
                    double d0 = this.getDistanceSq(livingentity);
                    if (d0 < 16.0D) {
                        areaeffectcloudentity.setPosition(livingentity.posX, livingentity.posY, livingentity.posZ);
                        break;
                    }
                }
            }

            this.world.addEntity(areaeffectcloudentity);

            this.world.setEntityState(this, (byte) 3);
            this.remove();
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