
package com.obscuria.aquamirae.client.renderer;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.client.AquamiraeLayers;
import com.obscuria.aquamirae.client.model.ModelEel;
import com.obscuria.aquamirae.client.renderer.layer.EelEyesLayer;
import com.obscuria.aquamirae.common.entity.Eel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EelRenderer extends MobRenderer<Eel, ModelEel> {
	public EelRenderer(EntityRendererProvider.Context context) {
		super(context, new ModelEel(context.bakeLayer(AquamiraeLayers.EEL)), 0f);
		this.addLayer(new EelEyesLayer(this));
	}

	@Override
	public ResourceLocation getTextureLocation(Eel entity) {
		return Aquamirae.key("textures/entity/eel.png");
	}
}
