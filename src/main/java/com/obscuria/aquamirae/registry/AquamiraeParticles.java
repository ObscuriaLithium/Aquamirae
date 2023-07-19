
package com.obscuria.aquamirae.registry;

import com.obscuria.aquamirae.Aquamirae;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public interface AquamiraeParticles {
	DefaultParticleType SHINE = FabricParticleTypes.simple(true);
	DefaultParticleType GHOST_SHINE = FabricParticleTypes.simple(true);
	DefaultParticleType GHOST = FabricParticleTypes.simple(true);
	DefaultParticleType ELECTRIC = FabricParticleTypes.simple(true);

	static void register() {
		Registry.register(Registries.PARTICLE_TYPE, Aquamirae.key("shine"), SHINE);
		Registry.register(Registries.PARTICLE_TYPE, Aquamirae.key("ghost_shine"), GHOST_SHINE);
		Registry.register(Registries.PARTICLE_TYPE, Aquamirae.key("ghost"), GHOST);
		Registry.register(Registries.PARTICLE_TYPE, Aquamirae.key("electric"), ELECTRIC);
	}
}
