package com.obscuria.aquamirae.client.animation;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.common.entity.Maw;
import com.obscuria.core.api.animation.AnimationHolder;
import com.obscuria.core.api.animation.entity.IEntityAnimationBundle;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public enum MawAnimations implements IEntityAnimationBundle<Maw> {
    IDLE(AnimationHolder.reference(Aquamirae.key("maw/idle")), IEntityAnimationBundle::simpleIdle),
    SPECIAL_IDLE(AnimationHolder.reference(Aquamirae.key("maw/special_idle")), IEntityAnimationBundle::simple),
    WALK(AnimationHolder.reference(Aquamirae.key("maw/walk")), IEntityAnimationBundle::simpleWalk);
    private final AnimationHolder holder;
    private final AnimationConsumer<Maw> consumer;

    MawAnimations(AnimationHolder reference, AnimationConsumer<Maw> setupFunction) {
        this.holder = reference;
        this.consumer = setupFunction;
    }

    @Override
    public AnimationHolder getHolder() {
        return holder;
    }

    @Override
    public AnimationConsumer<Maw> getConsumer() {
        return this.consumer;
    }
}
