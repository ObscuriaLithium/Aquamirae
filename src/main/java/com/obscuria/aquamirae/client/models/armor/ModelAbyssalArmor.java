package com.obscuria.aquamirae.client.models.armor;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

import javax.annotation.Nonnull;

public class ModelAbyssalArmor<T extends Entity> extends EntityModel<T> {

	public final ModelRenderer tiara;
	public final ModelRenderer helmet;
	public final ModelRenderer body;
	public final ModelRenderer leftArm;
	public final ModelRenderer rightArm;
	public final ModelRenderer leftLeg;
	public final ModelRenderer rightLeg;
	public final ModelRenderer leftBoot;
	public final ModelRenderer rightBoot;

	public ModelAbyssalArmor() {
		this.texWidth = 64;
		this.texHeight = 64;

		tiara = new ModelRenderer(this);
		tiara.setPos(0.0F, 1.0F, 0.0F);
		tiara.texOffs(0, 0).addBox(-4.0F, -9.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.55F, false);

		helmet = new ModelRenderer(this);
		helmet.setPos(0.0F, 0.0F, 0.0F);
		helmet.texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 9.0F, 8.0F, 1.0F, false);

		body = new ModelRenderer(this);
		body.setPos(0.0F, 0.0F, 0.0F);
		body.texOffs(0, 0).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 13.0F, 4.0F, 0.75F, false);

		leftArm = new ModelRenderer(this);
		leftArm.setPos(5.0F, 2.0F, 0.0F);
		leftArm.texOffs(24, 10).addBox(-1.0F, -8.0F, 0.0F, 10.0F, 10.0F, 0.0F, 0.001F, false);
		leftArm.texOffs(16, 17).addBox(-1.0F, -3.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.8F, false);

		rightArm = new ModelRenderer(this);
		rightArm.setPos(-5.0F, 2.0F, 0.0F);
		rightArm.texOffs(24, 0).addBox(-9.0F, -8.0F, 0.0F, 10.0F, 10.0F, 0.0F, 0.001F, false);
		rightArm.texOffs(0, 17).addBox(-3.0F, -3.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.8F, false);

		leftLeg = new ModelRenderer(this);
		leftLeg.setPos(2.0F, 12.0F, 0.0F);
		leftLeg.texOffs(16, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.34F, false);

		rightLeg = new ModelRenderer(this);
		rightLeg.setPos(-2.0F, 12.0F, 0.0F);
		rightLeg.texOffs(0, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.35F, false);

		leftBoot = new ModelRenderer(this);
		leftBoot.setPos(2.0F, 12.0F, 0.0F);
		leftBoot.texOffs(16, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.74F, false);
		leftBoot.texOffs(8, 16).addBox(2.5F, 0.1F, 0.0F, 4.0F, 10.0F, 0.0F, 0.001F, false);

		rightBoot = new ModelRenderer(this);
		rightBoot.setPos(-2.0F, 12.0F, 0.0F);
		rightBoot.texOffs(0, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.75F, false);
		rightBoot.texOffs(0, 16).addBox(-6.5F, 0.1F, 0.0F, 4.0F, 10.0F, 0.0F, 0.001F, false);
	}

	@Override
	public void renderToBuffer(@Nonnull MatrixStack poseStack, @Nonnull IVertexBuilder vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		tiara.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		helmet.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		leftArm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		rightArm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		leftLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		rightLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		leftBoot.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		rightBoot.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setupAnim(@Nonnull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}
}
