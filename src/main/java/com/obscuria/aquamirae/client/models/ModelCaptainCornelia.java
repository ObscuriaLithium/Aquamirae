package com.obscuria.aquamirae.client.models;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.obscuria.obscureapi.client.animations.AnimatedPart;
import com.obscuria.obscureapi.client.animations.HekateLib;
import com.obscuria.obscureapi.client.animations.KeyFrame;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.HandSide;

import javax.annotation.Nonnull;

public class ModelCaptainCornelia<T extends Entity> extends EntityModel<T> {

	public final ModelRenderer main, bodyTopZ, bodyBottomZ, bodyTop, bodyTop2, bodyBottom, head, rightBooby, leftBooby, rightArm, leftArm, rightLeg,
			leftLeg, rightArmBottom, leftArmBottom, rightLegBottom, leftLegBottom, item, ten1, ten1_1, ten1_2, ten1_3, ten1_4, ten2, ten2_1, ten2_2,
			ten2_3, ten2_4, ten3, ten3_1, ten3_2, ten3_3, ten3_4;

	public ModelCaptainCornelia() {
		texWidth = 128;
		texHeight = 128;

		main = new ModelRenderer(this);
		main.setPos(0.0F, 4.0F, 150.0F);

		bodyTopZ = new ModelRenderer(this);
		bodyTopZ.setPos(0.0F, -25.5F, -150.0F);
		main.addChild(bodyTopZ);

		bodyTop = new ModelRenderer(this);
		bodyTop.setPos(0.0F, 30.0F, 0.0F);
		bodyTopZ.addChild(bodyTop);
		bodyTop.texOffs(32, 41).addBox(-2.5F, -6.5F, -2.0F, 5.0F, 8.0F, 4.0F, 0.2F, false);

		bodyTop2 = new ModelRenderer(this);
		bodyTop2.setPos(0.0F, -5.0F, 0.0F);
		bodyTop.addChild(bodyTop2);

		head = new ModelRenderer(this);
		head.setPos(0.0F, -6.0F, 1.0F);
		bodyTop2.addChild(head);
		setRotationAngle(head, 0.0873F, 0.0F, 0.0F);
		head.texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 1.0F, false);
		head.texOffs(24, 8).addBox(-4.0F, -9.75F, -4.0F, 8.0F, 1.0F, 8.0F, 0.0F, false);
		head.texOffs(37, 54).addBox(-1.5F, -10.25F, -1.5F, 3.0F, 1.0F, 3.0F, 0.0F, false);
		head.texOffs(72, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);

		final ModelRenderer head1 = new ModelRenderer(this);
		head1.setPos(0.0F, -9.5F, 7.0F);
		head.addChild(head1);
		setRotationAngle(head1, -1.5708F, 0.0F, 0.0F);
		head1.texOffs(28, 53).addBox(-1.5F, 0.75F, 5.5F, 3.0F, 1.0F, 3.0F, 0.0F, false);

		final ModelRenderer head2 = new ModelRenderer(this);
		head2.setPos(0.0F, -9.5F, 0.0F);
		head.addChild(head2);
		setRotationAngle(head2, -1.5708F, 0.0F, 0.0F);
		head2.texOffs(22, 22).addBox(-4.0F, -5.75F, 1.5F, 8.0F, 1.0F, 8.0F, 0.0F, false);

		final ModelRenderer head3 = new ModelRenderer(this);
		head3.setPos(-5.25F, -4.0F, 0.05F);
		head.addChild(head3);
		setRotationAngle(head3, 0.0F, 1.5708F, 0.0F);
		head3.texOffs(12, 49).addBox(-3.5F, -3.5F, -1.05F, 7.0F, 7.0F, 1.0F, 0.0F, false);
		head3.texOffs(12, 57).addBox(3.0F, -3.0F, -0.3F, 1.0F, 6.0F, 1.0F, 0.0F, false);
		head3.texOffs(56, 33).addBox(-4.0F, -3.0F, -0.3F, 1.0F, 6.0F, 1.0F, 0.0F, false);
		head3.texOffs(52, 31).addBox(-4.0F, 3.0F, -0.3F, 8.0F, 1.0F, 1.0F, 0.0F, false);
		head3.texOffs(50, 48).addBox(-4.0F, -4.0F, -0.3F, 8.0F, 1.0F, 1.0F, 0.0F, false);

