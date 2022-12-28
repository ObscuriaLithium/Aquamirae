
package com.obscuria.aquamirae.registry;

import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.world.features.OxygeliumFeature;
import com.obscuria.aquamirae.world.features.WisteriaFeature;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.Features;
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
		if (event.getName() != null && AquamiraeMod.ICE_MAZE.contains(event.getName())) {
			event.getGeneration().getFeatures(GenerationStage.Decoration.LOCAL_MODIFICATIONS).add(() -> WisteriaFeature.CONFIGURED_FEATURE);
			event.getGeneration().getFeatures(GenerationStage.Decoration.RAW_GENERATION).add(() -> OxygeliumFeature.CONFIGURED_FEATURE);
			event.getGeneration().getFeatures(GenerationStage.Decoration.RAW_GENERATION).add(() -> Features.KELP_COLD);
			event.getGeneration().getFeatures(GenerationStage.Decoration.RAW_GENERATION).add(() -> Features.SEAGRASS_DEEP_COLD);
			event.getGeneration().getFeatures(GenerationStage.Decoration.RAW_GENERATION).add(() -> Features.SEAGRASS_NORMAL);
		}
	}
}
