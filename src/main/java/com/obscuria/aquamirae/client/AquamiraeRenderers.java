
package com.obscuria.aquamirae.client;

import com.obscuria.aquamirae.client.renderers.*;
import com.obscuria.aquamirae.registry.AquamiraeEntities;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.api.distmarker.Dist;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class AquamiraeRenderers {

	@SubscribeEvent
	public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(AquamiraeEntities.GOLDEN_MOTH.get(), GoldenMothRenderer::new);
		event.registerEntityRenderer(AquamiraeEntities.MAW.get(), MawRenderer::new);
		event.registerEntityRenderer(AquamiraeEntities.ANGLERFISH.get(), AnglerfishRenderer::new);
		event.registerEntityRenderer(AquamiraeEntities.MAZE_MOTHER.get(), MazeMotherRenderer::new);
		event.registerEntityRenderer(AquamiraeEntities.CAPTAIN_CORNELIA.get(), CaptainCorneliaRenderer::new);
		event.registerEntityRenderer(AquamiraeEntities.PILLAGERS_PATROL.get(), PillagersPatrolRenderer::new);
		event.registerEntityRenderer(AquamiraeEntities.TORTURED_SOUL.get(), TorturedSoulRenderer::new);
		event.registerEntityRenderer(AquamiraeEntities.EEL.get(), EelRenderer::new);
		event.registerEntityRenderer(AquamiraeEntities.MAZE_ROSE.get(), MazeRoseRenderer::new);
		event.registerEntityRenderer(AquamiraeEntities.POISONED_CHAKRA.get(), PoisonedChakraRenderer::new);
		event.registerEntityRenderer(AquamiraeEntities.SPINEFISH.get(), SpinefishRenderer::new);
		event.registerEntityRenderer(AquamiraeEntities.LUMINOUS_JELLY.get(), LuminousJellyRenderer::new);
	}
}
