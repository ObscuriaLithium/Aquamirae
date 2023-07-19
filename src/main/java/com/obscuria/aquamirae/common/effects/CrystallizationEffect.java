
package com.obscuria.aquamirae.common.effects;

import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class CrystallizationEffect extends StatusEffect {
	public CrystallizationEffect() {
		super(StatusEffectCategory.HARMFUL, -6750055);
		addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE, "5D6F0BA2-1286-46AC-B896-C61C5CAE91CA",
				-0.8D, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
		addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, "5D6F0BA2-1286-46AC-B896-C15C3CAE91CA",
				-0.4D, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
	}

	@Override
	public String getTranslationKey() {
		return "effect.aquamirae.crystallization";
	}
}
