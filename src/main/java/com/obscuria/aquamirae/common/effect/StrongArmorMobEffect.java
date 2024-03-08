
package com.obscuria.aquamirae.common.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.jetbrains.annotations.NotNull;

public class StrongArmorMobEffect extends MobEffect {
	public StrongArmorMobEffect() {
		super(MobEffectCategory.BENEFICIAL, -3407668);
		addAttributeModifier(Attributes.ARMOR, "5D6F0BA2-1286-46AC-B896-C61C5CAE91CC", 0.25D, AttributeModifier.Operation.MULTIPLY_BASE);
		addAttributeModifier(Attributes.ARMOR_TOUGHNESS, "5D6F0BA2-1286-46AC-B896-C61C5CAE92CC", 4D, AttributeModifier.Operation.ADDITION);
	}

	@Override
	public @NotNull String getDescriptionId() {
		return "effect.aquamirae.strong_armor";
	}
}
