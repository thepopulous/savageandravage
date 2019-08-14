package illager.savageandravage.entity.projectile;

import illager.savageandravage.entity.CreepiesEntity;
import illager.savageandravage.init.SavageEntityRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SporeCloudEntity extends AreaEffectCloudEntity {
    public SporeCloudEntity(EntityType<? extends AreaEffectCloudEntity> p_i50389_1_, World p_i50389_2_) {
        super(p_i50389_1_, p_i50389_2_);
        this.noClip = true;
        this.setRadius(3.0F);
    }

    public SporeCloudEntity(World worldIn, double x, double y, double z) {
        this(EntityType.AREA_EFFECT_CLOUD, worldIn);
        this.setPosition(x, y, z);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.world.rand.nextInt(150) == 0) {
            BlockPos blockPos = this.getPosition();

            for (int i = 0; i < 1 + this.rand.nextInt(2); i++) {
                BlockPos pos = new BlockPos(blockPos.getX() + this.rand.nextInt(6) - 3, blockPos.getY() + this.rand.nextInt(2) - 1, blockPos.getZ() + this.rand.nextInt(6) - 3);

                BlockState blockstate = this.world.getBlockState(pos);

                if (blockstate.isValidPosition(this.world, pos) && blockstate.isAir(this.world, pos)) {
                    if (!this.world.isRemote) {
                        this.world.playEvent(2005, pos, 0);
                    }

                    if (!world.isRemote) {
                        CreepiesEntity creepiesEntity = SavageEntityRegistry.CREEPIES.create(this.world);
                        creepiesEntity.setLocationAndAngles(pos.getX() + 0.5F, pos.getY(), pos.getZ() + 0.5F, this.rotationYaw, this.rotationPitch);
                        creepiesEntity.setOwner(getOwner());

                        this.world.addEntity(creepiesEntity);
                    }
                }


            }
        }
    }
}
