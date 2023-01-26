
package com.obscuria.aquamirae.world.items.weapon;

import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import com.obscuria.aquamirae.registry.AquamiraeMobEffects;
import com.obscuria.obscureapi.api.classes.Ability;
import com.obscuria.obscureapi.api.classes.ClassAbility;
import com.obscuria.obscureapi.api.classes.ClassItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

@ClassItem(itemClass = "aquamirae:sea_wolf", itemType = "weapon")
public class DividerItem extends SwordItem {
	public DividerItem() {
		super(new Tier() {
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

			public @NotNull Ingredient getRepairIngredient() {
				return Ingredient.of(new ItemStack(AquamiraeItems.SHIP_GRAVEYARD_ECHO.get()),
						new ItemStack(AquamiraeItems.ABYSSAL_AMETHYST.get()));
			}
		}, 3, -2.6f, new Item.Properties().fireResistant().rarity(Rarity.EPIC).tab(AquamiraeMod.TAB));
	}

	@ClassAbility
	public final Ability ABILITY = Ability.Builder.create(AquamiraeMod.MODID).description("divider").variables(10).build(this);

	@Override
	public boolean hurtEnemy(@NotNull ItemStack itemstack, @NotNull LivingEntity entity, @NotNull LivingEntity source) {
		final boolean hurt = super.hurtEnemy(itemstack, entity, source);
		if (hurt) {
			final MobEffectInstance EFFECT = entity.getEffect(AquamiraeMobEffects.HEALTH_DECREASE.get());
			if (EFFECT != null) entity.addEffect(new MobEffectInstance(AquamiraeMobEffects.HEALTH_DECREASE.get(),
						20 * ABILITY.getVariable(source, 1), Math.min(9, EFFECT.getAmplifier() + 1), false, false));
			else entity.addEffect(new MobEffectInstance(AquamiraeMobEffects.HEALTH_DECREASE.get(),
						20 * ABILITY.getVariable(source, 1), 0, false, false));
		}
		return hurt;
	}
}
