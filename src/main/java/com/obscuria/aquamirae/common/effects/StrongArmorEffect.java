
package com.obscuria.aquamirae.common.effects;

import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class StrongArmorEffect extends StatusEffect {
	public StrongArmorEffect() {
		super(StatusEffectCategory.BENEFICIAL, -3407668);
		addAttributeModifier(EntityAttributes.GENERIC_ARMOR, "5D6F0BA2-1286-46AC-B896-C61C5CAE91CC",
				0.25D, EntityAttributeModifier.Operation.MULTIPLY_BASE);
		addAttributeModifier(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, "5D6F0BA2-1286-46AC-B896-C61C5CAE92CC",
				4D, EntityAttributeModifier.Operation.ADDITION);
	}

	@Override
	public String getTranslationKey() {
		return "effect.aquamirae.strong_armor";
	}

	@Override
	public boolean canApplyUpdateEffect(int duration, int amplifier) {
		return false;
	}
}
