
package com.obscuria.aquamirae.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.client.AquamiraeLayers;
import com.obscuria.aquamirae.client.models.ModelAbyssalScyphoid;
import com.obscuria.aquamirae.client.renderers.layers.OverlayLayer;
import com.obscuria.aquamirae.common.entities.AbyssalScyphoid;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class AbyssalScyphoidRenderer extends MobRenderer<AbyssalScyphoid, ModelAbyssalScyphoid<AbyssalScyphoid>> {

    public static final ResourceLocation TEXTURE = Aquamirae.identifier("textures/entity/abyssal_scyphoid.png");
    public static final ResourceLocation OVERLAY_TEXTURE = Aquamirae.identifier("textures/entity/abyssal_scyphoid_overlay.png");

    public AbyssalScyphoidRenderer(EntityRendererProvider.Context context) {
        super(context, new ModelAbyssalScyphoid<>(context.bakeLayer(AquamiraeLayers.ABYSSAL_SCYPHOID)), 1.5f);
        this.addLayer(new OverlayLayer<>(this, TEXTURE));
        this.addLayer(new OverlayLayer<>(this, OVERLAY_TEXTURE));
    }

    @Override
    public void render(
            AbyssalScyphoid scyphoid, float f1, float f2, PoseStack pose,
            MultiBufferSource bufferSource, int light) {

        var scale = scyphoid.getVariant();
        pose.pushPose();
        pose.translate(0, 0.5, 0);
        pose.scale(scale, scale, scale);
        pose.translate(0, -0.5, 0);
        super.render(scyphoid, f1, f2, pose, bufferSource, light);
        pose.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(AbyssalScyphoid entity) {
        return TEXTURE;
    }
}
