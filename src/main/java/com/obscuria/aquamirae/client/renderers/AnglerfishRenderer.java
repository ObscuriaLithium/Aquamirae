
package com.obscuria.aquamirae.client.renderers;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.client.AquamiraeLayers;
import com.obscuria.aquamirae.client.models.ModelAnglerfish;
import com.obscuria.aquamirae.common.entities.AnglerfishEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.EyesFeatureRenderer;
import net.minecraft.util.Identifier;

public class AnglerfishRenderer extends MobEntityRenderer<AnglerfishEntity, ModelAnglerfish> {
	public AnglerfishRenderer(EntityRendererFactory.Context context) {
		super(context, new ModelAnglerfish(context.getPart(AquamiraeLayers.ANGLERFISH)), 1.5f);
		this.addFeature(new EyesFeatureRenderer<>(this) {
			@Override
			public RenderLayer getEyesTexture() {
				return RenderLayer.getEyes(Aquamirae.key("textures/entity/anglerfish_overlay.png"));
			}
		});
	}

	@Override
	public Identifier getTexture(AnglerfishEntity entity) {
		return Aquamirae.key("textures/entity/anglerfish.png");
	}
}
