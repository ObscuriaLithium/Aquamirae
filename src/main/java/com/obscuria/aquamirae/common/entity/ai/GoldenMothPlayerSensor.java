package com.obscuria.aquamirae.common.entity.ai;

import com.google.common.collect.ImmutableSet;
import com.obscuria.aquamirae.registry.AquamiraeMemoryModules;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;

import java.util.Set;

public class GoldenMothPlayerSensor extends Sensor<LivingEntity> {

    @Override
    public Set<MemoryModuleType<?>> requires() {
        return ImmutableSet.of(
                AquamiraeMemoryModules.GOLDEN_MOTH_PLAYER.get(),
                AquamiraeMemoryModules.REST_COOLDOWN_TICKS.get(),
                MemoryModuleType.WALK_TARGET);
    }

    @Override
    protected void doTick(ServerLevel level, LivingEntity entity) {
        final var module = AquamiraeMemoryModules.GOLDEN_MOTH_PLAYER.get();
        final var brain = entity.getBrain();
        final var memory = level.players().stream()
                .filter(EntitySelector.NO_CREATIVE_OR_SPECTATOR)
                .filter(player -> !player.isCrouching())
                .filter(player -> player.closerThan(entity, 16))
                .findFirst();
        if (!brain.hasMemoryValue(module) && memory.isPresent()) {
            brain.eraseMemory(MemoryModuleType.WALK_TARGET);
            brain.setMemory(AquamiraeMemoryModules.REST_COOLDOWN_TICKS.get(), 600);
        }
        brain.setMemory(module, memory);
    }
}