package com.obscuria.aquamirae.client.model.projectile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.obscuria.aquamirae.common.entity.projectile.AbstractShard;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;

public class ModelShard<T extends AbstractShard> extends EntityModel<T> {
	private final ModelPart body;

	public ModelShard(ModelPart root) {
		this.body = root.getChild("body");
	}

	public static LayerDefinition createBodyLayer() {
		final var meshDefinition = new MeshDefinition();
		final var partDefinition = meshDefinition.getRoot();
		final var body = partDefinition.addOrReplaceChild("body", CubeListBuilder.create(),
				PartPose.offset(0.0F, 24.0F, 0.0F));
		body.addOrReplaceChild("bone", CubeListBuilder.create()
						.texOffs(0, 6).addBox(-8.0F, -2.5F, 0.0F, 16.0F, 5.0F, 0.0F, new CubeDeformation(0.0F))
						.texOffs(0, 0).addBox(-8.0F, 0.0F, -2.5F, 16.0F, 0.0F, 5.0F, new CubeDeformation(0.0F))
						.texOffs(9, 19).addBox(5.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
						.texOffs(0, 12).addBox(-5.0F, -1.5F, -1.5F, 10.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
						.texOffs(0, 19).addBox(-7.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));
		return LayerDefinition.create(meshDefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount,
						  float ageInTicks, float netHeadYaw, float headPitch) {
		final var scale = 0.5f * (1f - (float) Math.pow(1f - Math.min(ageInTicks / 20f, 1), 2));
		this.body.xRot = (float) Math.toRadians(netHeadYaw);
		this.body.yRot = (float) Math.toRadians(headPitch);
		this.body.xScale = scale;
		this.body.yScale = scale;
		this.body.zScale = scale;
	}

	@Override
	public void renderToBuffer(PoseStack pose, VertexConsumer consumer, int packedLight, int packedOverlay,
							   float red, float green, float blue, float alpha) {
		body.render(pose, consumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}