		final ModelRenderer head4 = new ModelRenderer(this);
		head4.setPos(5.25F, -4.0F, 0.05F);
		head.addChild(head4);
		setRotationAngle(head4, 0.0F, -1.5708F, 0.0F);
		head4.texOffs(46, 17).addBox(-3.5F, -3.5F, -1.05F, 7.0F, 7.0F, 1.0F, 0.0F, false);
		head4.texOffs(44, 0).addBox(3.0F, -3.0F, -0.3F, 1.0F, 6.0F, 1.0F, 0.0F, false);
		head4.texOffs(40, 0).addBox(-4.0F, -3.0F, -0.3F, 1.0F, 6.0F, 1.0F, 0.0F, false);
		head4.texOffs(46, 27).addBox(-4.0F, 3.0F, -0.3F, 8.0F, 1.0F, 1.0F, 0.0F, false);
		head4.texOffs(46, 25).addBox(-4.0F, -4.0F, -0.3F, 8.0F, 1.0F, 1.0F, 0.0F, false);

		final ModelRenderer head5 = new ModelRenderer(this);
		head5.setPos(0.0F, 24.0F, 0.0F);
		head.addChild(head5);
		head5.texOffs(24, 0).addBox(-3.5F, -31.5F, -6.25F, 7.0F, 7.0F, 1.0F, 0.0F, false);
		head5.texOffs(4, 0).addBox(3.0F, -31.0F, -5.5F, 1.0F, 6.0F, 1.0F, 0.0F, false);
		head5.texOffs(0, 0).addBox(-4.0F, -31.0F, -5.5F, 1.0F, 6.0F, 1.0F, 0.0F, false);
		head5.texOffs(24, 19).addBox(-4.0F, -25.0F, -5.5F, 8.0F, 1.0F, 1.0F, 0.0F, false);
		head5.texOffs(24, 17).addBox(-4.0F, -32.0F, -5.5F, 8.0F, 1.0F, 1.0F, 0.0F, false);

		final ModelRenderer body = new ModelRenderer(this);
		body.setPos(0.0F, -4.5F, 0.0F);
		bodyTop2.addChild(body);
		body.texOffs(32, 31).addBox(-4.0F, -1.0F, -2.0F, 8.0F, 6.0F, 4.0F, 0.3F, false);

		leftBooby = new ModelRenderer(this);
		leftBooby.setPos(2.0F, -0.75F, 0.25F);
		bodyTop2.addChild(leftBooby);

		final ModelRenderer leftBoobyF = new ModelRenderer(this);
		leftBoobyF.setPos(0.0F, -1.0F, -4.0F);
		leftBooby.addChild(leftBoobyF);
		setRotationAngle(leftBoobyF, -0.5236F, -0.2269F, 0.0175F);
		leftBoobyF.texOffs(50, 41).addBox(-2.0F, -2.5F, 0.25F, 4.0F, 4.0F, 3.0F, 0.0F, false);

		rightBooby = new ModelRenderer(this);
		rightBooby.setPos(-2.0F, -0.75F, 0.25F);
		bodyTop2.addChild(rightBooby);

		final ModelRenderer rightBoobyF = new ModelRenderer(this);
		rightBoobyF.setPos(0.0F, -1.0F, -4.0F);
		rightBooby.addChild(rightBoobyF);
		setRotationAngle(rightBoobyF, -0.5236F, 0.2269F, -0.0175F);
		rightBoobyF.texOffs(47, 50).addBox(-2.0F, -2.5F, 0.25F, 4.0F, 4.0F, 3.0F, 0.0F, false);

		leftArm = new ModelRenderer(this);
		leftArm.setPos(4.0F, -3.5F, 1.0F);
		bodyTop2.addChild(leftArm);
		leftArm.texOffs(48, 0).addBox(0.25F, -1.0F, -2.0F, 3.0F, 6.0F, 3.0F, 0.0F, false);
		leftArm.texOffs(77, 16).addBox(0.25F, -1.0F, -2.0F, 3.0F, 6.0F, 3.0F, 0.2F, false);
		leftArm.texOffs(89, 16).addBox(0.25F, -5.0F, -0.5F, 7.0F, 9.0F, 0.0F, 0.0F, false);
		leftArm.texOffs(77, 16).addBox(0.25F, -1.0F, -2.0F, 3.0F, 6.0F, 3.0F, 0.2F, false);

