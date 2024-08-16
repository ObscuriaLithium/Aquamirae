
package com.obscuria.aquamirae.registry;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.common.worldgen.feature.ShipwreckChestProcessor;
import com.obscuria.core.registry.RegistryHandler;
import com.obscuria.core.registry.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.NonExtendable
public interface AquamiraeStructureProcessors {
	RegistryHandler<StructureProcessorType<?>> HANDLER = RegistryHandler.create(Registries.STRUCTURE_PROCESSOR, Aquamirae.MODID);

	RegistrySupplier<StructureProcessorType<ShipwreckChestProcessor>> SHIPWRECK_CHEST =
			simple("chest_loot_table", () -> ShipwreckChestProcessor.CODEC);

	@SuppressWarnings("all")
	private static <T extends StructureProcessor> RegistrySupplier<StructureProcessorType<T>>
	simple(String key, StructureProcessorType<T> type) {
		return HANDLER.register(key, () -> type);
	}
}
