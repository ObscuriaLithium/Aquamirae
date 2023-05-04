package com.obscuria.aquamirae.client.models.armor;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;

public class ModelThreeBoltArmor<T extends Entity> extends EntityModel<T> {
	public final ModelPart head, body, left_arm, right_arm, leggings_body, left_leg, right_leg, left_boot, right_boot;

	public ModelThreeBoltArmor(ModelPart root) {
		this.head = root.getChild("head");
		this.body = root.getChild("body");
		this.left_arm = root.getChild("left_arm");
		this.right_arm = root.getChild("right_arm");
		this.leggings_body = root.getChild("leggings_body");
		this.left_leg = root.getChild("left_leg");
		this.right_leg = root.getChild("right_leg");
		this.left_boot = root.getChild("left_boot");
		this.right_boot = root.getChild("right_boot");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition head = partdefinition.addOrReplaceChild("head",
				CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(1.0F)).texOffs(0, 0)
						.addBox(-4.0F, -9.75F, -4.0F, 8.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(18, 16)
						.addBox(-1.5F, -10.25F, -1.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition cube_r1 = head.addOrReplaceChild("cube_r1",
				CubeListBuilder.create().texOffs(18, 16).addBox(-1.5F, 0.75F, 5.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -9.5F, 7.0F, -1.5708F, 0.0F, 0.0F));
		PartDefinition cube_r2 = head.addOrReplaceChild("cube_r2",
				CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -5.75F, 1.5F, 8.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -9.5F, 0.0F, -1.5708F, 0.0F, 0.0F));
		PartDefinition bone3 = head.addOrReplaceChild("bone3",
				CubeListBuilder.create().texOffs(0, 18).addBox(-3.5F, -3.5F, -1.3F, 7.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 0)
						.addBox(3.0F, -3.0F, -0.3F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 0)
						.addBox(-4.0F, -3.0F, -0.3F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 16)
						.addBox(-4.0F, 3.0F, -0.3F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 16).addBox(-4.0F, -4.0F, -0.3F, 8.0F,
								1.0F, 1.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-5.25F, -4.0F, 0.05F, 0.0F, 1.5708F, 0.0F));
		PartDefinition bone2 = head.addOrReplaceChild("bone2",
				CubeListBuilder.create().texOffs(0, 18).addBox(-3.5F, -3.5F, -1.3F, 7.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 0)
						.addBox(3.0F, -3.0F, -0.3F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 0)
						.addBox(-4.0F, -3.0F, -0.3F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 16)
						.addBox(-4.0F, 3.0F, -0.3F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 16).addBox(-4.0F, -4.0F, -0.3F, 8.0F,
								1.0F, 1.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(5.25F, -4.0F, 0.05F, 0.0F, -1.5708F, 0.0F));
		PartDefinition bone = head.addOrReplaceChild("bone",
				CubeListBuilder.create().texOffs(0, 18).addBox(-3.5F, -31.5F, -6.5F, 7.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 0)
						.addBox(3.0F, -31.0F, -5.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 0)
						.addBox(-4.0F, -31.0F, -5.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 16)
						.addBox(-4.0F, -25.0F, -5.5F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 16)
						.addBox(-4.0F, -32.0F, -5.5F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 24.0F, 0.0F));
		PartDefinition body = partdefinition.addOrReplaceChild("body",
				CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.75F)).texOffs(0, 32)
						.addBox(0.25F, -2.0F, 3.0F, 4.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(0, 32)
						.addBox(-4.25F, -2.0F, 3.0F, 4.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition left_arm = partdefinition
				.addOrReplaceChild("left_arm",
						CubeListBuilder.create().texOffs(16, 16).addBox(-1.0F, -2.5F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.8F))
								.texOffs(0, 16).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)),
						PartPose.offset(5.0F, 2.0F, 0.0F));
		PartDefinition right_arm = partdefinition.addOrReplaceChild(
				"right_arm", CubeListBuilder.create().texOffs(0, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F))
						.texOffs(24, 0).addBox(-3.0F, -2.5F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.8F)),
				PartPose.offset(-5.0F, 2.0F, 0.0F));
		PartDefinition leggings_body = partdefinition.addOrReplaceChild("leggings_body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.4F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(16, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.34F)), PartPose.offset(2.0F, 12.0F, 0.0F));

		PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.35F)), PartPose.offset(-2.0F, 12.0F, 0.0F));

		PartDefinition left_boot = partdefinition.addOrReplaceChild("left_boot", CubeListBuilder.create().texOffs(16, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.74F)), PartPose.offset(2.0F, 12.0F, 0.0F));

		PartDefinition right_boot = partdefinition.addOrReplaceChild("right_boot", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.75F)), PartPose.offset(-2.0F, 12.0F, 0.0F));
		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green,
							   float blue, float alpha) {
		head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		left_arm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		right_arm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		leggings_body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		left_leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		right_leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		left_boot.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		right_boot.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}
}
