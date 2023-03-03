
package com.obscuria.aquamirae.registry;

import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.common.features.OxygeliumFeature;
import com.obscuria.aquamirae.common.features.WisteriaFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class AquamiraeFeatures {
	public static final DeferredRegister<Feature<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.FEATURES, AquamiraeMod.MODID);

	public static final RegistryObject<Feature<?>> OXYGELIUM = REGISTRY.register("oxygelium", OxygeliumFeature::feature);
	public static final RegistryObject<Feature<?>> WISTERIA = REGISTRY.register("wisteria", WisteriaFeature::feature);
}
