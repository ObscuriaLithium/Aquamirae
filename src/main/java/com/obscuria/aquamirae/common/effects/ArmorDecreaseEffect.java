
package com.obscuria.aquamirae.common.effects;

import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class ArmorDecreaseEffect extends StatusEffect {
	public ArmorDecreaseEffect() {
		super(StatusEffectCategory.HARMFUL, -6750055);
		addAttributeModifier(EntityAttributes.GENERIC_ARMOR, "5D6F0BA2-1186-46AC-B896-C61C5CEE99CC",
				-0.1D, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
	}

	@Override
	public String getTranslationKey() {
		return "effect.aquamirae.armor_decrease";
	}

	@Override
	public boolean canApplyUpdateEffect(int duration, int amplifier) {
		return false;
	}
}
