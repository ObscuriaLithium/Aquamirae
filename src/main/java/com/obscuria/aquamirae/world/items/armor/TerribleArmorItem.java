
package com.obscuria.aquamirae.world.items.armor;

import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.client.models.armor.ModelAbyssalArmor;
import com.obscuria.aquamirae.client.models.armor.ModelTerribleArmor;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import com.obscuria.obscureapi.ObscureAPI;
import com.obscuria.obscureapi.world.classes.*;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Mod.EventBusSubscriber
public abstract class TerribleArmorItem extends ArmorItem implements IClassItem, IAbilityItem, IBonusItem {
	public final EquipmentSlotType BONUS_SLOT;

	public TerribleArmorItem(EquipmentSlotType slot, Item.Properties properties) {
		super(new IArmorMaterial() {
			@Override
			public int getDurabilityForSlot(@Nonnull EquipmentSlotType slot) {
				return new int[]{13, 15, 16, 11}[slot.getIndex()] * 25;
			}

			@Override
			public int getDefenseForSlot(@Nonnull EquipmentSlotType slot) {
				return new int[]{2, 3, 7, 5}[slot.getIndex()];
			}

			@Override
			public int getEnchantmentValue() {
				return 12;
			}

			@Override
			@Nonnull
			public SoundEvent getEquipSound() {
				return SoundEvents.ARMOR_EQUIP_IRON;
			}

			@Override
			@Nonnull
			public Ingredient getRepairIngredient() {
				return Ingredient.of(new ItemStack(AquamiraeItems.SHIP_GRAVEYARD_ECHO.get()),
						new ItemStack(AquamiraeItems.ANGLERS_FANG.get()));
			}

			@Override
			@Nonnull
			public String getName() {
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
		if (this.BONUS_SLOT == EquipmentSlotType.HEAD) return Collections.singletonList(BONUS_HEAD);
		if (this.BONUS_SLOT == EquipmentSlotType.CHEST) return Collections.singletonList(BONUS_CHEST);
		if (this.BONUS_SLOT == EquipmentSlotType.LEGS) return Collections.singletonList(BONUS_LEGS);
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
			super(EquipmentSlotType.HEAD, new Item.Properties());
		}

		@Override
		public <A extends BipedModel<?>> A getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlotType slot, A defaultModel) {
			final BipedModel<?> model = new BipedModel<>(1F);
			model.head = new ModelTerribleArmor<>().head;
			model.crouching = living.isCrouching();
			model.young = living.isBaby();
			model.riding = defaultModel.riding;
			return (A) model;
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
			return "aquamirae:textures/entity/armor/terrible_helmet.png";
		}
	}

	public static class Chestplate extends TerribleArmorItem {
		public Chestplate() {
			super(EquipmentSlotType.CHEST, new Item.Properties());
		}

		@Override
		public <A extends BipedModel<?>> A getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlotType slot, A defaultModel) {
			final BipedModel<?> model = new BipedModel<>(1F);
			model.body = new ModelTerribleArmor<>().body;
			model.leftArm = new ModelTerribleArmor<>().leftArm;
			model.rightArm = new ModelTerribleArmor<>().rightArm;
			model.crouching = living.isCrouching();
			model.young = living.isBaby();
			model.riding = defaultModel.riding;
			return (A) model;
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
			return "aquamirae:textures/entity/armor/terrible_chestplate.png";
		}
	}

	public static class Leggings extends TerribleArmorItem {
		public Leggings() {
			super(EquipmentSlotType.LEGS, new Item.Properties());
		}

		@Override
		public <A extends BipedModel<?>> A getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlotType slot, A defaultModel) {
			final BipedModel<?> model = new BipedModel<>(1F);
			model.leftLeg = new ModelTerribleArmor<>().leftLeg;
			model.rightLeg = new ModelTerribleArmor<>().rightLeg;
			model.crouching = living.isCrouching();
			model.young = living.isBaby();
			model.riding = defaultModel.riding;
			return (A) model;
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
			return "aquamirae:textures/entity/armor/terrible_leggings.png";
		}
	}

	public static class Boots extends TerribleArmorItem {
		public Boots() {
			super(EquipmentSlotType.FEET, new Item.Properties());
		}

		@Override
		public <A extends BipedModel<?>> A getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlotType slot, A defaultModel) {
			final BipedModel<?> model = new BipedModel<>(1F);
			model.leftLeg = new ModelTerribleArmor<>().leftBoot;
			model.rightLeg = new ModelTerribleArmor<>().rightBoot;
			model.crouching = living.isCrouching();
			model.young = living.isBaby();
			model.riding = defaultModel.riding;
			return (A) model;
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
			return "aquamirae:textures/entity/armor/terrible_boots.png";
		}
	}
}
