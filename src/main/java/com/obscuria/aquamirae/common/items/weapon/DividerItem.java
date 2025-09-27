
package com.obscuria.aquamirae.common.items.weapon;

import com.obscuria.aquamirae.Aquamirae;
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

@ClassItem(clazz = "aquamirae:sea_wolf", type = "weapon")
public class DividerItem extends SwordItem {
	public DividerItem() {
		super(AquamiraeTiers.DIVIDER, 3, -2.6f, new Item.Properties().fireResistant().rarity(Rarity.EPIC));
	}

	@ClassAbility
	public final Ability ABILITY = Ability.create(Aquamirae.MODID, "divider").var(10, "s").action(
			(stack, entity, target, context, values) -> {
				if (target == null) return false;
				final MobEffectInstance EFFECT = target.getEffect(AquamiraeMobEffects.HEALTH_DECREASE.get());
				if (EFFECT != null) target.addEffect(new MobEffectInstance(AquamiraeMobEffects.HEALTH_DECREASE.get(),
						20 * values.get(0), Math.min(9, EFFECT.getAmplifier() + 1), false, false));
				else target.addEffect(new MobEffectInstance(AquamiraeMobEffects.HEALTH_DECREASE.get(),
						20 * values.get(0), 0, false, false));
				return true;
			}).build(DividerItem.class);

	@Override
	public boolean hurtEnemy(@NotNull ItemStack stack, @NotNull LivingEntity entity, @NotNull LivingEntity source) {
		final boolean hurt = super.hurtEnemy(stack, entity, source);
		if (hurt) ABILITY.use(stack, source, entity, null);
		return hurt;
	}
}
