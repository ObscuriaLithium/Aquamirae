
package com.obscuria.aquamirae.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.client.AquamiraeLayers;
import com.obscuria.aquamirae.client.models.ModelMaw;
import com.obscuria.aquamirae.common.entities.Maw;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

public class MawRenderer extends MobRenderer<Maw, ModelMaw> {
	public MawRenderer(EntityRendererProvider.Context context) {
		super(context, new ModelMaw(context.bakeLayer(AquamiraeLayers.MAW)), 0.9f);
		this.addLayer(new EyesLayer<>(this) {
			@Override public @NotNull RenderType renderType() {
				return RenderType.eyes(new ResourceLocation(Aquamirae.MODID,"textures/entity/maw_overlay.png"));
			}
		});
		this.addLayer(new MawItemLayer(this));
	}

	@Override
	public @NotNull ResourceLocation getTextureLocation(@NotNull Maw entity) {
		return new ResourceLocation(Aquamirae.MODID,"textures/entity/maw.png");
	}

	@OnlyIn(Dist.CLIENT)
	public static class MawItemLayer extends RenderLayer<Maw, ModelMaw> {
		public MawItemLayer(RenderLayerParent<Maw, ModelMaw> layer) {
			super(layer);
		}

		public void render(@NotNull PoseStack pose, @NotNull MultiBufferSource source, int i1, Maw maw, float f1, float f2, float f3, float f4, float f5, float f6) {
			if (!maw.getItemInMouth().isEmpty()) {
				pose.pushPose();
				this.getParentModel().translate(pose);
				pose.mulPose(Axis.XP.rotationDegrees(100.0F));
				pose.mulPose(Axis.ZP.rotationDegrees(0.0F));
				pose.translate(0.0D, -0.8D, 0.02D);
				pose.scale(0.7f, 0.7f, 0.7f);
				Minecraft.getInstance().gameRenderer.itemInHandRenderer.renderItem(maw, maw.getItemInMouth(), ItemDisplayContext.FIXED, false, pose, source, i1);
				pose.popPose();
			}
		}
	}
}
