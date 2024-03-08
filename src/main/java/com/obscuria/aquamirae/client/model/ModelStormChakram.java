package com.obscuria.aquamirae.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.obscuria.aquamirae.common.entity.StormChakram;
import com.obscuria.core.api.animation.ModelTools;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;

public class ModelStormChakram extends EntityModel<StormChakram> implements ModelTools {
    private final ModelPart main, part1, part2, part3;

    public ModelStormChakram(ModelPart root) {
        this.main = root.getChild("main");
        this.part1 = main.getChild("part1");
        this.part2 = main.getChild("part2");
        this.part3 = main.getChild("part3");
    }

    public static LayerDefinition createBodyLayer() {
        final var meshDefinition = new MeshDefinition();
        final var partDefinition = meshDefinition.getRoot();
        final var main = partDefinition.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
        final var part1 = main.addOrReplaceChild("part1", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        final var offset1 = part1.addOrReplaceChild("offset1", CubeListBuilder.create().texOffs(0, 14).addBox(-3.5F, -1.0F, -13.0F, 7.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-5.5F, 0.0F, -10.0F, 11.0F, 0.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(34, 0).addBox(-3.5F, 0.0F, -21.0F, 7.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        offset1.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 8).addBox(-2.0F, 0.0F, -6.75F, 6.0F, 0.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(20, 14).addBox(-2.0F, -1.0F, -0.75F, 6.0F, 2.0F, 3.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(4.5F, 0.0F, -11.5F, 0.0F, -0.3927F, 0.0F));
        offset1.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(24, 8).addBox(-4.0F, 0.0F, -6.75F, 6.0F, 0.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(38, 14).addBox(-4.0F, -1.0F, -0.75F, 6.0F, 2.0F, 3.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-4.5F, 0.0F, -11.5F, 0.0F, 0.3927F, 0.0F));
        offset1.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 19).addBox(3.0F, 0.0F, -1.75F, 4.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.5F, 0.0F, -11.5F, 0.0F, -0.7854F, 0.0F));
        offset1.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(14, 19).addBox(-7.0F, 0.0F, -1.75F, 4.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.5F, 0.0F, -11.5F, 0.0F, 0.7854F, 0.0F));
        final var part2 = main.addOrReplaceChild("part2", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 2.0944F, 0.0F));
        final var offset2 = part2.addOrReplaceChild("offset2", CubeListBuilder.create().texOffs(0, 14).addBox(-3.5F, -1.0F, -13.0F, 7.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-5.5F, 0.0F, -10.0F, 11.0F, 0.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(34, 0).addBox(-3.5F, 0.0F, -21.0F, 7.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        offset2.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 8).addBox(-2.0F, 0.0F, -6.75F, 6.0F, 0.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(20, 14).addBox(-2.0F, -1.0F, -0.75F, 6.0F, 2.0F, 3.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(4.5F, 0.0F, -11.5F, 0.0F, -0.3927F, 0.0F));
        offset2.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(24, 8).addBox(-4.0F, 0.0F, -6.75F, 6.0F, 0.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(38, 14).addBox(-4.0F, -1.0F, -0.75F, 6.0F, 2.0F, 3.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-4.5F, 0.0F, -11.5F, 0.0F, 0.3927F, 0.0F));
        offset2.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(0, 19).addBox(3.0F, 0.0F, -1.75F, 4.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.5F, 0.0F, -11.5F, 0.0F, -0.7854F, 0.0F));
        offset2.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(14, 19).addBox(-7.0F, 0.0F, -1.75F, 4.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.5F, 0.0F, -11.5F, 0.0F, 0.7854F, 0.0F));
        final var part3 = main.addOrReplaceChild("part3", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -2.0944F, 0.0F));
        final var offset3 = part3.addOrReplaceChild("offset3", CubeListBuilder.create().texOffs(0, 14).addBox(-3.5F, -1.0F, -13.0F, 7.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-5.5F, 0.0F, -10.0F, 11.0F, 0.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(34, 0).addBox(-3.5F, 0.0F, -21.0F, 7.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        offset3.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(0, 8).addBox(-2.0F, 0.0F, -6.75F, 6.0F, 0.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(20, 14).addBox(-2.0F, -1.0F, -0.75F, 6.0F, 2.0F, 3.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(4.5F, 0.0F, -11.5F, 0.0F, -0.3927F, 0.0F));
        offset3.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(24, 8).addBox(-4.0F, 0.0F, -6.75F, 6.0F, 0.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(38, 14).addBox(-4.0F, -1.0F, -0.75F, 6.0F, 2.0F, 3.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-4.5F, 0.0F, -11.5F, 0.0F, 0.3927F, 0.0F));
        offset3.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(0, 19).addBox(3.0F, 0.0F, -1.75F, 4.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.5F, 0.0F, -11.5F, 0.0F, -0.7854F, 0.0F));
        offset3.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(14, 19).addBox(-7.0F, 0.0F, -1.75F, 4.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.5F, 0.0F, -11.5F, 0.0F, 0.7854F, 0.0F));
        return LayerDefinition.create(meshDefinition, 64, 64);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public void setupAnim(StormChakram entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        final var spinFactor = entity.getSpinFactor(Minecraft.getInstance().getPartialTick());
        final var scale = Math.min(0.3f + spinFactor * 5f, 1f);
        entity.spin += 0.005f + spinFactor;

        setScale(this.part1, scale);
        setScale(this.part2, scale);
        setScale(this.part3, scale);
        this.part1.yRot = entity.spin;
        this.part2.yRot = 2.09f + entity.spin;
        this.part3.yRot = -2.09f + entity.spin;
        this.main.zRot = headPitch;
    }
}
