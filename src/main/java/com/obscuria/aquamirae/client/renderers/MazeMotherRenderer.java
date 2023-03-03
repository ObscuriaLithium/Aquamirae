
package com.obscuria.aquamirae.client.renderers;

import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.client.models.ModelMazeMother;
import com.obscuria.aquamirae.common.entities.MazeMother;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class MazeMotherRenderer extends MobRenderer<MazeMother, ModelMazeMother<MazeMother>> {
	public MazeMotherRenderer(EntityRendererManager context) {
		super(context, new ModelMazeMother<>(), 1f);
		this.addLayer(new GlowingLayer<>(this, new ResourceLocation(AquamiraeMod.MODID,"textures/entity/maze_mother_overlay.png")));
	}

	@Override
	public @Nonnull ResourceLocation getTextureLocation(@Nonnull MazeMother entity) {
		return new ResourceLocation(AquamiraeMod.MODID,"textures/entity/maze_mother.png");
	}
}
