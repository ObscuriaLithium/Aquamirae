
package com.obscuria.aquamirae.client.renderers;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.client.AquamiraeLayers;
import com.obscuria.aquamirae.client.models.ModelEel;
import com.obscuria.aquamirae.common.entities.EelEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.EyesFeatureRenderer;
import net.minecraft.util.Identifier;

public class EelRenderer extends MobEntityRenderer<EelEntity, ModelEel> {
	public EelRenderer(EntityRendererFactory.Context context) {
		super(context, new ModelEel(context.getPart(AquamiraeLayers.EEL)), 0f);
		this.addFeature(new EyesFeatureRenderer<>(this) {
			@Override
			public RenderLayer getEyesTexture() {
				return RenderLayer.getEyes(Aquamirae.key("textures/entity/eel_overlay.png"));
			}
		});
	}

	@Override
	public Identifier getTexture(EelEntity entity) {
		return Aquamirae.key("textures/entity/eel.png");
	}
}
