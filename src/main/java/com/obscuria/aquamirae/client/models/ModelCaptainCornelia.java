package com.obscuria.aquamirae.client.models;

import com.obscuria.aquamirae.common.entities.CaptainCorneliaEntity;
import com.obscuria.obscureapi.api.hekate.Animation;
import com.obscuria.obscureapi.api.hekate.HekateLib;
import com.obscuria.obscureapi.api.hekate.Interpolations;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Arm;

public class ModelCaptainCornelia extends EntityModel<CaptainCorneliaEntity> {
	public final ModelPart main, bodyTop, bodyTop2, bodyLower, head, rightBooby, leftBooby, rightArm, leftArm, rightLeg,
			leftLeg, rightArmLower, leftArmLower, rightLegLower, leftLegLower, item, ten1, ten1_1, ten1_2, ten1_3, ten1_4, ten2, ten2_1, ten2_2,
			ten2_3, ten2_4, ten3, ten3_1, ten3_2, ten3_3, ten3_4;

	public ModelCaptainCornelia(ModelPart root) {
		this.main = root.getChild("main");
		this.bodyTop = main.getChild("body_top_Z").getChild("body_top");
		this.bodyTop2 = bodyTop.getChild("body_top2");
		this.bodyLower = main.getChild("body_lower_Z").getChild("body_lower");
		this.head = bodyTop2.getChild("head");
		this.rightBooby = bodyTop2.getChild("right_booby");
		this.leftBooby = bodyTop2.getChild("left_booby");
		this.rightArm = bodyTop2.getChild("right_arm");
		this.leftArm = bodyTop2.getChild("left_arm");
		this.rightArmLower = rightArm.getChild("right_arm_lower");
		this.leftArmLower = leftArm.getChild("left_arm_lower");
		this.rightLeg = bodyLower.getChild("right_leg");
		this.leftLeg = bodyLower.getChild("left_leg");
		this.rightLegLower = rightLeg.getChild("right_leg_lower");
		this.leftLegLower = leftLeg.getChild("left_leg_lower");
		this.item = rightArmLower.getChild("bone");
		this.ten1 = bodyTop2.getChild("ten1");
		this.ten1_1 = ten1.getChild("ten1_1");
		this.ten1_2 = ten1_1.getChild("ten1_2");
		this.ten1_3 = ten1_2.getChild("ten1_3");
		this.ten1_4 = ten1_3.getChild("ten1_4");
		this.ten2 = bodyTop2.getChild("ten2");
		this.ten2_1 = ten2.getChild("ten2_1");
		this.ten2_2 = ten2_1.getChild("ten2_2");
		this.ten2_3 = ten2_2.getChild("ten2_3");
		this.ten2_4 = ten2_3.getChild("ten2_4");
		this.ten3 = bodyTop2.getChild("ten3");
		this.ten3_1 = ten3.getChild("ten3_1");
		this.ten3_2 = ten3_1.getChild("ten3_2");
		this.ten3_3 = ten3_2.getChild("ten3_3");
		this.ten3_4 = ten3_3.getChild("ten3_4");
	}

