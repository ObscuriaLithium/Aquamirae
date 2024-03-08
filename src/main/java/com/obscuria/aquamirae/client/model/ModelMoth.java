package com.obscuria.aquamirae.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.obscuria.aquamirae.common.entity.AbstractMoth;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;

public class ModelMoth<T extends AbstractMoth> extends EntityModel<T> {
	public final ModelPart main, leftWing, rightWing;

	public ModelMoth(ModelPart root) {
		this.main = root.getChild("main");
		this.leftWing = main.getChild("leftWing");
		this.rightWing = main.getChild("rightWing");
	}

	public static LayerDefinition createBodyLayer() {
		final var meshDefinition = new MeshDefinition();
		final var partDefinition = meshDefinition.getRoot();
		final var main = partDefinition.addOrReplaceChild("main", CubeListBuilder.create().texOffs(0, 17).addBox(-0.5F, -1.0F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));
		main.addOrReplaceChild("leftWing", CubeListBuilder.create().texOffs(8, 0).addBox(0.0F, -0.01F, -3.0F, 4.0F, 0.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(8, 7).addBox(0.0F, 0.0F, -3.0F, 4.0F, 0.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(8, 14).addBox(0.0F, 0.01F, -3.0F, 4.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.5F, 0.0F));
		main.addOrReplaceChild("rightWing", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -0.01F, -3.0F, 4.0F, 0.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(0, 7).addBox(-4.0F, 0.0F, -3.0F, 4.0F, 0.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(0, 14).addBox(-4.0F, 0.01F, -3.0F, 4.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.5F, 0.0F));
		return LayerDefinition.create(meshDefinition, 32, 32);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay,
							   float red, float green, float blue, float alpha) {
		main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		if (entity.onGround()) {
			this.leftWing.zRot = -1.3f + (float) (Math.cos(ageInTicks * 0.25f) * 0.1f);
			this.main.y = 23.5f;
		} else {
			this.leftWing.zRot = (float) (Math.cos(ageInTicks) * 1.25f);
			this.main.y = 23.5f + (float) (Math.cos(ageInTicks) * -0.4f);
		}
		this.rightWing.zRot = -this.leftWing.zRot;
		this.main.yRot = (float) Math.toRadians(netHeadYaw);
	}
}
