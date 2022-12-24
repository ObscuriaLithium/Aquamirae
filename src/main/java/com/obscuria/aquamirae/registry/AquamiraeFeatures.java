
package com.obscuria.aquamirae.registry;

import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.world.features.OxygeliumFeature;
import com.obscuria.aquamirae.world.features.WisteriaFeature;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber
public class AquamiraeFeatures {
	public static final DeferredRegister<Feature<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.FEATURES, AquamiraeMod.MODID);

	public static final RegistryObject<Feature<?>> OXYGELIUM = REGISTRY.register("oxygelium", OxygeliumFeature::feature);
	public static final RegistryObject<Feature<?>> WISTERIA = REGISTRY.register("wisteria", WisteriaFeature::feature);

	@SubscribeEvent
	public static void addFeaturesToBiomes(BiomeLoadingEvent event) {
		final Holder<Biome> BIOME = Holder.direct(ForgeRegistries.BIOMES.getValue(event.getName()));
		System.out.println(event.getName());
		if (BIOME.is(AquamiraeMod.ICE_MAZE)) {
			event.getGeneration().getFeatures(GenerationStep.Decoration.RAW_GENERATION).add(WisteriaFeature.PLACED_FEATURE);
			event.getGeneration().getFeatures(GenerationStep.Decoration.RAW_GENERATION).add(OxygeliumFeature.PLACED_FEATURE);
		}
	}
}
