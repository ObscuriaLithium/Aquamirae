
package com.obscuria.aquamirae.registry;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.core.api.registry.RegistryHandler;
import com.obscuria.core.api.registry.RegistrySupplier;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.NonExtendable
public interface AquamiraeParticles {
	RegistryHandler<ParticleType<?>> HANDLER = RegistryHandler.create(Registries.PARTICLE_TYPE, Aquamirae.MODID);

	RegistrySupplier<SimpleParticleType> SHINE = simple("shine");
	RegistrySupplier<SimpleParticleType> GHOST_SHINE = simple("ghost_shine");
	RegistrySupplier<SimpleParticleType> GHOST = simple("ghost");
	RegistrySupplier<SimpleParticleType> ELECTRIC = simple("electric");

	private static RegistrySupplier<SimpleParticleType> simple(String key) {
		return HANDLER.register(key, () -> new SimpleParticleType(true));
	}
}
