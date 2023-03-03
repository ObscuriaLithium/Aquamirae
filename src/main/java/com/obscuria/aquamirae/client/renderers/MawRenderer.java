
package com.obscuria.aquamirae.client.renderers;

import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.common.entities.Maw;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.RenderType;

import com.obscuria.aquamirae.client.models.ModelMaw;
import org.jetbrains.annotations.NotNull;

public class MawRenderer extends MobRenderer<Maw, ModelMaw<Maw>> {
	public MawRenderer(EntityRendererProvider.Context context) {
		super(context, new ModelMaw<>(context.bakeLayer(ModelMaw.LAYER_LOCATION)), 0.9f);
		this.addLayer(new EyesLayer<>(this) {
			@Override
			public @NotNull RenderType renderType() {
				return RenderType.eyes(new ResourceLocation(AquamiraeMod.MODID,"textures/entity/maw_overlay.png"));
			}
		});
	}

	@Override
	public @NotNull ResourceLocation getTextureLocation(@NotNull Maw entity) {
		return new ResourceLocation(AquamiraeMod.MODID,"textures/entity/maw.png");
	}
}
