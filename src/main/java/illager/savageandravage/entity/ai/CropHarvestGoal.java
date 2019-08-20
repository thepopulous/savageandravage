package illager.savageandravage.entity.ai;

import illager.savageandravage.entity.illager.PoultryFarmerIllagerEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropsBlock;
import net.minecraft.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class CropHarvestGoal extends MoveToBlockGoal {
    private final PoultryFarmerIllagerEntity illager;
    private boolean wantsToHarvest;
    private boolean canHarvest;
    private boolean canPlant;

    public CropHarvestGoal(PoultryFarmerIllagerEntity illagerIn) {
        super(illagerIn, (double) 0.7F, 16);
        this.illager = illagerIn;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute() {
        if (this.runDelay <= 0) {
            if (!net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.illager.world, this.illager)) {
                return false;
            }

            this.canHarvest = false;
            this.canPlant = false;
            this.wantsToHarvest = true;
        }

        return super.shouldExecute();
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting() {
        return (this.canHarvest || this.canPlant) && super.shouldContinueExecuting();
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void tick() {
        super.tick();
        this.illager.getLookController().setLookPosition((double) this.destinationBlock.getX() + 0.5D, (double) (this.destinationBlock.getY() + 1), (double) this.destinationBlock.getZ() + 0.5D, 10.0F, (float) this.illager.getVerticalFaceSpeed());
        if (this.getIsAboveDestination()) {
            World world = this.illager.world;
            BlockPos blockpos = this.destinationBlock.up();
            BlockState blockstate = world.getBlockState(blockpos);
            Block block = blockstate.getBlock();
            if (this.canHarvest && block instanceof CropsBlock) {
                Integer integer = blockstate.get(CropsBlock.AGE);
                if (integer == 7) {
                    world.destroyBlock(blockpos, true);
                }

            }

            BlockState blockstate2 = world.getBlockState(blockpos.down());

            ItemStack stack = this.findSeeds(this.illager);

            if (this.canPlant && blockstate2.getBlock() == Blocks.FARMLAND && !stack.isEmpty()) {
                world.setBlockState(blockpos, Blocks.WHEAT.getDefaultState(), 2);
                stack.shrink(1);
            }

            this.canPlant = false;
            this.canHarvest = false;
            this.runDelay = 10;
        }

    }

    public double getTargetDistanceSq() {
        return 2.0D;
    }


    /**
     * Return true to set given position as destination
     */
    protected boolean shouldMoveTo(IWorldReader worldIn, BlockPos pos) {
        Block block = worldIn.getBlockState(pos).getBlock();
        if (block == Blocks.FARMLAND && this.wantsToHarvest && (!this.canHarvest || !this.canPlant)) {
            pos = pos.up();
            BlockState blockstate = worldIn.getBlockState(pos);
            block = blockstate.getBlock();

            ItemStack stack = this.findSeeds(this.illager);
            if (block instanceof CropsBlock && ((CropsBlock) block).isMaxAge(blockstate)) {
                this.canHarvest = true;
                return true;
            } else if (blockstate.isAir(worldIn, pos) && !stack.isEmpty()) {
                this.canPlant = true;
                return true;
            }
        }

        return false;
    }

    private ItemStack findSeeds(PoultryFarmerIllagerEntity illager) {
        Inventory inventory = illager.getInventory();
        int i = inventory.getSizeInventory();

        for (int j = 0; j < i; ++j) {
            ItemStack itemstack = inventory.getStackInSlot(j);
            if (itemstack.isEmpty()) {
                return ItemStack.EMPTY;
            }

            if (itemstack.getItem() == Items.WHEAT_SEEDS) {
                return itemstack;
            }
        }
        return ItemStack.EMPTY;
    }
}