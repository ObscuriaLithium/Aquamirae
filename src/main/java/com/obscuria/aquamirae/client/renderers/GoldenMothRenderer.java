
package com.obscuria.aquamirae.client.renderers;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.client.AquamiraeLayers;
import com.obscuria.aquamirae.client.models.ModelGoldenMoth;
import com.obscuria.aquamirae.client.renderers.layers.OverlayLayer;
import com.obscuria.aquamirae.common.entities.GoldenMoth;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class GoldenMothRenderer extends MobRenderer<GoldenMoth, ModelGoldenMoth<GoldenMoth>> {

    public static final ResourceLocation TEXTURE = Aquamirae.identifier("textures/entity/golden_moth.png");
    public static final ResourceLocation OVERLAY_TEXTURE = Aquamirae.identifier("textures/entity/golden_moth_overlay.png");

    public GoldenMothRenderer(EntityRendererProvider.Context context) {
        super(context, new ModelGoldenMoth<>(context.bakeLayer(AquamiraeLayers.GOLDEN_MOTH)), 0.2f);
        this.addLayer(new OverlayLayer<>(this, OVERLAY_TEXTURE));
    }

    @Override
    public ResourceLocation getTextureLocation(GoldenMoth entity) {
        return TEXTURE;
    }
}
