package com.obscuria.aquamirae.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.obscuria.aquamirae.common.entity.Eel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class ModelEel extends EntityModel<Eel> {
	public final ModelPart main, body1, body2, body3, body4, body5, body6, body7, body8, body9, body10, head, headUpper, headLower, leftFin, rightFin;

	public ModelEel(ModelPart root) {
		this.main = root.getChild("main");
		this.body1 = main.getChild("body1");
		this.body2 = body1.getChild("body2");
		this.body3 = body2.getChild("body3");
		this.body4 = body3.getChild("body4");
		this.body5 = body4.getChild("body5");
		this.body6 = body5.getChild("body6");
		this.body7 = body6.getChild("body7");
		this.body8 = body7.getChild("body8");
		this.body9 = body8.getChild("body9");
		this.body10 = body9.getChild("body10");
		this.head = body10.getChild("head");
		this.headUpper = head.getChild("headTop");
		this.headLower = head.getChild("headBottom");
		this.leftFin = head.getChild("leftFinP").getChild("leftFin");
		this.rightFin = head.getChild("rightFinP").getChild("rightFin");
	}

	public static LayerDefinition createBodyLayer() {
		final MeshDefinition meshdefinition = new MeshDefinition();
		final PartDefinition root = meshdefinition.getRoot();
		final PartDefinition main = root.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 300.0F));
		final PartDefinition body1 = main.addOrReplaceChild("body1", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, -300.0F, 0.5672F, 0.0F, 0.0F));
		final PartDefinition body2 = body1.addOrReplaceChild("body2", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -5.0F, 0.0F, -0.3491F, 0.0F, 0.0F));
		final PartDefinition body3 = body2.addOrReplaceChild("body3", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -6.0F, 0.0F, -0.6109F, 0.0F, 0.0F));
		final PartDefinition body4 = body3.addOrReplaceChild("body4", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -6.0F, 0.0F, -0.5236F, 0.0F, 0.0F));
		final PartDefinition body5 = body4.addOrReplaceChild("body5", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -6.0F, 0.0F, 0.5236F, 0.0F, 0.0F));
		final PartDefinition body6 = body5.addOrReplaceChild("body6", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -7.0F, 0.0F, 0.5672F, 0.0F, 0.0F));
		final PartDefinition body7 = body6.addOrReplaceChild("body7", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -5.0F, 0.0F, 0.6109F, 0.0F, 0.0F));
		final PartDefinition body8 = body7.addOrReplaceChild("body8", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -6.0F, 0.0F, 0.5236F, 0.0F, 0.0F));
		final PartDefinition body9 = body8.addOrReplaceChild("body9", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -6.0F, 0.0F, 0.3927F, 0.0F, 0.0F));
		final PartDefinition body10 = body9.addOrReplaceChild("body10", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -6.0F, 0.0F, 0.2182F, 0.0F, 0.0F));
		final PartDefinition head = body10.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -3.0F, 0.0F, 0.2182F, 0.0F, 0.0F));
		final PartDefinition headTop = head.addOrReplaceChild("headTop", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -11.0F, -6.0F, 0.6981F, 0.0F, 0.0F));
		headTop.addOrReplaceChild("bone3", CubeListBuilder.create().texOffs(84, 61).addBox(-6.0F, -42.0F, -6.0F, 12.0F, 8.0F, 12.0F, new CubeDeformation(0.0F)).texOffs(12, 0).addBox(6.0F, -40.0F, -3.0F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(12, 8).addBox(-3.0F, -40.0F, 6.0F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(84, 82).addBox(-6.0F, -46.5F, -6.0F, 12.0F, 5.0F, 12.0F, new CubeDeformation(-0.5F)).texOffs(84, 82).addBox(-7.0F, -46.0F, -7.0F, 12.0F, 5.0F, 12.0F, new CubeDeformation(-1.0F)), PartPose.offsetAndRotation(0.0F, 42.5507F, 4.774F, 0.0F, -0.7854F, 0.0F));
		final PartDefinition headBottom = head.addOrReplaceChild("headBottom", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -11.0F, -6.0F, 2.0944F, 0.0F, 0.0F));
		headBottom.addOrReplaceChild("bone5", CubeListBuilder.create().texOffs(12, 23).addBox(-9.0F, -42.0F, -9.0F, 15.0F, 2.0F, 15.0F, new CubeDeformation(0.0F)).texOffs(12, 0).addBox(-9.0F, -40.5F, -9.0F, 15.0F, 8.0F, 15.0F, new CubeDeformation(-0.5F)).texOffs(12, 0).addBox(-10.0F, -41.0F, -10.0F, 15.0F, 8.0F, 15.0F, new CubeDeformation(-1.0F)), PartPose.offsetAndRotation(0.0F, 40.2191F, 4.5237F, 0.0F, -0.7854F, 0.0F));
		final PartDefinition bone2 = head.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(12, 40).addBox(-6.0F, -45.0F, -6.0F, 12.0F, 12.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 35.0F, 0.0F, 0.0F, -0.7854F, 0.0F));
		bone2.addOrReplaceChild("bone11", CubeListBuilder.create().texOffs(169, -8).addBox(0.0F, -8.0F, 7.2426F, 0.0F, 15.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -40.0F, 0.0F, 0.0F, 0.7854F, 0.0F));
		bone2.addOrReplaceChild("bone13", CubeListBuilder.create().texOffs(169, -8).addBox(0.0F, -8.0F, 7.2426F, 0.0F, 15.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -40.0F, 0.0F, 0.0F, 2.3562F, 0.0F));
		bone2.addOrReplaceChild("bone15", CubeListBuilder.create().texOffs(169, -8).addBox(0.0F, -8.0F, 7.2426F, 0.0F, 15.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -40.0F, 0.0F, 0.0F, -0.7854F, 0.0F));
		final PartDefinition leftFinP = head.addOrReplaceChild("leftFinP", CubeListBuilder.create(), PartPose.offsetAndRotation(4.0F, -7.0F, -4.0F, 0.0F, 0.7854F, 0.0F));
		leftFinP.addOrReplaceChild("leftFin", CubeListBuilder.create().texOffs(137, -9).addBox(0.5F, -1.0F, -5.0F, 0.0F, 18.0F, 10.0F, new CubeDeformation(0.0F)).texOffs(158, 1).addBox(0.0F, -1.0F, -2.0F, 1.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.9599F));
		final PartDefinition rightFinP = head.addOrReplaceChild("rightFinP", CubeListBuilder.create(), PartPose.offsetAndRotation(-5.0F, -7.0F, -4.0F, 0.0F, -0.7854F, 0.0F));
		rightFinP.addOrReplaceChild("rightFin", CubeListBuilder.create().texOffs(137, -9).addBox(0.5F, -1.0F, -5.0F, 0.0F, 18.0F, 10.0F, new CubeDeformation(0.0F)).texOffs(158, 1).addBox(0.0F, -1.0F, -2.0F, 1.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0908F));
		final PartDefinition bone20 = body10.addOrReplaceChild("bone20", CubeListBuilder.create().texOffs(48, 52).addBox(-6.0F, -44.0F, -6.0F, 12.0F, 9.0F, 12.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, 40.0F, 0.0F, 0.0F, -0.7854F, 0.0F));
		bone20.addOrReplaceChild("bone17", CubeListBuilder.create().texOffs(170, -8).addBox(0.0F, 3.0F, 5.0F, 0.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -47.0F, 0.0F, 0.0F, 0.7854F, 0.0F));
		bone20.addOrReplaceChild("bone19", CubeListBuilder.create().texOffs(170, -8).addBox(0.0F, 3.0F, 5.0F, 0.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -47.0F, 0.0F, 0.0F, 2.3562F, 0.0F));
		bone20.addOrReplaceChild("bone21", CubeListBuilder.create().texOffs(170, -8).addBox(0.0F, 3.0F, 5.0F, 0.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -47.0F, 0.0F, 0.0F, -0.7854F, 0.0F));
		final PartDefinition bone18 = body9.addOrReplaceChild("bone18", CubeListBuilder.create().texOffs(60, 11).addBox(-6.0F, -34.0F, -6.0F, 12.0F, 9.0F, 12.0F, new CubeDeformation(-0.15F)), PartPose.offsetAndRotation(0.0F, 30.0F, 0.0F, 0.0F, -0.7854F, 0.0F));
		bone18.addOrReplaceChild("bone22", CubeListBuilder.create().texOffs(170, -8).addBox(0.0F, 2.0F, 3.0F, 0.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -37.0F, 0.0F, 0.0F, 0.7854F, 0.0F));
		bone18.addOrReplaceChild("bone23", CubeListBuilder.create().texOffs(170, -8).addBox(0.0F, 2.0F, 3.0F, 0.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -37.0F, 0.0F, 0.0F, 2.3562F, 0.0F));
		bone18.addOrReplaceChild("bone24", CubeListBuilder.create().texOffs(170, -8).addBox(0.0F, 2.0F, 3.0F, 0.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -37.0F, 0.0F, 0.0F, -0.7854F, 0.0F));
		body8.addOrReplaceChild("bone16", CubeListBuilder.create().texOffs(12, 64).addBox(-6.0F, -24.0F, -6.0F, 12.0F, 9.0F, 12.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.0F, 20.0F, 0.0F, 0.0F, -0.7854F, 0.0F));
		body7.addOrReplaceChild("bone14", CubeListBuilder.create().texOffs(48, 73).addBox(-6.0F, -4.0F, -6.0F, 12.0F, 9.0F, 12.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));
		body6.addOrReplaceChild("bone12", CubeListBuilder.create().texOffs(84, 32).addBox(-6.0F, -3.0F, -6.0F, 12.0F, 9.0F, 12.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));
		body5.addOrReplaceChild("bone10", CubeListBuilder.create().texOffs(2, 85).addBox(-5.0F, -44.0F, -5.0F, 10.0F, 8.0F, 10.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(0.0F, 40.0F, 0.0F, 0.0F, -0.7854F, 0.0F));
		body4.addOrReplaceChild("bone8", CubeListBuilder.create().texOffs(32, 94).addBox(-5.0F, -34.0F, -5.0F, 10.0F, 8.0F, 10.0F, new CubeDeformation(0.15F)), PartPose.offsetAndRotation(0.0F, 30.0F, 0.0F, 0.0F, -0.7854F, 0.0F));
		body3.addOrReplaceChild("bone6", CubeListBuilder.create().texOffs(96, 0).addBox(-5.0F, -24.0F, -5.0F, 10.0F, 8.0F, 10.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, 20.0F, 0.0F, 0.0F, -0.7854F, 0.0F));
		body2.addOrReplaceChild("bone4", CubeListBuilder.create().texOffs(72, 99).addBox(-5.0F, -4.0F, -5.0F, 10.0F, 8.0F, 10.0F, new CubeDeformation(0.05F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));
		body1.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(0, 103).addBox(-5.0F, -3.0F, -5.0F, 10.0F, 8.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));
		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		poseStack.pushPose();
		poseStack.scale(1.8f, 1.8f, 1.8f);
		poseStack.translate(0F, -0.7F, 0F);
		main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		poseStack.popPose();
	}

	public void setupAnim(Eel eel, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
//		HekateLib.reset(main, body1, body2, body3, body4, body5, body6, body7, body8, body9, body10, head, headUpper, headLower, leftFin, rightFin);
//		HekateLib.push(ageInTicks, 0.1f, 1, HekateLib.Mode.DEFINITION)
//				.keyframe(body1, k -> k.xRot(-1F, -17.5F, -0.95F))
//				.keyframe(body2, k -> k.xRot(1F, 20F, -0.9F))
//				.keyframe(body3, k -> k.xRot(-1F, 22.5F, -0.85F))
//				.keyframe(body4, k -> k.xRot(-1F, 20F, -0.8F))
//				.keyframe(body5, k -> k.xRot(-2F, -22.5F, -0.75F))
//				.keyframe(body6, k -> k.xRot(-2F, -22.5F, -0.7F))
//				.keyframe(body7, k -> k.xRot(-2F, -27.5F, -0.65F))
//				.keyframe(body8, k -> k.xRot(-2F, -25F, -0.6F))
//				.keyframe(body9, k -> k.xRot(-3F, -27.5F, -0.55F))
//				.keyframe(body10, k -> k.xRot(-3F, -22.5F, -0.5F))
//				.keyframe(head, k -> k.xRot(-3F, -22.5F))
//				.keyframe(headUpper, k -> k.xRot(-40F))
//				.keyframe(headLower, k -> k.xRot(-10F, -105F))
//				.keyframe(leftFin, k -> k.zRot(10F, -45F))
//				.keyframe(rightFin, k -> k.zRot(-10F, 45F));
//		HekateLib.push(60, 60, Easing.EASE_IN_OUT_BACK, Easing.EASE_IN_OUT_BACK)
//				.pose(0, 300, Easing.CEIL, ageInTicks, 0.14f, builder -> builder
//						.keyframe(body5, k -> k.rotation(-2F, -22.5F, 1, 0, 0, 0, 2, -0.95f))
//						.keyframe(body6, k -> k.rotation(-2F, -22.5F, 2, 0, 0, 0, -0.9f))
//						.keyframe(body7, k -> k.rotation(-2F, -27.5F, 3, 0, 0, 0, -0.85f))
//						.keyframe(body8, k -> k.rotation(-2F, -25F, 4, 0, 0, 0, -0.8f))
//						.keyframe(body9, k -> k.rotation(-3F, -27.5F, 5, 0, 0, 0, -0.75f))
//						.keyframe(body10, k -> k.rotation(-3F, -22.5F, 6, 0, 0, 0, -0.7f))
//						.keyframe(head, k -> k.rotation(-3F, -22.5F, 7, 0, 0, 0, -0.65f))
//						.keyframe(headLower, k -> k.xRot(HekateLib.math.cycle(ageInTicks, 0.1f) * 8, -105, 10, 0)))
//				.animate(eel.RARE_IDLE);
//		HekateLib.push(12, 12, Easing.LINEAR, Easing.EASE_OUT_CUBIC)
//				.pose(0, 12, Easing.CEIL, ageInTicks, 1f, builder -> builder
//						.keyframe(body1, k -> k.xRot(-17.5F))
//						.keyframe(body2, k -> k.xRot(20F))
//						.keyframe(body3, k -> k.xRot(22.5F))
//						.keyframe(body4, k -> k.xRot(27.5F))
//						.keyframe(body5, k -> k.xRot(-2.5F))
//						.keyframe(body6, k -> k.xRot(-2.5F))
//						.keyframe(body7, k -> k.xRot(-30F))
//						.keyframe(body8, k -> k.xRot(-30F))
//						.keyframe(body9, k -> k.xRot(-35F))
//						.keyframe(body10, k -> k.xRot(-25F))
//						.keyframe(head, k -> k.xRot(-25F))
//						.keyframe(headUpper, k -> k.xRot(-40F))
//						.keyframe(headLower, k -> k.rotation(10F, -170F, 0F, 0F, 0F, 0F, 1.4F, 0F))
//						.keyframe(leftFin, k -> k.zRot(-90F))
//						.keyframe(rightFin, k -> k.zRot(90F)))
//				.pose(12, 30, Easing.EASE_IN_OUT_CUBIC.scale(0.2f), ageInTicks, 1f, builder -> builder
//						.keyframe(body1, k -> k.xRot(-17.5F))
//						.keyframe(body2, k -> k.xRot(10F).scale(1.025f))
//						.keyframe(body3, k -> k.xRot(15F).scale(1.025f))
//						.keyframe(body4, k -> k.xRot(12.5F).scale(1.025f))
//						.keyframe(body5, k -> k.xRot(-35F).yRot(-1F).scale(1.025f))
//						.keyframe(body6, k -> k.xRot(-30F).yRot(-1F).scale(1.025f))
//						.keyframe(body7, k -> k.xRot(-35F).yRot(-2F).scale(1.025f))
//						.keyframe(body8, k -> k.xRot(-25F).yRot(-2F).scale(1.025f))
//						.keyframe(body9, k -> k.xRot(-12.5F).yRot(-3F).scale(1.025f))
//						.keyframe(body10, k -> k.xRot(-2.5F).yRot(-3F).scale(1.025f))
//						.keyframe(head, k -> k.xRot(-20F).yRot(-4F).scale(1.025f))
//						.keyframe(headUpper, k -> k.xRot(-40F))
//						.keyframe(headLower, k -> k.xRot(-75F))
//						.keyframe(leftFin, k -> k.zRot(-40))
//						.keyframe(rightFin, k -> k.zRot(40)))
//				.animate(eel.ATTACK);
//		HekateLib.push(12, 48, Easing.EASE_OUT_BACK, Easing.EASE_IN_OUT_BACK)
//				.pose(0, 12, Easing.CEIL, ageInTicks, 1f, builder -> builder
//						.keyframe(body1, k -> k.xRot(-17.5F))
//						.keyframe(body2, k -> k.xRot(20F))
//						.keyframe(body3, k -> k.xRot(22.5F))
//						.keyframe(body4, k -> k.xRot(27.5F))
//						.keyframe(body5, k -> k.xRot(-2.5F))
//						.keyframe(body6, k -> k.rotation(0F, -2.5F, 0F, 0F, 5F, 0F, 0.4F, -0.95F))
//						.keyframe(body7, k -> k.rotation(0F, -30F, 0F, 0F, 5F, 0F, 0.4F, -0.90F))
//						.keyframe(body8, k -> k.rotation(0F, -30F, 0F, 0F, 5F, 0F, 0.4F, -0.85F))
//						.keyframe(body9, k -> k.rotation(0F, -35F, 0F, 0F, 5F, 0F, 0.4F, -0.80F))
//						.keyframe(body10, k -> k.rotation(0F, -25F, 0F, 0F, 5F, 0F, 0.4F, -0.75F))
//						.keyframe(head, k -> k.rotation(0F, -25F, 0F, 0F, 5F, 0F, 0.4F, -0.70F))
//						.keyframe(headUpper, k -> k.xRot(-40F))
//						.keyframe(headLower, k -> k.rotation(5F, -90F, 0F, 0F, 0F, 0F, 1.4F, 0F))
//						.keyframe(leftFin, k -> k.zRot(-90F))
//						.keyframe(rightFin, k -> k.zRot(90F)))
//				.pose(12, 100, Easing.EASE_IN_OUT_CUBIC.scale(0.05f), ageInTicks, 1f, builder -> builder
//						.keyframe(body1, k -> k.xRot(-17.5F))
//						.keyframe(body2, k -> k.xRot(12.5F))
//						.keyframe(body3, k -> k.xRot(25F))
//						.keyframe(body4, k -> k.xRot(22.5F))
//						.keyframe(body5, k -> k.xRot(-30F))
//						.keyframe(body6, k -> k.rotation(0F, -32.5F, 0F, 0F, 4F, 0F, 0.4F, -0.95F))
//						.keyframe(body7, k -> k.rotation(0F, -27.5F, 0F, 0F, 4F, 0F, 0.4F, -0.90F))
//						.keyframe(body8, k -> k.rotation(0F, -27.5F, 0F, 0F, 4F, 0F, 0.4F, -0.85F))
//						.keyframe(body9, k -> k.rotation(0F, -22.5F, 0F, 0F, 4F, 0F, 0.4F, -0.80F))
//						.keyframe(body10, k -> k.rotation(0F, -7.5F, 0F, 0F, 4F, 0F, 0.4F, -0.75F))
//						.keyframe(head, k -> k.rotation(0F, -12.5F, 0F, 0F, 4F, 0F, 0.4F, -0.70F))
//						.keyframe(headUpper, k -> k.xRot(-40F))
//						.keyframe(headLower, k -> k.rotation(10F, -145F, 0F, 0F, 0F, 0F, 1.5F, 0F))
//						.keyframe(leftFin, k -> k.zRot(-10, -80F, 2, 0))
//						.keyframe(rightFin, k -> k.zRot(10, 80F, 2, 0)))
//				.animate(eel.ROAR);
//		HekateLib.push(20, 0, Easing.EASE_OUT_CUBIC.scale(0.8f), Easing.CEIL)
//				.pose(0, 20, Easing.CEIL, ageInTicks, 1f, builder -> builder
//						.keyframe(body1, k -> k.xRot(-17.5F))
//						.keyframe(body2, k -> k.xRot(20F))
//						.keyframe(body3, k -> k.xRot(22.5F))
//						.keyframe(body4, k -> k.xRot(27.5F))
//						.keyframe(body5, k -> k.xRot(-2.5F))
//						.keyframe(body6, k -> k.rotation(0F, -2.5F, 0F, 0F, 5F, 0F, 0.4F, -0.95F))
//						.keyframe(body7, k -> k.rotation(0F, -30F, 0F, 0F, 10F, 0F, 0.4F, -0.90F))
//						.keyframe(body8, k -> k.rotation(0F, -30F, 0F, 0F, 15F, 0F, 0.4F, -0.85F))
//						.keyframe(body9, k -> k.rotation(0F, -35F, 0F, 0F, 20F, 0F, 0.4F, -0.80F))
//						.keyframe(body10, k -> k.rotation(0F, -25F, 0F, 0F, 20F, 0F, 0.4F, -0.75F))
//						.keyframe(head, k -> k.rotation(0F, -25F, 0F, 0F, 20F, 0F, 0.4F, -0.70F))
//						.keyframe(headUpper, k -> k.xRot(-40F))
//						.keyframe(headLower, k -> k.rotation(5F, -90F, 0F, 0F, 0F, 0F, 1.4F, 0F))
//						.keyframe(leftFin, k -> k.zRot(-90F))
//						.keyframe(rightFin, k -> k.zRot(90F)))
//				.pose(20, 60, Easing.EASE_OUT_BOUNCE.scale(0.8f), ageInTicks, 1f, builder -> builder
//						.keyframe(body1, k -> k.xRot(-17.5F))
//						.keyframe(body2, k -> k.xRot(-30))
//						.keyframe(body3, k -> k.xRot(-30))
//						.keyframe(body4, k -> k.xRot(-20))
//						.keyframe(body5, k -> k.xRot(-10))
//						.keyframe(body6, k -> k.rotation(10, 0, 10))
//						.keyframe(body7, k -> k.rotation(5, 0, 10))
//						.keyframe(body8, k -> k.rotation(4, 0, 10))
//						.keyframe(body9, k -> k.rotation(3, 0, 10))
//						.keyframe(body10, k -> k.rotation(2, 0, 10))
//						.keyframe(head, k -> k.rotation(1, 0, 10))
//						.keyframe(headUpper, k -> k.xRot(-40F))
//						.keyframe(headLower, k -> k.rotation(-100, 0, 0))
//						.keyframe(leftFin, k -> k.zRot(0))
//						.keyframe(rightFin, k -> k.zRot(0)))
//				.animate(eel.DEATH);
//		this.animateMove(eel.MOVE, ageInTicks);
		this.rotateHead(netHeadYaw);
	}

