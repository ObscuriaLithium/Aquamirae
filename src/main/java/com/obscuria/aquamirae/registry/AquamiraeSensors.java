
package com.obscuria.aquamirae.registry;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.common.entity.GoldenMothAI;
import com.obscuria.aquamirae.common.entity.ai.NearestRestPositionSensor;
import com.obscuria.aquamirae.common.entity.ai.GoldenMothPlayerSensor;
import com.obscuria.core.registry.RegistryHandler;
import com.obscuria.core.registry.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import org.jetbrains.annotations.ApiStatus;

import java.util.function.Supplier;

@SuppressWarnings("unused")
@ApiStatus.NonExtendable
public interface AquamiraeSensors {
	RegistryHandler<SensorType<?>> HANDLER = RegistryHandler.create(Registries.SENSOR_TYPE, Aquamirae.MODID);

	RegistrySupplier<SensorType<NearestRestPositionSensor>> GOLDEN_MOTH_REST_POSITION =
			simple("golden_moth_rest_position", GoldenMothAI::restPositionSensor);
	RegistrySupplier<SensorType<GoldenMothPlayerSensor>> GOLDEN_MOTH_PLAYER =
			simple("golden_moth_player", GoldenMothPlayerSensor::new);

	private static <V extends Sensor<?>> RegistrySupplier<SensorType<V>> simple(String key, Supplier<V> supplier) {
		return HANDLER.register(key, () -> new SensorType<>(supplier));
	}
}
