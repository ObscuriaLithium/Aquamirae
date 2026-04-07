
package com.obscuria.aquamirae.client.renderers;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.client.AquamiraeLayers;
import com.obscuria.aquamirae.client.renderers.layers.OverlayLayer;
import com.obscuria.aquamirae.common.entities.Anglerfish;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

import com.obscuria.aquamirae.client.models.ModelAnglerfish;

public class AnglerfishRenderer extends MobRenderer<Anglerfish, ModelAnglerfish> {

    private static final ResourceLocation TEXTURE = Aquamirae.identifier("textures/entity/anglerfish.png");
    private static final ResourceLocation OVERLAY_TEXTURE = Aquamirae.identifier("textures/entity/anglerfish_overlay.png");

    public AnglerfishRenderer(EntityRendererProvider.Context context) {
        super(context, new ModelAnglerfish(context.bakeLayer(AquamiraeLayers.ANGLERFISH)), 1.5f);
        this.addLayer(new OverlayLayer<>(this, OVERLAY_TEXTURE));
    }

    @Override
    public ResourceLocation getTextureLocation(Anglerfish entity) {
        return TEXTURE;
    }
}
