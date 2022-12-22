
package com.obscuria.aquamirae.registry;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.api.distmarker.Dist;

import com.obscuria.aquamirae.client.particle.ShineParticle;
import com.obscuria.aquamirae.client.particle.GhostShineParticle;
import com.obscuria.aquamirae.client.particle.GhostParticle;
import com.obscuria.aquamirae.client.particle.ElectricParticle;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class AquamiraeParticles {
	@SubscribeEvent
	public static void registerParticles(RegisterParticleProvidersEvent event) {
		event.register(AquamiraeParticleTypes.SHINE.get(), ShineParticle::provider);
		event.register(AquamiraeParticleTypes.GHOST_SHINE.get(), GhostShineParticle::provider);
		event.register(AquamiraeParticleTypes.GHOST.get(), GhostParticle::provider);
		event.register(AquamiraeParticleTypes.ELECTRIC.get(), ElectricParticle::provider);
	}
}
