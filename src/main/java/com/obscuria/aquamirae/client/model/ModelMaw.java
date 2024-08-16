package com.obscuria.aquamirae.client.model;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.obscuria.aquamirae.client.animation.MawAnimations;
import com.obscuria.aquamirae.common.entity.Maw;
import com.obscuria.core.client.animation.tool.IAnimatableModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;

import java.util.Map;

public class ModelMaw extends EntityModel<Maw> implements IAnimatableModel {
	private final Map<String, ModelPart> partsMap = Maps.newHashMap();
	public final ModelPart root, main, head, headUpper, headLower, body, body2, body3, rightFin, leftFin;

	public ModelMaw(ModelPart root) {
		this.root = this.definePart(root, "root");
		this.main = this.definePartChild(root, "main");
		this.head = this.definePartChild(main, "head");
		this.headUpper = this.definePartChild(head, "head_upper");
		this.headLower = this.definePartChild(head, "head_lower");
		this.body = this.definePartChild(main, "body");
		this.body2 = this.definePartChild(body, "body2");
		this.body3 = this.definePartChild(body2, "body3");
		this.rightFin = this.definePartChild(body, "right_fin");
		this.leftFin = this.definePartChild(body, "left_fin");
	}

	public static LayerDefinition createBodyLayer() {
		final var meshDefinition = new MeshDefinition();
		final var root = meshDefinition.getRoot();
		final var main = root.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0F, 15.0F, 150.0F));
		final var head = main.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -147.0F));
		final var head_upper = head.addOrReplaceChild("head_upper", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		head_upper.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(64, 66).addBox(-10.0F, 0.0F, -24.0F, 20.0F, 6.0F, 24.0F, new CubeDeformation(0.0F)).texOffs(0, 60).addBox(-10.0F, -6.0F, -24.0F, 20.0F, 6.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.4363F, 0.0F, 0.0F));
		head_upper.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(66, 19).addBox(0.01F, -14.4226F, -20.0937F, 0.0F, 8.0F, 23.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, -3.75F, -0.4363F, 0.0F, 0.0F));
		final var head_lower = head.addOrReplaceChild("head_lower", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		head_lower.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 0).addBox(-10.5F, -6.0F, -24.0F, 21.0F, 6.0F, 24.0F, new CubeDeformation(0.0F)).texOffs(0, 30).addBox(-10.5F, 0.0F, -24.0F, 21.0F, 6.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1745F, 0.0F, 0.0F));
		final var body = main.addOrReplaceChild("body", CubeListBuilder.create().texOffs(76, 16).addBox(-9.0F, -6.0F, 2.0F, 18.0F, 12.0F, 14.0F, new CubeDeformation(0.0F)).texOffs(0, 98).addBox(0.0F, -14.0F, 2.0F, 0.0F, 8.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -150.0F));
		final var body2 = body.addOrReplaceChild("body2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 16.0F));
		body2.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -11.0F, -1.0F, 0.0F, 8.0F, 12.0F, new CubeDeformation(0.0F)).texOffs(0, 90).addBox(-7.0F, -3.0F, -3.0F, 14.0F, 8.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.2618F, 0.0F, 0.0F));
		final var body3 = body2.addOrReplaceChild("body3", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 12.0F));
		body3.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(56, 76).addBox(0.0F, -2.0F, 5.0F, 0.0F, 12.0F, 20.0F, new CubeDeformation(0.0F)).texOffs(82, 96).addBox(-4.0F, 2.0F, -2.0F, 8.0F, 4.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.0873F, 0.0F, 0.0F));
		final var left_fin = body.addOrReplaceChild("left_fin", CubeListBuilder.create(), PartPose.offset(9.0F, 3.0F, 10.0F));
		left_fin.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(82, 52).addBox(0.0F, -1.0F, -4.0F, 16.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3491F));
		final var right_fin = body.addOrReplaceChild("right_fin", CubeListBuilder.create(), PartPose.offset(-9.0F, 3.0F, 10.0F));
		right_fin.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(66, 0).addBox(-16.0F, -1.0F, -4.0F, 16.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3491F));
		return LayerDefinition.create(meshDefinition, 256, 256);
	}

	public void translateToMouth(PoseStack pose) {
		this.main.translateAndRotate(pose);
		this.head.translateAndRotate(pose);
		this.headLower.translateAndRotate(pose);
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
	public void renderToBuffer(PoseStack pose, VertexConsumer consumer, int light, int overlay,
							   float red, float green, float blue, float alpha) {
		main.render(pose, consumer, light, overlay, red, green, blue, alpha);
	}

	public void setupAnim(Maw entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		MawAnimations.IDLE.animate(this, entity, limbSwing, limbSwingAmount);
		MawAnimations.SPECIAL_IDLE.animate(this, entity, limbSwing, limbSwingAmount);
		MawAnimations.WALK.animate(this, entity, limbSwing, limbSwingAmount);
		MawAnimations.BITE.animate(this, entity, limbSwing, limbSwingAmount);
		this.head.yRot += Math.toRadians(netHeadYaw * 0.5f);
	}
}
