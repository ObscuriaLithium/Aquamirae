
package com.obscuria.aquamirae.client.renderers;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.client.AquamiraeLayers;
import com.obscuria.aquamirae.client.models.ModelSpinefish;
import com.obscuria.aquamirae.common.entities.Spinefish;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SpinefishRenderer extends MobRenderer<Spinefish, ModelSpinefish<Spinefish>> {

    public static final ResourceLocation TEXTURE = Aquamirae.identifier("textures/entity/spinefish.png");

	public SpinefishRenderer(EntityRendererProvider.Context context) {
		super(context, new ModelSpinefish<>(context.bakeLayer(AquamiraeLayers.SPINEFISH)), 0.3f);
	}

	@Override
	public ResourceLocation getTextureLocation(Spinefish entity) {
		return TEXTURE;
	}
}
