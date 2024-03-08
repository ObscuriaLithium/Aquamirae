
package com.obscuria.aquamirae.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.client.AquamiraeLayers;
import com.obscuria.aquamirae.common.entity.GoldenMoth;
import com.obscuria.core.api.graphic.world.Trail3DRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.RenderType;

import com.obscuria.aquamirae.client.model.ModelMoth;
import org.jetbrains.annotations.NotNull;

public class GoldenMothRenderer extends MobRenderer<GoldenMoth, ModelMoth<GoldenMoth>> {
	public GoldenMothRenderer(EntityRendererProvider.Context context) {
		super(context, new ModelMoth<>(context.bakeLayer(AquamiraeLayers.MOTH)), 0.2f);
		this.addLayer(new EyesLayer<>(this) {
			@Override
			public @NotNull RenderType renderType() {
				return RenderType.eyes(Aquamirae.key("textures/entity/golden_moth_overlay.png"));
			}
		});
	}

	@Override
	public void render(GoldenMoth entity, float f1, float f2, PoseStack pose, MultiBufferSource bufferSource, int i) {
		super.render(entity, f1, f2, pose, bufferSource, i);
		Trail3DRenderer.of(entity.trail).render(entity, pose, bufferSource, Minecraft.getInstance().getPartialTick());
	}

	@Override
	public @NotNull ResourceLocation getTextureLocation(@NotNull GoldenMoth entity) {
		return Aquamirae.key("textures/entity/golden_moth.png");
	}
}
