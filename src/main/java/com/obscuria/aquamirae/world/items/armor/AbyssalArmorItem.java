
package com.obscuria.aquamirae.world.items.armor;

import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.client.models.armor.ModelAbyssalArmor;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import com.obscuria.aquamirae.registry.AquamiraeMobEffects;
import com.obscuria.obscureapi.ObscureAPI;
import com.obscuria.obscureapi.world.classes.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.IItemRenderProperties;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public abstract class AbyssalArmorItem extends ArmorItem implements IClassItem, IAbilityItem, IBonusItem {
	public final EquipmentSlot BONUS_SLOT;

	public AbyssalArmorItem(EquipmentSlot slot, Item.Properties properties) {
		super(new ArmorMaterial() {
			@Override
			public int getDurabilityForSlot(@NotNull EquipmentSlot slot) {
				return new int[]{13, 15, 16, 11}[slot.getIndex()] * 40;
			}

			@Override
			public int getDefenseForSlot(@NotNull EquipmentSlot slot) {
				return new int[]{2, 4, 4, 6}[slot.getIndex()];
			}

			@Override
			public int getEnchantmentValue() {
				return 12;
			}

			@Override
			public @NotNull SoundEvent getEquipSound() {
				return SoundEvents.ARMOR_EQUIP_NETHERITE;
			}

			@Override
			public @NotNull Ingredient getRepairIngredient() {
				return Ingredient.of(new ItemStack(AquamiraeItems.SHIP_GRAVEYARD_ECHO.get()),
						new ItemStack(AquamiraeItems.ABYSSAL_AMETHYST.get()));
			}

			@Override
			public @NotNull String getName() {
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

	@Override
	public void onArmorTick(ItemStack itemstack, Level world, Player player) {
		final boolean HEAD = player.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof AbyssalArmorItem
				|| player.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof AbyssalTiaraItem;
		final boolean CHEST = player.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof AbyssalArmorItem;
		final boolean LEGS = player.getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof AbyssalArmorItem;
		final boolean FEET = player.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof AbyssalArmorItem;
		final int TOTAL = (HEAD ? 1 : 0) + (CHEST ? 1 : 0) + (LEGS ? 1 : 0) + (FEET ? 1 : 0);
		if (TOTAL >= 2) {
			final MobEffectInstance EFFECT = player.getEffect(AquamiraeMobEffects.STRONG_ARMOR.get());
			if (EFFECT != null) {
				EFFECT.update(new MobEffectInstance(AquamiraeMobEffects.STRONG_ARMOR.get(), 4, 0, false, false));
			} else {
				player.addEffect(new MobEffectInstance(AquamiraeMobEffects.STRONG_ARMOR.get(), 4, 0, false, false));
			}
		}
	}

	public static class Helmet extends AbyssalArmorItem {
		public Helmet() {
			super(EquipmentSlot.HEAD, new Item.Properties());
		}

		public void initializeClient(Consumer<IItemRenderProperties> consumer) {
			consumer.accept(new IItemRenderProperties() {
				@Override
				public @NotNull HumanoidModel<? extends LivingEntity> getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {
					HumanoidModel<? extends LivingEntity> armorModel = new HumanoidModel<>(new ModelPart(Collections.emptyList(), Map.of(
							"head", new ModelAbyssalArmor<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModelAbyssalArmor.LAYER_LOCATION)).helmet,
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
			return "aquamirae:textures/entity/armor/abyssal_helmet.png";
		}
	}

	public static class Chestplate extends AbyssalArmorItem {
		public Chestplate() {
			super(EquipmentSlot.CHEST, new Item.Properties());
		}

		public void initializeClient(Consumer<IItemRenderProperties> consumer) {
			consumer.accept(new IItemRenderProperties() {
				@Override
				@OnlyIn(Dist.CLIENT)
				public @NotNull HumanoidModel<? extends LivingEntity> getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {
					HumanoidModel<? extends LivingEntity> armorModel = new HumanoidModel<>(new ModelPart(Collections.emptyList(), Map.of(
							"body", new ModelAbyssalArmor<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModelAbyssalArmor.LAYER_LOCATION)).body,
							"left_arm", new ModelAbyssalArmor<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModelAbyssalArmor.LAYER_LOCATION)).left_arm,
							"right_arm", new ModelAbyssalArmor<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModelAbyssalArmor.LAYER_LOCATION)).right_arm,
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
			return "aquamirae:textures/entity/armor/abyssal_brigantine.png";
		}
	}

	public static class Leggings extends AbyssalArmorItem {
		public Leggings() {
			super(EquipmentSlot.LEGS, new Item.Properties());
		}

		public void initializeClient(Consumer<IItemRenderProperties> consumer) {
			consumer.accept(new IItemRenderProperties() {
				@Override
				@OnlyIn(Dist.CLIENT)
				public @NotNull HumanoidModel<? extends LivingEntity> getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {
					HumanoidModel<? extends LivingEntity> armorModel = new HumanoidModel<>(new ModelPart(Collections.emptyList(), Map.of(
							"left_leg", new ModelAbyssalArmor<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModelAbyssalArmor.LAYER_LOCATION)).left_leg,
							"right_leg", new ModelAbyssalArmor<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModelAbyssalArmor.LAYER_LOCATION)).right_leg,
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
			return "aquamirae:textures/entity/armor/abyssal_leggings.png";
		}
	}

	public static class Boots extends AbyssalArmorItem {
		public Boots() {
			super(EquipmentSlot.FEET, new Item.Properties());
		}

		public void initializeClient(Consumer<IItemRenderProperties> consumer) {
			consumer.accept(new IItemRenderProperties() {
				@Override
				@OnlyIn(Dist.CLIENT)
				public @NotNull HumanoidModel<? extends LivingEntity> getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {
					HumanoidModel<? extends LivingEntity> armorModel = new HumanoidModel<>(new ModelPart(Collections.emptyList(), Map.of(
							"left_leg", new ModelAbyssalArmor<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModelAbyssalArmor.LAYER_LOCATION)).left_boot,
							"right_leg", new ModelAbyssalArmor<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModelAbyssalArmor.LAYER_LOCATION)).right_boot,
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
			return "aquamirae:textures/entity/armor/abyssal_boots.png";
		}
	}
}
