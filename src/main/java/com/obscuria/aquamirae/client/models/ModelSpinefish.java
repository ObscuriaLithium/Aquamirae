package com.obscuria.aquamirae.client.models;


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.obscuria.obscureapi.client.animations.HekateLib;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

import javax.annotation.Nonnull;

public class ModelSpinefish<T extends Entity> extends EntityModel<T> {

	private final ModelRenderer main, bodyTop, bodyBottom, head, tail;

	public ModelSpinefish() {
		this.texWidth = 32;
		this.texHeight = 32;

		main = new ModelRenderer(this);
		main.setPos(0.0F, 17.5F, 0.0F);

		bodyTop = new ModelRenderer(this);
		bodyTop.setPos(0.0F, 0.0F, 1.0F);
		main.addChild(bodyTop);
		bodyTop.texOffs(16, 8).addBox(-1.0F, -2.5F, -3.0F, 2.0F, 5.0F, 3.0F, 0.0F, false);
		bodyTop.texOffs(10, 0).addBox(0.0F, -6.5F, -3.0F, 0.0F, 13.0F, 3.0F, 0.0F, false);

		head = new ModelRenderer(this);
		head.setPos(0.0F, 0.0F, -2.9697F);
		bodyTop.addChild(head);
		head.texOffs(6, 13).addBox(0.0F, -5.5F, -6.0303F, 0.0F, 11.0F, 3.0F, 0.0F, false);
		head.texOffs(18, 16).addBox(-1.0F, -2.5F, -3.0303F, 2.0F, 5.0F, 3.0F, 0.0F, false);
		head.texOffs(0, 11).addBox(0.0F, -6.5F, -3.0303F, 0.0F, 13.0F, 3.0F, 0.0F, false);

		final ModelRenderer cube1 = new ModelRenderer(this);
		cube1.setPos(0.0F, 0.0F, -5.5303F);
		head.addChild(cube1);
		setRotationAngle(cube1, -0.7854F, 0.0F, 0.0F);
		cube1.texOffs(16, 0).addBox(-1.0F, -3.5F, -0.5F, 2.0F, 4.0F, 4.0F, 0.01F, false);

		bodyBottom = new ModelRenderer(this);
		bodyBottom.setPos(0.0F, 0.0F, 1.0F);
		main.addChild(bodyBottom);
		bodyBottom.texOffs(12, 13).addBox(0.0F, -5.5F, 0.0F, 0.0F, 11.0F, 3.0F, 0.0F, false);
		bodyBottom.texOffs(15, 24).addBox(-1.0F, -2.0F, 0.0F, 2.0F, 4.0F, 3.0F, 0.0F, false);

		tail = new ModelRenderer(this);
		tail.setPos(0.0F, 0.0F, 3.0F);
		bodyBottom.addChild(tail);
		tail.texOffs(0, 0).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);
		tail.texOffs(0, 0).addBox(0.0F, -4.5F, 0.0F, 0.0F, 9.0F, 5.0F, 0.0F, false);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}

	@Override
	public void setupAnim(@Nonnull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		final float speed = 0.5F;
		this.main.zRot = entity.isInWater() ? 0 : (float) Math.toRadians(-90F);
		this.main.y = entity.isInWater() ? 17.5F : 22F;
		this.bodyTop.yRot = HekateLib.render.idle(8F, 0F, speed, 0F, ageInTicks, 1F);
		this.head.yRot = HekateLib.render.idle(8F, 0F, speed, 0.1F, ageInTicks, 1F);
		this.bodyBottom.yRot = HekateLib.render.idle(-16F, 0F, speed, 0.1F, ageInTicks, 1F);
		this.tail.yRot = HekateLib.render.idle(-18F, 0F, speed, 0.2F, ageInTicks, 1F);
	}

	@Override
	public void renderToBuffer(@Nonnull MatrixStack poseStack, @Nonnull IVertexBuilder vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}