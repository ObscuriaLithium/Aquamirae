
package com.obscuria.aquamirae.common.items.weapon;

import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.registry.AquamiraeMobEffects;
import com.obscuria.aquamirae.common.items.AquamiraeTiers;
import com.obscuria.obscureapi.api.common.classes.Ability;
import com.obscuria.obscureapi.api.common.classes.ClassAbility;
import com.obscuria.obscureapi.api.common.classes.ClassItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SwordItem;
import org.jetbrains.annotations.NotNull;

@ClassItem(itemClass = "aquamirae:sea_wolf", itemType = "weapon")
public class DividerItem extends SwordItem {
	public DividerItem() {
		super(AquamiraeTiers.DIVIDER, 3, -2.6f, new Item.Properties().fireResistant().rarity(Rarity.EPIC).tab(AquamiraeMod.TAB));
	}

	@ClassAbility
	public final Ability ABILITY = Ability.create(AquamiraeMod.MODID).description("divider").variables(10).modifiers("s").build(this);

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
