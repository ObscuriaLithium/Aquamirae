
package com.obscuria.aquamirae.client.renderers;

import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.world.entities.TorturedSoul;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.RenderType;

import com.obscuria.aquamirae.client.models.ModelTorturedSoul;
import org.jetbrains.annotations.NotNull;

public class TorturedSoulRenderer extends MobRenderer<TorturedSoul, ModelTorturedSoul<TorturedSoul>> {
	public TorturedSoulRenderer(EntityRendererProvider.Context context) {
		super(context, new ModelTorturedSoul<>(context.bakeLayer(ModelTorturedSoul.LAYER_LOCATION)), 0.5f);
		this.addLayer(new EyesLayer<>(this) {
			@Override
			public @NotNull RenderType renderType() {
				return RenderType.eyes(new ResourceLocation(AquamiraeMod.MODID,"textures/entity/tortured_soul_overlay.png"));
			}
		});
	}

	@Override
	public @NotNull ResourceLocation getTextureLocation(@NotNull TorturedSoul entity) {
		return new ResourceLocation(AquamiraeMod.MODID,"textures/entity/tortured_soul.png");
	}

	@Override
	protected boolean isShaking(@NotNull TorturedSoul entity) {
		return true;
	}
}
