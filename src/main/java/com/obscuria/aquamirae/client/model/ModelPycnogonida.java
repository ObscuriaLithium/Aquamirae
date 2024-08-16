package com.obscuria.aquamirae.client.model;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.obscuria.aquamirae.client.animation.PycnogonidaAnimations;
import com.obscuria.aquamirae.common.entity.Pycnogonida;
import com.obscuria.core.client.animation.ModelTools;
import com.obscuria.core.client.animation.tool.IAnimatableModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import org.joml.Vector3f;

import java.util.List;
import java.util.Map;

public class ModelPycnogonida extends EntityModel<Pycnogonida> implements IAnimatableModel, ModelTools {
	private final Map<String, ModelPart> partMap = Maps.newHashMap();
	private final ModelPart root, main, head, body, butt, leftJaw, rightJaw,
			leg1_1, leg2_1, leg3_1, leg4_1, leg5_1, leg6_1, leg7_1, leg8_1,
			leg1_2, leg2_2, leg3_2, leg4_2, leg5_2, leg6_2, leg7_2, leg8_2,
			leg1_3, leg2_3, leg3_3, leg4_3, leg5_3, leg6_3, leg7_3, leg8_3;
	private final List<ModelPart> legsLayer1, legsLayer2, legsLayer3;

	public ModelPycnogonida(ModelPart root) {
		this.root = root;
		this.main = this.definePartChild(root, "main");
		this.body = this.definePartChild(main, "body");
		this.head = this.definePartChild(body, "head");
		this.butt = this.definePartChild(body, "butt");
		this.leftJaw = this.definePartChild(head, "left_jaw");
		this.rightJaw = this.definePartChild(head, "right_jaw");
		this.leg1_1 = this.definePartChild(body.getChild("leg1"), "leg1_1");
		this.leg2_1 = this.definePartChild(body.getChild("leg2"), "leg2_1");
		this.leg3_1 = this.definePartChild(body.getChild("leg3"), "leg3_1");
		this.leg4_1 = this.definePartChild(body.getChild("leg4"), "leg4_1");
		this.leg5_1 = this.definePartChild(body.getChild("leg5"), "leg5_1");
		this.leg6_1 = this.definePartChild(body.getChild("leg6"), "leg6_1");
		this.leg7_1 = this.definePartChild(body.getChild("leg7"), "leg7_1");
		this.leg8_1 = this.definePartChild(body.getChild("leg8"), "leg8_1");
		this.leg1_2 = this.definePartChild(leg1_1, "leg1_2");
		this.leg2_2 = this.definePartChild(leg2_1, "leg2_2");
		this.leg3_2 = this.definePartChild(leg3_1, "leg3_2");
		this.leg4_2 = this.definePartChild(leg4_1, "leg4_2");
		this.leg5_2 = this.definePartChild(leg5_1, "leg5_2");
		this.leg6_2 = this.definePartChild(leg6_1, "leg6_2");
		this.leg7_2 = this.definePartChild(leg7_1, "leg7_2");
		this.leg8_2 = this.definePartChild(leg8_1, "leg8_2");
		this.leg1_3 = this.definePartChild(leg1_2, "leg1_3");
		this.leg2_3 = this.definePartChild(leg2_2, "leg2_3");
		this.leg3_3 = this.definePartChild(leg3_2, "leg3_3");
		this.leg4_3 = this.definePartChild(leg4_2, "leg4_3");
		this.leg5_3 = this.definePartChild(leg5_2, "leg5_3");
		this.leg6_3 = this.definePartChild(leg6_2, "leg6_3");
		this.leg7_3 = this.definePartChild(leg7_2, "leg7_3");
		this.leg8_3 = this.definePartChild(leg8_2, "leg8_3");

		this.legsLayer1 = List.of(leg1_1, leg2_1, leg3_1, leg4_1, leg5_1, leg6_1, leg7_1, leg8_1);
		this.legsLayer2 = List.of(leg1_2, leg2_2, leg3_2, leg4_2, leg5_2, leg6_2, leg7_2, leg8_2);
		this.legsLayer3 = List.of(leg1_3, leg2_3, leg3_3, leg4_3, leg5_3, leg6_3, leg7_3, leg8_3);

		legsLayer2.forEach(part -> part.offsetScale(new Vector3f(-0.2f, -0.2f, -0.2f)));
		legsLayer3.forEach(part -> part.offsetScale(new Vector3f(-0.2f, -0.2f, -0.2f)));
		leftJaw.offsetScale(new Vector3f(-0.2f, -0.2f, -0.2f));
		rightJaw.offsetScale(new Vector3f(-0.2f, -0.2f, -0.2f));
	}

