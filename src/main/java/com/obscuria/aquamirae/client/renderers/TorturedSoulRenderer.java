
package com.obscuria.aquamirae.client.renderers;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.client.AquamiraeLayers;
import com.obscuria.aquamirae.client.models.ModelTorturedSoul;
import com.obscuria.aquamirae.common.entities.TorturedSoulEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.EyesFeatureRenderer;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

public class TorturedSoulRenderer extends MobEntityRenderer<TorturedSoulEntity, ModelTorturedSoul> {
	public TorturedSoulRenderer(EntityRendererFactory.Context context) {
		super(context, new ModelTorturedSoul(context.getPart(AquamiraeLayers.TORTURED_SOUL)), 0.5f);
		this.addFeature(new EyesFeatureRenderer<>(this) {
			@Override
			public RenderLayer getEyesTexture() {
				return RenderLayer.getEyes(Aquamirae.key("textures/entity/tortured_soul_overlay.png"));
			}
		});
	}

	@Override
	public Identifier getTexture(TorturedSoulEntity entity) {
		return Aquamirae.key("textures/entity/tortured_soul.png");
	}

	@Override
	protected boolean isShaking(@NotNull TorturedSoulEntity entity) {
		return true;
	}
}
