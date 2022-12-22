
package com.obscuria.aquamirae.client.renderers;

import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.obscureapi.client.ScalableMobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.RenderType;

import com.obscuria.aquamirae.world.entities.chakras.MazeRose;
import com.obscuria.aquamirae.client.models.ModelMazeRose;
import org.jetbrains.annotations.NotNull;

public class MazeRoseRenderer extends ScalableMobRenderer<MazeRose, ModelMazeRose<MazeRose>> {
	public MazeRoseRenderer(EntityRendererProvider.Context context) {
		super(context, new ModelMazeRose<>(context.bakeLayer(ModelMazeRose.LAYER_LOCATION)), 0.6f);
		this.addLayer(new EyesLayer<>(this) {
			@Override public @NotNull RenderType renderType() {
				return RenderType.eyes(new ResourceLocation(AquamiraeMod.MODID,"textures/entity/maze_rose_overlay.png")); }});
	}

	@Override
	public @NotNull ResourceLocation getTextureLocation(@NotNull MazeRose entity) {
		return new ResourceLocation(AquamiraeMod.MODID,"textures/entity/maze_rose.png");
	}
}
