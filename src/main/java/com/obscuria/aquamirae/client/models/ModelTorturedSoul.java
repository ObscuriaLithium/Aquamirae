package com.obscuria.aquamirae.client.models;

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

import com.obscuria.aquamirae.client.HekateLegacy;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;

public class ModelTorturedSoul<T extends Entity> extends EntityModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(AquamiraeMod.MODID, "tortured_soul"),
			"main");
	public final ModelPart main;
	public final ModelPart body;
	public final ModelPart heart;
	public final ModelPart head;
	public final ModelPart nose;
	public final ModelPart left_arm;
	public final ModelPart right_arm;
	public final ModelPart left_leg;
	public final ModelPart right_leg;
	public final ModelPart left_arm_bottom;
	public final ModelPart right_arm_bottom;
	public final ModelPart left_leg_bottom;
	public final ModelPart right_leg_bottom;

	public ModelTorturedSoul(ModelPart root) {
		this.main = root.getChild("main");
		this.body = main.getChild("body");
		this.heart = body.getChild("heart");
		this.head = body.getChild("head");
		this.nose = head.getChild("nose");
		this.left_arm = body.getChild("left_arm");
		this.right_arm = body.getChild("right_arm");
		this.left_leg = main.getChild("left_leg");
		this.right_leg = main.getChild("right_leg");
		this.left_arm_bottom = left_arm.getChild("left_arm_bottom");
		this.right_arm_bottom = right_arm.getChild("right_arm_bottom");
		this.left_leg_bottom = left_leg.getChild("left_leg_bottom");
		this.right_leg_bottom = right_leg.getChild("right_leg_bottom");
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
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green,
			float blue, float alpha) {
		main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		HekateLegacy.updateRenderer(entity);
		float i = HekateLegacy.setIdleModifier(limbSwingAmount, 5F);
		float m = HekateLegacy.setMoveModifier(limbSwingAmount, 5F);
		float a1 = HekateLegacy.getFrame(entity, "[attack]", 0.3, 15, 30);
		float a1Inv = 1F - a1;

		//IDLE
		float iMainX = HekateLegacy.idle(0.3F, 0.5F, 0.1F, 0F, ageInTicks, i);
		float iBodyX = HekateLegacy.idle(3F, 10F, 0.1F, 0F, ageInTicks, i);
		float iHeadX = HekateLegacy.idle(-3F, -16F, 0.1F, 0F, ageInTicks, i);

		float iRArmX = HekateLegacy.idle(-12F, -35F, 0.1F, 0.2F, ageInTicks, i);
		float iRArmZ = HekateLegacy.idle(-3F, 10F, 0.1F, 0.2F, ageInTicks, i);
		float iRArmBX = HekateLegacy.idle(-18F, -69F, 0.1F, 0.4F, ageInTicks, i);
		float iLArmX = HekateLegacy.idle(-10F, -15F, 0.1F, 0.2F, ageInTicks, i);
		float iLArmZ = HekateLegacy.idle(3F, -10F, 0.1F, 0.2F, ageInTicks, i);
		float iLArmBX = HekateLegacy.idle(-15F, -30F, 0.1F, 0.4F, ageInTicks, i);

		float iLLegX = HekateLegacy.idle(-15F, -30F, 0.1F, 0F, ageInTicks, i);
		float iLLegY = HekateLegacy.idle(0.5F, -4F, 0.1F, 0F, ageInTicks, i);
		float iLLegZ = HekateLegacy.idle(0.5F, -4F, 0.1F, 0F, ageInTicks, i);
		float iLLegBX = HekateLegacy.idle(30F, 44F, 0.1F, 0F, ageInTicks, i);

		//MOVE
		float mMainX = HekateLegacy.move(false, 0.5F, 0.3F, 1.4F, 0F, limbSwing, m);
		float mBodyX = HekateLegacy.move(true, 3F, 3F, 1.4F, 0.1F, limbSwing, m);
		float mHeadX = HekateLegacy.move(false, -3F, 3F, 1.4F, 0.2F, limbSwing, m);

		float mRArmX = HekateLegacy.move(true, 24F, 12F, 0.7F, 0.15F, limbSwing, m);
		float mRArmZ = HekateLegacy.move(true, 3F, 10F, 0.7F, 0.15F, limbSwing, m);
		float mRArmBX = HekateLegacy.move(true, 20F, -34F, 0.7F, 0.2F, limbSwing, m);
		float mLArmX = HekateLegacy.move(false, 24F, 12F, 0.7F, 0.15F, limbSwing, m);
		float mLArmZ = HekateLegacy.move(false, 3F, -10F, 0.7F, 0.15F, limbSwing, m);
		float mLArmBX = HekateLegacy.move(false, 20F, -34F, 0.7F, 0.2F, limbSwing, m);

		float mRLegX = HekateLegacy.move(false, 30F, -14F, 0.7F, 0.15F, limbSwing, m);
		float mRLegBX = HekateLegacy.move(false, 34F, 34F, 0.7F, 0F, limbSwing, m);
		float mLLegX = HekateLegacy.move(true, 30F, -14F, 0.7F, 0.15F, limbSwing, m);
		float mLLegBX = HekateLegacy.move(true, 34F, 34F, 0.7F, 0F, limbSwing, m);

		//ATTACK
		float a1BodyX = HekateLegacy.fixed(27F, a1);
		float a1ArmX = HekateLegacy.fixed(-125F, a1);
		float a1ArmBX = HekateLegacy.fixed(-11.5F, a1);

		//APPLYING PRESETS TO A MODEL 
		this.main.xRot = iMainX + mMainX;
		this.head.xRot = iHeadX + mHeadX;
		this.head.yRot = HekateLegacy.getHeadYaw(netHeadYaw, 0.35F);;
		this.body.xRot = (iBodyX + mBodyX) * a1Inv + a1BodyX;
		this.body.yRot = HekateLegacy.getHeadYaw(netHeadYaw, 0.35F);;

		this.right_arm.xRot = (iRArmX + mRArmX) * a1Inv + a1ArmX;
		this.right_arm.zRot = iRArmZ + mRArmZ;
		this.right_arm_bottom.xRot = (iRArmBX + mRArmBX) * a1Inv + a1ArmBX;
		this.left_arm.xRot = (iLArmX + mLArmX) * a1Inv + a1ArmX;
		this.left_arm.zRot = iLArmZ + mLArmZ;
		this.left_arm_bottom.xRot = (iLArmBX + mLArmBX) * a1Inv + a1ArmX;

		this.left_leg.xRot = iLLegX + mLLegX;
		this.left_leg.yRot = iLLegY;
		this.left_leg.zRot = iLLegZ;
		this.left_leg_bottom.xRot = iLLegBX + mLLegBX;
		this.right_leg.xRot = iLLegX + mRLegX;
		this.right_leg.yRot = -iLLegY;
		this.right_leg.zRot = -iLLegZ;
		this.right_leg_bottom.xRot = iLLegBX + mRLegBX;
		this.heart.xRot = ageInTicks / 13F;
		this.heart.yRot = ageInTicks / 9F;
		this.heart.zRot = ageInTicks / 5F;
	}
}
