
package com.obscuria.aquamirae.client.renderer;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.client.AquamiraeLayers;
import com.obscuria.aquamirae.common.entity.TorturedSoul;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.RenderType;

import com.obscuria.aquamirae.client.model.ModelTorturedSoul;
import org.jetbrains.annotations.NotNull;

public class TorturedSoulRenderer extends MobRenderer<TorturedSoul, ModelTorturedSoul> {
	public TorturedSoulRenderer(EntityRendererProvider.Context context) {
		super(context, new ModelTorturedSoul(context.bakeLayer(AquamiraeLayers.TORTURED_SOUL)), 0.5f);
		this.addLayer(new EyesLayer<>(this) {
			@Override
			public @NotNull RenderType renderType() {
				return RenderType.eyes(Aquamirae.key("textures/entity/tortured_soul_overlay.png"));
			}
		});
	}

	@Override
	public @NotNull ResourceLocation getTextureLocation(@NotNull TorturedSoul entity) {
		return Aquamirae.key("textures/entity/tortured_soul.png");
	}

	@Override
	protected boolean isShaking(@NotNull TorturedSoul entity) {
		return true;
	}
}
