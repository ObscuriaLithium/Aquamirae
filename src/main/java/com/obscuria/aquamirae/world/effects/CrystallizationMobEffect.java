
package com.obscuria.aquamirae.world.effects;

import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.damagesource.DamageSource;
import org.jetbrains.annotations.NotNull;

public class CrystallizationMobEffect extends MobEffect {
	public CrystallizationMobEffect() {
		super(MobEffectCategory.HARMFUL, -6750055);
		addAttributeModifier(Attributes.ATTACK_DAMAGE, "5D6F0BA2-1286-46AC-B896-C61C5CAE91CA", -0.8D, AttributeModifier.Operation.MULTIPLY_TOTAL);
	}

	@Override
	public @NotNull String getDescriptionId() {
		return "effect.aquamirae.crystallization";
	}

	@Override
	public void removeAttributeModifiers(@NotNull LivingEntity entity, @NotNull AttributeMap attributeMap, int amplifier) {
		super.removeAttributeModifiers(entity, attributeMap, amplifier);
		entity.getPersistentData().putBoolean("crystallization", true);
		entity.hurt(new DamageSource("crystallization").bypassArmor().setMagic(), 9999999F);
		if (entity.isAlive()) entity.getPersistentData().putBoolean("crystallization", false);
	}
}
