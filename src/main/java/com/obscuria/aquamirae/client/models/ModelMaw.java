package com.obscuria.aquamirae.client.models;

import com.obscuria.aquamirae.common.entities.MawEntity;
import com.obscuria.obscureapi.api.hekate.HekateLib;
import com.obscuria.obscureapi.api.hekate.Easing;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class ModelMaw extends EntityModel<MawEntity> {
	public final ModelPart main, head, headUpper, headLower, body, body2, body3, rightFin, leftFin;

	public ModelMaw(ModelPart root) {
		this.main = root.getChild("main");
		this.head = main.getChild("head");
		this.headUpper = head.getChild("head_upper");
		this.headLower = head.getChild("head_lower");
		this.body = main.getChild("body");
		this.body2 = body.getChild("body2");
		this.body3 = body2.getChild("body3");
		this.rightFin = body.getChild("right_fin");
		this.leftFin = body.getChild("left_fin");
	}

	public static TexturedModelData createModelData() {
		final ModelData modelData = new ModelData();
		final ModelPartData root = modelData.getRoot();
		final ModelPartData main = root.addChild("main", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 15.0F, 150.0F));
		final ModelPartData head = main.addChild("head", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, -147.0F));
		final ModelPartData headUpper = head.addChild("head_upper", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		headUpper.addChild("cube_r1", ModelPartBuilder.create().uv(64, 66).cuboid(-10.0F, 0.0F, -24.0F, 20.0F, 6.0F, 24.0F, new Dilation(0.0F)).uv(0, 60).cuboid(-10.0F, -6.0F, -24.0F, 20.0F, 6.0F, 24.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.4363F, 0.0F, 0.0F));
		headUpper.addChild("cube_r2", ModelPartBuilder.create().uv(66, 19).cuboid(0.01F, -14.4226F, -20.0937F, 0.0F, 8.0F, 23.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -1.0F, -3.75F, -0.4363F, 0.0F, 0.0F));
		final ModelPartData headLower = head.addChild("head_lower", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		headLower.addChild("cube_r3", ModelPartBuilder.create().uv(0, 0).cuboid(-10.5F, -6.0F, -24.0F, 21.0F, 6.0F, 24.0F, new Dilation(0.0F)).uv(0, 30).cuboid(-10.5F, 0.0F, -24.0F, 21.0F, 6.0F, 24.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.1745F, 0.0F, 0.0F));
		final ModelPartData body = main.addChild("body", ModelPartBuilder.create().uv(76, 16).cuboid(-9.0F, -6.0F, 2.0F, 18.0F, 12.0F, 14.0F, new Dilation(0.0F)).uv(0, 98).cuboid(0.0F, -14.0F, 2.0F, 0.0F, 8.0F, 14.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, -150.0F));
		final ModelPartData body2 = body.addChild("body2", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 16.0F));
		body2.addChild("cube_r4", ModelPartBuilder.create().uv(0, 0).cuboid(0.0F, -11.0F, -1.0F, 0.0F, 8.0F, 12.0F, new Dilation(0.0F)).uv(0, 90).cuboid(-7.0F, -3.0F, -3.0F, 14.0F, 8.0F, 14.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.2618F, 0.0F, 0.0F));
		final ModelPartData body3 = body2.addChild("body3", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 12.0F));
		body3.addChild("cube_r5", ModelPartBuilder.create().uv(56, 76).cuboid(0.0F, -2.0F, 5.0F, 0.0F, 12.0F, 20.0F, new Dilation(0.0F)).uv(82, 96).cuboid(-4.0F, 2.0F, -2.0F, 8.0F, 4.0F, 14.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.0873F, 0.0F, 0.0F));
		final ModelPartData leftFin = body.addChild("left_fin", ModelPartBuilder.create(), ModelTransform.pivot(9.0F, 3.0F, 10.0F));
		leftFin.addChild("cube_r6", ModelPartBuilder.create().uv(82, 52).cuboid(0.0F, -1.0F, -4.0F, 16.0F, 2.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3491F));
		final ModelPartData rightFin = body.addChild("right_fin", ModelPartBuilder.create(), ModelTransform.pivot(-9.0F, 3.0F, 10.0F));
		rightFin.addChild("cube_r7", ModelPartBuilder.create().uv(66, 0).cuboid(-16.0F, -1.0F, -4.0F, 16.0F, 2.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3491F));
		return TexturedModelData.of(modelData, 256, 256);
	}

	public void translate(MatrixStack pose) {
		this.main.rotate(pose);
		this.head.rotate(pose);
		this.headLower.rotate(pose);
	}

	@Override
	public void render(MatrixStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public void setAngles(MawEntity maw, float limbAngle, float limbDistance, float progress, float headYaw, float headPitch) {
		HekateLib.reset(main, head, headUpper, headLower, body, body2, body3, rightFin, leftFin);
		HekateLib.push(progress, 0.06f, HekateLib.mod.idle(limbDistance, 10), HekateLib.Mode.DEFINITION)
				.keyframe(headUpper, k -> k.rotation(-5, 0, 0, 0, 0, 0, 2, 0))
				.keyframe(body2, k -> k.xRot(-8, -8).yRot(-10, 0, 2, 0).zRot(4, 0))
				.keyframe(body3, k -> k.xRot(-8, -8, -0.1f).yRot(-10, 0, 2, -0.1f).zRot(4, 0, -0.1f));
		HekateLib.push(limbAngle, 0.6f, HekateLib.mod.move(limbDistance, 10), HekateLib.Mode.ADDITION)
				.keyframe(main, k -> k.zRot(-4, 0))
				.keyframe(headUpper, k -> k.xRot(-4, 12))
				.keyframe(body, k -> k.yRot(-12, 0))
				.keyframe(body2, k -> k.yRot(-12, 0, -0.1f))
				.keyframe(body3, k -> k.yRot(-12, 0, -0.2f))
				.keyframe(rightFin, k -> k.yRot(40, 0).zRot(-12, 10, -0.3f))
				.keyframe(leftFin, k -> k.yRot(40, 0).zRot(-12, -10, -0.3f));
		HekateLib.push(1, 13, Easing.EASE_IN_CUBIC, Easing.EASE_OUT_CUBIC)
				.pose(0, 2, Easing.EASE_OUT_CUBIC, progress, 1F, builder -> builder
						.keyframe(head, k -> k.xRot(0, -10))
						.keyframe(headUpper, k -> k.xRot(0, 50)))
				.pose(2, 20, Easing.EASE_IN_QUART.scale(0.15f), progress, 1F, builder -> builder
						.keyframe(head, k -> k.xRot(0, 10))
						.keyframe(headUpper, k -> k.xRot(0, -22)))
				.animate(maw.ATTACK);
		HekateLib.push(6, 0, Easing.EASE_OUT_CIRCLE, Easing.CEIL)
				.pose(0, 6, Easing.CEIL, progress, 1F, builder -> builder
						.keyframe(main, k -> k.xRot(8))
						.keyframe(head, k -> k.xRot(0, -50))
						.keyframe(body2, k -> k.xRot(20))
						.keyframe(body3, k -> k.xRot(40))
						.keyframe(headUpper, k -> k.xRot(0, 50)))
				.pose(6, 40, Easing.EASE_OUT_BOUNCE.scale(0.6f), progress, 1F, builder -> builder
						.keyframe(main, k -> k.xRot(0))
						.keyframe(head, k -> k.xRot(0, 10))
						.keyframe(body2, k -> k.xRot(0))
						.keyframe(body3, k -> k.xRot(0))
						.keyframe(headUpper, k -> k.xRot(0, -22)))
				.animate(maw.DEATH);
	}
}
