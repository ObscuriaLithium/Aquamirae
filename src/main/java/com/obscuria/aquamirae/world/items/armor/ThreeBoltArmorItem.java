
package com.obscuria.aquamirae.world.items.armor;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.obscuria.aquamirae.AquamiraeConfig;
import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.client.models.armor.ModelThreeBoltArmor;
import com.obscuria.aquamirae.registry.AquamiraeSounds;
import com.obscuria.obscureapi.ObscureAPI;
import com.obscuria.obscureapi.world.classes.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.item.ArmorItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.IItemRenderProperties;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Consumer;

public abstract class ThreeBoltArmorItem extends ArmorItem implements IClassItem, IAbilityItem, IBonusItem {
	public final EquipmentSlot BONUS_SLOT;

	public ThreeBoltArmorItem(EquipmentSlot slot, Item.Properties properties) {
		super(new ArmorMaterial() {
			@Override
			public int getDurabilityForSlot(@NotNull EquipmentSlot slot) {
				return new int[]{13, 15, 16, 11}[slot.getIndex()] * 75;
			}

			@Override
			public int getDefenseForSlot(@NotNull EquipmentSlot slot) {
				return new int[]{3, 5, 5, 7}[slot.getIndex()];
			}

			@Override
			public int getEnchantmentValue() {
				return 9;
			}

			@Override
			public @NotNull SoundEvent getEquipSound() {
				return SoundEvents.ARMOR_EQUIP_IRON;
			}

			@Override
			public @NotNull Ingredient getRepairIngredient() {
				return Ingredient.EMPTY;
			}

			@Override
			public @NotNull String getName() {
				return "three_bolt";
			}

			@Override
			public float getToughness() {
				return 2f;
			}

			@Override
			public float getKnockbackResistance() {
				return 0.1f;
			}
		}, slot, properties.tab(AquamiraeMod.TAB));
		this.BONUS_SLOT = slot;
	}

	public final ObscureAbility ABILITY_HALFSET = new ObscureAbility(this, "three_bolt_armor_half", ObscureAbility.Cost.NONE, 0);
	public final ObscureAbility ABILITY_FULLSET = new ObscureAbility(this, "three_bolt_armor_full", ObscureAbility.Cost.NONE, 0);
	public final ObscureBonus BONUS_TOP = new ObscureBonus(AquamiraeMod.SEA_WOLF, ObscureAPI.Types.ARMOR, ObscureBonus.Type.POWER, ObscureBonus.Operation.PERCENT, 30);
	public final ObscureBonus BONUS_BOTTOM = new ObscureBonus(AquamiraeMod.SEA_WOLF, ObscureAPI.Types.WEAPON, ObscureBonus.Type.POWER, ObscureBonus.Operation.PERCENT, 30);

	public List<ObscureAbility> getObscureAbilities() {
		return List.of(ABILITY_FULLSET, ABILITY_HALFSET);
	}

	public List<ObscureBonus> getObscureBonuses() {
		if (this.BONUS_SLOT == EquipmentSlot.HEAD || this.BONUS_SLOT == EquipmentSlot.CHEST) return Collections.singletonList(BONUS_TOP);
		return Collections.singletonList(BONUS_BOTTOM);
	}

	public ObscureClass getObscureClass() {
		return AquamiraeMod.SEA_WOLF;
	}

	public ObscureType getObscureType() {
		return ObscureAPI.Types.ARMOR;
	}

	public static class Helmet extends ThreeBoltArmorItem {
		public Helmet() {
			super(EquipmentSlot.HEAD, new Item.Properties());
		}

		public void initializeClient(Consumer<IItemRenderProperties> consumer) {
			consumer.accept(new IItemRenderProperties() {
				@Override
				public @NotNull HumanoidModel<? extends LivingEntity> getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {
					HumanoidModel<? extends LivingEntity> armorModel = new HumanoidModel<>(new ModelPart(Collections.emptyList(), Map.of(
							"head", new ModelThreeBoltArmor<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModelThreeBoltArmor.LAYER_LOCATION)).head,
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
					RenderSystem.setShaderTexture(0, new ResourceLocation(AquamiraeMod.MODID,"textures/screens/three_bolt_overlay.png"));
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
		public Chestplate() {
			super(EquipmentSlot.CHEST, new Item.Properties());
		}

		public void initializeClient(Consumer<IItemRenderProperties> consumer) {
			consumer.accept(new IItemRenderProperties() {
				@Override
				@OnlyIn(Dist.CLIENT)
				public @NotNull HumanoidModel<? extends LivingEntity> getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {
					HumanoidModel<? extends LivingEntity> armorModel = new HumanoidModel<>(new ModelPart(Collections.emptyList(),
							Map.of("body", new ModelThreeBoltArmor<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModelThreeBoltArmor.LAYER_LOCATION)).body,
									"left_arm", new ModelThreeBoltArmor<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModelThreeBoltArmor.LAYER_LOCATION)).left_arm,
									"right_arm", new ModelThreeBoltArmor<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModelThreeBoltArmor.LAYER_LOCATION)).right_arm,
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
		public void onArmorTick(ItemStack stack, Level world, Player entity) {
			if (entity.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof ThreeBoltArmorItem) {
				if (entity.getAirSupply() <= 0) {
					entity.setAirSupply(280);
					if (stack.hurt(10, new Random(), null)) {
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
		public Leggings() {
			super(EquipmentSlot.LEGS, new Item.Properties());
		}

		public void initializeClient(Consumer<IItemRenderProperties> consumer) {
			consumer.accept(new IItemRenderProperties() {
				@Override
				@OnlyIn(Dist.CLIENT)
				public @NotNull HumanoidModel<? extends LivingEntity> getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {
					HumanoidModel<? extends LivingEntity> armorModel = new HumanoidModel<>(new ModelPart(Collections.emptyList(),
							Map.of("body", new ModelThreeBoltArmor<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModelThreeBoltArmor.LAYER_LOCATION)).leggings_body,
									"left_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
									"right_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
									"head", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
									"hat", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
									"right_leg", new ModelThreeBoltArmor<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModelThreeBoltArmor.LAYER_LOCATION)).right_leg,
									"left_leg", new ModelThreeBoltArmor<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModelThreeBoltArmor.LAYER_LOCATION)).left_leg)));
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
		public Boots() {
			super(EquipmentSlot.FEET, new Item.Properties());
		}

		public void initializeClient(Consumer<IItemRenderProperties> consumer) {
			consumer.accept(new IItemRenderProperties() {
				@Override
				@OnlyIn(Dist.CLIENT)
				public @NotNull HumanoidModel<? extends LivingEntity> getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {
					HumanoidModel<? extends LivingEntity> armorModel = new HumanoidModel<>(new ModelPart(Collections.emptyList(),
							Map.of("body", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
									"left_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
									"right_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
									"head", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
									"hat", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
									"right_leg", new ModelThreeBoltArmor<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModelThreeBoltArmor.LAYER_LOCATION)).right_boot,
									"left_leg", new ModelThreeBoltArmor<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModelThreeBoltArmor.LAYER_LOCATION)).left_boot)));
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
