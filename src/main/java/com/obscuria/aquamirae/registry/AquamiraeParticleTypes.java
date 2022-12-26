
package com.obscuria.aquamirae.registry;

import com.obscuria.aquamirae.AquamiraeMod;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class AquamiraeParticleTypes {
	public static final DeferredRegister<ParticleType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, AquamiraeMod.MODID);
	public static final RegistryObject<BasicParticleType> SHINE = REGISTRY.register("shine", () -> new BasicParticleType(true));
	public static final RegistryObject<BasicParticleType> GHOST_SHINE = REGISTRY.register("ghost_shine", () -> new BasicParticleType(true));
	public static final RegistryObject<BasicParticleType> GHOST = REGISTRY.register("ghost", () -> new BasicParticleType(true));
}
