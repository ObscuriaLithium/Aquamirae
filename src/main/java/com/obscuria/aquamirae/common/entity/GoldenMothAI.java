package com.obscuria.aquamirae.common.entity;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.obscuria.aquamirae.common.entity.ai.NearestRestPositionSensor;
import com.obscuria.aquamirae.registry.AquamiraeBlocks;
import com.obscuria.aquamirae.registry.AquamiraeMemoryModules;
import com.obscuria.aquamirae.registry.AquamiraeSensors;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.phys.Vec3;

import java.util.function.Predicate;

public class GoldenMothAI {
    private static final ImmutableList<SensorType<? extends Sensor<? super GoldenMoth>>> SENSOR_TYPES =
            ImmutableList.of(
                    AquamiraeSensors.GOLDEN_MOTH_REST_POSITION.get(),
                    AquamiraeSensors.GOLDEN_MOTH_PLAYER.get());
    private static final ImmutableList<MemoryModuleType<?>> MEMORY_TYPES =
            ImmutableList.of(
                    AquamiraeMemoryModules.REST_POSITION.get(),
                    AquamiraeMemoryModules.REST_COOLDOWN_TICKS.get(),
                    AquamiraeMemoryModules.IS_SITTING.get(),
                    AquamiraeMemoryModules.GOLDEN_MOTH_PLAYER.get(),
                    MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
                    MemoryModuleType.WALK_TARGET,
                    MemoryModuleType.PATH);

    public static NearestRestPositionSensor restPositionSensor() {
        return new NearestRestPositionSensor(64, state -> state.is(AquamiraeBlocks.WISTERIA_NIVEIS.get()));
    }

    public static Brain.Provider<GoldenMoth> brainProvider() {
        return Brain.provider(MEMORY_TYPES, SENSOR_TYPES);
    }

    protected static Brain<GoldenMoth> makeBrain(Brain<GoldenMoth> brain) {
        initCoreActivity(brain);
        initIdleActivity(brain);
        initRestActivity(brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.useDefaultActivity();
        return brain;
    }

    private static void initCoreActivity(Brain<GoldenMoth> brain) {
        brain.addActivity(Activity.CORE, 0, ImmutableList.of(
                new Swim(1F),
                new MoveToTargetSink(),
                new CountDownCooldownTicks(AquamiraeMemoryModules.REST_COOLDOWN_TICKS.get()),
                PushGround.create()));
    }

    private static void initIdleActivity(Brain<GoldenMoth> brain) {
        brain.addActivity(Activity.IDLE, ImmutableList.of(
                Pair.of(0, new RunOne<>(
                        ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT),
                        ImmutableList.of(
                                Pair.of(BehaviorBuilder.triggerIf(GoldenMoth::shouldPanic,
                                        (OneShot<? super GoldenMoth>) RandomStroll.fly(2.0F)), 1),
                                Pair.of(BehaviorBuilder.triggerIf(Predicate.not(GoldenMoth::shouldPanic),
                                        (OneShot<? super GoldenMoth>) RandomStroll.fly(1.0F)), 1),
                                Pair.of(BehaviorBuilder.triggerIf(Predicate.not(GoldenMoth::shouldPanic),
                                        StartRest.create()), 1))))));
    }

    private static void initRestActivity(Brain<GoldenMoth> brain) {
        brain.addActivity(Activity.REST, 0, ImmutableList.of(new Rest(), StopRest.create()));
    }

    private static class Rest extends Behavior<GoldenMoth> {

        public Rest() {
            super(ImmutableMap.of(
                            AquamiraeMemoryModules.REST_POSITION.get(), MemoryStatus.VALUE_PRESENT,
                            AquamiraeMemoryModules.GOLDEN_MOTH_PLAYER.get(), MemoryStatus.VALUE_ABSENT,
                            AquamiraeMemoryModules.REST_COOLDOWN_TICKS.get(), MemoryStatus.VALUE_ABSENT,
                            AquamiraeMemoryModules.IS_SITTING.get(), MemoryStatus.REGISTERED,
                            MemoryModuleType.WALK_TARGET, MemoryStatus.REGISTERED),
                    Integer.MAX_VALUE);
        }

        @Override
        protected void tick(ServerLevel level, GoldenMoth moth, long time) {
            final var brain = moth.getBrain();
            final var restPosition = brain.getMemory(AquamiraeMemoryModules.REST_POSITION.get()).orElse(BlockPos.ZERO);
            if (moth.blockPosition().distSqr(restPosition) <= 6) {
                brain.setMemory(AquamiraeMemoryModules.IS_SITTING.get(), true);
                brain.eraseMemory(MemoryModuleType.WALK_TARGET);
                moth.addDeltaMovement(new Vec3(0, -0.01f, 0));
                moth.hurtMarked = true;
                if (moth.getRandom().nextInt(1000) <= 1)
                    brain.setMemory(AquamiraeMemoryModules.REST_COOLDOWN_TICKS.get(),
                            moth.getRandom().nextInt(600, 1200));
            } else if (brain.checkMemory(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT)) {
                brain.setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(restPosition, 1f, 2));
            }
        }

        @Override
        protected void stop(ServerLevel level, GoldenMoth moth, long time) {
            moth.getBrain().eraseMemory(AquamiraeMemoryModules.IS_SITTING.get());
        }

        @Override
        protected boolean canStillUse(ServerLevel level, GoldenMoth moth, long time) {
            return this.hasRequiredMemories(moth);
        }
    }

    private static class PushGround {

        private static OneShot<GoldenMoth> create() {
            return BehaviorBuilder.triggerIf(moth -> moth.onGround() && !moth.isInsideJar(),
                    BehaviorBuilder.create(instance -> instance
                            .group(instance.absent(AquamiraeMemoryModules.IS_SITTING.get()))
                            .apply(instance, (isSitting) -> (level, moth, time) -> {
                                moth.addDeltaMovement(new Vec3(0, 0.1f, 0));
                                moth.hurtMarked = true;
                                return true;
                            })));
        }
    }

    private static class StartRest {

        private static OneShot<GoldenMoth> create() {
            return BehaviorBuilder.create(instance -> instance
                    .group(instance.present(AquamiraeMemoryModules.REST_POSITION.get()),
                            instance.absent(AquamiraeMemoryModules.GOLDEN_MOTH_PLAYER.get()),
                            instance.absent(AquamiraeMemoryModules.REST_COOLDOWN_TICKS.get()))
                    .apply(instance, (restPosition, player, restCooldown) -> (level, moth, time) -> {
                        final var brain = moth.getBrain();
                        brain.setActiveActivityIfPossible(Activity.REST);
                        return true;
                    }));
        }
    }

    private static class StopRest {

        private static OneShot<GoldenMoth> create() {
            return BehaviorBuilder.create(instance -> instance
                    .group(instance.registered(AquamiraeMemoryModules.REST_POSITION.get()),
                            instance.registered(AquamiraeMemoryModules.GOLDEN_MOTH_PLAYER.get()),
                            instance.registered(AquamiraeMemoryModules.REST_COOLDOWN_TICKS.get()))
                    .apply(instance, (restPosition, player, restCooldown) -> (level, moth, time) -> {
                        if (instance.tryGet(restPosition).isPresent()
                                && instance.tryGet(player).isEmpty()
                                && instance.tryGet(restCooldown).isEmpty()) return false;
                        final var brain = moth.getBrain();
                        brain.setActiveActivityIfPossible(Activity.IDLE);
                        return true;
                    }));
        }
    }
}