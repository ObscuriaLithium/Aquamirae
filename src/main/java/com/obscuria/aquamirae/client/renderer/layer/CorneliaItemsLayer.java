package com.obscuria.aquamirae.client.renderer.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.obscuria.aquamirae.client.model.ModelCaptainCornelia;
import com.obscuria.aquamirae.common.entity.CaptainCornelia;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CorneliaItemsLayer extends RenderLayer<CaptainCornelia, ModelCaptainCornelia> {
    private final ItemInHandRenderer itemRenderer;

    public CorneliaItemsLayer(RenderLayerParent<CaptainCornelia, ModelCaptainCornelia> layer,
                              ItemInHandRenderer itemRenderer) {
        super(layer);
        this.itemRenderer = itemRenderer;
    }

    @Override
    public void render(PoseStack pose, MultiBufferSource source, int light, CaptainCornelia entity,
                       float f1, float f2, float f3, float f4, float f5, float f6) {
        final var rightStack = entity.getItemBySlot(EquipmentSlot.MAINHAND);
        final var leftStack = entity.getItemBySlot(EquipmentSlot.OFFHAND);
        if (!rightStack.isEmpty()) {
            pose.pushPose();
            this.getParentModel().translateToHand(HumanoidArm.RIGHT, pose);
            pose.mulPose(Axis.XP.rotationDegrees(-90.0F));
            pose.mulPose(Axis.YP.rotationDegrees(180.0F));
            pose.translate(0.0D, 0.1D, 0.0D);
            this.itemRenderer.renderItem(entity, rightStack,
                    ItemDisplayContext.THIRD_PERSON_RIGHT_HAND,
                    false, pose, source, light);
            pose.popPose();
        }
        if (!leftStack.isEmpty()) {
            pose.pushPose();
            this.getParentModel().translateToHand(HumanoidArm.LEFT, pose);
            pose.mulPose(Axis.XP.rotationDegrees(45.0F));
            pose.translate(0.0D, -0.15D, -0.65D);
            this.itemRenderer.renderItem(entity, leftStack,
                    ItemDisplayContext.THIRD_PERSON_LEFT_HAND,
                    false, pose, source, light);
            pose.popPose();
        }
    }
}
