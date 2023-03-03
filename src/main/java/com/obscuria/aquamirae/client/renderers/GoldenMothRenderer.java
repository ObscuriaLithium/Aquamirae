
package com.obscuria.aquamirae.client.renderers;

import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.client.models.ModelGoldenMoth;
import com.obscuria.aquamirae.common.entities.GoldenMoth;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class GoldenMothRenderer extends MobRenderer<GoldenMoth, ModelGoldenMoth<GoldenMoth>> {
	public GoldenMothRenderer(EntityRendererManager context) {
		super(context, new ModelGoldenMoth<>(), 0.2f);
		this.addLayer(new GlowingLayer<>(this, new ResourceLocation(AquamiraeMod.MODID,"textures/entity/golden_moth_overlay.png")));
	}

	@Override
	public @Nonnull ResourceLocation getTextureLocation(@Nonnull GoldenMoth entity) {
		return new ResourceLocation(AquamiraeMod.MODID,"textures/entity/golden_moth.png");
	}
}
