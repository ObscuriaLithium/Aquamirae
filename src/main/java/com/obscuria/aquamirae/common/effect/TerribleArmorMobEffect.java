
package com.obscuria.aquamirae.common.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.common.ForgeMod;

public class TerribleArmorMobEffect extends MobEffect {
	public TerribleArmorMobEffect() {
		super(MobEffectCategory.BENEFICIAL, -16737844);
		addAttributeModifier(ForgeMod.SWIM_SPEED.get(), "5D6F0BA2-1186-46AC-B896-C61C5CAE99CC",
				0.1D, AttributeModifier.Operation.MULTIPLY_BASE);
	}

	@Override
	public String getDescriptionId() {
		return "effect.aquamirae.terrible_armor";
	}
}
