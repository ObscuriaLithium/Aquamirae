package com.obscuria.aquamirae.client.models;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

import javax.annotation.Nonnull;

public class ModelPoisonedChakra<T extends Entity> extends EntityModel<T> {
	
	public final ModelRenderer main;

	public ModelPoisonedChakra() {
		texWidth = 64;
		texHeight = 64;

		main = new ModelRenderer(this);
		main.setPos(0.0F, 23.0F, 0.0F);

		final ModelRenderer part1 = new ModelRenderer(this);
		part1.setPos(-5.5F, 0.5F, -5.5F);
		main.addChild(part1);
		part1.texOffs(0, 0).addBox(-8.0F, -1.0F, -8.0F, 6.0F, 1.0F, 1.0F, 0.0F, false);
		part1.texOffs(0, 3).addBox(-7.0F, -1.0F, -7.0F, 6.0F, 1.0F, 1.0F, 0.0F, false);
		part1.texOffs(0, 6).addBox(-5.0F, -1.0F, -6.0F, 5.0F, 1.0F, 1.0F, 0.0F, false);
		part1.texOffs(0, 9).addBox(-4.0F, -1.0F, -5.0F, 5.0F, 1.0F, 1.0F, 0.0F, false);
		part1.texOffs(0, 12).addBox(-3.0F, -1.0F, -4.0F, 5.0F, 1.0F, 1.0F, 0.0F, false);
		part1.texOffs(0, 15).addBox(-2.0F, -1.0F, -3.0F, 6.0F, 1.0F, 1.0F, 0.0F, false);
		part1.texOffs(0, 18).addBox(-1.0F, -1.0F, -2.0F, 5.0F, 1.0F, 1.0F, 0.0F, false);
		part1.texOffs(0, 21).addBox(-1.0F, -1.0F, -1.0F, 5.0F, 1.0F, 1.0F, 0.0F, false);
		part1.texOffs(0, 24).addBox(-2.0F, -1.0F, 0.0F, 6.0F, 1.0F, 1.0F, 0.0F, false);
		part1.texOffs(0, 27).addBox(-2.0F, -1.0F, 1.0F, 5.0F, 1.0F, 1.0F, 0.0F, false);
		part1.texOffs(0, 30).addBox(-3.0F, -1.0F, 2.0F, 5.0F, 1.0F, 1.0F, 0.0F, false);
		part1.texOffs(0, 33).addBox(-3.0F, -1.0F, 3.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
		part1.texOffs(0, 36).addBox(-4.0F, -1.0F, 4.0F, 6.0F, 1.0F, 1.0F, 0.0F, false);
		part1.texOffs(0, 39).addBox(-5.0F, -1.0F, 5.0F, 8.0F, 1.0F, 1.0F, 0.0F, false);
		part1.texOffs(0, 42).addBox(-4.0F, -1.0F, 6.0F, 6.0F, 1.0F, 1.0F, 0.0F, false);

		final ModelRenderer part2 = new ModelRenderer(this);
		part2.setPos(-5.5F, 0.5F, 5.5F);
		main.addChild(part2);
		setRotationAngle(part2, 0.0F, 1.5708F, 0.0F);
		part2.texOffs(0, 0).addBox(-8.0F, -1.0F, -8.0F, 6.0F, 1.0F, 1.0F, 0.0F, false);
		part2.texOffs(0, 3).addBox(-7.0F, -1.0F, -7.0F, 6.0F, 1.0F, 1.0F, 0.0F, false);
		part2.texOffs(0, 6).addBox(-5.0F, -1.0F, -6.0F, 5.0F, 1.0F, 1.0F, 0.0F, false);
		part2.texOffs(0, 9).addBox(-4.0F, -1.0F, -5.0F, 5.0F, 1.0F, 1.0F, 0.0F, false);
		part2.texOffs(0, 12).addBox(-3.0F, -1.0F, -4.0F, 5.0F, 1.0F, 1.0F, 0.0F, false);
		part2.texOffs(0, 15).addBox(-2.0F, -1.0F, -3.0F, 6.0F, 1.0F, 1.0F, 0.0F, false);
		part2.texOffs(0, 18).addBox(-1.0F, -1.0F, -2.0F, 5.0F, 1.0F, 1.0F, 0.0F, false);
		part2.texOffs(0, 21).addBox(-1.0F, -1.0F, -1.0F, 5.0F, 1.0F, 1.0F, 0.0F, false);
		part2.texOffs(0, 24).addBox(-2.0F, -1.0F, 0.0F, 6.0F, 1.0F, 1.0F, 0.0F, false);
		part2.texOffs(0, 27).addBox(-2.0F, -1.0F, 1.0F, 5.0F, 1.0F, 1.0F, 0.0F, false);
		part2.texOffs(0, 30).addBox(-3.0F, -1.0F, 2.0F, 5.0F, 1.0F, 1.0F, 0.0F, false);
		part2.texOffs(0, 33).addBox(-3.0F, -1.0F, 3.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
		part2.texOffs(0, 36).addBox(-4.0F, -1.0F, 4.0F, 6.0F, 1.0F, 1.0F, 0.0F, false);
		part2.texOffs(0, 39).addBox(-5.0F, -1.0F, 5.0F, 8.0F, 1.0F, 1.0F, 0.0F, false);
		part2.texOffs(0, 42).addBox(-4.0F, -1.0F, 6.0F, 6.0F, 1.0F, 1.0F, 0.0F, false);

		final ModelRenderer part3 = new ModelRenderer(this);
		part3.setPos(5.5F, 0.5F, 5.5F);
		main.addChild(part3);
		setRotationAngle(part3, -3.1416F, 0.0F, 3.1416F);
		part3.texOffs(0, 0).addBox(-8.0F, -1.0F, -8.0F, 6.0F, 1.0F, 1.0F, 0.0F, false);
		part3.texOffs(0, 3).addBox(-7.0F, -1.0F, -7.0F, 6.0F, 1.0F, 1.0F, 0.0F, false);
		part3.texOffs(0, 6).addBox(-5.0F, -1.0F, -6.0F, 5.0F, 1.0F, 1.0F, 0.0F, false);
		part3.texOffs(0, 9).addBox(-4.0F, -1.0F, -5.0F, 5.0F, 1.0F, 1.0F, 0.0F, false);
		part3.texOffs(0, 12).addBox(-3.0F, -1.0F, -4.0F, 5.0F, 1.0F, 1.0F, 0.0F, false);
		part3.texOffs(0, 15).addBox(-2.0F, -1.0F, -3.0F, 6.0F, 1.0F, 1.0F, 0.0F, false);
		part3.texOffs(0, 18).addBox(-1.0F, -1.0F, -2.0F, 5.0F, 1.0F, 1.0F, 0.0F, false);
		part3.texOffs(0, 21).addBox(-1.0F, -1.0F, -1.0F, 5.0F, 1.0F, 1.0F, 0.0F, false);
		part3.texOffs(0, 24).addBox(-2.0F, -1.0F, 0.0F, 6.0F, 1.0F, 1.0F, 0.0F, false);
		part3.texOffs(0, 27).addBox(-2.0F, -1.0F, 1.0F, 5.0F, 1.0F, 1.0F, 0.0F, false);
		part3.texOffs(0, 30).addBox(-3.0F, -1.0F, 2.0F, 5.0F, 1.0F, 1.0F, 0.0F, false);
		part3.texOffs(0, 33).addBox(-3.0F, -1.0F, 3.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
		part3.texOffs(0, 36).addBox(-4.0F, -1.0F, 4.0F, 6.0F, 1.0F, 1.0F, 0.0F, false);
		part3.texOffs(0, 39).addBox(-5.0F, -1.0F, 5.0F, 8.0F, 1.0F, 1.0F, 0.0F, false);
		part3.texOffs(0, 42).addBox(-4.0F, -1.0F, 6.0F, 6.0F, 1.0F, 1.0F, 0.0F, false);

		final ModelRenderer part4 = new ModelRenderer(this);
		part4.setPos(5.5F, 0.5F, -5.5F);
		main.addChild(part4);
		setRotationAngle(part4, 0.0F, -1.5708F, 0.0F);
		part4.texOffs(0, 0).addBox(-8.0F, -1.0F, -8.0F, 6.0F, 1.0F, 1.0F, 0.0F, false);
		part4.texOffs(0, 3).addBox(-7.0F, -1.0F, -7.0F, 6.0F, 1.0F, 1.0F, 0.0F, false);
		part4.texOffs(0, 6).addBox(-5.0F, -1.0F, -6.0F, 5.0F, 1.0F, 1.0F, 0.0F, false);
		part4.texOffs(0, 9).addBox(-4.0F, -1.0F, -5.0F, 5.0F, 1.0F, 1.0F, 0.0F, false);
		part4.texOffs(0, 12).addBox(-3.0F, -1.0F, -4.0F, 5.0F, 1.0F, 1.0F, 0.0F, false);
		part4.texOffs(0, 15).addBox(-2.0F, -1.0F, -3.0F, 6.0F, 1.0F, 1.0F, 0.0F, false);
		part4.texOffs(0, 18).addBox(-1.0F, -1.0F, -2.0F, 5.0F, 1.0F, 1.0F, 0.0F, false);
		part4.texOffs(0, 21).addBox(-1.0F, -1.0F, -1.0F, 5.0F, 1.0F, 1.0F, 0.0F, false);
		part4.texOffs(0, 24).addBox(-2.0F, -1.0F, 0.0F, 6.0F, 1.0F, 1.0F, 0.0F, false);
		part4.texOffs(0, 27).addBox(-2.0F, -1.0F, 1.0F, 5.0F, 1.0F, 1.0F, 0.0F, false);
		part4.texOffs(0, 30).addBox(-3.0F, -1.0F, 2.0F, 5.0F, 1.0F, 1.0F, 0.0F, false);
		part4.texOffs(0, 33).addBox(-3.0F, -1.0F, 3.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
		part4.texOffs(0, 36).addBox(-4.0F, -1.0F, 4.0F, 6.0F, 1.0F, 1.0F, 0.0F, false);
		part4.texOffs(0, 39).addBox(-5.0F, -1.0F, 5.0F, 8.0F, 1.0F, 1.0F, 0.0F, false);
		part4.texOffs(0, 42).addBox(-4.0F, -1.0F, 6.0F, 6.0F, 1.0F, 1.0F, 0.0F, false);
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
		this.main.yRot = ageInTicks * 0.8F;
	}
}
