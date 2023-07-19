package com.obscuria.aquamirae.client.models;

import com.obscuria.aquamirae.common.entities.MazeMotherEntity;
import com.obscuria.obscureapi.api.hekate.HekateLib;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class ModelMazeMother extends EntityModel<MazeMotherEntity> {
	public final ModelPart main, main2, bodyTop, bodyLower, tail1, tail2, tail3, tail4, tail5, jaw1, jaw2, jaw3, jaw4,
			wing1LeftUpper, wing1LeftLower, wing1RightUpper, wing1RightLower, wing2LeftUpper, wing2LeftLower, wing2RightUpper, wing2RightLower;

	public ModelMazeMother(ModelPart root) {
		this.main = root.getChild("main");
		this.main2 = main.getChild("main2");
		this.bodyTop = main2.getChild("body_top");
		this.bodyLower = main2.getChild("body_bottom");
		this.jaw1 = bodyTop.getChild("jaw1");
		this.jaw2 = bodyTop.getChild("jaw2");
		this.jaw3 = bodyTop.getChild("jaw3");
		this.jaw4 = bodyTop.getChild("jaw4");
		this.tail1 = bodyLower.getChild("tail1");
		this.tail2 = tail1.getChild("tail2");
		this.tail3 = tail2.getChild("tail3");
		this.tail4 = tail3.getChild("tail4");
		this.tail5 = tail4.getChild("tail5");
		this.wing1LeftUpper = bodyTop.getChild("left_wing1_top");
		this.wing1RightUpper = bodyTop.getChild("right_wing1_top");
		this.wing2LeftUpper = wing1LeftUpper.getChild("left_wing2_top");
		this.wing2RightUpper = wing1RightUpper.getChild("right_wing2_top");
		this.wing1LeftLower = bodyLower.getChild("left_wing1_bottom");
		this.wing1RightLower = bodyLower.getChild("right_wing1_bottom");
		this.wing2LeftLower = wing1LeftLower.getChild("left_wing2_bottom");
		this.wing2RightLower = wing1RightLower.getChild("right_wing2_bottom");
	}

	public static TexturedModelData createModelData() {
		final ModelData modelData = new ModelData();
		final ModelPartData root = modelData.getRoot();
		final ModelPartData main = root.addChild("main", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -126.0F, 0.0F));
		final ModelPartData main2 = main.addChild("main2", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 147.0F, 0.0F));
		final ModelPartData body_top = main2.addChild("body_top", ModelPartBuilder.create().uv(0, 45).cuboid(-8.0F, -1.5F, -16.0F, 16.0F, 3.0F, 16.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		body_top.addChild("jaw1", ModelPartBuilder.create().uv(37, 26).cuboid(-3.0F, 0.0F, -10.5F, 6.0F, 0.0F, 11.0F, new Dilation(0.0F)), ModelTransform.pivot(-7.0F, 0.0F, -14.5F));
		body_top.addChild("jaw2", ModelPartBuilder.create().uv(49, 26).cuboid(-3.0F, 0.0F, -10.5F, 6.0F, 0.0F, 11.0F, new Dilation(0.0F)), ModelTransform.pivot(7.0F, 0.0F, -14.5F));
		body_top.addChild("jaw3", ModelPartBuilder.create().uv(65, 26).cuboid(-1.5F, 0.0F, -5.5F, 3.0F, 0.0F, 7.0F, new Dilation(0.0F)), ModelTransform.pivot(-3.5F, 0.0F, -15.5F));
		body_top.addChild("jaw4", ModelPartBuilder.create().uv(73, 26).cuboid(-1.5F, 0.0F, -5.5F, 3.0F, 0.0F, 7.0F, new Dilation(0.0F)), ModelTransform.pivot(3.5F, 0.0F, -15.5F));
		final ModelPartData crystal1_top = body_top.addChild("crystal1_top", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, -16.0F));
		crystal1_top.addChild("cube_r1", ModelPartBuilder.create().uv(32, 58).cuboid(-2.0F, -10.5F, -16.0F, 0.0F, 10.0F, 16.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -2.0F, 16.0F, 0.0F, 0.0F, -0.7854F));
		final ModelPartData crystal2_top = body_top.addChild("crystal2_top", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, -16.0F));
		crystal2_top.addChild("cube_r2", ModelPartBuilder.create().uv(64, 45).cuboid(-1.0F, -9.5F, -16.0F, 0.0F, 10.0F, 16.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -2.0F, 16.0F, 0.0F, 0.0F, -0.3491F));
		final ModelPartData crystal3_top = body_top.addChild("crystal3_top", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, -16.0F));
		crystal3_top.addChild("cube_r3", ModelPartBuilder.create().uv(64, 65).cuboid(1.0F, -9.5F, -16.0F, 0.0F, 10.0F, 16.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -2.0F, 16.0F, 0.0F, 0.0F, 0.3491F));
		final ModelPartData crystal4_top = body_top.addChild("crystal4_top", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, -16.0F));
		crystal4_top.addChild("cube_r4", ModelPartBuilder.create().uv(32, 48).cuboid(2.0F, -10.5F, -16.0F, 0.0F, 10.0F, 16.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -2.0F, 16.0F, 0.0F, 0.0F, 0.7854F));
		final ModelPartData left_wing1_top = body_top.addChild("left_wing1_top", ModelPartBuilder.create().uv(48, 45).cuboid(-9.0F, -1.0F, -14.0F, 9.0F, 2.0F, 14.0F, new Dilation(0.0F)), ModelTransform.pivot(-8.0F, 0.0F, 0.0F));
		left_wing1_top.addChild("left_wing2_top", ModelPartBuilder.create().uv(92, 0).cuboid(-5.0F, -1.0F, -11.0F, 5.0F, 2.0F, 11.0F, new Dilation(0.0F)), ModelTransform.pivot(-9.0F, 0.0F, 0.0F));
		final ModelPartData right_wing1_top = body_top.addChild("right_wing1_top", ModelPartBuilder.create().uv(60, 0).cuboid(0.0F, -1.0F, -14.0F, 9.0F, 2.0F, 14.0F, new Dilation(0.0F)), ModelTransform.pivot(8.0F, 0.0F, 0.0F));
		right_wing1_top.addChild("right_wing2_top", ModelPartBuilder.create().uv(47, 91).cuboid(0.0F, -1.0F, -11.0F, 5.0F, 2.0F, 11.0F, new Dilation(0.0F)), ModelTransform.pivot(9.0F, 0.0F, 0.0F));
		final ModelPartData body_bottom = main2.addChild("body_bottom", ModelPartBuilder.create().uv(0, 26).cuboid(-8.0F, 0.5F, 0.0F, 16.0F, 3.0F, 16.0F, new Dilation(0.0F)).uv(72, 26).cuboid(-1.5F, -0.5F, 0.0F, 3.0F, 3.0F, 16.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -2.0F, 0.0F));
		final ModelPartData crystal1_bottom = body_bottom.addChild("crystal1_bottom", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 2.0F, 0.0F));
		crystal1_bottom.addChild("cube_r5", ModelPartBuilder.create().uv(0, 58).cuboid(-2.0F, -10.5F, 0.0F, 0.0F, 10.0F, 16.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -2.0F, 0.0F, 0.0F, 0.0F, -0.7854F));
		final ModelPartData crystal2_bottom = body_bottom.addChild("crystal2_bottom", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 2.0F, 0.0F));
		crystal2_bottom.addChild("cube_r6", ModelPartBuilder.create().uv(60, 0).cuboid(-1.0F, -9.5F, 0.0F, 0.0F, 10.0F, 16.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -2.0F, 0.0F, 0.0F, 0.0F, -0.3491F));
		final ModelPartData crystal3_bottom = body_bottom.addChild("crystal3_bottom", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 2.0F, 0.0F));
		crystal3_bottom.addChild("cube_r7", ModelPartBuilder.create().uv(64, 55).cuboid(1.0F, -9.5F, 0.0F, 0.0F, 10.0F, 16.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -2.0F, 0.0F, 0.0F, 0.0F, 0.3491F));
		final ModelPartData crystal4_bottom = body_bottom.addChild("crystal4_bottom", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 2.0F, 0.0F));
		crystal4_bottom.addChild("cube_r8", ModelPartBuilder.create().uv(0, 48).cuboid(2.0F, -10.5F, 0.0F, 0.0F, 10.0F, 16.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -2.0F, 0.0F, 0.0F, 0.0F, 0.7854F));
		final ModelPartData left_wing1_bottom = body_bottom.addChild("left_wing1_bottom", ModelPartBuilder.create().uv(80, 45).cuboid(-9.0F, -1.0F, 0.0F, 9.0F, 2.0F, 10.0F, new Dilation(0.0F)), ModelTransform.pivot(-8.0F, 2.0F, 0.0F));
		left_wing1_bottom.addChild("left_wing2_bottom", ModelPartBuilder.create().uv(0, 0).cuboid(-5.0F, -1.0F, 0.0F, 5.0F, 2.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(-9.0F, 0.0F, 0.0F));
		final ModelPartData right_wing1_bottom = body_bottom.addChild("right_wing1_bottom", ModelPartBuilder.create().uv(20, 84).cuboid(0.0F, -1.0F, 0.0F, 9.0F, 2.0F, 10.0F, new Dilation(0.0F)), ModelTransform.pivot(8.0F, 2.0F, 0.0F));
		right_wing1_bottom.addChild("right_wing2_bottom", ModelPartBuilder.create().uv(0, 7).cuboid(0.0F, -1.0F, 0.0F, 5.0F, 2.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(9.0F, 0.0F, 0.0F));
		final ModelPartData tail1 = body_bottom.addChild("tail1", ModelPartBuilder.create().uv(96, 57).cuboid(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 9.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 1.0F, 16.0F));
		final ModelPartData tail2 = tail1.addChild("tail2", ModelPartBuilder.create().uv(98, 69).cuboid(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 7.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 9.0F));
		final ModelPartData tail3 = tail2.addChild("tail3", ModelPartBuilder.create().uv(0, 87).cuboid(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 9.0F, new Dilation(0.0F)).uv(22, 22).cuboid(-8.5F, 0.0F, 5.0F, 17.0F, 0.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 7.0F));
		final ModelPartData tail4 = tail3.addChild("tail4", ModelPartBuilder.create().uv(2, 98).cuboid(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 7.0F, new Dilation(0.0F)).uv(19, 15).cuboid(-8.5F, 0.0F, 0.0F, 17.0F, 0.0F, 7.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 9.0F));
		tail4.addChild("tail5", ModelPartBuilder.create().uv(11, 0).cuboid(-8.5F, 0.0F, 0.0F, 17.0F, 0.0F, 15.0F, new Dilation(0.0F)).uv(0, 14).cuboid(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 7.0F));
		return TexturedModelData.of(modelData, 128, 128);
	}

	@Override
	public void render(MatrixStack stack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		stack.push();
		stack.scale(5f, 5f, 5f);
		stack.translate(0F, -1F, 0F);
		main.render(stack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		stack.pop();
	}

	@Override
	public void setAngles(MazeMotherEntity entity, float limbAngle, float limbDistance, float progress, float headYaw, float headPitch) {
		HekateLib.reset(main, main2, bodyTop, bodyLower, tail1, tail2, tail3, tail4, tail5, wing1LeftUpper, wing1LeftLower,
				wing1RightUpper, wing1RightLower, wing2LeftUpper, wing2LeftLower, wing2RightUpper, wing2RightLower, jaw1, jaw2, jaw3, jaw4);
		HekateLib.push(progress, 0.1f, 1f, HekateLib.Mode.DEFINITION)
				.keyframe(bodyTop, k -> k.xRot(-10F, 0))
				.keyframe(bodyLower, k -> k.xRot(-10F, 0, -0.1f))
				.keyframe(tail1, k -> k.xRot(-10F, 0, -0.2f))
				.keyframe(tail2, k -> k.xRot(-10F, 0, -0.3f))
				.keyframe(tail3, k -> k.xRot(-10F, 0, -0.4f))
				.keyframe(tail4, k -> k.xRot(-10F, 0, -0.5f))
				.keyframe(tail5, k -> k.xRot(-10F, 0, -0.6f))
				.keyframe(wing1RightUpper, k -> k.zRot(15F, 0, -0.1f))
				.keyframe(wing1LeftUpper, k -> k.zRot(-15F, 0, -0.1f))
				.keyframe(wing2RightUpper, k -> k.zRot(15F, 0, -0.2f))
				.keyframe(wing2LeftUpper, k -> k.zRot(-15F, 0, -0.2f));

		this.wing1RightLower.roll = this.wing1RightUpper.roll;
		this.wing1LeftLower.roll = this.wing1LeftUpper.roll;
		this.wing2RightLower.roll = this.wing2RightUpper.roll;
		this.wing2LeftLower.roll = this.wing2LeftUpper.roll;

		HekateLib.math.i(jaw1, 0, 0, 10F, 10F, 0, 0, 0.4f, 0F, progress, HekateLib.math.cycle(progress, 0.02F, -1F));
		HekateLib.math.i(jaw2, 0, 0, -10F, -10F, 0, 0, 0.4f, 0F, progress, HekateLib.math.cycle(progress, 0.02F, -1F));
		HekateLib.math.i(jaw3, 0, 0, 15F, 5F, 0, 0, 0.6f, 0F, progress, HekateLib.math.cycle(progress, 0.02F, 0F));
		HekateLib.math.i(jaw4, 0, 0, -15F, -5F, 0, 0, 0.6f, 0F, progress, HekateLib.math.cycle(progress, 0.02F, 0F));
	}
}
