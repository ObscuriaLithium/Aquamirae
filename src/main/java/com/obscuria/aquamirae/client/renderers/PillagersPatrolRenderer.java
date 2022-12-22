
package com.obscuria.aquamirae.client.renderers;

import com.obscuria.aquamirae.AquamiraeMod;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.SlimeModel;

import com.obscuria.aquamirae.world.entities.PillagersPatrol;
import org.jetbrains.annotations.NotNull;

public class PillagersPatrolRenderer extends MobRenderer<PillagersPatrol, SlimeModel<PillagersPatrol>> {
	public PillagersPatrolRenderer(EntityRendererProvider.Context context) {
		super(context, new SlimeModel<>(context.bakeLayer(ModelLayers.SLIME)), 0f);
	}

	@Override
	public boolean shouldRender(@NotNull PillagersPatrol entity, @NotNull Frustum frustum, double d1, double d2, double d3) {
		return false;
	}

	@Override
	public @NotNull ResourceLocation getTextureLocation(@NotNull PillagersPatrol entity) {
		return new ResourceLocation(AquamiraeMod.MODID,"textures/entity/blank.png");
	}
}
