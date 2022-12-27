package com.obscuria.aquamirae.client.models;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.obscuria.obscureapi.client.animations.AnimatedPart;
import com.obscuria.obscureapi.client.animations.HekateLib;
import com.obscuria.obscureapi.client.animations.KeyFrame;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

import javax.annotation.Nonnull;

public class ModelTorturedSoul<T extends Entity> extends EntityModel<T> {

	private final ModelRenderer main, body, heart, head, nose, leftArm, rightArm, leftLeg, rightLeg, leftArmBottom, rightArmBottom, leftLegBottom, rightLegBottom;

	public ModelTorturedSoul() {
		this.texWidth = 64;
		this.texHeight = 64;

		main = new ModelRenderer(this);
		main.setPos(0.0F, 6.0F, 150.0F);

		body = new ModelRenderer(this);
		body.setPos(0.0F, 6.0F, -150.0F);
		main.addChild(body);
		body.texOffs(16, 20).addBox(-4.0F, -12.0F, -3.0F, 8.0F, 12.0F, 6.0F, -0.02F, false);
		body.texOffs(0, 39).addBox(-4.0F, -12.0F, -3.0F, 8.0F, 18.0F, 6.0F, 0.5F, false);

		heart = new ModelRenderer(this);
		heart.setPos(0.0F, -8.0F, 0.0F);
		body.addChild(heart);
		heart.texOffs(44, 21).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.0F, false);

		head = new ModelRenderer(this);
		head.setPos(0.0F, -12.0F, 0.0F);
		body.addChild(head);
		head.texOffs(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, 0.0F, false);
		head.texOffs(28, 39).addBox(-4.5F, -13.0F, -4.5F, 9.0F, 10.0F, 9.0F, -0.4F, false);

