
package com.obscuria.aquamirae.world.items.weapon;

import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import com.obscuria.obscureapi.ObscureAPI;
import com.obscuria.obscureapi.world.classes.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

public class PoisonedBladeItem extends SwordItem implements IClassItem, IAbilityItem {
	public PoisonedBladeItem() {
		super(new IItemTier() {
			public int getUses() {
				return 500;
			}

			public float getSpeed() {
				return 4f;
			}

			public float getAttackDamageBonus() {
				return 1f;
			}

			public int getLevel() {
				return 2;
			}

			public int getEnchantmentValue() {
				return 6;
			}

			public @Nonnull Ingredient getRepairIngredient() {
				return Ingredient.of(new ItemStack(AquamiraeItems.ANGLERS_FANG.get()),
						new ItemStack(AquamiraeItems.SHIP_GRAVEYARD_ECHO.get()));
			}
		}, 3, -1f, new Item.Properties().tab(AquamiraeMod.TAB));
	}

	public final ObscureAbility ABILITY = new ObscureAbility(this, "poisoned_blade", ObscureAbility.Cost.COOLDOWN, 10, 5);

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
	public boolean hurtEnemy(@Nonnull ItemStack itemstack, @Nonnull LivingEntity entity, @Nonnull LivingEntity source) {
		final boolean hurt = super.hurtEnemy(itemstack, entity, source);
		if (hurt)
			if (source instanceof PlayerEntity) {
				final PlayerEntity player = (PlayerEntity) source;
				if (!player.getCooldowns().isOnCooldown(this)) {
					player.getCooldowns().addCooldown(this, 20 * ABILITY.getCost(source));
					entity.addEffect(new EffectInstance(Effects.POISON, 20 * ABILITY.getAmount(source, 0), 1));
				}
			} else {
				entity.addEffect(new EffectInstance(Effects.POISON, 20 * ABILITY.getAmount(source, 0), 1));
			}
		return hurt;
	}
}
