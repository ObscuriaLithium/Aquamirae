package com.obscuria.aquamirae.common.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public final class PoisonImmunityMobEffect extends MobEffect {

    public PoisonImmunityMobEffect() {
        super(MobEffectCategory.BENEFICIAL, -6750055);
    }

    @Override
    public String getDescriptionId() {
        return "effect.aquamirae.poison_immunity";
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        entity.removeEffect(MobEffects.POISON);
    }
}
