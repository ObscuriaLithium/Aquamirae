package com.obscuria.aquamirae.client.models;

import com.obscuria.aquamirae.common.entities.AnglerfishEntity;
import com.obscuria.obscureapi.api.hekate.HekateLib;
import com.obscuria.obscureapi.api.hekate.Interpolations;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class ModelAnglerfish extends EntityModel<AnglerfishEntity> {
	public final ModelPart main, head, headUpper, headLower, body, tail1, tail2, tail3, tail4, tail5, tail6, leftFin, rightFin, lamp1, lamp2, lamp3, lamp4, lamp5;

	public ModelAnglerfish(ModelPart root) {
		this.main = root.getChild("main");
		this.head = main.getChild("head");
		this.body = main.getChild("body");
		this.headUpper = head.getChild("headTop");
		this.headLower = head.getChild("headBottom");
		this.leftFin = body.getChild("leftFin");
		this.rightFin = body.getChild("rightFin");
		this.tail1 = body.getChild("tail1");
		this.tail2 = tail1.getChild("tail2");
		this.tail3 = tail2.getChild("tail3");
		this.tail4 = tail3.getChild("tail4");
		this.tail5 = tail4.getChild("tail5");
		this.tail6 = tail5.getChild("tail6");
		this.lamp1 = headUpper.getChild("lamp1");
		this.lamp2 = lamp1.getChild("lamp2");
		this.lamp3 = lamp2.getChild("lamp3");
		this.lamp4 = lamp3.getChild("lamp4");
		this.lamp5 = lamp4.getChild("lamp5");
	}

	public static TexturedModelData createModelData() {
		final ModelData modelData = new ModelData();
		final ModelPartData root = modelData.getRoot();
		final ModelPartData main = root.addChild("main", ModelPartBuilder.create(), ModelTransform.of(0.0F, 5.0F, 0.0F, 0.2618F, 0.0F, 0.0F));
		final ModelPartData head = main.addChild("head", ModelPartBuilder.create(), ModelTransform.of(0.0F, 0.0F, -5.0F, 0.6109F, 0.0F, 0.0F));
		final ModelPartData headTop = head.addChild("headTop", ModelPartBuilder.create().uv(50, 45).cuboid(-7.0F, -16.0F, 0.0F, 14.0F, 16.0F, 10.0F, new Dilation(0.0F)).uv(59, 10).cuboid(1.0F, -17.0F, 2.0F, 5.0F, 1.0F, 5.0F, new Dilation(0.0F)).uv(40, 0).cuboid(-6.0F, -17.0F, 2.0F, 5.0F, 1.0F, 5.0F, new Dilation(0.0F)).uv(0, 55).cuboid(-7.0F, -16.0F, -8.0F, 14.0F, 16.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 2.0F, -5.0F));
		final ModelPartData lamp1 = headTop.addChild("lamp1", ModelPartBuilder.create().uv(107, 0).cuboid(-0.5F, -5.0F, 0.0F, 1.0F, 5.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -16.0F, 9.0F, -0.2182F, 0.0F, 0.0F));
		final ModelPartData lamp2 = lamp1.addChild("lamp2", ModelPartBuilder.create().uv(107, 5).cuboid(-0.5F, -5.0F, 0.0F, 1.0F, 5.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -5.0F, 0.0F, 0.1745F, 0.0F, 0.0F));
		final ModelPartData lamp3 = lamp2.addChild("lamp3", ModelPartBuilder.create().uv(107, 10).cuboid(-0.5F, -5.0F, 0.0F, 1.0F, 5.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -5.0F, 0.0F, 0.5672F, 0.0F, 0.0F));
		final ModelPartData lamp4 = lamp3.addChild("lamp4", ModelPartBuilder.create().uv(107, 15).cuboid(-0.5F, -5.0F, 0.0F, 1.0F, 5.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -5.0F, 0.0F, 0.9163F, 0.0F, 0.0F));
		lamp4.addChild("lamp5", ModelPartBuilder.create().uv(104, 20).cuboid(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -5.0F, 0.0F, 0.5672F, 0.0F, 0.0F));
		head.addChild("headBottom", ModelPartBuilder.create().uv(44, 16).cuboid(-7.0F, -2.0F, -14.0F, 14.0F, 3.0F, 16.0F, new Dilation(0.11F)).uv(0, 32).cuboid(-7.0F, -9.0F, -14.0F, 14.0F, 7.0F, 16.0F, new Dilation(0.1F)), ModelTransform.pivot(0.0F, 2.0F, -5.0F));
		final ModelPartData body = main.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-6.0F, -8.0F, -11.0F, 12.0F, 16.0F, 16.0F, new Dilation(0.0F)).uv(44, 55).cuboid(0.0F, -18.0F, -11.0F, 0.0F, 10.0F, 16.0F, new Dilation(0.0F)).uv(44, 19).cuboid(0.0F, 8.0F, -11.0F, 0.0F, 10.0F, 16.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		final ModelPartData tail1 = body.addChild("tail1", ModelPartBuilder.create().uv(68, 73).cuboid(-4.0F, -7.0F, 0.0F, 8.0F, 14.0F, 8.0F, new Dilation(0.0F)).uv(76, 27).cuboid(0.0F, -17.0F, 0.0F, 0.0F, 10.0F, 8.0F, new Dilation(0.0F)).uv(0, 24).cuboid(0.0F, 7.0F, 0.0F, 0.0F, 10.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 5.0F));
		final ModelPartData tail2 = tail1.addChild("tail2", ModelPartBuilder.create().uv(12, 79).cuboid(-2.0F, -6.0F, 0.0F, 4.0F, 12.0F, 6.0F, new Dilation(0.0F)).uv(52, 0).cuboid(0.0F, -15.0F, 0.0F, 0.0F, 9.0F, 6.0F, new Dilation(0.0F)).uv(40, 0).cuboid(0.0F, 6.0F, 0.0F, 0.0F, 9.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 8.0F));
		final ModelPartData tail3 = tail2.addChild("tail3", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, -3.0F, 0.0F, 2.0F, 6.0F, 6.0F, new Dilation(0.0F)).uv(0, 73).cuboid(0.0F, -12.0F, 0.0F, 0.0F, 24.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 6.0F));
		final ModelPartData tail4 = tail3.addChild("tail4", ModelPartBuilder.create().uv(56, 75).cuboid(0.0F, -8.0F, 0.0F, 0.0F, 16.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 6.0F));
		final ModelPartData tail5 = tail4.addChild("tail5", ModelPartBuilder.create().uv(44, 75).cuboid(0.0F, -8.0F, 0.0F, 0.0F, 16.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 6.0F));
		tail5.addChild("tail6", ModelPartBuilder.create().uv(32, 73).cuboid(0.0F, -8.0F, 0.0F, 0.0F, 16.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 6.0F));
		body.addChild("leftFin", ModelPartBuilder.create().uv(0, 103).cuboid(0.0F, -2.0F, -0.5F, 1.0F, 4.0F, 5.0F, new Dilation(0.0F)).uv(0, 103).cuboid(0.5F, -6.0F, -0.5F, 0.0F, 12.0F, 13.0F, new Dilation(0.0F)), ModelTransform.of(6.0F, 0.0F, -4.5F, 0.0F, 0.5672F, 0.0F));
		body.addChild("rightFin", ModelPartBuilder.create().uv(0, 103).cuboid(-1.0F, -2.0F, -0.5F, 1.0F, 4.0F, 5.0F, new Dilation(0.0F)).uv(0, 103).cuboid(-0.5F, -6.0F, -0.5F, 0.0F, 12.0F, 13.0F, new Dilation(0.0F)), ModelTransform.of(-6.0F, 0.0F, -4.5F, 0.0F, -0.6981F, 0.0F));
		return TexturedModelData.of(modelData, 128, 128);
	}

	@Override
	public void render(MatrixStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		poseStack.push();
		poseStack.scale(1.5F, 1.5F, 1.5F);
		main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		poseStack.pop();
	}

	@Override
	public void setAngles(AnglerfishEntity anglerfish, float limbAngle, float limbDistance, float progress, float headYaw, float headPitch) {
		HekateLib.reset(main, head, headUpper, headLower, body, tail1, tail2, tail3, tail4, tail5, tail6, leftFin, rightFin, lamp1, lamp2, lamp3, lamp4, lamp5);
		HekateLib.push(progress, 0.1f, HekateLib.mod.idle(limbDistance, 5f), HekateLib.Mode.DEFINITION)
				.keyframe(main, k -> k.xRot(1F, -15F))
				.keyframe(head, k -> k.xRot(-1F, -35F))
				.keyframe(body, k -> k.yRot(5F, 0))
				.keyframe(tail1, k -> k.yRot(7F, 0, -0.05f))
				.keyframe(tail2, k -> k.yRot(9F, 0, -0.1f))
				.keyframe(tail3, k -> k.yRot(11F, 0, -0.15f))
				.keyframe(tail4, k -> k.yRot(13F, 0, -0.2f))
				.keyframe(tail5, k -> k.yRot(15F, 0, -0.25f))
				.keyframe(tail6, k -> k.yRot(15F, 0, -0.3f))
				.keyframe(leftFin, k -> k.yRot(15F, -40F, -0.1f).zRot(0, -30F, -0.1f))
				.keyframe(rightFin, k -> k.yRot(-15F, 40F, -0.1f).zRot(0, 30F, -0.1f))
				.keyframe(lamp1, k -> k.xRot(2F, -5F))
				.keyframe(lamp2, k -> k.xRot(4F, -10F, -0.05f))
				.keyframe(lamp3, k -> k.xRot(6F, -30F, -0.1f))
				.keyframe(lamp4, k -> k.xRot(8F, -50F, -0.15f))
				.keyframe(lamp5, k -> k.xRot(10F, -30F, -0.2f));
		HekateLib.push(progress, 0.7f, HekateLib.mod.move(limbDistance, 5f), HekateLib.Mode.ADDITION)
				.keyframe(main, k -> k.xRot(1F, -15F))
				.keyframe(head, k -> k.xRot(-1F, -35F).yRot(-10F, 0))
				.keyframe(body, k -> k.yRot(5F, 0))
				.keyframe(tail1, k -> k.yRot(10F, 0, -0.05f))
				.keyframe(tail2, k -> k.yRot(12.5F, 0, -0.1f))
				.keyframe(tail3, k -> k.yRot(15F, 0, -0.15f))
				.keyframe(tail4, k -> k.yRot(20F, 0, -0.2f))
				.keyframe(tail5, k -> k.yRot(25F, 0, -0.25f))
				.keyframe(tail6, k -> k.yRot(30F, 0, -0.3f))
				.keyframe(leftFin, k -> k.yRot(15F, -40F, -0.1f).zRot(0, -30F, -0.1f))
				.keyframe(rightFin, k -> k.yRot(-15F, 40F, -0.1f).zRot(0, 30F, -0.1f))
				.keyframe(lamp1, k -> k.xRot(2F, -5F))
				.keyframe(lamp2, k -> k.xRot(4F, -10F, -0.05f))
				.keyframe(lamp3, k -> k.xRot(6F, -30F, -0.1f))
				.keyframe(lamp4, k -> k.xRot(8F, -50F, -0.15f))
				.keyframe(lamp5, k -> k.xRot(10F, -30F, -0.2f));
		HekateLib.push(14, 10, Interpolations.EASE_OUT_CUBIC, Interpolations.EASE_OUT_CUBIC)
				.pose(0, 20, Interpolations.CEIL, progress, 0.7f, builder -> builder
						.keyframe(headUpper, k -> k.xRot(1F, 15F, 4, 0))
						.keyframe(headLower, k -> k.xRot(-1F, -15F, 4, 0))
						.keyframe(body, k -> k.yRot(10F, 0))
						.keyframe(tail1, k -> k.yRot(15F, 0, -0.05f))
						.keyframe(tail2, k -> k.yRot(20F, 0, -0.1f))
						.keyframe(tail3, k -> k.yRot(25F, 0, -0.15f))
						.keyframe(tail4, k -> k.yRot(30F, 0, -0.2f))
						.keyframe(tail5, k -> k.yRot(35F, 0, -0.25f))
						.keyframe(tail6, k -> k.yRot(40F, 0, -0.3f)))
				.pose(20, 40, Interpolations.EASE_OUT_CUBIC.scale(0.15f), progress, 0.7f, builder -> builder
						.keyframe(headUpper, k -> k.xRot(-25F))
						.keyframe(headLower, k -> k.xRot(25F)))
				.animate(anglerfish.ATTACK);

		final float groundMod = MathHelper.lerp(HekateLib.getPartialTicks(), anglerfish.groundModLerp, anglerfish.groundMod);
		final float headMod = 1F - groundMod;
		this.main.pivotY = 10 * groundMod;
		this.main.roll += (float) Math.toRadians(90f * groundMod);
		this.main.pitch += HekateLib.mod.head(headPitch, headMod);
		this.main.yaw += HekateLib.mod.head(headYaw, headMod);
		this.main.pitch += HekateLib.mod.head(headYaw, 1F - headMod);
	}
}
