package com.obscuria.aquamirae.client.renderers.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.obscuria.aquamirae.client.models.ModelMaw;
import com.obscuria.aquamirae.common.entities.Maw;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.item.ItemDisplayContext;

public class MawItemLayer extends RenderLayer<Maw, ModelMaw> {

    public MawItemLayer(RenderLayerParent<Maw, ModelMaw> layer) {
        super(layer);
    }

    public void render(
            PoseStack pose, MultiBufferSource source, int i1, Maw maw,
            float f1, float f2, float f3, float f4, float f5, float f6) {

        if (maw.getItemInMouth().isEmpty()) return;
        pose.pushPose();
        this.getParentModel().translate(pose);
        pose.mulPose(Axis.XP.rotationDegrees(100.0F));
        pose.mulPose(Axis.ZP.rotationDegrees(0.0F));
        pose.translate(0.0D, -0.8D, 0.02D);
        pose.scale(0.7f, 0.7f, 0.7f);
        Minecraft.getInstance().gameRenderer.itemInHandRenderer.renderItem(
                maw, maw.getItemInMouth(), ItemDisplayContext.FIXED,
                false, pose, source, i1);
        pose.popPose();
    }
}
