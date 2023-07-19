package com.obscuria.aquamirae.client.models;

import com.obscuria.obscureapi.api.hekate.HekateLib;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

public class ModelGoldenMoth<T extends Entity> extends EntityModel<T> {
	public final ModelPart body, wing1, wing2;

	public ModelGoldenMoth(ModelPart root) {
		this.body = root.getChild("body");
		this.wing1 = root.getChild("wing1");
		this.wing2 = root.getChild("wing2");
	}

	public static TexturedModelData createModelData() {
		ModelData modelData = new ModelData();
		ModelPartData root = modelData.getRoot();
		root.addChild("body",
				ModelPartBuilder.create().uv(0, 10).cuboid(-0.5F, -0.5F, -2.0F, 1.0F, 1.0F, 4.0F),
				ModelTransform.pivot(0.0F, 23.0F, 0.0F));
		root.addChild("wing1",
				ModelPartBuilder.create().uv(0, 7).cuboid(0.0F, 0.0F, -3.5F, 4.0F, 0.0F, 7.0F),
				ModelTransform.pivot(0.0F, 23.0F, 0.5F));
		root.addChild("wing2",
				ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, 0.0F, -3.5F, 4.0F, 0.0F, 7.0F),
				ModelTransform.pivot(0.0F, 23.0F, 0.5F));
		return TexturedModelData.of(modelData, 32, 32);
	}

	@Override
	public void render(MatrixStack stack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		body.render(stack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		wing1.render(stack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		wing2.render(stack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public void setAngles(T entity, float limbAngle, float limbDistance, float progress, float headYaw, float headPitch) {
		this.wing1.roll = HekateLib.math.idle(75F, 0F, 1.5F, 0F, progress, 1F);
		this.wing2.roll = -this.wing1.roll;
	}
}
