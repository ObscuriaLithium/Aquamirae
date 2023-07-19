package com.obscuria.aquamirae.client.models;

import com.obscuria.obscureapi.api.hekate.HekateLib;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

public class ModelSpinefish<T extends Entity> extends EntityModel<T> {
	private final ModelPart main, bodyTop, bodyBottom, head, tail;

	public ModelSpinefish(ModelPart root) {
		this.main = root.getChild("main");
		this.bodyTop = main.getChild("bodyTop");
		this.bodyBottom = main.getChild("bodyBottom");
		this.head = bodyTop.getChild("head");
		this.tail = bodyBottom.getChild("tail");
	}

	public static TexturedModelData createModelData() {
		final ModelData modelData = new ModelData();
		final ModelPartData root = modelData.getRoot();
		final ModelPartData main = root.addChild("main", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 17.5F, 0.0F));
		final ModelPartData bodyTop = main.addChild("bodyTop", ModelPartBuilder.create().uv(16, 8).cuboid(-1.0F, -2.5F, -3.0F, 2.0F, 5.0F, 3.0F, new Dilation(0.0F)).uv(10, 0).cuboid(0.0F, -6.5F, -3.0F, 0.0F, 13.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 1.0F));
		final ModelPartData head = bodyTop.addChild("head", ModelPartBuilder.create().uv(6, 13).cuboid(0.0F, -5.5F, -6.0303F, 0.0F, 11.0F, 3.0F, new Dilation(0.0F)).uv(18, 16).cuboid(-1.0F, -2.5F, -3.0303F, 2.0F, 5.0F, 3.0F, new Dilation(0.0F)).uv(0, 11).cuboid(0.0F, -6.5F, -3.0303F, 0.0F, 13.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, -2.9697F));
		head.addChild("cube_r1", ModelPartBuilder.create().uv(16, 0).cuboid(-1.0F, -3.5F, -0.5F, 2.0F, 4.0F, 4.0F, new Dilation(0.01F)), ModelTransform.of(0.0F, 0.0F, -5.5303F, -0.7854F, 0.0F, 0.0F));
		final ModelPartData bodyBottom = main.addChild("bodyBottom", ModelPartBuilder.create().uv(12, 13).cuboid(0.0F, -5.5F, 0.0F, 0.0F, 11.0F, 3.0F, new Dilation(0.0F)).uv(15, 24).cuboid(-1.0F, -2.0F, 0.0F, 2.0F, 4.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 1.0F));
		bodyBottom.addChild("tail", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)).uv(0, 0).cuboid(0.0F, -4.5F, 0.0F, 0.0F, 9.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 3.0F));
		return TexturedModelData.of(modelData, 32, 32);
	}

	@Override
	public void render(MatrixStack stack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		main.render(stack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public void setAngles(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		final float speed = 0.5F;
		this.main.roll = entity.isTouchingWater() ? 0 : (float) Math.toRadians(-90F);
		this.main.pivotY = entity.isTouchingWater() ? 17.5F : 22F;
		this.bodyTop.yaw = HekateLib.math.idle(8F, 0F, speed, 0F, ageInTicks, 1F);
		this.head.yaw = HekateLib.math.idle(8F, 0F, speed, 0.1F, ageInTicks, 1F);
		this.bodyBottom.yaw = HekateLib.math.idle(-16F, 0F, speed, 0.1F, ageInTicks, 1F);
		this.tail.yaw = HekateLib.math.idle(-18F, 0F, speed, 0.2F, ageInTicks, 1F);
	}
}