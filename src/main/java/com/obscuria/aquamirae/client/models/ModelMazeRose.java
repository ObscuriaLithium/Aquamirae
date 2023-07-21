package com.obscuria.aquamirae.client.models;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

public class ModelMazeRose<T extends Entity> extends EntityModel<T> {
	public final ModelPart main;

	public ModelMazeRose(ModelPart root) {
		this.main = root.getChild("main");
	}

	public static TexturedModelData createModelData() {
		ModelData modelData = new ModelData();
		ModelPartData root = modelData.getRoot();
		ModelPartData main = root.addChild("main", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 23.0F, 0.0F));
		main.addChild("part1",
				ModelPartBuilder.create().uv(0, 0).cuboid(0.5F, -1.0F, 13.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 2)
						.cuboid(0.5F, -1.0F, 12.5F, 2.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 4)
						.cuboid(0.5F, -1.0F, 11.5F, 3.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 6)
						.cuboid(0.5F, -1.0F, 10.5F, 3.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 8)
						.cuboid(0.5F, -1.0F, 9.5F, 3.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 10)
						.cuboid(0.5F, -1.0F, 8.5F, 10.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(8, 8)
						.cuboid(6.5F, -1.0F, 9.5F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 12)
						.cuboid(0.5F, -1.0F, 7.5F, 10.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 14)
						.cuboid(0.5F, -1.0F, 6.5F, 10.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 16)
						.cuboid(0.5F, -1.0F, 5.5F, 9.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 18)
						.cuboid(0.5F, -1.0F, 4.5F, 9.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 20)
						.cuboid(0.5F, -1.0F, 3.5F, 8.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 22)
						.cuboid(0.5F, -1.0F, 2.5F, 12.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 24)
						.cuboid(2.5F, -1.0F, 1.5F, 11.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 26)
						.cuboid(2.5F, -1.0F, 0.5F, 12.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 28)
						.cuboid(1.5F, -1.0F, -0.5F, 14.0F, 1.0F, 1.0F, new Dilation(0.0F)),
				ModelTransform.pivot(0.0F, 0.5F, 0.0F));
		main.addChild("part2",
				ModelPartBuilder.create().uv(0, 0).cuboid(0.5F, -1.0F, 13.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 2)
						.cuboid(0.5F, -1.0F, 12.5F, 2.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 4)
						.cuboid(0.5F, -1.0F, 11.5F, 3.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 6)
						.cuboid(0.5F, -1.0F, 10.5F, 3.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 8)
						.cuboid(0.5F, -1.0F, 9.5F, 3.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 10)
						.cuboid(0.5F, -1.0F, 8.5F, 10.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(8, 8)
						.cuboid(6.5F, -1.0F, 9.5F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 12)
						.cuboid(0.5F, -1.0F, 7.5F, 10.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 14)
						.cuboid(0.5F, -1.0F, 6.5F, 10.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 16)
						.cuboid(0.5F, -1.0F, 5.5F, 9.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 18)
						.cuboid(0.5F, -1.0F, 4.5F, 9.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 20)
						.cuboid(0.5F, -1.0F, 3.5F, 8.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 22)
						.cuboid(0.5F, -1.0F, 2.5F, 12.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 24)
						.cuboid(2.5F, -1.0F, 1.5F, 11.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 26)
						.cuboid(2.5F, -1.0F, 0.5F, 12.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 28).cuboid(1.5F, -1.0F, -0.5F, 14.0F,
								1.0F, 1.0F, new Dilation(0.0F)),
				ModelTransform.of(0.0F, 0.5F, 0.0F, 0.0F, -1.5708F, 0.0F));
		main.addChild("part3",
				ModelPartBuilder.create().uv(0, 0).cuboid(0.5F, -1.0F, 13.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 2)
						.cuboid(0.5F, -1.0F, 12.5F, 2.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 4)
						.cuboid(0.5F, -1.0F, 11.5F, 3.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 6)
						.cuboid(0.5F, -1.0F, 10.5F, 3.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 8)
						.cuboid(0.5F, -1.0F, 9.5F, 3.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 10)
						.cuboid(0.5F, -1.0F, 8.5F, 10.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(8, 8)
						.cuboid(6.5F, -1.0F, 9.5F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 12)
						.cuboid(0.5F, -1.0F, 7.5F, 10.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 14)
						.cuboid(0.5F, -1.0F, 6.5F, 10.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 16)
						.cuboid(0.5F, -1.0F, 5.5F, 9.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 18)
						.cuboid(0.5F, -1.0F, 4.5F, 9.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 20)
						.cuboid(0.5F, -1.0F, 3.5F, 8.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 22)
						.cuboid(0.5F, -1.0F, 2.5F, 12.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 24)
						.cuboid(2.5F, -1.0F, 1.5F, 11.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 26)
						.cuboid(2.5F, -1.0F, 0.5F, 12.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 28).cuboid(1.5F, -1.0F, -0.5F, 14.0F,
								1.0F, 1.0F, new Dilation(0.0F)),
				ModelTransform.of(0.0F, 0.5F, 0.0F, -3.1416F, 0.0F, 3.1416F));
		main.addChild("part4",
				ModelPartBuilder.create().uv(0, 0).cuboid(0.5F, -1.0F, 13.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 2)
						.cuboid(0.5F, -1.0F, 12.5F, 2.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 4)
						.cuboid(0.5F, -1.0F, 11.5F, 3.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 6)
						.cuboid(0.5F, -1.0F, 10.5F, 3.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 8)
						.cuboid(0.5F, -1.0F, 9.5F, 3.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 10)
						.cuboid(0.5F, -1.0F, 8.5F, 10.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(8, 8)
						.cuboid(6.5F, -1.0F, 9.5F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 12)
						.cuboid(0.5F, -1.0F, 7.5F, 10.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 14)
						.cuboid(0.5F, -1.0F, 6.5F, 10.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 16)
						.cuboid(0.5F, -1.0F, 5.5F, 9.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 18)
						.cuboid(0.5F, -1.0F, 4.5F, 9.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 20)
						.cuboid(0.5F, -1.0F, 3.5F, 8.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 22)
						.cuboid(0.5F, -1.0F, 2.5F, 12.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 24)
						.cuboid(2.5F, -1.0F, 1.5F, 11.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 26)
						.cuboid(2.5F, -1.0F, 0.5F, 12.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 28).cuboid(1.5F, -1.0F, -0.5F, 14.0F,
								1.0F, 1.0F, new Dilation(0.0F)),
				ModelTransform.of(0.0F, 0.5F, 0.0F, 0.0F, 1.5708F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}

	@Override
	public void render(MatrixStack stack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		main.render(stack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setAngles(T entity, float limbAngle, float limbDistance, float progress, float headYaw, float headPitch) {
		this.main.yaw = progress * 0.8F;
	}
}
