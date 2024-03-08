
package com.obscuria.aquamirae.client.renderer;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.common.entity.projectile.PoisonedChakra;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class PoisonedChakraRenderer extends EntityRenderer<PoisonedChakra> {

	public PoisonedChakraRenderer(EntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public @NotNull ResourceLocation getTextureLocation(@NotNull PoisonedChakra poisonedChakra) {
		return Aquamirae.key("textures/entity/poisoned_chakra.png");
	}
}
