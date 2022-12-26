package com.obscuria.aquamirae.client.models;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.obscuria.obscureapi.client.animations.AnimatedPart;
import com.obscuria.obscureapi.client.animations.HekateLib;
import com.obscuria.obscureapi.client.animations.IHekateProvider;
import com.obscuria.obscureapi.client.animations.KeyFrame;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelAnglerfish<T extends Entity> extends EntityModel<T> {
	public final ModelRenderer main, head, headTop, headBottom, body, tail1, tail2, tail3, tail4, tail5, tail6, leftFin, rightFin, lamp1, lamp2, lamp3, lamp4, lamp5;

	public ModelAnglerfish() {
		texWidth = 128;
		texHeight = 128;

		main = new ModelRenderer(this);
		main.setPos(0.0F, 5.0F, 0.0F);

		head = new ModelRenderer(this);
		head.setPos(0.0F, 0.0F, -5.0F);
		main.addChild(head);
		setRotationAngle(head, 0.6109F, 0.0F, 0.0F);

		headTop = new ModelRenderer(this);
		headTop.setPos(0.0F, 2.0F, -5.0F);
		head.addChild(headTop);
		headTop.texOffs(50, 45).addBox(-7.0F, -16.0F, 0.0F, 14.0F, 16.0F, 10.0F, 0.0F, false);
		headTop.texOffs(59, 10).addBox(1.0F, -17.0F, 2.0F, 5.0F, 1.0F, 5.0F, 0.0F, false);
		headTop.texOffs(40, 0).addBox(-6.0F, -17.0F, 2.0F, 5.0F, 1.0F, 5.0F, 0.0F, false);
		headTop.texOffs(0, 55).addBox(-7.0F, -16.0F, -8.0F, 14.0F, 16.0F, 8.0F, 0.0F, false);

		lamp1 = new ModelRenderer(this);
		lamp1.setPos(0.0F, -16.0F, 9.0F);
		headTop.addChild(lamp1);
		setRotationAngle(lamp1, -0.2182F, 0.0F, 0.0F);
		lamp1.texOffs(107, 0).addBox(-0.5F, -5.0F, 0.0F, 1.0F, 5.0F, 0.0F, 0.0F, false);

		lamp2 = new ModelRenderer(this);
		lamp2.setPos(0.0F, -5.0F, 0.0F);
		lamp1.addChild(lamp2);
		setRotationAngle(lamp2, 0.1745F, 0.0F, 0.0F);
		lamp2.texOffs(107, 5).addBox(-0.5F, -5.0F, 0.0F, 1.0F, 5.0F, 0.0F, 0.0F, false);

		lamp3 = new ModelRenderer(this);
		lamp3.setPos(0.0F, -5.0F, 0.0F);
		lamp2.addChild(lamp3);
		setRotationAngle(lamp3, 0.5672F, 0.0F, 0.0F);
		lamp3.texOffs(107, 10).addBox(-0.5F, -5.0F, 0.0F, 1.0F, 5.0F, 0.0F, 0.0F, false);

		lamp4 = new ModelRenderer(this);
		lamp4.setPos(0.0F, -5.0F, 0.0F);
		lamp3.addChild(lamp4);
		setRotationAngle(lamp4, 0.9163F, 0.0F, 0.0F);
		lamp4.texOffs(107, 15).addBox(-0.5F, -5.0F, 0.0F, 1.0F, 5.0F, 0.0F, 0.0F, false);

		lamp5 = new ModelRenderer(this);
		lamp5.setPos(0.0F, -5.0F, 0.0F);
		lamp4.addChild(lamp5);
		setRotationAngle(lamp5, 0.5672F, 0.0F, 0.0F);
		lamp5.texOffs(104, 20).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 2.0F, 0.0F, false);

		headBottom = new ModelRenderer(this);
		headBottom.setPos(0.0F, 2.0F, -5.0F);
		head.addChild(headBottom);
		headBottom.texOffs(44, 16).addBox(-7.0F, -2.0F, -14.0F, 14.0F, 3.0F, 16.0F, 0.11F, false);
		headBottom.texOffs(0, 32).addBox(-7.0F, -9.0F, -14.0F, 14.0F, 7.0F, 16.0F, 0.1F, false);

		body = new ModelRenderer(this);
		body.setPos(0.0F, 0.0F, 0.0F);
		main.addChild(body);
		body.texOffs(0, 0).addBox(-6.0F, -8.0F, -11.0F, 12.0F, 16.0F, 16.0F, 0.0F, false);
		body.texOffs(44, 55).addBox(0.0F, -18.0F, -11.0F, 0.0F, 10.0F, 16.0F, 0.0F, false);
		body.texOffs(44, 19).addBox(0.0F, 8.0F, -11.0F, 0.0F, 10.0F, 16.0F, 0.0F, false);

		tail1 = new ModelRenderer(this);
		tail1.setPos(0.0F, 0.0F, 5.0F);
		body.addChild(tail1);
		tail1.texOffs(68, 73).addBox(-4.0F, -7.0F, 0.0F, 8.0F, 14.0F, 8.0F, 0.0F, false);
		tail1.texOffs(76, 27).addBox(0.0F, -17.0F, 0.0F, 0.0F, 10.0F, 8.0F, 0.0F, false);
		tail1.texOffs(0, 24).addBox(0.0F, 7.0F, 0.0F, 0.0F, 10.0F, 8.0F, 0.0F, false);

		tail2 = new ModelRenderer(this);
		tail2.setPos(0.0F, 0.0F, 8.0F);
		tail1.addChild(tail2);
		tail2.texOffs(12, 79).addBox(-2.0F, -6.0F, 0.0F, 4.0F, 12.0F, 6.0F, 0.0F, false);
		tail2.texOffs(52, 0).addBox(0.0F, -15.0F, 0.0F, 0.0F, 9.0F, 6.0F, 0.0F, false);
		tail2.texOffs(40, 0).addBox(0.0F, 6.0F, 0.0F, 0.0F, 9.0F, 6.0F, 0.0F, false);

		tail3 = new ModelRenderer(this);
		tail3.setPos(0.0F, 0.0F, 6.0F);
		tail2.addChild(tail3);
		tail3.texOffs(0, 0).addBox(-1.0F, -3.0F, 0.0F, 2.0F, 6.0F, 6.0F, 0.0F, false);
		tail3.texOffs(0, 73).addBox(0.0F, -12.0F, 0.0F, 0.0F, 24.0F, 6.0F, 0.0F, false);

		tail4 = new ModelRenderer(this);
		tail4.setPos(0.0F, 0.0F, 6.0F);
		tail3.addChild(tail4);
		tail4.texOffs(56, 75).addBox(0.0F, -8.0F, 0.0F, 0.0F, 16.0F, 6.0F, 0.0F, false);

		tail5 = new ModelRenderer(this);
		tail5.setPos(0.0F, 0.0F, 6.0F);
		tail4.addChild(tail5);
		tail5.texOffs(44, 75).addBox(0.0F, -8.0F, 0.0F, 0.0F, 16.0F, 6.0F, 0.0F, false);

		tail6 = new ModelRenderer(this);
		tail6.setPos(0.0F, 0.0F, 6.0F);
		tail5.addChild(tail6);
		tail6.texOffs(32, 73).addBox(0.0F, -8.0F, 0.0F, 0.0F, 16.0F, 6.0F, 0.0F, false);

		leftFin = new ModelRenderer(this);
		leftFin.setPos(6.0F, 0.0F, -4.5F);
		body.addChild(leftFin);
		setRotationAngle(leftFin, 0.0F, 0.7854F, -0.5236F);
		leftFin.texOffs(0, 103).addBox(0.0F, -2.0F, -0.5F, 1.0F, 4.0F, 5.0F, 0.0F, false);
		leftFin.texOffs(0, 103).addBox(0.5F, -6.0F, -0.5F, 0.0F, 12.0F, 13.0F, 0.0F, false);

		rightFin = new ModelRenderer(this);
		rightFin.setPos(-6.0F, 0.0F, -4.5F);
		body.addChild(rightFin);
		rightFin.texOffs(0, 103).addBox(-1.0F, -2.0F, -0.5F, 1.0F, 4.0F, 5.0F, 0.0F, false);
		rightFin.texOffs(0, 103).addBox(-0.5F, -6.0F, -0.5F, 0.0F, 12.0F, 13.0F, 0.0F, false);
		/*
		main = new ModelRenderer(this);
		main.addChild("main", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 5.0F, 0.0F, 0.2618F, 0.0F, 0.0F));

		PartDefinition head = main.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, -5.0F, 0.6109F, 0.0F, 0.0F));

		PartDefinition headTop = head.addOrReplaceChild("headTop", CubeListBuilder.create().texOffs(50, 45).addBox(-7.0F, -16.0F, 0.0F, 14.0F, 16.0F, 10.0F, new CubeDeformation(0.0F))
				.texOffs(59, 10).addBox(1.0F, -17.0F, 2.0F, 5.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(40, 0).addBox(-6.0F, -17.0F, 2.0F, 5.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(0, 55).addBox(-7.0F, -16.0F, -8.0F, 14.0F, 16.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, -5.0F));

		PartDefinition lamp1 = headTop.addOrReplaceChild("lamp1", CubeListBuilder.create().texOffs(107, 0).addBox(-0.5F, -5.0F, 0.0F, 1.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -16.0F, 9.0F, -0.2182F, 0.0F, 0.0F));

		PartDefinition lamp2 = lamp1.addOrReplaceChild("lamp2", CubeListBuilder.create().texOffs(107, 5).addBox(-0.5F, -5.0F, 0.0F, 1.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.0F, 0.0F, 0.1745F, 0.0F, 0.0F));

		PartDefinition lamp3 = lamp2.addOrReplaceChild("lamp3", CubeListBuilder.create().texOffs(107, 10).addBox(-0.5F, -5.0F, 0.0F, 1.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.0F, 0.0F, 0.5672F, 0.0F, 0.0F));

		PartDefinition lamp4 = lamp3.addOrReplaceChild("lamp4", CubeListBuilder.create().texOffs(107, 15).addBox(-0.5F, -5.0F, 0.0F, 1.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.0F, 0.0F, 0.9163F, 0.0F, 0.0F));

		PartDefinition lamp5 = lamp4.addOrReplaceChild("lamp5", CubeListBuilder.create().texOffs(104, 20).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.0F, 0.0F, 0.5672F, 0.0F, 0.0F));

		PartDefinition headBottom = head.addOrReplaceChild("headBottom", CubeListBuilder.create().texOffs(44, 16).addBox(-7.0F, -2.0F, -14.0F, 14.0F, 3.0F, 16.0F, new CubeDeformation(0.11F))
				.texOffs(0, 32).addBox(-7.0F, -9.0F, -14.0F, 14.0F, 7.0F, 16.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, 2.0F, -5.0F));

		PartDefinition body = main.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -8.0F, -11.0F, 12.0F, 16.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(44, 55).addBox(0.0F, -18.0F, -11.0F, 0.0F, 10.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(44, 19).addBox(0.0F, 8.0F, -11.0F, 0.0F, 10.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition tail1 = body.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(68, 73).addBox(-4.0F, -7.0F, 0.0F, 8.0F, 14.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(76, 27).addBox(0.0F, -17.0F, 0.0F, 0.0F, 10.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(0, 24).addBox(0.0F, 7.0F, 0.0F, 0.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 5.0F));

		PartDefinition tail2 = tail1.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(12, 79).addBox(-2.0F, -6.0F, 0.0F, 4.0F, 12.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(52, 0).addBox(0.0F, -15.0F, 0.0F, 0.0F, 9.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(40, 0).addBox(0.0F, 6.0F, 0.0F, 0.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 8.0F));

		PartDefinition tail3 = tail2.addOrReplaceChild("tail3", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -3.0F, 0.0F, 2.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(0, 73).addBox(0.0F, -12.0F, 0.0F, 0.0F, 24.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 6.0F));

		PartDefinition tail4 = tail3.addOrReplaceChild("tail4", CubeListBuilder.create().texOffs(56, 75).addBox(0.0F, -8.0F, 0.0F, 0.0F, 16.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 6.0F));

		PartDefinition tail5 = tail4.addOrReplaceChild("tail5", CubeListBuilder.create().texOffs(44, 75).addBox(0.0F, -8.0F, 0.0F, 0.0F, 16.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 6.0F));

		PartDefinition tail6 = tail5.addOrReplaceChild("tail6", CubeListBuilder.create().texOffs(32, 73).addBox(0.0F, -8.0F, 0.0F, 0.0F, 16.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 6.0F));

		PartDefinition leftFin = body.addOrReplaceChild("leftFin", CubeListBuilder.create().texOffs(0, 103).addBox(0.0F, -2.0F, -0.5F, 1.0F, 4.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(0, 103).addBox(0.5F, -6.0F, -0.5F, 0.0F, 12.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0F, 0.0F, -4.5F, 0.0F, 0.5672F, 0.0F));

		PartDefinition rightFin = body.addOrReplaceChild("rightFin", CubeListBuilder.create().texOffs(0, 103).addBox(-1.0F, -2.0F, -0.5F, 1.0F, 4.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(0, 103).addBox(-0.5F, -6.0F, -0.5F, 0.0F, 12.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0F, 0.0F, -4.5F, 0.0F, -0.6981F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);

		 */
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}

	@Override
	public void renderToBuffer(MatrixStack pose, IVertexBuilder vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		pose.pushPose();
		pose.scale(1.5F, 1.5F, 1.5F);
		main.render(pose, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		pose.popPose();
	}

	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		final float idle = HekateLib.mod.idle(limbSwingAmount, 3F);
		final float move = HekateLib.mod.move(limbSwingAmount, 3F);
		final float speed1 = 0.1F;
		final float speed2 = 0.6F;

		HekateLib.render.tick(entity);
		HekateLib.render.prepare(main, head, headTop, headBottom, body, tail1, tail2, tail3, tail4, tail5, tail6, leftFin, rightFin, lamp1, lamp2, lamp3, lamp4, lamp5);

		HekateLib.i(main, 1F, -15F, 0, 0, 0, 0, speed1, 0F, ageInTicks, idle);
		HekateLib.i(head, -1F, -35F, 0, 0, 0, 0, speed1, 0F, ageInTicks, idle);
		HekateLib.i(body, 0, 0, 5F, 0, 0, 0, speed1, 0F, ageInTicks, idle);
		HekateLib.i(tail1, 0, 0, 7F, 0, 0, 0, speed1, -0.05F, ageInTicks, idle);
		HekateLib.i(tail2, 0, 0, 9F, 0, 0, 0, speed1, -0.1F, ageInTicks, idle);
		HekateLib.i(tail3, 0, 0, 11F, 0, 0, 0, speed1, -0.15F, ageInTicks, idle);
		HekateLib.i(tail4, 0, 0, 13F, 0, 0, 0, speed1, -0.2F, ageInTicks, idle);
		HekateLib.i(tail5, 0, 0, 15F, 0, 0, 0, speed1, -0.25F, ageInTicks, idle);
		HekateLib.i(tail6, 0, 0, 15F, 0, 0, 0, speed1, -0.3F, ageInTicks, idle);
		HekateLib.i(leftFin, 0, 0, 15F, -40F, 0, -30F, speed1, -0.1F, ageInTicks, idle);
		HekateLib.i(rightFin, 0, 0, -15F, 40F, 0, 30F, speed1, -0.1F, ageInTicks, idle);
		HekateLib.i(lamp1, 2F, -5F, 0, 0, 0, 0, speed1, 0F, ageInTicks, idle);
		HekateLib.i(lamp2, 4F, -10F, 0, 0, 0, 0, speed1, -0.05F, ageInTicks, idle);
		HekateLib.i(lamp3, 6F, -30F, 0, 0, 0, 0, speed1, -0.1F, ageInTicks, idle);
		HekateLib.i(lamp4, 8F, -50F, 0, 0, 0, 0, speed1, -0.15F, ageInTicks, idle);
		HekateLib.i(lamp5, 10F, -30F, 0, 0, 0, 0, speed1, -0.2F, ageInTicks, idle);

		HekateLib.m(main, 1F, -15F, 0, 0, 0, 0, speed2, 0F, limbSwing, move);
		HekateLib.m(head, -1F, -35F, -10F, 0, 0, 0, speed2, 0F, limbSwing, move);
		HekateLib.m(body, 0, 0, 5F, 0, 0, 0, speed2, 0F, limbSwing, move);
		HekateLib.m(tail1, 0, 0, 10F, 0, 0, 0, speed2, -0.05F, limbSwing, move);
		HekateLib.m(tail2, 0, 0, 10F, 0, 0, 0, speed2, -0.1F, limbSwing, move);
		HekateLib.m(tail3, 0, 0, 15F, 0, 0, 0, speed2, -0.15F, limbSwing, move);
		HekateLib.m(tail4, 0, 0, 15F, 0, 0, 0, speed2, -0.2F, limbSwing, move);
		HekateLib.m(tail5, 0, 0, 20F, 0, 0, 0, speed2, -0.25F, limbSwing, move);
		HekateLib.m(tail6, 0, 0, 20F, 0, 0, 0, speed2, -0.3F, limbSwing, move);
		HekateLib.m(leftFin, 0, 0, 15F, -40F, 0, -30F, speed2, -0.1F, limbSwing, move);
		HekateLib.m(rightFin, 0, 0, -15F, 40F, 0, 30F, speed2, -0.1F, limbSwing, move);
		HekateLib.m(lamp1, 2F, -5F, 0, 0, 0, 0, speed2, 0F, ageInTicks, move);
		HekateLib.m(lamp2, 4F, -10F, 0, 0, 0, 0, speed2, -0.05F, ageInTicks, move);
		HekateLib.m(lamp3, 6F, -30F, 0, 0, 0, 0, speed2, -0.1F, ageInTicks, move);
		HekateLib.m(lamp4, 8F, -50F, 0, 0, 0, 0, speed2, -0.15F, ageInTicks, move);
		HekateLib.m(lamp5, 10F, -30F, 0, 0, 0, 0, speed2, -0.2F, ageInTicks, move);

		if (entity instanceof IHekateProvider)
			this.main.y = 5F + 5F * ((IHekateProvider)entity).getHekateProvider().getModifier("onGround1");
		HekateLib.render.animation(entity, "onGround", ageInTicks,
				new KeyFrame(20, 0, 5F, 5F,
						new AnimatedPart(main, 0, 0, 90),
						new AnimatedPart(body, 0, 0, 2F, 2F, 0, 0, speed2, 0F),
						new AnimatedPart(tail1, 0, 0, 3F, 3F, 0, 0, speed2, -0.05F),
						new AnimatedPart(tail2, 0, 0, 4F, 4F, 0, 0, speed2, -0.1F),
						new AnimatedPart(tail3, 0, 0, 5F, 5F, 0, 0, speed2, -0.15F),
						new AnimatedPart(tail4, 0, 0, 6F, 6F, 0, 0, speed2, -0.2F),
						new AnimatedPart(tail5, 0, 0, 7F, 7F, 0, 0, speed2, -0.25F),
						new AnimatedPart(tail6, 0, 0, 8F, 8F, 0, 0, speed2, -0.3F)));
		HekateLib.render.animation(entity, "attack", ageInTicks,
				new KeyFrame(30, 10, 5F, 18F,
						new AnimatedPart(headTop, 1F, 15F, 0, 0, 0, 0, speed2 * 4, 0F),
						new AnimatedPart(headBottom, -1F, -15F, 0, 0, 0, 0, speed2 * 4, 0F),
						new AnimatedPart(body, 0, 0, 10F, 0, 0, 0, speed2, 0F),
						new AnimatedPart(tail1, 0, 0, 15F, 0, 0, 0, speed2, -0.05F),
						new AnimatedPart(tail2, 0, 0, 20F, 0, 0, 0, speed2, -0.1F),
						new AnimatedPart(tail3, 0, 0, 25F, 0, 0, 0, speed2, -0.15F),
						new AnimatedPart(tail4, 0, 0, 30F, 0, 0, 0, speed2, -0.2F),
						new AnimatedPart(tail5, 0, 0, 35F, 0, 0, 0, speed2, -0.25F),
						new AnimatedPart(tail6, 0, 0, 40F, 0, 0, 0, speed2, -0.3F)),
				new KeyFrame(10, 0, 18F, 4F,
						new AnimatedPart(headTop, -25F, 0, 0),
						new AnimatedPart(headBottom, 25F, 0, 0),
						new AnimatedPart(body, 0, 0, 5F, 0, 0, 0, speed2, 0F),
						new AnimatedPart(tail1, 0, 0, 10F, 0, 0, 0, speed2, -0.05F),
						new AnimatedPart(tail2, 0, 0, 15F, 0, 0, 0, speed2, -0.1F),
						new AnimatedPart(tail3, 0, 0, 20F, 0, 0, 0, speed2, -0.15F),
						new AnimatedPart(tail4, 0, 0, 25F, 0, 0, 0, speed2, -0.2F),
						new AnimatedPart(tail5, 0, 0, 25F, 0, 0, 0, speed2, -0.25F),
						new AnimatedPart(tail6, 0, 0, 25F, 0, 0, 0, speed2, -0.3F)));
		final float headMod = entity instanceof IHekateProvider ? 1F - ((IHekateProvider)entity).getHekateProvider().getModifier("onGround1") : 1F;
		this.main.xRot += HekateLib.render.head(headPitch, headMod);
		this.main.yRot += HekateLib.render.head(netHeadYaw, headMod);
		this.main.xRot += HekateLib.render.head(netHeadYaw, 1F - headMod);
	}
}
