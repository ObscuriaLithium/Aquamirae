
package com.obscuria.aquamirae.world.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.jetbrains.annotations.NotNull;

public class HealthDecreaseMobEffect extends MobEffect {
	public HealthDecreaseMobEffect() {
		super(MobEffectCategory.NEUTRAL, -6750055);
		addAttributeModifier(Attributes.MAX_HEALTH, "5D6F0BA2-1186-46AC-B896-C61C5CEE99CC", -0.05D, AttributeModifier.Operation.MULTIPLY_TOTAL);
	}

	@Override
	public @NotNull String getDescriptionId() {
		return "effect.aquamirae.health_decrease";
	}

	@Override
	public boolean isDurationEffectTick(int duration, int amplifier) {
		return true;
	}

	public void addAttributeModifiers(@NotNull LivingEntity entity, @NotNull AttributeMap map, int level) {
		super.addAttributeModifiers(entity, map, level);
		if (entity.getHealth() > entity.getMaxHealth())
			entity.setHealth(entity.getMaxHealth());
	}
}