	public static LayerDefinition createBodyLayer() {
		final var meshDefinition = new MeshDefinition();
		final var partDefinition = meshDefinition.getRoot();
		final var main = partDefinition.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0F, 15.0F, 0.0F));
		final var body = main.addOrReplaceChild("body", CubeListBuilder.create().texOffs(23, 29).addBox(-1.0F, -0.5F, -2.75F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(0, 4).addBox(1.0F, -0.5F, -1.75F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(0, 0).addBox(-2.0F, -0.5F, -1.75F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		final var head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 30).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, 0.0F, -2.5F, 0.2618F, 0.0F, 0.0F));
		head.addOrReplaceChild("left_jaw", CubeListBuilder.create().texOffs(12, 30).addBox(0.0F, -0.5F, -0.75F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.5F, -1.75F));
		head.addOrReplaceChild("right_jaw", CubeListBuilder.create().texOffs(8, 30).addBox(-1.0F, -0.5F, -0.75F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.5F, -1.75F));
		final var leg1 = body.addOrReplaceChild("leg1", CubeListBuilder.create(), PartPose.offsetAndRotation(1.0F, -0.25F, -1.5F, -0.9033F, 0.9418F, -0.9895F));
		final var leg1_1 = leg1.addOrReplaceChild("leg1_1", CubeListBuilder.create().texOffs(0, 28).addBox(0.0F, -0.5F, -0.5F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		final var leg1_2 = leg1_1.addOrReplaceChild("leg1_2", CubeListBuilder.create().texOffs(27, 27).addBox(0.0F, -0.5F, -0.5F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(13.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.8727F));
		leg1_2.addOrReplaceChild("leg1_3", CubeListBuilder.create().texOffs(27, 25).addBox(0.0F, -0.5F, -0.5F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(13.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.48F));
		final var leg2 = body.addOrReplaceChild("leg2", CubeListBuilder.create(), PartPose.offsetAndRotation(1.5F, -0.25F, -0.5F, -0.2964F, 0.2615F, -0.8683F));
		final var leg2_1 = leg2.addOrReplaceChild("leg2_1", CubeListBuilder.create().texOffs(27, 23).addBox(0.0F, -0.5F, -0.5F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		final var leg2_2 = leg2_1.addOrReplaceChild("leg2_2", CubeListBuilder.create().texOffs(27, 21).addBox(0.0F, -0.5F, -0.5F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(13.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.3963F));
		leg2_2.addOrReplaceChild("leg2_3", CubeListBuilder.create().texOffs(27, 19).addBox(0.0F, -0.5F, -0.5F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(13.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.3963F));
		final var leg3 = body.addOrReplaceChild("leg3", CubeListBuilder.create(), PartPose.offsetAndRotation(1.5F, -0.25F, 0.5F, 0.4024F, -0.3446F, -0.9F));
		final var leg3_1 = leg3.addOrReplaceChild("leg3_1", CubeListBuilder.create().texOffs(27, 17).addBox(0.0F, -0.5F, -0.5F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		final var leg3_2 = leg3_1.addOrReplaceChild("leg3_2", CubeListBuilder.create().texOffs(27, 15).addBox(0.0F, -0.5F, -0.5F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(13.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.3963F));
		leg3_2.addOrReplaceChild("leg3_3", CubeListBuilder.create().texOffs(27, 13).addBox(0.0F, -0.5F, -0.5F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(13.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.3963F));
		final var leg4 = body.addOrReplaceChild("leg4", CubeListBuilder.create(), PartPose.offsetAndRotation(1.0F, -0.25F, 1.5F, 0.9878F, -0.6956F, -1.1709F));
		final var leg4_1 = leg4.addOrReplaceChild("leg4_1", CubeListBuilder.create().texOffs(27, 11).addBox(0.0F, -0.5F, -0.5F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		final var leg4_2 = leg4_1.addOrReplaceChild("leg4_2", CubeListBuilder.create().texOffs(0, 26).addBox(0.0F, -0.5F, -0.5F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(13.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.3963F));
		leg4_2.addOrReplaceChild("leg4_3", CubeListBuilder.create().texOffs(0, 24).addBox(0.0F, -0.5F, -0.5F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(13.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.3963F));
		final var leg5 = body.addOrReplaceChild("leg5", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0F, -0.25F, -1.5F, -0.9033F, -0.9418F, 0.9895F));
		final var leg5_1 = leg5.addOrReplaceChild("leg5_1", CubeListBuilder.create().texOffs(0, 28).mirror().addBox(-13.0F, -0.5F, -0.5F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));
		final var leg5_2 = leg5_1.addOrReplaceChild("leg5_2", CubeListBuilder.create().texOffs(27, 27).mirror().addBox(-13.0F, -0.5F, -0.5F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-13.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.8727F));
		leg5_2.addOrReplaceChild("leg5_3", CubeListBuilder.create().texOffs(27, 25).mirror().addBox(-13.0F, -0.5F, -0.5F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-13.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.48F));
		final var leg6 = body.addOrReplaceChild("leg6", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.5F, -0.25F, -0.5F, -0.2964F, -0.2615F, 0.8683F));
		final var leg6_1 = leg6.addOrReplaceChild("leg6_1", CubeListBuilder.create().texOffs(27, 23).mirror().addBox(-13.0F, -0.5F, -0.5F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));
		final var leg6_2 = leg6_1.addOrReplaceChild("leg6_2", CubeListBuilder.create().texOffs(27, 21).mirror().addBox(-13.0F, -0.5F, -0.5F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-13.0F, 0.0F, 0.0F, 0.0F, 0.0F, -1.3963F));
		leg6_2.addOrReplaceChild("leg6_3", CubeListBuilder.create().texOffs(27, 19).mirror().addBox(-12.7792F, -0.9964F, -0.1173F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-12.2208F, 0.4964F, -0.3827F, 0.0F, 0.0F, -1.3963F));
		final var leg7 = body.addOrReplaceChild("leg7", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.5F, -0.25F, 0.5F, 0.4024F, 0.3446F, 0.9F));
		final var leg7_1 = leg7.addOrReplaceChild("leg7_1", CubeListBuilder.create().texOffs(27, 17).mirror().addBox(-13.0F, -0.5F, -0.5F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));
		final var leg7_2 = leg7_1.addOrReplaceChild("leg7_2", CubeListBuilder.create().texOffs(27, 15).mirror().addBox(-13.0F, -0.5F, -0.5F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-13.0F, 0.0F, 0.0F, 0.0F, 0.0F, -1.3963F));
		leg7_2.addOrReplaceChild("leg7_3", CubeListBuilder.create().texOffs(27, 13).mirror().addBox(-13.0F, -0.5F, -0.5F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-13.0F, 0.0F, 0.0F, 0.0F, 0.0F, -1.3963F));
		final var leg8 = body.addOrReplaceChild("leg8", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0F, -0.25F, 1.5F, 0.9878F, 0.6956F, 1.1709F));
		final var leg8_1 = leg8.addOrReplaceChild("leg8_1", CubeListBuilder.create().texOffs(27, 11).mirror().addBox(-13.0F, -0.5F, -0.5F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));
		final var leg8_2 = leg8_1.addOrReplaceChild("leg8_2", CubeListBuilder.create().texOffs(0, 26).mirror().addBox(-13.0F, -0.5F, -0.5F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-13.0F, 0.0F, 0.0F, 0.0F, 0.0F, -1.3963F));
		leg8_2.addOrReplaceChild("leg8_3", CubeListBuilder.create().texOffs(0, 24).mirror().addBox(-13.0F, -0.5F, -0.5F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-13.0F, 0.0F, 0.0F, 0.0F, 0.0F, -1.3963F));
		body.addOrReplaceChild("butt", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.0F, 0.0F, 3.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 2.0F, -0.6545F, 0.0F, 0.0F));
		return LayerDefinition.create(meshDefinition, 64, 64);
	}

	@Override
	public ModelPart getRoot() {
		return this.root;
	}

	@Override
	public Map<String, ModelPart> getPartsMap() {
		return this.partMap;
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay,
							   float red, float green, float blue, float alpha) {
		main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, 0.25f);
	}

	@Override
	public void setupAnim(Pycnogonida entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		PycnogonidaAnimations.IDLE.animate(this, entity, limbSwing, limbSwingAmount);
	}
}