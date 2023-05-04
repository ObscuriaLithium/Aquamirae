package com.obscuria.aquamirae.client.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class ModelLuminousJelly<T extends Entity> extends EntityModel<T> {
	private final ModelPart main;

	public ModelLuminousJelly(ModelPart root) {
		this.main = root.getChild("main");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition head = main.addOrReplaceChild("head", CubeListBuilder.create().texOffs(4, 13).addBox(-2.0F, -17.0F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.2F))
		.texOffs(0, 0).addBox(-4.0F, -19.0F, -4.0F, 8.0F, 5.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(4, 20).addBox(-2.0F, -14.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition ten1_1 = main.addOrReplaceChild("ten1_1", CubeListBuilder.create().texOffs(24, 13).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, -12.5F, 0.0F));

		PartDefinition ten1_2 = ten1_1.addOrReplaceChild("ten1_2", CubeListBuilder.create().texOffs(0, 13).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 0.0F));

		PartDefinition ten1_3 = ten1_2.addOrReplaceChild("ten1_3", CubeListBuilder.create().texOffs(0, 16).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 0.0F));

		PartDefinition ten1_4 = ten1_3.addOrReplaceChild("ten1_4", CubeListBuilder.create().texOffs(0, 19).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 0.0F));

		PartDefinition ten1_5 = ten1_4.addOrReplaceChild("ten1_5", CubeListBuilder.create().texOffs(0, 22).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(20, 19).addBox(0.0F, 0.0F, -1.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 0.0F));

		PartDefinition ten1_6 = ten1_5.addOrReplaceChild("ten1_6", CubeListBuilder.create().texOffs(20, 21).addBox(0.0F, 0.0F, -1.0F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 0.0F));

		PartDefinition ten2_1 = main.addOrReplaceChild("ten2_1", CubeListBuilder.create().texOffs(24, 13).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, -12.5F, 0.0F));

		PartDefinition ten2_2 = ten2_1.addOrReplaceChild("ten2_2", CubeListBuilder.create().texOffs(0, 13).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 0.0F));

		PartDefinition ten2_3 = ten2_2.addOrReplaceChild("ten2_3", CubeListBuilder.create().texOffs(0, 16).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 0.0F));

		PartDefinition ten2_4 = ten2_3.addOrReplaceChild("ten2_4", CubeListBuilder.create().texOffs(0, 19).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 0.0F));

		PartDefinition ten2_5 = ten2_4.addOrReplaceChild("ten2_5", CubeListBuilder.create().texOffs(0, 22).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(20, 19).addBox(0.0F, 0.0F, -1.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 0.0F));

		PartDefinition ten2_6 = ten2_5.addOrReplaceChild("ten2_6", CubeListBuilder.create().texOffs(20, 21).addBox(0.0F, 0.0F, -1.0F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 0.0F));

		PartDefinition ten3_1 = main.addOrReplaceChild("ten3_1", CubeListBuilder.create().texOffs(24, 13).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, -12.5F, -1.5F, 0.0F, 1.5708F, 0.0F));

		PartDefinition ten3_2 = ten3_1.addOrReplaceChild("ten3_2", CubeListBuilder.create().texOffs(0, 13).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 0.0F));

		PartDefinition ten3_3 = ten3_2.addOrReplaceChild("ten3_3", CubeListBuilder.create().texOffs(0, 16).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 0.0F));

		PartDefinition ten3_4 = ten3_3.addOrReplaceChild("ten3_4", CubeListBuilder.create().texOffs(0, 19).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 0.0F));

		PartDefinition ten3_5 = ten3_4.addOrReplaceChild("ten3_5", CubeListBuilder.create().texOffs(0, 22).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(20, 19).addBox(0.0F, 0.0F, -1.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 0.0F));

		PartDefinition ten3_6 = ten3_5.addOrReplaceChild("ten3_6", CubeListBuilder.create().texOffs(20, 21).addBox(0.0F, 0.0F, -1.0F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 0.0F));

		PartDefinition ten4_1 = main.addOrReplaceChild("ten4_1", CubeListBuilder.create().texOffs(24, 13).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, -12.5F, 1.5F, 0.0F, 1.5708F, 0.0F));

		PartDefinition ten4_2 = ten4_1.addOrReplaceChild("ten4_2", CubeListBuilder.create().texOffs(0, 13).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 0.0F));

		PartDefinition ten4_3 = ten4_2.addOrReplaceChild("ten4_3", CubeListBuilder.create().texOffs(0, 16).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 0.0F));

		PartDefinition ten4_4 = ten4_3.addOrReplaceChild("ten4_4", CubeListBuilder.create().texOffs(0, 19).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 0.0F));

		PartDefinition ten4_5 = ten4_4.addOrReplaceChild("ten4_5", CubeListBuilder.create().texOffs(0, 22).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(20, 19).addBox(0.0F, 0.0F, -1.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 0.0F));

		PartDefinition ten4_6 = ten4_5.addOrReplaceChild("ten4_6", CubeListBuilder.create().texOffs(20, 21).addBox(0.0F, 0.0F, -1.0F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(@NotNull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}