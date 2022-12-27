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

public class ModelMaw<T extends Entity> extends EntityModel<T> {

	private final ModelRenderer main, head, headTop, headBottom, body, body2, body3, rightFin, leftFin;

	public ModelMaw() {
		this.texWidth = 256;
		this.texHeight = 256;

		main = new ModelRenderer(this);
		main.setPos(0.0F, 15.0F, 150.0F);

		head = new ModelRenderer(this);
		head.setPos(0.0F, 0.0F, -147.0F);
		main.addChild(head);

		headTop = new ModelRenderer(this);
		headTop.setPos(0.0F, 0.0F, 0.0F);
		head.addChild(headTop);

		ModelRenderer cube1 = new ModelRenderer(this);
		cube1.setPos(0.0F, 0.0F, 0.0F);
		headTop.addChild(cube1);
		setRotationAngle(cube1, -0.4363F, 0.0F, 0.0F);
		cube1.texOffs(64, 66).addBox(-10.0F, 0.0F, -24.0F, 20.0F, 6.0F, 24.0F, 0.0F, false);
		cube1.texOffs(0, 60).addBox(-10.0F, -6.0F, -24.0F, 20.0F, 6.0F, 24.0F, 0.0F, false);

		ModelRenderer cube2 = new ModelRenderer(this);
		cube2.setPos(0.0F, -1.0F, -3.75F);
		headTop.addChild(cube2);
		setRotationAngle(cube2, -0.4363F, 0.0F, 0.0F);
		cube2.texOffs(66, 19).addBox(0.01F, -14.4226F, -20.0937F, 0.0F, 8.0F, 23.0F, 0.0F, false);

		headBottom = new ModelRenderer(this);
		headBottom.setPos(0.0F, 0.0F, 0.0F);
		head.addChild(headBottom);

		ModelRenderer cube3 = new ModelRenderer(this);
		cube3.setPos(0.0F, 0.0F, 0.0F);
		headBottom.addChild(cube3);
		setRotationAngle(cube3, 0.1745F, 0.0F, 0.0F);
		cube3.texOffs(0, 0).addBox(-10.5F, -6.0F, -24.0F, 21.0F, 6.0F, 24.0F, 0.0F, false);
		cube3.texOffs(0, 30).addBox(-10.5F, 0.0F, -24.0F, 21.0F, 6.0F, 24.0F, 0.0F, false);

		body = new ModelRenderer(this);
		body.setPos(0.0F, 0.0F, -150.0F);
		main.addChild(body);
		body.texOffs(76, 16).addBox(-9.0F, -6.0F, 2.0F, 18.0F, 12.0F, 14.0F, 0.0F, false);
		body.texOffs(0, 98).addBox(0.0F, -14.0F, 2.0F, 0.0F, 8.0F, 14.0F, 0.0F, false);

		body2 = new ModelRenderer(this);
		body2.setPos(0.0F, 0.0F, 16.0F);

		ModelRenderer cube4 = new ModelRenderer(this);
		cube4.setPos(0.0F, 0.0F, 0.0F);
		body2.addChild(cube4);
		setRotationAngle(cube4, -0.2618F, 0.0F, 0.0F);
		cube4.texOffs(0, 0).addBox(0.0F, -11.0F, -1.0F, 0.0F, 8.0F, 12.0F, 0.0F, false);
		cube4.texOffs(0, 90).addBox(-7.0F, -3.0F, -3.0F, 14.0F, 8.0F, 14.0F, 0.0F, false);

		body3 = new ModelRenderer(this);
		body3.setPos(0.0F, 0.0F, 12.0F);
		body2.addChild(body3);

		ModelRenderer cube5 = new ModelRenderer(this);
		cube5.setPos(0.0F, 0.0F, 0.0F);
		body3.addChild(cube5);
		setRotationAngle(cube5,-0.0873F, 0.0F, 0.0F);
		cube5.texOffs(56, 76).addBox(0.0F, -2.0F, 5.0F, 0.0F, 12.0F, 20.0F, 0.0F,false);
		cube5.texOffs(82, 96).addBox(-4.0F, 2.0F, -2.0F, 8.0F, 4.0F, 14.0F, 0.0F, false);

		leftFin = new ModelRenderer(this);
		leftFin.setPos(9.0F, 3.0F, 10.0F);
		body.addChild(leftFin);

		ModelRenderer cube6 = new ModelRenderer(this);
		cube6.setPos(0.0F, 0.0F, 0.0F);
		leftFin.addChild(cube6);
		setRotationAngle(cube6, 0.0F, 0.0F, 0.3491F);
		cube6.texOffs(82, 52).addBox(0.0F, -1.0F, -4.0F, 16.0F, 2.0F, 8.0F, 0.0F, false);

		rightFin = new ModelRenderer(this);
		rightFin.setPos(-9.0F, 3.0F, 10.0F);

		ModelRenderer cube7 = new ModelRenderer(this);
		cube7.setPos(0.0F, 0.0F, 0.0F);
		rightFin.addChild(cube7);
		setRotationAngle(cube7, 0.0F, 0.0F, -0.3491F);
		cube7.texOffs(66, 0).addBox(-16.0F, -1.0F, -4.0F, 16.0F, 2.0F, 8.0F, 0.0F, false);
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
		final float idle = HekateLib.mod.idle(limbSwingAmount, 5F);
		final float move = HekateLib.mod.move(limbSwingAmount, 5F);
		final float speed1 = 0.03F;
		final float speed2 = 0.6F;

		HekateLib.render.tick(entity);
		HekateLib.render.prepare(main, head, headTop, headBottom, body, body2, body3, rightFin, leftFin);

		HekateLib.i(headTop, -5F, 0, 0, 0, 0, 0, speed1 * 2, 0F, ageInTicks, idle);
		HekateLib.i(body2, -8F, -8F, 0, 0, 4F, 0, speed1, 0F, ageInTicks, idle);
		HekateLib.i(body2, 0, 0, -10F, 0, 0, 0, speed1 * 2, 0F, ageInTicks, idle);
		HekateLib.i(body3, -8F, -8F, 0, 0, 4F, 0, speed1, -0.1F, ageInTicks, idle);
		HekateLib.i(body3, 0, 0, -10F, 0, 0, 0, speed1 * 2, -0.1F, ageInTicks, idle);

		HekateLib.m(main, 0, 0, 0, 0, -4F, 0, speed2, 0F, limbSwing, move);
		HekateLib.m(headTop, -4F, 12F, 0, 0, 0, 0, speed2, 0F, limbSwing, move);
		HekateLib.m(body, 0, 0, -12F, 0, 0, 0, speed2, 0F, limbSwing, move);
		HekateLib.m(body2, 0, 0, -12F, 0, 0, 0, speed2, -0.1F, limbSwing, move);
		HekateLib.m(body3, 0, 0, -12F, 0, 0, 0, speed2, -0.2F, limbSwing, move);
		HekateLib.m(rightFin, 0, 0, 40F, 0, 0, 0, speed2, 0F, limbSwing, move);
		HekateLib.m(leftFin, 0, 0, 40F, 0, 0, 0, speed2, 0F, limbSwing, move);
		HekateLib.m(rightFin, 0, 0, 0, 0, 12F, 10F, speed2, -0.3F, limbSwing, move);
		HekateLib.m(leftFin, 0, 0, 0, 0, 12F, -10F, speed2, -0.3F, limbSwing, move);

		HekateLib.render.animation(entity, "attack", ageInTicks,
				new KeyFrame(20, 0, 20F, 5F,
						new AnimatedPart(head, 10F, 0, 0),
						new AnimatedPart(headTop, -22F, 0, 0)));
	}
}
