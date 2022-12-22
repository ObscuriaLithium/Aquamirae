package com.obscuria.aquamirae.client.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.obscureapi.client.animations.HekateLib;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class ModelGoldenMoth<T extends Entity> extends EntityModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(AquamiraeMod.MODID, "golden_moth"), "main");
	public final ModelPart body;
	public final ModelPart wing1;
	public final ModelPart wing2;

	public ModelGoldenMoth(ModelPart root) {
		this.body = root.getChild("body");
		this.wing1 = root.getChild("wing1");
		this.wing2 = root.getChild("wing2");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition body = partdefinition.addOrReplaceChild("body",
				CubeListBuilder.create().texOffs(0, 10).addBox(-0.5F, -0.5F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 23.0F, 0.0F));
		PartDefinition wing1 = partdefinition.addOrReplaceChild("wing1",
				CubeListBuilder.create().texOffs(0, 7).addBox(0.0F, 0.0F, -3.5F, 4.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 23.0F, 0.5F));
		PartDefinition wing2 = partdefinition.addOrReplaceChild("wing2",
				CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, 0.0F, -3.5F, 4.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 23.0F, 0.5F));
		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green,
							   float blue, float alpha) {
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		wing1.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		wing2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setupAnim(@NotNull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.wing1.zRot = HekateLib.render.idle(75F, 0F, 1.5F, 0F, ageInTicks, 1F);
		this.wing2.zRot = -this.wing1.zRot;
	}
}
