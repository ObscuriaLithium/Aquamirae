
package com.obscuria.aquamirae.client;

import com.obscuria.aquamirae.client.particle.ElectricParticle;
import com.obscuria.aquamirae.client.particle.GhostParticle;
import com.obscuria.aquamirae.client.particle.GhostShineParticle;
import com.obscuria.aquamirae.client.particle.ShineParticle;
import com.obscuria.aquamirae.client.renderer.*;
import com.obscuria.aquamirae.registry.AquamiraeEntities;
import com.obscuria.aquamirae.registry.AquamiraeParticles;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.NonExtendable
public interface AquamiraeRenderers {

	static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(AquamiraeEntities.GOLDEN_MOTH.get(), GoldenMothRenderer::new);
		event.registerEntityRenderer(AquamiraeEntities.MAW.get(), MawRenderer::new);
		event.registerEntityRenderer(AquamiraeEntities.ANGLERFISH.get(), AnglerfishRenderer::new);
		event.registerEntityRenderer(AquamiraeEntities.MAZE_MOTHER.get(), MazeMotherRenderer::new);
		event.registerEntityRenderer(AquamiraeEntities.CAPTAIN_CORNELIA.get(), CaptainCorneliaRenderer::new);
		event.registerEntityRenderer(AquamiraeEntities.TORTURED_SOUL.get(), TorturedSoulRenderer::new);
		event.registerEntityRenderer(AquamiraeEntities.EEL.get(), EelRenderer::new);
		event.registerEntityRenderer(AquamiraeEntities.MAZE_ROSE.get(), MazeRoseRenderer::new);
		event.registerEntityRenderer(AquamiraeEntities.POISONED_CHAKRA.get(), PoisonedChakraRenderer::new);
		event.registerEntityRenderer(AquamiraeEntities.SPINEFISH.get(), SpinefishRenderer::new);
		event.registerEntityRenderer(AquamiraeEntities.PYCNOGONIDA.get(), PycnogonidaRenderer::new);
		event.registerEntityRenderer(AquamiraeEntities.CURSED_MOTH.get(), CursedMothRenderer::new);
		event.registerEntityRenderer(AquamiraeEntities.STORM_CHAKRAM.get(), StormChakramRenderer::new);
		event.registerEntityRenderer(AquamiraeEntities.PILLAGERS_PATROL.get(), PillagersPatrolRenderer::new);
	}

	static void registerParticles(RegisterParticleProvidersEvent event) {
		event.registerSpriteSet(AquamiraeParticles.SHINE.get(), ShineParticle::provider);
		event.registerSpriteSet(AquamiraeParticles.GHOST_SHINE.get(), GhostShineParticle::provider);
		event.registerSpriteSet(AquamiraeParticles.GHOST.get(), GhostParticle::provider);
		event.registerSpriteSet(AquamiraeParticles.ELECTRIC.get(), ElectricParticle::provider);
	}
}
