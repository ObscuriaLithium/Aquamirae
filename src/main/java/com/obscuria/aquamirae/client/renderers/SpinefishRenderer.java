
package com.obscuria.aquamirae.client.renderers;

import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.client.models.ModelSpinefish;
import com.obscuria.aquamirae.world.entities.Spinefish;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class SpinefishRenderer extends MobRenderer<Spinefish, ModelSpinefish<Spinefish>> {
	public SpinefishRenderer(EntityRendererManager context) {
		super(context, new ModelSpinefish<>(), 0.3f);
	}

	@Override
	public @Nonnull ResourceLocation getTextureLocation(@Nonnull Spinefish entity) {
		return new ResourceLocation(AquamiraeMod.MODID,"textures/entity/spinefish.png");
	}
}
