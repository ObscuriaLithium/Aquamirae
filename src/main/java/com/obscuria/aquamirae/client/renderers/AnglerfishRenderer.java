
package com.obscuria.aquamirae.client.renderers;

import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.world.entities.Anglerfish;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.RenderType;

import com.obscuria.aquamirae.client.models.ModelAnglerfish;
import org.jetbrains.annotations.NotNull;

public class AnglerfishRenderer extends MobRenderer<Anglerfish, ModelAnglerfish<Anglerfish>> {
	public AnglerfishRenderer(EntityRendererProvider.Context context) {
		super(context, new ModelAnglerfish<>(context.bakeLayer(ModelAnglerfish.LAYER_LOCATION)), 1.5f);
		this.addLayer(new EyesLayer<>(this) {
			@Override
			public @NotNull RenderType renderType() {
				return RenderType.eyes(new ResourceLocation(AquamiraeMod.MODID, "textures/entity/anglerfish_overlay.png"));
			}
		});
	}

	@Override
	public @NotNull ResourceLocation getTextureLocation(@NotNull Anglerfish entity) {
		return new ResourceLocation(AquamiraeMod.MODID,"textures/entity/anglerfish.png");
	}
}
