package com.obscuria.aquamirae.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.obscuria.aquamirae.common.entity.Pycnogonida;
import com.obscuria.core.api.animation.ModelTools;
import com.obscuria.core.api.animation.entity.IAnimatableModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import org.joml.Vector3f;

import java.util.List;

public class ModelPycnogonida extends EntityModel<Pycnogonida> implements IAnimatableModel, ModelTools {
	private final ModelPart root, main, head, body, butt,
			leg1, leg2, leg3, leg4, leg5, leg6, leg7, leg8,
			leg1_1, leg2_1, leg3_1, leg4_1, leg5_1, leg6_1, leg7_1, leg8_1,
			leg1_2, leg2_2, leg3_2, leg4_2, leg5_2, leg6_2, leg7_2, leg8_2;
	private final List<ModelPart> legsLayer0, legsLayer1, legsLayer2;

	public ModelPycnogonida(ModelPart root) {
		this.root = root;
		this.main = root.getChild("main");
		this.body = main.getChild("body");
		this.head = body.getChild("head");
		this.butt = body.getChild("butt");
		this.leg1 = body.getChild("leftLegs").getChild("leg1");
		this.leg2 = body.getChild("leftLegs").getChild("leg2");
		this.leg3 = body.getChild("leftLegs").getChild("leg3");
		this.leg4 = body.getChild("leftLegs").getChild("leg4");
		this.leg5 = body.getChild("rightLegs").getChild("leg5");
		this.leg6 = body.getChild("rightLegs").getChild("leg6");
		this.leg7 = body.getChild("rightLegs").getChild("leg7");
		this.leg8 = body.getChild("rightLegs").getChild("leg8");
		this.leg1_1 = leg1.getChild("leg1_1");
		this.leg2_1 = leg2.getChild("leg2_1");
		this.leg3_1 = leg3.getChild("leg3_1");
		this.leg4_1 = leg4.getChild("leg4_1");
		this.leg5_1 = leg5.getChild("leg5_1");
		this.leg6_1 = leg6.getChild("leg6_1");
		this.leg7_1 = leg7.getChild("leg7_1");
		this.leg8_1 = leg8.getChild("leg8_1");
		this.leg1_2 = leg1_1.getChild("leg1_2");
		this.leg2_2 = leg2_1.getChild("leg2_2");
		this.leg3_2 = leg3_1.getChild("leg3_2");
		this.leg4_2 = leg4_1.getChild("leg4_2");
		this.leg5_2 = leg5_1.getChild("leg5_2");
		this.leg6_2 = leg6_1.getChild("leg6_2");
		this.leg7_2 = leg7_1.getChild("leg7_2");
		this.leg8_2 = leg8_1.getChild("leg8_2");

		this.legsLayer0 = List.of(leg1, leg2, leg3, leg4, leg5, leg6, leg7, leg8);
		this.legsLayer1 = List.of(leg1_1, leg2_1, leg3_1, leg4_1, leg5_1, leg6_1, leg7_1, leg8_1);
		this.legsLayer2 = List.of(leg1_2, leg2_2, leg3_2, leg4_2, leg5_2, leg6_2, leg7_2, leg8_2);

		final var scaleOffset = new Vector3f(-0.1f, -0.1f, -0.1f);
		legsLayer1.forEach(part -> part.offsetScale(scaleOffset));
		legsLayer2.forEach(part -> part.offsetScale(scaleOffset));
	}

