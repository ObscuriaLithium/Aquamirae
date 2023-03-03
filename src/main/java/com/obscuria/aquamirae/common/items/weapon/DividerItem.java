
package com.obscuria.aquamirae.common.items.weapon;

import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import com.obscuria.aquamirae.registry.AquamiraeMobEffects;
import com.obscuria.obscureapi.ObscureAPI;
import com.obscuria.obscureapi.world.classes.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.EffectInstance;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

public class DividerItem extends SwordItem implements IClassItem, IAbilityItem {
	public DividerItem() {
		super(new IItemTier() {
			public int getUses() {
				return 2200;
			}

			public float getSpeed() {
				return 6f;
			}

			public float getAttackDamageBonus() {
				return 3f;
			}

			public int getLevel() {
				return 3;
			}

			public int getEnchantmentValue() {
				return 14;
			}

			public @Nonnull Ingredient getRepairIngredient() {
				return Ingredient.of(new ItemStack(AquamiraeItems.SHIP_GRAVEYARD_ECHO.get()),
						new ItemStack(AquamiraeItems.ABYSSAL_AMETHYST.get()));
			}
		}, 3, -2.6f, new Item.Properties().fireResistant().rarity(Rarity.EPIC).tab(AquamiraeMod.TAB));
	}

	public final ObscureAbility ABILITY = new ObscureAbility(this, "divider", ObscureAbility.Cost.NONE, 0, 10);

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
	public boolean hurtEnemy(@Nonnull ItemStack itemstack, @Nonnull  LivingEntity entity, @Nonnull LivingEntity source) {
		final boolean hurt = super.hurtEnemy(itemstack, entity, source);
		if (hurt) {
			final EffectInstance EFFECT = entity.getEffect(AquamiraeMobEffects.HEALTH_DECREASE.get());
			if (EFFECT != null) entity.addEffect(new EffectInstance(AquamiraeMobEffects.HEALTH_DECREASE.get(),
						20 * ABILITY.getAmount(source, 0), Math.min(9, EFFECT.getAmplifier() + 1), false, false));
			else entity.addEffect(new EffectInstance(AquamiraeMobEffects.HEALTH_DECREASE.get(),
						20 * ABILITY.getAmount(source, 0), 0, false, false));
		}
		return hurt;
	}
}