
package com.obscuria.aquamirae.client.renderers;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.client.AquamiraeLayers;
import com.obscuria.aquamirae.client.models.ModelPoisonedChakram;
import com.obscuria.aquamirae.common.entities.projectiles.PoisonedChakra;
import com.obscuria.obscureapi.client.renderer.DynamicProjectileRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class PoisonedChakramRenderer extends DynamicProjectileRenderer<PoisonedChakra> {

    public static final ResourceLocation TEXTURE = Aquamirae.identifier("textures/entity/poisoned_chakra.png");

    public PoisonedChakramRenderer(EntityRendererProvider.Context context) {
        super(context, new ModelPoisonedChakram<>(context.bakeLayer(AquamiraeLayers.POISONED_CHAKRA)));
    }

    @Override
    public ResourceLocation getTextureLocation(PoisonedChakra poisonedChakra) {
        return TEXTURE;
    }

    @Override
    public @Nullable ResourceLocation getGlowingTextureLocation(PoisonedChakra poisonedChakra) {
        return null;
    }
}