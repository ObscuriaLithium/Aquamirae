
package com.obscuria.aquamirae.client.renderer;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.client.AquamiraeLayers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.RenderType;

import com.obscuria.aquamirae.common.entity.MazeMother;
import com.obscuria.aquamirae.client.model.ModelMazeMother;
import org.jetbrains.annotations.NotNull;

public class MazeMotherRenderer extends MobRenderer<MazeMother, ModelMazeMother> {
	public MazeMotherRenderer(EntityRendererProvider.Context context) {
		super(context, new ModelMazeMother(context.bakeLayer(AquamiraeLayers.MAZE_MOTHER)), 1f);
		this.addLayer(new EyesLayer<>(this) {
			@Override
			public @NotNull RenderType renderType() {
				return RenderType.eyes(Aquamirae.key("textures/entity/maze_mother_overlay.png"));
			}
		});
	}

	@Override
	public @NotNull ResourceLocation getTextureLocation(@NotNull MazeMother entity) {
		return Aquamirae.key("textures/entity/maze_mother.png");
	}
}