		leftArmBottom = new ModelRenderer(this);
		leftArmBottom.setPos(1.75F, 5.0F, -0.5F);
		leftArm.addChild(leftArmBottom);
		leftArmBottom.texOffs(0, 64).addBox(-1.5F, -1.0F, -1.5F, 3.0F, 7.0F, 3.0F, 0.01F, false);

		rightArm = new ModelRenderer(this);
		rightArm.setPos(-4.25F, -3.5F, 0.0F);
		bodyTop2.addChild(rightArm);
		rightArm.texOffs(0, 48).addBox(-3.0F, -1.0F, -1.0F, 3.0F, 6.0F, 3.0F, 0.0F, false);

		rightArmBottom = new ModelRenderer(this);
		rightArmBottom.setPos(-1.5F, 5.0F, 0.5F);
		rightArm.addChild(rightArmBottom);
		rightArmBottom.texOffs(0, 64).addBox(-1.5F, -1.0F, -1.5F, 3.0F, 7.0F, 3.0F, 0.01F, false);

		item = new ModelRenderer(this);
		item.setPos(0.0F, 4.5F, 0.0F);
		rightArmBottom.addChild(item);
		item.texOffs(84, 106).addBox(-0.5F, -0.5F, -10.5F, 1.0F, 1.0F, 21.0F, 0.0F);

		final ModelRenderer bone = new ModelRenderer(this);
		bone.setPos(0.0F, 4.5F, 0.0F);
		rightArmBottom.addChild(bone);
		bone.texOffs(84, 106).addBox(-0.5F, -0.5F, -10.5F, 1.0F, 1.0F, 21.0F, 0.0F, false);

