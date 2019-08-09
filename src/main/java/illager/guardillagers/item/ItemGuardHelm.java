package illager.guardillagers.item;

import com.mojang.blaze3d.platform.GlStateManager;
import illager.guardillagers.GuardIllagers;
import illager.guardillagers.client.model.GuardHatModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class ItemGuardHelm extends ArmorItem {
    protected static final ResourceLocation HAT_TEX = new ResourceLocation(GuardIllagers.MODID, "textures/overlay/guard_perspective.png");

	public ItemGuardHelm(IArmorMaterial materialIn, EquipmentSlotType slots, Properties properties) {
        super(materialIn, slots, properties);
    }


    @OnlyIn(Dist.CLIENT)
    public BipedModel getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, BipedModel _default) {
	    return new GuardHatModel();
    }

	@Nullable
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
		return GuardIllagers.MODID + ":textures/models/armor/guard_hat.png";
	}

	@Override
	public void renderHelmetOverlay(ItemStack stack, PlayerEntity player, int width, int height, float partialTicks) {

        GlStateManager.disableDepthTest();
        GlStateManager.depthMask(false);
        GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableAlphaTest();
        Minecraft.getInstance().getTextureManager().bindTexture(HAT_TEX);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos(0.0D, (double) height, -90.0D).tex(0.0D, 1.0D).endVertex();
        bufferbuilder.pos((double) width, (double) height, -90.0D).tex(1.0D, 1.0D).endVertex();
        bufferbuilder.pos((double) width, 0.0D, -90.0D).tex(1.0D, 0.0D).endVertex();
        bufferbuilder.pos(0.0D, 0.0D, -90.0D).tex(0.0D, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.depthMask(true);
        GlStateManager.enableDepthTest();
        GlStateManager.enableAlphaTest();
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
    }
}