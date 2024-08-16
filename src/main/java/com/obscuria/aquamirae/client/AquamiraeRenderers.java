
package com.obscuria.aquamirae.client;

import com.obscuria.aquamirae.client.particle.ElectricParticle;
import com.obscuria.aquamirae.client.particle.GhostParticle;
import com.obscuria.aquamirae.client.particle.GhostShineParticle;
import com.obscuria.aquamirae.client.particle.ShineParticle;
import com.obscuria.aquamirae.client.renderer.*;
import com.obscuria.aquamirae.client.renderer.projectile.AbyssalArrowRenderer;
import com.obscuria.aquamirae.client.renderer.projectile.AbyssalShardRenderer;
import com.obscuria.aquamirae.client.renderer.projectile.IceShardRenderer;
import com.obscuria.aquamirae.client.renderer.projectile.SpineArrowRenderer;
import com.obscuria.aquamirae.registry.AquamiraeEntityTypes;
import com.obscuria.aquamirae.registry.AquamiraeParticleTypes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import org.jetbrains.annotations.ApiStatus;

@OnlyIn(Dist.CLIENT)
@ApiStatus.NonExtendable
public interface AquamiraeRenderers {

	static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(AquamiraeEntityTypes.GOLDEN_MOTH.get(), GoldenMothRenderer::new);
		event.registerEntityRenderer(AquamiraeEntityTypes.MAW.get(), MawRenderer::new);
		event.registerEntityRenderer(AquamiraeEntityTypes.ANGLERFISH.get(), AnglerfishRenderer::new);
		event.registerEntityRenderer(AquamiraeEntityTypes.MAZE_MOTHER.get(), MazeMotherRenderer::new);
		event.registerEntityRenderer(AquamiraeEntityTypes.CAPTAIN_CORNELIA.get(), CaptainCorneliaRenderer::new);
		event.registerEntityRenderer(AquamiraeEntityTypes.TORTURED_SOUL.get(), TorturedSoulRenderer::new);
		event.registerEntityRenderer(AquamiraeEntityTypes.EEL.get(), EelRenderer::new);
		event.registerEntityRenderer(AquamiraeEntityTypes.MAZE_ROSE.get(), MazeRoseRenderer::new);
		event.registerEntityRenderer(AquamiraeEntityTypes.POISONED_CHAKRA.get(), PoisonedChakraRenderer::new);
		event.registerEntityRenderer(AquamiraeEntityTypes.SPINEFISH.get(), SpinefishRenderer::new);
		event.registerEntityRenderer(AquamiraeEntityTypes.PYCNOGONIDA.get(), PycnogonidaRenderer::new);
		event.registerEntityRenderer(AquamiraeEntityTypes.CURSED_MOTH.get(), CursedMothRenderer::new);
		event.registerEntityRenderer(AquamiraeEntityTypes.STORM_CHAKRAM.get(), StormChakramRenderer::new);
		event.registerEntityRenderer(AquamiraeEntityTypes.PILLAGERS_PATROL.get(), PillagersPatrolRenderer::new);
		event.registerEntityRenderer(AquamiraeEntityTypes.SHADOW_MERCHANT.get(), ShadowMerchantRenderer::new);
		event.registerEntityRenderer(AquamiraeEntityTypes.SPINE_ARROW.get(), SpineArrowRenderer::new);
		event.registerEntityRenderer(AquamiraeEntityTypes.ABYSSAL_ARROW.get(), AbyssalArrowRenderer::new);
		event.registerEntityRenderer(AquamiraeEntityTypes.ICE_SHARD.get(), IceShardRenderer::new);
		event.registerEntityRenderer(AquamiraeEntityTypes.ABYSSAL_SHARD.get(), AbyssalShardRenderer::new);
	}

	static void registerParticles(RegisterParticleProvidersEvent event) {
		event.registerSpriteSet(AquamiraeParticleTypes.SHINE.get(), ShineParticle.Provider::new);
		event.registerSpriteSet(AquamiraeParticleTypes.GHOST_SHINE.get(), GhostShineParticle.Provider::new);
		event.registerSpriteSet(AquamiraeParticleTypes.GHOST.get(), GhostParticle.Provider::new);
		event.registerSpriteSet(AquamiraeParticleTypes.ELECTRIC.get(), ElectricParticle.Providers::new);
	}
}
