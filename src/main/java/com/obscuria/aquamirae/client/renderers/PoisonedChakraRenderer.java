
package com.obscuria.aquamirae.client.renderers;

import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.obscureapi.client.ScalableMobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import com.obscuria.aquamirae.world.entities.chakras.PoisonedChakra;
import com.obscuria.aquamirae.client.models.ModelPoisonedChakra;
import org.jetbrains.annotations.NotNull;

public class PoisonedChakraRenderer extends ScalableMobRenderer<PoisonedChakra, ModelPoisonedChakra<PoisonedChakra>> {
	public PoisonedChakraRenderer(EntityRendererProvider.Context context) {
		super(context, new ModelPoisonedChakra<>(context.bakeLayer(ModelPoisonedChakra.LAYER_LOCATION)), 0.6f);
	}

	@Override
	public @NotNull ResourceLocation getTextureLocation(@NotNull PoisonedChakra entity) {
		return new ResourceLocation(AquamiraeMod.MODID,"textures/entity/poisoned_chakra.png");
	}
}
