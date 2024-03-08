package com.obscuria.aquamirae.client.model;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;

public class ModelSpinefish<T extends Entity> extends EntityModel<T> {
	private final ModelPart main, bodyTop, bodyBottom, head, tail;

	public ModelSpinefish(ModelPart root) {
		this.main = root.getChild("main");
		this.bodyTop = main.getChild("bodyTop");
		this.bodyBottom = main.getChild("bodyBottom");
		this.head = bodyTop.getChild("head");
		this.tail = bodyBottom.getChild("tail");
	}

	public static LayerDefinition createBodyLayer() {
		final MeshDefinition meshdefinition = new MeshDefinition();
		final PartDefinition root = meshdefinition.getRoot();
		final PartDefinition main = root.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0F, 17.5F, 0.0F));
		final PartDefinition bodyTop = main.addOrReplaceChild("bodyTop", CubeListBuilder.create().texOffs(16, 8).addBox(-1.0F, -2.5F, -3.0F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(10, 0).addBox(0.0F, -6.5F, -3.0F, 0.0F, 13.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 1.0F));
		final PartDefinition head = bodyTop.addOrReplaceChild("head", CubeListBuilder.create().texOffs(6, 13).addBox(0.0F, -5.5F, -6.0303F, 0.0F, 11.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(18, 16).addBox(-1.0F, -2.5F, -3.0303F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(0, 11).addBox(0.0F, -6.5F, -3.0303F, 0.0F, 13.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -2.9697F));
		head.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(16, 0).addBox(-1.0F, -3.5F, -0.5F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, 0.0F, -5.5303F, -0.7854F, 0.0F, 0.0F));
		final PartDefinition bodyBottom = main.addOrReplaceChild("bodyBottom", CubeListBuilder.create().texOffs(12, 13).addBox(0.0F, -5.5F, 0.0F, 0.0F, 11.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(15, 24).addBox(-1.0F, -2.0F, 0.0F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 1.0F));
		bodyBottom.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 0).addBox(0.0F, -4.5F, 0.0F, 0.0F, 9.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 3.0F));
		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		final float speed = 0.5F;
		this.main.zRot = entity.isInWater() ? 0 : (float) Math.toRadians(-90F);
		this.main.y = entity.isInWater() ? 17.5F : 22F;
//		this.bodyTop.yRot = HekateLib.math.idle(8F, 0F, speed, 0F, ageInTicks, 1F);
//		this.head.yRot = HekateLib.math.idle(8F, 0F, speed, 0.1F, ageInTicks, 1F);
//		this.bodyBottom.yRot = HekateLib.math.idle(-16F, 0F, speed, 0.1F, ageInTicks, 1F);
//		this.tail.yRot = HekateLib.math.idle(-18F, 0F, speed, 0.2F, ageInTicks, 1F);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}