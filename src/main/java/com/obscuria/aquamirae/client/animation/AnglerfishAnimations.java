package com.obscuria.aquamirae.client.animation;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.common.entity.Anglerfish;
import com.obscuria.core.client.animation.AnimationHolder;
import com.obscuria.core.client.animation.tool.IEntityAnimationBundle;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public enum AnglerfishAnimations implements IEntityAnimationBundle<Anglerfish> {
    IDLE(AnimationHolder.reference(Aquamirae.key("anglerfish/idle")), IEntityAnimationBundle::simpleIdle),
    WALK(AnimationHolder.reference(Aquamirae.key("anglerfish/walk")), IEntityAnimationBundle::simpleWalk),
    BITE(AnimationHolder.reference(Aquamirae.key("anglerfish/bite")), IEntityAnimationBundle::simple);

    private final AnimationHolder holder;
    private final AnimationConsumer<Anglerfish> consumer;

    AnglerfishAnimations(AnimationHolder reference, AnimationConsumer<Anglerfish> setupFunction) {
        this.holder = reference;
        this.consumer = setupFunction;
    }

    @Override
    public AnimationHolder getHolder() {
        return holder;
    }

    @Override
    public AnimationConsumer<Anglerfish> getConsumer() {
        return this.consumer;
    }
}
