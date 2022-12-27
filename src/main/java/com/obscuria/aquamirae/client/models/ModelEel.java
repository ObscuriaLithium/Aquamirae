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

public class ModelEel<T extends Entity> extends EntityModel<T> {

	public final ModelRenderer main, body1, body2, body3, body4, body5, body6, body7, body8, body9, body10, head, headTop, headBottom, leftFin, rightFin;

	public ModelEel() {
		this.texWidth = 256;
		this.texHeight = 256;

		main = new ModelRenderer(this);
		main.setPos(0.0F, 24.0F, 300.0F);

		body1 = new ModelRenderer(this);
		body1.setPos(0.0F, 24.0F, 0.0F);
		main.addChild(body1);
		setRotationAngle(body1, 0.5672F, 0.0F, 0.0F);

		body2 = new ModelRenderer(this);
		body2.setPos(0.0F, -5.0F, 0.0F);
		body1.addChild(body2);
		setRotationAngle(body2, -0.3491F, 0.0F, 0.0F);

		body3 = new ModelRenderer(this);
		body3.setPos(0.0F, -6.0F, 0.0F);
		body2.addChild(body3);
		setRotationAngle(body3, -0.6109F, 0.0F, 0.0F);

		body4 = new ModelRenderer(this);
		body4.setPos(0.0F, -6.0F, 0.0F);
		body3.addChild(body4);
		setRotationAngle(body4, -0.5236F, 0.0F, 0.0F);

		body5 = new ModelRenderer(this);
		body5.setPos(0.0F, -6.0F, 0.0F);
		body4.addChild(body5);
		setRotationAngle(body5, 0.5236F, 0.0F, 0.0F);

		body6 = new ModelRenderer(this);
		body6.setPos(0.0F, -7.0F, 0.0F);
		body5.addChild(body6);
		setRotationAngle(body6, 0.5672F, 0.0F, 0.0F);

		body7 = new ModelRenderer(this);
		body7.setPos(0.0F, -5.0F, 0.0F);
		body6.addChild(body7);
		setRotationAngle(body7, 0.6109F, 0.0F, 0.0F);

		body8 = new ModelRenderer(this);
		body8.setPos(0.0F, -6.0F, 0.0F);
		body7.addChild(body8);
		setRotationAngle(body8, 0.5236F, 0.0F, 0.0F);

		body9 = new ModelRenderer(this);
		body9.setPos(0.0F, -6.0F, 0.0F);
		body8.addChild(body9);
		setRotationAngle(body9, 0.3927F, 0.0F, 0.0F);

		body10 = new ModelRenderer(this);
		body10.setPos(0.0F, -6.0F, 0.0F);
		body9.addChild(body10);
		setRotationAngle(body10, 0.2182F, 0.0F, 0.0F);

		head = new ModelRenderer(this);
		head.setPos(0.0F, -3.0F, 0.0F);
		body10.addChild(head);
		setRotationAngle(head, 0.2182F, 0.0F, 0.0F);

		headTop = new ModelRenderer(this);
		headTop.setPos(0.0F, -11.0F, -6.0F);
		head.addChild(headTop);
		setRotationAngle(headTop, 0.6981F, 0.0F, 0.0F);

		final ModelRenderer bone3 = new ModelRenderer(this);
		bone3.setPos(0.0F, 42.5507F, 4.774F);
		headTop.addChild(bone3);
		setRotationAngle(bone3, 0.0F, -0.7854F, 0.0F);
		bone3.texOffs(84, 61).addBox(-6.0F, -42.0F, -6.0F, 12.0F, 8.0F, 12.0F, 0.0F, false);
		bone3.texOffs(12, 0).addBox(6.0F, -40.0F, -3.0F, 1.0F, 4.0F, 4.0F, 0.0F, false);
		bone3.texOffs(12, 8).addBox(-3.0F, -40.0F, 6.0F, 4.0F, 4.0F, 1.0F, 0.0F, false);
		bone3.texOffs(84, 82).addBox(-6.0F, -46.5F, -6.0F, 12.0F, 5.0F, 12.0F, -0.5F, false);
		bone3.texOffs(84, 82).addBox(-7.0F, -46.0F, -7.0F, 12.0F, 5.0F, 12.0F, -1.0F, false);

		headBottom = new ModelRenderer(this);
		headBottom.setPos(0.0F, -11.0F, -6.0F);
		head.addChild(headBottom);
		setRotationAngle(headBottom, 2.0944F, 0.0F, 0.0F);

		final ModelRenderer bone5 = new ModelRenderer(this);
		bone5.setPos(0.0F, 40.2191F, 4.5237F);
		headBottom.addChild(bone5);
		setRotationAngle(bone5, 0.0F, -0.7854F, 0.0F);
		bone5.texOffs(12, 23).addBox(-9.0F, -42.0F, -9.0F, 15.0F, 2.0F, 15.0F, 0.0F, false);
		bone5.texOffs(12, 0).addBox(-9.0F, -40.5F, -9.0F, 15.0F, 8.0F, 15.0F, -0.5F, false);
		bone5.texOffs(12, 0).addBox(-10.0F, -41.0F, -10.0F, 15.0F, 8.0F, 15.0F, -1.0F, false);

		final ModelRenderer bone2 = new ModelRenderer(this);
		bone2.setPos(0.0F, 35.0F, 0.0F);
		head.addChild(bone2);
		setRotationAngle(bone2, 0.0F, -0.7854F, 0.0F);
		bone2.texOffs(12, 40).addBox(-6.0F, -45.0F, -6.0F, 12.0F, 12.0F, 12.0F, 0.0F, false);

		final ModelRenderer bone11 = new ModelRenderer(this);
		bone11.setPos(0.0F, -40.0F, 0.0F);
		bone2.addChild(bone11);
		setRotationAngle(bone11, 0.0F, 0.7854F, 0.0F);
		bone11.texOffs(169, -8).addBox(0.0F, -8.0F, 7.2426F, 0.0F, 15.0F, 9.0F, 0.0F, false);

		final ModelRenderer bone13 = new ModelRenderer(this);
		bone13.setPos(0.0F, -40.0F, 0.0F);
		bone2.addChild(bone13);
		setRotationAngle(bone13, 0.0F, 2.3562F, 0.0F);
		bone13.texOffs(169, -8).addBox(0.0F, -8.0F, 7.2426F, 0.0F, 15.0F, 9.0F, 0.0F, false);

		final ModelRenderer bone15 = new ModelRenderer(this);
		bone15.setPos(0.0F, -40.0F, 0.0F);
		bone2.addChild(bone15);
		setRotationAngle(bone15, 0.0F, -0.7854F, 0.0F);
		bone15.texOffs(169, -8).addBox(0.0F, -8.0F, 7.2426F, 0.0F, 15.0F, 9.0F, 0.0F, false);

		final ModelRenderer leftFinP = new ModelRenderer(this);
		leftFinP.setPos(4.0F, -7.0F, -4.0F);
		head.addChild(leftFinP);
		setRotationAngle(leftFinP, 0.0F, 0.7854F, 0.0F);

		leftFin = new ModelRenderer(this);
		leftFin.setPos(0.0F, 0.0F, 0.0F);
		leftFinP.addChild(leftFin);
		setRotationAngle(leftFin, 0.0F, 0.0F, -0.9599F);
		leftFin.texOffs(137, -9).addBox(0.5F, -1.0F, -5.0F, 0.0F, 18.0F, 10.0F, 0.0F, false);
		leftFin.texOffs(158, 1).addBox(0.0F, -1.0F, -2.0F, 1.0F, 8.0F, 4.0F, 0.0F, false);

		final ModelRenderer rightFinP = new ModelRenderer(this);
		rightFinP.setPos(-5.0F, -7.0F, -4.0F);
		head.addChild(rightFinP);
		setRotationAngle(rightFinP, 0.0F, -0.7854F, 0.0F);

		rightFin = new ModelRenderer(this);
		rightFin.setPos(0.0F, 0.0F, 0.0F);
		rightFinP.addChild(rightFin);
		setRotationAngle(rightFin, 0.0F, 0.0F, 1.0908F);
		rightFin.texOffs(137, -9).addBox(0.5F, -1.0F, -5.0F, 0.0F, 18.0F, 10.0F, 0.0F, false);
		rightFin.texOffs(158, 1).addBox(0.0F, -1.0F, -2.0F, 1.0F, 8.0F, 4.0F, 0.0F, false);

		final ModelRenderer bone20 = new ModelRenderer(this);
		bone20.setPos(0.0F, 40.0F, 0.0F);
		body10.addChild(bone20);
		setRotationAngle(bone20, 0.0F, -0.7854F, 0.0F);
		bone20.texOffs(48, 52).addBox(-6.0F, -44.0F, -6.0F, 12.0F, 9.0F, 12.0F, -0.1F, false);

		final ModelRenderer bone17 = new ModelRenderer(this);
		bone17.setPos(0.0F, -47.0F, 0.0F);
		bone20.addChild(bone17);
		setRotationAngle(bone17, 0.0F, 0.7854F, 0.0F);
		bone17.texOffs(170, -8).addBox(0.0F, 3.0F, 5.0F, 0.0F, 10.0F, 8.0F, 0.0F, false);

		final ModelRenderer bone19 = new ModelRenderer(this);
		bone19.setPos(0.0F, -47.0F, 0.0F);
		bone20.addChild(bone19);
		setRotationAngle(bone19, 0.0F, 2.3562F, 0.0F);
		bone19.texOffs(170, -8).addBox(0.0F, 3.0F, 5.0F, 0.0F, 10.0F, 8.0F, 0.0F, false);

		final ModelRenderer bone21 = new ModelRenderer(this);
		bone21.setPos(0.0F, -47.0F, 0.0F);
		bone20.addChild(bone21);
		setRotationAngle(bone21, 0.0F, -0.7854F, 0.0F);
		bone21.texOffs(170, -8).addBox(0.0F, 3.0F, 5.0F, 0.0F, 10.0F, 8.0F, 0.0F, false);

		final ModelRenderer bone18 = new ModelRenderer(this);
		bone18.setPos(0.0F, 30.0F, 0.0F);
		body9.addChild(bone18);
		setRotationAngle(bone18, 0.0F, -0.7854F, 0.0F);
		bone18.texOffs(60, 11).addBox(-6.0F, -34.0F, -6.0F, 12.0F, 9.0F, 12.0F, -0.15F, false);

		final ModelRenderer bone22 = new ModelRenderer(this);
		bone22.setPos(0.0F, -37.0F, 0.0F);
		bone18.addChild(bone22);
		setRotationAngle(bone22, 0.0F, 0.7854F, 0.0F);
		bone22.texOffs(170, -8).addBox(0.0F, 2.0F, 3.0F, 0.0F, 10.0F, 8.0F, 0.0F, false);

		final ModelRenderer bone23 = new ModelRenderer(this);
		bone23.setPos(0.0F, -37.0F, 0.0F);
		bone18.addChild(bone23);
		setRotationAngle(bone23, 0.0F, 2.3562F, 0.0F);
		bone23.texOffs(170, -8).addBox(0.0F, 2.0F, 3.0F, 0.0F, 10.0F, 8.0F, 0.0F, false);

		final ModelRenderer bone24 = new ModelRenderer(this);
		bone24.setPos(0.0F, -37.0F, 0.0F);
		bone18.addChild(bone24);
		setRotationAngle(bone24, 0.0F, -0.7854F, 0.0F);
		bone24.texOffs(170, -8).addBox(0.0F, 2.0F, 3.0F, 0.0F, 10.0F, 8.0F, 0.0F, false);

		final ModelRenderer bone16 = new ModelRenderer(this);
		bone16.setPos(0.0F, 20.0F, 0.0F);
		body8.addChild(bone16);
		setRotationAngle(bone16, 0.0F, -0.7854F, 0.0F);
		bone16.texOffs(12, 64).addBox(-6.0F, -24.0F, -6.0F, 12.0F, 9.0F, 12.0F, -0.2F, false);

		final ModelRenderer bone14 = new ModelRenderer(this);
		bone14.setPos(0.0F, 0.0F, 0.0F);
		body7.addChild(bone14);
		setRotationAngle(bone14, 0.0F, -0.7854F, 0.0F);
		bone14.texOffs(48, 73).addBox(-6.0F, -4.0F, -6.0F, 12.0F, 9.0F, 12.0F, -0.25F, false);

		final ModelRenderer bone12 = new ModelRenderer(this);
		bone12.setPos(0.0F, 0.0F, 0.0F);
		body6.addChild(bone12);
		setRotationAngle(bone12, 0.0F, -0.7854F, 0.0F);
		bone12.texOffs(84, 32).addBox(-6.0F, -3.0F, -6.0F, 12.0F, 9.0F, 12.0F, -0.3F, false);

		final ModelRenderer bone10 = new ModelRenderer(this);
		bone10.setPos(0.0F, 40.0F, 0.0F);
		body5.addChild(bone10);
		setRotationAngle(bone10, 0.0F, -0.7854F, 0.0F);
		bone10.texOffs(2, 85).addBox(-5.0F, -44.0F, -5.0F, 10.0F, 8.0F, 10.0F, 0.2F, false);

		final ModelRenderer bone8 = new ModelRenderer(this);
		bone8.setPos(0.0F, 30.0F, 0.0F);
		body4.addChild(bone8);
		setRotationAngle(bone8, 0.0F, -0.7854F, 0.0F);
		bone8.texOffs(32, 94).addBox(-5.0F, -34.0F, -5.0F, 10.0F, 8.0F, 10.0F, 0.15F, false);

		final ModelRenderer bone6 = new ModelRenderer(this);
		bone6.setPos(0.0F, 20.0F, 0.0F);
		body3.addChild(bone6);
		setRotationAngle(bone6, 0.0F, -0.7854F, 0.0F);
		bone6.texOffs(96, 0).addBox(-5.0F, -24.0F, -5.0F, 10.0F, 8.0F, 10.0F, 0.1F, false);

		final ModelRenderer bone4 = new ModelRenderer(this);
		bone4.setPos(0.0F, 0.0F, 0.0F);
		body2.addChild(bone4);
		setRotationAngle(bone4, 0.0F, -0.7854F, 0.0F);
		bone4.texOffs(72, 99).addBox(-5.0F, -4.0F, -5.0F, 10.0F, 8.0F, 10.0F, 0.05F, false);

		final ModelRenderer bone = new ModelRenderer(this);
		bone.setPos(0.0F, 0.0F, 0.0F);
		body1.addChild(bone);
		setRotationAngle(bone, 0.0F, -0.7854F, 0.0F);
		bone.texOffs(0, 103).addBox(-5.0F, -3.0F, -5.0F, 10.0F, 8.0F, 10.0F, 0.0F, false);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}

