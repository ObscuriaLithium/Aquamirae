package com.obscuria.aquamirae.client.models;

import com.obscuria.aquamirae.common.entities.EelEntity;
import com.obscuria.obscureapi.api.hekate.Animation;
import com.obscuria.obscureapi.api.hekate.HekateLib;
import com.obscuria.obscureapi.api.hekate.Interpolations;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class ModelEel extends EntityModel<EelEntity> {
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

	public static TexturedModelData createModelData() {
		final ModelData modelData = new ModelData();
		final ModelPartData root = modelData.getRoot();
		final ModelPartData main = root.addChild("main", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 300.0F));
		final ModelPartData body1 = main.addChild("body1", ModelPartBuilder.create(), ModelTransform.of(0.0F, 0.0F, -300.0F, 0.5672F, 0.0F, 0.0F));
		final ModelPartData body2 = body1.addChild("body2", ModelPartBuilder.create(), ModelTransform.of(0.0F, -5.0F, 0.0F, -0.3491F, 0.0F, 0.0F));
		final ModelPartData body3 = body2.addChild("body3", ModelPartBuilder.create(), ModelTransform.of(0.0F, -6.0F, 0.0F, -0.6109F, 0.0F, 0.0F));
		final ModelPartData body4 = body3.addChild("body4", ModelPartBuilder.create(), ModelTransform.of(0.0F, -6.0F, 0.0F, -0.5236F, 0.0F, 0.0F));
		final ModelPartData body5 = body4.addChild("body5", ModelPartBuilder.create(), ModelTransform.of(0.0F, -6.0F, 0.0F, 0.5236F, 0.0F, 0.0F));
		final ModelPartData body6 = body5.addChild("body6", ModelPartBuilder.create(), ModelTransform.of(0.0F, -7.0F, 0.0F, 0.5672F, 0.0F, 0.0F));
		final ModelPartData body7 = body6.addChild("body7", ModelPartBuilder.create(), ModelTransform.of(0.0F, -5.0F, 0.0F, 0.6109F, 0.0F, 0.0F));
		final ModelPartData body8 = body7.addChild("body8", ModelPartBuilder.create(), ModelTransform.of(0.0F, -6.0F, 0.0F, 0.5236F, 0.0F, 0.0F));
		final ModelPartData body9 = body8.addChild("body9", ModelPartBuilder.create(), ModelTransform.of(0.0F, -6.0F, 0.0F, 0.3927F, 0.0F, 0.0F));
		final ModelPartData body10 = body9.addChild("body10", ModelPartBuilder.create(), ModelTransform.of(0.0F, -6.0F, 0.0F, 0.2182F, 0.0F, 0.0F));
		final ModelPartData head = body10.addChild("head", ModelPartBuilder.create(), ModelTransform.of(0.0F, -3.0F, 0.0F, 0.2182F, 0.0F, 0.0F));
		final ModelPartData headTop = head.addChild("headTop", ModelPartBuilder.create(), ModelTransform.of(0.0F, -11.0F, -6.0F, 0.6981F, 0.0F, 0.0F));
		headTop.addChild("bone3", ModelPartBuilder.create().uv(84, 61).cuboid(-6.0F, -42.0F, -6.0F, 12.0F, 8.0F, 12.0F, new Dilation(0.0F)).uv(12, 0).cuboid(6.0F, -40.0F, -3.0F, 1.0F, 4.0F, 4.0F, new Dilation(0.0F)).uv(12, 8).cuboid(-3.0F, -40.0F, 6.0F, 4.0F, 4.0F, 1.0F, new Dilation(0.0F)).uv(84, 82).cuboid(-6.0F, -46.5F, -6.0F, 12.0F, 5.0F, 12.0F, new Dilation(-0.5F)).uv(84, 82).cuboid(-7.0F, -46.0F, -7.0F, 12.0F, 5.0F, 12.0F, new Dilation(-1.0F)), ModelTransform.of(0.0F, 42.5507F, 4.774F, 0.0F, -0.7854F, 0.0F));
		final ModelPartData headBottom = head.addChild("headBottom", ModelPartBuilder.create(), ModelTransform.of(0.0F, -11.0F, -6.0F, 2.0944F, 0.0F, 0.0F));
		headBottom.addChild("bone5", ModelPartBuilder.create().uv(12, 23).cuboid(-9.0F, -42.0F, -9.0F, 15.0F, 2.0F, 15.0F, new Dilation(0.0F)).uv(12, 0).cuboid(-9.0F, -40.5F, -9.0F, 15.0F, 8.0F, 15.0F, new Dilation(-0.5F)).uv(12, 0).cuboid(-10.0F, -41.0F, -10.0F, 15.0F, 8.0F, 15.0F, new Dilation(-1.0F)), ModelTransform.of(0.0F, 40.2191F, 4.5237F, 0.0F, -0.7854F, 0.0F));
		final ModelPartData bone2 = head.addChild("bone2", ModelPartBuilder.create().uv(12, 40).cuboid(-6.0F, -45.0F, -6.0F, 12.0F, 12.0F, 12.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 35.0F, 0.0F, 0.0F, -0.7854F, 0.0F));
		bone2.addChild("bone11", ModelPartBuilder.create().uv(169, -8).cuboid(0.0F, -8.0F, 7.2426F, 0.0F, 15.0F, 9.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -40.0F, 0.0F, 0.0F, 0.7854F, 0.0F));
		bone2.addChild("bone13", ModelPartBuilder.create().uv(169, -8).cuboid(0.0F, -8.0F, 7.2426F, 0.0F, 15.0F, 9.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -40.0F, 0.0F, 0.0F, 2.3562F, 0.0F));
		bone2.addChild("bone15", ModelPartBuilder.create().uv(169, -8).cuboid(0.0F, -8.0F, 7.2426F, 0.0F, 15.0F, 9.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -40.0F, 0.0F, 0.0F, -0.7854F, 0.0F));
		final ModelPartData leftFinP = head.addChild("leftFinP", ModelPartBuilder.create(), ModelTransform.of(4.0F, -7.0F, -4.0F, 0.0F, 0.7854F, 0.0F));
		leftFinP.addChild("leftFin", ModelPartBuilder.create().uv(137, -9).cuboid(0.5F, -1.0F, -5.0F, 0.0F, 18.0F, 10.0F, new Dilation(0.0F)).uv(158, 1).cuboid(0.0F, -1.0F, -2.0F, 1.0F, 8.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.9599F));
		final ModelPartData rightFinP = head.addChild("rightFinP", ModelPartBuilder.create(), ModelTransform.of(-5.0F, -7.0F, -4.0F, 0.0F, -0.7854F, 0.0F));
		rightFinP.addChild("rightFin", ModelPartBuilder.create().uv(137, -9).cuboid(0.5F, -1.0F, -5.0F, 0.0F, 18.0F, 10.0F, new Dilation(0.0F)).uv(158, 1).cuboid(0.0F, -1.0F, -2.0F, 1.0F, 8.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0908F));
		final ModelPartData bone20 = body10.addChild("bone20", ModelPartBuilder.create().uv(48, 52).cuboid(-6.0F, -44.0F, -6.0F, 12.0F, 9.0F, 12.0F, new Dilation(-0.1F)), ModelTransform.of(0.0F, 40.0F, 0.0F, 0.0F, -0.7854F, 0.0F));
		bone20.addChild("bone17", ModelPartBuilder.create().uv(170, -8).cuboid(0.0F, 3.0F, 5.0F, 0.0F, 10.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -47.0F, 0.0F, 0.0F, 0.7854F, 0.0F));
		bone20.addChild("bone19", ModelPartBuilder.create().uv(170, -8).cuboid(0.0F, 3.0F, 5.0F, 0.0F, 10.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -47.0F, 0.0F, 0.0F, 2.3562F, 0.0F));
		bone20.addChild("bone21", ModelPartBuilder.create().uv(170, -8).cuboid(0.0F, 3.0F, 5.0F, 0.0F, 10.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -47.0F, 0.0F, 0.0F, -0.7854F, 0.0F));
		final ModelPartData bone18 = body9.addChild("bone18", ModelPartBuilder.create().uv(60, 11).cuboid(-6.0F, -34.0F, -6.0F, 12.0F, 9.0F, 12.0F, new Dilation(-0.15F)), ModelTransform.of(0.0F, 30.0F, 0.0F, 0.0F, -0.7854F, 0.0F));
		bone18.addChild("bone22", ModelPartBuilder.create().uv(170, -8).cuboid(0.0F, 2.0F, 3.0F, 0.0F, 10.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -37.0F, 0.0F, 0.0F, 0.7854F, 0.0F));
		bone18.addChild("bone23", ModelPartBuilder.create().uv(170, -8).cuboid(0.0F, 2.0F, 3.0F, 0.0F, 10.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -37.0F, 0.0F, 0.0F, 2.3562F, 0.0F));
		bone18.addChild("bone24", ModelPartBuilder.create().uv(170, -8).cuboid(0.0F, 2.0F, 3.0F, 0.0F, 10.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -37.0F, 0.0F, 0.0F, -0.7854F, 0.0F));
		body8.addChild("bone16", ModelPartBuilder.create().uv(12, 64).cuboid(-6.0F, -24.0F, -6.0F, 12.0F, 9.0F, 12.0F, new Dilation(-0.2F)), ModelTransform.of(0.0F, 20.0F, 0.0F, 0.0F, -0.7854F, 0.0F));
		body7.addChild("bone14", ModelPartBuilder.create().uv(48, 73).cuboid(-6.0F, -4.0F, -6.0F, 12.0F, 9.0F, 12.0F, new Dilation(-0.25F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));
		body6.addChild("bone12", ModelPartBuilder.create().uv(84, 32).cuboid(-6.0F, -3.0F, -6.0F, 12.0F, 9.0F, 12.0F, new Dilation(-0.3F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));
		body5.addChild("bone10", ModelPartBuilder.create().uv(2, 85).cuboid(-5.0F, -44.0F, -5.0F, 10.0F, 8.0F, 10.0F, new Dilation(0.2F)), ModelTransform.of(0.0F, 40.0F, 0.0F, 0.0F, -0.7854F, 0.0F));
		body4.addChild("bone8", ModelPartBuilder.create().uv(32, 94).cuboid(-5.0F, -34.0F, -5.0F, 10.0F, 8.0F, 10.0F, new Dilation(0.15F)), ModelTransform.of(0.0F, 30.0F, 0.0F, 0.0F, -0.7854F, 0.0F));
		body3.addChild("bone6", ModelPartBuilder.create().uv(96, 0).cuboid(-5.0F, -24.0F, -5.0F, 10.0F, 8.0F, 10.0F, new Dilation(0.1F)), ModelTransform.of(0.0F, 20.0F, 0.0F, 0.0F, -0.7854F, 0.0F));
		body2.addChild("bone4", ModelPartBuilder.create().uv(72, 99).cuboid(-5.0F, -4.0F, -5.0F, 10.0F, 8.0F, 10.0F, new Dilation(0.05F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));
		body1.addChild("bone", ModelPartBuilder.create().uv(0, 103).cuboid(-5.0F, -3.0F, -5.0F, 10.0F, 8.0F, 10.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));
		return TexturedModelData.of(modelData, 256, 256);
	}

	@Override
	public void render(MatrixStack stack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		stack.push();
		stack.scale(1.8f, 1.8f, 1.8f);
		stack.translate(0F, -0.7F, 0F);
		main.render(stack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		stack.pop();
	}

	@Override
	public void setAngles(EelEntity eel, float limbAngle, float limbDistance, float progress, float headYaw, float headPitch) {
		HekateLib.reset(main, body1, body2, body3, body4, body5, body6, body7, body8, body9, body10, head, headUpper, headLower, leftFin, rightFin);
		HekateLib.push(progress, 0.1f, 1, HekateLib.Mode.DEFINITION)
				.keyframe(body1, k -> k.xRot(-1F, -17.5F, -0.95F))
				.keyframe(body2, k -> k.xRot(1F, 20F, -0.9F))
				.keyframe(body3, k -> k.xRot(-1F, 22.5F, -0.85F))
				.keyframe(body4, k -> k.xRot(-1F, 20F, -0.8F))
				.keyframe(body5, k -> k.xRot(-2F, -22.5F, -0.75F))
				.keyframe(body6, k -> k.xRot(-2F, -22.5F, -0.7F))
				.keyframe(body7, k -> k.xRot(-2F, -27.5F, -0.65F))
				.keyframe(body8, k -> k.xRot(-2F, -25F, -0.6F))
				.keyframe(body9, k -> k.xRot(-3F, -27.5F, -0.55F))
				.keyframe(body10, k -> k.xRot(-3F, -22.5F, -0.5F))
				.keyframe(head, k -> k.xRot(-3F, -22.5F))
				.keyframe(headUpper, k -> k.xRot(-40F))
				.keyframe(headLower, k -> k.xRot(-10F, -105F))
				.keyframe(leftFin, k -> k.zRot(10F, -45F))
				.keyframe(rightFin, k -> k.zRot(-10F, 45F));
		HekateLib.push(60, 60, Interpolations.EASE_IN_OUT_BACK, Interpolations.EASE_IN_OUT_BACK)
				.pose(0, 300, Interpolations.CEIL, progress, 0.14f, builder -> builder
						.keyframe(body5, k -> k.rotation(-2F, -22.5F, 1, 0, 0, 0, 2, -0.95f))
						.keyframe(body6, k -> k.rotation(-2F, -22.5F, 2, 0, 0, 0, -0.9f))
						.keyframe(body7, k -> k.rotation(-2F, -27.5F, 3, 0, 0, 0, -0.85f))
						.keyframe(body8, k -> k.rotation(-2F, -25F, 4, 0, 0, 0, -0.8f))
						.keyframe(body9, k -> k.rotation(-3F, -27.5F, 5, 0, 0, 0, -0.75f))
						.keyframe(body10, k -> k.rotation(-3F, -22.5F, 6, 0, 0, 0, -0.7f))
						.keyframe(head, k -> k.rotation(-3F, -22.5F, 7, 0, 0, 0, -0.65f))
						.keyframe(headLower, k -> k.xRot(HekateLib.math.cycle(progress, 0.1f) * 8, -105, 10, 0)))
				.animate(eel.RARE_IDLE);
		HekateLib.push(12, 12, Interpolations.LINEAR, Interpolations.EASE_OUT_CUBIC)
				.pose(0, 12, Interpolations.CEIL, progress, 1f, builder -> builder
						.keyframe(body1, k -> k.xRot(-17.5F))
						.keyframe(body2, k -> k.xRot(20F))
						.keyframe(body3, k -> k.xRot(22.5F))
						.keyframe(body4, k -> k.xRot(27.5F))
						.keyframe(body5, k -> k.xRot(-2.5F))
						.keyframe(body6, k -> k.xRot(-2.5F))
						.keyframe(body7, k -> k.xRot(-30F))
						.keyframe(body8, k -> k.xRot(-30F))
						.keyframe(body9, k -> k.xRot(-35F))
						.keyframe(body10, k -> k.xRot(-25F))
						.keyframe(head, k -> k.xRot(-25F))
						.keyframe(headUpper, k -> k.xRot(-40F))
						.keyframe(headLower, k -> k.rotation(10F, -170F, 0F, 0F, 0F, 0F, 1.4F, 0F))
						.keyframe(leftFin, k -> k.zRot(-90F))
						.keyframe(rightFin, k -> k.zRot(90F)))
				.pose(12, 30, Interpolations.EASE_IN_OUT_CUBIC.scale(0.2f), progress, 1f, builder -> builder
						.keyframe(body1, k -> k.xRot(-17.5F))
						.keyframe(body2, k -> k.xRot(10F).scale(1.025f))
						.keyframe(body3, k -> k.xRot(15F).scale(1.025f))
						.keyframe(body4, k -> k.xRot(12.5F).scale(1.025f))
						.keyframe(body5, k -> k.xRot(-35F).yRot(-1F).scale(1.025f))
						.keyframe(body6, k -> k.xRot(-30F).yRot(-1F).scale(1.025f))
						.keyframe(body7, k -> k.xRot(-35F).yRot(-2F).scale(1.025f))
						.keyframe(body8, k -> k.xRot(-25F).yRot(-2F).scale(1.025f))
						.keyframe(body9, k -> k.xRot(-12.5F).yRot(-3F).scale(1.025f))
						.keyframe(body10, k -> k.xRot(-2.5F).yRot(-3F).scale(1.025f))
						.keyframe(head, k -> k.xRot(-20F).yRot(-4F).scale(1.025f))
						.keyframe(headUpper, k -> k.xRot(-40F))
						.keyframe(headLower, k -> k.xRot(-75F))
						.keyframe(leftFin, k -> k.zRot(-40))
						.keyframe(rightFin, k -> k.zRot(40)))
				.animate(eel.ATTACK);
		HekateLib.push(12, 48, Interpolations.EASE_OUT_BACK, Interpolations.EASE_IN_OUT_BACK)
				.pose(0, 12, Interpolations.CEIL, progress, 1f, builder -> builder
						.keyframe(body1, k -> k.xRot(-17.5F))
						.keyframe(body2, k -> k.xRot(20F))
						.keyframe(body3, k -> k.xRot(22.5F))
						.keyframe(body4, k -> k.xRot(27.5F))
						.keyframe(body5, k -> k.xRot(-2.5F))
						.keyframe(body6, k -> k.rotation(0F, -2.5F, 0F, 0F, 5F, 0F, 0.4F, -0.95F))
						.keyframe(body7, k -> k.rotation(0F, -30F, 0F, 0F, 5F, 0F, 0.4F, -0.90F))
						.keyframe(body8, k -> k.rotation(0F, -30F, 0F, 0F, 5F, 0F, 0.4F, -0.85F))
						.keyframe(body9, k -> k.rotation(0F, -35F, 0F, 0F, 5F, 0F, 0.4F, -0.80F))
						.keyframe(body10, k -> k.rotation(0F, -25F, 0F, 0F, 5F, 0F, 0.4F, -0.75F))
						.keyframe(head, k -> k.rotation(0F, -25F, 0F, 0F, 5F, 0F, 0.4F, -0.70F))
						.keyframe(headUpper, k -> k.xRot(-40F))
						.keyframe(headLower, k -> k.rotation(5F, -90F, 0F, 0F, 0F, 0F, 1.4F, 0F))
						.keyframe(leftFin, k -> k.zRot(-90F))
						.keyframe(rightFin, k -> k.zRot(90F)))
				.pose(12, 100, Interpolations.EASE_IN_OUT_CUBIC.scale(0.05f), progress, 1f, builder -> builder
						.keyframe(body1, k -> k.xRot(-17.5F))
						.keyframe(body2, k -> k.xRot(12.5F))
						.keyframe(body3, k -> k.xRot(25F))
						.keyframe(body4, k -> k.xRot(22.5F))
						.keyframe(body5, k -> k.xRot(-30F))
						.keyframe(body6, k -> k.rotation(0F, -32.5F, 0F, 0F, 4F, 0F, 0.4F, -0.95F))
						.keyframe(body7, k -> k.rotation(0F, -27.5F, 0F, 0F, 4F, 0F, 0.4F, -0.90F))
						.keyframe(body8, k -> k.rotation(0F, -27.5F, 0F, 0F, 4F, 0F, 0.4F, -0.85F))
						.keyframe(body9, k -> k.rotation(0F, -22.5F, 0F, 0F, 4F, 0F, 0.4F, -0.80F))
						.keyframe(body10, k -> k.rotation(0F, -7.5F, 0F, 0F, 4F, 0F, 0.4F, -0.75F))
						.keyframe(head, k -> k.rotation(0F, -12.5F, 0F, 0F, 4F, 0F, 0.4F, -0.70F))
						.keyframe(headUpper, k -> k.xRot(-40F))
						.keyframe(headLower, k -> k.rotation(10F, -145F, 0F, 0F, 0F, 0F, 1.5F, 0F))
						.keyframe(leftFin, k -> k.zRot(-10, -80F, 2, 0))
						.keyframe(rightFin, k -> k.zRot(10, 80F, 2, 0)))
				.animate(eel.ROAR);
		HekateLib.push(20, 0, Interpolations.EASE_OUT_CUBIC.scale(0.8f), Interpolations.CEIL)
				.pose(0, 20, Interpolations.CEIL, progress, 1f, builder -> builder
						.keyframe(body1, k -> k.xRot(-17.5F))
						.keyframe(body2, k -> k.xRot(20F))
						.keyframe(body3, k -> k.xRot(22.5F))
						.keyframe(body4, k -> k.xRot(27.5F))
						.keyframe(body5, k -> k.xRot(-2.5F))
						.keyframe(body6, k -> k.rotation(0F, -2.5F, 0F, 0F, 5F, 0F, 0.4F, -0.95F))
						.keyframe(body7, k -> k.rotation(0F, -30F, 0F, 0F, 10F, 0F, 0.4F, -0.90F))
						.keyframe(body8, k -> k.rotation(0F, -30F, 0F, 0F, 15F, 0F, 0.4F, -0.85F))
						.keyframe(body9, k -> k.rotation(0F, -35F, 0F, 0F, 20F, 0F, 0.4F, -0.80F))
						.keyframe(body10, k -> k.rotation(0F, -25F, 0F, 0F, 20F, 0F, 0.4F, -0.75F))
						.keyframe(head, k -> k.rotation(0F, -25F, 0F, 0F, 20F, 0F, 0.4F, -0.70F))
						.keyframe(headUpper, k -> k.xRot(-40F))
						.keyframe(headLower, k -> k.rotation(5F, -90F, 0F, 0F, 0F, 0F, 1.4F, 0F))
						.keyframe(leftFin, k -> k.zRot(-90F))
						.keyframe(rightFin, k -> k.zRot(90F)))
				.pose(20, 60, Interpolations.EASE_OUT_BOUNCE.scale(0.8f), progress, 1f, builder -> builder
						.keyframe(body1, k -> k.xRot(-17.5F))
						.keyframe(body2, k -> k.xRot(-30))
						.keyframe(body3, k -> k.xRot(-30))
						.keyframe(body4, k -> k.xRot(-20))
						.keyframe(body5, k -> k.xRot(-10))
						.keyframe(body6, k -> k.rotation(10, 0, 10))
						.keyframe(body7, k -> k.rotation(5, 0, 10))
						.keyframe(body8, k -> k.rotation(4, 0, 10))
						.keyframe(body9, k -> k.rotation(3, 0, 10))
						.keyframe(body10, k -> k.rotation(2, 0, 10))
						.keyframe(head, k -> k.rotation(1, 0, 10))
						.keyframe(headUpper, k -> k.xRot(-40F))
						.keyframe(headLower, k -> k.rotation(-100, 0, 0))
						.keyframe(leftFin, k -> k.zRot(0))
						.keyframe(rightFin, k -> k.zRot(0)))
				.animate(eel.DEATH);
		this.animateMove(eel.MOVE, progress);
		this.rotateHead(headYaw);
	}

	private void animateMove(Animation move, float ageInTicks) {
		this.main.visible = !(move.getTick() >= 24 && move.getTick() <= 70);
		HekateLib.push(30, 30, Interpolations.EASE_IN_QUINT.scale(0.8f), Interpolations.EASE_OUT_CUBIC)
				.pose(0, 30, Interpolations.CEIL, ageInTicks, 1f, builder -> builder
						.keyframe(main, k -> k.rotation(-20F, 0F, 0F).scale(0.8f)))
				.pose(70, 100, Interpolations.EASE_OUT_CUBIC, ageInTicks, 1f, builder -> builder
						.keyframe(main, k -> k.rotation(0F, 0F, 0F).scale(1f)))
				.animate(move);
		HekateLib.push(20, 20, Interpolations.EASE_IN_QUINT, Interpolations.EASE_OUT_CUBIC)
				.pose(0, 30, Interpolations.CEIL, ageInTicks, 0.4f, builder -> builder
						.keyframe(body2, k -> k.rotation(-1F, 0F, 0F, 0F, 1F, 0F, -0.90F))
						.keyframe(body3, k -> k.rotation(-1F, 0F, 0F, 0F, 1F, 0F, -0.85F))
						.keyframe(body4, k -> k.rotation(-1F, 0F, 0F, 0F, 1F, 0F, -0.80F))
						.keyframe(body5, k -> k.rotation(-1F, 0F, 0F, 0F, 1F, 0F, -0.75F))
						.keyframe(body6, k -> k.rotation(-1F, 0F, 0F, 0F, 1F, 0F, -0.75F))
						.keyframe(body7, k -> k.rotation(-1F, 0F, 0F, 0F, 1F, 0F, -0.65F))
						.keyframe(body8, k -> k.rotation(-1F, 0F, 0F, 0F, 1F, 0F, -0.60F))
						.keyframe(body9, k -> k.rotation(-1F, 0F, 0F, 0F, 1F, 0F, -0.55F))
						.keyframe(body10, k -> k.rotation(-1F, 0F, 0F, 0F, 1F, 0F, -0.50F))
						.keyframe(head, k -> k.rotation(-1F, 0F, 0F, 0F, 1F, 0F, -0.50F))
						.keyframe(headUpper, k -> k.rotation(-40F, 0F, 0F))
						.keyframe(headLower, k -> k.rotation(5F, -90F, 0F, 0F, 0F, 0F, 3f, 0))
						.keyframe(leftFin, k -> k.rotation(0F, 0F, -90F))
						.keyframe(rightFin, k -> k.rotation(0F, 0F, 90F)))
				.pose(70, 100, Interpolations.EASE_OUT_CUBIC, ageInTicks, 0.4f, builder -> builder
						.keyframe(body2, k -> k.rotation(-3F, 0F, 0F, 0F, 3F, 0F, -0.90F))
						.keyframe(body3, k -> k.rotation(-3F, 0F, 0F, 0F, 3F, 0F, -0.85F))
						.keyframe(body4, k -> k.rotation(-3F, 0F, 0F, 0F, 3F, 0F, -0.80F))
						.keyframe(body5, k -> k.rotation(-3F, 0F, 0F, 0F, 3F, 0F, -0.75F))
						.keyframe(body6, k -> k.rotation(-3F, 0F, 0F, 0F, 3F, 0F, -0.75F))
						.keyframe(body7, k -> k.rotation(-3F, 0F, 0F, 0F, 3F, 0F, -0.65F))
						.keyframe(body8, k -> k.rotation(-3F, 0F, 0F, 0F, 3F, 0F, -0.60F))
						.keyframe(body9, k -> k.rotation(-3F, 0F, 0F, 0F, 3F, 0F, -0.55F))
						.keyframe(body10, k -> k.rotation(-3F, 0F, 0F, 0F, 3F, 0F, -0.50F))
						.keyframe(head, k -> k.rotation(-1F, 0F, 0F, 0F, 1F, 0F, -0.50F))
						.keyframe(headUpper, k -> k.rotation(-40F, 0F, 0F))
						.keyframe(headLower, k -> k.rotation(5F, -90F, 0F, 0F, 0F, 0F, 3f, 0))
						.keyframe(leftFin, k -> k.rotation(0F, 0F, -90F))
						.keyframe(rightFin, k -> k.rotation(0F, 0F, 90F)))
				.animate(move);
	}

	private void rotateHead(float netHeadYaw) {
		final float head = HekateLib.mod.head(netHeadYaw, 0.1F);
		this.body1.yaw += head;
		this.body2.yaw += head;
		this.body3.yaw += head;
		this.body4.yaw += head;
		this.body5.yaw += head;
		this.body6.yaw += head;
		this.body7.yaw += head;
		this.body8.yaw += head;
		this.body9.yaw += head;
		this.body10.yaw += head;
	}
}
