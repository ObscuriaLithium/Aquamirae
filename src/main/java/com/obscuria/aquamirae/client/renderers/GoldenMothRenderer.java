
package com.obscuria.aquamirae.client.renderers;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.client.AquamiraeLayers;
import com.obscuria.aquamirae.client.models.ModelGoldenMoth;
import com.obscuria.aquamirae.common.entities.GoldenMothEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.EyesFeatureRenderer;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

public class GoldenMothRenderer extends MobEntityRenderer<GoldenMothEntity, ModelGoldenMoth<GoldenMothEntity>> {
	public GoldenMothRenderer(EntityRendererFactory.Context context) {
		super(context, new ModelGoldenMoth<>(context.getPart(AquamiraeLayers.GOLDEN_MOTH)), 0.2f);
		this.addFeature(new EyesFeatureRenderer<>(this) {
			@Override
			public RenderLayer getEyesTexture() {
				return RenderLayer.getEyes(Aquamirae.key("textures/entity/golden_moth_overlay.png"));
			}
		});
	}

	@Override
	public Identifier getTexture(@NotNull GoldenMothEntity entity) {
		return Aquamirae.key("textures/entity/golden_moth.png");
	}
}
