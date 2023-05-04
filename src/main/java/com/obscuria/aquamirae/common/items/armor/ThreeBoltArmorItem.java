
package com.obscuria.aquamirae.common.items.armor;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.AquamiraeConfig;
import com.obscuria.aquamirae.client.AquamiraeLayers;
import com.obscuria.aquamirae.client.models.armor.ModelThreeBoltArmor;
import com.obscuria.aquamirae.registry.AquamiraeSounds;
import com.obscuria.aquamirae.common.items.AquamiraeTiers;
import com.obscuria.obscureapi.api.common.classes.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Map;
import java.util.function.Consumer;

@ClassItem(clazz = "aquamirae:sea_wolf", type = "armor")
public abstract class ThreeBoltArmorItem extends ArmorItem {

	public ThreeBoltArmorItem(EquipmentSlot slot, Item.@NotNull Properties properties) {
		super(AquamiraeTiers.THREE_BOLT_ARMOR, slot, properties.tab(Aquamirae.TAB));
	}

	public final Ability ABILITY_HALFSET = Ability.create(Aquamirae.MODID, "three_bolt_armor_half").build(ThreeBoltArmorItem.class);
	public final Ability ABILITY_FULLSET = Ability.create(Aquamirae.MODID, "three_bolt_armor_full").build(ThreeBoltArmorItem.class);
	public final Bonus BONUS = Bonus.create().target(Aquamirae.SEA_WOLF, "armor").type(Bonus.Type.POWER, Bonus.Operation.PERCENT).value(30).build();

	public static class Helmet extends ThreeBoltArmorItem {

		@ClassAbility
		public final Ability ABILITY_HALFSET = super.ABILITY_HALFSET;
		@ClassAbility public final Ability ABILITY_FULLSET = super.ABILITY_FULLSET;
		@ClassBonus
		public final Bonus BONUS = super.BONUS;

		public Helmet() {
			super(EquipmentSlot.HEAD, new Item.Properties());
		}

