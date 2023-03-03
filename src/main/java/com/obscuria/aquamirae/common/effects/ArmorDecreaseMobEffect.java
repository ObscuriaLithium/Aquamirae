
package com.obscuria.aquamirae.common.effects;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

import javax.annotation.Nonnull;

public class ArmorDecreaseMobEffect extends Effect {
	public ArmorDecreaseMobEffect() {
		super(EffectType.NEUTRAL, -6750055);
		addAttributeModifier(Attributes.ARMOR, "5D6F0BA2-1186-46AC-B896-C61C5CEE99CC", -0.1D, AttributeModifier.Operation.MULTIPLY_TOTAL);
	}

	@Override
	@Nonnull
	public String getDescriptionId() {
		return "effect.aquamirae.armor_decrease";
	}

	@Override
	public boolean isDurationEffectTick(int duration, int amplifier) {
		return true;
	}
}
