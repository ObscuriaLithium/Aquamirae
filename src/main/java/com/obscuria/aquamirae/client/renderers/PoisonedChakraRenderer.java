
package com.obscuria.aquamirae.client.renderers;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.client.AquamiraeLayers;
import com.obscuria.aquamirae.client.models.ModelPoisonedChakra;
import com.obscuria.aquamirae.common.entities.projectiles.PoisonedChakra;
import com.obscuria.obscureapi.client.renderer.DynamicProjectileRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class PoisonedChakraRenderer extends DynamicProjectileRenderer<PoisonedChakra> {

	public PoisonedChakraRenderer(EntityRendererProvider.Context context) {
		super(context, new ModelPoisonedChakra<>(context.bakeLayer(AquamiraeLayers.POISONED_CHAKRA)));
	}

	@Override
	public @NotNull ResourceLocation getTextureLocation(@NotNull PoisonedChakra poisonedChakra) {
		return new ResourceLocation(Aquamirae.MODID,"textures/entity/poisoned_chakra.png");
	}

	@Override
	public ResourceLocation getGlowingTextureLocation(PoisonedChakra poisonedChakra) {
		return null;
	}
}
