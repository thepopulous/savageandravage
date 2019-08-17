package illager.savageandravage.client.render;

import com.mojang.blaze3d.platform.GlStateManager;
import illager.savageandravage.SavageAndRavageCore;
import illager.savageandravage.client.model.GuardIllagerModel;
import illager.savageandravage.client.render.layer.HeldItemGuardLayer;
import illager.savageandravage.entity.illager.GuardIllagerEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.HeadLayer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GuardIllagerRender<T extends GuardIllagerEntity> extends MobRenderer<T, GuardIllagerModel<T>> {
    private static final ResourceLocation ILLAGER_TEXTURE = new ResourceLocation(SavageAndRavageCore.MODID, "textures/entity/illager/guardillager.png");

	public GuardIllagerRender(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new GuardIllagerModel<>(), 0.5F);
		this.addLayer(new HeadLayer<>(this));
		this.addLayer(new HeldItemLayer(this) {
			public void render(LivingEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
				if (((GuardIllagerEntity) entitylivingbaseIn).isAggressive()) {
					super.render(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
				}

			}
		});
		this.addLayer(new HeldItemGuardLayer(this) {
			public void render(GuardIllagerEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
				if (!entitylivingbaseIn.isAggressive()) {
					super.render(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
				}

			}
		});
	}

	protected ResourceLocation getEntityTexture(GuardIllagerEntity entity) {
		return ILLAGER_TEXTURE;
	}

	/**
	 * Allows the render to do state modifications necessary before the model is rendered.
	 */
	protected void preRenderCallback(GuardIllagerEntity entitylivingbaseIn, float partialTickTime) {
		float f = 0.9375F;
		GlStateManager.scalef(0.9375F, 0.9375F, 0.9375F);
	}
}