
package com.obscuria.aquamirae.common.effect;

import com.obscuria.aquamirae.registry.AquamiraeAttributes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class DepthsFuryMobEffect extends MobEffect {
	public DepthsFuryMobEffect() {
		super(MobEffectCategory.BENEFICIAL, -16737844);
		addAttributeModifier(AquamiraeAttributes.DEPTHS_FURY.get(), "A6D6B8C9-6E5A-4C72-9AA9-D4BE2C217E31",
				0.1D, AttributeModifier.Operation.MULTIPLY_BASE);
	}

	@Override
	public String getDescriptionId() {
		return "effect.aquamirae.depths_fury";
	}
}
