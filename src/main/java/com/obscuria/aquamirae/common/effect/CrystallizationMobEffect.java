
package com.obscuria.aquamirae.common.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class CrystallizationMobEffect extends MobEffect {
	public CrystallizationMobEffect() {
		super(MobEffectCategory.HARMFUL, -6750055);
		addAttributeModifier(Attributes.ATTACK_DAMAGE, "5D6F0BA2-1286-46AC-B896-C61C5CAE91CA",
				-0.01D, AttributeModifier.Operation.MULTIPLY_BASE);
	}

	@Override
	public String getDescriptionId() {
		return "effect.aquamirae.crystallization";
	}
}
