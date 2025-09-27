
package com.obscuria.aquamirae.registry;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.common.worldgen.feature.OxygeliumFeature;
import com.obscuria.aquamirae.common.worldgen.feature.WisteriaFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AquamiraeFeatures {
	public static final DeferredRegister<Feature<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.FEATURES, Aquamirae.MODID);

	public static final RegistryObject<OxygeliumFeature> OXYGELIUM = REGISTRY.register("oxygelium", () -> new OxygeliumFeature(NoneFeatureConfiguration.CODEC));
	public static final RegistryObject<WisteriaFeature> WISTERIA = REGISTRY.register("wisteria", () -> new WisteriaFeature(NoneFeatureConfiguration.CODEC));
}
