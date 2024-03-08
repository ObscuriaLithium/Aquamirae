
package com.obscuria.aquamirae.common.effect;

import com.obscuria.aquamirae.registry.AquamiraeMobEffects;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.common.ForgeMod;

import java.util.Optional;

public class ThreeeBoltArmorMobEffect extends MobEffect {
	public ThreeeBoltArmorMobEffect() {
		super(MobEffectCategory.BENEFICIAL, -16737844);
		addAttributeModifier(ForgeMod.SWIM_SPEED.get(), "1D6F0BA2-1186-461C-B896-C61C5CAE59CC",
				0.2D, AttributeModifier.Operation.MULTIPLY_BASE);
	}

	@Override
	public boolean shouldApplyEffectTickThisTick(int tick, int amplifier) {
		return true;
	}

	@Override
	public void applyEffectTick(LivingEntity entity, int amplifier) {
		Optional.ofNullable(entity.getEffect(AquamiraeMobEffects.THREE_BOLT_ARMOR.get()))
				.ifPresent(effect -> {
					final var duration = effect.getDuration();
					if (duration <= 0) return;
					final var newAmplifier = Math.round(5 * Mth.clamp(duration / 200f, 0, 1));
					if (newAmplifier == amplifier) return;
					entity.removeEffect(effect.getEffect());
					entity.addEffect(new MobEffectInstance(effect.getEffect(), duration, newAmplifier));
				});
	}

	@Override
	public String getDescriptionId() {
		return "effect.aquamirae.three_bolt_armor";
	}
}
