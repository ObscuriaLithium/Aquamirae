
package com.obscuria.aquamirae.registry;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.common.worldgen.feature.IceSpiralFeature;
import com.obscuria.aquamirae.common.worldgen.feature.OxygeliumFeature;
import com.obscuria.aquamirae.common.worldgen.feature.WisteriaFeature;
import com.obscuria.core.api.registry.RegistryHandler;
import com.obscuria.core.api.registry.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import org.jetbrains.annotations.ApiStatus;

import java.util.function.Supplier;

@SuppressWarnings("unused")
@ApiStatus.NonExtendable
public interface AquamiraeFeatures {
	RegistryHandler<Feature<?>> HANDLER = RegistryHandler.create(Registries.FEATURE, Aquamirae.MODID);

	RegistrySupplier<OxygeliumFeature> OXYGELIUM = simple("oxygelium", OxygeliumFeature::new);
	RegistrySupplier<WisteriaFeature> WISTERIA = simple("wisteria", WisteriaFeature::new);
	RegistrySupplier<IceSpiralFeature> ICE_SPIRAL = simple("ice_spiral", IceSpiralFeature::new);

	private static <F extends Feature<?>> RegistrySupplier<F> simple(String key, Supplier<F> supplier) {
		return HANDLER.register(key, supplier);
	}
}
