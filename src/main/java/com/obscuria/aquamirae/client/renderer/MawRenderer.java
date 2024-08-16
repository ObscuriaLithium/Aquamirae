
package com.obscuria.aquamirae.client.renderer;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.client.AquamiraeLayers;
import com.obscuria.aquamirae.client.model.ModelMaw;
import com.obscuria.aquamirae.client.renderer.layer.MawEyesLayer;
import com.obscuria.aquamirae.client.renderer.layer.MawItemLayer;
import com.obscuria.aquamirae.common.entity.Maw;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MawRenderer extends MobRenderer<Maw, ModelMaw> {

	public MawRenderer(EntityRendererProvider.Context context) {
		super(context, new ModelMaw(context.bakeLayer(AquamiraeLayers.MAW)), 0.9f);
		this.addLayer(new MawItemLayer(this, context.getItemInHandRenderer()));
		this.addLayer(new MawEyesLayer(this));
	}

	@Override
	public ResourceLocation getTextureLocation(Maw entity) {
		return Aquamirae.key("textures/entity/maw.png");
	}
}
