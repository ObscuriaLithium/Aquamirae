
package com.obscuria.aquamirae.client;

import com.obscuria.aquamirae.client.renderers.*;
import com.obscuria.aquamirae.registry.AquamiraeEntities;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class AquamiraeRenderers {

	@SubscribeEvent
	public static void register(ModelRegistryEvent event) {
		RenderingRegistry.registerEntityRenderingHandler(AquamiraeEntities.ANGLERFISH.get(), AnglerfishRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(AquamiraeEntities.GOLDEN_MOTH.get(), GoldenMothRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(AquamiraeEntities.MAW.get(), MawRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(AquamiraeEntities.MAZE_MOTHER.get(), MazeMotherRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(AquamiraeEntities.CAPTAIN_CORNELIA.get(), CaptainCorneliaRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(AquamiraeEntities.TORTURED_SOUL.get(), TorturedSoulRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(AquamiraeEntities.EEL.get(), EelRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(AquamiraeEntities.MAZE_ROSE.get(), MazeRoseRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(AquamiraeEntities.POISONED_CHAKRA.get(), PoisonedChakraRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(AquamiraeEntities.SPINEFISH.get(), SpinefishRenderer::new);
	}
}
