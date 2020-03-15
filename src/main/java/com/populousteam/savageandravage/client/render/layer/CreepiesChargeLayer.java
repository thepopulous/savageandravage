package com.populousteam.savageandravage.client.render.layer;

import com.populousteam.savageandravage.client.model.CreepiesModel;
import com.populousteam.savageandravage.entity.CreepieEntity;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.EnergyLayer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CreepiesChargeLayer extends EnergyLayer<CreepieEntity, CreepiesModel<CreepieEntity>> {
    private static final ResourceLocation LIGHTNING_TEXTURE = new ResourceLocation("textures/entity/creeper/creeper_armor.png");
    private final CreepiesModel<CreepieEntity> creeperModel = new CreepiesModel<>(2.0F);

    public CreepiesChargeLayer(IEntityRenderer<CreepieEntity, CreepiesModel<CreepieEntity>> p_i50947_1_) {
        super(p_i50947_1_);
    }

    protected float func_225634_a_(float p_225634_1_) {
        return p_225634_1_ * 0.01F;
    }

    protected ResourceLocation func_225633_a_() {
        return LIGHTNING_TEXTURE;
    }

    protected EntityModel<CreepieEntity> func_225635_b_() {
        return this.creeperModel;
    }
}