	@Override
	public void renderToBuffer(MatrixStack poseStack, @Nonnull IVertexBuilder vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		poseStack.pushPose();
		poseStack.scale(1.8f, 1.8f, 1.8f);
		poseStack.translate(0F, -0.7F, 0F);
		main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		poseStack.popPose();
	}

	public void setupAnim(@Nonnull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

		final float rareIdle = HekateLib.mod.get(entity, "rareIdle", 1F, 1F);
		final float s1 = 0.1F;
		HekateLib.render.tick(entity);
		HekateLib.render.prepare(main, body1, body2, body3, body4, body5, body6, body7, body8, body9, body10, head, headTop, headBottom, leftFin, rightFin);

		HekateLib.i(this.body1, -1F, -17.5F, 0F, 0F, 0F, 0F, s1, -0.95F, ageInTicks);
		HekateLib.i(this.body2, -1F, 20F, 0F, 0F, 0F, 0F, s1, -0.90F, ageInTicks);
		HekateLib.i(this.body3, -1F, 22.5F, 0F, 0F, 0F, 0F, s1, -0.85F, ageInTicks);
		HekateLib.i(this.body4, -1F, 20F, 0F, 0F, 0F, 0F, s1, -0.80F, ageInTicks);
		HekateLib.i(this.body5, -2F, -22.5F, 0F, 0F, 0F, 0F, s1, -0.75F, ageInTicks);
		HekateLib.i(this.body6, -2F, -22.5F, 0F, 0F, 0F, 0F, s1, -0.70F, ageInTicks);
		HekateLib.i(this.body7, -2F, -27.5F, 0F, 0F, 0F, 0F, s1, -0.65F, ageInTicks);
		HekateLib.i(this.body8, -2F, -25F, 0F, 0F, 0F, 0F, s1, -0.60F, ageInTicks);
		HekateLib.i(this.body9, -3F, -27.5F, 0F, 0F, 0F, 0F, s1, -0.55F, ageInTicks);
		HekateLib.i(this.body10, -3F, -22.5F, 0F, 0F, 0F, 0F, s1, -0.50F, ageInTicks);
		HekateLib.i(this.head, -3F, -22.5F, 0F, 0F, 0F, 0F, s1, 0F, ageInTicks);
		HekateLib.i(this.headTop, 0F, -40F, 0F, 0F, 0F, 0F, s1, 0F, ageInTicks);
		HekateLib.i(this.headBottom, -10F, -105F, 0F, 0F, 0F, 0F, s1, 0F, ageInTicks);
		HekateLib.i(this.leftFin, 0F, 0F, 0F, 0F, 10F, -45F, s1, 0F, ageInTicks);
		HekateLib.i(this.rightFin, 0F, 0F, 0F, 0F, -10F, 45F, s1, 0F, ageInTicks);

		this.body5.yRot = HekateLib.render.idle(1F, 0F, s1 * 1.4F, -0.95F, ageInTicks, rareIdle);
		this.body6.yRot = HekateLib.render.idle(2F, 0F, s1 * 1.4F, -0.90F, ageInTicks, rareIdle);
		this.body7.yRot = HekateLib.render.idle(3F, 0F, s1 * 1.4F, -0.85F, ageInTicks, rareIdle);
		this.body8.yRot = HekateLib.render.idle(4F, 0F, s1 * 1.4F, -0.80F, ageInTicks, rareIdle);
		this.body9.yRot = HekateLib.render.idle(5F, 0F, s1 * 1.4F, -0.75F, ageInTicks, rareIdle);
		this.body10.yRot = HekateLib.render.idle(6F, 0F, s1 * 1.4F, -0.70F, ageInTicks, rareIdle);
		this.head.yRot = HekateLib.render.idle(7F, 0F, s1 * 1.4F, -0.65F, ageInTicks, rareIdle);

		HekateLib.render.animation(entity, "attack", ageInTicks, new KeyFrame(20, 8, 6F, 24F, new AnimatedPart(this.body1, -17.5F, 0F, 0F),
				new AnimatedPart(this.body2, 20F, 0F, 0F), new AnimatedPart(this.body3, 22.5F, 0F, 0F), new AnimatedPart(this.body4, 27.5F, 0F, 0F),
				new AnimatedPart(this.body5, -2.5F, 0F, 0F), new AnimatedPart(this.body6, -2.5F, 0F, 0F), new AnimatedPart(this.body7, -30F, 0F, 0F),
				new AnimatedPart(this.body8, -30F, 0F, 0F), new AnimatedPart(this.body9, -35F, 0F, 0F), new AnimatedPart(this.body10, -25F, 0F, 0F),
				new AnimatedPart(this.head, -25F, 0F, 0F), new AnimatedPart(this.headTop, -40F, 0F, 0F),
				new AnimatedPart(this.headBottom, 10F, -170F, 0F, 0F, 0F, 0F, 1.4F, 0F), new AnimatedPart(this.leftFin, 0F, 0F, -90F),
				new AnimatedPart(this.rightFin, 0F, 0F, 90F)),
				new KeyFrame(8, 0, 24F, 2F, new AnimatedPart(this.body1, -17.5F, 0F, 0F), new AnimatedPart(this.body2, 10F, 0F, 0F),
						new AnimatedPart(this.body3, 15F, 0F, 0F), new AnimatedPart(this.body4, 12.5F, 0F, 0F),
						new AnimatedPart(this.body5, -35F, -1F, 0F), new AnimatedPart(this.body6, -30F, -1F, 0F),
						new AnimatedPart(this.body7, -35F, -2F, 0F), new AnimatedPart(this.body8, -25F, -2F, 0F),
						new AnimatedPart(this.body9, -12.5F, -3F, 0F), new AnimatedPart(this.body10, -2.5F, -3F, 0F),
						new AnimatedPart(this.head, -20F, -4F, 0F), new AnimatedPart(this.headTop, -40F, 0F, 0F),
						new AnimatedPart(this.headBottom, -75F, 0F, 0F), new AnimatedPart(this.leftFin, 0F, 0F, -40F),
						new AnimatedPart(this.rightFin, 0F, 0F, 40F)));

		HekateLib.render.animation(entity, "roar", ageInTicks,
				new KeyFrame(52, 40, 4F, 14F, new AnimatedPart(this.body1, -17.5F, 0F, 0F), new AnimatedPart(this.body2, 20F, 0F, 0F),
						new AnimatedPart(this.body3, 22.5F, 0F, 0F), new AnimatedPart(this.body4, 27.5F, 0F, 0F),
						new AnimatedPart(this.body5, -2.5F, 0F, 0F), new AnimatedPart(this.body6, 0F, -2.5F, 0F, 0F, 5F, 0F, 0.4F, -0.95F),
						new AnimatedPart(this.body7, 0F, -30F, 0F, 0F, 5F, 0F, 0.4F, -0.90F),
						new AnimatedPart(this.body8, 0F, -30F, 0F, 0F, 5F, 0F, 0.4F, -0.85F),
						new AnimatedPart(this.body9, 0F, -35F, 0F, 0F, 5F, 0F, 0.4F, -0.80F),
						new AnimatedPart(this.body10, 0F, -25F, 0F, 0F, 5F, 0F, 0.4F, -0.75F),
						new AnimatedPart(this.head, 0F, -25F, 0F, 0F, 5F, 0F, 0.4F, -0.70F), new AnimatedPart(this.headTop, -40F, 0F, 0F),
						new AnimatedPart(this.headBottom, 5F, -90F, 0F, 0F, 0F, 0F, 1.4F, 0F), new AnimatedPart(this.leftFin, 0F, 0F, -90F),
						new AnimatedPart(this.rightFin, 0F, 0F, 90F)),
				new KeyFrame(40, 0, 14F, 2F, new AnimatedPart(this.body1, -17.5F, 0F, 0F), new AnimatedPart(this.body2, 12.5F, 0F, 0F),
						new AnimatedPart(this.body3, 25F, 0F, 0F), new AnimatedPart(this.body4, 22.5F, 0F, 0F),
						new AnimatedPart(this.body5, -30F, 0F, 0F), new AnimatedPart(this.body6, 0F, -32.5F, 0F, 0F, 4F, 0F, 0.4F, -0.95F),
						new AnimatedPart(this.body7, 0F, -27.5F, 0F, 0F, 4F, 0F, 0.4F, -0.90F),
						new AnimatedPart(this.body8, 0F, -27.5F, 0F, 0F, 4F, 0F, 0.4F, -0.85F),
						new AnimatedPart(this.body9, 0F, -22.5F, 0F, 0F, 4F, 0F, 0.4F, -0.80F),
						new AnimatedPart(this.body10, 0F, -7.5F, 0F, 0F, 4F, 0F, 0.4F, -0.75F),
						new AnimatedPart(this.head, 0F, -12.5F, 0F, 0F, 4F, 0F, 0.4F, -0.70F), new AnimatedPart(this.headTop, -40F, 0F, 0F),
						new AnimatedPart(this.headBottom, 10F, -145F, 0F, 0F, 0F, 0F, 1.5F, 0F), new AnimatedPart(this.leftFin, 0F, 0F, -80F),
						new AnimatedPart(this.rightFin, 0F, 0F, 80F)));

		HekateLib.render.animation(entity, "moveMain", ageInTicks, new KeyFrame(50, 20, 1.6F, 2F, new AnimatedPart(this.main, -20F, 0F, 0F)),
				new KeyFrame(20, 0, 2F, 4F, new AnimatedPart(this.main, 0F, 0F, 0F)));
		HekateLib.render.animation(entity, "move", ageInTicks,
				new KeyFrame(50, 20, 4F, 2F, new AnimatedPart(this.body1, -1F, 0F, 0F, 0F, 1F, 0F, 0.4F, -0.95F),
						new AnimatedPart(this.body2, -1F, 0F, 0F, 0F, 1F, 0F, 0.4F, -0.90F),
						new AnimatedPart(this.body3, -1F, 0F, 0F, 0F, 1F, 0F, 0.4F, -0.85F),
						new AnimatedPart(this.body4, -1F, 0F, 0F, 0F, 1F, 0F, 0.4F, -0.80F),
						new AnimatedPart(this.body5, -1F, 0F, 0F, 0F, 1F, 0F, 0.4F, -0.75F),
						new AnimatedPart(this.body6, -1F, 0F, 0F, 0F, 1F, 0F, 0.4F, -0.75F),
						new AnimatedPart(this.body7, -1F, 0F, 0F, 0F, 1F, 0F, 0.4F, -0.65F),
						new AnimatedPart(this.body8, -1F, 0F, 0F, 0F, 1F, 0F, 0.4F, -0.60F),
						new AnimatedPart(this.body9, -1F, 0F, 0F, 0F, 1F, 0F, 0.4F, -0.55F),
						new AnimatedPart(this.body10, -1F, 0F, 0F, 0F, 1F, 0F, 0.4F, -0.50F),
						new AnimatedPart(this.head, -1F, 0F, 0F, 0F, 1F, 0F, 0.4F, -0.50F), new AnimatedPart(this.headTop, -40F, 0F, 0F),
						new AnimatedPart(this.headBottom, 5F, -90F, 0F, 0F, 0F, 0F, 1.4F, 0F), new AnimatedPart(this.leftFin, 0F, 0F, -90F),
						new AnimatedPart(this.rightFin, 0F, 0F, 90F)),
				new KeyFrame(20, 0, 2F, 4F, new AnimatedPart(this.body1, -3F, 0F, 0F, 0F, 3F, 0F, 0.4F, -0.95F),
						new AnimatedPart(this.body2, -3F, 0F, 0F, 0F, 3F, 0F, 0.4F, -0.90F),
						new AnimatedPart(this.body3, -3F, 0F, 0F, 0F, 3F, 0F, 0.4F, -0.85F),
						new AnimatedPart(this.body4, -3F, 0F, 0F, 0F, 3F, 0F, 0.4F, -0.80F),
						new AnimatedPart(this.body5, -3F, 0F, 0F, 0F, 3F, 0F, 0.4F, -0.75F),
						new AnimatedPart(this.body6, -3F, 0F, 0F, 0F, 3F, 0F, 0.4F, -0.75F),
						new AnimatedPart(this.body7, -3F, 0F, 0F, 0F, 3F, 0F, 0.4F, -0.65F),
						new AnimatedPart(this.body8, -3F, 0F, 0F, 0F, 3F, 0F, 0.4F, -0.60F),
						new AnimatedPart(this.body9, -3F, 0F, 0F, 0F, 3F, 0F, 0.4F, -0.55F),
						new AnimatedPart(this.body10, -3F, 0F, 0F, 0F, 3F, 0F, 0.4F, -0.50F),
						new AnimatedPart(this.head, -3F, 0F, 0F, 0F, 3F, 0F, 0.4F, -0.50F), new AnimatedPart(this.headTop, -40F, 0F, 0F),
						new AnimatedPart(this.headBottom, 5F, -90F, 0F, 0F, 0F, 0F, 1.4F, 0F), new AnimatedPart(this.leftFin, 0F, 0F, -90F),
						new AnimatedPart(this.rightFin, 0F, 0F, 90F)));
		this.body1.yRot += HekateLib.render.head(netHeadYaw, 0.1F);
		this.body2.yRot += HekateLib.render.head(netHeadYaw, 0.1F);
		this.body3.yRot += HekateLib.render.head(netHeadYaw, 0.1F);
		this.body4.yRot += HekateLib.render.head(netHeadYaw, 0.1F);
		this.body5.yRot += HekateLib.render.head(netHeadYaw, 0.1F);
		this.body6.yRot += HekateLib.render.head(netHeadYaw, 0.1F);
		this.body7.yRot += HekateLib.render.head(netHeadYaw, 0.1F);
		this.body8.yRot += HekateLib.render.head(netHeadYaw, 0.1F);
		this.body9.yRot += HekateLib.render.head(netHeadYaw, 0.1F);
		this.body10.yRot += HekateLib.render.head(netHeadYaw, 0.1F);
	}
}
