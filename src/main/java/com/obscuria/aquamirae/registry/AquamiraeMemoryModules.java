
package com.obscuria.aquamirae.registry;

import com.mojang.serialization.Codec;
import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.core.registry.RegistryHandler;
import com.obscuria.core.registry.RegistrySupplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.ApiStatus;

import java.util.Optional;

@SuppressWarnings("unused")
@ApiStatus.NonExtendable
public interface AquamiraeMemoryModules {
	RegistryHandler<MemoryModuleType<?>> HANDLER = RegistryHandler.create(Registries.MEMORY_MODULE_TYPE, Aquamirae.MODID);

	RegistrySupplier<MemoryModuleType<BlockPos>> REST_POSITION = simple("rest_position", BlockPos.CODEC);
	RegistrySupplier<MemoryModuleType<Integer>> REST_COOLDOWN_TICKS = simple("rest_cooldown_ticks", Codec.INT);
	RegistrySupplier<MemoryModuleType<Boolean>> IS_SITTING = simple("is_sitting", Codec.BOOL);
	RegistrySupplier<MemoryModuleType<Player>> GOLDEN_MOTH_PLAYER = temp("golden_moth_player");

	private static <U> RegistrySupplier<MemoryModuleType<U>> simple(String key, Codec<U> codec) {
		return HANDLER.register(key, () -> new MemoryModuleType<>(Optional.of(codec)));
	}

	private static <U> RegistrySupplier<MemoryModuleType<U>> temp(String key) {
		return HANDLER.register(key, () -> new MemoryModuleType<>(Optional.empty()));
	}
}
