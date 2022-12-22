
package com.obscuria.aquamirae.registry;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.particles.ParticleType;

import com.obscuria.aquamirae.AquamiraeMod;

public class AquamiraeParticleTypes {
	public static final DeferredRegister<ParticleType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, AquamiraeMod.MODID);
	public static final RegistryObject<SimpleParticleType> SHINE = REGISTRY.register("shine", () -> new SimpleParticleType(true));
	public static final RegistryObject<SimpleParticleType> GHOST_SHINE = REGISTRY.register("ghost_shine", () -> new SimpleParticleType(true));
	public static final RegistryObject<SimpleParticleType> GHOST = REGISTRY.register("ghost", () -> new SimpleParticleType(true));
	public static final RegistryObject<SimpleParticleType> ELECTRIC = REGISTRY.register("electric", () -> new SimpleParticleType(true));
}