		ten1 = new ModelRenderer(this);
		ten1.setPos(0.0F, -5.5F, 3.5F);
		bodyTop2.addChild(ten1);
		ten1.texOffs(60, 0).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 4.0F, 2.0F, 0.0F, false);

		ten1_1 = new ModelRenderer(this);
		ten1_1.setPos(0.0F, 4.0F, 0.0F);
		ten1.addChild(ten1_1);
		ten1_1.texOffs(60, 0).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 4.0F, 2.0F, 0.0F, false);

		ten1_2 = new ModelRenderer(this);
		ten1_2.setPos(0.0F, 4.0F, 0.0F);
		ten1_1.addChild(ten1_2);
		ten1_2.texOffs(60, 6).addBox(-1.5F, 0.0F, -1.0F, 3.0F, 4.0F, 2.0F, 0.0F, false);

		ten1_3 = new ModelRenderer(this);
		ten1_3.setPos(0.0F, 4.0F, 0.0F);
		ten1_2.addChild(ten1_3);
		ten1_3.texOffs(60, 6).addBox(-1.5F, 0.0F, -1.0F, 3.0F, 4.0F, 2.0F, 0.0F, false);

		ten1_4 = new ModelRenderer(this);
		ten1_4.setPos(0.0F, 4.0F, 0.0F);
		ten1_3.addChild(ten1_4);
		ten1_4.texOffs(60, 12).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, false);

		ten2 = new ModelRenderer(this);
		ten2.setPos(2.75F, -7.5F, 3.0F);
		bodyTop2.addChild(ten2);
		ten2.texOffs(60, 0).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 4.0F, 2.0F, 0.0F, false);

		ten2_1 = new ModelRenderer(this);
		ten2_1.setPos(0.0F, 4.0F, 0.0F);
		ten2.addChild(ten2_1);
		ten2_1.texOffs(60, 0).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 4.0F, 2.0F, 0.0F, false);

		ten2_2 = new ModelRenderer(this);
		ten2_2.setPos(0.0F, 4.0F, 0.0F);
		ten2_1.addChild(ten2_2);
		ten2_2.texOffs(60, 6).addBox(-1.5F, 0.0F, -1.0F, 3.0F, 4.0F, 2.0F, 0.0F, false);

		ten2_3 = new ModelRenderer(this);
		ten2_3.setPos(0.0F, 4.0F, 0.0F);
		ten2_2.addChild(ten2_3);
		ten2_3.texOffs(60, 6).addBox(-1.5F, 0.0F, -1.0F, 3.0F, 4.0F, 2.0F, 0.0F, false);

		ten2_4 = new ModelRenderer(this);
		ten2_4.setPos(0.0F, 4.0F, 0.0F);
		ten2_3.addChild(ten2_4);
		ten2_4.texOffs(60, 12).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, false);

		ten3 = new ModelRenderer(this);
		ten3.setPos(-2.75F, -7.5F, 3.0F);
		bodyTop2.addChild(ten3);
		ten3.texOffs(60, 0).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 4.0F, 2.0F, 0.0F, false);

		ten3_1 = new ModelRenderer(this);
		ten3_1.setPos(0.0F, 4.0F, 0.0F);
		ten3.addChild(ten3_1);
		ten3_1.texOffs(60, 0).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 4.0F, 2.0F, 0.0F, false);

		ten3_2 = new ModelRenderer(this);
		ten3_2.setPos(0.0F, 4.0F, 0.0F);
		ten3_1.addChild(ten3_2);
		ten3_2.texOffs(60, 6).addBox(-1.5F, 0.0F, -1.0F, 3.0F, 4.0F, 2.0F, 0.0F, false);

		ten3_3 = new ModelRenderer(this);
		ten3_3.setPos(0.0F, 4.0F, 0.0F);
		ten3_2.addChild(ten3_3);
		ten3_3.texOffs(60, 6).addBox(-1.5F, 0.0F, -1.0F, 3.0F, 4.0F, 2.0F, 0.0F, false);

		ten3_4 = new ModelRenderer(this);
		ten3_4.setPos(0.0F, 4.0F, 0.0F);
		ten3_3.addChild(ten3_4);
		ten3_4.texOffs(60, 12).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, false);

		bodyBottomZ = new ModelRenderer(this);
		bodyBottomZ.setPos(0.0F, 34.5F, -150.0F);
		main.addChild(bodyBottomZ);

		bodyBottom = new ModelRenderer(this);
		bodyBottom.setPos(0.0F, -30.0F, 0.0F);
		bodyBottomZ.addChild(bodyBottom);
		bodyBottom.texOffs(0, 16).addBox(-4.5F, -0.5F, -2.75F, 9.0F, 8.0F, 6.0F, 0.2F, false);

		rightLeg = new ModelRenderer(this);
		rightLeg.setPos(-2.5F, 3.5F, 0.0F);
		bodyBottom.addChild(rightLeg);
		rightLeg.texOffs(16, 31).addBox(-2.0F, -1.5F, -2.0F, 4.0F, 7.0F, 4.0F, 0.0F, false);

		rightLegBottom = new ModelRenderer(this);
		rightLegBottom.setPos(0.0F, 5.5F, 0.0F);
		rightLeg.addChild(rightLegBottom);
		rightLegBottom.texOffs(12, 64).addBox(-2.0F, -1.0F, -2.0F, 4.0F, 8.0F, 4.0F, 0.01F, false);

		leftLeg = new ModelRenderer(this);
		leftLeg.setPos(2.5F, 3.5F, 0.0F);
		bodyBottom.addChild(leftLeg);
		leftLeg.texOffs(0, 30).addBox(-2.0F, -1.5F, -2.0F, 4.0F, 7.0F, 4.0F, 0.0F, false);

		leftLegBottom = new ModelRenderer(this);
		leftLegBottom.setPos(0.0F, 5.5F, 0.0F);
		leftLeg.addChild(leftLegBottom);
		leftLegBottom.texOffs(12, 64).addBox(-2.0F, -1.0F, -2.0F, 4.0F, 8.0F, 4.0F, 0.01F, false);
	}

	public void translateToHand(HandSide arm, MatrixStack pose) {
		this.main.translateAndRotate(pose);
		this.bodyTopZ.translateAndRotate(pose);
		this.bodyTop.translateAndRotate(pose);
		this.bodyTop2.translateAndRotate(pose);
		if (arm == HandSide.LEFT) {
			this.leftArm.translateAndRotate(pose);
			this.leftArmBottom.translateAndRotate(pose);
			this.item.translateAndRotate(pose);
		} else {
			this.rightArm.translateAndRotate(pose);
			this.rightArmBottom.translateAndRotate(pose);
			this.item.translateAndRotate(pose);
		}
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}

	@Override
	public void renderToBuffer(@Nonnull MatrixStack poseStack, @Nonnull IVertexBuilder vertexConsumer, int packedLight, int packedOverlay, float red, float green,
							   float blue, float alpha) {
		main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setupAnim(@Nonnull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		final float idle = HekateLib.mod.idle(limbSwingAmount, 3F);
		final float move = HekateLib.mod.move(limbSwingAmount, 3F);
		final float s1 = 0.1F;
		final float s2 = 0.4F;

		HekateLib.render.tick(entity);
		HekateLib.render.prepare(main, bodyTopZ, bodyBottomZ, bodyTop, bodyTop2, bodyBottom, head, rightBooby, leftBooby, rightArm, leftArm, rightLeg,
				leftLeg, rightArmBottom, leftArmBottom, rightLegBottom, leftLegBottom, item, ten1, ten1_1, ten1_2, ten1_3, ten1_4, ten2, ten2_1,
				ten2_2, ten2_3, ten2_4, ten3, ten3_1, ten3_2, ten3_3, ten3_4);

		HekateLib.i(this.main, 0.6F, 1.2F, 0F, 0F, 0F, 0F, s1, 0F, ageInTicks, idle);
		HekateLib.i(this.bodyBottom, 15F, 20F, 0F, 0F, 0F, 0F, s1, -0.9F, ageInTicks, idle);
		HekateLib.i(this.bodyTop2, 6F, -6F, 0F, 0F, 0F, 0F, s1, 0.9F, ageInTicks, idle);
		HekateLib.i(this.head, 4F, -6F, 0F, 0F, 0F, 0F, s1, 0.8F, ageInTicks, idle);
		HekateLib.i(this.rightArm, 12F, -9F, 9F, -27F, 6F, 20F, s1, -0.95F, ageInTicks, idle);
		HekateLib.i(this.rightArmBottom, -16F, 20F, 0F, 0F, 0F, 0F, s1, -0.90F, ageInTicks, idle);
		HekateLib.i(this.item, -5F, -35F, 0F, 0F, 0F, 0F, s1, -0.85F, ageInTicks, idle);
		HekateLib.i(this.leftArm, 12F, -9F, -9F, 27F, -9F, -27F, s1, -0.95F, ageInTicks, idle);
		HekateLib.i(this.leftArmBottom, -24F, 30F, 0F, 0F, 0F, 0F, s1, -0.90F, ageInTicks, idle);
		HekateLib.i(this.rightLeg, 12F, 24F, 0.6F, -3F, 0.6F, -3F, s1, -0.8F, ageInTicks, idle);
		HekateLib.i(this.rightLegBottom, -12F, -48F, 0F, 0F, 0F, 0F, s1, -0.6F, ageInTicks, idle);
		HekateLib.i(this.leftLeg, 6F, -12F, 0.6F, 3F, 0.6F, 3F, s1, -0.8F, ageInTicks, idle);
		HekateLib.i(this.leftLegBottom, -24F, -27F, 0F, 0F, 0F, 0F, s1, -0.7F, ageInTicks, idle);
		HekateLib.i(this.ten1, 16F, -20F, 0F, 0F, 0F, 0F, s1, 0.35F, ageInTicks, idle);
		HekateLib.i(this.ten1_1, 16F, -12F, 0F, 0F, 0F, 0F, s1, 0.30F, ageInTicks, idle);
		HekateLib.i(this.ten1_2, 16F, -6F, 0F, 0F, 0F, 0F, s1, 0.25F, ageInTicks, idle);
		HekateLib.i(this.ten1_3, 16F, 0F, 0F, 0F, 0F, 0F, s1, 0.20F, ageInTicks, idle);
		HekateLib.i(this.ten1_4, 16F, 0F, 0F, 0F, 0F, 0F, s1, 0.15F, ageInTicks, idle);
		HekateLib.i(this.ten2, 16F, -20F, 0F, -29F, 0F, 0F, s1, 0.25F, ageInTicks, idle);
		HekateLib.i(this.ten2_1, 16F, -12F, 0F, 0F, 0F, 0F, s1, 0.20F, ageInTicks, idle);
		HekateLib.i(this.ten2_2, 16F, -6F, 0F, 0F, 0F, 0F, s1, 0.15F, ageInTicks, idle);
		HekateLib.i(this.ten2_3, 16F, 0F, 0F, 0F, 0F, 0F, s1, 0.10F, ageInTicks, idle);
		HekateLib.i(this.ten2_4, 16F, 0F, 0F, 0F, 0F, 0F, s1, 0.05F, ageInTicks, idle);
		HekateLib.i(this.ten3, 16F, -20F, 0F, 29F, 0F, 0F, s1, 0.45F, ageInTicks, idle);
		HekateLib.i(this.ten3_1, 16F, -12F, 0F, 0F, 0F, 0F, s1, 0.40F, ageInTicks, idle);
		HekateLib.i(this.ten3_2, 16F, -6F, 0F, 0F, 0F, 0F, s1, 0.35F, ageInTicks, idle);
		HekateLib.i(this.ten3_3, 16F, 0F, 0F, 0F, 0F, 0F, s1, 0.30F, ageInTicks, idle);
		HekateLib.i(this.ten3_4, 16F, 0F, 0F, 0F, 0F, 0F, s1, 0.25F, ageInTicks, idle);
		HekateLib.m(this.main, -1.4F, 1.8F, 0F, 0F, 0F, 0F, s2, 0F, limbSwing, move);
		HekateLib.m(this.bodyTop, 6F, -24F, 0F, 0F, 0F, 0F, s2, 0F, limbSwing, move);
		HekateLib.m(this.bodyTop2, 6F, 12F, 0F, 0F, 0F, 0F, s2, 0F, limbSwing, move);
		HekateLib.m(this.bodyBottom, 6F, -36F, 0F, 0F, 0F, 0F, s2, 0F, limbSwing, move);
		HekateLib.m(this.rightArm, 12F, -9F, 9F, -27F, -9F, 27F, s2, 0.05F, limbSwing, move);
		HekateLib.m(this.rightArmBottom, 24F, 30F, 0F, 0F, 0F, 0F, s2, 0F, limbSwing, move);
		HekateLib.m(this.leftArm, 12F, -9F, -9F, 27F, 9F, -27F, s2, 0.05F, limbSwing, move);
		HekateLib.m(this.leftArmBottom, 24F, 30F, 0F, 0F, 0F, 0F, s2, 0F, limbSwing, move);
		HekateLib.m(this.rightLeg, 24F, 12F, 0.6F, -6F, 0.6F, -6F, s2, 0.5F, limbSwing, move);
		HekateLib.m(this.rightLegBottom, 24F, -74F, 0F, 0F, 0F, 0F, s2, 0.7F, limbSwing, move);
		HekateLib.m(this.leftLeg, 24F, 0F, -0.6F, 6F, -0.6F, 6F, s2, 0.5F, limbSwing, move);
		HekateLib.m(this.leftLegBottom, 24F, -30F, 0F, 0F, 0F, 0F, s2, 0.8F, limbSwing, move);
		HekateLib.m(this.ten1, -14F, -29F, 0F, 0F, 0F, 0F, s2, 0.25F, limbSwing, move);
		HekateLib.m(this.ten1_1, -14F, -18F, 0F, 0F, 0F, 0F, s2, 0.20F, limbSwing, move);
		HekateLib.m(this.ten1_2, -14F, -12F, 0F, 0F, 0F, 0F, s2, 0.15F, limbSwing, move);
		HekateLib.m(this.ten1_3, -14F, -6F, 0F, 0F, 0F, 0F, s2, 0.10F, limbSwing, move);
		HekateLib.m(this.ten1_4, -14F, -6F, 0F, 0F, 0F, 0F, s2, 0.05F, limbSwing, move);
		HekateLib.m(this.ten2, -14F, -29F, 0F, -29F, 0F, 0F, s2, 0.25F, limbSwing, move);
		HekateLib.m(this.ten2_1, -14F, -18F, 0F, 0F, 0F, 0F, s2, 0.20F, limbSwing, move);
		HekateLib.m(this.ten2_2, -14F, -12F, 0F, 0F, 0F, 0F, s2, 0.15F, limbSwing, move);
		HekateLib.m(this.ten2_3, -14F, -6F, 0F, 0F, 0F, 0F, s2, 0.10F, limbSwing, move);
		HekateLib.m(this.ten2_4, -14F, -6F, 0F, 0F, 0F, 0F, s2, 0.05F, limbSwing, move);
		HekateLib.m(this.ten3, -14F, -29F, 0F, 29F, 0F, 0F, s2, 0.25F, limbSwing, move);
		HekateLib.m(this.ten3_1, -14F, -18F, 0F, 0F, 0F, 0F, s2, 0.20F, limbSwing, move);
		HekateLib.m(this.ten3_2, -14F, -12F, 0F, 0F, 0F, 0F, s2, 0.15F, limbSwing, move);
		HekateLib.m(this.ten3_3, -14F, -6F, 0F, 0F, 0F, 0F, s2, 0.10F, limbSwing, move);
		HekateLib.m(this.ten3_4, -14F, -6F, 0F, 0F, 0F, 0F, s2, 0.05F, limbSwing, move);

		HekateLib.render.animation(entity, "attack", ageInTicks,

				new KeyFrame(60, 50, 8F, 6F, new AnimatedPart(this.main, 2F, 0F, 0F), new AnimatedPart(this.bodyTop, 0F, -17.5F, 0F),
						new AnimatedPart(this.bodyTop2, 0F, -32.5F, 0F), new AnimatedPart(this.head, -10F, 46F, -3.5F),
						new AnimatedPart(this.leftArm, 30F, 35F, -32F), new AnimatedPart(this.leftArmBottom, 25F, 0F, 0F),
						new AnimatedPart(this.rightArm, 5.5F, 28F, 19.5F), new AnimatedPart(this.rightArmBottom, 25F, 0F, 0F),
						new AnimatedPart(this.item, -50F, 0F, 0F), new AnimatedPart(this.bodyBottom, 27.5F, 0F, 0F),
						new AnimatedPart(this.rightLeg, 25F, 2.6F, -4.2F), new AnimatedPart(this.rightLegBottom, -60F, 0F, 0F),
						new AnimatedPart(this.leftLeg, -2.5F, 0F, 2.5F), new AnimatedPart(this.leftLegBottom, -17.5F, 0F, 0F)),
				new KeyFrame(50, 40, 6F, 6F, new AnimatedPart(this.main, 1.5F, 0F, 0F), new AnimatedPart(this.bodyTop, 0F, -7.5F, 0F),
						new AnimatedPart(this.bodyTop2, 10F, -15F, 0F), new AnimatedPart(this.head, -15F, 27F, 0F),
						new AnimatedPart(this.leftArm, -19F, 12.5F, -31F), new AnimatedPart(this.leftArmBottom, 105F, 0F, 0F),
						new AnimatedPart(this.rightArm, -10F, 1F, 20F), new AnimatedPart(this.rightArmBottom, 52F, 0F, 0F),
						new AnimatedPart(this.item, -50F, 0F, 0F), new AnimatedPart(this.bodyBottom, -22.5F, 0F, 0F),
						new AnimatedPart(this.rightLeg, 25F, 2.6F, -4.2F), new AnimatedPart(this.rightLegBottom, -70F, 0F, 0F),
						new AnimatedPart(this.leftLeg, -2.5F, 0F, 2.5F), new AnimatedPart(this.leftLegBottom, -17.5F, 0F, 0F)),

				new KeyFrame(40, 28, 6F, 20F, new AnimatedPart(this.main, 2.5F, 0F, 0F), new AnimatedPart(this.bodyTop, -14F, 42F, -2F),
						new AnimatedPart(this.bodyTop2, 0F, 25F, 0F), new AnimatedPart(this.head, 3F, -56F, -8F),
						new AnimatedPart(this.leftArm, -21F, 12F, -18.5F), new AnimatedPart(this.leftArmBottom, 70F, 0F, 0F),
						new AnimatedPart(this.rightArm, 57F, 25F, 74F), new AnimatedPart(this.rightArmBottom, 75F, 0F, 0F),
						new AnimatedPart(this.item, -45F, 0F, 0F), new AnimatedPart(this.bodyBottom, 25F, 0F, 0F),
						new AnimatedPart(this.rightLeg, 57F, 2.6F, -4.2F), new AnimatedPart(this.rightLegBottom, -90F, 0F, 0F),
						new AnimatedPart(this.leftLeg, -2.5F, 0F, 2.5F), new AnimatedPart(this.leftLegBottom, -17.5F, 0F, 0F)),
				new KeyFrame(28, 20, 20F, 6F, new AnimatedPart(this.main, 1.5F, 0F, 0F), new AnimatedPart(this.bodyTop, -10F, -4F, 8F),
						new AnimatedPart(this.bodyTop2, 0F, -20F, 0F), new AnimatedPart(this.head, 2F, 24F, -4.5F),
						new AnimatedPart(this.leftArm, -50F, 30F, -35F), new AnimatedPart(this.leftArmBottom, 100F, 0F, 0F),
						new AnimatedPart(this.rightArm, 35F, 12F, 47F), new AnimatedPart(this.rightArmBottom, 10F, 0F, 0F),
						new AnimatedPart(this.item, -82F, 0F, 0F), new AnimatedPart(this.bodyBottom, -32F, 2F, -4F),
						new AnimatedPart(this.rightLeg, 57F, 2.6F, -4.2F), new AnimatedPart(this.rightLegBottom, -90F, 0F, 0F),
						new AnimatedPart(this.leftLeg, -2.5F, 0F, 2.5F), new AnimatedPart(this.leftLegBottom, -17.5F, 0F, 0F)),

				new KeyFrame(20, 8, 6F, 20F, new AnimatedPart(this.main, 2.5F, 0F, 0F), new AnimatedPart(this.bodyTop, 0F, -17.5F, 0F),
						new AnimatedPart(this.bodyTop2, 0F, -27.5F, 0F), new AnimatedPart(this.head, -22F, 42F, -9F),
						new AnimatedPart(this.leftArm, 33.5F, 12F, -18.5F), new AnimatedPart(this.leftArmBottom, 67F, 0F, 0F),
						new AnimatedPart(this.rightArm, -48F, -10.5F, 57F), new AnimatedPart(this.rightArmBottom, 75F, 0F, 0F),
						new AnimatedPart(this.item, -45F, 0F, 0F), new AnimatedPart(this.bodyBottom, 25F, 0F, 0F),
						new AnimatedPart(this.rightLeg, 32.4F, 2.6F, -4.2F), new AnimatedPart(this.rightLegBottom, -90F, 0F, 0F),
						new AnimatedPart(this.leftLeg, 0F, 0F, 2.5F), new AnimatedPart(this.leftLegBottom, -38F, 0F, 0F)),
				new KeyFrame(8, 0, 20F, 2F, new AnimatedPart(this.main, 1.5F, 0F, 0F), new AnimatedPart(this.bodyTop, -12.5F, 35F, 0F),
						new AnimatedPart(this.bodyTop2, 0F, 7.5F, 0F), new AnimatedPart(this.head, 1F, -38F, 0F),
						new AnimatedPart(this.leftArm, -21.5F, 12F, -18.5F), new AnimatedPart(this.leftArmBottom, 70F, 0F, 0F),
						new AnimatedPart(this.rightArm, 24F, -10.5F, 57F), new AnimatedPart(this.rightArmBottom, 12.5F, 0F, 0F),
						new AnimatedPart(this.item, -60F, 0F, 0F), new AnimatedPart(this.bodyBottom, -28F, 10F, -2F),
						new AnimatedPart(this.rightLeg, 57F, 2.6F, -4.2F), new AnimatedPart(this.rightLegBottom, -90F, 0F, 0F),
						new AnimatedPart(this.leftLeg, -2.5F, 0F, 2.5F), new AnimatedPart(this.leftLegBottom, -17.5F, 0F, 0F)));

		this.head.yRot += HekateLib.render.head(netHeadYaw, 0.33F);
		this.bodyTop.yRot += HekateLib.render.head(netHeadYaw, 0.33F);
		this.bodyTop2.yRot += HekateLib.render.head(netHeadYaw, 0.33F);
	}
}
