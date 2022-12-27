package com.obscuria.aquamirae.client.models.armor;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

import javax.annotation.Nonnull;

public class ModelThreeBoltArmor<T extends Entity> extends EntityModel<T> {

	public final ModelRenderer head, body, leftArm, rightArm, leggingsBody, leftLeg, rightLeg, leftBoot, rightBoot;

	public ModelThreeBoltArmor() {
		this.texWidth = 64;
		this.texHeight = 64;

		head = new ModelRenderer(this);
		head.setPos(0.0F, 0.0F, 0.0F);
		head.texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 1.0F, false);
		head.texOffs(0, 0).addBox(-4.0F, -9.75F, -4.0F, 8.0F, 1.0F, 8.0F, 0.0F, false);
		head.texOffs(18, 16).addBox(-1.5F, -10.25F, -1.5F, 3.0F, 1.0F, 3.0F, 0.0F, false);

		final ModelRenderer cube1 = new ModelRenderer(this);
		cube1.setPos(0.0F, -9.5F, 7.0F);
		head.addChild(cube1);
		setRotationAngle(cube1, -1.5708F, 0.0F, 0.0F);
		cube1.texOffs(18, 16).addBox(-1.5F, 0.75F, 5.5F, 3.0F, 1.0F, 3.0F, 0.0F, false);

		final ModelRenderer cube2 = new ModelRenderer(this);
		cube2.setPos(0.0F, -9.5F, 0.0F);
		head.addChild(cube2);
		setRotationAngle(cube2, -1.5708F, 0.0F, 0.0F);
		cube2.texOffs(0, 0).addBox(-4.0F, -5.75F, 1.5F, 8.0F, 1.0F, 8.0F, 0.0F, false);

		final ModelRenderer cube3 = new ModelRenderer(this);
		cube3.setPos(-5.25F, -4.0F, 0.05F);
		head.addChild(cube3);
		setRotationAngle(cube3, 0.0F, 1.5708F, 0.0F);
		cube3.texOffs(0, 18).addBox(-3.5F, -3.5F, -1.3F, 7.0F, 7.0F, 1.0F, 0.0F, false);
		cube3.texOffs(0, 0).addBox(3.0F, -3.0F, -0.3F, 1.0F, 6.0F, 1.0F, 0.0F, false);
		cube3.texOffs(0, 16).addBox(-4.0F, 3.0F, -0.3F, 8.0F, 1.0F, 1.0F, 0.0F, false);
		cube3.texOffs(0, 16).addBox(-4.0F, -4.0F, -0.3F, 8.0F, 1.0F, 1.0F, 0.0F, false);

		final ModelRenderer cube4 = new ModelRenderer(this);
		cube4.setPos(5.25F, -4.0F, 0.05F);
		head.addChild(cube4);
		setRotationAngle(cube4, 0.0F, -1.5708F, 0.0F);
		cube4.texOffs(0, 18).addBox(-3.5F, -3.5F, -1.3F, 7.0F, 7.0F, 1.0F, 0.0F, false);
		cube4.texOffs(0, 0).addBox(3.0F, -3.0F, -0.3F, 1.0F, 6.0F, 1.0F, 0.0F, false);
		cube4.texOffs(0, 0).addBox(-4.0F, -3.0F, -0.3F, 1.0F, 6.0F, 1.0F, 0.0F, false);
		cube4.texOffs(0, 16).addBox(-4.0F, 3.0F, -0.3F, 8.0F, 1.0F, 1.0F, 0.0F, false);
		cube4.texOffs(0, 16).addBox(-4.0F, -4.0F, -0.3F, 8.0F, 1.0F, 1.0F, 0.0F, false);

		final ModelRenderer cube5 = new ModelRenderer(this);
		cube5.setPos(0.0F, 24.0F, 0.0F);
		head.addChild(cube5);
		cube5.texOffs(0, 18).addBox(-3.5F, -31.5F, -6.5F, 7.0F, 7.0F, 1.0F, 0.0F, false);
		cube5.texOffs(0, 0).addBox(3.0F, -31.0F, -5.5F, 1.0F, 6.0F, 1.0F, 0.0F, false);
		cube5.texOffs(0, 0).addBox(-4.0F, -31.0F, -5.5F, 1.0F, 6.0F, 1.0F, 0.0F, false);
		cube5.texOffs(0, 16).addBox(-4.0F, -25.0F, -5.5F, 8.0F, 1.0F, 1.0F, 0.0F, false);
		cube5.texOffs(0, 16).addBox(-4.0F, -32.0F, -5.5F, 8.0F, 1.0F, 1.0F, 0.0F, false);

		body = new ModelRenderer(this);
		body.setPos(0.0F, 0.0F, 0.0F);
		body.texOffs(0, 0).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.75F, false);
		body.texOffs(0, 32).addBox(0.25F, -2.0F, 3.0F, 4.0F, 10.0F, 4.0F, 0.0F, false);
		body.texOffs(0, 32).addBox(-4.25F, -2.0F, 3.0F, 4.0F, 10.0F, 4.0F, 0.0F, false);

		leftArm = new ModelRenderer(this);
		leftArm.setPos(5.0F, 2.0F, 0.0F);
		leftArm.texOffs(16, 16).addBox(-1.0F, -2.5F, -2.0F, 4.0F, 12.0F, 4.0F, 0.8F, false);
		leftArm.texOffs(0, 16).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.25F, false);

		rightArm = new ModelRenderer(this);
		rightArm.setPos(-5.0F, 2.0F, 0.0F);
		rightArm.texOffs(0, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.25F, false);
		rightArm.texOffs(24, 0).addBox(-3.0F, -2.5F, -2.0F, 4.0F, 12.0F, 4.0F, 0.8F, false);

		leggingsBody = new ModelRenderer(this);
		leggingsBody.setPos(0.0F, 0.0F, 0.0F);
		leggingsBody.texOffs(0, 0).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.4F, false);

		leftLeg = new ModelRenderer(this);
		leftLeg.setPos(2.0F, 12.0F, 0.0F);
		leftLeg.texOffs(16, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.34F, false);

		rightLeg = new ModelRenderer(this);
		rightLeg.setPos(-2.0F, 12.0F, 0.0F);
		rightLeg.texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.35F, false);

		leftBoot = new ModelRenderer(this);
		leftBoot.setPos(2.0F, 12.0F, 0.0F);
		leftBoot.texOffs(16, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.74F, false);

		rightBoot = new ModelRenderer(this);
		rightBoot.setPos(-2.0F, 12.0F, 0.0F);
		rightBoot.texOffs(0, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.75F, false);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}

	@Override
	public void renderToBuffer(@Nonnull MatrixStack poseStack, @Nonnull IVertexBuilder vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		leftArm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		rightArm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		leggingsBody.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		leftLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		rightLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		leftBoot.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		rightBoot.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setupAnim(@Nonnull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}
}
