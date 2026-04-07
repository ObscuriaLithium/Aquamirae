
package com.obscuria.aquamirae.client.renderers;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.client.AquamiraeLayers;
import com.obscuria.aquamirae.client.models.ModelMaw;
import com.obscuria.aquamirae.client.renderers.layers.MawItemLayer;
import com.obscuria.aquamirae.client.renderers.layers.OverlayLayer;
import com.obscuria.aquamirae.common.entities.Maw;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class MawRenderer extends MobRenderer<Maw, ModelMaw> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(Aquamirae.MODID, "textures/entity/maw.png");
    public static final ResourceLocation OVERLAY_TEXTURE = new ResourceLocation(Aquamirae.MODID, "textures/entity/maw_overlay.png");

    public MawRenderer(EntityRendererProvider.Context context) {
        super(context, new ModelMaw(context.bakeLayer(AquamiraeLayers.MAW)), 0.9f);
        this.addLayer(new OverlayLayer<>(this, OVERLAY_TEXTURE));
        this.addLayer(new MawItemLayer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(Maw entity) {
        return TEXTURE;
    }
}
