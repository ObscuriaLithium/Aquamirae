
package com.obscuria.aquamirae.client.renderers;

import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.client.models.ModelPoisonedChakra;
import com.obscuria.aquamirae.common.entities.projectiles.PoisonedChakra;
import com.obscuria.obscureapi.client.ScalableMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class PoisonedChakraRenderer extends ScalableMobRenderer<PoisonedChakra, ModelPoisonedChakra<PoisonedChakra>> {
	public PoisonedChakraRenderer(EntityRendererManager context) {
		super(context, new ModelPoisonedChakra<>(), 0.6f);
	}

	@Override
	public @Nonnull ResourceLocation getTextureLocation(@Nonnull PoisonedChakra entity) {
		return new ResourceLocation(AquamiraeMod.MODID,"textures/entity/poisoned_chakra.png");
	}
}
