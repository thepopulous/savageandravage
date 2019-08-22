package illager.savageandravage.api.entity;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface ICrossArm {
    @OnlyIn(Dist.CLIENT)
    ArmPose getArmPose();
}
