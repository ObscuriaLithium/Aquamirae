package com.obscuria.aquamirae.client.models;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.obscuria.obscureapi.client.animations.HekateLib;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

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

		final ModelRenderer cube5 = new ModelRenderer(this);
		cube5.setPos(0.0F, 0.0F, -16.0F);
		bodyTop.addChild(cube5);

		final ModelRenderer cube6 = new ModelRenderer(this);
		cube6.setPos(0.0F, -2.0F, 16.0F);
		cube5.addChild(cube6);
		setRotationAngle(cube6, 0.0F, 0.0F, 0.3491F);
		cube6.texOffs(64, 65).addBox(1.0F, -9.5F, -16.0F, 0.0F, 10.0F, 16.0F, 0.0F, false);

		final ModelRenderer cube7 = new ModelRenderer(this);
		cube7.setPos(0.0F, 0.0F, -16.0F);
		bodyTop.addChild(cube7);

		final ModelRenderer cube8 = new ModelRenderer(this);
		cube8.setPos(0.0F, -2.0F, 16.0F);
		cube7.addChild(cube8);
		setRotationAngle(cube8, 0.0F, 0.0F, 0.7854F);
		cube8.texOffs(32, 48).addBox(2.0F, -10.5F, -16.0F, 0.0F, 10.0F, 16.0F, 0.0F, false);

		wing1LeftTop = new ModelRenderer(this);
		wing1LeftTop.setPos(-8.0F, 0.0F, 0.0F);
		bodyTop.addChild(wing1LeftTop);
		wing1LeftTop.texOffs(48, 45).addBox(-9.0F, -1.0F, -14.0F, 9.0F, 2.0F, 14.0F, 0.0F, false);

		wing2LeftTop = new ModelRenderer(this);
		wing2LeftTop.setPos(-9.0F, 0.0F, 0.0F);
		wing1LeftTop.addChild(wing2LeftTop);
		wing2LeftTop.texOffs(92, 0).addBox(-5.0F, -1.0F, -11.0F, 5.0F, 2.0F, 11.0F, 0.0F, false);

		wing1RightTop = new ModelRenderer(this);
		wing1RightTop.setPos(8.0F, 0.0F, 0.0F);
		bodyTop.addChild(wing1RightTop);
		wing1RightTop.texOffs(60, 0).addBox(0.0F, -1.0F, -14.0F, 9.0F, 2.0F, 14.0F, 0.0F, false);

		wing2RightTop = new ModelRenderer(this);
		wing2RightTop.setPos(9.0F, 0.0F, 0.0F);
		wing1RightTop.addChild(wing2RightTop);
		wing2RightTop.texOffs(47, 91).addBox(0.0F, -1.0F, -11.0F, 5.0F, 2.0F, 11.0F, 0.0F, false);

		bodyBottom = new ModelRenderer(this);
		bodyBottom.setPos(0.0F, -2.0F, 0.0F);
		main2.addChild(bodyBottom);
		bodyBottom.texOffs(0, 26).addBox(-8.0F, 0.5F, 0.0F, 16.0F, 3.0F, 16.0F, 0.0F, false);
		bodyBottom.texOffs(72, 26).addBox(-1.5F, -0.5F, 0.0F, 3.0F, 3.0F, 16.0F, 0.0F, false);

		final ModelRenderer cube9 = new ModelRenderer(this);
		cube9.setPos(0.0F, 2.0F, 0.0F);
		bodyBottom.addChild(cube9);

		final ModelRenderer cube10 = new ModelRenderer(this);
		cube10.setPos(0.0F, -2.0F, 0.0F);
		cube9.addChild(cube10);
		setRotationAngle(cube10, 0.0F, 0.0F, -0.7854F);
		cube10.texOffs(0, 58).addBox(-2.0F, -10.5F, 0.0F, 0.0F, 10.0F, 16.0F, 0.0F, false);

		final ModelRenderer cube11 = new ModelRenderer(this);
		cube11.setPos(0.0F, 2.0F, 0.0F);
		bodyBottom.addChild(cube11);

		final ModelRenderer cube12 = new ModelRenderer(this);
		cube12.setPos(0.0F, -2.0F, 0.0F);
		cube11.addChild(cube12);
		setRotationAngle(cube12, 0.0F, 0.0F, -0.3491F);
		cube12.texOffs(60, 0).addBox(-1.0F, -9.5F, 0.0F, 0.0F, 10.0F, 16.0F, 0.0F, false);

		final ModelRenderer cube13 = new ModelRenderer(this);
		cube13.setPos(0.0F, 2.0F, 0.0F);
		bodyBottom.addChild(cube13);

		final ModelRenderer cube14 = new ModelRenderer(this);
		cube14.setPos(0.0F, -2.0F, 0.0F);
		cube13.addChild(cube14);
		setRotationAngle(cube14, 0.0F, 0.0F, 0.3491F);
		cube14.texOffs(64, 55).addBox(1.0F, -9.5F, 0.0F, 0.0F, 10.0F, 16.0F, 0.0F, false);

		final ModelRenderer cube15 = new ModelRenderer(this);
		cube15.setPos(0.0F, 2.0F, 0.0F);
		bodyBottom.addChild(cube15);

		final ModelRenderer cube16 = new ModelRenderer(this);
		cube16.setPos(0.0F, -2.0F, 0.0F);
		cube15.addChild(cube16);
		setRotationAngle(cube16, 0.0F, 0.0F, 0.7854F);
		cube16.texOffs(0, 48).addBox(2.0F, -10.5F, 0.0F, 0.0F, 10.0F, 16.0F, 0.0F, false);

		wing1LeftBottom = new ModelRenderer(this);
		wing1LeftBottom.setPos(-8.0F, 2.0F, 0.0F);
		bodyBottom.addChild(wing1LeftBottom);
		wing1LeftBottom.texOffs(80, 45).addBox(-9.0F, -1.0F, 0.0F, 9.0F, 2.0F, 10.0F, 0.0F, false);

		wing2LeftBottom = new ModelRenderer(this);
		wing2LeftBottom.setPos(-9.0F, 0.0F, 0.0F);
		wing1LeftBottom.addChild(wing2LeftBottom);
		wing2LeftBottom.texOffs(0, 0).addBox(-5.0F, -1.0F, 0.0F, 5.0F, 2.0F, 5.0F, 0.0F, false);

		wing1RightBottom = new ModelRenderer(this);
		wing1RightBottom.setPos(8.0F, 2.0F, 0.0F);
		bodyBottom.addChild(wing1RightBottom);
		wing1RightBottom.texOffs(20, 84).addBox(0.0F, -1.0F, 0.0F, 9.0F, 2.0F, 10.0F, 0.0F, false);

		wing2RightBottom = new ModelRenderer(this);
		wing2RightBottom.setPos(9.0F, 0.0F, 0.0F);
		wing1RightBottom.addChild(wing2RightBottom);
		wing2RightBottom.texOffs(0, 7).addBox(0.0F, -1.0F, 0.0F, 5.0F, 2.0F, 5.0F, 0.0F, false);

		tail1 = new ModelRenderer(this);
		tail1.setPos(0.0F, 1.0F, 16.0F);
		bodyBottom.addChild(tail1);
		tail1.texOffs(96, 57).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 9.0F, 0.0F, false);

		tail2 = new ModelRenderer(this);
		tail2.setPos(0.0F, 0.0F, 9.0F);
		tail1.addChild(tail2);
		tail2.texOffs(98, 69).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 7.0F, 0.0F, false);

		tail3 = new ModelRenderer(this);
		tail3.setPos(0.0F, 0.0F, 7.0F);
		tail2.addChild(tail3);
		tail3.texOffs(0, 87).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 9.0F, 0.0F, false);
		tail3.texOffs(22, 22).addBox(-8.5F, 0.0F, 5.0F, 17.0F, 0.0F, 4.0F, 0.0F, false);

		tail4 = new ModelRenderer(this);
		tail4.setPos(0.0F, 0.0F, 9.0F);
		tail3.addChild(tail4);
		tail4.texOffs(2, 98).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 7.0F, 0.0F, false);
		tail4.texOffs(19, 15).addBox(-8.5F, 0.0F, 0.0F, 17.0F, 0.0F, 7.0F, 0.0F,false);

		tail5 = new ModelRenderer(this);
		tail5.setPos(0.0F, 0.0F, 7.0F);
		tail4.addChild(tail5);
		tail5.texOffs(11, 0).addBox(-8.5F, 0.0F, 0.0F, 17.0F, 0.0F, 15.0F, 0.0F, false);
		tail5.texOffs(0, 14).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 8.0F, 0.0F, false);
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
