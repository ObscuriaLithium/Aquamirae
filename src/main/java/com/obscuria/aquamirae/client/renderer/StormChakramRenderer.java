package com.obscuria.aquamirae.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.client.AquamiraeLayers;
import com.obscuria.aquamirae.client.model.ModelStormChakram;
import com.obscuria.aquamirae.common.entity.projectile.StormChakram;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import com.obscuria.core.client.graphic.world.Trail3DRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class StormChakramRenderer extends EntityRenderer<StormChakram> {
    private final ModelStormChakram model;
    private final ItemRenderer itemRenderer;

    public StormChakramRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new ModelStormChakram(context.bakeLayer(AquamiraeLayers.RUNE_OF_THE_STORM));
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(StormChakram entity, float f1, float partialTicks, PoseStack pose,
                       MultiBufferSource bufferSource, int packedLight) {
        final var timer = entity.tickCount + partialTicks;
        final var yaw = Mth.wrapDegrees(Mth.lerp(partialTicks, entity.yRotO, entity.getYRot()) - 90);
        final var pitch = (float) Math.toRadians(Mth.lerp(partialTicks, entity.xRotO, entity.getXRot()));

        pose.pushPose();
        pose.mulPose(Axis.YP.rotationDegrees(yaw));
        pose.scale(-1, -1, 1);


        pose.pushPose();
        pose.scale(-1, -1, 1);
        pose.mulPose(Axis.YP.rotation(timer * 0.15f));
        this.itemRenderer.renderStatic(AquamiraeItems.RUNE_OF_THE_STORM.get().getDefaultInstance(),
                ItemDisplayContext.GROUND, packedLight, OverlayTexture.NO_OVERLAY,
                pose, bufferSource, null, entity.getId());
        pose.popPose();

        pose.translate(0, -1.6, 0);
        final var overlay = Math.min(entity.getSpinFactor(Minecraft.getInstance().getPartialTick()) * 5f, 1f);
        this.model.setupAnim(entity, 0, 0, timer, yaw, pitch);
        this.model.renderToBuffer(pose, bufferSource.getBuffer(RenderType.entityCutout(getTextureLocation(entity))),
                packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
        this.model.renderToBuffer(pose, bufferSource.getBuffer(RenderType.eyes(getOverlayTextureLocation())),
                packedLight, OverlayTexture.NO_OVERLAY, overlay, overlay, overlay, 0);
        pose.popPose();
        Trail3DRenderer.of(entity.trail).render(entity, pose, bufferSource, Minecraft.getInstance().getPartialTick());
        super.render(entity, f1, partialTicks, pose, bufferSource, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(StormChakram entity) {
        return Aquamirae.key("textures/entity/rune_of_the_storm.png");
    }

    public ResourceLocation getOverlayTextureLocation() {
        return Aquamirae.key("textures/entity/rune_of_the_storm_overlay.png");
    }
}
