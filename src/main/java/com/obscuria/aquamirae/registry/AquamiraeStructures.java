
package com.obscuria.aquamirae.registry;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.common.features.IceMazeFeature;
import com.obscuria.aquamirae.common.features.OutpostFeature;
import com.obscuria.aquamirae.common.features.ShelterFeature;
import com.obscuria.aquamirae.common.features.ShipFeature;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;

public class AquamiraeStructures {
	public static final DeferredRegister<Structure<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, AquamiraeMod.MODID);
	public static final RegistryObject<Structure<NoFeatureConfig>> ICE_MAZE = REGISTRY.register("ice_maze", IceMazeFeature::new);
	public static final RegistryObject<Structure<NoFeatureConfig>> SHIP = REGISTRY.register("ship", ShipFeature::new);
	public static final RegistryObject<Structure<NoFeatureConfig>> OUTPOST = REGISTRY.register("outpost", OutpostFeature::new);
	public static final RegistryObject<Structure<NoFeatureConfig>> SHELTER = REGISTRY.register("shelter", ShelterFeature::new);

	public static void register() {
		registerSpacing(ICE_MAZE.get(), new StructureSeparationSettings(2, 0, 1191766080 ), false);
		registerSpacing(SHIP.get(), new StructureSeparationSettings(25, 10, 1391766081 ), false);
		registerSpacing(OUTPOST.get(), new StructureSeparationSettings(18, 10, 1291764181 ), false);
		registerSpacing(SHELTER.get(), new StructureSeparationSettings(40, 20, 1492356481 ), true);
	}

	private static <F extends Structure<?>> void registerSpacing(F structure, StructureSeparationSettings structureSeparationSettings, boolean transformSurroundingLand) {
		if (structure.getRegistryName() == null) return;

		Structure.STRUCTURES_REGISTRY.put(structure.getRegistryName().toString(), structure);

		if (transformSurroundingLand) {
			Structure.NOISE_AFFECTING_FEATURES = ImmutableList.<Structure<?>>builder()
					.addAll(Structure.NOISE_AFFECTING_FEATURES).add(structure).build();
		}

		DimensionStructuresSettings.DEFAULTS = ImmutableMap.<Structure<?>, StructureSeparationSettings>builder()
				.putAll(DimensionStructuresSettings.DEFAULTS).put(structure, structureSeparationSettings).build();

		WorldGenRegistries.NOISE_GENERATOR_SETTINGS.entrySet().forEach(settings -> {
			Map<Structure<?>, StructureSeparationSettings> structureMap = settings.getValue().structureSettings().structureConfig();

			if (structureMap instanceof ImmutableMap) {
				Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(structureMap);
				tempMap.put(structure, structureSeparationSettings);
				settings.getValue().structureSettings().structureConfig = tempMap;
			} else {
				structureMap.put(structure, structureSeparationSettings);
			}
		});
	}
}
