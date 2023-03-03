
package com.obscuria.aquamirae.client.renderers;

import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.client.models.ModelMazeRose;
import com.obscuria.aquamirae.common.entities.chakras.MazeRose;
import com.obscuria.obscureapi.client.ScalableMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class MazeRoseRenderer extends ScalableMobRenderer<MazeRose, ModelMazeRose<MazeRose>> {
	public MazeRoseRenderer(EntityRendererManager context) {
		super(context, new ModelMazeRose<>(), 0.6f);
		this.addLayer(new GlowingLayer<>(this, new ResourceLocation(AquamiraeMod.MODID,"textures/entity/maze_rose_overlay.png")));
	}

	@Override
	public @Nonnull ResourceLocation getTextureLocation(@Nonnull MazeRose entity) {
		return new ResourceLocation(AquamiraeMod.MODID,"textures/entity/maze_rose.png");
	}
}
