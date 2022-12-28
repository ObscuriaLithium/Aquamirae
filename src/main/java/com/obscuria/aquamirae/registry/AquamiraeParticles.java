
package com.obscuria.aquamirae.registry;

import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.client.particle.GhostParticle;
import com.obscuria.aquamirae.client.particle.GhostShineParticle;
import com.obscuria.aquamirae.client.particle.ShineParticle;
import net.minecraft.client.Minecraft;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class AquamiraeParticles {
	public static final DeferredRegister<ParticleType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, AquamiraeMod.MODID);
	public static final RegistryObject<BasicParticleType> SHINE = REGISTRY.register("shine", () -> new BasicParticleType(true));
	public static final RegistryObject<BasicParticleType> GHOST_SHINE = REGISTRY.register("ghost_shine", () -> new BasicParticleType(true));
	public static final RegistryObject<BasicParticleType> GHOST = REGISTRY.register("ghost", () -> new BasicParticleType(true));

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void registerParticle(ParticleFactoryRegisterEvent event) {
		Minecraft.getInstance().particleEngine.register(SHINE.get(), ShineParticle.Factory::new);
		Minecraft.getInstance().particleEngine.register(GHOST_SHINE.get(), GhostShineParticle.Factory::new);
		Minecraft.getInstance().particleEngine.register(GHOST.get(), GhostParticle.Factory::new);
	}

}
