
package com.obscuria.aquamirae.client.renderers;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.client.AquamiraeLayers;
import com.obscuria.aquamirae.client.models.ModelMazeRose;
import com.obscuria.aquamirae.common.entities.projectiles.MazeRose;
import com.obscuria.obscureapi.client.renderer.DynamicProjectileRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;

public class MazeRoseRenderer extends DynamicProjectileRenderer<MazeRose> {
	public MazeRoseRenderer(EntityRendererFactory.Context context) {
		super(context, new ModelMazeRose<>(context.getPart(AquamiraeLayers.MAZE_ROSE)));
	}

	@Override
	public Identifier getTexture(MazeRose entity) {
		return Aquamirae.key("textures/entity/maze_rose.png");
	}

	@Override
	public Identifier getGlowingTextureLocation(MazeRose rose) {
		return Aquamirae.key("textures/entity/maze_rose_overlay.png");
	}
}
