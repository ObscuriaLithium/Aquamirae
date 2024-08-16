package com.obscuria.aquamirae.client.renderer.projectile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.obscuria.aquamirae.common.entity.projectile.AbstractShard;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;

public abstract class ShardRenderer<T extends AbstractShard> extends EntityRenderer<T> {
    private final EntityModel<T> model;

    public ShardRenderer(EntityRendererProvider.Context context, EntityModel<T> model) {
        super(context);
        this.model = model;
    }

    @Override
    public void render(T shard, float f1, float f2, PoseStack pose, MultiBufferSource source, int light) {
        super.render(shard, f1, f2, pose, source, light);
        final var partialTicks = Minecraft.getInstance().getPartialTick();
        pose.pushPose();
        pose.scale(-1, -1, 1);
        final var scale = shard.getShardScale();
        pose.scale(scale, scale, scale);
        pose.translate(0, -1.5, 0);
        this.model.setupAnim(shard, 0, 0,
                shard.tickCount + partialTicks,
                Mth.lerp(partialTicks, shard.localXRotO, shard.localXRot),
                Mth.lerp(partialTicks, shard.localYRotO, shard.localYRot));
        this.model.renderToBuffer(pose,
                source.getBuffer(RenderType.eyes(this.getTextureLocation(shard))),
                light, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
        pose.popPose();
    }
}
