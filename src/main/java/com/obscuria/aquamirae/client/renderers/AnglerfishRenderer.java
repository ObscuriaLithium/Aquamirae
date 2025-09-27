
package com.obscuria.aquamirae.client.renderers;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.client.AquamiraeLayers;
import com.obscuria.aquamirae.common.entities.Anglerfish;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.RenderType;

import com.obscuria.aquamirae.client.models.ModelAnglerfish;
import org.jetbrains.annotations.NotNull;

public class AnglerfishRenderer extends MobRenderer<Anglerfish, ModelAnglerfish> {
	public AnglerfishRenderer(EntityRendererProvider.Context context) {
		super(context, new ModelAnglerfish(context.bakeLayer(AquamiraeLayers.ANGLERFISH)), 1.5f);
		this.addLayer(new EyesLayer<>(this) {
			@Override
			public @NotNull RenderType renderType() {
				return RenderType.eyes(new ResourceLocation(Aquamirae.MODID, "textures/entity/anglerfish_overlay.png"));
			}
		});
	}

	@Override
	public @NotNull ResourceLocation getTextureLocation(@NotNull Anglerfish entity) {
		return new ResourceLocation(Aquamirae.MODID,"textures/entity/anglerfish.png");
	}
}
