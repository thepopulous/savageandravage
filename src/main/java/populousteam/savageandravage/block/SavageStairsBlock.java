package populousteam.savageandravage.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.StairsBlock;

/**This class is necessary because StairsBlock doesn't work in the same way SlabBlock and WallBlock do.*/
public class SavageStairsBlock extends StairsBlock {
    public SavageStairsBlock(BlockState state, Properties properties){
        super(state, properties);
    }
}