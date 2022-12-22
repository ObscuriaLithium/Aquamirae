
package com.obscuria.aquamirae.client.renderers;

import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.obscureapi.client.ScalableMobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.RenderType;

import com.obscuria.aquamirae.world.entities.Eel;
import com.obscuria.aquamirae.client.models.ModelEel;
import org.jetbrains.annotations.NotNull;

public class EelRenderer extends ScalableMobRenderer<Eel, ModelEel<Eel>> {
	public EelRenderer(EntityRendererProvider.Context context) {
		super(context, new ModelEel<>(context.bakeLayer(ModelEel.LAYER_LOCATION)), 0f);
		this.addLayer(new EyesLayer<>(this) {
			@Override
			public @NotNull RenderType renderType() {
				return RenderType.eyes(new ResourceLocation(AquamiraeMod.MODID,"textures/entity/eel_overlay.png"));
			}
		});
	}

	@Override
	public @NotNull ResourceLocation getTextureLocation(@NotNull Eel entity) {
		return new ResourceLocation(AquamiraeMod.MODID,"textures/entity/eel.png");
	}
}
