
package com.obscuria.aquamirae.client.renderers;

import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.client.models.ModelTorturedSoul;
import com.obscuria.aquamirae.common.entities.TorturedSoul;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class TorturedSoulRenderer extends MobRenderer<TorturedSoul, ModelTorturedSoul<TorturedSoul>> {
	public TorturedSoulRenderer(EntityRendererManager context) {
		super(context, new ModelTorturedSoul<>(), 0.5f);
		this.addLayer(new GlowingLayer<>(this, new ResourceLocation(AquamiraeMod.MODID,"textures/entity/tortured_soul_overlay.png")));
	}

	@Override
	public @Nonnull ResourceLocation getTextureLocation(@Nonnull TorturedSoul entity) {
		return new ResourceLocation(AquamiraeMod.MODID,"textures/entity/tortured_soul.png");
	}

	@Override
	protected boolean isShaking(@Nonnull TorturedSoul entity) {
		return true;
	}
}
