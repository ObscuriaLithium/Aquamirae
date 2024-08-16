
package com.obscuria.aquamirae.common.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class DividerMobEffect extends MobEffect {

	public DividerMobEffect() {
		super(MobEffectCategory.HARMFUL, -6750055);
		addAttributeModifier(Attributes.MAX_HEALTH, "5D6F0BA2-1186-46AC-B896-C61C5CEE99CC",
				-0.04D, AttributeModifier.Operation.MULTIPLY_TOTAL);
	}

	@Override
	public String getDescriptionId() {
		return "effect.aquamirae.divider";
	}
}
