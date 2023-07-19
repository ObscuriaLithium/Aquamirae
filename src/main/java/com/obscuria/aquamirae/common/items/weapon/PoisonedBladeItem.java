
package com.obscuria.aquamirae.common.items.weapon;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.common.items.AquamiraeMaterials;
import com.obscuria.obscureapi.common.classes.ClassItem;
import com.obscuria.obscureapi.common.classes.ability.Ability;
import com.obscuria.obscureapi.common.classes.ability.RegisterAbility;
import com.obscuria.obscureapi.common.classes.ability.context.AbilityContext;
import com.obscuria.obscureapi.common.classes.ability.context.CombatAbilityContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;

import java.util.List;

@ClassItem(value = Aquamirae.SEA_WOLF_ID, type = "weapon")
public class PoisonedBladeItem extends SwordItem {
	@RegisterAbility public static final Ability PASSIVE;

	public PoisonedBladeItem(Settings settings) {
		super(AquamiraeMaterials.POISONED_BLADE, 3, -1f, settings);
	}

	@Override
	public boolean postHit(ItemStack stack, LivingEntity entity, LivingEntity source) {
		final boolean hit = super.postHit(stack, entity, source);
		if (hit) PASSIVE.use(new CombatAbilityContext(source, stack, entity));
		return hit;
	}

	public static boolean applyEffect(AbilityContext context, List<Integer> vars) {
		if (context instanceof CombatAbilityContext combat) {
			combat.getTarget().addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 20 * vars.get(0), 1));
			return true;
		}
		return false;
	}

	static {
		PASSIVE = Ability.create(Aquamirae.MODID, "poisoned_blade")
				.cost(Ability.CostType.COOLDOWN, 10)
				.action(PoisonedBladeItem::applyEffect)
				.sec(5)
				.build(PoisonedBladeItem.class);
	}
}
