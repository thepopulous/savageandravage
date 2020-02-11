package com.populousteam.savageandravage.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;
import java.util.stream.Stream;

import static net.minecraft.block.HorizontalBlock.HORIZONTAL_FACING;

public class HatefulIdolBlock extends Block {

    public HatefulIdolBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(HORIZONTAL_FACING, Direction.NORTH));
    }

    public static final VoxelShape SOUTH_SHAPE = Stream.of(
            Block.makeCuboidShape(4, 4, 4, 12, 14, 12),
            Block.makeCuboidShape(5, 2, 5, 11, 4, 11),
            Block.makeCuboidShape(3, 0, 3, 13, 2, 13),
            Block.makeCuboidShape(7, 3, 2, 9, 7, 4)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();
    public static final VoxelShape NORTH_SHAPE = Stream.of(
            Block.makeCuboidShape(4, 4, 4, 12, 14, 12),
            Block.makeCuboidShape(5, 2, 5, 11, 4, 11),
            Block.makeCuboidShape(3, 0, 3, 13, 2, 13),
            Block.makeCuboidShape(7, 3, 12, 9, 7, 14)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();
    public static final VoxelShape EAST_SHAPE = Stream.of(
            Block.makeCuboidShape(4, 4, 4, 12, 14, 12),
            Block.makeCuboidShape(3, 0, 3, 13, 2, 13),
            Block.makeCuboidShape(2, 3, 7, 4, 7, 9),
            Block.makeCuboidShape(5, 2, 5, 11, 4, 11)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();
    public static final VoxelShape WEST_SHAPE = Stream.of(
            Block.makeCuboidShape(4, 4, 4, 12, 14, 12),
            Block.makeCuboidShape(3, 0, 3, 13, 2, 13),
            Block.makeCuboidShape(12, 3, 7, 14, 7, 9),
            Block.makeCuboidShape(5, 2, 5, 11, 4, 11)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context) {
        switch ((Direction)state.get(HORIZONTAL_FACING)) {
            case SOUTH:
                return SOUTH_SHAPE;
            case EAST:
                return EAST_SHAPE;
            case WEST:
                return WEST_SHAPE;
            default:
                return NORTH_SHAPE;
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context){
        return this.getDefaultState().with(HORIZONTAL_FACING, context.getPlacementHorizontalFacing());
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder){
        builder.add(BlockStateProperties.HORIZONTAL_FACING);
    }
}
