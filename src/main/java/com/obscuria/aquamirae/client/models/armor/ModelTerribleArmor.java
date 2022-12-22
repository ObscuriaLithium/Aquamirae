package com.obscuria.aquamirae.client.models.armor;

import com.obscuria.aquamirae.AquamiraeMod;
import net.minecraft.world.entity.Entity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.EntityModel;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;

public class ModelTerribleArmor<T extends Entity> extends EntityModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(AquamiraeMod.MODID, "terrible_armor"),
			"main");
	public final ModelPart left_shoe;
	public final ModelPart right_shoe;
	public final ModelPart body;
	public final ModelPart left_arm;
	public final ModelPart right_arm;
	public final ModelPart head;
	public final ModelPart body2;
	public final ModelPart left_shoe2;
	public final ModelPart right_shoe2;

	public ModelTerribleArmor(ModelPart root) {
		this.left_shoe = root.getChild("left_shoe");
		this.right_shoe = root.getChild("right_shoe");
		this.body = root.getChild("body");
		this.left_arm = root.getChild("left_arm");
		this.right_arm = root.getChild("right_arm");
		this.head = root.getChild("head");
		this.body2 = root.getChild("body2");
		this.left_shoe2 = root.getChild("left_shoe2");
		this.right_shoe2 = root.getChild("right_shoe2");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition left_shoe = partdefinition
				.addOrReplaceChild("left_shoe",
						CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.75F))
								.texOffs(0, 28).addBox(-3.5F, 7.0F, 0.0F, 8.0F, 11.0F, 0.0F, new CubeDeformation(0.0F)),
						PartPose.offset(2.0F, 12.0F, 0.0F));
		PartDefinition right_shoe = partdefinition
				.addOrReplaceChild("right_shoe",
						CubeListBuilder.create().texOffs(12, 12).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.75F))
								.texOffs(16, 0).addBox(-4.5F, 7.0F, 0.0F, 8.0F, 11.0F, 0.0F, new CubeDeformation(0.0F)),
						PartPose.offset(-2.0F, 12.0F, 0.0F));
		PartDefinition body = partdefinition
				.addOrReplaceChild(
						"body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.75F))
								.texOffs(0, 8).addBox(0.0F, -1.0F, 0.0F, 0.0F, 13.0F, 8.0F, new CubeDeformation(0.0F)),
						PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition left_arm = partdefinition.addOrReplaceChild(
				"left_arm", CubeListBuilder.create().texOffs(16, 32).addBox(-1.0F, -7.0F, 0.0F, 8.0F, 10.0F, 0.0F, new CubeDeformation(0.0F))
						.texOffs(16, 16).addBox(-1.0F, -3.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.8F)),
				PartPose.offset(5.0F, 2.0F, 0.0F));
		PartDefinition right_arm = partdefinition
				.addOrReplaceChild("right_arm",
						CubeListBuilder.create().texOffs(24, 0).addBox(-3.0F, -3.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.8F))
								.texOffs(0, 29).addBox(-7.0F, -7.0F, 0.0F, 8.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)),
						PartPose.offset(-5.0F, 2.0F, 0.0F));
		PartDefinition head = partdefinition
				.addOrReplaceChild(
						"head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(1.0F))
								.texOffs(0, 16).addBox(-8.0F, -14.0F, 0.0F, 16.0F, 11.0F, 0.0F, new CubeDeformation(0.0F)),
						PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition body2 = partdefinition.addOrReplaceChild("body2",
				CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -1.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.5F)),
				PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition left_shoe2 = partdefinition
				.addOrReplaceChild("left_shoe2",
						CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F))
								.texOffs(0, 32).addBox(2.0F, -2.0F, 0.0F, 4.0F, 11.0F, 0.0F, new CubeDeformation(0.0F)),
						PartPose.offset(2.0F, 12.0F, 0.0F));
		PartDefinition right_shoe2 = partdefinition.addOrReplaceChild(
				"right_shoe2", CubeListBuilder.create().texOffs(16, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F))
						.texOffs(24, 0).addBox(-6.0F, -2.0F, 0.0F, 4.0F, 11.0F, 0.0F, new CubeDeformation(0.0F)),
				PartPose.offset(-2.0F, 12.0F, 0.0F));
		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green,
			float blue, float alpha) {
		left_shoe.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		right_shoe.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		left_arm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		right_arm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		body2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		left_shoe2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		right_shoe2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}
}
