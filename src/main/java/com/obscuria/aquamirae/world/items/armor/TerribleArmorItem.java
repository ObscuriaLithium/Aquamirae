
package com.obscuria.aquamirae.world.items.armor;

import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.client.models.armor.ModelTerribleArmor;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import com.obscuria.obscureapi.ObscureAPI;
import com.obscuria.obscureapi.world.classes.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.IItemRenderProperties;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Mod.EventBusSubscriber
public abstract class TerribleArmorItem extends ArmorItem implements IClassItem, IAbilityItem, IBonusItem {
	public final EquipmentSlot BONUS_SLOT;

	public TerribleArmorItem(EquipmentSlot slot, Item.Properties properties) {
		super(new ArmorMaterial() {
			@Override
			public int getDurabilityForSlot(@NotNull EquipmentSlot slot) {
				return new int[]{13, 15, 16, 11}[slot.getIndex()] * 25;
			}

			@Override
			public int getDefenseForSlot(@NotNull EquipmentSlot slot) {
				return new int[]{2, 3, 7, 5}[slot.getIndex()];
			}

			@Override
			public int getEnchantmentValue() {
				return 12;
			}

			@Override
			public @NotNull SoundEvent getEquipSound() {
				return SoundEvents.ARMOR_EQUIP_IRON;
			}

			@Override
			public @NotNull Ingredient getRepairIngredient() {
				return Ingredient.of(new ItemStack(AquamiraeItems.SHIP_GRAVEYARD_ECHO.get()),
						new ItemStack(AquamiraeItems.ANGLERS_FANG.get()));
			}

			@Override
			public @NotNull String getName() {
				return "terrible";
			}

			@Override
			public float getToughness() {
				return 0f;
			}

			@Override
			public float getKnockbackResistance() {
				return 0f;
			}
		}, slot, properties.tab(AquamiraeMod.TAB));
		this.BONUS_SLOT = slot;
	}

	public final ObscureAbility ABILITY_HALFSET = new ObscureAbility(this, "terrible_armor_half", ObscureAbility.Cost.COOLDOWN, 10, 120, 6);
	public final ObscureAbility ABILITY_FULLSET = new ObscureAbility(this, "terrible_armor_full", ObscureAbility.Cost.NONE, 0, 4);
	public final ObscureBonus BONUS_HEAD = new ObscureBonus(AquamiraeMod.SEA_WOLF, ObscureAPI.Types.WEAPON, ObscureBonus.Type.POWER, ObscureBonus.Operation.PERCENT, 20);
	public final ObscureBonus BONUS_CHEST = new ObscureBonus(AquamiraeMod.SEA_WOLF, ObscureAPI.Types.WEAPON, ObscureBonus.Type.POWER, ObscureBonus.Operation.PERCENT, 10);
	public final ObscureBonus BONUS_LEGS = new ObscureBonus(AquamiraeMod.SEA_WOLF, ObscureAPI.Types.WEAPON, ObscureBonus.Type.COOLDOWN, ObscureBonus.Operation.PERCENT, -10);
	public final ObscureBonus BONUS_FEET = new ObscureBonus(AquamiraeMod.SEA_WOLF, ObscureAPI.Types.WEAPON, ObscureBonus.Type.COOLDOWN, ObscureBonus.Operation.PERCENT, -20);

	public List<ObscureAbility> getObscureAbilities() {
		return Arrays.asList(ABILITY_FULLSET, ABILITY_HALFSET);
	}

	public List<ObscureBonus> getObscureBonuses() {
		if (this.BONUS_SLOT == EquipmentSlot.HEAD) return Collections.singletonList(BONUS_HEAD);
		if (this.BONUS_SLOT == EquipmentSlot.CHEST) return Collections.singletonList(BONUS_CHEST);
		if (this.BONUS_SLOT == EquipmentSlot.LEGS) return Collections.singletonList(BONUS_LEGS);
		return Collections.singletonList(BONUS_FEET);
	}

	public ObscureClass getObscureClass() {
		return AquamiraeMod.SEA_WOLF;
	}

	public ObscureType getObscureType() {
		return ObscureAPI.Types.ARMOR;
	}

