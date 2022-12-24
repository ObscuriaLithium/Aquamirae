
package com.obscuria.aquamirae.registry;

import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.world.features.OxygeliumFeature;
import com.obscuria.aquamirae.world.features.WisteriaFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber
public class AquamiraeFeatures {
	public static final DeferredRegister<Feature<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.FEATURES, AquamiraeMod.MODID);

	public static final RegistryObject<Feature<?>> OXYGELIUM = REGISTRY.register("oxygelium", OxygeliumFeature::feature);
	public static final RegistryObject<Feature<?>> WISTERIA = REGISTRY.register("wisteria", WisteriaFeature::feature);
}
