package com.obscuria.aquamirae.client.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.client.animations.CaptainCorneliaAnimations;
import com.obscuria.obscureapi.api.animations.HekateLib;
import com.obscuria.obscureapi.api.animations.IAnimated;
import net.minecraft.client.Minecraft;
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
		if (entity instanceof IAnimated iAnimated && iAnimated.getAnimations() instanceof CaptainCorneliaAnimations animations) {
			final float partialTicks = Minecraft.getInstance().getPartialTick();
			animations.MAIN.apply(this.main, partialTicks);
			animations.HEAD.apply(this.head, partialTicks);
			animations.BODY_TOP.apply(this.bodyTop, partialTicks);
			animations.BODY_TOP_2.apply(this.bodyTop2, partialTicks);
			animations.BODY_BOTTOM.apply(this.bodyBottom, partialTicks);
			animations.LEFT_ARM.apply(this.leftArm, partialTicks);
			animations.LEFT_ARM_BOTTOM.apply(this.leftArmBottom, partialTicks);
			animations.RIGHT_ARM.apply(this.rightArm, partialTicks);
			animations.RIGHT_ARM_BOTTOM.apply(this.rightArmBottom, partialTicks);
			animations.WEAPON.apply(this.item, partialTicks);
			animations.LEFT_LEG.apply(this.leftLeg, partialTicks);
			animations.LEFT_LEG_BOTTOM.apply(this.leftLegBottom, partialTicks);
			animations.RIGHT_LEG.apply(this.rightLeg, partialTicks);
			animations.RIGHT_LEG_BOTTOM.apply(this.rightLegBottom, partialTicks);
			animations.TENTACLE_1.apply(this.ten1, partialTicks);
			animations.TENTACLE_1_1.apply(this.ten1_1, partialTicks);
			animations.TENTACLE_1_2.apply(this.ten1_2, partialTicks);
			animations.TENTACLE_1_3.apply(this.ten1_3, partialTicks);
			animations.TENTACLE_1_4.apply(this.ten1_4, partialTicks);
			animations.TENTACLE_2.apply(this.ten2, partialTicks);
			animations.TENTACLE_2_1.apply(this.ten2_1, partialTicks);
			animations.TENTACLE_2_2.apply(this.ten2_2, partialTicks);
			animations.TENTACLE_2_3.apply(this.ten2_3, partialTicks);
			animations.TENTACLE_2_4.apply(this.ten2_4, partialTicks);
			animations.TENTACLE_3.apply(this.ten3, partialTicks);
			animations.TENTACLE_3_1.apply(this.ten3_1, partialTicks);
			animations.TENTACLE_3_2.apply(this.ten3_2, partialTicks);
			animations.TENTACLE_3_3.apply(this.ten3_3, partialTicks);
			animations.TENTACLE_3_4.apply(this.ten3_4, partialTicks);
		}
		this.head.yRot += HekateLib.render.head(netHeadYaw, 0.33F);
		this.bodyTop.yRot += HekateLib.render.head(netHeadYaw, 0.33F);
		this.bodyTop2.yRot += HekateLib.render.head(netHeadYaw, 0.33F);
	}
}
