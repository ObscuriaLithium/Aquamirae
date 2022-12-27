package com.obscuria.aquamirae.client.models.armor;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

import javax.annotation.Nonnull;

public class ModelTerribleArmor<T extends Entity> extends EntityModel<T> {

	public final ModelRenderer head;
	public final ModelRenderer body;
	public final ModelRenderer leftArm;
	public final ModelRenderer rightArm;
	public final ModelRenderer leftBoot;
	public final ModelRenderer rightBoot;
	public final ModelRenderer leftLeg;
	public final ModelRenderer rightLeg;

	public ModelTerribleArmor() {
		this.texWidth = 64;
		this.texHeight = 64;

		head = new ModelRenderer(this);
		head.setPos(0.0F, 0.0F, 0.0F);
		head.texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 1.0F, false);
		head.texOffs(0, 16).addBox(-8.0F, -14.0F, 0.0F, 16.0F, 11.0F, 0.0F, 0.0F, false);

		body = new ModelRenderer(this);
		body.setPos(0.0F, 0.0F, 0.0F);
		body.texOffs(0, 0).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.75F, false);
		body.texOffs(0, 8).addBox(0.0F, -1.0F, 0.0F, 0.0F, 13.0F, 8.0F, 0.0F, false);

		leftArm = new ModelRenderer(this);
		leftArm.setPos(5.0F, 2.0F, 0.0F);
		leftArm.texOffs(16, 32).addBox(-1.0F, -7.0F, 0.0F, 8.0F, 10.0F, 0.0F, 0.0F, false);
		leftArm.texOffs(16, 16).addBox(-1.0F, -3.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.8F, false);

		rightArm = new ModelRenderer(this);
		rightArm.setPos(-5.0F, 2.0F, 0.0F);
		rightArm.texOffs(24, 0).addBox(-3.0F, -3.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.8F, false);
		rightArm.texOffs(0, 29).addBox(-7.0F, -7.0F, 0.0F, 8.0F, 10.0F, 0.0F, 0.0F, false);

		leftBoot = new ModelRenderer(this);
		leftBoot.setPos(2.0F, 12.0F, 0.0F);
		leftBoot.texOffs(0, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.75F, false);
		leftBoot.texOffs(0, 28).addBox(-3.5F, 7.0F, 0.0F, 8.0F, 11.0F, 0.0F, 0.0F, false);

		rightBoot = new ModelRenderer(this);
		rightBoot.setPos(-2.0F, 12.0F, 0.0F);
		rightBoot.texOffs(12, 12).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.75F, false);
		rightBoot.texOffs(16, 0).addBox(-4.5F, 7.0F, 0.0F, 8.0F, 11.0F, 0.0F, 0.0F, false);

		leftLeg = new ModelRenderer(this);
		leftLeg.setPos(2.0F, 12.0F, 0.0F);
		leftLeg.texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.5F, false);
		leftLeg.texOffs(0, 32).addBox(2.0F, -2.0F, 0.0F, 4.0F, 11.0F, 0.0F, 0.0F, false);

		rightLeg = new ModelRenderer(this);
		rightLeg.setPos(-2.0F, 12.0F, 0.0F);
		rightLeg.texOffs(16, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.5F, false);
		rightLeg.texOffs(24, 0).addBox(-6.0F, -2.0F, 0.0F, 4.0F, 11.0F, 0.0F, 0.0F, false);
	}

	@Override
	public void renderToBuffer(@Nonnull MatrixStack poseStack, @Nonnull IVertexBuilder vertexConsumer, int packedLight, int packedOverlay, float red, float green,
							   float blue, float alpha) {
		leftBoot.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		rightBoot.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		leftArm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		rightArm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		leftLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		rightLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setupAnim(@Nonnull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}
}
