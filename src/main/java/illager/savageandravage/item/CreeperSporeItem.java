package illager.savageandravage.item;

import illager.savageandravage.entity.CreepiesEntity;
import illager.savageandravage.init.SavageEntityRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.World;

public class CreeperSporeItem extends Item {
    public CreeperSporeItem(Properties builder) {
        super(builder);
    }

    /**
     * Called to trigger the item's "innate" right click behavior. To handle when this item is used on a Block, see
     * {@link #onItemUse}.
     */
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        RayTraceResult raytraceresult = rayTrace(worldIn, playerIn, RayTraceContext.FluidMode.NONE);
        if (raytraceresult.getType() == RayTraceResult.Type.MISS) {
            return new ActionResult<>(ActionResultType.PASS, itemstack);
        } else if (raytraceresult.getType() != RayTraceResult.Type.BLOCK) {
            return new ActionResult<>(ActionResultType.PASS, itemstack);
        } else {
            if (!playerIn.abilities.isCreativeMode) {
                itemstack.shrink(1);
            }


            playerIn.setActiveHand(handIn);
            BlockRayTraceResult blockraytraceresult = (BlockRayTraceResult) raytraceresult;
            BlockPos blockpos = blockraytraceresult.getPos();
            if (worldIn.isBlockModifiable(playerIn, blockpos) && playerIn.canPlayerEdit(blockpos, blockraytraceresult.getFace(), itemstack)) {
                worldIn.playSound((PlayerEntity) null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_EGG_THROW, SoundCategory.PLAYERS, 0.5F, 0.4F / (worldIn.rand.nextFloat() * 0.4F + 0.8F));


                if (!worldIn.isRemote) {

                    CreepiesEntity creepiesEntity = SavageEntityRegistry.CREEPIES.create(worldIn);
                    creepiesEntity.setLocationAndAngles(blockpos.getX() + 0.5F, blockpos.getY() + 1.0F, blockpos.getZ() + 0.5F, 0.0F, 0.0F);
                    creepiesEntity.setOwner(playerIn);

                    worldIn.addEntity(creepiesEntity);


                }
                IParticleData iparticledata = ParticleTypes.SNEEZE;
                for (int i = 0; i < 6; ++i) {
                    float f1 = worldIn.rand.nextFloat() * ((float) Math.PI * 2F);
                    float f2 = MathHelper.sqrt(worldIn.rand.nextFloat()) * 0.2F;
                    float f3 = MathHelper.cos(f1) * f2;
                    float f4 = MathHelper.sin(f1) * f2;

                    worldIn.addParticle(iparticledata, blockpos.getX() + (double) f3 + 0.5F, blockpos.getY() + 1.0F, blockpos.getZ() + (double) f4 + 0.5F, 0.0D, 0.0D, 0.0D);

                }

            }
            playerIn.addStat(Stats.ITEM_USED.get(this));
            return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
        }
    }
}