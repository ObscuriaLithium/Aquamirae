
package com.obscuria.aquamirae.client.renderers;

import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.client.models.ModelLuminousJelly;
import com.obscuria.aquamirae.common.entities.LuminousJelly;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class LuminousJellyRenderer extends MobRenderer<LuminousJelly, ModelLuminousJelly<LuminousJelly>> {
	public LuminousJellyRenderer(EntityRendererProvider.Context context) {
		super(context, new ModelLuminousJelly<>(context.bakeLayer(ModelLuminousJelly.LAYER_LOCATION)), 1.5f);
		this.addLayer(new EyesLayer<>(this) {
			@Override public @NotNull RenderType renderType() {
				return RenderType.eyes(new ResourceLocation(AquamiraeMod.MODID,"textures/entity/luminous_jelly_overlay.png"));
			}
		});
	}

	@Override
	public @NotNull ResourceLocation getTextureLocation(@NotNull LuminousJelly entity) {
		return new ResourceLocation(AquamiraeMod.MODID,"textures/entity/luminous_jelly.png");
	}
}