//	private void animateMove(Animation move, float ageInTicks) {
//		this.main.visible = !(move.getTick() >= 24 && move.getTick() <= 70);
//		HekateLib.push(30, 30, Easing.EASE_IN_QUINT.scale(0.8f), Easing.EASE_OUT_CUBIC)
//				.pose(0, 30, Easing.CEIL, ageInTicks, 1f, builder -> builder
//						.keyframe(main, k -> k.rotation(-20F, 0F, 0F).scale(0.8f)))
//				.pose(70, 100, Easing.EASE_OUT_CUBIC, ageInTicks, 1f, builder -> builder
//						.keyframe(main, k -> k.rotation(0F, 0F, 0F).scale(1f)))
//				.animate(move);
//		HekateLib.push(20, 20, Easing.EASE_IN_QUINT, Easing.EASE_OUT_CUBIC)
//				.pose(0, 30, Easing.CEIL, ageInTicks, 0.4f, builder -> builder
//						.keyframe(body2, k -> k.rotation(-1F, 0F, 0F, 0F, 1F, 0F, -0.90F))
//						.keyframe(body3, k -> k.rotation(-1F, 0F, 0F, 0F, 1F, 0F, -0.85F))
//						.keyframe(body4, k -> k.rotation(-1F, 0F, 0F, 0F, 1F, 0F, -0.80F))
//						.keyframe(body5, k -> k.rotation(-1F, 0F, 0F, 0F, 1F, 0F, -0.75F))
//						.keyframe(body6, k -> k.rotation(-1F, 0F, 0F, 0F, 1F, 0F, -0.75F))
//						.keyframe(body7, k -> k.rotation(-1F, 0F, 0F, 0F, 1F, 0F, -0.65F))
//						.keyframe(body8, k -> k.rotation(-1F, 0F, 0F, 0F, 1F, 0F, -0.60F))
//						.keyframe(body9, k -> k.rotation(-1F, 0F, 0F, 0F, 1F, 0F, -0.55F))
//						.keyframe(body10, k -> k.rotation(-1F, 0F, 0F, 0F, 1F, 0F, -0.50F))
//						.keyframe(head, k -> k.rotation(-1F, 0F, 0F, 0F, 1F, 0F, -0.50F))
//						.keyframe(headUpper, k -> k.rotation(-40F, 0F, 0F))
//						.keyframe(headLower, k -> k.rotation(5F, -90F, 0F, 0F, 0F, 0F, 3f, 0))
//						.keyframe(leftFin, k -> k.rotation(0F, 0F, -90F))
//						.keyframe(rightFin, k -> k.rotation(0F, 0F, 90F)))
//				.pose(70, 100, Easing.EASE_OUT_CUBIC, ageInTicks, 0.4f, builder -> builder
//						.keyframe(body2, k -> k.rotation(-3F, 0F, 0F, 0F, 3F, 0F, -0.90F))
//						.keyframe(body3, k -> k.rotation(-3F, 0F, 0F, 0F, 3F, 0F, -0.85F))
//						.keyframe(body4, k -> k.rotation(-3F, 0F, 0F, 0F, 3F, 0F, -0.80F))
//						.keyframe(body5, k -> k.rotation(-3F, 0F, 0F, 0F, 3F, 0F, -0.75F))
//						.keyframe(body6, k -> k.rotation(-3F, 0F, 0F, 0F, 3F, 0F, -0.75F))
//						.keyframe(body7, k -> k.rotation(-3F, 0F, 0F, 0F, 3F, 0F, -0.65F))
//						.keyframe(body8, k -> k.rotation(-3F, 0F, 0F, 0F, 3F, 0F, -0.60F))
//						.keyframe(body9, k -> k.rotation(-3F, 0F, 0F, 0F, 3F, 0F, -0.55F))
//						.keyframe(body10, k -> k.rotation(-3F, 0F, 0F, 0F, 3F, 0F, -0.50F))
//						.keyframe(head, k -> k.rotation(-1F, 0F, 0F, 0F, 1F, 0F, -0.50F))
//						.keyframe(headUpper, k -> k.rotation(-40F, 0F, 0F))
//						.keyframe(headLower, k -> k.rotation(5F, -90F, 0F, 0F, 0F, 0F, 3f, 0))
//						.keyframe(leftFin, k -> k.rotation(0F, 0F, -90F))
//						.keyframe(rightFin, k -> k.rotation(0F, 0F, 90F)))
//				.animate(move);
//	}

	private void rotateHead(float netHeadYaw) {
		final float head = 0;//HekateLib.mod.head(netHeadYaw, 0.1F);
		this.body1.yRot += head;
		this.body2.yRot += head;
		this.body3.yRot += head;
		this.body4.yRot += head;
		this.body5.yRot += head;
		this.body6.yRot += head;
		this.body7.yRot += head;
		this.body8.yRot += head;
		this.body9.yRot += head;
		this.body10.yRot += head;
	}
}
