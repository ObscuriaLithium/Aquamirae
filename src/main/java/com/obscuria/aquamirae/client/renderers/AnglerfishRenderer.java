
package com.obscuria.aquamirae.client.renderers;

import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.client.models.ModelAnglerfish;
import com.obscuria.aquamirae.world.entities.Anglerfish;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class AnglerfishRenderer extends MobRenderer<Anglerfish, ModelAnglerfish<Anglerfish>> {
	public AnglerfishRenderer(EntityRendererManager context) {
		super(context, new ModelAnglerfish<>(), 1.5f);
		this.addLayer(new GlowingLayer<>(this, new ResourceLocation(AquamiraeMod.MODID, "textures/entity/anglerfish_overlay.png")));
	}

	@Override
	public @Nonnull ResourceLocation getTextureLocation(@Nonnull Anglerfish entity) {
		return new ResourceLocation(AquamiraeMod.MODID,"textures/entity/anglerfish.png");
	}
}
