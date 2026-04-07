
package com.obscuria.aquamirae.client.renderers;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.client.AquamiraeLayers;
import com.obscuria.aquamirae.client.models.ModelEel;
import com.obscuria.aquamirae.client.renderers.layers.OverlayLayer;
import com.obscuria.aquamirae.common.entities.Eel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class EelRenderer extends MobRenderer<Eel, ModelEel> {

    public static final ResourceLocation TEXTURE = Aquamirae.identifier("textures/entity/eel.png");
    public static final ResourceLocation OVERLAY_TEXTURE = Aquamirae.identifier("textures/entity/eel_overlay.png");

    public EelRenderer(EntityRendererProvider.Context context) {
        super(context, new ModelEel(context.bakeLayer(AquamiraeLayers.EEL)), 0f);
        this.addLayer(new OverlayLayer<>(this, OVERLAY_TEXTURE));
    }

    @Override
    public ResourceLocation getTextureLocation(Eel entity) {
        return TEXTURE;
    }
}
