
package com.obscuria.aquamirae.client.renderers;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.client.AquamiraeLayers;
import com.obscuria.aquamirae.client.models.ModelSpinefish;
import com.obscuria.aquamirae.common.entities.Spinefish;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class SpinefishRenderer extends MobRenderer<Spinefish, ModelSpinefish<Spinefish>> {
	public SpinefishRenderer(EntityRendererProvider.Context context) {
		super(context, new ModelSpinefish<>(context.bakeLayer(AquamiraeLayers.SPINEFISH)), 0.3f);
	}

	@Override
	public @NotNull ResourceLocation getTextureLocation(@NotNull Spinefish entity) {
		return new ResourceLocation(Aquamirae.MODID,"textures/entity/spinefish.png");
	}
}
