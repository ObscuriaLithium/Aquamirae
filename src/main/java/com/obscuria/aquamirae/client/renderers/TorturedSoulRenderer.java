
package com.obscuria.aquamirae.client.renderers;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.client.AquamiraeLayers;
import com.obscuria.aquamirae.client.renderers.layers.OverlayLayer;
import com.obscuria.aquamirae.common.entities.TorturedSoul;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import com.obscuria.aquamirae.client.models.ModelTorturedSoul;

public class TorturedSoulRenderer extends MobRenderer<TorturedSoul, ModelTorturedSoul> {

    public static final ResourceLocation TEXTURE = Aquamirae.identifier("textures/entity/tortured_soul.png");
    public static final ResourceLocation OVERLAY_TEXTURE = Aquamirae.identifier("textures/entity/tortured_soul_overlay.png");

	public TorturedSoulRenderer(EntityRendererProvider.Context context) {
		super(context, new ModelTorturedSoul(context.bakeLayer(AquamiraeLayers.TORTURED_SOUL)), 0.5f);
		this.addLayer(new OverlayLayer<>(this, OVERLAY_TEXTURE));
	}

	@Override
	public ResourceLocation getTextureLocation(TorturedSoul entity) {
		return TEXTURE;
	}

	@Override
	protected boolean isShaking(TorturedSoul entity) {
		return true;
	}
}
