
package com.obscuria.aquamirae.client.renderers;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.client.AquamiraeLayers;
import com.obscuria.aquamirae.client.models.ModelMazeRose;
import com.obscuria.aquamirae.common.entities.projectiles.MazeRose;
import com.obscuria.obscureapi.client.renderer.DynamicProjectileRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class MazeRoseRenderer extends DynamicProjectileRenderer<MazeRose> {

    public static final ResourceLocation TEXTURE = Aquamirae.identifier("textures/entity/maze_rose.png");
    public static final ResourceLocation OVERLAY_TEXTURE = Aquamirae.identifier("textures/entity/maze_rose_overlay.png");

	public MazeRoseRenderer(EntityRendererProvider.Context context) {
		super(context, new ModelMazeRose<>(context.bakeLayer(AquamiraeLayers.MAZE_ROSE)));
	}

	@Override
	public ResourceLocation getTextureLocation(MazeRose mazeRose) {
		return TEXTURE;
	}

	@Override
	public ResourceLocation getGlowingTextureLocation(MazeRose mazeRose) {
		return OVERLAY_TEXTURE;
	}
}
