package illager.savageandravage.item;

import illager.savageandravage.entity.projectile.SporeCloudEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
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
                worldIn.playSound((PlayerEntity) null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_EGG_THROW, SoundCategory.PLAYERS, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));


                if (!worldIn.isRemote) {

                    SporeCloudEntity areaeffectcloudentity = new SporeCloudEntity(worldIn, blockpos.getX(), blockpos.getY() + 1.0F, blockpos.getZ());
                    areaeffectcloudentity.setOwner(playerIn);
                    areaeffectcloudentity.setParticleData(ParticleTypes.SNEEZE);
                    areaeffectcloudentity.setRadius(2.0F);
                    areaeffectcloudentity.setRadiusOnUse(-0.05F);
                    areaeffectcloudentity.setDuration(200);
                    areaeffectcloudentity.setRadiusPerTick(-areaeffectcloudentity.getRadius() / (float) areaeffectcloudentity.getDuration());
                    areaeffectcloudentity.addEffect(new EffectInstance(Effects.NAUSEA, 100, 0));


                    worldIn.addEntity(areaeffectcloudentity);

                }
            }
            playerIn.addStat(Stats.ITEM_USED.get(this));
            return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
        }
    }
}