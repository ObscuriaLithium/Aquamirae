
package com.obscuria.aquamirae.common.items.armor;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.common.items.AquamiraeMaterials;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import com.obscuria.obscureapi.common.classes.ClassItem;
import com.obscuria.obscureapi.common.classes.ability.Ability;
import com.obscuria.obscureapi.common.classes.ability.RegisterAbility;
import com.obscuria.obscureapi.common.classes.bonus.Bonus;
import com.obscuria.obscureapi.common.classes.bonus.RegisterBonus;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;

@ClassItem(value = Aquamirae.SEA_WOLF_ID, type = "armor")
public abstract class TerribleArmorItem extends ArmorItem {
	public static final Ability HALFSET;
	public static final Ability FULLSET;

	public TerribleArmorItem(ArmorItem.Type type, Settings settings) {
		super(AquamiraeMaterials.TERRIBLE_ARMOR, type, settings);
	}

	public static void halfSetEffect(LivingEntity entity) {
		if (entity.isSubmergedInWater()) {
			if (entity instanceof PlayerEntity player) {
				if (player.getItemCooldownManager().isCoolingDown(AquamiraeItems.TERRIBLE_CHESTPLATE)) return;
				player.addStatusEffect(new StatusEffectInstance(StatusEffects.DOLPHINS_GRACE, 20 * HALFSET.getVariable(entity, 1)));
				final int cooldown = 20 * HALFSET.getCost(player);
				player.getItemCooldownManager().set(AquamiraeItems.TERRIBLE_HELMET, cooldown);
				player.getItemCooldownManager().set(AquamiraeItems.TERRIBLE_CHESTPLATE, cooldown);
				player.getItemCooldownManager().set(AquamiraeItems.TERRIBLE_LEGGINGS, cooldown);
				player.getItemCooldownManager().set(AquamiraeItems.TERRIBLE_BOOTS, cooldown);
			}
			entity.addStatusEffect(new StatusEffectInstance(StatusEffects.DOLPHINS_GRACE, 20 * HALFSET.getVariable(entity, 1)));
		}
	}

	public static void fullSetEffect(LivingEntity entity, DamageSource source) {
		if (source.getSource() instanceof LivingEntity attacker)
			attacker.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON,
					20 * FULLSET.getVariable(entity, 1), 1, false, true));
	}

	static {
		HALFSET = Ability.create(Aquamirae.MODID, "terrible_armor_half")
				.cost(Ability.CostType.COOLDOWN, 10)
				.sec(20)
				.build(TerribleArmorItem.class);
		FULLSET = Ability.create(Aquamirae.MODID, "terrible_armor_full")
				.sec(4)
				.build(TerribleArmorItem.class);
	}

	public static class Helmet extends TerribleArmorItem {
		@RegisterAbility public static final Ability HALFSET;
		@RegisterAbility public static final Ability FULLSET;
		@RegisterBonus public static final Bonus BONUS;

		public Helmet(Settings settings) {
			super(Type.HELMET, settings);
		}

		static {
			HALFSET = TerribleArmorItem.HALFSET;
			FULLSET = TerribleArmorItem.FULLSET;
			BONUS = Bonus.create()
					.target(Aquamirae.SEA_WOLF, "weapon")
					.type(Bonus.Type.POWER, Bonus.Operation.PERCENT)
					.value(20)
					.build();
		}
	}

	public static class Chestplate extends TerribleArmorItem {
		@RegisterAbility public static final Ability HALFSET;
		@RegisterAbility public static final Ability FULLSET;
		@RegisterBonus public static final Bonus BONUS;

		public Chestplate(Settings settings) {
			super(Type.CHESTPLATE, settings);
		}

		static {
			HALFSET = TerribleArmorItem.HALFSET;
			FULLSET = TerribleArmorItem.FULLSET;
			BONUS = Bonus.create()
					.target(Aquamirae.SEA_WOLF, "weapon")
					.type(Bonus.Type.POWER, Bonus.Operation.PERCENT)
					.value(10)
					.build();
		}
	}

	public static class Leggings extends TerribleArmorItem {
		@RegisterAbility public static final Ability HALFSET;
		@RegisterAbility public static final Ability FULLSET;
		@RegisterBonus public static final Bonus BONUS;

		public Leggings(Settings settings) {
			super(Type.LEGGINGS, settings);
		}

		static {
			HALFSET = TerribleArmorItem.HALFSET;
			FULLSET = TerribleArmorItem.FULLSET;
			BONUS = Bonus.create()
					.target(Aquamirae.SEA_WOLF, "weapon")
					.type(Bonus.Type.COOLDOWN, Bonus.Operation.PERCENT)
					.value(-10)
					.build();
		}
	}

	public static class Boots extends TerribleArmorItem {
		@RegisterAbility public static final Ability HALFSET;
		@RegisterAbility public static final Ability FULLSET;
		@RegisterBonus public static final Bonus BONUS;

		public Boots(Settings settings) {
			super(Type.BOOTS, settings);
		}

		static {
			HALFSET = TerribleArmorItem.HALFSET;
			FULLSET = TerribleArmorItem.FULLSET;
			BONUS = Bonus.create()
					.target(Aquamirae.SEA_WOLF, "weapon")
					.type(Bonus.Type.COOLDOWN, Bonus.Operation.PERCENT)
					.value(-20)
					.build();
		}
	}
}
