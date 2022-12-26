
package com.obscuria.aquamirae.registry;

import com.obscuria.aquamirae.client.particle.GhostParticle;
import com.obscuria.aquamirae.client.particle.GhostShineParticle;
import com.obscuria.aquamirae.client.particle.ShineParticle;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class AquamiraeParticles {
	@SubscribeEvent
	public static void registerParticles(ParticleFactoryRegisterEvent event) {
		Minecraft.getInstance().particleEngine.register(AquamiraeParticleTypes.SHINE.get(), ShineParticle.Factory::new);
		Minecraft.getInstance().particleEngine.register(AquamiraeParticleTypes.GHOST_SHINE.get(), GhostShineParticle.Factory::new);
		Minecraft.getInstance().particleEngine.register(AquamiraeParticleTypes.GHOST.get(), GhostParticle.Factory::new);
	}
}
