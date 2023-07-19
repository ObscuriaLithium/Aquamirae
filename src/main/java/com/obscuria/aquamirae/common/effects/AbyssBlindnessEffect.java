package com.obscuria.aquamirae.common.effects;

import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class AbyssBlindnessEffect extends StatusEffect {
	public AbyssBlindnessEffect() {
		super(StatusEffectCategory.HARMFUL, -6750055);
		addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, "5D6F0BA2-1286-46AC-B896-C61C5CAE91DA",
				-0.5D, EntityAttributeModifier.Operation.MULTIPLY_BASE);
	}

	@Override
	public String getTranslationKey() {
		return "effect.aquamirae.abyss_blindness";
	}

	@Override
	public boolean canApplyUpdateEffect(int duration, int amplifier) {
		return false;
	}
}
