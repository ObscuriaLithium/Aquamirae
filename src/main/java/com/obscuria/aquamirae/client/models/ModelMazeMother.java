package com.obscuria.aquamirae.client.models;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.obscureapi.client.animations.HekateLib;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class ModelMazeMother<T extends Entity> extends EntityModel<T> {

	public final ModelRenderer main, main2, bodyTop, bodyBottom, tail1, tail2, tail3, tail4, tail5, jaw1, jaw2, jaw3, jaw4,
			wing1LeftTop, wing1LeftBottom, wing1RightTop, wing1RightBottom, wing2LeftTop, wing2LeftBottom, wing2RightTop, wing2RightBottom;

	public ModelMazeMother() {
		this.texWidth = 128;
		this.texHeight = 128;

		main = new ModelRenderer(this);
		main.setPos(0.0F, -126.0F, 0.0F);

		main2 = new ModelRenderer(this);
		main2.setPos(0.0F, 147.0F, 0.0F);
		main.addChild(main2);

		bodyTop = new ModelRenderer(this);
		bodyTop.setPos(0.0F, 0.0F, 0.0F);
		main2.addChild(bodyTop);
		bodyTop.texOffs(0, 45).addBox(-8.0F, -1.5F, -16.0F, 16.0F, 3.0F, 16.0F, 0.0F, false);

		jaw1 = new ModelRenderer(this);
		jaw1.setPos(-7.0F, 0.0F, -14.5F);
		bodyTop.addChild(jaw1);
		jaw1.texOffs(37, 26).addBox(-3.0F, 0.0F, -10.5F, 6.0F, 0.0F, 11.0F, 0.0F, false);

		jaw2 = new ModelRenderer(this);
		jaw2.setPos(7.0F, 0.0F, -14.5F);
		bodyTop.addChild(jaw2);
		jaw2.texOffs(49, 26).addBox(-3.0F, 0.0F, -10.5F, 6.0F, 0.0F, 11.0F, 0.0F, false);

		jaw3 = new ModelRenderer(this);
		jaw3.setPos(-3.5F, 0.0F, -15.5F);
		bodyTop.addChild(jaw3);
		jaw3.texOffs(65, 26).addBox(-1.5F, 0.0F, -5.5F, 3.0F, 0.0F, 7.0F, 0.0F, false);

		jaw4 = new ModelRenderer(this);
		jaw4.setPos(3.5F, 0.0F, -15.5F);
		bodyTop.addChild(jaw4);
		jaw4.texOffs(73, 26).addBox(-1.5F, 0.0F, -5.5F, 3.0F, 0.0F, 7.0F, 0.0F, false);

		final ModelRenderer cube1 = new ModelRenderer(this);
		cube1.setPos(0.0F, 0.0F, -16.0F);
		bodyTop.addChild(cube1);

		final ModelRenderer cube2 = new ModelRenderer(this);
		cube2.setPos(0.0F, -2.0F, 16.0F);
		cube1.addChild(cube2);
		setRotationAngle(cube2, 0.0F, 0.0F, -0.7854F);
		cube2.texOffs(32, 58).addBox(-2.0F, -10.5F, -16.0F, 0.0F, 10.0F, 16.0F, 0.0F, false);

		final ModelRenderer cube3 = new ModelRenderer(this);
		cube3.setPos(0.0F, 0.0F, -16.0F);
		bodyTop.addChild(cube3);

		final ModelRenderer cube4 = new ModelRenderer(this);
		cube4.setPos(0.0F, -2.0F, 16.0F);
		cube3.addChild(cube4);
		setRotationAngle(cube4, 0.0F, 0.0F, -0.3491F);
		cube4.texOffs(64, 45).addBox(-1.0F, -9.5F, -16.0F, 0.0F, 10.0F, 16.0F, 0.0F, false);




		PartDefinition crystal3_top = body_top.addOrReplaceChild("crystal3_top", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -16.0F));
		PartDefinition cube_r3 = crystal3_top.addOrReplaceChild("cube_r3",
				CubeListBuilder.create().texOffs(64, 65).addBox(1.0F, -9.5F, -16.0F, 0.0F, 10.0F, 16.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -2.0F, 16.0F, 0.0F, 0.0F, 0.3491F));
		PartDefinition crystal4_top = body_top.addOrReplaceChild("crystal4_top", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -16.0F));
		PartDefinition cube_r4 = crystal4_top.addOrReplaceChild("cube_r4",
				CubeListBuilder.create().texOffs(32, 48).addBox(2.0F, -10.5F, -16.0F, 0.0F, 10.0F, 16.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -2.0F, 16.0F, 0.0F, 0.0F, 0.7854F));
		PartDefinition left_wing1_top = body_top.addOrReplaceChild("left_wing1_top",
				CubeListBuilder.create().texOffs(48, 45).addBox(-9.0F, -1.0F, -14.0F, 9.0F, 2.0F, 14.0F, new CubeDeformation(0.0F)),
				PartPose.offset(-8.0F, 0.0F, 0.0F));
		PartDefinition left_wing2_top = left_wing1_top.addOrReplaceChild("left_wing2_top",
				CubeListBuilder.create().texOffs(92, 0).addBox(-5.0F, -1.0F, -11.0F, 5.0F, 2.0F, 11.0F, new CubeDeformation(0.0F)),
				PartPose.offset(-9.0F, 0.0F, 0.0F));
		PartDefinition right_wing1_top = body_top.addOrReplaceChild("right_wing1_top",
				CubeListBuilder.create().texOffs(60, 0).addBox(0.0F, -1.0F, -14.0F, 9.0F, 2.0F, 14.0F, new CubeDeformation(0.0F)),
				PartPose.offset(8.0F, 0.0F, 0.0F));
		PartDefinition right_wing2_top = right_wing1_top.addOrReplaceChild("right_wing2_top",
				CubeListBuilder.create().texOffs(47, 91).addBox(0.0F, -1.0F, -11.0F, 5.0F, 2.0F, 11.0F, new CubeDeformation(0.0F)),
				PartPose.offset(9.0F, 0.0F, 0.0F));
		PartDefinition body_bottom = main2.addOrReplaceChild(
				"body_bottom", CubeListBuilder.create().texOffs(0, 26).addBox(-8.0F, 0.5F, 0.0F, 16.0F, 3.0F, 16.0F, new CubeDeformation(0.0F))
						.texOffs(72, 26).addBox(-1.5F, -0.5F, 0.0F, 3.0F, 3.0F, 16.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, -2.0F, 0.0F));
		PartDefinition crystal1_bottom = body_bottom.addOrReplaceChild("crystal1_bottom", CubeListBuilder.create(),
				PartPose.offset(0.0F, 2.0F, 0.0F));
		PartDefinition cube_r5 = crystal1_bottom.addOrReplaceChild("cube_r5",
				CubeListBuilder.create().texOffs(0, 58).addBox(-2.0F, -10.5F, 0.0F, 0.0F, 10.0F, 16.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -2.0F, 0.0F, 0.0F, 0.0F, -0.7854F));
		PartDefinition crystal2_bottom = body_bottom.addOrReplaceChild("crystal2_bottom", CubeListBuilder.create(),
				PartPose.offset(0.0F, 2.0F, 0.0F));
		PartDefinition cube_r6 = crystal2_bottom.addOrReplaceChild("cube_r6",
				CubeListBuilder.create().texOffs(60, 0).addBox(-1.0F, -9.5F, 0.0F, 0.0F, 10.0F, 16.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -2.0F, 0.0F, 0.0F, 0.0F, -0.3491F));
		PartDefinition crystal3_bottom = body_bottom.addOrReplaceChild("crystal3_bottom", CubeListBuilder.create(),
				PartPose.offset(0.0F, 2.0F, 0.0F));
		PartDefinition cube_r7 = crystal3_bottom.addOrReplaceChild("cube_r7",
				CubeListBuilder.create().texOffs(64, 55).addBox(1.0F, -9.5F, 0.0F, 0.0F, 10.0F, 16.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -2.0F, 0.0F, 0.0F, 0.0F, 0.3491F));
		PartDefinition crystal4_bottom = body_bottom.addOrReplaceChild("crystal4_bottom", CubeListBuilder.create(),
				PartPose.offset(0.0F, 2.0F, 0.0F));
		PartDefinition cube_r8 = crystal4_bottom.addOrReplaceChild("cube_r8",
				CubeListBuilder.create().texOffs(0, 48).addBox(2.0F, -10.5F, 0.0F, 0.0F, 10.0F, 16.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -2.0F, 0.0F, 0.0F, 0.0F, 0.7854F));
		PartDefinition left_wing1_bottom = body_bottom.addOrReplaceChild("left_wing1_bottom",
				CubeListBuilder.create().texOffs(80, 45).addBox(-9.0F, -1.0F, 0.0F, 9.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)),
				PartPose.offset(-8.0F, 2.0F, 0.0F));
		PartDefinition left_wing2_bottom = left_wing1_bottom.addOrReplaceChild("left_wing2_bottom",
				CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -1.0F, 0.0F, 5.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)),
				PartPose.offset(-9.0F, 0.0F, 0.0F));
		PartDefinition right_wing1_bottom = body_bottom.addOrReplaceChild("right_wing1_bottom",
				CubeListBuilder.create().texOffs(20, 84).addBox(0.0F, -1.0F, 0.0F, 9.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)),
				PartPose.offset(8.0F, 2.0F, 0.0F));
		PartDefinition right_wing2_bottom = right_wing1_bottom.addOrReplaceChild("right_wing2_bottom",
				CubeListBuilder.create().texOffs(0, 7).addBox(0.0F, -1.0F, 0.0F, 5.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)),
				PartPose.offset(9.0F, 0.0F, 0.0F));
		PartDefinition tail1 = body_bottom.addOrReplaceChild("tail1",
				CubeListBuilder.create().texOffs(96, 57).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 9.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 1.0F, 16.0F));
		PartDefinition tail2 = tail1.addOrReplaceChild("tail2",
				CubeListBuilder.create().texOffs(98, 69).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 0.0F, 9.0F));
		PartDefinition tail3 = tail2
				.addOrReplaceChild("tail3",
						CubeListBuilder.create().texOffs(0, 87).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 9.0F, new CubeDeformation(0.0F))
								.texOffs(22, 22).addBox(-8.5F, 0.0F, 5.0F, 17.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)),
						PartPose.offset(0.0F, 0.0F, 7.0F));
		PartDefinition tail4 = tail3
				.addOrReplaceChild("tail4",
						CubeListBuilder.create().texOffs(2, 98).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
								.texOffs(19, 15).addBox(-8.5F, 0.0F, 0.0F, 17.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)),
						PartPose.offset(0.0F, 0.0F, 9.0F));
		PartDefinition tail5 = tail4
				.addOrReplaceChild("tail5",
						CubeListBuilder.create().texOffs(11, 0).addBox(-8.5F, 0.0F, 0.0F, 17.0F, 0.0F, 15.0F, new CubeDeformation(0.0F))
								.texOffs(0, 14).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)),
						PartPose.offset(0.0F, 0.0F, 7.0F));
		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}

	@Override
	public void renderToBuffer(MatrixStack poseStack, @Nonnull IVertexBuilder vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		poseStack.pushPose();
		poseStack.scale(5f, 5f, 5f);
		poseStack.translate(0F, -1F, 0F);
		main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		poseStack.popPose();
	}

	public void setupAnim(@Nonnull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		final float speed = 0.06F;

		HekateLib.render.tick(entity);
		HekateLib.render.prepare(main, main2, bodyTop, bodyBottom, tail1, tail2, tail3, tail4, tail5, jaw1, jaw2, jaw3, jaw4,
				wing1LeftTop, wing1LeftBottom, wing1RightTop, wing1RightBottom, wing2LeftTop, wing2LeftBottom, wing2RightTop, wing2RightBottom);

		HekateLib.i(bodyTop, -10F, 0, 0, 0, 0, 0, speed, 0F, ageInTicks, 1F);
		HekateLib.i(bodyBottom, -10F, 0, 0, 0, 0, 0, speed, -0.1F, ageInTicks, 1F);
		HekateLib.i(tail1, -10F, 0, 0, 0, 0, 0, speed, -0.2F, ageInTicks, 1F);
		HekateLib.i(tail2, -10F, 0, 0, 0, 0, 0, speed, -0.3F, ageInTicks, 1F);
		HekateLib.i(tail3, -10F, 0, 0, 0, 0, 0, speed, -0.4F, ageInTicks, 1F);
		HekateLib.i(tail4, -10F, 0, 0, 0, 0, 0, speed, -0.5F, ageInTicks, 1F);
		HekateLib.i(tail5, -10F, 0, 0, 0, 0, 0, speed, -0.6F, ageInTicks, 1F);
		HekateLib.i(wing1RightTop, 0, 0, 0, 0, 15F, 0, speed, -0.1F, ageInTicks, 1F);
		HekateLib.i(wing1LeftTop, 0, 0, 0, 0, -15F, 0, speed, -0.1F, ageInTicks, 1F);
		HekateLib.i(wing2RightTop, 0, 0, 0, 0, 15F, 0, speed, -0.2F, ageInTicks, 1F);
		HekateLib.i(wing2LeftTop, 0, 0, 0, 0, -15F, 0, speed, -0.2F, ageInTicks, 1F);
		this.wing1RightBottom.zRot = this.wing1RightTop.zRot;
		this.wing1LeftBottom.zRot = this.wing1LeftTop.zRot;
		this.wing2RightBottom.zRot = this.wing2RightTop.zRot;
		this.wing2LeftBottom.zRot = this.wing2LeftTop.zRot;
	}
}
