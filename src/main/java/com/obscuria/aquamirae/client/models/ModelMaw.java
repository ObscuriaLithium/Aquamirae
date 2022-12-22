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

import com.obscuria.aquamirae.client.legacy.HekateLegacy;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;

public class ModelMaw<T extends Entity> extends EntityModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(AquamiraeMod.MODID, "maw"), "main");
	public final ModelPart main;
	public final ModelPart head;
	public final ModelPart head_top;
	public final ModelPart head_bottom;
	public final ModelPart body;
	public final ModelPart body2;
	public final ModelPart body3;
	public final ModelPart right_fin;
	public final ModelPart left_fin;

	public ModelMaw(ModelPart root) {
		this.main = root.getChild("main");
		this.head = main.getChild("head");
		this.head_top = head.getChild("headtop");
		this.head_bottom = head.getChild("headbottom");
		this.body = main.getChild("body");
		this.body2 = body.getChild("body2");
		this.body3 = body2.getChild("body3");
		this.right_fin = body.getChild("right_fin");
		this.left_fin = body.getChild("left_fin");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0F, 15.0F, 150.0F));
		PartDefinition head = main.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -147.0F));
		PartDefinition headtop = head.addOrReplaceChild("headtop", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition cube_r1 = headtop.addOrReplaceChild("cube_r1",
				CubeListBuilder.create().texOffs(64, 66).addBox(-10.0F, 0.0F, -24.0F, 20.0F, 6.0F, 24.0F, new CubeDeformation(0.0F)).texOffs(0, 60)
						.addBox(-10.0F, -6.0F, -24.0F, 20.0F, 6.0F, 24.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.4363F, 0.0F, 0.0F));
		PartDefinition cube_r2 = headtop.addOrReplaceChild("cube_r2",
				CubeListBuilder.create().texOffs(66, 19).addBox(0.01F, -14.4226F, -20.0937F, 0.0F, 8.0F, 23.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -1.0F, -3.75F, -0.4363F, 0.0F, 0.0F));
		PartDefinition headbottom = head.addOrReplaceChild("headbottom", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition cube_r3 = headbottom.addOrReplaceChild("cube_r3",
				CubeListBuilder.create().texOffs(0, 0).addBox(-10.5F, -6.0F, -24.0F, 21.0F, 6.0F, 24.0F, new CubeDeformation(0.0F)).texOffs(0, 30)
						.addBox(-10.5F, 0.0F, -24.0F, 21.0F, 6.0F, 24.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1745F, 0.0F, 0.0F));
		PartDefinition body = main.addOrReplaceChild(
				"body", CubeListBuilder.create().texOffs(76, 16).addBox(-9.0F, -6.0F, 2.0F, 18.0F, 12.0F, 14.0F, new CubeDeformation(0.0F))
						.texOffs(0, 98).addBox(0.0F, -14.0F, 2.0F, 0.0F, 8.0F, 14.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 0.0F, -150.0F));
		PartDefinition body2 = body.addOrReplaceChild("body2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 16.0F));
		PartDefinition cube_r4 = body2.addOrReplaceChild("cube_r4",
				CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -11.0F, -1.0F, 0.0F, 8.0F, 12.0F, new CubeDeformation(0.0F)).texOffs(0, 90)
						.addBox(-7.0F, -3.0F, -3.0F, 14.0F, 8.0F, 14.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.2618F, 0.0F, 0.0F));
		PartDefinition body3 = body2.addOrReplaceChild("body3", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 12.0F));
		PartDefinition cube_r5 = body3.addOrReplaceChild("cube_r5",
				CubeListBuilder.create().texOffs(56, 76).addBox(0.0F, -2.0F, 5.0F, 0.0F, 12.0F, 20.0F, new CubeDeformation(0.0F)).texOffs(82, 96)
						.addBox(-4.0F, 2.0F, -2.0F, 8.0F, 4.0F, 14.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.0873F, 0.0F, 0.0F));
		PartDefinition left_fin = body.addOrReplaceChild("left_fin", CubeListBuilder.create(), PartPose.offset(9.0F, 3.0F, 10.0F));
		PartDefinition cube_r6 = left_fin.addOrReplaceChild("cube_r6",
				CubeListBuilder.create().texOffs(82, 52).addBox(0.0F, -1.0F, -4.0F, 16.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3491F));
		PartDefinition right_fin = body.addOrReplaceChild("right_fin", CubeListBuilder.create(), PartPose.offset(-9.0F, 3.0F, 10.0F));
		PartDefinition cube_r7 = right_fin.addOrReplaceChild("cube_r7",
				CubeListBuilder.create().texOffs(66, 0).addBox(-16.0F, -1.0F, -4.0F, 16.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3491F));
		return LayerDefinition.create(meshdefinition, 256, 256);
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
		float a1i = 1F - a1;
		//IDLE
		float iHeadTX = HekateLegacy.idle(5F, 0F, 0.06F, 0F, ageInTicks, i);
		float iBody2X = HekateLegacy.idle(8F, 8F, 0.03F, 0.1F, ageInTicks, i);
		float iBody2Y = HekateLegacy.idle(10F, 0F, 0.06F, 0.1F, ageInTicks, i);
		float iBody2Z = HekateLegacy.idle(4F, 0F, 0.03F, 0.1F, ageInTicks, i);
		float iBody3X = HekateLegacy.idle(8F, 8F, 0.03F, 0F, ageInTicks, i);
		float iBody3Y = HekateLegacy.idle(10F, 0F, 0.06F, 0F, ageInTicks, i);
		float iBody3Z = HekateLegacy.idle(4F, 0F, 0.03F, 0F, ageInTicks, i);
		//MOVE
		float mMainZ = HekateLegacy.move(false, 4F, 0F, 0.8F, 0.1F, limbSwing, m);
		float mHeadTX = HekateLegacy.move(false, 4F, -12F, 0.8F, 0.2F, limbSwing, m);
		float mBodyY = HekateLegacy.move(false, 12F, 0F, 0.8F, 0.2F, limbSwing, m);
		float mBody2Y = HekateLegacy.move(false, 12F, 0F, 0.8F, 0.1F, limbSwing, m);
		float mBody3Y = HekateLegacy.move(false, 12F, 0F, 0.8F, 0F, limbSwing, m);
		float mRFinY = HekateLegacy.move(true, 30F, 0F, 0.8F, 0.1F, limbSwing, m);
		float mRFinZ = HekateLegacy.move(false, 12F, 10F, 0.8F, 0.3F, limbSwing, m);
		float mLFinY = HekateLegacy.move(true, 30F, 0F, 0.8F, 0.1F, limbSwing, m);
		float mLFinZ = HekateLegacy.move(true, 12F, -10F, 0.8F, 0.8F, limbSwing, m);
		//ATTACK
		float a1HeadTX = HekateLegacy.fixed(20F, a1);
		float a1HeadX = HekateLegacy.fixed(10F, a1);
		float a1Body2X = HekateLegacy.fixed(-15F, a1);
		float a1Body3X = HekateLegacy.fixed(-25F, a1);
		float a1RFinZ = HekateLegacy.fixed(-30F, a1);
		float a1LFinZ = HekateLegacy.fixed(30F, a1);
		//APPLYING PRESETS TO A MODEL
		this.main.zRot = mMainZ;
		this.head.xRot = a1HeadX;
		this.head.yRot = HekateLegacy.getHeadYaw(netHeadYaw, 0.3F);
		this.head_top.xRot = (iHeadTX + mHeadTX) * a1i + a1HeadTX;
		this.body.yRot = mBodyY;
		this.body2.xRot = (iBody2X) * a1i + a1Body2X;
		this.body2.yRot = iBody2Y + mBody2Y;
		this.body2.zRot = iBody2Z;
		this.body3.xRot = (iBody3X) * a1i + a1Body3X;
		this.body3.yRot = iBody3Y + mBody3Y;
		this.body3.zRot = iBody3Z;
		this.right_fin.yRot = mRFinY;
		this.right_fin.zRot = (mRFinZ) * a1i + a1RFinZ;
		this.left_fin.yRot = mLFinY;
		this.left_fin.zRot = (mLFinZ) * a1i + a1LFinZ;
	}
}
