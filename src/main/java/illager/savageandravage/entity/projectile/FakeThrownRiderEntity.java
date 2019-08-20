package illager.savageandravage.entity.projectile;

import illager.savageandravage.init.SavageEntityRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRendersAsItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.IPacket;
import net.minecraft.util.DamageSource;
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
public class FakeThrownRiderEntity extends ProjectileItemEntity {

    public FakeThrownRiderEntity(EntityType<? extends FakeThrownRiderEntity> p_i50159_1_, World p_i50159_2_) {
        super(p_i50159_1_, p_i50159_2_);
    }

    public FakeThrownRiderEntity(World worldIn, LivingEntity throwerIn) {
        super(SavageEntityRegistry.FAKE_THROWN_RIDER, throwerIn, worldIn);
    }

    public FakeThrownRiderEntity(World worldIn, double x, double y, double z) {
        super(SavageEntityRegistry.FAKE_THROWN_RIDER, x, y, z, worldIn);
    }

    public FakeThrownRiderEntity(FMLPlayMessages.SpawnEntity spawnEntity, World world) {
        this(SavageEntityRegistry.FAKE_THROWN_RIDER, world);
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        if (result.getType() == RayTraceResult.Type.ENTITY) {
            Entity entity = ((EntityRayTraceResult) result).getEntity();

            if (this.getThrower() == entity && this.ticksExisted < 4) {
                return;
            }

            entity.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), (float) 4);
        }
    }

    @Override
    protected Item func_213885_i() {
        return null;
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
