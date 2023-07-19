package com.obscuria.aquamirae.client.models.armor;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.client.AquamiraeLayers;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.util.Collections;
import java.util.Map;

public class ModelThreeBoltArmor<T extends Entity> extends EntityModel<T> {
	public final ModelPart head, body, leftArm, rightArm, bodyLower, leftLeg, rightLeg, leftBoot, rightBoot;

	public ModelThreeBoltArmor(ModelPart root) {
		this.head = root.getChild("head");
		this.body = root.getChild("body");
		this.leftArm = root.getChild("left_arm");
		this.rightArm = root.getChild("right_arm");
		this.bodyLower = root.getChild("body_lower");
		this.leftLeg = root.getChild("left_leg");
		this.rightLeg = root.getChild("right_leg");
		this.leftBoot = root.getChild("left_boot");
		this.rightBoot = root.getChild("right_boot");
	}

	public static TexturedModelData createModelData() {
		final ModelData modelData = new ModelData();
		final ModelPartData root = modelData.getRoot();
		final ModelPartData head = root.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(1.0F)).uv(0, 0).cuboid(-4.0F, -9.75F, -4.0F, 8.0F, 1.0F, 8.0F, new Dilation(0.0F)).uv(18, 16).cuboid(-1.5F, -10.25F, -1.5F, 3.0F, 1.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		head.addChild("cube_r1", ModelPartBuilder.create().uv(18, 16).cuboid(-1.5F, 0.75F, 5.5F, 3.0F, 1.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -9.5F, 7.0F, -1.5708F, 0.0F, 0.0F));
		head.addChild("cube_r2", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -5.75F, 1.5F, 8.0F, 1.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -9.5F, 0.0F, -1.5708F, 0.0F, 0.0F));
		head.addChild("bone3", ModelPartBuilder.create().uv(0, 18).cuboid(-3.5F, -3.5F, -1.3F, 7.0F, 7.0F, 1.0F, new Dilation(0.0F)).uv(0, 0).cuboid(3.0F, -3.0F, -0.3F, 1.0F, 6.0F, 1.0F, new Dilation(0.0F)).uv(0, 0).cuboid(-4.0F, -3.0F, -0.3F, 1.0F, 6.0F, 1.0F, new Dilation(0.0F)).uv(0, 16).cuboid(-4.0F, 3.0F, -0.3F, 8.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 16).cuboid(-4.0F, -4.0F, -0.3F, 8.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-5.25F, -4.0F, 0.05F, 0.0F, 1.5708F, 0.0F));
		head.addChild("bone2", ModelPartBuilder.create().uv(0, 18).cuboid(-3.5F, -3.5F, -1.3F, 7.0F, 7.0F, 1.0F, new Dilation(0.0F)).uv(0, 0).cuboid(3.0F, -3.0F, -0.3F, 1.0F, 6.0F, 1.0F, new Dilation(0.0F)).uv(0, 0).cuboid(-4.0F, -3.0F, -0.3F, 1.0F, 6.0F, 1.0F, new Dilation(0.0F)).uv(0, 16).cuboid(-4.0F, 3.0F, -0.3F, 8.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 16).cuboid(-4.0F, -4.0F, -0.3F, 8.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(5.25F, -4.0F, 0.05F, 0.0F, -1.5708F, 0.0F));
		head.addChild("bone", ModelPartBuilder.create().uv(0, 18).cuboid(-3.5F, -31.5F, -6.5F, 7.0F, 7.0F, 1.0F, new Dilation(0.0F)).uv(0, 0).cuboid(3.0F, -31.0F, -5.5F, 1.0F, 6.0F, 1.0F, new Dilation(0.0F)).uv(0, 0).cuboid(-4.0F, -31.0F, -5.5F, 1.0F, 6.0F, 1.0F, new Dilation(0.0F)).uv(0, 16).cuboid(-4.0F, -25.0F, -5.5F, 8.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(0, 16).cuboid(-4.0F, -32.0F, -5.5F, 8.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
		root.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new Dilation(0.75F)).uv(0, 32).cuboid(0.25F, -2.0F, 3.0F, 4.0F, 10.0F, 4.0F, new Dilation(0.0F)).uv(0, 32).cuboid(-4.25F, -2.0F, 3.0F, 4.0F, 10.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		root.addChild("left_arm", ModelPartBuilder.create().uv(16, 16).cuboid(-1.0F, -2.5F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.8F)).uv(0, 16).cuboid(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.25F)), ModelTransform.pivot(5.0F, 2.0F, 0.0F));
		root.addChild("right_arm", ModelPartBuilder.create().uv(0, 16).cuboid(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.25F)).uv(24, 0).cuboid(-3.0F, -2.5F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.8F)), ModelTransform.pivot(-5.0F, 2.0F, 0.0F));
		root.addChild("body_lower", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new Dilation(0.4F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		root.addChild("left_leg", ModelPartBuilder.create().uv(16, 16).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.34F)), ModelTransform.pivot(2.0F, 12.0F, 0.0F));
		root.addChild("right_leg", ModelPartBuilder.create().uv(0, 16).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.35F)), ModelTransform.pivot(-2.0F, 12.0F, 0.0F));
		root.addChild("left_boot", ModelPartBuilder.create().uv(16, 0).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.74F)), ModelTransform.pivot(2.0F, 12.0F, 0.0F));
		root.addChild("right_boot", ModelPartBuilder.create().uv(0, 0).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.75F)), ModelTransform.pivot(-2.0F, 12.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}

	public static BipedEntityModel<LivingEntity> createArmorModel(ItemStack stack) {
		final ModelThreeBoltArmor<?> raw = new ModelThreeBoltArmor<>(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(AquamiraeLayers.THREE_BOLT_ARMOR));
		final ModelPart blank = new ModelPart(Collections.emptyList(), Collections.emptyMap());
		if (stack.getItem() == AquamiraeItems.THREE_BOLT_HELMET) return new BipedEntityModel<>(new ModelPart(Collections.emptyList(), Map.of("head", raw.head, "hat", blank, "body", blank, "right_arm", blank, "left_arm", blank, "right_leg", blank, "left_leg", blank)));
		if (stack.getItem() == AquamiraeItems.THREE_BOLT_SUIT) return new BipedEntityModel<>(new ModelPart(Collections.emptyList(), Map.of("head", blank, "hat", blank, "body", raw.body, "right_arm", raw.rightArm, "left_arm", raw.leftArm, "right_leg", blank, "left_leg", blank)));
		if (stack.getItem() == AquamiraeItems.THREE_BOLT_LEGGINGS) return new BipedEntityModel<>(new ModelPart(Collections.emptyList(), Map.of("head", blank, "hat", blank, "body", raw.bodyLower, "right_arm", blank, "left_arm", blank, "right_leg", raw.rightLeg, "left_leg", raw.leftLeg)));
		return new BipedEntityModel<>(new ModelPart(Collections.emptyList(), Map.of("head", blank, "hat", blank, "body", blank, "right_arm", blank, "left_arm", blank, "right_leg", raw.rightBoot, "left_leg", raw.leftBoot)));
	}

	public static Identifier getTexture(ItemStack stack) {
		if (stack.getItem() == AquamiraeItems.THREE_BOLT_HELMET) return Aquamirae.key("textures/entity/armor/three_bolt_helmet.png");
		if (stack.getItem() == AquamiraeItems.THREE_BOLT_SUIT) return Aquamirae.key("textures/entity/armor/three_bolt_suit.png");
		if (stack.getItem() == AquamiraeItems.THREE_BOLT_LEGGINGS) return Aquamirae.key("textures/entity/armor/three_bolt_leggings.png");
		return Aquamirae.key("textures/entity/armor/three_bolt_boots.png");
	}

	@Override
	public void render(MatrixStack stack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		head.render(stack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		body.render(stack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		leftArm.render(stack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		rightArm.render(stack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		bodyLower.render(stack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		leftLeg.render(stack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		rightLeg.render(stack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		leftBoot.render(stack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		rightBoot.render(stack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {}
}
