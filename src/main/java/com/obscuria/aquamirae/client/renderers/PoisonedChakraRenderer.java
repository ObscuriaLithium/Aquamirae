
package com.obscuria.aquamirae.client.renderers;

import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.client.models.ModelPoisonedChakra;
import com.obscuria.aquamirae.world.entities.chakras.PoisonedChakra;
import com.obscuria.obscureapi.client.renderer.DynamicProjectileRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class PoisonedChakraRenderer extends DynamicProjectileRenderer<PoisonedChakra> {

	public PoisonedChakraRenderer(EntityRendererProvider.Context context) {
		super(context, new ModelPoisonedChakra<>(context.bakeLayer(ModelPoisonedChakra.LAYER_LOCATION)));
	}

	@Override
	public @NotNull ResourceLocation getTextureLocation(@NotNull PoisonedChakra poisonedChakra) {
		return new ResourceLocation(AquamiraeMod.MODID,"textures/entity/poisoned_chakra.png");
	}

	@Override
	public ResourceLocation getGlowingTextureLocation(PoisonedChakra poisonedChakra) {
		return null;
	}
}
