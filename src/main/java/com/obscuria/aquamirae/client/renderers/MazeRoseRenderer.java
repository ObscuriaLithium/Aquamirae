
package com.obscuria.aquamirae.client.renderers;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.client.AquamiraeLayers;
import com.obscuria.aquamirae.client.models.ModelMazeRose;
import com.obscuria.aquamirae.common.entities.projectiles.MazeRose;
import com.obscuria.obscureapi.client.renderer.DynamicProjectileRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class MazeRoseRenderer extends DynamicProjectileRenderer<MazeRose> {
	public MazeRoseRenderer(EntityRendererProvider.Context context) {
		super(context, new ModelMazeRose<>(context.bakeLayer(AquamiraeLayers.MAZE_ROSE)));
	}

	@Override
	public @NotNull ResourceLocation getTextureLocation(@NotNull MazeRose mazeRose) {
		return new ResourceLocation(Aquamirae.MODID,"textures/entity/maze_rose.png");
	}

	@Override
	public ResourceLocation getGlowingTextureLocation(MazeRose mazeRose) {
		return new ResourceLocation(Aquamirae.MODID,"textures/entity/maze_rose_overlay.png");
	}
}
