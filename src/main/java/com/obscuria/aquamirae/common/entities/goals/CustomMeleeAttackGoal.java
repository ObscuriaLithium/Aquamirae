package com.obscuria.aquamirae.common.entities.goals;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class CustomMeleeAttackGoal extends MeleeAttackGoal {

    private final double rangeBonus;

    public CustomMeleeAttackGoal(PathfinderMob mob, double rangeBonus, double speedModifier, boolean alwaysFollow) {
        super(mob, speedModifier, alwaysFollow);
        this.rangeBonus = rangeBonus;
    }

    @Override
    protected double getAttackReachSqr(LivingEntity entity) {
        return rangeBonus + entity.getBbWidth() * entity.getBbWidth();
    }
}
