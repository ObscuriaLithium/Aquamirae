
package com.obscuria.aquamirae.client.renderer;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.client.AquamiraeLayers;
import com.obscuria.aquamirae.client.model.ModelSpinefish;
import com.obscuria.aquamirae.common.entity.Spinefish;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SpinefishRenderer extends MobRenderer<Spinefish, ModelSpinefish<Spinefish>> {
	public SpinefishRenderer(EntityRendererProvider.Context context) {
		super(context, new ModelSpinefish<>(context.bakeLayer(AquamiraeLayers.SPINEFISH)), 0.3f);
	}

	@Override
	public ResourceLocation getTextureLocation(Spinefish entity) {
		return Aquamirae.key("textures/entity/spinefish.png");
	}
}
