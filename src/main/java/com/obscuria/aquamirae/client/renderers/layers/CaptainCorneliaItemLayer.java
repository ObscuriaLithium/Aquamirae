package com.obscuria.aquamirae.client.renderers.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.obscuria.aquamirae.client.models.ModelCaptainCornelia;
import com.obscuria.aquamirae.common.entities.CaptainCornelia;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemDisplayContext;

public class CaptainCorneliaItemLayer extends RenderLayer<CaptainCornelia, ModelCaptainCornelia> {

    public CaptainCorneliaItemLayer(RenderLayerParent<CaptainCornelia, ModelCaptainCornelia> layer) {
        super(layer);
    }

    public void render(
            PoseStack pose, MultiBufferSource source, int i1, CaptainCornelia entity,
            float f1, float f2, float f3, float f4, float f5, float f6) {

        var rightHandItem = entity.getItemBySlot(EquipmentSlot.MAINHAND);
        if (!rightHandItem.isEmpty()) {
            pose.pushPose();
            this.getParentModel().translateToHand(HumanoidArm.RIGHT, pose);
            pose.mulPose(Axis.XP.rotationDegrees(-90.0F));
            pose.mulPose(Axis.YP.rotationDegrees(180.0F));
            pose.translate(0.0D, 0.1D, 0.0D);
            Minecraft.getInstance().gameRenderer.itemInHandRenderer.renderItem(
                    entity, rightHandItem, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND,
                    false, pose, source, i1);
            pose.popPose();
        }

        var leftHandItem = entity.getItemBySlot(EquipmentSlot.OFFHAND);
        if (!leftHandItem.isEmpty()) {
            pose.pushPose();
            this.getParentModel().translateToHand(HumanoidArm.LEFT, pose);
            pose.mulPose(Axis.XP.rotationDegrees(45.0F));
            pose.translate(0.0D, -0.15D, -0.65D);
            Minecraft.getInstance().gameRenderer.itemInHandRenderer.renderItem(
                    entity, leftHandItem, ItemDisplayContext.THIRD_PERSON_LEFT_HAND,
                    false, pose, source, i1);
            pose.popPose();
        }
    }
}
