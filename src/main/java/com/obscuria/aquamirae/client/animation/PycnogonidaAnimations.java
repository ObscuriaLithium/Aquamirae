package com.obscuria.aquamirae.client.animation;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.common.entity.Pycnogonida;
import com.obscuria.core.client.animation.AnimationHolder;
import com.obscuria.core.client.animation.tool.IEntityAnimationBundle;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public enum PycnogonidaAnimations implements IEntityAnimationBundle<Pycnogonida> {
    IDLE(AnimationHolder.reference(Aquamirae.key("pycnogonida/idle")), IEntityAnimationBundle::simpleIdle);

    private final AnimationHolder holder;
    private final AnimationConsumer<Pycnogonida> consumer;

    PycnogonidaAnimations(AnimationHolder reference, AnimationConsumer<Pycnogonida> setupFunction) {
        this.holder = reference;
        this.consumer = setupFunction;
    }

    @Override
    public AnimationHolder getHolder() {
        return holder;
    }

    @Override
    public AnimationConsumer<Pycnogonida> getConsumer() {
        return this.consumer;
    }
}
