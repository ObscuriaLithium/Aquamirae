
package com.obscuria.aquamirae.client.renderers;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.client.AquamiraeLayers;
import com.obscuria.aquamirae.client.models.ModelMazeMother;
import com.obscuria.aquamirae.common.entities.MazeMotherEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.EyesFeatureRenderer;
import net.minecraft.util.Identifier;

public class MazeMotherRenderer extends MobEntityRenderer<MazeMotherEntity, ModelMazeMother> {
	public MazeMotherRenderer(EntityRendererFactory.Context context) {
		super(context, new ModelMazeMother(context.getPart(AquamiraeLayers.MAZE_MOTHER)), 1f);
		this.addFeature(new EyesFeatureRenderer<>(this) {
			@Override
			public RenderLayer getEyesTexture() {
				return RenderLayer.getEyes(Aquamirae.key("textures/entity/maze_mother_overlay.png"));
			}
		});
	}

	@Override
	public Identifier getTexture(MazeMotherEntity entity) {
		return Aquamirae.key("textures/entity/maze_mother.png");
	}
}
