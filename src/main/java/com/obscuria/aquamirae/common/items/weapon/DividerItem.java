
package com.obscuria.aquamirae.common.items.weapon;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.common.items.AquamiraeMaterials;
import com.obscuria.aquamirae.registry.AquamiraeEffects;
import com.obscuria.obscureapi.common.classes.ClassItem;
import com.obscuria.obscureapi.common.classes.ability.Ability;
import com.obscuria.obscureapi.common.classes.ability.RegisterAbility;
import com.obscuria.obscureapi.common.classes.ability.context.AbilityContext;
import com.obscuria.obscureapi.common.classes.ability.context.CombatAbilityContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;

import java.util.List;

@ClassItem(value = Aquamirae.SEA_WOLF_ID, type = "weapon")
public class DividerItem extends SwordItem {
	@RegisterAbility public static final Ability PASSIVE;

	public DividerItem(Settings settings) {
		super(AquamiraeMaterials.DIVIDER, 3, -2.6f, settings);
	}

	@Override
	public boolean postHit(ItemStack stack, LivingEntity entity, LivingEntity source) {
		final boolean hit = super.postHit(stack, entity, source);
		if (hit) PASSIVE.use(new CombatAbilityContext(source, stack, entity));
		return hit;
	}

	public static boolean applyEffect(AbilityContext context, List<Integer> vars) {
		if (context instanceof CombatAbilityContext combat) {
			final StatusEffectInstance effect = combat.getTarget().getStatusEffect(AquamiraeEffects.HEALTH_DECREASE);
			if (effect != null) combat.getTarget().addStatusEffect(new StatusEffectInstance(AquamiraeEffects.HEALTH_DECREASE,
					20 * vars.get(0), Math.min(9, effect.getAmplifier() + 1), false, false));
			else combat.getTarget().addStatusEffect(new StatusEffectInstance(AquamiraeEffects.HEALTH_DECREASE,
					20 * vars.get(0), 0, false, false));
			return true;
		}
		return false;
	}

	static {
		PASSIVE = Ability.create(Aquamirae.MODID, "divider")
				.action(DividerItem::applyEffect)
				.sec(10)
				.build(DividerItem.class);
	}
}
