
package com.obscuria.aquamirae.client.renderers;

import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.client.models.ModelEel;
import com.obscuria.aquamirae.common.entities.Eel;
import com.obscuria.obscureapi.client.ScalableMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class EelRenderer extends ScalableMobRenderer<Eel, ModelEel<Eel>> {
	public EelRenderer(EntityRendererManager context) {
		super(context, new ModelEel<>(), 0f);
		this.addLayer(new GlowingLayer<>(this, new ResourceLocation(AquamiraeMod.MODID,"textures/entity/eel_overlay.png")));
	}

	@Override
	public @Nonnull ResourceLocation getTextureLocation(@Nonnull Eel entity) {
		return new ResourceLocation(AquamiraeMod.MODID,"textures/entity/eel.png");
	}
}
