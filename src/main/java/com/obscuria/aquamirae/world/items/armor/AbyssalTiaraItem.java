
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
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.IItemRenderProperties;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Consumer;

public abstract class AbyssalTiaraItem extends ArmorItem implements IClassItem, IAbilityItem, IBonusItem {
	public AbyssalTiaraItem(EquipmentSlot slot, Item.Properties properties) {
		super(new ArmorMaterial() {
			@Override
			public int getDurabilityForSlot(@NotNull EquipmentSlot slot) {
				return new int[]{13, 15, 16, 11}[slot.getIndex()] * 40;
			}

			@Override
			public int getDefenseForSlot(@NotNull EquipmentSlot slot) {
				return new int[]{3, 6, 8, 2}[slot.getIndex()];
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
	public void onArmorTick(ItemStack itemstack, Level world, Player entity) {
		if (entity.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof AbyssalArmorItem
				&& entity.getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof AbyssalArmorItem
				&& entity.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof AbyssalArmorItem) {
			final Vec3 center = new Vec3(entity.getX(), entity.getY() + 1, entity.getZ());
			List<Monster> list = world.getEntitiesOfClass(Monster.class, new AABB(center, center).inflate(4), e -> true).stream()
					.sorted(Comparator.comparingDouble(entities -> entities.distanceToSqr(center))).toList();
			for (Monster monster : list) {
				monster.addEffect(new MobEffectInstance(AquamiraeMobEffects.ABYSS_BLINDNESS.get(), 10, 0, false, false));
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
			super(EquipmentSlot.HEAD, new Item.Properties());
		}

		public void initializeClient(Consumer<IItemRenderProperties> consumer) {
			consumer.accept(new IItemRenderProperties() {
				@Override
				public @NotNull HumanoidModel<? extends LivingEntity> getArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {
					HumanoidModel<? extends LivingEntity> armorModel = new HumanoidModel<>(new ModelPart(Collections.emptyList(), Map.of(
							"head", new ModelAbyssalArmor<>(Minecraft.getInstance().getEntityModels().bakeLayer(ModelAbyssalArmor.LAYER_LOCATION)).tiara,
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
			return "aquamirae:textures/entity/armor/abyssal_tiara.png";
		}
	}
}
