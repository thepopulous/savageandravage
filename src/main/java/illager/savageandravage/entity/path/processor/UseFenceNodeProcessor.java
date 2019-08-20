package illager.savageandravage.entity.path.processor;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.IFluidState;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.PathType;
import net.minecraft.pathfinding.WalkNodeProcessor;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class UseFenceNodeProcessor extends WalkNodeProcessor {

    protected PathNodeType func_215744_a(IBlockReader p_215744_1_, boolean p_215744_2_, boolean p_215744_3_, BlockPos p_215744_4_, PathNodeType p_215744_5_) {
        p_215744_5_ = super.func_215744_a(p_215744_1_, p_215744_2_, p_215744_3_, p_215744_4_, p_215744_5_);

        if (p_215744_5_ == PathNodeType.FENCE && p_215744_2_ && p_215744_3_ && (p_215744_1_.getBlockState(p_215744_4_).getBlock() instanceof FenceGateBlock)) {
            p_215744_5_ = PathNodeType.OPEN;
        }

        return p_215744_5_;
    }

    protected PathNodeType getPathNodeTypeRaw(IBlockReader blockaccessIn, int x, int y, int z) {
        BlockPos blockpos = new BlockPos(x, y, z);
        BlockState blockstate = blockaccessIn.getBlockState(blockpos);
        PathNodeType type = blockstate.getAiPathNodeType(blockaccessIn, blockpos, this.currentEntity);
        if (type != null) return type;
        Block block = blockstate.getBlock();
        Material material = blockstate.getMaterial();
        if (blockstate.isAir(blockaccessIn, blockpos)) {
            return PathNodeType.OPEN;
        } else if (!block.isIn(BlockTags.TRAPDOORS) && block != Blocks.LILY_PAD) {
            if (block == Blocks.FIRE) {
                return PathNodeType.DAMAGE_FIRE;
            } else if (block == Blocks.CACTUS) {
                return PathNodeType.DAMAGE_CACTUS;
            } else if (block == Blocks.SWEET_BERRY_BUSH) {
                return PathNodeType.DAMAGE_OTHER;
            } else if (block instanceof DoorBlock && material == Material.WOOD && !blockstate.get(DoorBlock.OPEN)) {
                return PathNodeType.DOOR_WOOD_CLOSED;
            } else if (block instanceof DoorBlock && material == Material.IRON && !blockstate.get(DoorBlock.OPEN)) {
                return PathNodeType.DOOR_IRON_CLOSED;
            } else if (block instanceof DoorBlock && blockstate.get(DoorBlock.OPEN)) {
                return PathNodeType.DOOR_OPEN;
            } else if (block instanceof AbstractRailBlock) {
                return PathNodeType.RAIL;
            } else if (block instanceof LeavesBlock) {
                return PathNodeType.LEAVES;
            } else if (!block.isIn(BlockTags.FENCES) && !block.isIn(BlockTags.WALLS) && (!(block instanceof FenceGateBlock) || blockstate.get(FenceGateBlock.OPEN))) {
                IFluidState ifluidstate = blockaccessIn.getFluidState(blockpos);
                if (ifluidstate.isTagged(FluidTags.WATER)) {
                    return PathNodeType.WATER;
                } else if (ifluidstate.isTagged(FluidTags.LAVA)) {
                    return PathNodeType.LAVA;
                } else {
                    return blockstate.allowsMovement(blockaccessIn, blockpos, PathType.LAND) ? PathNodeType.OPEN : PathNodeType.BLOCKED;
                }
            } else {
                return PathNodeType.FENCE;
            }
        } else {
            return PathNodeType.TRAPDOOR;
        }
    }
}
