
package com.obscuria.aquamirae.world.items.armor;

import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.client.models.armor.ModelAbyssalArmor;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import com.obscuria.aquamirae.registry.AquamiraeMobEffects;
import com.obscuria.obscureapi.ObscureAPI;
import com.obscuria.obscureapi.world.classes.*;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class AbyssalArmorItem extends ArmorItem implements IClassItem, IAbilityItem, IBonusItem {
	public final EquipmentSlotType BONUS_SLOT;

	public AbyssalArmorItem(EquipmentSlotType slot, Item.Properties properties) {

		super(new IArmorMaterial() {
			@Override
			public int getDurabilityForSlot(@Nonnull EquipmentSlotType slot) {
				return new int[]{13, 15, 16, 11}[slot.getIndex()] * 40;
			}

			@Override
			public int getDefenseForSlot(@Nonnull EquipmentSlotType slot) {
				return new int[]{2, 4, 4, 6}[slot.getIndex()];
			}

			@Override
			public int getEnchantmentValue() {
				return 12;
			}

			@Override
			@Nonnull
			public SoundEvent getEquipSound() {
				return SoundEvents.ARMOR_EQUIP_NETHERITE;
			}

			@Override
			@Nonnull
			public Ingredient getRepairIngredient() {
				return Ingredient.of(new ItemStack(AquamiraeItems.SHIP_GRAVEYARD_ECHO.get()),
						new ItemStack(AquamiraeItems.ABYSSAL_AMETHYST.get()));
			}

			@Override
			@Nonnull
			public String getName() {
				return "abyssal";
			}

			@Override
			public float getToughness() {
				return 3f;
			}

			@Override
			public float getKnockbackResistance() {
				return 0.1f;
			}
		}, slot, properties.rarity(Rarity.EPIC).tab(AquamiraeMod.TAB));
		this.BONUS_SLOT = slot;
	}

	public final ObscureAbility ABILITY_HALFSET = new ObscureAbility(this, "abyssal_armor_half", ObscureAbility.Cost.NONE, 0);
	public final ObscureAbility ABILITY_FULLSET_1 = new ObscureAbility(this, "abyssal_armor_full_1", ObscureAbility.Cost.NONE, 0, 90);
	public final ObscureAbility ABILITY_FULLSET_2 = new ObscureAbility(this, "abyssal_armor_full_2", ObscureAbility.Cost.NONE, 0);
	public final ObscureBonus BONUS_HEAD = new ObscureBonus(AquamiraeMod.SEA_WOLF, ObscureAPI.Types.WEAPON, ObscureBonus.Type.POWER, ObscureBonus.Operation.AMOUNT, 3);
	public final ObscureBonus BONUS_CHEST = new ObscureBonus(AquamiraeMod.SEA_WOLF, ObscureAPI.Types.WEAPON, ObscureBonus.Type.POWER, ObscureBonus.Operation.PERCENT, 25);
	public final ObscureBonus BONUS_LEGS = new ObscureBonus(AquamiraeMod.SEA_WOLF, ObscureAPI.Types.WEAPON, ObscureBonus.Type.POWER, ObscureBonus.Operation.PERCENT, 25);
	public final ObscureBonus BONUS_FEET = new ObscureBonus(AquamiraeMod.SEA_WOLF, ObscureAPI.Types.WEAPON, ObscureBonus.Type.POWER, ObscureBonus.Operation.AMOUNT, 3);

	public List<ObscureAbility> getObscureAbilities() {
		return Arrays.asList(ABILITY_FULLSET_2, ABILITY_FULLSET_1, ABILITY_HALFSET);
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

	@Override
	public void onArmorTick(ItemStack itemstack, World world, PlayerEntity player) {
		final boolean HEAD = player.getItemBySlot(EquipmentSlotType.HEAD).getItem() instanceof AbyssalArmorItem
				|| player.getItemBySlot(EquipmentSlotType.HEAD).getItem() instanceof AbyssalTiaraItem;
		final boolean CHEST = player.getItemBySlot(EquipmentSlotType.CHEST).getItem() instanceof AbyssalArmorItem;
		final boolean LEGS = player.getItemBySlot(EquipmentSlotType.LEGS).getItem() instanceof AbyssalArmorItem;
		final boolean FEET = player.getItemBySlot(EquipmentSlotType.FEET).getItem() instanceof AbyssalArmorItem;
		final int TOTAL = (HEAD ? 1 : 0) + (CHEST ? 1 : 0) + (LEGS ? 1 : 0) + (FEET ? 1 : 0);
		if (TOTAL >= 2) {
			final EffectInstance EFFECT = player.getEffect(AquamiraeMobEffects.STRONG_ARMOR.get());
			if (EFFECT != null) {
				EFFECT.update(new EffectInstance(AquamiraeMobEffects.STRONG_ARMOR.get(), 4, 0, false, false));
			} else {
				player.addEffect(new EffectInstance(AquamiraeMobEffects.STRONG_ARMOR.get(), 4, 0, false, false));
			}
		}
	}

	public static class Helmet extends AbyssalArmorItem {
		public Helmet() {
			super(EquipmentSlotType.HEAD, new Item.Properties());
		}

		@Override
		public <A extends BipedModel<?>> A getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlotType slot, A defaultModel) {
			final BipedModel<?> model = new BipedModel<>(1F);
			model.head = new ModelAbyssalArmor<>().helmet;
			model.crouching = living.isCrouching();
			model.young = living.isBaby();
			model.riding = defaultModel.riding;
			return (A) model;
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
			return "aquamirae:textures/entity/armor/abyssal_heaume.png";
		}
	}

	public static class Chestplate extends AbyssalArmorItem {
		public Chestplate() {
			super(EquipmentSlotType.CHEST, new Item.Properties());
		}

		@Override
		public <A extends BipedModel<?>> A getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlotType slot, A defaultModel) {
			final BipedModel<?> model = new BipedModel<>(1F);
			model.body = new ModelAbyssalArmor<>().body;
			model.leftArm = new ModelAbyssalArmor<>().leftArm;
			model.rightArm = new ModelAbyssalArmor<>().rightArm;
			model.crouching = living.isCrouching();
			model.young = living.isBaby();
			model.riding = defaultModel.riding;
			return (A) model;
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
			return "aquamirae:textures/entity/armor/abyssal_brigantine.png";
		}
	}

	public static class Leggings extends AbyssalArmorItem {
		public Leggings() {
			super(EquipmentSlotType.LEGS, new Item.Properties());
		}

		@Override
		public <A extends BipedModel<?>> A getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlotType slot, A defaultModel) {
			final BipedModel<?> model = new BipedModel<>(1F);
			model.leftLeg = new ModelAbyssalArmor<>().leftLeg;
			model.rightLeg = new ModelAbyssalArmor<>().rightLeg;
			model.crouching = living.isCrouching();
			model.young = living.isBaby();
			model.riding = defaultModel.riding;
			return (A) model;
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
			return "aquamirae:textures/entity/armor/abyssal_leggings.png";
		}
	}

	public static class Boots extends AbyssalArmorItem {
		public Boots() {
			super(EquipmentSlotType.FEET, new Item.Properties());
		}

		@Override
		public <A extends BipedModel<?>> A getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlotType slot, A defaultModel) {
			final BipedModel<?> model = new BipedModel<>(1F);
			model.leftLeg = new ModelAbyssalArmor<>().leftBoot;
			model.rightLeg = new ModelAbyssalArmor<>().rightBoot;
			model.crouching = living.isCrouching();
			model.young = living.isBaby();
			model.riding = defaultModel.riding;
			return (A) model;
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
			return "aquamirae:textures/entity/armor/abyssal_boots.png";
		}
	}
}
