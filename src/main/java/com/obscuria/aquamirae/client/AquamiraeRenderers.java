
package com.obscuria.aquamirae.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.client.renderers.*;
import com.obscuria.aquamirae.registry.AquamiraeEntities;
import com.obscuria.aquamirae.common.entities.PillagersPatrol;
import net.minecraft.client.model.SlimeModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class AquamiraeRenderers {

	@SubscribeEvent
	public static void registerEntityRenderers(EntityRenderersEvent.@NotNull RegisterRenderers event) {
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
		event.registerEntityRenderer(AquamiraeEntities.LUMINOUS_JELLY.get(), LuminousJellyRenderer::new);

		event.registerEntityRenderer(AquamiraeEntities.PILLAGERS_PATROL.get(),
				provider -> new MobRenderer<>(provider, new SlimeModel<>(provider.bakeLayer(ModelLayers.SLIME)) {
					@Override public void setupAnim(@NotNull PillagersPatrol entity, float f1, float f2, float f3, float f4, float f5) {}
					@Override public void renderToBuffer(@NotNull PoseStack pose, @NotNull VertexConsumer consumer, int i1, int i2, float f1, float f2, float f3, float f4) {}
				}, 0F) {
					@Override @NotNull public ResourceLocation getTextureLocation(@NotNull PillagersPatrol entity) {
						return new ResourceLocation(Aquamirae.MODID, "textures/entity/blank.png");
					}
				});
	}
}
