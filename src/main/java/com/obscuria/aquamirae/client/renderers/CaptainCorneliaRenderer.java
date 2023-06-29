
package com.obscuria.aquamirae.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.client.AquamiraeLayers;
import com.obscuria.aquamirae.client.models.ModelCaptainCornelia;
import com.obscuria.aquamirae.common.entities.CaptainCornelia;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

public class CaptainCorneliaRenderer extends MobRenderer<CaptainCornelia, ModelCaptainCornelia> {
	public CaptainCorneliaRenderer(EntityRendererProvider.Context context) {
		super(context, new ModelCaptainCornelia(context.bakeLayer(AquamiraeLayers.CAPTAIN_CORNELIA)), 0.5f);
		this.addLayer(new EyesLayer<>(this) {
			@Override public @NotNull RenderType renderType() {
				return RenderType.eyes(new ResourceLocation(Aquamirae.MODID,"textures/entity/captain_cornelia_overlay.png"));
			}
		});
		this.addLayer(new CaptainCorneliaItemLayer(this));
	}

	@Override
	public @NotNull ResourceLocation getTextureLocation(@NotNull CaptainCornelia entity) {
		return new ResourceLocation(Aquamirae.MODID,"textures/entity/captain_cornelia.png");
	}

	@OnlyIn(Dist.CLIENT)
	public static class CaptainCorneliaItemLayer extends RenderLayer<CaptainCornelia, ModelCaptainCornelia> {
		public CaptainCorneliaItemLayer(RenderLayerParent<CaptainCornelia, ModelCaptainCornelia> layer) {
			super(layer);
		}

		public void render(@NotNull PoseStack pose, @NotNull MultiBufferSource source, int i1, CaptainCornelia entity, float f1, float f2, float f3, float f4, float f5, float f6) {
			ItemStack right = entity.getItemBySlot(EquipmentSlot.MAINHAND);
			if (!right.isEmpty()) {
				pose.pushPose();
				this.getParentModel().translateToHand(HumanoidArm.RIGHT, pose);
				pose.mulPose(Axis.XP.rotationDegrees(-90.0F));
				pose.mulPose(Axis.YP.rotationDegrees(180.0F));
				pose.translate(0.0D, 0.1D, 0.0D);
				Minecraft.getInstance().gameRenderer.itemInHandRenderer.renderItem(entity, right, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, false, pose, source, i1);
				pose.popPose();
			}
			ItemStack left = entity.getItemBySlot(EquipmentSlot.OFFHAND);
			if (!left.isEmpty()) {
				pose.pushPose();
				this.getParentModel().translateToHand(HumanoidArm.LEFT, pose);
				pose.mulPose(Axis.XP.rotationDegrees(45.0F));
				pose.translate(0.0D, -0.15D, -0.65D);
				Minecraft.getInstance().gameRenderer.itemInHandRenderer.renderItem(entity, left, ItemDisplayContext.THIRD_PERSON_LEFT_HAND, false, pose, source, i1);
				pose.popPose();
			}
		}
	}
}
