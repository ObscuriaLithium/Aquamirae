
package com.obscuria.aquamirae.registry;

import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.world.features.OxygeliumFeature;
import com.obscuria.aquamirae.world.features.WisteriaFeature;
import net.minecraft.data.worldgen.placement.AquaticPlacements;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber
public class AquamiraeFeatures {
	public static final DeferredRegister<Feature<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.FEATURES, AquamiraeMod.MODID);

	public static final RegistryObject<Feature<?>> OXYGELIUM = REGISTRY.register("oxygelium", OxygeliumFeature::feature);
	public static final RegistryObject<Feature<?>> WISTERIA = REGISTRY.register("wisteria", WisteriaFeature::feature);

	@SubscribeEvent
	public static void addFeaturesToBiomes(BiomeLoadingEvent event) {
		if (event.getName() != null && event.getName().equals(AquamiraeMod.BIOME)) {
			event.getGeneration().getFeatures(GenerationStage.Decoration.LOCAL_MODIFICATIONS).add(WisteriaFeature.PLACED_FEATURE);
			event.getGeneration().getFeatures(GenerationStage.Decoration.RAW_GENERATION).add(OxygeliumFeature.PLACED_FEATURE);
			event.getGeneration().getFeatures(GenerationStage.Decoration.RAW_GENERATION).add(AquaticPlacements.KELP_COLD);
			event.getGeneration().getFeatures(GenerationStage.Decoration.RAW_GENERATION).add(AquaticPlacements.SEAGRASS_DEEP_COLD);
			event.getGeneration().getFeatures(GenerationStage.Decoration.RAW_GENERATION).add(AquaticPlacements.SEAGRASS_NORMAL);
		}
	}
}
