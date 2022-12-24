
package com.obscuria.aquamirae.registry;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.api.distmarker.Dist;

import com.obscuria.aquamirae.client.particle.ShineParticle;
import com.obscuria.aquamirae.client.particle.GhostShineParticle;
import com.obscuria.aquamirae.client.particle.GhostParticle;
import com.obscuria.aquamirae.client.particle.ElectricParticle;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class AquamiraeParticles {
	@SubscribeEvent
	public static void registerParticles(ParticleFactoryRegisterEvent event) {
		Minecraft.getInstance().particleEngine.register(AquamiraeParticleTypes.SHINE.get(), ShineParticle::provider);
		Minecraft.getInstance().particleEngine.register(AquamiraeParticleTypes.GHOST_SHINE.get(), GhostShineParticle::provider);
		Minecraft.getInstance().particleEngine.register(AquamiraeParticleTypes.GHOST.get(), GhostParticle::provider);
		Minecraft.getInstance().particleEngine.register(AquamiraeParticleTypes.ELECTRIC.get(), ElectricParticle::provider);
	}
}
