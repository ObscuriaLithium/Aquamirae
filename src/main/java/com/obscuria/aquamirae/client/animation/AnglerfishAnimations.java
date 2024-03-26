package com.obscuria.aquamirae.client.animation;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.common.entity.Anglerfish;
import com.obscuria.core.api.animation.Animation;
import com.obscuria.core.api.animation.AnimationHolder;
import com.obscuria.core.api.animation.KeyFrame;
import com.obscuria.core.api.animation.entity.IEntityAnimationBundle;
import com.obscuria.core.api.animation.tool.Animator;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public enum AnglerfishAnimations implements IEntityAnimationBundle<Anglerfish> {
    IDLE("IDLE", AnglerfishAnimations::createIdle, AnglerfishAnimations::animateIdle),
    SWIM("SWIM", AnglerfishAnimations::createSwim, AnglerfishAnimations::animateSwim),
    ATTACK("ATTACK", AnglerfishAnimations::createAttack, AnglerfishAnimations::animate);

    private final String name;
    private final Supplier<Animation> animation;
    private final AnimationConsumer<Anglerfish> consumer;

    AnglerfishAnimations(String name, Supplier<Animation> animation, AnimationConsumer<Anglerfish> consumer) {
        this.name = name;
        this.animation = animation;
        this.consumer = consumer;
    }

    @Override
    public AnimationHolder getHolder() {
        return AnimationHolder.reference(Aquamirae.key("test"));
    }

    @Override
    public AnimationConsumer<Anglerfish> getConsumer() {
        return this.consumer;
    }

    @Override
    public String getName() {
        return this.name;
    }

    private static Animation createIdle() {
        return Animation.create(0)
                .addKeyFrame(KeyFrame.create(0))
                .build();
    }

    private static Animation createSwim() {
        return Animation.create(0).build();
    }

    private static Animation createAttack() {
        return Animation.create(0).build();
    }

    private static void animateIdle(Animator animator, Anglerfish entity, String name, float moveTimer, float moveFactor) {

    }

    private static void animateSwim(Animator animator, Anglerfish entity, String name, float moveTimer, float moveFactor) {

    }

    private static void animate(Animator animator, Anglerfish entity, String name, float moveTimer, float moveFactor) {

    }
}
