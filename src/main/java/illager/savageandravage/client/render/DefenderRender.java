package illager.savageandravage.client.render;

import illager.savageandravage.SavageAndRavageCore;
import illager.savageandravage.client.model.DefenderModel;
import illager.savageandravage.client.render.layer.HeldItemGuardLayer;
import illager.savageandravage.entity.illager.DefenderEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.HeadLayer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DefenderRender<T extends DefenderEntity> extends MobRenderer<T, DefenderModel<T>> {
    private static final ResourceLocation ILLAGER_TEXTURE = new ResourceLocation(SavageAndRavageCore.MODID, "textures/entity/illager/guardillager.png");

    public DefenderRender(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new DefenderModel<>(), 0.5F);
		this.addLayer(new HeadLayer<>(this));
		this.addLayer(new HeldItemLayer(this) {
			public void render(LivingEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
                if (((DefenderEntity) entitylivingbaseIn).isAggressive()) {
					super.render(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
				}

			}
		});
		this.addLayer(new HeldItemGuardLayer(this) {
            public void render(DefenderEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
				if (!entitylivingbaseIn.isAggressive()) {
					super.render(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
				}

			}
		});
	}

    protected ResourceLocation getEntityTexture(DefenderEntity entity) {
		return ILLAGER_TEXTURE;
	}

}