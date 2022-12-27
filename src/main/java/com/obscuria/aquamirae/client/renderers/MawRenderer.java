
package com.obscuria.aquamirae.client.renderers;

import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.client.models.ModelMaw;
import com.obscuria.aquamirae.world.entities.Maw;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class MawRenderer extends MobRenderer<Maw, ModelMaw<Maw>> {
	public MawRenderer(EntityRendererManager context) {
		super(context, new ModelMaw<>(), 0.9f);
		this.addLayer(new GlowingLayer<>(this, new ResourceLocation(AquamiraeMod.MODID,"textures/entity/maw_overlay.png")));
	}

	@Override
	public @Nonnull ResourceLocation getTextureLocation(@Nonnull Maw entity) {
		return new ResourceLocation(AquamiraeMod.MODID,"textures/entity/maw.png");
	}
}
