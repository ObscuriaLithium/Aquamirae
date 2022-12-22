package com.obscuria.aquamirae.client.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.obscureapi.client.animations.AnimatedPart;
import com.obscuria.obscureapi.client.animations.HekateLib;
import com.obscuria.obscureapi.client.animations.KeyFrame;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import org.jetbrains.annotations.NotNull;

public class ModelCaptainCornelia<T extends Entity> extends EntityModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(AquamiraeMod.MODID, "captain_cornelia"),
			"main");
	public final ModelPart main, bodyTopZ, bodyBottomZ, bodyTop, bodyTop2, bodyBottom, head, rightBooby, leftBooby, rightArm, leftArm, rightLeg,
			leftLeg, rightArmBottom, leftArmBottom, rightLegBottom, leftLegBottom, item, ten1, ten1_1, ten1_2, ten1_3, ten1_4, ten2, ten2_1, ten2_2,
			ten2_3, ten2_4, ten3, ten3_1, ten3_2, ten3_3, ten3_4;

	public ModelCaptainCornelia(ModelPart root) {
		this.main = root.getChild("main");
		this.bodyTopZ = main.getChild("body_top_Z");
		this.bodyBottomZ = main.getChild("body_bottom_Z");
		this.bodyTop = bodyTopZ.getChild("body_top");
		this.bodyTop2 = bodyTop.getChild("body_top2");
		this.bodyBottom = bodyBottomZ.getChild("body_bottom");
		this.head = bodyTop2.getChild("head");
		this.rightBooby = bodyTop2.getChild("right_booby");
		this.leftBooby = bodyTop2.getChild("left_booby");
		this.rightArm = bodyTop2.getChild("right_arm");
		this.leftArm = bodyTop2.getChild("left_arm");
		this.rightArmBottom = rightArm.getChild("right_arm_bottom");
		this.leftArmBottom = leftArm.getChild("left_arm_bottom");
		this.rightLeg = bodyBottom.getChild("right_leg");
		this.leftLeg = bodyBottom.getChild("left_leg");
		this.rightLegBottom = rightLeg.getChild("right_leg_bottom");
		this.leftLegBottom = leftLeg.getChild("left_leg_bottom");
		this.item = rightArmBottom.getChild("bone");
		this.ten1 = bodyTop2.getChild("ten1");
		this.ten1_1 = ten1.getChild("ten1_1");
		this.ten1_2 = ten1_1.getChild("ten1_2");
		this.ten1_3 = ten1_2.getChild("ten1_3");
		this.ten1_4 = ten1_3.getChild("ten1_4");
		this.ten2 = bodyTop2.getChild("ten2");
		this.ten2_1 = ten2.getChild("ten2_1");
		this.ten2_2 = ten2_1.getChild("ten2_2");
		this.ten2_3 = ten2_2.getChild("ten2_3");
		this.ten2_4 = ten2_3.getChild("ten2_4");
		this.ten3 = bodyTop2.getChild("ten3");
		this.ten3_1 = ten3.getChild("ten3_1");
		this.ten3_2 = ten3_1.getChild("ten3_2");
		this.ten3_3 = ten3_2.getChild("ten3_3");
		this.ten3_4 = ten3_3.getChild("ten3_4");
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
		this.main.translateAndRotate(pose);
		this.bodyTopZ.translateAndRotate(pose);
		this.bodyTop.translateAndRotate(pose);
		this.bodyTop2.translateAndRotate(pose);
		if (arm == HumanoidArm.LEFT) {
			this.leftArm.translateAndRotate(pose);
			this.leftArmBottom.translateAndRotate(pose);
			this.item.translateAndRotate(pose);
		} else {
			this.rightArm.translateAndRotate(pose);
			this.rightArmBottom.translateAndRotate(pose);
			this.item.translateAndRotate(pose);
		}
	}

	@Override
	public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green,
							   float blue, float alpha) {
		main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setupAnim(@NotNull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		final float idle = HekateLib.mod.idle(limbSwingAmount, 3F);
		final float move = HekateLib.mod.move(limbSwingAmount, 3F);
		final float s1 = 0.1F;
		final float s2 = 0.4F;

		HekateLib.render.tick(entity);
		HekateLib.render.prepare(main, bodyTopZ, bodyBottomZ, bodyTop, bodyTop2, bodyBottom, head, rightBooby, leftBooby, rightArm, leftArm, rightLeg,
				leftLeg, rightArmBottom, leftArmBottom, rightLegBottom, leftLegBottom, item, ten1, ten1_1, ten1_2, ten1_3, ten1_4, ten2, ten2_1,
				ten2_2, ten2_3, ten2_4, ten3, ten3_1, ten3_2, ten3_3, ten3_4);

		HekateLib.i(this.main, 0.6F, 1.2F, 0F, 0F, 0F, 0F, s1, 0F, ageInTicks, idle);
		HekateLib.i(this.bodyBottom, 15F, 20F, 0F, 0F, 0F, 0F, s1, -0.9F, ageInTicks, idle);
		HekateLib.i(this.bodyTop2, 6F, -6F, 0F, 0F, 0F, 0F, s1, 0.9F, ageInTicks, idle);
		HekateLib.i(this.head, 4F, -6F, 0F, 0F, 0F, 0F, s1, 0.8F, ageInTicks, idle);
		HekateLib.i(this.rightArm, 12F, -9F, 9F, -27F, 6F, 20F, s1, -0.95F, ageInTicks, idle);
		HekateLib.i(this.rightArmBottom, -16F, 20F, 0F, 0F, 0F, 0F, s1, -0.90F, ageInTicks, idle);
		HekateLib.i(this.item, -5F, -35F, 0F, 0F, 0F, 0F, s1, -0.85F, ageInTicks, idle);
		HekateLib.i(this.leftArm, 12F, -9F, -9F, 27F, -9F, -27F, s1, -0.95F, ageInTicks, idle);
		HekateLib.i(this.leftArmBottom, -24F, 30F, 0F, 0F, 0F, 0F, s1, -0.90F, ageInTicks, idle);
		HekateLib.i(this.rightLeg, 12F, 24F, 0.6F, -3F, 0.6F, -3F, s1, -0.8F, ageInTicks, idle);
		HekateLib.i(this.rightLegBottom, -12F, -48F, 0F, 0F, 0F, 0F, s1, -0.6F, ageInTicks, idle);
		HekateLib.i(this.leftLeg, 6F, -12F, 0.6F, 3F, 0.6F, 3F, s1, -0.8F, ageInTicks, idle);
		HekateLib.i(this.leftLegBottom, -24F, -27F, 0F, 0F, 0F, 0F, s1, -0.7F, ageInTicks, idle);
		HekateLib.i(this.ten1, 16F, -20F, 0F, 0F, 0F, 0F, s1, 0.35F, ageInTicks, idle);
		HekateLib.i(this.ten1_1, 16F, -12F, 0F, 0F, 0F, 0F, s1, 0.30F, ageInTicks, idle);
		HekateLib.i(this.ten1_2, 16F, -6F, 0F, 0F, 0F, 0F, s1, 0.25F, ageInTicks, idle);
		HekateLib.i(this.ten1_3, 16F, 0F, 0F, 0F, 0F, 0F, s1, 0.20F, ageInTicks, idle);
		HekateLib.i(this.ten1_4, 16F, 0F, 0F, 0F, 0F, 0F, s1, 0.15F, ageInTicks, idle);
		HekateLib.i(this.ten2, 16F, -20F, 0F, -29F, 0F, 0F, s1, 0.25F, ageInTicks, idle);
		HekateLib.i(this.ten2_1, 16F, -12F, 0F, 0F, 0F, 0F, s1, 0.20F, ageInTicks, idle);
		HekateLib.i(this.ten2_2, 16F, -6F, 0F, 0F, 0F, 0F, s1, 0.15F, ageInTicks, idle);
		HekateLib.i(this.ten2_3, 16F, 0F, 0F, 0F, 0F, 0F, s1, 0.10F, ageInTicks, idle);
		HekateLib.i(this.ten2_4, 16F, 0F, 0F, 0F, 0F, 0F, s1, 0.05F, ageInTicks, idle);
		HekateLib.i(this.ten3, 16F, -20F, 0F, 29F, 0F, 0F, s1, 0.45F, ageInTicks, idle);
		HekateLib.i(this.ten3_1, 16F, -12F, 0F, 0F, 0F, 0F, s1, 0.40F, ageInTicks, idle);
		HekateLib.i(this.ten3_2, 16F, -6F, 0F, 0F, 0F, 0F, s1, 0.35F, ageInTicks, idle);
		HekateLib.i(this.ten3_3, 16F, 0F, 0F, 0F, 0F, 0F, s1, 0.30F, ageInTicks, idle);
		HekateLib.i(this.ten3_4, 16F, 0F, 0F, 0F, 0F, 0F, s1, 0.25F, ageInTicks, idle);
		HekateLib.m(this.main, -1.4F, 1.8F, 0F, 0F, 0F, 0F, s2, 0F, limbSwing, move);
		HekateLib.m(this.bodyTop, 6F, -24F, 0F, 0F, 0F, 0F, s2, 0F, limbSwing, move);
		HekateLib.m(this.bodyTop2, 6F, 12F, 0F, 0F, 0F, 0F, s2, 0F, limbSwing, move);
		HekateLib.m(this.bodyBottom, 6F, -36F, 0F, 0F, 0F, 0F, s2, 0F, limbSwing, move);
		HekateLib.m(this.rightArm, 12F, -9F, 9F, -27F, -9F, 27F, s2, 0.05F, limbSwing, move);
		HekateLib.m(this.rightArmBottom, 24F, 30F, 0F, 0F, 0F, 0F, s2, 0F, limbSwing, move);
		HekateLib.m(this.leftArm, 12F, -9F, -9F, 27F, 9F, -27F, s2, 0.05F, limbSwing, move);
		HekateLib.m(this.leftArmBottom, 24F, 30F, 0F, 0F, 0F, 0F, s2, 0F, limbSwing, move);
		HekateLib.m(this.rightLeg, 24F, 12F, 0.6F, -6F, 0.6F, -6F, s2, 0.5F, limbSwing, move);
		HekateLib.m(this.rightLegBottom, 24F, -74F, 0F, 0F, 0F, 0F, s2, 0.7F, limbSwing, move);
		HekateLib.m(this.leftLeg, 24F, 0F, -0.6F, 6F, -0.6F, 6F, s2, 0.5F, limbSwing, move);
		HekateLib.m(this.leftLegBottom, 24F, -30F, 0F, 0F, 0F, 0F, s2, 0.8F, limbSwing, move);
		HekateLib.m(this.ten1, -14F, -29F, 0F, 0F, 0F, 0F, s2, 0.25F, limbSwing, move);
		HekateLib.m(this.ten1_1, -14F, -18F, 0F, 0F, 0F, 0F, s2, 0.20F, limbSwing, move);
		HekateLib.m(this.ten1_2, -14F, -12F, 0F, 0F, 0F, 0F, s2, 0.15F, limbSwing, move);
		HekateLib.m(this.ten1_3, -14F, -6F, 0F, 0F, 0F, 0F, s2, 0.10F, limbSwing, move);
		HekateLib.m(this.ten1_4, -14F, -6F, 0F, 0F, 0F, 0F, s2, 0.05F, limbSwing, move);
		HekateLib.m(this.ten2, -14F, -29F, 0F, -29F, 0F, 0F, s2, 0.25F, limbSwing, move);
		HekateLib.m(this.ten2_1, -14F, -18F, 0F, 0F, 0F, 0F, s2, 0.20F, limbSwing, move);
		HekateLib.m(this.ten2_2, -14F, -12F, 0F, 0F, 0F, 0F, s2, 0.15F, limbSwing, move);
		HekateLib.m(this.ten2_3, -14F, -6F, 0F, 0F, 0F, 0F, s2, 0.10F, limbSwing, move);
		HekateLib.m(this.ten2_4, -14F, -6F, 0F, 0F, 0F, 0F, s2, 0.05F, limbSwing, move);
		HekateLib.m(this.ten3, -14F, -29F, 0F, 29F, 0F, 0F, s2, 0.25F, limbSwing, move);
		HekateLib.m(this.ten3_1, -14F, -18F, 0F, 0F, 0F, 0F, s2, 0.20F, limbSwing, move);
		HekateLib.m(this.ten3_2, -14F, -12F, 0F, 0F, 0F, 0F, s2, 0.15F, limbSwing, move);
		HekateLib.m(this.ten3_3, -14F, -6F, 0F, 0F, 0F, 0F, s2, 0.10F, limbSwing, move);
		HekateLib.m(this.ten3_4, -14F, -6F, 0F, 0F, 0F, 0F, s2, 0.05F, limbSwing, move);
		//ATTACK
		HekateLib.render.animation(entity, "attack", ageInTicks,
				//1
				new KeyFrame(60, 50, 8F, 6F, new AnimatedPart(this.main, 2F, 0F, 0F), new AnimatedPart(this.bodyTop, 0F, -17.5F, 0F),
						new AnimatedPart(this.bodyTop2, 0F, -32.5F, 0F), new AnimatedPart(this.head, -10F, 46F, -3.5F),
						new AnimatedPart(this.leftArm, 30F, 35F, -32F), new AnimatedPart(this.leftArmBottom, 25F, 0F, 0F),
						new AnimatedPart(this.rightArm, 5.5F, 28F, 19.5F), new AnimatedPart(this.rightArmBottom, 25F, 0F, 0F),
						new AnimatedPart(this.item, -50F, 0F, 0F), new AnimatedPart(this.bodyBottom, 27.5F, 0F, 0F),
						new AnimatedPart(this.rightLeg, 25F, 2.6F, -4.2F), new AnimatedPart(this.rightLegBottom, -60F, 0F, 0F),
						new AnimatedPart(this.leftLeg, -2.5F, 0F, 2.5F), new AnimatedPart(this.leftLegBottom, -17.5F, 0F, 0F)),
				new KeyFrame(50, 40, 6F, 6F, new AnimatedPart(this.main, 1.5F, 0F, 0F), new AnimatedPart(this.bodyTop, 0F, -7.5F, 0F),
						new AnimatedPart(this.bodyTop2, 10F, -15F, 0F), new AnimatedPart(this.head, -15F, 27F, 0F),
						new AnimatedPart(this.leftArm, -19F, 12.5F, -31F), new AnimatedPart(this.leftArmBottom, 105F, 0F, 0F),
						new AnimatedPart(this.rightArm, -10F, 1F, 20F), new AnimatedPart(this.rightArmBottom, 52F, 0F, 0F),
						new AnimatedPart(this.item, -50F, 0F, 0F), new AnimatedPart(this.bodyBottom, -22.5F, 0F, 0F),
						new AnimatedPart(this.rightLeg, 25F, 2.6F, -4.2F), new AnimatedPart(this.rightLegBottom, -70F, 0F, 0F),
						new AnimatedPart(this.leftLeg, -2.5F, 0F, 2.5F), new AnimatedPart(this.leftLegBottom, -17.5F, 0F, 0F)),
				//2
				new KeyFrame(40, 28, 6F, 20F, new AnimatedPart(this.main, 2.5F, 0F, 0F), new AnimatedPart(this.bodyTop, -14F, 42F, -2F),
						new AnimatedPart(this.bodyTop2, 0F, 25F, 0F), new AnimatedPart(this.head, 3F, -56F, -8F),
						new AnimatedPart(this.leftArm, -21F, 12F, -18.5F), new AnimatedPart(this.leftArmBottom, 70F, 0F, 0F),
						new AnimatedPart(this.rightArm, 57F, 25F, 74F), new AnimatedPart(this.rightArmBottom, 75F, 0F, 0F),
						new AnimatedPart(this.item, -45F, 0F, 0F), new AnimatedPart(this.bodyBottom, 25F, 0F, 0F),
						new AnimatedPart(this.rightLeg, 57F, 2.6F, -4.2F), new AnimatedPart(this.rightLegBottom, -90F, 0F, 0F),
						new AnimatedPart(this.leftLeg, -2.5F, 0F, 2.5F), new AnimatedPart(this.leftLegBottom, -17.5F, 0F, 0F)),
				new KeyFrame(28, 20, 20F, 6F, new AnimatedPart(this.main, 1.5F, 0F, 0F), new AnimatedPart(this.bodyTop, -10F, -4F, 8F),
						new AnimatedPart(this.bodyTop2, 0F, -20F, 0F), new AnimatedPart(this.head, 2F, 24F, -4.5F),
						new AnimatedPart(this.leftArm, -50F, 30F, -35F), new AnimatedPart(this.leftArmBottom, 100F, 0F, 0F),
						new AnimatedPart(this.rightArm, 35F, 12F, 47F), new AnimatedPart(this.rightArmBottom, 10F, 0F, 0F),
						new AnimatedPart(this.item, -82F, 0F, 0F), new AnimatedPart(this.bodyBottom, -32F, 2F, -4F),
						new AnimatedPart(this.rightLeg, 57F, 2.6F, -4.2F), new AnimatedPart(this.rightLegBottom, -90F, 0F, 0F),
						new AnimatedPart(this.leftLeg, -2.5F, 0F, 2.5F), new AnimatedPart(this.leftLegBottom, -17.5F, 0F, 0F)),
				//3
				new KeyFrame(20, 8, 6F, 20F, new AnimatedPart(this.main, 2.5F, 0F, 0F), new AnimatedPart(this.bodyTop, 0F, -17.5F, 0F),
						new AnimatedPart(this.bodyTop2, 0F, -27.5F, 0F), new AnimatedPart(this.head, -22F, 42F, -9F),
						new AnimatedPart(this.leftArm, 33.5F, 12F, -18.5F), new AnimatedPart(this.leftArmBottom, 67F, 0F, 0F),
						new AnimatedPart(this.rightArm, -48F, -10.5F, 57F), new AnimatedPart(this.rightArmBottom, 75F, 0F, 0F),
						new AnimatedPart(this.item, -45F, 0F, 0F), new AnimatedPart(this.bodyBottom, 25F, 0F, 0F),
						new AnimatedPart(this.rightLeg, 32.4F, 2.6F, -4.2F), new AnimatedPart(this.rightLegBottom, -90F, 0F, 0F),
						new AnimatedPart(this.leftLeg, 0F, 0F, 2.5F), new AnimatedPart(this.leftLegBottom, -38F, 0F, 0F)),
				new KeyFrame(8, 0, 20F, 2F, new AnimatedPart(this.main, 1.5F, 0F, 0F), new AnimatedPart(this.bodyTop, -12.5F, 35F, 0F),
						new AnimatedPart(this.bodyTop2, 0F, 7.5F, 0F), new AnimatedPart(this.head, 1F, -38F, 0F),
						new AnimatedPart(this.leftArm, -21.5F, 12F, -18.5F), new AnimatedPart(this.leftArmBottom, 70F, 0F, 0F),
						new AnimatedPart(this.rightArm, 24F, -10.5F, 57F), new AnimatedPart(this.rightArmBottom, 12.5F, 0F, 0F),
						new AnimatedPart(this.item, -60F, 0F, 0F), new AnimatedPart(this.bodyBottom, -28F, 10F, -2F),
						new AnimatedPart(this.rightLeg, 57F, 2.6F, -4.2F), new AnimatedPart(this.rightLegBottom, -90F, 0F, 0F),
						new AnimatedPart(this.leftLeg, -2.5F, 0F, 2.5F), new AnimatedPart(this.leftLegBottom, -17.5F, 0F, 0F)));

		this.head.yRot += HekateLib.render.headYaw(netHeadYaw, 0.33F);
		this.bodyTop.yRot += HekateLib.render.headYaw(netHeadYaw, 0.33F);
		this.bodyTop2.yRot += HekateLib.render.headYaw(netHeadYaw, 0.33F);
	}
}