	public static LayerDefinition createBodyLayer() {
		final var meshDefinition = new MeshDefinition();
		final var partDefinition = meshDefinition.getRoot();
		final var main = partDefinition.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0F, 15.0F, 0.0F));
		final var body = main.addOrReplaceChild("body", CubeListBuilder.create().texOffs(23, 29).addBox(-1.0F, -0.5F, -2.75F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(0, 4).addBox(1.0F, -0.5F, -1.75F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(0, 0).addBox(-2.0F, -0.5F, -1.75F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		final var head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 30).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, 0.0F, -2.5F, 0.2618F, 0.0F, 0.0F));
		head.addOrReplaceChild("leftJaw", CubeListBuilder.create().texOffs(12, 30).addBox(0.0F, -0.5F, -0.75F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.5F, -1.75F));
		head.addOrReplaceChild("rightJaw", CubeListBuilder.create().texOffs(8, 30).addBox(-1.0F, -0.5F, -0.75F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.5F, -1.75F));
		final var leftLegs = body.addOrReplaceChild("leftLegs", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		final var leg1 = leftLegs.addOrReplaceChild("leg1", CubeListBuilder.create().texOffs(0, 28).addBox(0.0F, -0.5F, -0.5F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -0.25F, -1.5F, -0.9033F, 0.9854F, -0.9895F));
		final var leg1_1 = leg1.addOrReplaceChild("leg1_1", CubeListBuilder.create().texOffs(27, 27).addBox(0.0F, -0.5F, -0.5F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(13.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.8727F));
		leg1_1.addOrReplaceChild("leg1_2", CubeListBuilder.create().texOffs(27, 25).addBox(0.0F, -0.5F, -0.5F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(13.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.48F));
		final var leg2 = leftLegs.addOrReplaceChild("leg2", CubeListBuilder.create().texOffs(27, 23).addBox(0.0F, -0.5F, -0.5F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, -0.25F, -0.5F, -0.2964F, 0.2615F, -0.8683F));
		final var leg2_1 = leg2.addOrReplaceChild("leg2_1", CubeListBuilder.create().texOffs(27, 21).addBox(0.0F, -0.5F, -0.5F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(13.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.3963F));
		leg2_1.addOrReplaceChild("leg2_2", CubeListBuilder.create().texOffs(27, 19).addBox(0.0F, -0.5F, -0.5F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(13.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.3963F));
		final var leg3 = leftLegs.addOrReplaceChild("leg3", CubeListBuilder.create().texOffs(27, 17).addBox(0.0F, -0.5F, -0.5F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, -0.25F, 0.5F, 0.4024F, -0.3446F, -0.9F));
		final var leg3_1 = leg3.addOrReplaceChild("leg3_1", CubeListBuilder.create().texOffs(27, 15).addBox(0.0F, -0.5F, -0.5F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(13.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.3963F));
		leg3_1.addOrReplaceChild("leg3_2", CubeListBuilder.create().texOffs(27, 13).addBox(0.0F, -0.5F, -0.5F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(13.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.3963F));
		final var leg4 = leftLegs.addOrReplaceChild("leg4", CubeListBuilder.create().texOffs(27, 11).addBox(0.0F, -0.5F, -0.5F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -0.25F, 1.5F, 0.9878F, -0.6956F, -1.1709F));
		final var leg4_1 = leg4.addOrReplaceChild("leg4_1", CubeListBuilder.create().texOffs(0, 26).addBox(0.0F, -0.5F, -0.5F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(13.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.3963F));
		leg4_1.addOrReplaceChild("leg4_2", CubeListBuilder.create().texOffs(0, 24).addBox(0.0F, -0.5F, -0.5F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(13.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.3963F));
		leg4_1.addOrReplaceChild("leg4_2", CubeListBuilder.create().texOffs(0, 24).addBox(0.0F, -0.5F, 2.5F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(13.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.3963F));
		final var rightLegs = body.addOrReplaceChild("rightLegs", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -3.1416F, -0.0873F, 3.1416F));
		final var leg5 = rightLegs.addOrReplaceChild("leg5", CubeListBuilder.create().texOffs(22, 8).addBox(0.0F, -0.5F, -0.5F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -0.25F, -1.5F, -1.1122F, 0.6878F, -1.2671F));
		final var leg5_1 = leg5.addOrReplaceChild("leg5_1", CubeListBuilder.create().texOffs(0, 22).addBox(0.0F, -0.5F, -0.5F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(13.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.3963F));
		leg5_1.addOrReplaceChild("leg5_2", CubeListBuilder.create().texOffs(0, 20).addBox(0.0F, -0.5F, -0.5F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(13.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.3963F));
		final var leg6 = rightLegs.addOrReplaceChild("leg6", CubeListBuilder.create().texOffs(0, 18).addBox(0.0F, -0.5F, -0.5F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, -0.25F, -0.5F, -0.2964F, 0.2615F, -0.8683F));
		final var leg6_1 = leg6.addOrReplaceChild("leg6_1", CubeListBuilder.create().texOffs(0, 16).addBox(0.0F, -0.5F, -0.5F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(13.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.3963F));
		leg6_1.addOrReplaceChild("leg6_2", CubeListBuilder.create().texOffs(14, 6).addBox(0.0F, -0.5F, -0.5F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(13.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.3963F));
		final var leg7 = rightLegs.addOrReplaceChild("leg7", CubeListBuilder.create().texOffs(14, 4).addBox(0.0F, -0.5F, -0.5F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, -0.25F, 0.5F, 0.4024F, -0.3446F, -0.9F));
		final var leg7_1 = leg7.addOrReplaceChild("leg7_1", CubeListBuilder.create().texOffs(14, 2).addBox(0.0F, -0.5F, -0.5F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(13.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.3963F));
		leg7_1.addOrReplaceChild("leg7_2", CubeListBuilder.create().texOffs(14, 0).addBox(0.0F, -0.5F, -0.5F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(13.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.3963F));
		final var leg8 = rightLegs.addOrReplaceChild("leg8", CubeListBuilder.create().texOffs(0, 14).addBox(0.0F, -0.5F, -0.5F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -0.25F, 1.5F, 1.0457F, -1.1243F, -1.0893F));
		final var leg8_1 = leg8.addOrReplaceChild("leg8_1", CubeListBuilder.create().texOffs(0, 12).addBox(0.0F, -0.5F, -0.5F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(13.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.6981F));
		leg8_1.addOrReplaceChild("leg8_2", CubeListBuilder.create().texOffs(0, 10).addBox(0.0F, -0.5F, -0.5F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(13.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.6109F));
		body.addOrReplaceChild("butt", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.0F, 0.0F, 3.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 2.0F, -0.6545F, 0.0F, 0.0F));
		return LayerDefinition.create(meshDefinition, 64, 64);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay,
							   float red, float green, float blue, float alpha) {
		main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, 0.25f);
	}

	@Override
	public void setupAnim(Pycnogonida entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		legsLayer1.forEach(part -> setScale(part, 0.8f, 0.8f, 0.8f));
		legsLayer2.forEach(part -> setScale(part, 0.8f, 0.8f, 0.8f));
	}

	@Override
	public ModelPart getRoot() {
		return this.root;
	}
}