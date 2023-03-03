
package com.obscuria.aquamirae.common.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifierManager;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.DamageSource;

import javax.annotation.Nonnull;

public class CrystallizationMobEffect extends Effect {
	public CrystallizationMobEffect() {
		super(EffectType.HARMFUL, -6750055);
		addAttributeModifier(Attributes.ATTACK_DAMAGE, "5D6F0BA2-1286-46AC-B896-C61C5CAE91CA", -0.8D, AttributeModifier.Operation.MULTIPLY_TOTAL);
	}

	@Override
	@Nonnull
	public String getDescriptionId() {
		return "effect.aquamirae.crystallization";
	}

	@Override
	public void removeAttributeModifiers(@Nonnull LivingEntity entity, @Nonnull AttributeModifierManager attributeMap, int amplifier) {
		super.removeAttributeModifiers(entity, attributeMap, amplifier);
		entity.getPersistentData().putBoolean("crystallization", true);
		entity.hurt(new DamageSource("crystallization").bypassArmor().setMagic(), 9999999F);
		if (entity.isAlive()) entity.getPersistentData().putBoolean("crystallization", false);
	}
}
