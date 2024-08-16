
package com.obscuria.aquamirae.client.renderer;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.client.AquamiraeLayers;
import com.obscuria.aquamirae.client.model.ModelTorturedSoul;
import com.obscuria.aquamirae.client.renderer.layer.TorturedSoulEyesLayer;
import com.obscuria.aquamirae.common.entity.TorturedSoul;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TorturedSoulRenderer extends MobRenderer<TorturedSoul, ModelTorturedSoul> {
	public TorturedSoulRenderer(EntityRendererProvider.Context context) {
		super(context, new ModelTorturedSoul(context.bakeLayer(AquamiraeLayers.TORTURED_SOUL)), 0.5f);
		this.addLayer(new TorturedSoulEyesLayer(this));
	}

	@Override
	public ResourceLocation getTextureLocation(TorturedSoul entity) {
		return Aquamirae.key("textures/entity/tortured_soul.png");
	}

	@Override
	protected boolean isShaking(TorturedSoul entity) {
		return true;
	}
}
