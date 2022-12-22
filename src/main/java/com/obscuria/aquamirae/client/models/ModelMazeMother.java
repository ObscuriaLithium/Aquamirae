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

public class ModelMazeMother<T extends Entity> extends EntityModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(AquamiraeMod.MODID, "maze_mother"), "main");
	public final ModelPart main;
	public final ModelPart main2;
	public final ModelPart body_top;
	public final ModelPart body_bottom;
	public final ModelPart tail1;
	public final ModelPart tail2;
	public final ModelPart tail3;
	public final ModelPart tail4;
	public final ModelPart tail5;
	public final ModelPart jaw1;
	public final ModelPart jaw2;
	public final ModelPart jaw3;
	public final ModelPart jaw4;
	public final ModelPart left_wing1_top;
	public final ModelPart left_wing2_top;
	public final ModelPart right_wing1_top;
	public final ModelPart right_wing2_top;
	public final ModelPart left_wing1_bottom;
	public final ModelPart left_wing2_bottom;
	public final ModelPart right_wing1_bottom;
	public final ModelPart right_wing2_bottom;

	public ModelMazeMother(ModelPart root) {
		this.main = root.getChild("main");
		this.main2 = main.getChild("main2");
		this.body_top = main2.getChild("body_top");
		this.body_bottom = main2.getChild("body_bottom");
		this.jaw1 = body_top.getChild("jaw1");
		this.jaw2 = body_top.getChild("jaw2");
		this.jaw3 = body_top.getChild("jaw3");
		this.jaw4 = body_top.getChild("jaw4");
		this.tail1 = body_bottom.getChild("tail1");
		this.tail2 = tail1.getChild("tail2");
		this.tail3 = tail2.getChild("tail3");
		this.tail4 = tail3.getChild("tail4");
		this.tail5 = tail4.getChild("tail5");
		this.left_wing1_top = body_top.getChild("left_wing1_top");
		this.right_wing1_top = body_top.getChild("right_wing1_top");
		this.left_wing2_top = left_wing1_top.getChild("left_wing2_top");
		this.right_wing2_top = right_wing1_top.getChild("right_wing2_top");
		this.left_wing1_bottom = body_bottom.getChild("left_wing1_bottom");
		this.right_wing1_bottom = body_bottom.getChild("right_wing1_bottom");
		this.left_wing2_bottom = left_wing1_bottom.getChild("left_wing2_bottom");
		this.right_wing2_bottom = right_wing1_bottom.getChild("right_wing2_bottom");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0F, -126.0F, 0.0F));
		PartDefinition main2 = main.addOrReplaceChild("main2", CubeListBuilder.create(), PartPose.offset(0.0F, 147.0F, 0.0F));
		PartDefinition body_top = main2.addOrReplaceChild("body_top",
				CubeListBuilder.create().texOffs(0, 45).addBox(-8.0F, -1.5F, -16.0F, 16.0F, 3.0F, 16.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition jaw1 = body_top.addOrReplaceChild("jaw1",
				CubeListBuilder.create().texOffs(37, 26).addBox(-3.0F, 0.0F, -10.5F, 6.0F, 0.0F, 11.0F, new CubeDeformation(0.0F)),
				PartPose.offset(-7.0F, 0.0F, -14.5F));
		PartDefinition jaw2 = body_top.addOrReplaceChild("jaw2",
				CubeListBuilder.create().texOffs(49, 26).addBox(-3.0F, 0.0F, -10.5F, 6.0F, 0.0F, 11.0F, new CubeDeformation(0.0F)),
				PartPose.offset(7.0F, 0.0F, -14.5F));
		PartDefinition jaw3 = body_top.addOrReplaceChild("jaw3",
				CubeListBuilder.create().texOffs(65, 26).addBox(-1.5F, 0.0F, -5.5F, 3.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)),
				PartPose.offset(-3.5F, 0.0F, -15.5F));
		PartDefinition jaw4 = body_top.addOrReplaceChild("jaw4",
				CubeListBuilder.create().texOffs(73, 26).addBox(-1.5F, 0.0F, -5.5F, 3.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)),
				PartPose.offset(3.5F, 0.0F, -15.5F));
		PartDefinition crystal1_top = body_top.addOrReplaceChild("crystal1_top", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -16.0F));
		PartDefinition cube_r1 = crystal1_top.addOrReplaceChild("cube_r1",
				CubeListBuilder.create().texOffs(32, 58).addBox(-2.0F, -10.5F, -16.0F, 0.0F, 10.0F, 16.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -2.0F, 16.0F, 0.0F, 0.0F, -0.7854F));
		PartDefinition crystal2_top = body_top.addOrReplaceChild("crystal2_top", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -16.0F));
		PartDefinition cube_r2 = crystal2_top.addOrReplaceChild("cube_r2",
				CubeListBuilder.create().texOffs(64, 45).addBox(-1.0F, -9.5F, -16.0F, 0.0F, 10.0F, 16.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -2.0F, 16.0F, 0.0F, 0.0F, -0.3491F));
		PartDefinition crystal3_top = body_top.addOrReplaceChild("crystal3_top", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -16.0F));
		PartDefinition cube_r3 = crystal3_top.addOrReplaceChild("cube_r3",
				CubeListBuilder.create().texOffs(64, 65).addBox(1.0F, -9.5F, -16.0F, 0.0F, 10.0F, 16.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -2.0F, 16.0F, 0.0F, 0.0F, 0.3491F));
		PartDefinition crystal4_top = body_top.addOrReplaceChild("crystal4_top", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -16.0F));
		PartDefinition cube_r4 = crystal4_top.addOrReplaceChild("cube_r4",
				CubeListBuilder.create().texOffs(32, 48).addBox(2.0F, -10.5F, -16.0F, 0.0F, 10.0F, 16.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -2.0F, 16.0F, 0.0F, 0.0F, 0.7854F));
		PartDefinition left_wing1_top = body_top.addOrReplaceChild("left_wing1_top",
				CubeListBuilder.create().texOffs(48, 45).addBox(-9.0F, -1.0F, -14.0F, 9.0F, 2.0F, 14.0F, new CubeDeformation(0.0F)),
				PartPose.offset(-8.0F, 0.0F, 0.0F));
		PartDefinition left_wing2_top = left_wing1_top.addOrReplaceChild("left_wing2_top",
				CubeListBuilder.create().texOffs(92, 0).addBox(-5.0F, -1.0F, -11.0F, 5.0F, 2.0F, 11.0F, new CubeDeformation(0.0F)),
				PartPose.offset(-9.0F, 0.0F, 0.0F));
		PartDefinition right_wing1_top = body_top.addOrReplaceChild("right_wing1_top",
				CubeListBuilder.create().texOffs(60, 0).addBox(0.0F, -1.0F, -14.0F, 9.0F, 2.0F, 14.0F, new CubeDeformation(0.0F)),
				PartPose.offset(8.0F, 0.0F, 0.0F));
		PartDefinition right_wing2_top = right_wing1_top.addOrReplaceChild("right_wing2_top",
				CubeListBuilder.create().texOffs(47, 91).addBox(0.0F, -1.0F, -11.0F, 5.0F, 2.0F, 11.0F, new CubeDeformation(0.0F)),
				PartPose.offset(9.0F, 0.0F, 0.0F));
		PartDefinition body_bottom = main2.addOrReplaceChild(
				"body_bottom", CubeListBuilder.create().texOffs(0, 26).addBox(-8.0F, 0.5F, 0.0F, 16.0F, 3.0F, 16.0F, new CubeDeformation(0.0F))
						.texOffs(72, 26).addBox(-1.5F, -0.5F, 0.0F, 3.0F, 3.0F, 16.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, -2.0F, 0.0F));
		PartDefinition crystal1_bottom = body_bottom.addOrReplaceChild("crystal1_bottom", CubeListBuilder.create(),
				PartPose.offset(0.0F, 2.0F, 0.0F));
		PartDefinition cube_r5 = crystal1_bottom.addOrReplaceChild("cube_r5",
				CubeListBuilder.create().texOffs(0, 58).addBox(-2.0F, -10.5F, 0.0F, 0.0F, 10.0F, 16.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -2.0F, 0.0F, 0.0F, 0.0F, -0.7854F));
		PartDefinition crystal2_bottom = body_bottom.addOrReplaceChild("crystal2_bottom", CubeListBuilder.create(),
				PartPose.offset(0.0F, 2.0F, 0.0F));
		PartDefinition cube_r6 = crystal2_bottom.addOrReplaceChild("cube_r6",
				CubeListBuilder.create().texOffs(60, 0).addBox(-1.0F, -9.5F, 0.0F, 0.0F, 10.0F, 16.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -2.0F, 0.0F, 0.0F, 0.0F, -0.3491F));
		PartDefinition crystal3_bottom = body_bottom.addOrReplaceChild("crystal3_bottom", CubeListBuilder.create(),
				PartPose.offset(0.0F, 2.0F, 0.0F));
		PartDefinition cube_r7 = crystal3_bottom.addOrReplaceChild("cube_r7",
				CubeListBuilder.create().texOffs(64, 55).addBox(1.0F, -9.5F, 0.0F, 0.0F, 10.0F, 16.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -2.0F, 0.0F, 0.0F, 0.0F, 0.3491F));
		PartDefinition crystal4_bottom = body_bottom.addOrReplaceChild("crystal4_bottom", CubeListBuilder.create(),
				PartPose.offset(0.0F, 2.0F, 0.0F));
		PartDefinition cube_r8 = crystal4_bottom.addOrReplaceChild("cube_r8",
				CubeListBuilder.create().texOffs(0, 48).addBox(2.0F, -10.5F, 0.0F, 0.0F, 10.0F, 16.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -2.0F, 0.0F, 0.0F, 0.0F, 0.7854F));
		PartDefinition left_wing1_bottom = body_bottom.addOrReplaceChild("left_wing1_bottom",
				CubeListBuilder.create().texOffs(80, 45).addBox(-9.0F, -1.0F, 0.0F, 9.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)),
				PartPose.offset(-8.0F, 2.0F, 0.0F));
		PartDefinition left_wing2_bottom = left_wing1_bottom.addOrReplaceChild("left_wing2_bottom",
				CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -1.0F, 0.0F, 5.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)),
				PartPose.offset(-9.0F, 0.0F, 0.0F));
		PartDefinition right_wing1_bottom = body_bottom.addOrReplaceChild("right_wing1_bottom",
				CubeListBuilder.create().texOffs(20, 84).addBox(0.0F, -1.0F, 0.0F, 9.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)),
				PartPose.offset(8.0F, 2.0F, 0.0F));
		PartDefinition right_wing2_bottom = right_wing1_bottom.addOrReplaceChild("right_wing2_bottom",
				CubeListBuilder.create().texOffs(0, 7).addBox(0.0F, -1.0F, 0.0F, 5.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)),
				PartPose.offset(9.0F, 0.0F, 0.0F));
		PartDefinition tail1 = body_bottom.addOrReplaceChild("tail1",
				CubeListBuilder.create().texOffs(96, 57).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 9.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 1.0F, 16.0F));
		PartDefinition tail2 = tail1.addOrReplaceChild("tail2",
				CubeListBuilder.create().texOffs(98, 69).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 0.0F, 9.0F));
		PartDefinition tail3 = tail2
				.addOrReplaceChild("tail3",
						CubeListBuilder.create().texOffs(0, 87).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 9.0F, new CubeDeformation(0.0F))
								.texOffs(22, 22).addBox(-8.5F, 0.0F, 5.0F, 17.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)),
						PartPose.offset(0.0F, 0.0F, 7.0F));
		PartDefinition tail4 = tail3
				.addOrReplaceChild("tail4",
						CubeListBuilder.create().texOffs(2, 98).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
								.texOffs(19, 15).addBox(-8.5F, 0.0F, 0.0F, 17.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)),
						PartPose.offset(0.0F, 0.0F, 9.0F));
		PartDefinition tail5 = tail4
				.addOrReplaceChild("tail5",
						CubeListBuilder.create().texOffs(11, 0).addBox(-8.5F, 0.0F, 0.0F, 17.0F, 0.0F, 15.0F, new CubeDeformation(0.0F))
								.texOffs(0, 14).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)),
						PartPose.offset(0.0F, 0.0F, 7.0F));
		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green,
			float blue, float alpha) {
		poseStack.pushPose();
		poseStack.scale(5f, 5f, 5f);
		poseStack.translate(0F, -1F, 0F);
		main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		poseStack.popPose();
	}

	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		HekateLegacy.updateRenderer(entity);
		float i = HekateLegacy.setIdleModifier(limbSwingAmount, 3F);
		float m = HekateLegacy.setMoveModifier(limbSwingAmount, 3F);
		float a1 = HekateLegacy.getFrame(entity, "[attack]", 0.3, 15, 30);
		float a1i = 1F - a1;
		//IDLE
		float iBodyTX = HekateLegacy.idle(10F, 0F, 0.06F, 0.6F, ageInTicks, 1F);
		float iBodyBX = HekateLegacy.idle(10F, 0F, 0.06F, 0.5F, ageInTicks, 1F);
		float iTail1X = HekateLegacy.idle(10F, 0F, 0.06F, 0.4F, ageInTicks, 1F);
		float iTail2X = HekateLegacy.idle(10F, 0F, 0.06F, 0.3F, ageInTicks, 1F);
		float iTail3X = HekateLegacy.idle(10F, 0F, 0.06F, 0.2F, ageInTicks, 1F);
		float iTail4X = HekateLegacy.idle(10F, 0F, 0.06F, 0.1F, ageInTicks, 1F);
		float iTail5X = HekateLegacy.idle(10F, 0F, 0.06F, 0F, ageInTicks, 1F);
		float iLWing1Z = HekateLegacy.idle(-15F, 0F, 0.06F, 0.1F, ageInTicks, 1F);
		float iRWing1Z = HekateLegacy.idle(15F, 0F, 0.06F, 0.1F, ageInTicks, 1F);
		float iLWing2Z = HekateLegacy.idle(-15F, 0F, 0.06F, 0F, ageInTicks, 1F);
		float iRWing2Z = HekateLegacy.idle(15F, 0F, 0.06F, 0F, ageInTicks, 1F);
		float iJaw1Y = HekateLegacy.idle(12F, 0F, 0.3F, 0F, ageInTicks, HekateLegacy.getModifier(ageInTicks, 0.05F, 0F));
		float iJaw2Y = HekateLegacy.idle(10F, 0F, 0.6F, 0F, ageInTicks, HekateLegacy.getModifier(ageInTicks, 0.05F, 0.2F));
		//ATTACK
		float a1MainX = HekateLegacy.fixed(-2F, a1);
		float a1Jaw1Y = HekateLegacy.fixed(-30F, a1);
		float a1Jaw2Y = HekateLegacy.fixed(-30F, a1);
		//APPLYING PRESETS TO A MODEL
		this.main.xRot = a1MainX;
		this.body_top.xRot = iBodyTX;
		this.body_bottom.xRot = iBodyBX;
		this.tail1.xRot = iTail1X;
		this.tail2.xRot = iTail2X;
		this.tail3.xRot = iTail3X;
		this.tail4.xRot = iTail4X;
		this.tail5.xRot = iTail5X;
		this.left_wing1_top.zRot = iLWing1Z;
		this.left_wing1_bottom.zRot = iLWing1Z;
		this.right_wing1_top.zRot = iRWing1Z;
		this.right_wing1_bottom.zRot = iRWing1Z;
		this.left_wing2_top.zRot = iLWing2Z;
		this.left_wing2_bottom.zRot = iLWing2Z;
		this.right_wing2_top.zRot = iRWing2Z;
		this.right_wing2_bottom.zRot = iRWing2Z;
		this.jaw1.yRot = iJaw1Y * a1i + a1Jaw1Y;
		this.jaw2.yRot = -iJaw1Y * a1i + -a1Jaw1Y;
		this.jaw3.yRot = iJaw2Y * a1i + a1Jaw2Y;
		this.jaw4.yRot = -iJaw2Y * a1i + -a1Jaw2Y;
	}
}
