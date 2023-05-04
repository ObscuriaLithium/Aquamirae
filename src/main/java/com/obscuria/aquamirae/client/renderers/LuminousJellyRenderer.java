
package com.obscuria.aquamirae.client.renderers;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.client.AquamiraeLayers;
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
		super(context, new ModelLuminousJelly<>(context.bakeLayer(AquamiraeLayers.LUMINOUS_JELLY)), 1.5f);
		this.addLayer(new EyesLayer<>(this) {
			@Override public @NotNull RenderType renderType() {
				return RenderType.eyes(new ResourceLocation(Aquamirae.MODID,"textures/entity/luminous_jelly_overlay.png"));
			}
		});
	}

	@Override
	public @NotNull ResourceLocation getTextureLocation(@NotNull LuminousJelly entity) {
		return new ResourceLocation(Aquamirae.MODID,"textures/entity/luminous_jelly.png");
	}
}
