package com.obscuria.aquamirae.common.entity.ai;

import com.obscuria.aquamirae.registry.AquamiraeMemoryModules;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Set;
import java.util.function.Predicate;

public class NearestRestPositionSensor extends Sensor<LivingEntity> {
    private static final int SCAN_RATE = 60;
    private static final int SCAN_RADIUS = 9;
    private final Predicate<BlockState> predicate;
    private final int maxDistanceSqr;

    public NearestRestPositionSensor(int maxDistance, Predicate<BlockState> predicate) {
        super(SCAN_RATE);
        this.predicate = predicate;
        this.maxDistanceSqr = maxDistance * maxDistance;
    }

    @Override
    public Set<MemoryModuleType<?>> requires() {
        return Set.of(AquamiraeMemoryModules.REST_POSITION.get());
    }

    @Override
    protected void doTick(ServerLevel level, LivingEntity entity) {
        entity.getBrain().getMemory(AquamiraeMemoryModules.REST_POSITION.get())
                .ifPresentOrElse(
                        pos -> this.validateMemory(level, entity, pos),
                        () -> this.searchForBlock(level, entity));
    }

    private void validateMemory(ServerLevel level, LivingEntity entity, BlockPos memory) {
        if (level.isLoaded(memory)
                && entity.blockPosition().distSqr(memory) <= maxDistanceSqr
                && predicate.test(level.getBlockState(memory))) return;
        entity.getBrain().eraseMemory(AquamiraeMemoryModules.REST_POSITION.get());
    }

    private void searchForBlock(ServerLevel level, LivingEntity entity) {
        final var origin = entity.blockPosition();
        for (var x = -SCAN_RADIUS; x <= SCAN_RADIUS; x++)
            for (var y = -SCAN_RADIUS; y <= SCAN_RADIUS; y++)
                for (var z = -SCAN_RADIUS; z <= SCAN_RADIUS; z++) {
                    final var target = origin.offset(x, y, z);
                    if (!level.isLoaded(target) || !predicate.test(level.getBlockState(target))) continue;
                    entity.getBrain().setMemory(AquamiraeMemoryModules.REST_POSITION.get(), target);
                    return;
                }
    }
}
