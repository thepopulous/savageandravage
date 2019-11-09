package populousteam.savageandravage.client.model;//Made with Blockbench
//Paste this code into your mod.

import com.mojang.blaze3d.platform.GlStateManager;
import populousteam.savageandravage.entity.SavagelingEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.ModelBox;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SavagelingModel<T extends SavagelingEntity> extends EntityModel<T> {
    private final RendererModel head;
    private final RendererModel body;
    private final RendererModel leg_right;
    private final RendererModel leg_left;
    private final RendererModel jaw;
    private final RendererModel wing_right;
    private final RendererModel wing_left;
    public SavagelingModel() {
        textureWidth = 40;
        textureHeight = 40;

        head = new RendererModel(this);
        head.setRotationPoint(0.0F, 15.0F, -2.0F);
        head.cubeList.add(new ModelBox(head, 0, 0, -3.0F, -6.0F, -6.0F, 6, 8, 6, 0.0F, false));
        head.cubeList.add(new ModelBox(head, 24, 0, -1.0F, -1.0F, -8.0F, 2, 4, 2, 0.0F, false));

        body = new RendererModel(this);
        body.setRotationPoint(0.0F, 24.0F, 0.0F);
        body.cubeList.add(new ModelBox(body, 0, 26, -4.0F, -10.0F, -3.0F, 8, 6, 8, 0.0F, false));
        body.cubeList.add(new ModelBox(body, 13, 19, -3.0F, -8.0F, 5.0F, 6, 0, 5, 0.0F, false));

        leg_right = new RendererModel(this);
        leg_right.setRotationPoint(-2.0F, 20.0F, 2.0F);
        leg_right.cubeList.add(new ModelBox(leg_right, 28, 8, -1.0F, 0.0F, -1.0F, 1, 4, 1, 0.0F, false));
        leg_right.cubeList.add(new ModelBox(leg_right, 22, 6, -2.0F, 4.0F, -2.0F, 3, 0, 2, 0.0F, false));

        leg_left = new RendererModel(this);
        leg_left.setRotationPoint(2.0F, 20.0F, 2.0F);
        leg_left.cubeList.add(new ModelBox(leg_left, 28, 8, 0.0F, 0.0F, -1.0F, 1, 4, 1, 0.0F, false));
        leg_left.cubeList.add(new ModelBox(leg_left, 22, 6, -1.0F, 4.0F, -2.0F, 3, 0, 2, 0.0F, false));

        jaw = new RendererModel(this);
        jaw.setRotationPoint(0.0F, 17.0F, -2.0F);
        setRotationAngle(jaw, 0.0873F, 0.0F, 0.0F);
        jaw.cubeList.add(new ModelBox(jaw, 0, 19, -3.0F, -1.0F, -6.0F, 6, 2, 5, 0.0F, false));

        wing_right = new RendererModel(this);
        wing_right.setRotationPoint(-4.0F, 14.0F, 1.0F);
        wing_right.cubeList.add(new ModelBox(wing_right, 18, 8, -1.0F, 0.0F, -3.0F, 1, 5, 6, 0.0F, false));

        wing_left = new RendererModel(this);
        wing_left.setRotationPoint(4.0F, 14.0F, 0.0F);
        wing_left.cubeList.add(new ModelBox(wing_left, 18, 8, 0.0F, 0.0F, -2.0F, 1, 5, 6, 0.0F, false));
    }

    @Override
    public void render(T entity, float f, float f1, float f2, float f3, float f4, float f5) {
        if (isChild) {
            GlStateManager.pushMatrix();
            GlStateManager.scalef(1.35F, 1.35F, 1.35F);
            GlStateManager.translated(0.0F, -0.12F, 0.0F);
            head.render(f5);
            jaw.render(f5);
            GlStateManager.popMatrix();
        } else {
            head.render(f5);
            jaw.render(f5);
        }
        body.render(f5);
        leg_right.render(f5);
        leg_left.render(f5);
        wing_right.render(f5);
        wing_left.render(f5);
    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
        this.head.rotateAngleX = headPitch * ((float) Math.PI / 180F);
        this.head.rotateAngleY = netHeadYaw * ((float) Math.PI / 180F);

        this.jaw.rotateAngleX = headPitch * ((float) Math.PI / 180F) * 0.7F + 0.4F;
        this.jaw.rotateAngleY = netHeadYaw * ((float) Math.PI / 180F);

        this.leg_right.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.leg_left.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;

        this.wing_right.rotateAngleZ = ageInTicks;
        this.wing_left.rotateAngleZ = -ageInTicks;
    }

    public void setRotationAngle(RendererModel modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}