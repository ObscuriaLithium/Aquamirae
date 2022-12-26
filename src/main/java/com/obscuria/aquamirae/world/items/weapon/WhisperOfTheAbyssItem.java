
package com.obscuria.aquamirae.world.items.weapon;

import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import com.obscuria.aquamirae.registry.AquamiraeMobEffects;
import com.obscuria.obscureapi.ObscureAPI;
import com.obscuria.obscureapi.world.classes.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.EffectInstance;

import java.util.Collections;
import java.util.List;

public class WhisperOfTheAbyssItem extends SwordItem implements IClassItem, IAbilityItem {
	public WhisperOfTheAbyssItem() {
		super(new IItemTier() {
			public int getUses() {
				return 1400;
			}

			public float getSpeed() {
				return 6f;
			}

			public float getAttackDamageBonus() {
				return 10f;
			}

			public int getLevel() {
				return 3;
			}

			public int getEnchantmentValue() {
				return 14;
			}

			public Ingredient getRepairIngredient() {
				return Ingredient.of(new ItemStack(AquamiraeItems.SHIP_GRAVEYARD_ECHO.get()),
						new ItemStack(AquamiraeItems.ABYSSAL_AMETHYST.get()));
			}
		}, 3, -3.2f, new Item.Properties().fireResistant().rarity(Rarity.EPIC).tab(AquamiraeMod.TAB));
	}

	public final ObscureAbility ABILITY = new ObscureAbility(this, "whisper_of_the_abyss", ObscureAbility.Cost.NONE, 0, 10);

	public List<ObscureAbility> getObscureAbilities() {
		return Collections.singletonList(ABILITY);
	}

	public ObscureClass getObscureClass() {
		return AquamiraeMod.SEA_WOLF;
	}

	public ObscureType getObscureType() {
		return ObscureAPI.Types.WEAPON;
	}

	@Override
	public boolean hurtEnemy(ItemStack itemstack, LivingEntity entity, LivingEntity source) {
		final boolean hurt = super.hurtEnemy(itemstack, entity, source);
		if (hurt) {
			final EffectInstance EFFECT = entity.getEffect(AquamiraeMobEffects.ARMOR_DECREASE.get());
			if (EFFECT != null) entity.addEffect(new EffectInstance(AquamiraeMobEffects.ARMOR_DECREASE.get(),
						20 * ABILITY.getAmount(source, 0), Math.min(4, EFFECT.getAmplifier() + 1), false, false));
			else entity.addEffect(new EffectInstance(AquamiraeMobEffects.ARMOR_DECREASE.get(),
						20 * ABILITY.getAmount(source, 0), 0, false, false));
		}
		return hurt;
	}
}
