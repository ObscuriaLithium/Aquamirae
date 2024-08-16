
package com.obscuria.aquamirae.client.renderer;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.client.AquamiraeLayers;
import com.obscuria.aquamirae.client.model.ModelAnglerfish;
import com.obscuria.aquamirae.client.renderer.layer.AnglerfishEscaLayer;
import com.obscuria.aquamirae.common.entity.Anglerfish;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AnglerfishRenderer extends MobRenderer<Anglerfish, ModelAnglerfish> {
	public AnglerfishRenderer(EntityRendererProvider.Context context) {
		super(context, new ModelAnglerfish(context.bakeLayer(AquamiraeLayers.ANGLERFISH)), 1.5f);
		this.addLayer(new AnglerfishEscaLayer(this));
	}

	@Override
	public ResourceLocation getTextureLocation(Anglerfish entity) {
		return Aquamirae.key("textures/entity/anglerfish.png");
	}
}
