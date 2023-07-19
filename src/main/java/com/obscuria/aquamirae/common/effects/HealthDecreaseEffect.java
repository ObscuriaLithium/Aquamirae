
package com.obscuria.aquamirae.common.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class HealthDecreaseEffect extends StatusEffect {
	public HealthDecreaseEffect() {
		super(StatusEffectCategory.HARMFUL, -6750055);
		addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH, "5D6F0BA2-1186-46AC-B896-C61C5CEE99CC",
				-0.05D, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
	}

	@Override
	public String getTranslationKey() {
		return "effect.aquamirae.health_decrease";
	}

	@Override
	public boolean canApplyUpdateEffect(int duration, int amplifier) {
		return false;
	}

	@Override
	public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
		super.onApplied(entity, attributes, amplifier);
		if (entity.getHealth() > entity.getMaxHealth())
			entity.setHealth(entity.getMaxHealth());
	}
}
