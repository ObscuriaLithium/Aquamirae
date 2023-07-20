package com.obscuria.aquamirae.client.models;

import com.obscuria.aquamirae.common.entities.TorturedSoulEntity;
import com.obscuria.obscureapi.api.hekate.HekateLib;
import com.obscuria.obscureapi.api.hekate.Easing;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class ModelTorturedSoul extends EntityModel<TorturedSoulEntity> {
	public final ModelPart main, body, heart, head, nose, leftArm, rightArm, leftLeg, rightLeg, leftArmLower, rightArmLower, leftLegLower, rightLegLower;

	public ModelTorturedSoul(ModelPart root) {
		this.main = root.getChild("main");
		this.body = main.getChild("body");
		this.heart = body.getChild("heart");
		this.head = body.getChild("head");
		this.nose = head.getChild("nose");
		this.leftArm = body.getChild("left_arm");
		this.rightArm = body.getChild("right_arm");
		this.leftLeg = main.getChild("left_leg");
		this.rightLeg = main.getChild("right_leg");
		this.leftArmLower = leftArm.getChild("left_arm_bottom");
		this.rightArmLower = rightArm.getChild("right_arm_bottom");
		this.leftLegLower = leftLeg.getChild("left_leg_bottom");
		this.rightLegLower = rightLeg.getChild("right_leg_bottom");
	}

	public static TexturedModelData createModelData() {
		final ModelData modelData = new ModelData();
		final ModelPartData root = modelData.getRoot();
		final ModelPartData main = root.addChild("main", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 6.0F, 150.0F));
		final ModelPartData body = main.addChild("body", ModelPartBuilder.create().uv(16, 20).cuboid(-4.0F, -12.0F, -3.0F, 8.0F, 12.0F, 6.0F, new Dilation(-0.02F)).uv(0, 39).cuboid(-4.0F, -12.0F, -3.0F, 8.0F, 18.0F, 6.0F, new Dilation(0.5F)), ModelTransform.pivot(0.0F, 6.0F, -150.0F));
		body.addChild("heart", ModelPartBuilder.create().uv(44, 21).cuboid(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -8.0F, 0.0F));
		final ModelPartData head = body.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, new Dilation(0.0F)).uv(28, 39).cuboid(-4.5F, -13.0F, -4.5F, 9.0F, 10.0F, 9.0F, new Dilation(-0.4F)), ModelTransform.pivot(0.0F, -12.0F, 0.0F));
		head.addChild("nose", ModelPartBuilder.create().uv(24, 0).cuboid(-1.0F, -1.0F, -2.0F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -2.0F, -4.0F));
		final ModelPartData leftArm = body.addChild("left_arm", ModelPartBuilder.create().uv(38, 0).mirrored().cuboid(0.0F, -2.0F, -2.0F, 4.0F, 7.0F, 4.0F, new Dilation(0.0F)).mirrored(false).uv(44, 30).mirrored().cuboid(0.0F, -6.0F, -2.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.01F)).mirrored(false), ModelTransform.of(4.0F, -10.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		leftArm.addChild("left_arm_bottom", ModelPartBuilder.create().uv(38, 11).mirrored().cuboid(-2.0F, 0.0F, -3.0F, 4.0F, 6.0F, 4.0F, new Dilation(0.01F)).mirrored(false), ModelTransform.pivot(2.0F, 4.0F, 1.0F));
		final ModelPartData rightArm = body.addChild("right_arm", ModelPartBuilder.create().uv(38, 0).cuboid(-4.0F, -2.0F, -2.0F, 4.0F, 7.0F, 4.0F, new Dilation(0.0F)).uv(44, 30).cuboid(-4.0F, -6.0F, -2.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.01F)), ModelTransform.pivot(-4.0F, -10.0F, 0.0F));
		rightArm.addChild("right_arm_bottom", ModelPartBuilder.create().uv(38, 11).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new Dilation(0.01F)), ModelTransform.pivot(-2.0F, 4.0F, 0.0F));
		final ModelPartData leftLeg = main.addChild("left_leg", ModelPartBuilder.create().uv(0, 18).mirrored().cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 7.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(2.0F, 6.0F, -150.0F));
		leftLeg.addChild("left_leg_bottom", ModelPartBuilder.create().uv(0, 29).mirrored().cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new Dilation(0.01F)).mirrored(false), ModelTransform.pivot(0.0F, 6.0F, 0.0F));
		final ModelPartData rightLeg = main.addChild("right_leg", ModelPartBuilder.create().uv(0, 18).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 7.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.0F, 6.0F, -150.0F));
		rightLeg.addChild("right_leg_bottom", ModelPartBuilder.create().uv(0, 29).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new Dilation(0.01F)), ModelTransform.pivot(0.0F, 6.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}

	@Override
	public void render(MatrixStack stack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		main.render(stack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public void setAngles(TorturedSoulEntity soul, float limbAngle, float limbDistance, float progress, float headYaw, float headPitch) {
		System.out.println(progress);
		HekateLib.reset(main, body, heart, head, nose, leftArm, rightArm, leftLeg, rightLeg, leftArmLower, rightArmLower, leftLegLower, rightLegLower);
		HekateLib.push(progress, 0.1f, HekateLib.mod.idle(limbDistance, 5), HekateLib.Mode.DEFINITION)
				.keyframe(main, k -> k.xRot(-0.3F, -0.5F))
				.keyframe(body, k -> k.xRot(-3F, -10F))
				.keyframe(head, k -> k.xRot(3F, 16F))
				.keyframe(rightArm, k -> k.xRot(12F, 35F, -0.2f).zRot(-3F, 10F, -0.2f))
				.keyframe(rightArmLower, k -> k.xRot(18F, 69F, -0.4f))
				.keyframe(leftArm, k -> k.xRot(10F, 15F, -0.2f).zRot(3F, -10F, -0.2f))
				.keyframe(leftArmLower, k -> k.xRot(15F, 30F, -0.4f))
				.keyframe(rightLeg, k -> k.rotation(15F, 30F, -0.5F, -4F, 0.5F, 4F))
				.keyframe(rightLegLower, k -> k.xRot(-30F, -44F))
				.keyframe(leftLeg, k -> k.rotation(15F, 30F, -0.5F, 4F, 0.5F, -4F))
				.keyframe(leftLegLower, k -> k.xRot(-30F, -44F));
		HekateLib.push(progress, 0.4f, HekateLib.mod.move(limbDistance, 5), HekateLib.Mode.ADDITION)
				.keyframe(main, k -> k.xRot(0.5F, -0.3F, 2, 0))
				.keyframe(body, k -> k.xRot(-3F, -3F, 2, -0.1f))
				.keyframe(head, k -> k.xRot(-3F, -3F, 2, -0.2f))
				.keyframe(rightArm, k -> k.xRot(24F, -12F, -0.1f).zRot(-3F, 10F, -0.1f))
				.keyframe(rightArmLower, k -> k.xRot(20F, 34F, -0.2f))
				.keyframe(leftArm, k -> k.xRot(-24F, -12F, -0.1f).zRot(3F, -10F, -0.1f))
				.keyframe(leftArmLower, k -> k.xRot(-20F, 34F, -0.2f))
				.keyframe(rightLeg, k -> k.xRot(-30F, 14F, -0.1f))
				.keyframe(rightLegLower, k -> k.xRot(-34F, -34F, -0.2f))
				.keyframe(leftLeg, k -> k.xRot(30F, 14F, -0.1f))
				.keyframe(leftLegLower, k -> k.xRot(34F, -34F, -0.2f));
		HekateLib.push(8, 12, Easing.EASE_OUT_ELASTIC, Easing.EASE_OUT_BACK)
				.pose(0, 20, Easing.CEIL, progress, 1f, builder -> builder
						.keyframe(body, k -> k.rotation(-27F, 0, 0))
						.keyframe(leftArm, k -> k.rotation(125F, 0, 0))
						.keyframe(rightArm, k -> k.rotation(125F, 0, 0))
						.keyframe(leftArmLower, k -> k.rotation(12F, 0, 0))
						.keyframe(rightArmLower, k -> k.rotation(12F, 0, 0)))
				.animate(soul.ATTACK);
		this.heart.pitch = progress / 13F;
		this.heart.yaw = progress / 9F;
		this.heart.roll = progress / 5F;
		this.head.yaw += HekateLib.mod.head(headYaw, 0.5F);
		this.body.yaw += HekateLib.mod.head(headYaw, 0.5F);
	}
}
