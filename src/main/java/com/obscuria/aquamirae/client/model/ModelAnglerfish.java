package com.obscuria.aquamirae.client.model;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.obscuria.aquamirae.client.animation.AnglerfishAnimations;
import com.obscuria.aquamirae.common.entity.Anglerfish;
import com.obscuria.core.client.animation.tool.IAnimatableModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

import java.util.Map;

public class ModelAnglerfish extends EntityModel<Anglerfish> implements IAnimatableModel {
	private final Map<String, ModelPart> partsMap = Maps.newHashMap();
	public final ModelPart root, main, head, headUpper, headLower, body,
			tail1, tail2, tail3, tail4, tail5, tail6,
			leftFin, rightFin, lamp1, lamp2, lamp3, lamp4, lamp5;

	public ModelAnglerfish(ModelPart root) {
		this.root = this.definePart(root, "root");
		this.main = this.definePartChild(root, "main");
		this.head = this.definePartChild(main, "head");
		this.body = this.definePartChild(main, "body");
		this.headUpper = this.definePartChild(head, "head_upper");
		this.headLower = this.definePartChild(head, "head_lower");
		this.leftFin = this.definePartChild(body, "left_fin");
		this.rightFin = this.definePartChild(body, "right_fin");
		this.tail1 = this.definePartChild(body, "tail1");
		this.tail2 = this.definePartChild(tail1, "tail2");
		this.tail3 = this.definePartChild(tail2, "tail3");
		this.tail4 = this.definePartChild(tail3, "tail4");
		this.tail5 = this.definePartChild(tail4, "tail5");
		this.tail6 = this.definePartChild(tail5, "tail6");
		this.lamp1 = this.definePartChild(headUpper, "lamp1");
		this.lamp2 = this.definePartChild(lamp1, "lamp2");
		this.lamp3 = this.definePartChild(lamp2, "lamp3");
		this.lamp4 = this.definePartChild(lamp3, "lamp4");
		this.lamp5 = this.definePartChild(lamp4, "lamp5");
	}

