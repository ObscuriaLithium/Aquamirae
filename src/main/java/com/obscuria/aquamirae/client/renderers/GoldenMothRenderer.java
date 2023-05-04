
package com.obscuria.aquamirae.client.renderers;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.client.AquamiraeLayers;
import com.obscuria.aquamirae.common.entities.GoldenMoth;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.RenderType;

import com.obscuria.aquamirae.client.models.ModelGoldenMoth;
import org.jetbrains.annotations.NotNull;

public class GoldenMothRenderer extends MobRenderer<GoldenMoth, ModelGoldenMoth<GoldenMoth>> {
	public GoldenMothRenderer(EntityRendererProvider.Context context) {
		super(context, new ModelGoldenMoth<>(context.bakeLayer(AquamiraeLayers.GOLDEN_MOTH)), 0.2f);
		this.addLayer(new EyesLayer<>(this) {
			@Override
			public @NotNull RenderType renderType() {
				return RenderType.eyes(new ResourceLocation(Aquamirae.MODID,"textures/entity/golden_moth_overlay.png"));
			}
		});
	}

	@Override
	public @NotNull ResourceLocation getTextureLocation(@NotNull GoldenMoth entity) {
		return new ResourceLocation(Aquamirae.MODID,"textures/entity/golden_moth.png");
	}
}
