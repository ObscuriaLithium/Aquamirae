
package com.obscuria.aquamirae.common.items.armor;

import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.client.models.armor.ModelAbyssalArmor;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import com.obscuria.aquamirae.registry.AquamiraeMobEffects;
import com.obscuria.obscureapi.ObscureAPI;
import com.obscuria.obscureapi.world.classes.*;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbyssalTiaraItem extends ArmorItem implements IClassItem, IAbilityItem, IBonusItem {
	public AbyssalTiaraItem(EquipmentSlotType slot, Item.Properties properties) {
		super(new IArmorMaterial() {
			@Override
			public int getDurabilityForSlot(@Nonnull EquipmentSlotType slot) {
				return new int[]{13, 15, 16, 11}[slot.getIndex()] * 40;
			}

			@Override
			public int getDefenseForSlot(@Nonnull EquipmentSlotType slot) {
				return new int[]{3, 6, 8, 2}[slot.getIndex()];
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
				return "abyssal_extra";
			}

			@Override
			public float getToughness() {
				return 0f;
			}

			@Override
			public float getKnockbackResistance() {
				return 0f;
			}
		}, slot, properties.rarity(Rarity.EPIC).tab(AquamiraeMod.TAB));
	}

	public final ObscureAbility ABILITY_HALFSET = new ObscureAbility(this, "abyssal_armor_half", ObscureAbility.Cost.NONE, 0);
	public final ObscureAbility ABILITY_FULLSET_1 = new ObscureAbility(this, "abyssal_armor_full_1", ObscureAbility.Cost.NONE, 0, 30);
	public final ObscureAbility ABILITY_FULLSET_2 = new ObscureAbility(this, "abyssal_armor_full_2", ObscureAbility.Cost.NONE, 0);
	public final ObscureBonus BONUS = new ObscureBonus(AquamiraeMod.SEA_WOLF, ObscureAPI.Types.WEAPON, ObscureBonus.Type.POWER, ObscureBonus.Operation.AMOUNT, 3);

	public List<ObscureAbility> getObscureAbilities() {
		return Arrays.asList(ABILITY_FULLSET_2, ABILITY_FULLSET_1, ABILITY_HALFSET);
	}

	public List<ObscureBonus> getObscureBonuses() {
		return Collections.singletonList(BONUS);
	}

	public ObscureClass getObscureClass() {
		return AquamiraeMod.SEA_WOLF;
	}

	public ObscureType getObscureType() {
		return ObscureAPI.Types.ARMOR;
	}

	@Override
	public void onArmorTick(ItemStack itemstack, World world, PlayerEntity entity) {
		if (entity.getItemBySlot(EquipmentSlotType.CHEST).getItem() instanceof AbyssalArmorItem
				&& entity.getItemBySlot(EquipmentSlotType.LEGS).getItem() instanceof AbyssalArmorItem
				&& entity.getItemBySlot(EquipmentSlotType.FEET).getItem() instanceof AbyssalArmorItem) {
			final Vector3d center = new Vector3d(entity.getX(), entity.getY() + 1, entity.getZ());
			List<MonsterEntity> list = world.getEntitiesOfClass(MonsterEntity.class, new AxisAlignedBB(center, center).inflate(4), e -> true).stream()
					.sorted(Comparator.comparingDouble(entities -> entities.distanceToSqr(center))).collect(Collectors.toList());
			for (MonsterEntity monster : list) {
				monster.addEffect(new EffectInstance(AquamiraeMobEffects.ABYSS_BLINDNESS.get(), 10, 0, false, false));
			}
			final double radius = 4;
			world.addParticle(ParticleTypes.DRAGON_BREATH, entity.getX() + Math.cos(entity.tickCount * 0.05) * radius, entity.getY() + 0.5,
					entity.getZ() + Math.sin(entity.tickCount * 0.05) * radius, 0, 0, 0);
			world.addParticle(ParticleTypes.DRAGON_BREATH, entity.getX() + Math.cos(entity.tickCount * 0.05 + 3.12) * radius, entity.getY() + 0.5,
					entity.getZ() + Math.sin(entity.tickCount * 0.05 + 3.12) * radius, 0, 0, 0);
		}
	}

	public static class Helmet extends AbyssalTiaraItem {
		public Helmet() {
			super(EquipmentSlotType.HEAD, new Item.Properties());
		}

		@Override
		public <A extends BipedModel<?>> A getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlotType slot, A defaultModel) {
			final BipedModel<?> model = new BipedModel<>(1F);
			model.head = new ModelAbyssalArmor<>().tiara;
			model.crouching = living.isCrouching();
			model.young = living.isBaby();
			model.riding = defaultModel.riding;
			return (A) model;
		}

		@Override
		public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
			return "aquamirae:textures/entity/armor/abyssal_tiara.png";
		}
	}
}
