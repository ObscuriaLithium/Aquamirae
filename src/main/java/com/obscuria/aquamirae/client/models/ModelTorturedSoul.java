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
import org.jetbrains.annotations.NotNull;

public class ModelTorturedSoul<T extends Entity> extends EntityModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(AquamiraeMod.MODID, "tortured_soul"),
			"main");
	public final ModelPart main, body, heart, head, nose, leftArm, rightArm, leftLeg, rightLeg, leftArmBottom, rightArmBottom, leftLegBottom, rightLegBottom;

	public ModelTorturedSoul(ModelPart root) {
		this.main = root.getChild("main");
		this.body = main.getChild("body");
		this.heart = body.getChild("heart");
		this.head = body.getChild("head");
		this.nose = head.getChild("nose");
		this.leftArm = body.getChild("left_arm");
		this.rightArm = body.getChild("right_arm");
		this.leftLeg = main.getChild("left_leg");
		this.rightLeg = main.getChild("right_leg");
		this.leftArmBottom = leftArm.getChild("left_arm_bottom");
		this.rightArmBottom = rightArm.getChild("right_arm_bottom");
		this.leftLegBottom = leftLeg.getChild("left_leg_bottom");
		this.rightLegBottom = rightLeg.getChild("right_leg_bottom");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0F, 6.0F, 150.0F));
		PartDefinition body = main.addOrReplaceChild(
				"body", CubeListBuilder.create().texOffs(16, 20).addBox(-4.0F, -12.0F, -3.0F, 8.0F, 12.0F, 6.0F, new CubeDeformation(-0.02F))
						.texOffs(0, 39).addBox(-4.0F, -12.0F, -3.0F, 8.0F, 18.0F, 6.0F, new CubeDeformation(0.5F)),
				PartPose.offset(0.0F, 6.0F, -150.0F));
		PartDefinition heart = body.addOrReplaceChild("heart",
				CubeListBuilder.create().texOffs(44, 21).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, -8.0F, 0.0F));
		PartDefinition head = body.addOrReplaceChild(
				"head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, new CubeDeformation(0.0F))
						.texOffs(28, 39).addBox(-4.5F, -13.0F, -4.5F, 9.0F, 10.0F, 9.0F, new CubeDeformation(-0.4F)),
				PartPose.offset(0.0F, -12.0F, 0.0F));
		PartDefinition nose = head.addOrReplaceChild("nose",
				CubeListBuilder.create().texOffs(24, 0).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, -2.0F, -4.0F));
		PartDefinition left_arm = body.addOrReplaceChild("left_arm",
				CubeListBuilder.create().texOffs(38, 0).mirror().addBox(0.0F, -2.0F, -2.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
						.texOffs(44, 30).mirror().addBox(0.0F, -6.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.01F)).mirror(false),
				PartPose.offsetAndRotation(4.0F, -10.0F, 0.0F, 0.0F, 0.0F, 0.0F));
		PartDefinition left_arm_bottom = left_arm.addOrReplaceChild("left_arm_bottom", CubeListBuilder.create().texOffs(38, 11).mirror()
				.addBox(-2.0F, 0.0F, -3.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offset(2.0F, 4.0F, 1.0F));
		PartDefinition right_arm = body.addOrReplaceChild(
				"right_arm", CubeListBuilder.create().texOffs(38, 0).addBox(-4.0F, -2.0F, -2.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
						.texOffs(44, 30).addBox(-4.0F, -6.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.01F)),
				PartPose.offset(-4.0F, -10.0F, 0.0F));
		PartDefinition right_arm_bottom = right_arm.addOrReplaceChild("right_arm_bottom",
				CubeListBuilder.create().texOffs(38, 11).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.01F)),
				PartPose.offset(-2.0F, 4.0F, 0.0F));
		PartDefinition left_leg = main.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 18).mirror()
				.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(2.0F, 6.0F, -150.0F));
		PartDefinition left_leg_bottom = left_leg.addOrReplaceChild("left_leg_bottom", CubeListBuilder.create().texOffs(0, 29).mirror()
				.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offset(0.0F, 6.0F, 0.0F));
		PartDefinition right_leg = main.addOrReplaceChild("right_leg",
				CubeListBuilder.create().texOffs(0, 18).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)),
				PartPose.offset(-2.0F, 6.0F, -150.0F));
		PartDefinition right_leg_bottom = right_leg.addOrReplaceChild("right_leg_bottom",
				CubeListBuilder.create().texOffs(0, 29).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.01F)),
				PartPose.offset(0.0F, 6.0F, 0.0F));
		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green,
							   float blue, float alpha) {
		main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setupAnim(@NotNull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		final float idle = HekateLib.mod.idle(limbSwingAmount, 3F);
		final float move = HekateLib.mod.move(limbSwingAmount, 3F);
		final float speed1 = 0.1F;
		final float speed2 = 0.7F;

		HekateLib.render.tick(entity);
		HekateLib.render.prepare(main, body, heart, head, nose, leftArm, rightArm, leftLeg, rightLeg, leftArmBottom, rightArmBottom, leftLegBottom, rightLegBottom);

		HekateLib.i(main, -0.3F, -0.5F, 0, 0, 0, 0, speed1, 0F, ageInTicks, idle);
		HekateLib.i(body, -3F, -10F, 0, 0, 0, 0, speed1, 0F, ageInTicks, idle);
		HekateLib.i(head, 3F, 16F, 0, 0, 0, 0, speed1, 0F, ageInTicks, idle);
		HekateLib.i(rightArm, 12F, 35F, 0, 0, -3F, 10F, speed1, -0.2F, ageInTicks, idle);
		HekateLib.i(rightArmBottom, 18F, 69F, 0, 0, 0, 0, speed1, -0.4F, ageInTicks, idle);
		HekateLib.i(leftArm, 10F, 15F, 0, 0, 3F, -10F, speed1, -0.2F, ageInTicks, idle);
		HekateLib.i(leftArmBottom, 15F, 30F, 0, 0, 0, 0, speed1, -0.4F, ageInTicks, idle);
		HekateLib.i(leftLeg, 15F, 30F, -0.5F, 4F, 0.5F, -4F, speed1, 0F, ageInTicks, idle);
		HekateLib.i(leftLegBottom, -30F, -44F, 0, 0, 0, 0, speed1, 0F, ageInTicks, idle);
		this.rightLeg.xRot = this.leftLeg.xRot;
		this.rightLeg.yRot = -this.leftLeg.yRot;
		this.rightLeg.zRot = -this.leftLeg.zRot;
		this.rightLegBottom.xRot = this.leftLegBottom.xRot;

		HekateLib.m(main, -0.5F, -0.3F, 0, 0, 0, 0, speed2 * 2, 0F, limbSwing, move);
		HekateLib.m(body, 3F, -3F, 0, 0, 0, 0, speed2 * 2, -0.1F, limbSwing, move);
		HekateLib.m(head, 3F, -3F, 0, 0, 0, 0, speed2 * 2, -0.2F, limbSwing, move);
		HekateLib.m(rightArm, 24F, -12F, 0, 0, -3F, 10F, speed2, -0.1F, limbSwing, move);
		HekateLib.m(rightArmBottom, 20F, 34F, 0, 0, 0, 0, speed2, -0.2F, limbSwing, move);
		HekateLib.m(leftArm, -24F, -12F, 0, 0, 3F, -10F, speed2, -0.1F, limbSwing, move);
		HekateLib.m(leftArmBottom, -20F, 34F, 0, 0, 0, 0, speed2, -0.2F, limbSwing, move);
		HekateLib.m(rightLeg, -30F, 14F, 0, 0, 0, 0, speed2, -0.1F, limbSwing, move);
		HekateLib.m(rightLegBottom, -34F, -34F, 0, 0, 0, 0, speed2, -0.2F, limbSwing, move);
		HekateLib.m(leftLeg, 30F, 14F, 0, 0, 0, 0, speed2, -0.1F, limbSwing, move);
		HekateLib.m(leftLegBottom, 34F, -34F, 0, 0, 0, 0, speed2, -0.2F, limbSwing, move);

		this.heart.xRot = ageInTicks / 13F;
		this.heart.yRot = ageInTicks / 9F;
		this.heart.zRot = ageInTicks / 5F;

		this.head.yRot += HekateLib.render.head(netHeadYaw, 0.5F);
		this.body.yRot += HekateLib.render.head(netHeadYaw, 0.5F);

		HekateLib.render.animation(entity, "attack", ageInTicks,
				new KeyFrame(20, 0, 16F, 5F,
						new AnimatedPart(body, -27F, 0, 0),
						new AnimatedPart(leftArm, 125F, 0, 0),
						new AnimatedPart(rightArm, 125F, 0, 0),
						new AnimatedPart(leftArmBottom, 12F, 0, 0),
						new AnimatedPart(rightArmBottom, 12F, 0, 0)));
	}
}
