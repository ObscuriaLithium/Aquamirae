
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
public class WhisperOfTheAbyssItem extends SwordItem {
	@RegisterAbility public static final Ability PASSIVE;

	public WhisperOfTheAbyssItem(Settings settings) {
		super(AquamiraeMaterials.WHISPER_OF_tHE_ABYSS, 3, -3.2f, settings);
	}

	@Override
	public boolean postHit(ItemStack stack, LivingEntity entity, LivingEntity source) {
		final boolean hit = super.postHit(stack, entity, source);
		if (hit) PASSIVE.use(new CombatAbilityContext(source, stack, entity));
		return hit;
	}

	public static boolean applyEffect(AbilityContext context, List<Integer> vars) {
		if (context instanceof CombatAbilityContext combat) {
			final StatusEffectInstance effect = combat.getTarget().getStatusEffect(AquamiraeEffects.ARMOR_DECREASE);
			if (effect != null) combat.getTarget().addStatusEffect(new StatusEffectInstance(AquamiraeEffects.ARMOR_DECREASE,
					20 * vars.get(0), Math.min(4, effect.getAmplifier() + 1), false, false));
			else combat.getTarget().addStatusEffect(new StatusEffectInstance(AquamiraeEffects.ARMOR_DECREASE,
					20 * vars.get(0), 0, false, false));
			return true;
		}
		return false;
	}

	static {
		PASSIVE = Ability.create(Aquamirae.MODID, "whisper_of_the_abyss")
				.action(WhisperOfTheAbyssItem::applyEffect)
				.sec(10)
				.build(WhisperOfTheAbyssItem.class);
	}
}
