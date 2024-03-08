
package com.obscuria.aquamirae.client.renderer;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.client.AquamiraeLayers;
import com.obscuria.aquamirae.client.model.ModelEel;
import com.obscuria.aquamirae.common.entity.Eel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class EelRenderer extends MobRenderer<Eel, ModelEel> {
	public EelRenderer(EntityRendererProvider.Context context) {
		super(context, new ModelEel(context.bakeLayer(AquamiraeLayers.EEL)), 0f);
		this.addLayer(new EyesLayer<>(this) {
			@Override
			public @NotNull RenderType renderType() {
				return RenderType.eyes(Aquamirae.key("textures/entity/eel_overlay.png"));
			}
		});
	}

	@Override
	public @NotNull ResourceLocation getTextureLocation(@NotNull Eel entity) {
		return Aquamirae.key("textures/entity/eel.png");
	}
}