	public static class Helmet extends TerribleArmorItem {
		public Helmet() {
			super(EquipmentSlot.HEAD, new Item.Properties());
		}

		public void initializeClient(Consumer<IItemRenderProperties> consumer) {
			consumer.accept(new IItemRenderProperties() {
				@Override
				public @NotNull HumanoidModel<? extends LivingEntity> getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {
					HumanoidModel<? extends LivingEntity> armorModel = new HumanoidModel<>(new ModelPart(Collections.emptyList(), Map.of(
							"head", new ModelTerribleArmor<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModelTerribleArmor.LAYER_LOCATION)).head,
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
			});
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return "aquamirae:textures/entity/armor/terrible_helmet.png";
		}
	}

	public static class Chestplate extends TerribleArmorItem {
		public Chestplate() {
			super(EquipmentSlot.CHEST, new Item.Properties());
		}

		public void initializeClient(Consumer<IItemRenderProperties> consumer) {
			consumer.accept(new IItemRenderProperties() {
				@Override
				@OnlyIn(Dist.CLIENT)
				public @NotNull HumanoidModel<? extends LivingEntity> getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {
					HumanoidModel<? extends LivingEntity> armorModel = new HumanoidModel<>(new ModelPart(Collections.emptyList(), Map.of(
							"body", new ModelTerribleArmor<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModelTerribleArmor.LAYER_LOCATION)).body,
							"left_arm", new ModelTerribleArmor<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModelTerribleArmor.LAYER_LOCATION)).left_arm,
							"right_arm", new ModelTerribleArmor<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModelTerribleArmor.LAYER_LOCATION)).right_arm,
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
			return "aquamirae:textures/entity/armor/terrible_chestplate.png";
		}
	}

	public static class Leggings extends TerribleArmorItem {
		public Leggings() {
			super(EquipmentSlot.LEGS, new Item.Properties());
		}

		public void initializeClient(Consumer<IItemRenderProperties> consumer) {
			consumer.accept(new IItemRenderProperties() {
				@Override
				@OnlyIn(Dist.CLIENT)
				public @NotNull HumanoidModel<? extends LivingEntity> getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {
					HumanoidModel<? extends LivingEntity> armorModel = new HumanoidModel<>(new ModelPart(Collections.emptyList(),
							Map.of("left_leg", new ModelTerribleArmor<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModelTerribleArmor.LAYER_LOCATION)).left_shoe2,
									"right_leg", new ModelTerribleArmor<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModelTerribleArmor.LAYER_LOCATION)).right_shoe2,
									"head", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
									"hat", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
									"body", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
									"right_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
									"left_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()))));
					armorModel.crouching = living.isShiftKeyDown();
					armorModel.riding = defaultModel.riding;
					armorModel.young = living.isBaby();
					return armorModel;
				}
			});
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return "aquamirae:textures/entity/armor/terrible_leggings.png";
		}
	}

	public static class Boots extends TerribleArmorItem {
		public Boots() {
			super(EquipmentSlot.FEET, new Item.Properties());
		}

		public void initializeClient(Consumer<IItemRenderProperties> consumer) {
			consumer.accept(new IItemRenderProperties() {
				@Override
				@OnlyIn(Dist.CLIENT)
				public @NotNull HumanoidModel<? extends LivingEntity> getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {
					HumanoidModel<? extends LivingEntity> armorModel = new HumanoidModel<>(new ModelPart(Collections.emptyList(),
							Map.of("left_leg", new ModelTerribleArmor<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModelTerribleArmor.LAYER_LOCATION)).left_shoe,
									"right_leg", new ModelTerribleArmor<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModelTerribleArmor.LAYER_LOCATION)).right_shoe,
									"head", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
									"hat", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
									"body", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
									"right_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
									"left_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()))));
					armorModel.crouching = living.isShiftKeyDown();
					armorModel.riding = defaultModel.riding;
					armorModel.young = living.isBaby();
					return armorModel;
				}
			});
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
			return "aquamirae:textures/entity/armor/terrible_boots.png";
		}
	}
}
