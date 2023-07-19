package com.obscuria.aquamirae.client.models.armor;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.client.AquamiraeLayers;
import com.obscuria.aquamirae.common.items.armor.AbyssalArmorItem;
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

public class ModelAbyssalArmor<T extends Entity> extends EntityModel<T> {
	public final ModelPart tiara, helmet, body, leftArm, rightArm, leftLeg, rightLeg, leftBoot, rightBoot;

	public ModelAbyssalArmor(ModelPart root) {
		this.tiara = root.getChild("tiara");
		this.helmet = root.getChild("helmet");
		this.body = root.getChild("body");
		this.leftArm = root.getChild("left_arm");
		this.rightArm = root.getChild("right_arm");
		this.leftLeg = root.getChild("left_leg");
		this.rightLeg = root.getChild("right_leg");
		this.leftBoot = root.getChild("left_boot");
		this.rightBoot = root.getChild("right_boot");
	}

	public static TexturedModelData createModelData() {
		final ModelData modelData = new ModelData();
		final ModelPartData root = modelData.getRoot();
		root.addChild("tiara", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -9.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.55F)), ModelTransform.pivot(0.0F, 1.0F, 0.0F));
		root.addChild("helmet", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 9.0F, 8.0F, new Dilation(1.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		root.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, 0.0F, -2.0F, 8.0F, 13.0F, 4.0F, new Dilation(0.75F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		root.addChild("left_arm", ModelPartBuilder.create().uv(24, 10).cuboid(-1.0F, -8.0F, 0.0F, 10.0F, 10.0F, 0.0F, new Dilation(0.001F)).uv(16, 17).cuboid(-1.0F, -3.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.8F)), ModelTransform.pivot(5.0F, 2.0F, 0.0F));
		root.addChild("right_arm", ModelPartBuilder.create().uv(24, 0).cuboid(-9.0F, -8.0F, 0.0F, 10.0F, 10.0F, 0.0F, new Dilation(0.001F)).uv(0, 17).cuboid(-3.0F, -3.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.8F)), ModelTransform.pivot(-5.0F, 2.0F, 0.0F));
		root.addChild("left_leg", ModelPartBuilder.create().uv(16, 0).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.34F)), ModelTransform.pivot(2.0F, 12.0F, 0.0F));
		root.addChild("right_leg", ModelPartBuilder.create().uv(0, 0).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.35F)), ModelTransform.pivot(-2.0F, 12.0F, 0.0F));
		root.addChild("left_boot", ModelPartBuilder.create().uv(16, 0).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.74F)).uv(8, 16).cuboid(2.5F, 0.1F, 0.0F, 4.0F, 10.0F, 0.0F, new Dilation(0.001F)), ModelTransform.pivot(2.0F, 12.0F, 0.0F));
		root.addChild("right_boot", ModelPartBuilder.create().uv(0, 0).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.75F)).uv(0, 16).cuboid(-6.5F, 0.1F, 0.0F, 4.0F, 10.0F, 0.0F, new Dilation(0.001F)), ModelTransform.pivot(-2.0F, 12.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}

	public static BipedEntityModel<LivingEntity> createArmorModel(ItemStack stack) {
		final ModelAbyssalArmor<?> raw = new ModelAbyssalArmor<>(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(AquamiraeLayers.ABYSSAL_ARMOR));
		final ModelPart blank = new ModelPart(Collections.emptyList(), Collections.emptyMap());
		if (stack.getItem() instanceof AbyssalArmorItem.Heaume) return new BipedEntityModel<>(new ModelPart(Collections.emptyList(), Map.of("head", raw.helmet, "hat", blank, "body", blank, "right_arm", blank, "left_arm", blank, "right_leg", blank, "left_leg", blank)));
		if (stack.getItem() instanceof AbyssalArmorItem.Tiara) return new BipedEntityModel<>(new ModelPart(Collections.emptyList(), Map.of("head", raw.tiara, "hat", blank, "body", blank, "right_arm", blank, "left_arm", blank, "right_leg", blank, "left_leg", blank)));
		if (stack.getItem() instanceof AbyssalArmorItem.Brigantine) return new BipedEntityModel<>(new ModelPart(Collections.emptyList(), Map.of("head", blank, "hat", blank, "body", raw.body, "right_arm", raw.rightArm, "left_arm", raw.leftArm, "right_leg", blank, "left_leg", blank)));
		if (stack.getItem() instanceof AbyssalArmorItem.Leggings) return new BipedEntityModel<>(new ModelPart(Collections.emptyList(), Map.of("head", blank, "hat", blank, "body", blank, "right_arm", blank, "left_arm", blank, "right_leg", raw.rightLeg, "left_leg", raw.leftLeg)));
		return new BipedEntityModel<>(new ModelPart(Collections.emptyList(), Map.of("head", blank, "hat", blank, "body", blank, "right_arm", blank, "left_arm", blank, "right_leg", raw.rightBoot, "left_leg", raw.leftBoot)));
	}

	public static Identifier getTexture(ItemStack stack) {
		if (stack.getItem() instanceof AbyssalArmorItem.Heaume) return Aquamirae.key("textures/entity/armor/abyssal_heaume.png");
		if (stack.getItem() instanceof AbyssalArmorItem.Tiara) return Aquamirae.key("textures/entity/armor/abyssal_tiara.png");
		if (stack.getItem() instanceof AbyssalArmorItem.Brigantine) return Aquamirae.key("textures/entity/armor/abyssal_brigantine.png");
		if (stack.getItem() instanceof AbyssalArmorItem.Leggings) return Aquamirae.key("textures/entity/armor/abyssal_leggings.png");
		return Aquamirae.key("textures/entity/armor/abyssal_boots.png");
	}

	@Override
	public void render(MatrixStack stack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		tiara.render(stack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		helmet.render(stack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		body.render(stack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		leftArm.render(stack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		rightArm.render(stack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		leftLeg.render(stack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		rightLeg.render(stack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		leftBoot.render(stack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		rightBoot.render(stack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
	
	@Override
	public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {}
}
