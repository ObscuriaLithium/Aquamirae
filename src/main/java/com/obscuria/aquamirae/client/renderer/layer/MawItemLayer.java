package com.obscuria.aquamirae.client.renderer.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.obscuria.aquamirae.client.model.ModelMaw;
import com.obscuria.aquamirae.common.entity.Maw;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MawItemLayer extends RenderLayer<Maw, ModelMaw> {
    private final ItemInHandRenderer itemRenderer;

    public MawItemLayer(RenderLayerParent<Maw, ModelMaw> parent,
                        ItemInHandRenderer itemRenderer) {
        super(parent);
        this.itemRenderer = itemRenderer;
    }

    @Override
    public void render(PoseStack pose, MultiBufferSource source, int light, Maw maw,
                       float f1, float f2, float f3, float f4, float f5, float f6) {
        final var stack = maw.getMouthItem();
        if (stack.isEmpty()) return;
        pose.pushPose();
        this.getParentModel().translateToMouth(pose);
        pose.mulPose(Axis.XP.rotationDegrees(100.0F));
        pose.mulPose(Axis.ZP.rotationDegrees(0.0F));
        pose.translate(0.0D, -0.8D, 0.02D);
        pose.scale(0.7f, 0.7f, 0.7f);
        this.itemRenderer.renderItem(maw, maw.getMouthItem(),
                ItemDisplayContext.FIXED, false, pose, source, light);
        pose.popPose();
    }
}
