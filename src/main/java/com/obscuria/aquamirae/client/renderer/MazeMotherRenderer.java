
package com.obscuria.aquamirae.client.renderer;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.client.AquamiraeLayers;
import com.obscuria.aquamirae.client.model.ModelMazeMother;
import com.obscuria.aquamirae.client.renderer.layer.MazeMotherCrystalsLayer;
import com.obscuria.aquamirae.common.entity.MazeMother;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class MazeMotherRenderer extends MobRenderer<MazeMother, ModelMazeMother> {

	public MazeMotherRenderer(EntityRendererProvider.Context context) {
		super(context, new ModelMazeMother(context.bakeLayer(AquamiraeLayers.MAZE_MOTHER)), 1f);
		this.addLayer(new MazeMotherCrystalsLayer(this));
	}

	@Override
	public ResourceLocation getTextureLocation(@NotNull MazeMother entity) {
		return Aquamirae.key("textures/entity/maze_mother.png");
	}
}
