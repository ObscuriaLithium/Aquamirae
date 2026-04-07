
package com.obscuria.aquamirae.client.renderers;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.client.AquamiraeLayers;
import com.obscuria.aquamirae.client.models.ModelCaptainCornelia;
import com.obscuria.aquamirae.client.renderers.layers.CaptainCorneliaItemLayer;
import com.obscuria.aquamirae.client.renderers.layers.OverlayLayer;
import com.obscuria.aquamirae.common.entities.CaptainCornelia;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class CaptainCorneliaRenderer extends MobRenderer<CaptainCornelia, ModelCaptainCornelia> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(Aquamirae.MODID, "textures/entity/captain_cornelia.png");
    public static final ResourceLocation OVERLAY_TEXTURE = new ResourceLocation(Aquamirae.MODID, "textures/entity/captain_cornelia_overlay.png");

    public CaptainCorneliaRenderer(EntityRendererProvider.Context context) {
        super(context, new ModelCaptainCornelia(context.bakeLayer(AquamiraeLayers.CAPTAIN_CORNELIA)), 0.5f);
        this.addLayer(new OverlayLayer<>(this, OVERLAY_TEXTURE));
        this.addLayer(new CaptainCorneliaItemLayer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(CaptainCornelia entity) {
        return TEXTURE;
    }
}