	public static LayerDefinition createBodyLayer() {
		final var meshDefinition = new MeshDefinition();
		final var root = meshDefinition.getRoot();
		final var main = root.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 5.0F, 0.0F, 0.2618F, 0.0F, 0.0F));
		final var head = main.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, -5.0F, 0.6109F, 0.0F, 0.0F));
		final var headTop = head.addOrReplaceChild("head_upper", CubeListBuilder.create().texOffs(50, 45).addBox(-7.0F, -16.0F, 0.0F, 14.0F, 16.0F, 10.0F, new CubeDeformation(0.0F)).texOffs(59, 10).addBox(1.0F, -17.0F, 2.0F, 5.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(40, 0).addBox(-6.0F, -17.0F, 2.0F, 5.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(0, 55).addBox(-7.0F, -16.0F, -8.0F, 14.0F, 16.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, -5.0F));
		final var lamp1 = headTop.addOrReplaceChild("lamp1", CubeListBuilder.create().texOffs(107, 0).addBox(-0.5F, -5.0F, 0.0F, 1.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -16.0F, 9.0F, -0.2182F, 0.0F, 0.0F));
		final var lamp2 = lamp1.addOrReplaceChild("lamp2", CubeListBuilder.create().texOffs(107, 5).addBox(-0.5F, -5.0F, 0.0F, 1.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.0F, 0.0F, 0.1745F, 0.0F, 0.0F));
		final var lamp3 = lamp2.addOrReplaceChild("lamp3", CubeListBuilder.create().texOffs(107, 10).addBox(-0.5F, -5.0F, 0.0F, 1.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.0F, 0.0F, 0.5672F, 0.0F, 0.0F));
		final var lamp4 = lamp3.addOrReplaceChild("lamp4", CubeListBuilder.create().texOffs(107, 15).addBox(-0.5F, -5.0F, 0.0F, 1.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.0F, 0.0F, 0.9163F, 0.0F, 0.0F));
		lamp4.addOrReplaceChild("lamp5", CubeListBuilder.create().texOffs(104, 20).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.0F, 0.0F, 0.5672F, 0.0F, 0.0F));
		head.addOrReplaceChild("head_lower", CubeListBuilder.create().texOffs(44, 16).addBox(-7.0F, -2.0F, -14.0F, 14.0F, 3.0F, 16.0F, new CubeDeformation(0.11F)).texOffs(0, 32).addBox(-7.0F, -9.0F, -14.0F, 14.0F, 7.0F, 16.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, 2.0F, -5.0F));
		final var body = main.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -8.0F, -11.0F, 12.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)).texOffs(44, 55).addBox(0.0F, -18.0F, -11.0F, 0.0F, 10.0F, 16.0F, new CubeDeformation(0.0F)).texOffs(44, 19).addBox(0.0F, 8.0F, -11.0F, 0.0F, 10.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		final var tail1 = body.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(68, 73).addBox(-4.0F, -7.0F, 0.0F, 8.0F, 14.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(76, 27).addBox(0.0F, -17.0F, 0.0F, 0.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(0, 24).addBox(0.0F, 7.0F, 0.0F, 0.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 5.0F));
		final var tail2 = tail1.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(12, 79).addBox(-2.0F, -6.0F, 0.0F, 4.0F, 12.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(52, 0).addBox(0.0F, -15.0F, 0.0F, 0.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(40, 0).addBox(0.0F, 6.0F, 0.0F, 0.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 8.0F));
		final var tail3 = tail2.addOrReplaceChild("tail3", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -3.0F, 0.0F, 2.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(0, 73).addBox(0.0F, -12.0F, 0.0F, 0.0F, 24.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 6.0F));
		final var tail4 = tail3.addOrReplaceChild("tail4", CubeListBuilder.create().texOffs(56, 75).addBox(0.0F, -8.0F, 0.0F, 0.0F, 16.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 6.0F));
		final var tail5 = tail4.addOrReplaceChild("tail5", CubeListBuilder.create().texOffs(44, 75).addBox(0.0F, -8.0F, 0.0F, 0.0F, 16.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 6.0F));
		tail5.addOrReplaceChild("tail6", CubeListBuilder.create().texOffs(32, 73).addBox(0.0F, -8.0F, 0.0F, 0.0F, 16.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 6.0F));
		body.addOrReplaceChild("left_fin", CubeListBuilder.create().texOffs(0, 103).addBox(0.0F, -2.0F, -0.5F, 1.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(0, 103).addBox(0.5F, -6.0F, -0.5F, 0.0F, 12.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0F, 0.0F, -4.5F, 0.0F, 0.5672F, 0.0F));
		body.addOrReplaceChild("right_fin", CubeListBuilder.create().texOffs(0, 103).addBox(-1.0F, -2.0F, -0.5F, 1.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(0, 103).addBox(-0.5F, -6.0F, -0.5F, 0.0F, 12.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0F, 0.0F, -4.5F, 0.0F, -0.6981F, 0.0F));
		return LayerDefinition.create(meshDefinition, 128, 128);
	}

	@Override
	public ModelPart getRoot() {
		return this.root;
	}

	@Override
	public Map<String, ModelPart> getPartsMap() {
		return this.partsMap;
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		poseStack.pushPose();
		poseStack.scale(1.5F, 1.5F, 1.5F);
		main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		poseStack.popPose();
	}

	public void setupAnim(Anglerfish anglerfish, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		AnglerfishAnimations.IDLE.animate(this, anglerfish, limbSwing, limbSwingAmount);
		AnglerfishAnimations.WALK.animate(this, anglerfish, limbSwing, limbSwingAmount);
		AnglerfishAnimations.BITE.animate(this, anglerfish, limbSwing, limbSwingAmount);
		final var groundMod = Mth.lerp(Minecraft.getInstance().getPartialTick(), anglerfish.onGroundAnimO, anglerfish.onGroundAnim);
		final var headMOd = 1f - groundMod;
		this.main.y = 10 * groundMod;
		this.main.zRot += (float) Math.toRadians(90f * groundMod);
		this.main.xRot += (float) Math.toRadians(headPitch - 20) * headMOd;
		this.main.yRot += (float) Math.toRadians(netHeadYaw) * headMOd;
	}
}
