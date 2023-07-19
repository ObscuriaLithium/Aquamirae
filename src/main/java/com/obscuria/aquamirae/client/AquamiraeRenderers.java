
package com.obscuria.aquamirae.client;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.client.models.armor.ModelAbyssalArmor;
import com.obscuria.aquamirae.client.models.armor.ModelTerribleArmor;
import com.obscuria.aquamirae.client.models.armor.ModelThreeBoltArmor;
import com.obscuria.aquamirae.client.particles.ElectricParticle;
import com.obscuria.aquamirae.client.particles.GhostParticle;
import com.obscuria.aquamirae.client.particles.GhostShineParticle;
import com.obscuria.aquamirae.client.particles.ShineParticle;
import com.obscuria.aquamirae.client.renderers.*;
import com.obscuria.aquamirae.common.entities.PillagersPatrolSpawner;
import com.obscuria.aquamirae.registry.AquamiraeBlocks;
import com.obscuria.aquamirae.registry.AquamiraeEntities;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import com.obscuria.aquamirae.registry.AquamiraeParticles;
import com.obscuria.obscureapi.api.BossBarsRenderRegistry;
import com.obscuria.obscureapi.api.utils.RenderUtils;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.ClientBossBar;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.SlimeEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public final class AquamiraeRenderers {
	private static final Identifier CORNELIA_BOSS_BAR = Aquamirae.key("textures/gui/bossbars/cornelia.png");

	public static void register() {
		EntityRendererRegistry.register(AquamiraeEntities.GOLDEN_MOTH, GoldenMothRenderer::new);
		EntityRendererRegistry.register(AquamiraeEntities.MAW, MawRenderer::new);
		EntityRendererRegistry.register(AquamiraeEntities.ANGLERFISH, AnglerfishRenderer::new);
		EntityRendererRegistry.register(AquamiraeEntities.MAZE_MOTHER, MazeMotherRenderer::new);
		EntityRendererRegistry.register(AquamiraeEntities.CAPTAIN_CORNELIA, CaptainCorneliaRenderer::new);
		EntityRendererRegistry.register(AquamiraeEntities.TORTURED_SOUL, TorturedSoulRenderer::new);
		EntityRendererRegistry.register(AquamiraeEntities.EEL, EelRenderer::new);
		EntityRendererRegistry.register(AquamiraeEntities.MAZE_ROSE, MazeRoseRenderer::new);
		EntityRendererRegistry.register(AquamiraeEntities.POISONED_CHAKRA, PoisonedChakraRenderer::new);
		EntityRendererRegistry.register(AquamiraeEntities.SPINEFISH, SpinefishRenderer::new);
		EntityRendererRegistry.register(AquamiraeEntities.PILLAGERS_PATROL, AquamiraeRenderers::patrolRenderer);

		BlockRenderLayerMap.INSTANCE.putBlock(AquamiraeBlocks.GOLDEN_MOTH_IN_A_JAR, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(AquamiraeBlocks.FROZEN_CHEST, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(AquamiraeBlocks.LUMINESCENT_BUBBLE, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(AquamiraeBlocks.LUMINESCENT_LAMP, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(AquamiraeBlocks.OXYGELIUM, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(AquamiraeBlocks.ELODEA, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(AquamiraeBlocks.WISTERIA_NIVEIS, RenderLayer.getCutout());

		ParticleFactoryRegistry.getInstance().register(AquamiraeParticles.SHINE, ShineParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(AquamiraeParticles.GHOST_SHINE, GhostShineParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(AquamiraeParticles.GHOST, GhostParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(AquamiraeParticles.ELECTRIC, ElectricParticle.Factory::new);

		ArmorRenderer.register(AquamiraeRenderers::renderTerribleArmor,
				AquamiraeItems.TERRIBLE_HELMET,
				AquamiraeItems.TERRIBLE_CHESTPLATE,
				AquamiraeItems.TERRIBLE_LEGGINGS,
				AquamiraeItems.TERRIBLE_BOOTS);
		ArmorRenderer.register(AquamiraeRenderers::renderAbyssalArmor,
				AquamiraeItems.ABYSSAL_HEAUME,
				AquamiraeItems.ABYSSAL_TIARA,
				AquamiraeItems.ABYSSAL_BRIGANTINE,
				AquamiraeItems.ABYSSAL_LEGGINGS,
				AquamiraeItems.ABYSSAL_BOOTS);
		ArmorRenderer.register(AquamiraeRenderers::renderThreeBoltArmor,
				AquamiraeItems.THREE_BOLT_HELMET,
				AquamiraeItems.THREE_BOLT_SUIT,
				AquamiraeItems.THREE_BOLT_LEGGINGS,
				AquamiraeItems.THREE_BOLT_BOOTS);

		BossBarsRenderRegistry.register("entity.aquamirae.captain_cornelia", false, true, true,
				BossBarsRenderRegistry.DEFAULT_INCREMENT, AquamiraeRenderers::renderCorneliaBossbar);
	}

	private static void renderTerribleArmor(MatrixStack matrices, VertexConsumerProvider vertexConsumers, ItemStack stack, LivingEntity entity, EquipmentSlot slot, int light, BipedEntityModel<LivingEntity> contextModel) {
		final BipedEntityModel<LivingEntity> model = ModelTerribleArmor.createArmorModel(stack);
		final Identifier texture = ModelTerribleArmor.getTexture(stack);
		renderArmor(model, texture, matrices, vertexConsumers, stack, light, contextModel);
	}

	private static void renderAbyssalArmor(MatrixStack matrices, VertexConsumerProvider vertexConsumers, ItemStack stack, LivingEntity entity, EquipmentSlot slot, int light, BipedEntityModel<LivingEntity> contextModel) {
		final BipedEntityModel<LivingEntity> model = ModelAbyssalArmor.createArmorModel(stack);
		final Identifier texture = ModelAbyssalArmor.getTexture(stack);
		renderArmor(model, texture, matrices, vertexConsumers, stack, light, contextModel);
	}

	private static void renderThreeBoltArmor(MatrixStack matrices, VertexConsumerProvider vertexConsumers, ItemStack stack, LivingEntity entity, EquipmentSlot slot, int light, BipedEntityModel<LivingEntity> contextModel) {
		final BipedEntityModel<LivingEntity> model = ModelThreeBoltArmor.createArmorModel(stack);
		final Identifier texture = ModelThreeBoltArmor.getTexture(stack);
		renderArmor(model, texture, matrices, vertexConsumers, stack, light, contextModel);
	}

	private static void renderArmor(BipedEntityModel<LivingEntity> model, Identifier texture, MatrixStack matrices, VertexConsumerProvider vertexConsumers, ItemStack stack, int light, BipedEntityModel<LivingEntity> contextModel) {
		contextModel.copyBipedStateTo(model);
		VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getArmorCutoutNoCull(texture));
		model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1F, 1F, 1F, 1F);
		if (stack.hasGlint()) model.render(matrices, vertexConsumers.getBuffer(RenderLayer.getArmorEntityGlint()), light, OverlayTexture.DEFAULT_UV, 1F, 1F, 1F, 1F);
	}

	private static void renderCorneliaBossbar(MinecraftClient client, DrawContext context, int x, int y, ClientBossBar bossBar, Text name) {
		RenderUtils.screen(CORNELIA_BOSS_BAR, () -> context.drawTexture(CORNELIA_BOSS_BAR, x - 37, y - 20, 0, 0, 256, 64, 256, 64));
	}

	private static MobEntityRenderer<PillagersPatrolSpawner, EntityModel<PillagersPatrolSpawner>> patrolRenderer(EntityRendererFactory.Context factory) {
		return new MobEntityRenderer<>(factory, new SlimeEntityModel<>(factory.getPart(EntityModelLayers.SLIME)) {
			@Override
			public void render(MatrixStack pose, VertexConsumer consumer, int i1, int i2, float f1, float f2, float f3, float f4) {
			}
		}, 0F) {
			@Override
			public Identifier getTexture(PillagersPatrolSpawner entity) {
				return new Identifier(Aquamirae.MODID, "textures/entity/blank.png");
			}
		};
	}
}
