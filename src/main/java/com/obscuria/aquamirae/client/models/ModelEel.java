package com.obscuria.aquamirae.client.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.obscureapi.api.animations.HekateLib;
import com.obscuria.obscureapi.client.animations.AnimatedPart;
import com.obscuria.obscureapi.client.animations.KeyFrame;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class ModelEel<T extends Entity> extends EntityModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(AquamiraeMod.MODID, "eel"), "main");
	public final ModelPart main, body1, body2, body3, body4, body5, body6, body7, body8, body9, body10, head, headTop, headBottom, leftFin, rightFin;

	public ModelEel(ModelPart root) {
		this.main = root.getChild("main");
		this.body1 = main.getChild("body1");
		this.body2 = body1.getChild("body2");
		this.body3 = body2.getChild("body3");
		this.body4 = body3.getChild("body4");
		this.body5 = body4.getChild("body5");
		this.body6 = body5.getChild("body6");
		this.body7 = body6.getChild("body7");
		this.body8 = body7.getChild("body8");
		this.body9 = body8.getChild("body9");
		this.body10 = body9.getChild("body10");
		this.head = body10.getChild("head");
		this.headTop = head.getChild("headTop");
		this.headBottom = head.getChild("headBottom");
		this.leftFin = head.getChild("leftFinP").getChild("leftFin");
		this.rightFin = head.getChild("rightFinP").getChild("rightFin");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 300.0F));
		PartDefinition body1 = main.addOrReplaceChild("body1", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, 0.0F, -300.0F, 0.5672F, 0.0F, 0.0F));
		PartDefinition body2 = body1.addOrReplaceChild("body2", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, -5.0F, 0.0F, -0.3491F, 0.0F, 0.0F));
		PartDefinition body3 = body2.addOrReplaceChild("body3", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, -6.0F, 0.0F, -0.6109F, 0.0F, 0.0F));
		PartDefinition body4 = body3.addOrReplaceChild("body4", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, -6.0F, 0.0F, -0.5236F, 0.0F, 0.0F));
		PartDefinition body5 = body4.addOrReplaceChild("body5", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, -6.0F, 0.0F, 0.5236F, 0.0F, 0.0F));
		PartDefinition body6 = body5.addOrReplaceChild("body6", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, -7.0F, 0.0F, 0.5672F, 0.0F, 0.0F));
		PartDefinition body7 = body6.addOrReplaceChild("body7", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, -5.0F, 0.0F, 0.6109F, 0.0F, 0.0F));
		PartDefinition body8 = body7.addOrReplaceChild("body8", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, -6.0F, 0.0F, 0.5236F, 0.0F, 0.0F));
		PartDefinition body9 = body8.addOrReplaceChild("body9", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, -6.0F, 0.0F, 0.3927F, 0.0F, 0.0F));
		PartDefinition body10 = body9.addOrReplaceChild("body10", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, -6.0F, 0.0F, 0.2182F, 0.0F, 0.0F));
		PartDefinition head = body10.addOrReplaceChild("head", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, -3.0F, 0.0F, 0.2182F, 0.0F, 0.0F));
		PartDefinition headTop = head.addOrReplaceChild("headTop", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, -11.0F, -6.0F, 0.6981F, 0.0F, 0.0F));
		PartDefinition bone3 = headTop.addOrReplaceChild("bone3",
				CubeListBuilder.create().texOffs(84, 61).addBox(-6.0F, -42.0F, -6.0F, 12.0F, 8.0F, 12.0F, new CubeDeformation(0.0F)).texOffs(12, 0)
						.addBox(6.0F, -40.0F, -3.0F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(12, 8)
						.addBox(-3.0F, -40.0F, 6.0F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(84, 82)
						.addBox(-6.0F, -46.5F, -6.0F, 12.0F, 5.0F, 12.0F, new CubeDeformation(-0.5F)).texOffs(84, 82)
						.addBox(-7.0F, -46.0F, -7.0F, 12.0F, 5.0F, 12.0F, new CubeDeformation(-1.0F)),
				PartPose.offsetAndRotation(0.0F, 42.5507F, 4.774F, 0.0F, -0.7854F, 0.0F));
		PartDefinition headBottom = head.addOrReplaceChild("headBottom", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, -11.0F, -6.0F, 2.0944F, 0.0F, 0.0F));
		PartDefinition bone5 = headBottom.addOrReplaceChild("bone5",
				CubeListBuilder.create().texOffs(12, 23).addBox(-9.0F, -42.0F, -9.0F, 15.0F, 2.0F, 15.0F, new CubeDeformation(0.0F)).texOffs(12, 0)
						.addBox(-9.0F, -40.5F, -9.0F, 15.0F, 8.0F, 15.0F, new CubeDeformation(-0.5F)).texOffs(12, 0)
						.addBox(-10.0F, -41.0F, -10.0F, 15.0F, 8.0F, 15.0F, new CubeDeformation(-1.0F)),
				PartPose.offsetAndRotation(0.0F, 40.2191F, 4.5237F, 0.0F, -0.7854F, 0.0F));
		PartDefinition bone2 = head.addOrReplaceChild("bone2",
				CubeListBuilder.create().texOffs(12, 40).addBox(-6.0F, -45.0F, -6.0F, 12.0F, 12.0F, 12.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 35.0F, 0.0F, 0.0F, -0.7854F, 0.0F));
		PartDefinition bone11 = bone2.addOrReplaceChild("bone11",
				CubeListBuilder.create().texOffs(169, -8).addBox(0.0F, -8.0F, 7.2426F, 0.0F, 15.0F, 9.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -40.0F, 0.0F, 0.0F, 0.7854F, 0.0F));
		PartDefinition bone13 = bone2.addOrReplaceChild("bone13",
				CubeListBuilder.create().texOffs(169, -8).addBox(0.0F, -8.0F, 7.2426F, 0.0F, 15.0F, 9.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -40.0F, 0.0F, 0.0F, 2.3562F, 0.0F));
		PartDefinition bone15 = bone2.addOrReplaceChild("bone15",
				CubeListBuilder.create().texOffs(169, -8).addBox(0.0F, -8.0F, 7.2426F, 0.0F, 15.0F, 9.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -40.0F, 0.0F, 0.0F, -0.7854F, 0.0F));
		PartDefinition leftFinP = head.addOrReplaceChild("leftFinP", CubeListBuilder.create(),
				PartPose.offsetAndRotation(4.0F, -7.0F, -4.0F, 0.0F, 0.7854F, 0.0F));
		PartDefinition leftFin = leftFinP.addOrReplaceChild("leftFin",
				CubeListBuilder.create().texOffs(137, -9).addBox(0.5F, -1.0F, -5.0F, 0.0F, 18.0F, 10.0F, new CubeDeformation(0.0F)).texOffs(158, 1)
						.addBox(0.0F, -1.0F, -2.0F, 1.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.9599F));
		PartDefinition rightFinP = head.addOrReplaceChild("rightFinP", CubeListBuilder.create(),
				PartPose.offsetAndRotation(-5.0F, -7.0F, -4.0F, 0.0F, -0.7854F, 0.0F));
		PartDefinition rightFin = rightFinP.addOrReplaceChild("rightFin",
				CubeListBuilder.create().texOffs(137, -9).addBox(0.5F, -1.0F, -5.0F, 0.0F, 18.0F, 10.0F, new CubeDeformation(0.0F)).texOffs(158, 1)
						.addBox(0.0F, -1.0F, -2.0F, 1.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0908F));
		PartDefinition bone20 = body10.addOrReplaceChild("bone20",
				CubeListBuilder.create().texOffs(48, 52).addBox(-6.0F, -44.0F, -6.0F, 12.0F, 9.0F, 12.0F, new CubeDeformation(-0.1F)),
				PartPose.offsetAndRotation(0.0F, 40.0F, 0.0F, 0.0F, -0.7854F, 0.0F));
		PartDefinition bone17 = bone20.addOrReplaceChild("bone17",
				CubeListBuilder.create().texOffs(170, -8).addBox(0.0F, 3.0F, 5.0F, 0.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -47.0F, 0.0F, 0.0F, 0.7854F, 0.0F));
		PartDefinition bone19 = bone20.addOrReplaceChild("bone19",
				CubeListBuilder.create().texOffs(170, -8).addBox(0.0F, 3.0F, 5.0F, 0.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -47.0F, 0.0F, 0.0F, 2.3562F, 0.0F));
		PartDefinition bone21 = bone20.addOrReplaceChild("bone21",
				CubeListBuilder.create().texOffs(170, -8).addBox(0.0F, 3.0F, 5.0F, 0.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -47.0F, 0.0F, 0.0F, -0.7854F, 0.0F));
		PartDefinition bone18 = body9.addOrReplaceChild("bone18",
				CubeListBuilder.create().texOffs(60, 11).addBox(-6.0F, -34.0F, -6.0F, 12.0F, 9.0F, 12.0F, new CubeDeformation(-0.15F)),
				PartPose.offsetAndRotation(0.0F, 30.0F, 0.0F, 0.0F, -0.7854F, 0.0F));
		PartDefinition bone22 = bone18.addOrReplaceChild("bone22",
				CubeListBuilder.create().texOffs(170, -8).addBox(0.0F, 2.0F, 3.0F, 0.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -37.0F, 0.0F, 0.0F, 0.7854F, 0.0F));
		PartDefinition bone23 = bone18.addOrReplaceChild("bone23",
				CubeListBuilder.create().texOffs(170, -8).addBox(0.0F, 2.0F, 3.0F, 0.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -37.0F, 0.0F, 0.0F, 2.3562F, 0.0F));
		PartDefinition bone24 = bone18.addOrReplaceChild("bone24",
				CubeListBuilder.create().texOffs(170, -8).addBox(0.0F, 2.0F, 3.0F, 0.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -37.0F, 0.0F, 0.0F, -0.7854F, 0.0F));
		PartDefinition bone16 = body8.addOrReplaceChild("bone16",
				CubeListBuilder.create().texOffs(12, 64).addBox(-6.0F, -24.0F, -6.0F, 12.0F, 9.0F, 12.0F, new CubeDeformation(-0.2F)),
				PartPose.offsetAndRotation(0.0F, 20.0F, 0.0F, 0.0F, -0.7854F, 0.0F));
		PartDefinition bone14 = body7.addOrReplaceChild("bone14",
				CubeListBuilder.create().texOffs(48, 73).addBox(-6.0F, -4.0F, -6.0F, 12.0F, 9.0F, 12.0F, new CubeDeformation(-0.25F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));
		PartDefinition bone12 = body6.addOrReplaceChild("bone12",
				CubeListBuilder.create().texOffs(84, 32).addBox(-6.0F, -3.0F, -6.0F, 12.0F, 9.0F, 12.0F, new CubeDeformation(-0.3F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));
		PartDefinition bone10 = body5.addOrReplaceChild("bone10",
				CubeListBuilder.create().texOffs(2, 85).addBox(-5.0F, -44.0F, -5.0F, 10.0F, 8.0F, 10.0F, new CubeDeformation(0.2F)),
				PartPose.offsetAndRotation(0.0F, 40.0F, 0.0F, 0.0F, -0.7854F, 0.0F));
		PartDefinition bone8 = body4.addOrReplaceChild("bone8",
				CubeListBuilder.create().texOffs(32, 94).addBox(-5.0F, -34.0F, -5.0F, 10.0F, 8.0F, 10.0F, new CubeDeformation(0.15F)),
				PartPose.offsetAndRotation(0.0F, 30.0F, 0.0F, 0.0F, -0.7854F, 0.0F));
		PartDefinition bone6 = body3.addOrReplaceChild("bone6",
				CubeListBuilder.create().texOffs(96, 0).addBox(-5.0F, -24.0F, -5.0F, 10.0F, 8.0F, 10.0F, new CubeDeformation(0.1F)),
				PartPose.offsetAndRotation(0.0F, 20.0F, 0.0F, 0.0F, -0.7854F, 0.0F));
		PartDefinition bone4 = body2.addOrReplaceChild("bone4",
				CubeListBuilder.create().texOffs(72, 99).addBox(-5.0F, -4.0F, -5.0F, 10.0F, 8.0F, 10.0F, new CubeDeformation(0.05F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));
		PartDefinition bone = body1.addOrReplaceChild("bone",
				CubeListBuilder.create().texOffs(0, 103).addBox(-5.0F, -3.0F, -5.0F, 10.0F, 8.0F, 10.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));
		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green,
							   float blue, float alpha) {
		poseStack.pushPose();
		poseStack.scale(1.8f, 1.8f, 1.8f);
		poseStack.translate(0F, -0.7F, 0F);
		main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		poseStack.popPose();
	}

	public void setupAnim(@NotNull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

		final float rareIdle = HekateLib.mod.get(entity, "rareIdle", 1F, 1F);
		final float s1 = 0.1F;
		HekateLib.render.tick(entity);
		HekateLib.render.prepare(main, body1, body2, body3, body4, body5, body6, body7, body8, body9, body10, head, headTop, headBottom, leftFin,
				rightFin);

		HekateLib.i(this.body1, -1F, -17.5F, 0F, 0F, 0F, 0F, s1, -0.95F, ageInTicks);
		HekateLib.i(this.body2, -1F, 20F, 0F, 0F, 0F, 0F, s1, -0.90F, ageInTicks);
		HekateLib.i(this.body3, -1F, 22.5F, 0F, 0F, 0F, 0F, s1, -0.85F, ageInTicks);
		HekateLib.i(this.body4, -1F, 20F, 0F, 0F, 0F, 0F, s1, -0.80F, ageInTicks);
		HekateLib.i(this.body5, -2F, -22.5F, 0F, 0F, 0F, 0F, s1, -0.75F, ageInTicks);
		HekateLib.i(this.body6, -2F, -22.5F, 0F, 0F, 0F, 0F, s1, -0.70F, ageInTicks);
		HekateLib.i(this.body7, -2F, -27.5F, 0F, 0F, 0F, 0F, s1, -0.65F, ageInTicks);
		HekateLib.i(this.body8, -2F, -25F, 0F, 0F, 0F, 0F, s1, -0.60F, ageInTicks);
		HekateLib.i(this.body9, -3F, -27.5F, 0F, 0F, 0F, 0F, s1, -0.55F, ageInTicks);
		HekateLib.i(this.body10, -3F, -22.5F, 0F, 0F, 0F, 0F, s1, -0.50F, ageInTicks);
		HekateLib.i(this.head, -3F, -22.5F, 0F, 0F, 0F, 0F, s1, 0F, ageInTicks);
		HekateLib.i(this.headTop, 0F, -40F, 0F, 0F, 0F, 0F, s1, 0F, ageInTicks);
		HekateLib.i(this.headBottom, -10F, -105F, 0F, 0F, 0F, 0F, s1, 0F, ageInTicks);
		HekateLib.i(this.leftFin, 0F, 0F, 0F, 0F, 10F, -45F, s1, 0F, ageInTicks);
		HekateLib.i(this.rightFin, 0F, 0F, 0F, 0F, -10F, 45F, s1, 0F, ageInTicks);

		this.body5.yRot = HekateLib.render.idle(1F, 0F, s1 * 1.4F, -0.95F, ageInTicks, rareIdle);
		this.body6.yRot = HekateLib.render.idle(2F, 0F, s1 * 1.4F, -0.90F, ageInTicks, rareIdle);
		this.body7.yRot = HekateLib.render.idle(3F, 0F, s1 * 1.4F, -0.85F, ageInTicks, rareIdle);
		this.body8.yRot = HekateLib.render.idle(4F, 0F, s1 * 1.4F, -0.80F, ageInTicks, rareIdle);
		this.body9.yRot = HekateLib.render.idle(5F, 0F, s1 * 1.4F, -0.75F, ageInTicks, rareIdle);
		this.body10.yRot = HekateLib.render.idle(6F, 0F, s1 * 1.4F, -0.70F, ageInTicks, rareIdle);
		this.head.yRot = HekateLib.render.idle(7F, 0F, s1 * 1.4F, -0.65F, ageInTicks, rareIdle);

		HekateLib.render.animation(entity, "attack", ageInTicks, new KeyFrame(20, 8, 6F, 24F, new AnimatedPart(this.body1, -17.5F, 0F, 0F),
				new AnimatedPart(this.body2, 20F, 0F, 0F), new AnimatedPart(this.body3, 22.5F, 0F, 0F), new AnimatedPart(this.body4, 27.5F, 0F, 0F),
				new AnimatedPart(this.body5, -2.5F, 0F, 0F), new AnimatedPart(this.body6, -2.5F, 0F, 0F), new AnimatedPart(this.body7, -30F, 0F, 0F),
				new AnimatedPart(this.body8, -30F, 0F, 0F), new AnimatedPart(this.body9, -35F, 0F, 0F), new AnimatedPart(this.body10, -25F, 0F, 0F),
				new AnimatedPart(this.head, -25F, 0F, 0F), new AnimatedPart(this.headTop, -40F, 0F, 0F),
				new AnimatedPart(this.headBottom, 10F, -170F, 0F, 0F, 0F, 0F, 1.4F, 0F), new AnimatedPart(this.leftFin, 0F, 0F, -90F),
				new AnimatedPart(this.rightFin, 0F, 0F, 90F)),
				new KeyFrame(8, 0, 24F, 2F, new AnimatedPart(this.body1, -17.5F, 0F, 0F), new AnimatedPart(this.body2, 10F, 0F, 0F),
						new AnimatedPart(this.body3, 15F, 0F, 0F), new AnimatedPart(this.body4, 12.5F, 0F, 0F),
						new AnimatedPart(this.body5, -35F, -1F, 0F), new AnimatedPart(this.body6, -30F, -1F, 0F),
						new AnimatedPart(this.body7, -35F, -2F, 0F), new AnimatedPart(this.body8, -25F, -2F, 0F),
						new AnimatedPart(this.body9, -12.5F, -3F, 0F), new AnimatedPart(this.body10, -2.5F, -3F, 0F),
						new AnimatedPart(this.head, -20F, -4F, 0F), new AnimatedPart(this.headTop, -40F, 0F, 0F),
						new AnimatedPart(this.headBottom, -75F, 0F, 0F), new AnimatedPart(this.leftFin, 0F, 0F, -40F),
						new AnimatedPart(this.rightFin, 0F, 0F, 40F)));

		HekateLib.render.animation(entity, "roar", ageInTicks,
				new KeyFrame(52, 40, 4F, 14F, new AnimatedPart(this.body1, -17.5F, 0F, 0F), new AnimatedPart(this.body2, 20F, 0F, 0F),
						new AnimatedPart(this.body3, 22.5F, 0F, 0F), new AnimatedPart(this.body4, 27.5F, 0F, 0F),
						new AnimatedPart(this.body5, -2.5F, 0F, 0F), new AnimatedPart(this.body6, 0F, -2.5F, 0F, 0F, 5F, 0F, 0.4F, -0.95F),
						new AnimatedPart(this.body7, 0F, -30F, 0F, 0F, 5F, 0F, 0.4F, -0.90F),
						new AnimatedPart(this.body8, 0F, -30F, 0F, 0F, 5F, 0F, 0.4F, -0.85F),
						new AnimatedPart(this.body9, 0F, -35F, 0F, 0F, 5F, 0F, 0.4F, -0.80F),
						new AnimatedPart(this.body10, 0F, -25F, 0F, 0F, 5F, 0F, 0.4F, -0.75F),
						new AnimatedPart(this.head, 0F, -25F, 0F, 0F, 5F, 0F, 0.4F, -0.70F), new AnimatedPart(this.headTop, -40F, 0F, 0F),
						new AnimatedPart(this.headBottom, 5F, -90F, 0F, 0F, 0F, 0F, 1.4F, 0F), new AnimatedPart(this.leftFin, 0F, 0F, -90F),
						new AnimatedPart(this.rightFin, 0F, 0F, 90F)),
				new KeyFrame(40, 0, 14F, 2F, new AnimatedPart(this.body1, -17.5F, 0F, 0F), new AnimatedPart(this.body2, 12.5F, 0F, 0F),
						new AnimatedPart(this.body3, 25F, 0F, 0F), new AnimatedPart(this.body4, 22.5F, 0F, 0F),
						new AnimatedPart(this.body5, -30F, 0F, 0F), new AnimatedPart(this.body6, 0F, -32.5F, 0F, 0F, 4F, 0F, 0.4F, -0.95F),
						new AnimatedPart(this.body7, 0F, -27.5F, 0F, 0F, 4F, 0F, 0.4F, -0.90F),
						new AnimatedPart(this.body8, 0F, -27.5F, 0F, 0F, 4F, 0F, 0.4F, -0.85F),
						new AnimatedPart(this.body9, 0F, -22.5F, 0F, 0F, 4F, 0F, 0.4F, -0.80F),
						new AnimatedPart(this.body10, 0F, -7.5F, 0F, 0F, 4F, 0F, 0.4F, -0.75F),
						new AnimatedPart(this.head, 0F, -12.5F, 0F, 0F, 4F, 0F, 0.4F, -0.70F), new AnimatedPart(this.headTop, -40F, 0F, 0F),
						new AnimatedPart(this.headBottom, 10F, -145F, 0F, 0F, 0F, 0F, 1.5F, 0F), new AnimatedPart(this.leftFin, 0F, 0F, -80F),
						new AnimatedPart(this.rightFin, 0F, 0F, 80F)));

		HekateLib.render.animation(entity, "moveMain", ageInTicks, new KeyFrame(50, 20, 1.6F, 2F, new AnimatedPart(this.main, -20F, 0F, 0F)),
				new KeyFrame(20, 0, 2F, 4F, new AnimatedPart(this.main, 0F, 0F, 0F)));
		HekateLib.render.animation(entity, "move", ageInTicks,
				new KeyFrame(50, 20, 4F, 2F, new AnimatedPart(this.body1, -1F, 0F, 0F, 0F, 1F, 0F, 0.4F, -0.95F),
						new AnimatedPart(this.body2, -1F, 0F, 0F, 0F, 1F, 0F, 0.4F, -0.90F),
						new AnimatedPart(this.body3, -1F, 0F, 0F, 0F, 1F, 0F, 0.4F, -0.85F),
						new AnimatedPart(this.body4, -1F, 0F, 0F, 0F, 1F, 0F, 0.4F, -0.80F),
						new AnimatedPart(this.body5, -1F, 0F, 0F, 0F, 1F, 0F, 0.4F, -0.75F),
						new AnimatedPart(this.body6, -1F, 0F, 0F, 0F, 1F, 0F, 0.4F, -0.75F),
						new AnimatedPart(this.body7, -1F, 0F, 0F, 0F, 1F, 0F, 0.4F, -0.65F),
						new AnimatedPart(this.body8, -1F, 0F, 0F, 0F, 1F, 0F, 0.4F, -0.60F),
						new AnimatedPart(this.body9, -1F, 0F, 0F, 0F, 1F, 0F, 0.4F, -0.55F),
						new AnimatedPart(this.body10, -1F, 0F, 0F, 0F, 1F, 0F, 0.4F, -0.50F),
						new AnimatedPart(this.head, -1F, 0F, 0F, 0F, 1F, 0F, 0.4F, -0.50F), new AnimatedPart(this.headTop, -40F, 0F, 0F),
						new AnimatedPart(this.headBottom, 5F, -90F, 0F, 0F, 0F, 0F, 1.4F, 0F), new AnimatedPart(this.leftFin, 0F, 0F, -90F),
						new AnimatedPart(this.rightFin, 0F, 0F, 90F)),
				new KeyFrame(20, 0, 2F, 4F, new AnimatedPart(this.body1, -3F, 0F, 0F, 0F, 3F, 0F, 0.4F, -0.95F),
						new AnimatedPart(this.body2, -3F, 0F, 0F, 0F, 3F, 0F, 0.4F, -0.90F),
						new AnimatedPart(this.body3, -3F, 0F, 0F, 0F, 3F, 0F, 0.4F, -0.85F),
						new AnimatedPart(this.body4, -3F, 0F, 0F, 0F, 3F, 0F, 0.4F, -0.80F),
						new AnimatedPart(this.body5, -3F, 0F, 0F, 0F, 3F, 0F, 0.4F, -0.75F),
						new AnimatedPart(this.body6, -3F, 0F, 0F, 0F, 3F, 0F, 0.4F, -0.75F),
						new AnimatedPart(this.body7, -3F, 0F, 0F, 0F, 3F, 0F, 0.4F, -0.65F),
						new AnimatedPart(this.body8, -3F, 0F, 0F, 0F, 3F, 0F, 0.4F, -0.60F),
						new AnimatedPart(this.body9, -3F, 0F, 0F, 0F, 3F, 0F, 0.4F, -0.55F),
						new AnimatedPart(this.body10, -3F, 0F, 0F, 0F, 3F, 0F, 0.4F, -0.50F),
						new AnimatedPart(this.head, -3F, 0F, 0F, 0F, 3F, 0F, 0.4F, -0.50F), new AnimatedPart(this.headTop, -40F, 0F, 0F),
						new AnimatedPart(this.headBottom, 5F, -90F, 0F, 0F, 0F, 0F, 1.4F, 0F), new AnimatedPart(this.leftFin, 0F, 0F, -90F),
						new AnimatedPart(this.rightFin, 0F, 0F, 90F)));
		this.body1.yRot += HekateLib.render.head(netHeadYaw, 0.1F);
		this.body2.yRot += HekateLib.render.head(netHeadYaw, 0.1F);
		this.body3.yRot += HekateLib.render.head(netHeadYaw, 0.1F);
		this.body4.yRot += HekateLib.render.head(netHeadYaw, 0.1F);
		this.body5.yRot += HekateLib.render.head(netHeadYaw, 0.1F);
		this.body6.yRot += HekateLib.render.head(netHeadYaw, 0.1F);
		this.body7.yRot += HekateLib.render.head(netHeadYaw, 0.1F);
		this.body8.yRot += HekateLib.render.head(netHeadYaw, 0.1F);
		this.body9.yRot += HekateLib.render.head(netHeadYaw, 0.1F);
		this.body10.yRot += HekateLib.render.head(netHeadYaw, 0.1F);
	}
}
