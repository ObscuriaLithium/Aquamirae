
package com.obscuria.aquamirae.client.renderers;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.client.AquamiraeLayers;
import com.obscuria.aquamirae.client.models.ModelPoisonedChakra;
import com.obscuria.aquamirae.common.entities.projectiles.PoisonedChakra;
import com.obscuria.obscureapi.client.renderer.DynamicProjectileRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class PoisonedChakraRenderer extends DynamicProjectileRenderer<PoisonedChakra> {

	public PoisonedChakraRenderer(EntityRendererFactory.Context context) {
		super(context, new ModelPoisonedChakra<>(context.getPart(AquamiraeLayers.POISONED_CHAKRA)));
	}

	@Override
	public Identifier getTexture(PoisonedChakra entity) {
		return Aquamirae.key("textures/entity/poisoned_chakra.png");
	}

	@Nullable
	@Override
	public Identifier getGlowingTextureLocation(PoisonedChakra poisonedChakra) {
		return null;
	}
}
