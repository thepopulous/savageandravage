package com.populousteam.savageandravage.client.render;

import com.populousteam.savageandravage.SavageAndRavageCore;
import com.populousteam.savageandravage.client.model.HyenaModel;
import com.populousteam.savageandravage.entity.HyenaEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.HeadLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class HyenaRender<T extends HyenaEntity> extends MobRenderer<T, HyenaModel<T>> {
    private static final ResourceLocation HYENA_TEXTURE = new ResourceLocation(SavageAndRavageCore.MODID, "textures/entity/hyena/hyena.png");
    private static final ResourceLocation HYENA_TAMED_TEXTURE = new ResourceLocation(SavageAndRavageCore.MODID, "textures/entity/hyena/hyena_tame.png");
    private static final ResourceLocation HYENA_ANGRY_TEXTURE = new ResourceLocation(SavageAndRavageCore.MODID, "textures/entity/hyena/hyena_angry.png");
    private static final ResourceLocation HYENA_BAGU_TEXTURE = new ResourceLocation(SavageAndRavageCore.MODID, "textures/entity/hyena/hyena_bagu.png");
    private static final ResourceLocation HYENA_BAGU_TAMED_TEXTURE = new ResourceLocation(SavageAndRavageCore.MODID, "textures/entity/hyena/hyena_bagu_tamed.png");

    public HyenaRender(EntityRendererManager p_i50959_1_) {
        super(p_i50959_1_, new HyenaModel<>(), 0.5F);
        this.addLayer(new HeadLayer<>(this));
    }

    @Nullable
    protected ResourceLocation getEntityTexture(HyenaEntity entity) {
        String s = TextFormatting.getTextWithoutFormattingCodes(entity.getName().getString());

        if (s != null && "bagu_chan".equals(s)) {
            if (entity.isBegging()) {
                return HYENA_BAGU_TAMED_TEXTURE;
            } else {
                return HYENA_BAGU_TEXTURE;
            }
        } else if (entity.isAngry()) {
            return HYENA_ANGRY_TEXTURE;
        } else {
            if (entity.isBegging()) {
                return HYENA_TAMED_TEXTURE;
            } else {
                return HYENA_TEXTURE;
            }
        }
    }
}