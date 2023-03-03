
package com.obscuria.aquamirae.common.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifierManager;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

import javax.annotation.Nonnull;

public class HealthDecreaseMobEffect extends Effect {
	public HealthDecreaseMobEffect() {
		super(EffectType.NEUTRAL, -6750055);
		addAttributeModifier(Attributes.MAX_HEALTH, "5D6F0BA2-1186-46AC-B896-C61C5CEE99CC", -0.05D, AttributeModifier.Operation.MULTIPLY_TOTAL);
	}

	@Override
	@Nonnull
	public String getDescriptionId() {
		return "effect.aquamirae.health_decrease";
	}

	@Override
	public boolean isDurationEffectTick(int duration, int amplifier) {
		return true;
	}

	public void addAttributeModifiers(@Nonnull LivingEntity entity, @Nonnull AttributeModifierManager map, int level) {
		super.addAttributeModifiers(entity, map, level);
		if (entity.getHealth() > entity.getMaxHealth())
			entity.setHealth(entity.getMaxHealth());
	}
}
