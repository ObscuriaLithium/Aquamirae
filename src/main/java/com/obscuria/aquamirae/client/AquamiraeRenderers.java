
package com.obscuria.aquamirae.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.client.renderers.*;
import com.obscuria.aquamirae.registry.AquamiraeEntities;
import com.obscuria.aquamirae.common.entities.PillagersPatrol;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;

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

		RenderingRegistry.registerEntityRenderingHandler(AquamiraeEntities.PILLAGERS_PATROL.get(),
				manager -> new MobRenderer<PillagersPatrol, EntityModel<PillagersPatrol>>(manager, new EntityModel<PillagersPatrol>() {
					@Override public void setupAnim(@Nonnull PillagersPatrol entity, float f1, float f2, float f3, float f4, float f5) {}
					@Override public void renderToBuffer(@Nonnull MatrixStack pose,@Nonnull IVertexBuilder source, int i1, int i2, float f1, float f2, float f3, float f4) {}}, 0F) {
					@Override @Nonnull public ResourceLocation getTextureLocation(@Nonnull PillagersPatrol entity) {
						return new ResourceLocation(AquamiraeMod.MODID,"textures/entity/blank.png"); } });
	}
}