		nose = new ModelRenderer(this);
		nose.setPos(0.0F, -2.0F, -4.0F);
		head.addChild(nose);
		nose.texOffs(24, 0).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 4.0F, 2.0F, 0.0F, false);

		leftArm = new ModelRenderer(this);
		leftArm.setPos(4.0F, -10.0F, 0.0F);
		body.addChild(leftArm);
		setRotationAngle(leftArm, 0.0F, 0.0F, 0.0F);
		leftArm.texOffs(38, 0).addBox(0.0F, -2.0F, -2.0F, 4.0F, 7.0F, 4.0F, 0.0F, true);
		leftArm.texOffs(44, 30).addBox(0.0F, -6.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.01F, true);

		leftArmBottom = new ModelRenderer(this);
		leftArmBottom.setPos(2.0F, 4.0F, 1.0F);
		leftArm.addChild(leftArmBottom);
		leftArmBottom.texOffs(38, 11).addBox(-2.0F, 0.0F, -3.0F, 4.0F, 6.0F, 4.0F, 0.01F, true);

		rightArm = new ModelRenderer(this);
		rightArm.setPos(-4.0F, -10.0F, 0.0F);
		body.addChild(rightArm);
		rightArm.texOffs(38, 0).addBox(-4.0F, -2.0F, -2.0F, 4.0F, 7.0F, 4.0F, 0.0F, false);
		rightArm.texOffs(44, 30).addBox(-4.0F, -6.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.01F, false);

		rightArmBottom = new ModelRenderer(this);
		rightArmBottom.setPos(-2.0F, 4.0F, 0.0F);
		rightArm.addChild(rightArmBottom);
		rightArmBottom.texOffs(38, 11).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.01F, false);

		leftLeg = new ModelRenderer(this);
		leftLeg.setPos(2.0F, 6.0F, -150.0F);
		main.addChild(leftLeg);
		leftLeg.texOffs(0, 18).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 7.0F, 4.0F, 0.0F, true);

		leftLegBottom = new ModelRenderer(this);
		leftLegBottom.setPos(0.0F, 6.0F, 0.0F);
		leftLeg.addChild(leftLegBottom);
		leftLegBottom.texOffs(0, 29).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.01F, true);

		rightLeg = new ModelRenderer(this);
		rightLeg.setPos(-2.0F, 6.0F, -150.0F);
		main.addChild(rightLeg);
		rightLeg.texOffs(0, 18).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 7.0F, 4.0F, 0.0F, false);

		rightLegBottom = new ModelRenderer(this);
		rightLegBottom.setPos(0.0F, 6.0F, 0.0F);
		rightLeg.addChild(rightLegBottom);
		rightLegBottom.texOffs(0, 29).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.01F, false);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}

	@Override
	public void renderToBuffer(@Nonnull MatrixStack poseStack, @Nonnull IVertexBuilder vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setupAnim(@Nonnull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		final float idle = HekateLib.mod.idle(limbSwingAmount, 3F);
		final float move = HekateLib.mod.move(limbSwingAmount, 3F);
		final float speed1 = 0.1F;
		final float speed2 = 0.7F;

		HekateLib.render.tick(entity);
		HekateLib.render.prepare(main, body, heart, head, nose, leftArm, rightArm, leftLeg, rightLeg, leftArmBottom, rightArmBottom, leftLegBottom, rightLegBottom);

		HekateLib.i(main, -0.3F, -0.5F, 0, 0, 0, 0, speed1, 0F, ageInTicks, idle);
		HekateLib.i(body, -3F, -10F, 0, 0, 0, 0, speed1, 0F, ageInTicks, idle);
		HekateLib.i(head, 3F, 16F, 0, 0, 0, 0, speed1, 0F, ageInTicks, idle);
		HekateLib.i(rightArm, 12F, 35F, 0, 0, -3F, 10F, speed1, -0.2F, ageInTicks, idle);
		HekateLib.i(rightArmBottom, 18F, 69F, 0, 0, 0, 0, speed1, -0.4F, ageInTicks, idle);
		HekateLib.i(leftArm, 10F, 15F, 0, 0, 3F, -10F, speed1, -0.2F, ageInTicks, idle);
		HekateLib.i(leftArmBottom, 15F, 30F, 0, 0, 0, 0, speed1, -0.4F, ageInTicks, idle);
		HekateLib.i(leftLeg, 15F, 30F, -0.5F, 4F, 0.5F, -4F, speed1, 0F, ageInTicks, idle);
		HekateLib.i(leftLegBottom, -30F, -44F, 0, 0, 0, 0, speed1, 0F, ageInTicks, idle);
		this.rightLeg.xRot = this.leftLeg.xRot;
		this.rightLeg.yRot = -this.leftLeg.yRot;
		this.rightLeg.zRot = -this.leftLeg.zRot;
		this.rightLegBottom.xRot = this.leftLegBottom.xRot;

		HekateLib.m(main, -0.5F, -0.3F, 0, 0, 0, 0, speed2 * 2, 0F, limbSwing, move);
		HekateLib.m(body, 3F, -3F, 0, 0, 0, 0, speed2 * 2, -0.1F, limbSwing, move);
		HekateLib.m(head, 3F, -3F, 0, 0, 0, 0, speed2 * 2, -0.2F, limbSwing, move);
		HekateLib.m(rightArm, 24F, -12F, 0, 0, -3F, 10F, speed2, -0.1F, limbSwing, move);
		HekateLib.m(rightArmBottom, 20F, 34F, 0, 0, 0, 0, speed2, -0.2F, limbSwing, move);
		HekateLib.m(leftArm, -24F, -12F, 0, 0, 3F, -10F, speed2, -0.1F, limbSwing, move);
		HekateLib.m(leftArmBottom, -20F, 34F, 0, 0, 0, 0, speed2, -0.2F, limbSwing, move);
		HekateLib.m(rightLeg, -30F, 14F, 0, 0, 0, 0, speed2, -0.1F, limbSwing, move);
		HekateLib.m(rightLegBottom, -34F, -34F, 0, 0, 0, 0, speed2, -0.2F, limbSwing, move);
		HekateLib.m(leftLeg, 30F, 14F, 0, 0, 0, 0, speed2, -0.1F, limbSwing, move);
		HekateLib.m(leftLegBottom, 34F, -34F, 0, 0, 0, 0, speed2, -0.2F, limbSwing, move);

		this.heart.xRot = ageInTicks / 13F;
		this.heart.yRot = ageInTicks / 9F;
		this.heart.zRot = ageInTicks / 5F;

		this.head.yRot += HekateLib.render.head(netHeadYaw, 0.5F);
		this.body.yRot += HekateLib.render.head(netHeadYaw, 0.5F);

		HekateLib.render.animation(entity, "attack", ageInTicks,
				new KeyFrame(20, 0, 16F, 5F,
						new AnimatedPart(body, -27F, 0, 0),
						new AnimatedPart(leftArm, 125F, 0, 0),
						new AnimatedPart(rightArm, 125F, 0, 0),
						new AnimatedPart(leftArmBottom, 12F, 0, 0),
						new AnimatedPart(rightArmBottom, 12F, 0, 0)));
	}
}
