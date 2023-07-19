package com.obscuria.aquamirae.client.models;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

public class ModelPoisonedChakra<T extends Entity> extends EntityModel<T> {
	public final ModelPart main;

	public ModelPoisonedChakra(ModelPart root) {
		this.main = root.getChild("main");
	}

	public static TexturedModelData createModelData() {
		ModelData modelData = new ModelData();
		ModelPartData root = modelData.getRoot();
		ModelPartData main = root.addChild("main", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
		main.addChild("part1",
				ModelPartBuilder.create().uv(0, 0).cuboid(-8.0F, -2.0F, -8.0F, 6.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 3)
						.cuboid(-7.0F, -2.0F, -7.0F, 6.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 6)
						.cuboid(-5.0F, -2.0F, -6.0F, 5.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 9)
						.cuboid(-4.0F, -2.0F, -5.0F, 5.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 12)
						.cuboid(-3.0F, -2.0F, -4.0F, 5.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 15)
						.cuboid(-2.0F, -2.0F, -3.0F, 6.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 18)
						.cuboid(-1.0F, -2.0F, -2.0F, 5.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 21)
						.cuboid(-1.0F, -2.0F, -1.0F, 5.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 24)
						.cuboid(-2.0F, -2.0F, 0.0F, 6.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 27)
						.cuboid(-2.0F, -2.0F, 1.0F, 5.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 30)
						.cuboid(-3.0F, -2.0F, 2.0F, 5.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 33)
						.cuboid(-3.0F, -2.0F, 3.0F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 36)
						.cuboid(-4.0F, -2.0F, 4.0F, 6.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 39)
						.cuboid(-5.0F, -2.0F, 5.0F, 8.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 42)
						.cuboid(-4.0F, -2.0F, 6.0F, 6.0F, 1.0F, 1.0F, new Dilation(0.0F)),
				ModelTransform.pivot(-5.5F, 0.5F, -5.5F));
		main.addChild("part2",
				ModelPartBuilder.create().uv(0, 0).cuboid(-8.0F, -2.0F, -8.0F, 6.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 3)
						.cuboid(-7.0F, -2.0F, -7.0F, 6.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 6)
						.cuboid(-5.0F, -2.0F, -6.0F, 5.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 9)
						.cuboid(-4.0F, -2.0F, -5.0F, 5.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 12)
						.cuboid(-3.0F, -2.0F, -4.0F, 5.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 15)
						.cuboid(-2.0F, -2.0F, -3.0F, 6.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 18)
						.cuboid(-1.0F, -2.0F, -2.0F, 5.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 21)
						.cuboid(-1.0F, -2.0F, -1.0F, 5.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 24)
						.cuboid(-2.0F, -2.0F, 0.0F, 6.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 27)
						.cuboid(-2.0F, -2.0F, 1.0F, 5.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 30)
						.cuboid(-3.0F, -2.0F, 2.0F, 5.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 33)
						.cuboid(-3.0F, -2.0F, 3.0F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 36)
						.cuboid(-4.0F, -2.0F, 4.0F, 6.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 39)
						.cuboid(-5.0F, -2.0F, 5.0F, 8.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 42).cuboid(-4.0F, -2.0F, 6.0F, 6.0F, 1.0F,
								1.0F, new Dilation(0.0F)),
				ModelTransform.of(-5.5F, 0.5F, 5.5F, 0.0F, 1.5708F, 0.0F));
		main.addChild("part3",
				ModelPartBuilder.create().uv(0, 0).cuboid(-8.0F, -2.0F, -8.0F, 6.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 3)
						.cuboid(-7.0F, -2.0F, -7.0F, 6.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 6)
						.cuboid(-5.0F, -2.0F, -6.0F, 5.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 9)
						.cuboid(-4.0F, -2.0F, -5.0F, 5.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 12)
						.cuboid(-3.0F, -2.0F, -4.0F, 5.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 15)
						.cuboid(-2.0F, -2.0F, -3.0F, 6.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 18)
						.cuboid(-1.0F, -2.0F, -2.0F, 5.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 21)
						.cuboid(-1.0F, -2.0F, -1.0F, 5.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 24)
						.cuboid(-2.0F, -2.0F, 0.0F, 6.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 27)
						.cuboid(-2.0F, -2.0F, 1.0F, 5.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 30)
						.cuboid(-3.0F, -2.0F, 2.0F, 5.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 33)
						.cuboid(-3.0F, -2.0F, 3.0F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 36)
						.cuboid(-4.0F, -2.0F, 4.0F, 6.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 39)
						.cuboid(-5.0F, -2.0F, 5.0F, 8.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 42).cuboid(-4.0F, -2.0F, 6.0F, 6.0F, 1.0F,
								1.0F, new Dilation(0.0F)),
				ModelTransform.of(5.5F, 0.5F, 5.5F, -3.1416F, 0.0F, 3.1416F));
		main.addChild("part4",
				ModelPartBuilder.create().uv(0, 0).cuboid(-8.0F, -2.0F, -8.0F, 6.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 3)
						.cuboid(-7.0F, -2.0F, -7.0F, 6.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 6)
						.cuboid(-5.0F, -2.0F, -6.0F, 5.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 9)
						.cuboid(-4.0F, -2.0F, -5.0F, 5.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 12)
						.cuboid(-3.0F, -2.0F, -4.0F, 5.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 15)
						.cuboid(-2.0F, -2.0F, -3.0F, 6.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 18)
						.cuboid(-1.0F, -2.0F, -2.0F, 5.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 21)
						.cuboid(-1.0F, -2.0F, -1.0F, 5.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 24)
						.cuboid(-2.0F, -2.0F, 0.0F, 6.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 27)
						.cuboid(-2.0F, -2.0F, 1.0F, 5.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 30)
						.cuboid(-3.0F, -2.0F, 2.0F, 5.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 33)
						.cuboid(-3.0F, -2.0F, 3.0F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 36)
						.cuboid(-4.0F, -2.0F, 4.0F, 6.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 39)
						.cuboid(-5.0F, -2.0F, 5.0F, 8.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 42).cuboid(-4.0F, -2.0F, 6.0F, 6.0F, 1.0F,
								1.0F, new Dilation(0.0F)),
				ModelTransform.of(5.5F, 0.5F, -5.5F, 0.0F, -1.5708F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}

	@Override
	public void render(MatrixStack stack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		main.render(stack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public void setAngles(T entity, float limbAngle, float limbDistance, float progress, float headYaw, float headPitch) {
		this.main.yaw = (progress + MinecraftClient.getInstance().getTickDelta()) * 0.8F;
	}
}
