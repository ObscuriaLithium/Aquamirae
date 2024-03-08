package com.obscuria.aquamirae.common.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class HydrophobiaModEffect extends MobEffect {
    public HydrophobiaModEffect() {
        super(MobEffectCategory.HARMFUL, -16737844);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int tick, int amplifier) {
        return true;
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity instanceof Player player && (player.isCreative() || player.isSpectator())) return;
        if (entity.isUnderWater())
            entity.setAirSupply(Math.max(0, entity.getAirSupply() - amplifier * 2));
    }
}
