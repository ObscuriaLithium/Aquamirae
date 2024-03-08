
package com.obscuria.aquamirae.client.renderer;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.client.AquamiraeLayers;
import com.obscuria.aquamirae.client.model.ModelSpinefish;
import com.obscuria.aquamirae.common.entity.Spinefish;
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
		return Aquamirae.key("textures/entity/spinefish.png");
	}
}
