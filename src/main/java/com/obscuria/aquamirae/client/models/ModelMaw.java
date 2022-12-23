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

public class ModelMaw<T extends Entity> extends EntityModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(AquamiraeMod.MODID, "maw"), "main");
	public final ModelPart main, head, headTop, headBottom, body, body2, body3, rightFin, leftFin;

	public ModelMaw(ModelPart root) {
		this.main = root.getChild("main");
		this.head = main.getChild("head");
		this.headTop = head.getChild("headtop");
		this.headBottom = head.getChild("headbottom");
		this.body = main.getChild("body");
		this.body2 = body.getChild("body2");
		this.body3 = body2.getChild("body3");
		this.rightFin = body.getChild("right_fin");
		this.leftFin = body.getChild("left_fin");
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
	public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green,
							   float blue, float alpha) {
		main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setupAnim(@NotNull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		final float idle = HekateLib.mod.idle(limbSwingAmount, 5F);
		final float move = HekateLib.mod.move(limbSwingAmount, 5F);
		final float speed1 = 0.03F;
		final float speed2 = 0.6F;

		HekateLib.render.tick(entity);
		HekateLib.render.prepare(main, head, headTop, headBottom, body, body2, body3, rightFin, leftFin);

		HekateLib.i(headTop, -5F, 0, 0, 0, 0, 0, speed1 * 2, 0F, ageInTicks, idle);
		HekateLib.i(body2, -8F, -8F, 0, 0, 4F, 0, speed1, 0F, ageInTicks, idle);
		HekateLib.i(body2, 0, 0, -10F, 0, 0, 0, speed1 * 2, 0F, ageInTicks, idle);
		HekateLib.i(body3, -8F, -8F, 0, 0, 4F, 0, speed1, -0.1F, ageInTicks, idle);
		HekateLib.i(body3, 0, 0, -10F, 0, 0, 0, speed1 * 2, -0.1F, ageInTicks, idle);

		HekateLib.m(main, 0, 0, 0, 0, -4F, 0, speed2, 0F, limbSwing, move);
		HekateLib.m(headTop, -4F, 12F, 0, 0, 0, 0, speed2, 0F, limbSwing, move);
		HekateLib.m(body, 0, 0, -12F, 0, 0, 0, speed2, 0F, limbSwing, move);
		HekateLib.m(body2, 0, 0, -12F, 0, 0, 0, speed2, -0.1F, limbSwing, move);
		HekateLib.m(body3, 0, 0, -12F, 0, 0, 0, speed2, -0.2F, limbSwing, move);
		HekateLib.m(rightFin, 0, 0, 40F, 0, 0, 0, speed2, 0F, limbSwing, move);
		HekateLib.m(leftFin, 0, 0, 40F, 0, 0, 0, speed2, 0F, limbSwing, move);
		HekateLib.m(rightFin, 0, 0, 0, 0, 12F, 10F, speed2, -0.3F, limbSwing, move);
		HekateLib.m(leftFin, 0, 0, 0, 0, 12F, -10F, speed2, -0.3F, limbSwing, move);

		HekateLib.render.animation(entity, "attack", ageInTicks,
				new KeyFrame(20, 0, 20F, 5F,
						new AnimatedPart(head, 10F, 0, 0),
						new AnimatedPart(headTop, -22F, 0, 0)));
	}
}
