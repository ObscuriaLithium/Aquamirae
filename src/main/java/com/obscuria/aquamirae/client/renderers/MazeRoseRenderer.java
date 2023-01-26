
package com.obscuria.aquamirae.client.renderers;

import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.client.models.ModelMazeRose;
import com.obscuria.aquamirae.world.entities.chakras.MazeRose;
import com.obscuria.obscureapi.client.renderer.ChakraRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class MazeRoseRenderer extends ChakraRenderer<MazeRose> {
	public MazeRoseRenderer(EntityRendererProvider.Context context) {
		super(context, new ModelMazeRose<>(context.bakeLayer(ModelMazeRose.LAYER_LOCATION)));
	}

	@Override
	public @NotNull ResourceLocation getTextureLocation(@NotNull MazeRose mazeRose) {
		return new ResourceLocation(AquamiraeMod.MODID,"textures/entity/maze_rose.png");
	}

	@Override
	public ResourceLocation getGlowingTextureLocation(MazeRose mazeRose) {
		return new ResourceLocation(AquamiraeMod.MODID,"textures/entity/maze_rose_overlay.png");
	}
}