		public void initializeClient(@NotNull Consumer<IClientItemExtensions> consumer) {
			consumer.accept(new IClientItemExtensions() {
				@Override
				public @NotNull HumanoidModel<? extends LivingEntity> getHumanoidArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {
					HumanoidModel<? extends LivingEntity> armorModel = new HumanoidModel<>(new ModelPart(Collections.emptyList(), Map.of(
							"head", new ModelThreeBoltArmor<>(Minecraft.getInstance().getEntityModels().bakeLayer(AquamiraeLayers.THREE_BOLT_ARMOR)).head,
							"hat", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
							"body", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
							"right_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
							"left_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
							"right_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
							"left_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap()))));
					armorModel.crouching = living.isShiftKeyDown();
					armorModel.riding = defaultModel.riding;
					armorModel.young = living.isBaby();
					return armorModel;
				}

				@Override
				public void renderHelmetOverlay(ItemStack stack, Player player, int width, int height, float partialTick) {
					if (!AquamiraeConfig.Client.overlay.get()) return;
					RenderSystem.disableDepthTest();
					RenderSystem.depthMask(false);
					RenderSystem.defaultBlendFunc();
					RenderSystem.setShader(GameRenderer::getPositionTexShader);
					RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
					RenderSystem.setShaderTexture(0, new ResourceLocation(Aquamirae.MODID,"textures/screens/three_bolt_overlay.png"));
					Tesselator tesselator = Tesselator.getInstance();
					BufferBuilder bufferbuilder = tesselator.getBuilder();
					bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
					bufferbuilder.vertex(0.0D, height, -90.0D).uv(0.0F, 1.0F).endVertex();
					bufferbuilder.vertex(width, height, -90.0D).uv(1.0F, 1.0F).endVertex();
					bufferbuilder.vertex(width, 0.0D, -90.0D).uv(1.0F, 0.0F).endVertex();
					bufferbuilder.vertex(0.0D, 0.0D, -90.0D).uv(0.0F, 0.0F).endVertex();
					tesselator.end();
					RenderSystem.depthMask(true);
					RenderSystem.enableDepthTest();
					RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
				}
			});
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return "aquamirae:textures/entity/armor/three_bolt_helmet.png";
		}
	}

	public static class Chestplate extends ThreeBoltArmorItem {

		@ClassAbility public final Ability ABILITY_HALFSET = super.ABILITY_HALFSET;
		@ClassAbility public final Ability ABILITY_FULLSET = super.ABILITY_FULLSET;
		@ClassBonus public final Bonus BONUS = super.BONUS;

		public Chestplate() {
			super(EquipmentSlot.CHEST, new Item.Properties());
		}

		public void initializeClient(@NotNull Consumer<IClientItemExtensions> consumer) {
			consumer.accept(new IClientItemExtensions() {
				@Override
				@OnlyIn(Dist.CLIENT)
				public @NotNull HumanoidModel<? extends LivingEntity> getHumanoidArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {
					HumanoidModel<? extends LivingEntity> armorModel = new HumanoidModel<>(new ModelPart(Collections.emptyList(),
							Map.of("body", new ModelThreeBoltArmor<>(Minecraft.getInstance().getEntityModels().bakeLayer(AquamiraeLayers.THREE_BOLT_ARMOR)).body,
									"left_arm", new ModelThreeBoltArmor<>(Minecraft.getInstance().getEntityModels().bakeLayer(AquamiraeLayers.THREE_BOLT_ARMOR)).left_arm,
									"right_arm", new ModelThreeBoltArmor<>(Minecraft.getInstance().getEntityModels().bakeLayer(AquamiraeLayers.THREE_BOLT_ARMOR)).right_arm,
									"head", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
									"hat", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
									"right_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
									"left_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap()))));
					armorModel.crouching = living.isShiftKeyDown();
					armorModel.riding = defaultModel.riding;
					armorModel.young = living.isBaby();
					return armorModel;
				}
			});
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return "aquamirae:textures/entity/armor/three_bolt_chestplate.png";
		}

		@Override
		public void onArmorTick(ItemStack stack, Level world, @NotNull Player entity) {
			if (entity.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof ThreeBoltArmorItem) {
				if (entity.getAirSupply() <= 0) {
					entity.setAirSupply(280);
					if (stack.hurt(10, RandomSource.create(), null)) {
						stack.shrink(1);
						stack.setDamageValue(0);
					}
					if (!entity.getLevel().isClientSide()) entity.getLevel().playSound(null, new BlockPos(entity.getX(),
							entity.getY(), entity.getZ()), AquamiraeSounds.EFFECT_OXYGEN.get(), SoundSource.PLAYERS, 1, 1);
				}
			}
		}
	}

	public static class Leggings extends ThreeBoltArmorItem {

		@ClassAbility public final Ability ABILITY_HALFSET = super.ABILITY_HALFSET;
		@ClassAbility public final Ability ABILITY_FULLSET = super.ABILITY_FULLSET;
		@ClassBonus public final Bonus BONUS = super.BONUS;

		public Leggings() {
			super(EquipmentSlot.LEGS, new Item.Properties());
		}

		public void initializeClient(@NotNull Consumer<IClientItemExtensions> consumer) {
			consumer.accept(new IClientItemExtensions() {
				@Override
				@OnlyIn(Dist.CLIENT)
				public @NotNull HumanoidModel<? extends LivingEntity> getHumanoidArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {
					HumanoidModel<? extends LivingEntity> armorModel = new HumanoidModel<>(new ModelPart(Collections.emptyList(),
							Map.of("body", new ModelThreeBoltArmor<>(Minecraft.getInstance().getEntityModels().bakeLayer(AquamiraeLayers.THREE_BOLT_ARMOR)).leggings_body,
									"left_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
									"right_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
									"head", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
									"hat", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
									"right_leg", new ModelThreeBoltArmor<>(Minecraft.getInstance().getEntityModels().bakeLayer(AquamiraeLayers.THREE_BOLT_ARMOR)).right_leg,
									"left_leg", new ModelThreeBoltArmor<>(Minecraft.getInstance().getEntityModels().bakeLayer(AquamiraeLayers.THREE_BOLT_ARMOR)).left_leg)));
					armorModel.crouching = living.isShiftKeyDown();
					armorModel.riding = defaultModel.riding;
					armorModel.young = living.isBaby();
					return armorModel;
				}
			});
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return "aquamirae:textures/entity/armor/three_bolt_leggings.png";
		}
	}

	public static class Boots extends ThreeBoltArmorItem {

		@ClassAbility public final Ability ABILITY_HALFSET = super.ABILITY_HALFSET;
		@ClassAbility public final Ability ABILITY_FULLSET = super.ABILITY_FULLSET;
		@ClassBonus public final Bonus BONUS = super.BONUS;

		public Boots() {
			super(EquipmentSlot.FEET, new Item.Properties());
		}

		public void initializeClient(@NotNull Consumer<IClientItemExtensions> consumer) {
			consumer.accept(new IClientItemExtensions() {
				@Override
				@OnlyIn(Dist.CLIENT)
				public @NotNull HumanoidModel<? extends LivingEntity> getHumanoidArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {
					HumanoidModel<? extends LivingEntity> armorModel = new HumanoidModel<>(new ModelPart(Collections.emptyList(),
							Map.of("body", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
									"left_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
									"right_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
									"head", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
									"hat", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
									"right_leg", new ModelThreeBoltArmor<>(Minecraft.getInstance().getEntityModels().bakeLayer(AquamiraeLayers.THREE_BOLT_ARMOR)).right_boot,
									"left_leg", new ModelThreeBoltArmor<>(Minecraft.getInstance().getEntityModels().bakeLayer(AquamiraeLayers.THREE_BOLT_ARMOR)).left_boot)));
					armorModel.crouching = living.isShiftKeyDown();
					armorModel.riding = defaultModel.riding;
					armorModel.young = living.isBaby();
					return armorModel;
				}
			});
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return "aquamirae:textures/entity/armor/three_bolt_boots.png";
		}
	}
}
