package populousteam.savageandravage.entity.path;

import net.minecraft.entity.MobEntity;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.world.World;
import populousteam.savageandravage.entity.path.processor.UseFenceNodeProcessor;

public class GroundFencePathNavigator extends GroundPathNavigator {
    public GroundFencePathNavigator(MobEntity entitylivingIn, World worldIn) {
        super(entitylivingIn, worldIn);
    }

    protected PathFinder getPathFinder(int p_179679_1_) {
        this.nodeProcessor = new UseFenceNodeProcessor();
        this.nodeProcessor.setCanEnterDoors(true);
        return new PathFinder(this.nodeProcessor, p_179679_1_);
    }
}
