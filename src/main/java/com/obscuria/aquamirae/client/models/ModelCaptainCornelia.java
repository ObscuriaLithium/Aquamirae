package com.obscuria.aquamirae.client.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.common.entities.CaptainCornelia;
import com.obscuria.obscureapi.api.animations.HekateLib;
import com.obscuria.obscureapi.api.animations.NamedModelPart;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;
import org.jetbrains.annotations.NotNull;

public class ModelCaptainCornelia extends EntityModel<CaptainCornelia> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(AquamiraeMod.MODID, "captain_cornelia"),
			"main");
	public final NamedModelPart main, bodyTop, bodyTop2, bodyBottom, head, rightBooby, leftBooby, rightArm, leftArm, rightLeg,
			leftLeg, rightArmBottom, leftArmBottom, rightLegBottom, leftLegBottom, item, ten1, ten1_1, ten1_2, ten1_3, ten1_4, ten2, ten2_1, ten2_2,
			ten2_3, ten2_4, ten3, ten3_1, ten3_2, ten3_3, ten3_4;

	public ModelCaptainCornelia(ModelPart root) {
		this.main = NamedModelPart.create(root.getChild("main"), "main");
		this.bodyTop = NamedModelPart.create(main.PART.getChild("body_top_Z").getChild("body_top"), "bodyTop");
		this.bodyTop2 = NamedModelPart.create(bodyTop.PART.getChild("body_top2"), "bodyTop2");
		this.bodyBottom = NamedModelPart.create(main.PART.getChild("body_bottom_Z").getChild("body_bottom"), "bodyBottom");
		this.head = NamedModelPart.create(bodyTop2.PART.getChild("head"), "head");
		this.rightBooby = NamedModelPart.create(bodyTop2.PART.getChild("right_booby"), "rightBooby");
		this.leftBooby = NamedModelPart.create(bodyTop2.PART.getChild("left_booby"), "leftBooby");
		this.rightArm = NamedModelPart.create(bodyTop2.PART.getChild("right_arm"), "rightArm");
		this.leftArm = NamedModelPart.create(bodyTop2.PART.getChild("left_arm"), "leftArm");
		this.rightArmBottom = NamedModelPart.create(rightArm.PART.getChild("right_arm_bottom"), "rightArmBottom");
		this.leftArmBottom = NamedModelPart.create(leftArm.PART.getChild("left_arm_bottom"), "leftArmBottom");
		this.rightLeg = NamedModelPart.create(bodyBottom.PART.getChild("right_leg"), "rightLeg");
		this.leftLeg = NamedModelPart.create(bodyBottom.PART.getChild("left_leg"), "leftLeg");
		this.rightLegBottom = NamedModelPart.create(rightLeg.PART.getChild("right_leg_bottom"), "rightLegBottom");
		this.leftLegBottom = NamedModelPart.create(leftLeg.PART.getChild("left_leg_bottom"), "leftLegBottom");
		this.item = NamedModelPart.create(rightArmBottom.PART.getChild("bone"), "weapon");
		this.ten1 = NamedModelPart.create(bodyTop2.PART.getChild("ten1"), "ten1");
		this.ten1_1 = NamedModelPart.create(ten1.PART.getChild("ten1_1"), "ten1_1");
		this.ten1_2 = NamedModelPart.create(ten1_1.PART.getChild("ten1_2"), "ten1_2");
		this.ten1_3 = NamedModelPart.create(ten1_2.PART.getChild("ten1_3"), "ten1_3");
		this.ten1_4 = NamedModelPart.create(ten1_3.PART.getChild("ten1_4"), "ten1_4");
		this.ten2 = NamedModelPart.create(bodyTop2.PART.getChild("ten2"), "ten2");
		this.ten2_1 = NamedModelPart.create(ten2.PART.getChild("ten2_1"), "ten2_1");
		this.ten2_2 = NamedModelPart.create(ten2_1.PART.getChild("ten2_2"), "ten2_2");
		this.ten2_3 = NamedModelPart.create(ten2_2.PART.getChild("ten2_3"), "ten2_3");
		this.ten2_4 = NamedModelPart.create(ten2_3.PART.getChild("ten2_4"), "ten2_4");
		this.ten3 = NamedModelPart.create(bodyTop2.PART.getChild("ten3"), "ten3");
		this.ten3_1 = NamedModelPart.create(ten3.PART.getChild("ten3_1"), "ten3_1");
		this.ten3_2 = NamedModelPart.create(ten3_1.PART.getChild("ten3_2"), "ten3_2");
		this.ten3_3 = NamedModelPart.create(ten3_2.PART.getChild("ten3_3"), "ten3_3");
		this.ten3_4 = NamedModelPart.create(ten3_3.PART.getChild("ten3_4"), "ten3_4");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0F, 4.0F, 150.0F));
		PartDefinition body_top_Z = main.addOrReplaceChild("body_top_Z", CubeListBuilder.create(), PartPose.offset(0.0F, -25.5F, -150.0F));
		PartDefinition body_top = body_top_Z.addOrReplaceChild("body_top",
				CubeListBuilder.create().texOffs(32, 41).addBox(-2.5F, -6.5F, -2.0F, 5.0F, 8.0F, 4.0F, new CubeDeformation(0.2F)),
				PartPose.offset(0.0F, 30.0F, 0.0F));
		PartDefinition body_top2 = body_top.addOrReplaceChild("body_top2", CubeListBuilder.create(), PartPose.offset(0.0F, -5.0F, 0.0F));
		PartDefinition head = body_top2.addOrReplaceChild("head",
				CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(1.0F)).texOffs(24, 8)
						.addBox(-4.0F, -9.75F, -4.0F, 8.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(37, 54)
						.addBox(-1.5F, -10.25F, -1.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(72, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F,
								8.0F, 8.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -6.0F, 1.0F, 0.0873F, 0.0F, 0.0F));
		PartDefinition head1 = head.addOrReplaceChild("head1",
				CubeListBuilder.create().texOffs(28, 53).addBox(-1.5F, 0.75F, 5.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -9.5F, 7.0F, -1.5708F, 0.0F, 0.0F));
		PartDefinition head2 = head.addOrReplaceChild("head2",
				CubeListBuilder.create().texOffs(22, 22).addBox(-4.0F, -5.75F, 1.5F, 8.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -9.5F, 0.0F, -1.5708F, 0.0F, 0.0F));
		PartDefinition head3 = head.addOrReplaceChild("head3",
				CubeListBuilder.create().texOffs(12, 49).addBox(-3.5F, -3.5F, -1.05F, 7.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(12, 57)
						.addBox(3.0F, -3.0F, -0.3F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(56, 33)
						.addBox(-4.0F, -3.0F, -0.3F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(52, 31)
						.addBox(-4.0F, 3.0F, -0.3F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(50, 48).addBox(-4.0F, -4.0F, -0.3F, 8.0F,
								1.0F, 1.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-5.25F, -4.0F, 0.05F, 0.0F, 1.5708F, 0.0F));
		PartDefinition head4 = head.addOrReplaceChild("head4",
				CubeListBuilder.create().texOffs(46, 17).addBox(-3.5F, -3.5F, -1.05F, 7.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(44, 0)
						.addBox(3.0F, -3.0F, -0.3F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(40, 0)
						.addBox(-4.0F, -3.0F, -0.3F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(46, 27)
						.addBox(-4.0F, 3.0F, -0.3F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(46, 25).addBox(-4.0F, -4.0F, -0.3F, 8.0F,
								1.0F, 1.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(5.25F, -4.0F, 0.05F, 0.0F, -1.5708F, 0.0F));
		PartDefinition head5 = head.addOrReplaceChild("head5",
				CubeListBuilder.create().texOffs(24, 0).addBox(-3.5F, -31.5F, -6.25F, 7.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(4, 0)
						.addBox(3.0F, -31.0F, -5.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 0)
						.addBox(-4.0F, -31.0F, -5.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(24, 19)
						.addBox(-4.0F, -25.0F, -5.5F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(24, 17)
						.addBox(-4.0F, -32.0F, -5.5F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 24.0F, 0.0F));
		PartDefinition body = body_top2.addOrReplaceChild("body",
				CubeListBuilder.create().texOffs(32, 31).addBox(-4.0F, -1.0F, -2.0F, 8.0F, 6.0F, 4.0F, new CubeDeformation(0.3F)),
				PartPose.offset(0.0F, -4.5F, 0.0F));
		PartDefinition left_booby = body_top2.addOrReplaceChild("left_booby", CubeListBuilder.create(), PartPose.offset(2.0F, -0.75F, 0.25F));
		PartDefinition left_boobyF = left_booby.addOrReplaceChild("left_boobyF",
				CubeListBuilder.create().texOffs(50, 41).addBox(-2.0F, -2.5F, 0.25F, 4.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -1.0F, -4.0F, -0.5236F, -0.2269F, 0.0175F));
		PartDefinition right_booby = body_top2.addOrReplaceChild("right_booby", CubeListBuilder.create(), PartPose.offset(-2.0F, -0.75F, 0.25F));
		PartDefinition right_boobyF = right_booby.addOrReplaceChild("right_boobyF",
				CubeListBuilder.create().texOffs(47, 50).addBox(-2.0F, -2.5F, 0.25F, 4.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -1.0F, -4.0F, -0.5236F, 0.2269F, -0.0175F));
		PartDefinition left_arm = body_top2.addOrReplaceChild("left_arm",
				CubeListBuilder.create().texOffs(48, 0).addBox(0.25F, -1.0F, -2.0F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(77, 16)
						.addBox(0.25F, -1.0F, -2.0F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.2F)).texOffs(89, 16)
						.addBox(0.25F, -5.0F, -0.5F, 7.0F, 9.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(77, 16)
						.addBox(0.25F, -1.0F, -2.0F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.2F)),
				PartPose.offset(4.0F, -3.5F, 1.0F));
		PartDefinition left_arm_bottom = left_arm.addOrReplaceChild("left_arm_bottom",
				CubeListBuilder.create().texOffs(0, 64).addBox(-1.5F, -1.0F, -1.5F, 3.0F, 7.0F, 3.0F, new CubeDeformation(0.01F)),
				PartPose.offset(1.75F, 5.0F, -0.5F));
		PartDefinition right_arm = body_top2.addOrReplaceChild("right_arm",
				CubeListBuilder.create().texOffs(0, 48).addBox(-3.0F, -1.0F, -1.0F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)),
				PartPose.offset(-4.25F, -3.5F, 0.0F));
		PartDefinition right_arm_bottom = right_arm.addOrReplaceChild("right_arm_bottom",
				CubeListBuilder.create().texOffs(0, 64).addBox(-1.5F, -1.0F, -1.5F, 3.0F, 7.0F, 3.0F, new CubeDeformation(0.01F)),
				PartPose.offset(-1.5F, 5.0F, 0.5F));
		PartDefinition bone = right_arm_bottom.addOrReplaceChild("bone",
				CubeListBuilder.create().texOffs(84, 106).addBox(-0.5F, -0.5F, -10.5F, 1.0F, 1.0F, 21.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 4.5F, 0.0F));
		PartDefinition ten1 = body_top2.addOrReplaceChild("ten1",
				CubeListBuilder.create().texOffs(60, 0).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, -5.5F, 3.5F));
		PartDefinition ten1_1 = ten1.addOrReplaceChild("ten1_1",
				CubeListBuilder.create().texOffs(60, 0).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 4.0F, 0.0F));
		PartDefinition ten1_2 = ten1_1.addOrReplaceChild("ten1_2",
				CubeListBuilder.create().texOffs(60, 6).addBox(-1.5F, 0.0F, -1.0F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 4.0F, 0.0F));
		PartDefinition ten1_3 = ten1_2.addOrReplaceChild("ten1_3",
				CubeListBuilder.create().texOffs(60, 6).addBox(-1.5F, 0.0F, -1.0F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 4.0F, 0.0F));
		PartDefinition ten1_4 = ten1_3.addOrReplaceChild("ten1_4",
				CubeListBuilder.create().texOffs(60, 12).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 4.0F, 0.0F));
		PartDefinition ten2 = body_top2.addOrReplaceChild("ten2",
				CubeListBuilder.create().texOffs(60, 0).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offset(2.75F, -7.5F, 3.0F));
		PartDefinition ten2_1 = ten2.addOrReplaceChild("ten2_1",
				CubeListBuilder.create().texOffs(60, 0).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 4.0F, 0.0F));
		PartDefinition ten2_2 = ten2_1.addOrReplaceChild("ten2_2",
				CubeListBuilder.create().texOffs(60, 6).addBox(-1.5F, 0.0F, -1.0F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 4.0F, 0.0F));
		PartDefinition ten2_3 = ten2_2.addOrReplaceChild("ten2_3",
				CubeListBuilder.create().texOffs(60, 6).addBox(-1.5F, 0.0F, -1.0F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 4.0F, 0.0F));
		PartDefinition ten2_4 = ten2_3.addOrReplaceChild("ten2_4",
				CubeListBuilder.create().texOffs(60, 12).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 4.0F, 0.0F));
		PartDefinition ten3 = body_top2.addOrReplaceChild("ten3",
				CubeListBuilder.create().texOffs(60, 0).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offset(-2.75F, -7.5F, 3.0F));
		PartDefinition ten3_1 = ten3.addOrReplaceChild("ten3_1",
				CubeListBuilder.create().texOffs(60, 0).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 4.0F, 0.0F));
		PartDefinition ten3_2 = ten3_1.addOrReplaceChild("ten3_2",
				CubeListBuilder.create().texOffs(60, 6).addBox(-1.5F, 0.0F, -1.0F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 4.0F, 0.0F));
		PartDefinition ten3_3 = ten3_2.addOrReplaceChild("ten3_3",
				CubeListBuilder.create().texOffs(60, 6).addBox(-1.5F, 0.0F, -1.0F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 4.0F, 0.0F));
		PartDefinition ten3_4 = ten3_3.addOrReplaceChild("ten3_4",
				CubeListBuilder.create().texOffs(60, 12).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 4.0F, 0.0F));
		PartDefinition body_bottom_Z = main.addOrReplaceChild("body_bottom_Z", CubeListBuilder.create(), PartPose.offset(0.0F, 34.5F, -150.0F));
		PartDefinition body_bottom = body_bottom_Z.addOrReplaceChild("body_bottom",
				CubeListBuilder.create().texOffs(0, 16).addBox(-4.5F, -0.5F, -2.75F, 9.0F, 8.0F, 6.0F, new CubeDeformation(0.2F)),
				PartPose.offset(0.0F, -30.0F, 0.0F));
		PartDefinition right_leg = body_bottom.addOrReplaceChild("right_leg",
				CubeListBuilder.create().texOffs(16, 31).addBox(-2.0F, -1.5F, -2.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)),
				PartPose.offset(-2.5F, 3.5F, 0.0F));
		PartDefinition right_leg_bottom = right_leg.addOrReplaceChild("right_leg_bottom",
				CubeListBuilder.create().texOffs(12, 64).addBox(-2.0F, -1.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.01F)),
				PartPose.offset(0.0F, 5.5F, 0.0F));
		PartDefinition left_leg = body_bottom.addOrReplaceChild("left_leg",
				CubeListBuilder.create().texOffs(0, 30).addBox(-2.0F, -1.5F, -2.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)),
				PartPose.offset(2.5F, 3.5F, 0.0F));
		PartDefinition left_leg_bottom = left_leg.addOrReplaceChild("left_leg_bottom",
				CubeListBuilder.create().texOffs(12, 64).addBox(-2.0F, -1.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.01F)),
				PartPose.offset(0.0F, 5.5F, 0.0F));
		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	public void translateToHand(HumanoidArm arm, PoseStack pose) {
		this.main.PART.translateAndRotate(pose);
		this.main.PART.getChild("body_top_Z").translateAndRotate(pose);
		this.bodyTop.PART.translateAndRotate(pose);
		this.bodyTop2.PART.translateAndRotate(pose);
		if (arm == HumanoidArm.LEFT) {
			this.leftArm.PART.translateAndRotate(pose);
			this.leftArmBottom.PART.translateAndRotate(pose);
			this.item.PART.translateAndRotate(pose);
		} else {
			this.rightArm.PART.translateAndRotate(pose);
			this.rightArmBottom.PART.translateAndRotate(pose);
			this.item.PART.translateAndRotate(pose);
		}
	}

	@Override
	public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green,
							   float blue, float alpha) {
		main.PART.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setupAnim(@NotNull CaptainCornelia entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		entity.getAnimations().apply(main, bodyTop, bodyTop2, bodyBottom, head, rightBooby, leftBooby, rightArm, leftArm, rightLeg,
				leftLeg, rightArmBottom, leftArmBottom, rightLegBottom, leftLegBottom, item, ten1, ten1_1, ten1_2, ten1_3, ten1_4, ten2, ten2_1, ten2_2,
				ten2_3, ten2_4, ten3, ten3_1, ten3_2, ten3_3, ten3_4);
		this.head.PART.yRot += HekateLib.render.head(netHeadYaw, 0.33F);
		this.bodyTop.PART.yRot += HekateLib.render.head(netHeadYaw, 0.33F);
		this.bodyTop2.PART.yRot += HekateLib.render.head(netHeadYaw, 0.33F);
	}
}