	public static TexturedModelData createModelData() {
		final ModelData modelData = new ModelData();
		final ModelPartData root = modelData.getRoot();
		final ModelPartData main = root.addChild("main", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 4.0F, 150.0F));
		final ModelPartData bodyTopZ = main.addChild("body_top_Z", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -25.5F, -150.0F));
		final ModelPartData bodyTop = bodyTopZ.addChild("body_top", ModelPartBuilder.create().uv(32, 41).cuboid(-2.5F, -6.5F, -2.0F, 5.0F, 8.0F, 4.0F, new Dilation(0.2F)), ModelTransform.pivot(0.0F, 30.0F, 0.0F));
		final ModelPartData bodyTop2 = bodyTop.addChild("body_top2", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -5.0F, 0.0F));
		final ModelPartData head = bodyTop2.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(1.0F)).uv(24, 8).cuboid(-4.0F, -9.75F, -4.0F, 8.0F, 1.0F, 8.0F, new Dilation(0.0F)).uv(37, 54).cuboid(-1.5F, -10.25F, -1.5F, 3.0F, 1.0F, 3.0F, new Dilation(0.0F)).uv(72, 0).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -6.0F, 1.0F, 0.0873F, 0.0F, 0.0F));
		head.addChild("head1", ModelPartBuilder.create().uv(28, 53).cuboid(-1.5F, 0.75F, 5.5F, 3.0F, 1.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -9.5F, 7.0F, -1.5708F, 0.0F, 0.0F));
		head.addChild("head2", ModelPartBuilder.create().uv(22, 22).cuboid(-4.0F, -5.75F, 1.5F, 8.0F, 1.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -9.5F, 0.0F, -1.5708F, 0.0F, 0.0F));
		head.addChild("head3", ModelPartBuilder.create().uv(12, 49).cuboid(-3.5F, -3.5F, -1.05F, 7.0F, 7.0F, 1.0F, new Dilation(0.0F)).uv(12, 57).cuboid(3.0F, -3.0F, -0.3F, 1.0F, 6.0F, 1.0F, new Dilation(0.0F)).uv(56, 33).cuboid(-4.0F, -3.0F, -0.3F, 1.0F, 6.0F, 1.0F, new Dilation(0.0F)).uv(52, 31).cuboid(-4.0F, 3.0F, -0.3F, 8.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(50, 48).cuboid(-4.0F, -4.0F, -0.3F, 8.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-5.25F, -4.0F, 0.05F, 0.0F, 1.5708F, 0.0F));
		head.addChild("head4", ModelPartBuilder.create().uv(46, 17).cuboid(-3.5F, -3.5F, -1.05F, 7.0F, 7.0F, 1.0F, new Dilation(0.0F)).uv(44, 0).cuboid(3.0F, -3.0F, -0.3F, 1.0F, 6.0F, 1.0F, new Dilation(0.0F)).uv(40, 0).cuboid(-4.0F, -3.0F, -0.3F, 1.0F, 6.0F, 1.0F, new Dilation(0.0F)).uv(46, 27).cuboid(-4.0F, 3.0F, -0.3F, 8.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(46, 25).cuboid(-4.0F, -4.0F, -0.3F, 8.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(5.25F, -4.0F, 0.05F, 0.0F, -1.5708F, 0.0F));
		head.addChild("head5", ModelPartBuilder.create().uv(24, 0).cuboid(-3.5F, -31.5F, -6.25F, 7.0F, 7.0F, 1.0F, new Dilation(0.0F)).uv(4, 0).cuboid(3.0F, -31.0F, -5.5F, 1.0F, 6.0F, 1.0F, new Dilation(0.0F)).uv(0, 0).cuboid(-4.0F, -31.0F, -5.5F, 1.0F, 6.0F, 1.0F, new Dilation(0.0F)).uv(24, 19).cuboid(-4.0F, -25.0F, -5.5F, 8.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(24, 17).cuboid(-4.0F, -32.0F, -5.5F, 8.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
		bodyTop2.addChild("body", ModelPartBuilder.create().uv(32, 31).cuboid(-4.0F, -1.0F, -2.0F, 8.0F, 6.0F, 4.0F, new Dilation(0.3F)), ModelTransform.pivot(0.0F, -4.5F, 0.0F));
		final ModelPartData leftBooby = bodyTop2.addChild("left_booby", ModelPartBuilder.create(), ModelTransform.pivot(2.0F, -0.75F, 0.25F));
		leftBooby.addChild("left_boobyF", ModelPartBuilder.create().uv(50, 41).cuboid(-2.0F, -2.5F, 0.25F, 4.0F, 4.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -1.0F, -4.0F, -0.5236F, -0.2269F, 0.0175F));
		final ModelPartData rightBooby = bodyTop2.addChild("right_booby", ModelPartBuilder.create(), ModelTransform.pivot(-2.0F, -0.75F, 0.25F));
		rightBooby.addChild("right_boobyF", ModelPartBuilder.create().uv(47, 50).cuboid(-2.0F, -2.5F, 0.25F, 4.0F, 4.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -1.0F, -4.0F, -0.5236F, 0.2269F, -0.0175F));
		final ModelPartData leftArm = bodyTop2.addChild("left_arm", ModelPartBuilder.create().uv(48, 0).cuboid(0.25F, -1.0F, -2.0F, 3.0F, 6.0F, 3.0F, new Dilation(0.0F)).uv(77, 16).cuboid(0.25F, -1.0F, -2.0F, 3.0F, 6.0F, 3.0F, new Dilation(0.2F)).uv(89, 16).cuboid(0.25F, -5.0F, -0.5F, 7.0F, 9.0F, 0.0F, new Dilation(0.0F)).uv(77, 16).cuboid(0.25F, -1.0F, -2.0F, 3.0F, 6.0F, 3.0F, new Dilation(0.2F)), ModelTransform.pivot(4.0F, -3.5F, 1.0F));
		leftArm.addChild("left_arm_lower", ModelPartBuilder.create().uv(0, 64).cuboid(-1.5F, -1.0F, -1.5F, 3.0F, 7.0F, 3.0F, new Dilation(0.01F)), ModelTransform.pivot(1.75F, 5.0F, -0.5F));
		final ModelPartData rightArm = bodyTop2.addChild("right_arm", ModelPartBuilder.create().uv(0, 48).cuboid(-3.0F, -1.0F, -1.0F, 3.0F, 6.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(-4.25F, -3.5F, 0.0F));
		final ModelPartData rightArmLower = rightArm.addChild("right_arm_lower", ModelPartBuilder.create().uv(0, 64).cuboid(-1.5F, -1.0F, -1.5F, 3.0F, 7.0F, 3.0F, new Dilation(0.01F)), ModelTransform.pivot(-1.5F, 5.0F, 0.5F));
		rightArmLower.addChild("bone", ModelPartBuilder.create().uv(84, 106).cuboid(-0.5F, -0.5F, -10.5F, 1.0F, 1.0F, 21.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 4.5F, 0.0F));
		final ModelPartData ten1 = bodyTop2.addChild("ten1", ModelPartBuilder.create().uv(60, 0).cuboid(-2.0F, 0.0F, -1.0F, 4.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -5.5F, 3.5F));
		final ModelPartData ten1_1 = ten1.addChild("ten1_1", ModelPartBuilder.create().uv(60, 0).cuboid(-2.0F, 0.0F, -1.0F, 4.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 4.0F, 0.0F));
		final ModelPartData ten1_2 = ten1_1.addChild("ten1_2", ModelPartBuilder.create().uv(60, 6).cuboid(-1.5F, 0.0F, -1.0F, 3.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 4.0F, 0.0F));
		final ModelPartData ten1_3 = ten1_2.addChild("ten1_3", ModelPartBuilder.create().uv(60, 6).cuboid(-1.5F, 0.0F, -1.0F, 3.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 4.0F, 0.0F));
		ten1_3.addChild("ten1_4", ModelPartBuilder.create().uv(60, 12).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 4.0F, 0.0F));
		final ModelPartData ten2 = bodyTop2.addChild("ten2", ModelPartBuilder.create().uv(60, 0).cuboid(-2.0F, 0.0F, -1.0F, 4.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(2.75F, -7.5F, 3.0F));
		final ModelPartData ten2_1 = ten2.addChild("ten2_1", ModelPartBuilder.create().uv(60, 0).cuboid(-2.0F, 0.0F, -1.0F, 4.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 4.0F, 0.0F));
		final ModelPartData ten2_2 = ten2_1.addChild("ten2_2", ModelPartBuilder.create().uv(60, 6).cuboid(-1.5F, 0.0F, -1.0F, 3.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 4.0F, 0.0F));
		final ModelPartData ten2_3 = ten2_2.addChild("ten2_3", ModelPartBuilder.create().uv(60, 6).cuboid(-1.5F, 0.0F, -1.0F, 3.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 4.0F, 0.0F));
		ten2_3.addChild("ten2_4", ModelPartBuilder.create().uv(60, 12).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 4.0F, 0.0F));
		final ModelPartData ten3 = bodyTop2.addChild("ten3", ModelPartBuilder.create().uv(60, 0).cuboid(-2.0F, 0.0F, -1.0F, 4.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.75F, -7.5F, 3.0F));
		final ModelPartData ten3_1 = ten3.addChild("ten3_1", ModelPartBuilder.create().uv(60, 0).cuboid(-2.0F, 0.0F, -1.0F, 4.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 4.0F, 0.0F));
		final ModelPartData ten3_2 = ten3_1.addChild("ten3_2", ModelPartBuilder.create().uv(60, 6).cuboid(-1.5F, 0.0F, -1.0F, 3.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 4.0F, 0.0F));
		final ModelPartData ten3_3 = ten3_2.addChild("ten3_3", ModelPartBuilder.create().uv(60, 6).cuboid(-1.5F, 0.0F, -1.0F, 3.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 4.0F, 0.0F));
		ten3_3.addChild("ten3_4", ModelPartBuilder.create().uv(60, 12).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 4.0F, 0.0F));
		final ModelPartData bodyLowerZ = main.addChild("body_lower_Z", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 34.5F, -150.0F));
		final ModelPartData bodyLower = bodyLowerZ.addChild("body_lower", ModelPartBuilder.create().uv(0, 16).cuboid(-4.5F, -0.5F, -2.75F, 9.0F, 8.0F, 6.0F, new Dilation(0.2F)), ModelTransform.pivot(0.0F, -30.0F, 0.0F));
		final ModelPartData rightLeg = bodyLower.addChild("right_leg", ModelPartBuilder.create().uv(16, 31).cuboid(-2.0F, -1.5F, -2.0F, 4.0F, 7.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.5F, 3.5F, 0.0F));
		rightLeg.addChild("right_leg_lower", ModelPartBuilder.create().uv(12, 64).cuboid(-2.0F, -1.0F, -2.0F, 4.0F, 8.0F, 4.0F, new Dilation(0.01F)), ModelTransform.pivot(0.0F, 5.5F, 0.0F));
		final ModelPartData leftLeg = bodyLower.addChild("left_leg", ModelPartBuilder.create().uv(0, 30).cuboid(-2.0F, -1.5F, -2.0F, 4.0F, 7.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(2.5F, 3.5F, 0.0F));
		leftLeg.addChild("left_leg_lower", ModelPartBuilder.create().uv(12, 64).cuboid(-2.0F, -1.0F, -2.0F, 4.0F, 8.0F, 4.0F, new Dilation(0.01F)), ModelTransform.pivot(0.0F, 5.5F, 0.0F));
		return TexturedModelData.of(modelData, 128, 128);
	}

	public void translateToHand(Arm arm, MatrixStack stack) {
		this.main.rotate(stack);
		this.main.getChild("body_top_Z").rotate(stack);
		this.bodyTop.rotate(stack);
		this.bodyTop2.rotate(stack);
		if (arm == Arm.LEFT) {
			this.leftArm.rotate(stack);
			this.leftArmLower.rotate(stack);
		} else {
			this.rightArm.rotate(stack);
			this.rightArmLower.rotate(stack);
			this.item.rotate(stack);
		}
	}

	@Override
	public void render(MatrixStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public void setAngles(CaptainCorneliaEntity entity, float limbAngle, float limbDistance, float progress, float headYaw, float headPitch) {
		HekateLib.reset(main, bodyTop, bodyTop2, bodyLower, head, rightBooby, leftBooby, rightArm, leftArm, rightLeg,
				leftLeg, rightArmLower, leftArmLower, rightLegLower, leftLegLower, item, ten1, ten1_1, ten1_2, ten1_3, ten1_4, ten2, ten2_1, ten2_2,
				ten2_3, ten2_4, ten3, ten3_1, ten3_2, ten3_3, ten3_4);
		this.animateIdleAndMove(progress, limbDistance);
		this.animateAttack(entity.SWITCH_WEAPON, entity.PULL_ATTACK, entity.SWING_ATTACK, entity.THRUST_ATTACK, progress);
		this.animateDeath(entity.DEATH, progress);
		this.head.yaw += HekateLib.mod.head(headYaw, 0.333F);
		this.bodyTop.yaw += HekateLib.mod.head(headYaw, 0.333F);
		this.bodyTop2.yaw += HekateLib.mod.head(headYaw, 0.333F);
	}

	private void animateIdleAndMove(float ageInTicks, float limbSwingAmount) {
		HekateLib.push(ageInTicks, 0.1f, HekateLib.mod.idle(limbSwingAmount, 5f), HekateLib.Mode.DEFINITION)
				.keyframe(main, k -> k.xRot(0.6F, 1.2F))
				.keyframe(bodyLower, k -> k.xRot(15F, 20F, -0.9f))
				.keyframe(bodyTop2, k -> k.xRot(6F, -6F, 0.9f))
				.keyframe(head, k -> k.xRot(4F, -6F, 0.8f))
				.keyframe(rightArm, k -> k.rotation(12F, -9F, 9F, -27F, 6F, 20F, -0.95F))
				.keyframe(rightArmLower, k -> k.xRot(-16F, 20F, -0.9f))
				.keyframe(item, k -> k.xRot(-5F, -35F, 0.85f))
				.keyframe(leftArm, k -> k.rotation(12F, -9F, -9F, 27F, -9F, -27F, -0.95F))
				.keyframe(leftArmLower, k -> k.xRot(-24F, 30F, -0.9f))
				.keyframe(rightLeg, k -> k.rotation(12F, 24F, 0.6F, -3F, 0.6F, -3F, -0.8F))
				.keyframe(rightLegLower, k -> k.xRot(-12F, -48F, -0.6f))
				.keyframe(leftLeg, k -> k.rotation(6F, -12F, 0.6F, 3F, 0.6F, 3F, -0.8f))
				.keyframe(leftLegLower, k -> k.xRot(-24F, -27F, -0.7f))
				.keyframe(ten1, k -> k.xRot(16F, -20F, 0.35f))
				.keyframe(ten1_1, k -> k.xRot(16F, -12F, 0.3f))
				.keyframe(ten1_2, k -> k.xRot(16F, -6F, 0.25f))
				.keyframe(ten1_3, k -> k.xRot(16F, 0F, 0.2f))
				.keyframe(ten1_4, k -> k.xRot(16F, 0F, 0.15f))
				.keyframe(ten2, k -> k.xRot(16F, -20F, 0.25f).yRot(-30))
				.keyframe(ten2_1, k -> k.xRot(16F, -12F, 0.2f))
				.keyframe(ten2_2, k -> k.xRot(16F, -6F, 0.15f))
				.keyframe(ten2_3, k -> k.xRot(16F, 0F, 0.1f))
				.keyframe(ten2_4, k -> k.xRot(16F, 0F, 0.05f))
				.keyframe(ten3, k -> k.xRot(16F, -20F, 0.45f).yRot(30))
				.keyframe(ten3_1, k -> k.xRot(16F, -12F, 0.4f))
				.keyframe(ten3_2, k -> k.xRot(16F, -6F, 0.35f))
				.keyframe(ten3_3, k -> k.xRot(16F, 0F, 0.3f))
				.keyframe(ten3_4, k -> k.xRot(16F, 0F, 0.25f));
		HekateLib.push(ageInTicks, 0.2f, HekateLib.mod.move(limbSwingAmount, 5f), HekateLib.Mode.ADDITION)
				.keyframe(main, k -> k.xRot(-1.4F, 1.8F))
				.keyframe(bodyLower, k -> k.xRot(6F, -36F))
				.keyframe(bodyTop, k -> k.xRot(6F, -24F))
				.keyframe(bodyTop2, k -> k.xRot(6F, 12F))
				.keyframe(rightArm, k -> k.rotation(12F, -9F, 9F, -27F, -9F, 27F, 0.05F))
				.keyframe(rightArmLower, k -> k.xRot(24F, 30F))
				.keyframe(leftArm, k -> k.rotation(12F, -9F, -9F, 27F, 9F, -27F, 0.05F))
				.keyframe(leftArmLower, k -> k.xRot(24F, 30F))
				.keyframe(rightLeg, k -> k.rotation(24F, 12F, 0.6F, -6F, 0.6F, -6F, 0.5F))
				.keyframe(rightLegLower, k -> k.xRot(24F, -74F, 0.7f))
				.keyframe(leftLeg, k -> k.rotation(24F, 0F, -0.6F, 6F, -0.6F, 6F, 0.5F))
				.keyframe(leftLegLower, k -> k.xRot(24F, -30F, 0.8f))
				.keyframe(ten1, k -> k.xRot(-14F, -29F, 0.25f))
				.keyframe(ten1_1, k -> k.xRot(-14F, -18F, 0.2f))
				.keyframe(ten1_2, k -> k.xRot(-14F, -12F, 0.15f))
				.keyframe(ten1_3, k -> k.xRot(-14F, -6F, 0.1f))
				.keyframe(ten1_4, k -> k.xRot(-14F, -6F, 0.05f))
				.keyframe(ten2, k -> k.xRot(-14F, -29F, 0.25f).yRot(-30))
				.keyframe(ten2_1, k -> k.xRot(-14F, -18F, 0.2f))
				.keyframe(ten2_2, k -> k.xRot(-14F, -12F, 0.15f))
				.keyframe(ten2_3, k -> k.xRot(-14F, -6F, 0.1f))
				.keyframe(ten2_4, k -> k.xRot(-14F, -6F, 0.05f))
				.keyframe(ten3, k -> k.xRot(-14F, -29F, 0.25f).yRot(30))
				.keyframe(ten3_1, k -> k.xRot(-14F, -18F, 0.2f))
				.keyframe(ten3_2, k -> k.xRot(-14F, -12F, 0.15f))
				.keyframe(ten3_3, k -> k.xRot(-14F, -6F, 0.1f))
				.keyframe(ten3_4, k -> k.xRot(-14F, -6F, 0.05f));
	}

	private void animateAttack(Animation weapon, Animation pull, Animation swing, Animation thrust, float ageInTicks) {
		HekateLib.push(10, 10, Interpolations.EASE_OUT_BACK.scale(0.95f), Interpolations.EASE_OUT_CUBIC)
				.pose(0, 20, Interpolations.CEIL, ageInTicks, 1f, builder -> builder
						.keyframe(bodyTop, k -> k.rotation(-20F, 22F, 0F))
						.keyframe(bodyTop2, k -> k.rotation(-12F, 20F, 0F))
						.keyframe(head, k -> k.rotation(-12F, 0F, 0F))
						.keyframe(leftArm, k -> k.rotation(-20F, 0F, -17F))
						.keyframe(leftArmLower, k -> k.rotation(55F, 0F, 0F))
						.keyframe(rightArm, k -> k.rotation(90F, 45F, 90F))
						.keyframe(rightArmLower, k -> k.rotation(40F, 0F, 0F))
						.keyframe(item, k -> k.rotation(-45F, 0F, 0F)))
				.animate(weapon);

		HekateLib.push(10, 10, Interpolations.EASE_OUT_SINE, Interpolations.EASE_OUT_CUBIC)
				.pose(0, 10, Interpolations.CEIL, ageInTicks, 1f, builder -> builder
						.keyframe(main, k -> k.rotation(2F, 0F, 0F))
						.keyframe(bodyTop, k -> k.rotation(0F, -17.5F, 0F))
						.keyframe(bodyTop2, k -> k.rotation(0F, -32.5F, 0F))
						.keyframe(head, k -> k.rotation(-10F, 46F, -3.5F))
						.keyframe(leftArm, k -> k.rotation(30F, 35F, -32F))
						.keyframe(leftArmLower, k -> k.rotation(25F, 0F, 0F))
						.keyframe(rightArm, k -> k.rotation(5.5F, 28F, 19.5F))
						.keyframe(rightArmLower, k -> k.rotation(25F, 0F, 0F))
						.keyframe(item, k -> k.rotation(-50F, 0F, 0F))
						.keyframe(bodyLower, k -> k.rotation(27.5F, 0F, 0F))
						.keyframe(rightLeg, k -> k.rotation(25F, 2.6F, -4.2F))
						.keyframe(rightLegLower, k -> k.rotation(-60F, 0F, 0F))
						.keyframe(leftLeg, k -> k.rotation(-2.5F, 0F, 2.5F))
						.keyframe(leftLegLower, k -> k.rotation(-17.5F, 0F, 0F)))
				.pose(10, 30, Interpolations.EASE_OUT_EXPO.scale(0.5f), ageInTicks, 1f, builder -> builder
						.keyframe(main, k -> k.rotation(1.5F, 0F, 0F))
						.keyframe(bodyTop, k -> k.rotation(0F, -7.5F, 0F))
						.keyframe(bodyTop2, k -> k.rotation(10F, -15F, 0F))
						.keyframe(head, k -> k.rotation(-15F, 27F, 0F))
						.keyframe(leftArm, k -> k.rotation(-19F, 12.5F, -31F))
						.keyframe(leftArmLower, k -> k.rotation(105F, 0F, 0F))
						.keyframe(rightArm, k -> k.rotation(-10F, 1F, 20F))
						.keyframe(rightArmLower, k -> k.rotation(52F, 0F, 0F))
						.keyframe(item, k -> k.rotation(-50F, 0F, 0F))
						.keyframe(bodyLower, k -> k.rotation(-22.5F, 0F, 0F))
						.keyframe(rightLeg, k -> k.rotation(25F, 2.6F, -4.2F))
						.keyframe(rightLegLower, k -> k.rotation(-70F, 0F, 0F))
						.keyframe(leftLeg, k -> k.rotation(-2.5F, 0F, 2.5F))
						.keyframe(leftLegLower, k -> k.rotation(-17.5F, 0F, 0F)))
				.animate(pull);
		HekateLib.push(12, 20, Interpolations.EASE_OUT_SINE, Interpolations.EASE_OUT_CUBIC)
				.pose(0, 12, Interpolations.CEIL, ageInTicks, 1f, builder -> builder
						.keyframe(main, k -> k.rotation(2.5F, 0F, 0F))
						.keyframe(bodyTop, k -> k.rotation(-14F, 42F, -2F))
						.keyframe(bodyTop2, k -> k.rotation(0F, 25F, 0F))
						.keyframe(head, k -> k.rotation(3F, -56F, -8F))
						.keyframe(leftArm, k -> k.rotation(-21F, 12F, -18.5F))
						.keyframe(leftArmLower, k -> k.rotation(70F, 0F, 0F))
						.keyframe(rightArm, k -> k.rotation(57F, 25F, 74F))
						.keyframe(rightArmLower, k -> k.rotation(75F, 0F, 0F))
						.keyframe(item, k -> k.rotation(-35F, 0F, 0F))
						.keyframe(bodyLower, k -> k.rotation(25F, 0F, 0F))
						.keyframe(rightLeg, k -> k.rotation(57F, 2.6F, -4.2F))
						.keyframe(rightLegLower, k -> k.rotation(-90F, 0F, 0F))
						.keyframe(leftLeg, k -> k.rotation(-2.5F, 0F, 2.5F))
						.keyframe(leftLegLower, k -> k.rotation(-17.5F, 0F, 0F)))
				.pose(12, 40, Interpolations.EASE_OUT_EXPO.scale(0.2f), ageInTicks, 1f, builder -> builder
						.keyframe(main, k -> k.rotation(1.5F, 0F, 0F))
						.keyframe(bodyTop, k -> k.rotation(-10F, -4F, 8F))
						.keyframe(bodyTop2, k -> k.rotation(0F, -20F, 0F))
						.keyframe(head, k -> k.rotation(2F, 24F, -4.5F))
						.keyframe(leftArm, k -> k.rotation(-50F, 30F, -35F))
						.keyframe(leftArmLower, k -> k.rotation(100F, 0F, 0F))
						.keyframe(rightArm, k -> k.rotation(35F, 12F, 47F))
						.keyframe(rightArmLower, k -> k.rotation(10F, 0F, 0F))
						.keyframe(item, k -> k.rotation(-82F, 0F, 0F))
						.keyframe(bodyLower, k -> k.rotation(-32F, 2F, -4F))
						.keyframe(rightLeg, k -> k.rotation(57F, 2.6F, -4.2F))
						.keyframe(rightLegLower, k -> k.rotation(-90F, 0F, 0F))
						.keyframe(leftLeg, k -> k.rotation(-2.5F, 0F, 2.5F))
						.keyframe(leftLegLower, k -> k.rotation(-17.5F, 0F, 0F)))
						.animate(swing);
		HekateLib.push(12, 20, Interpolations.EASE_OUT_SINE, Interpolations.EASE_OUT_CUBIC)
				.pose(0, 12, Interpolations.CEIL, ageInTicks, 1f, builder -> builder
						.keyframe(main, k -> k.rotation(2.5F, 0F, 0F))
						.keyframe(bodyTop, k -> k.rotation(0F, -17.5F, 0F))
						.keyframe(bodyTop2, k -> k.rotation(0F, -27.5F, 0F))
						.keyframe(head, k -> k.rotation(-22F, 42F, -9F))
						.keyframe(leftArm, k -> k.rotation(33.5F, 12F, -18.5F))
						.keyframe(leftArmLower, k -> k.rotation(67F, 0F, 0F))
						.keyframe(rightArm, k -> k.rotation(-48F, -10.5F, 57F))
						.keyframe(rightArmLower, k -> k.rotation(75F, 0F, 0F))
						.keyframe(item, k -> k.rotation(-45F, 0F, 0F))
						.keyframe(bodyLower, k -> k.rotation(25F, 0F, 0F))
						.keyframe(rightLeg, k -> k.rotation(32.4F, 2.6F, -4.2F))
						.keyframe(rightLegLower, k -> k.rotation(-90F, 0F, 0F))
						.keyframe(leftLeg, k -> k.rotation(0F, 0F, 2.5F))
						.keyframe(leftLegLower, k -> k.rotation(-38F, 0F, 0F)))
				.pose(12, 40, Interpolations.EASE_OUT_EXPO.scale(0.2f), ageInTicks, 1f, builder -> builder
						.keyframe(main, k -> k.rotation(1.5F, 0F, 0F))
						.keyframe(bodyTop, k -> k.rotation(-12.5F, 35F, 0F))
						.keyframe(bodyTop2, k -> k.rotation(0F, 7.5F, 0F))
						.keyframe(head, k -> k.rotation(1F, -38F, 0F))
						.keyframe(leftArm, k -> k.rotation(-21.5F, 12F, -18.5F))
						.keyframe(leftArmLower, k -> k.rotation(70F, 0F, 0F))
						.keyframe(rightArm, k -> k.rotation(24F, -10.5F, 57F))
						.keyframe(rightArmLower, k -> k.rotation(12.5F, 0F, 0F))
						.keyframe(item, k -> k.rotation(-60F, 0F, 0F))
						.keyframe(bodyLower, k -> k.rotation(-28F, 10F, -2F))
						.keyframe(rightLeg, k -> k.rotation(57F, 2.6F, -4.2F))
						.keyframe(rightLegLower, k -> k.rotation(-90F, 0F, 0F))
						.keyframe(leftLeg, k -> k.rotation(-2.5F, 0F, 2.5F))
						.keyframe(leftLegLower, k -> k.rotation(-17.5F, 0F, 0F)))
				.animate(thrust);
	}

	private void animateDeath(Animation death, float ageInTicks) {
		HekateLib.push(10, 0, Interpolations.EASE_OUT_BACK, Interpolations.CEIL)
				.pose(0, 10, Interpolations.CEIL, ageInTicks, 1f, builder -> builder
						.keyframe(main, k -> k.rotation(5F, 0, 0))
						.keyframe(bodyTop, k -> k.rotation(10, 0, 0))
						.keyframe(head, k -> k.rotation(10, 0, 0))
						.keyframe(leftArm, k -> k.rotation(-35, 0, -30))
						.keyframe(leftArmLower, k -> k.rotation(45, 0, 0))
						.keyframe(rightArm, k -> k.rotation(-35, 0, 30))
						.keyframe(rightArmLower, k -> k.rotation(45, 0, 0))
						.keyframe(bodyLower, k -> k.rotation(-5, 0, 0))
						.keyframe(rightLeg, k -> k.rotation(0, 0, -3))
						.keyframe(rightLegLower, k -> k.rotation(-23, 0, 0))
						.keyframe(leftLeg, k -> k.rotation(-8, 0, 3)))
				.pose(10, 60, Interpolations.EASE_OUT_BOUNCE.scale(0.5f), ageInTicks, 1f, builder -> builder
						.keyframe(main, k -> k.rotation(-5F, 0F, 0F))
						.keyframe(bodyTop, k -> k.rotation(-82.5F, 0F, 0F))
						.keyframe(bodyTop2, k -> k.rotation(12.5F, 0F, 0F))
						.keyframe(head, k -> k.rotation(-27F, 34F, -16F))
						.keyframe(leftArm, k -> k.rotation(47F, 55F, 5F))
						.keyframe(leftArmLower, k -> k.rotation(82F, 0F, 0F))
						.keyframe(rightArm, k -> k.rotation(37F, -56F, 22F))
						.keyframe(rightArmLower, k -> k.rotation(62F, 0F, 0F))
						.keyframe(item, k -> k.rotation(-40F, 0F, 0F))
						.keyframe(bodyLower, k -> k.rotation(-85F, -5F, 0F))
						.keyframe(rightLeg, k -> k.rotation(0F, 0F, -5F))
						.keyframe(rightLegLower, k -> k.rotation(0F, 0F, 0F))
						.keyframe(leftLeg, k -> k.rotation(0F, 0F, 2.5F))
						.keyframe(leftLegLower, k -> k.rotation(0F, 0F, 0F)))
				.animate(death);
	}
}
