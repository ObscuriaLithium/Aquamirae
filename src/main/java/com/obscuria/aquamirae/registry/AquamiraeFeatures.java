
package com.obscuria.aquamirae.registry;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.common.worldgen.feature.OxygeliumFeature;
import com.obscuria.aquamirae.common.worldgen.feature.WisteriaFeature;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.PlacedFeature;

public interface AquamiraeFeatures {
	OxygeliumFeature OXYGELIUM = new OxygeliumFeature(DefaultFeatureConfig.CODEC);
	WisteriaFeature WISTERIA = new WisteriaFeature(DefaultFeatureConfig.CODEC);

	RegistryKey<PlacedFeature> OXYGELIUM_PLACED_FEATURE = RegistryKey.of(RegistryKeys.PLACED_FEATURE, Aquamirae.key("ice_maze_oxygelium"));
	RegistryKey<PlacedFeature> WISTERIA_PLACED_FEATURE = RegistryKey.of(RegistryKeys.PLACED_FEATURE, Aquamirae.key("ice_maze_wisteria"));
	RegistryKey<PlacedFeature> KELP_PLACED_FEATURE = RegistryKey.of(RegistryKeys.PLACED_FEATURE, Aquamirae.key("ice_maze_kelp"));
	RegistryKey<PlacedFeature> SEAGRASS_SHORT_PLACED_FEATURE = RegistryKey.of(RegistryKeys.PLACED_FEATURE, Aquamirae.key("ice_maze_seagrass_short"));
	RegistryKey<PlacedFeature> SEAGRASS_TALL_PLACED_FEATURE = RegistryKey.of(RegistryKeys.PLACED_FEATURE, Aquamirae.key("ice_maze_seagrass_tall"));

	static void register() {
		Registry.register(Registries.FEATURE, Aquamirae.key("oxygelium"), OXYGELIUM);
		Registry.register(Registries.FEATURE, Aquamirae.key("wisteria"), WISTERIA);

		BiomeModifications.addFeature(AquamiraeFeatures::inIceMaze, GenerationStep.Feature.RAW_GENERATION, OXYGELIUM_PLACED_FEATURE);
		BiomeModifications.addFeature(AquamiraeFeatures::inIceMaze, GenerationStep.Feature.TOP_LAYER_MODIFICATION, WISTERIA_PLACED_FEATURE);
		BiomeModifications.addFeature(AquamiraeFeatures::inIceMaze, GenerationStep.Feature.SURFACE_STRUCTURES, KELP_PLACED_FEATURE);
		BiomeModifications.addFeature(AquamiraeFeatures::inIceMaze, GenerationStep.Feature.SURFACE_STRUCTURES, SEAGRASS_SHORT_PLACED_FEATURE);
		BiomeModifications.addFeature(AquamiraeFeatures::inIceMaze, GenerationStep.Feature.SURFACE_STRUCTURES, SEAGRASS_TALL_PLACED_FEATURE);
	}

	static boolean inIceMaze(BiomeSelectionContext context) {
		return context.getBiomeRegistryEntry().isIn(Aquamirae.ICE_MAZE);
	}
}
