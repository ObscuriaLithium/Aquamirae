package com.obscuria.aquamirae.client.models;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.obscuria.obscureapi.client.animations.HekateLib;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

import javax.annotation.Nonnull;

public class ModelGoldenMoth<T extends Entity> extends EntityModel<T> {
	public final ModelRenderer body, wing1, wing2;

	public ModelGoldenMoth() {
		this.texWidth = 32;
		this.texHeight = 32;

		body = new ModelRenderer(this);
		body.setPos(0.0F, 23.0F, 0.0F);
		body.texOffs(0, 10).addBox(-0.5F, -0.5F, -2.0F, 1.0F, 1.0F, 4.0F, 0.0F, false);

		wing1 = new ModelRenderer(this);
		wing1.setPos(0.0F, 23.0F, 0.5F);
		wing1.texOffs(0, 7).addBox(0.0F, 0.0F, -3.5F, 4.0F, 0.0F, 7.0F, 0.0F, false);

		wing2 = new ModelRenderer(this);
		wing2.setPos(0.0F, 23.0F, 0.5F);
		wing2.texOffs(0, 0).addBox(-4.0F, 0.0F, -3.5F, 4.0F, 0.0F, 7.0F, 0.0F, false);
	}

	@Override
	public void renderToBuffer(@Nonnull MatrixStack poseStack, @Nonnull IVertexBuilder vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		wing1.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		wing2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setupAnim(@Nonnull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.wing1.zRot = HekateLib.render.idle(75F, 0F, 1.5F, 0F, ageInTicks, 1F);
		this.wing2.zRot = -this.wing1.zRot;
	}
}
