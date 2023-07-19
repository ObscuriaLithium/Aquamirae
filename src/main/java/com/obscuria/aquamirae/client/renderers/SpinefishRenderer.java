
package com.obscuria.aquamirae.client.renderers;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.client.AquamiraeLayers;
import com.obscuria.aquamirae.client.models.ModelSpinefish;
import com.obscuria.aquamirae.common.entities.SpinefishEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class SpinefishRenderer extends MobEntityRenderer<SpinefishEntity, ModelSpinefish<SpinefishEntity>> {
	public SpinefishRenderer(EntityRendererFactory.Context context) {
		super(context, new ModelSpinefish<>(context.getPart(AquamiraeLayers.SPINEFISH)), 0.3f);
	}

	@Override
	public Identifier getTexture(SpinefishEntity entity) {
		return Aquamirae.key("textures/entity/spinefish.png");
	}
}
