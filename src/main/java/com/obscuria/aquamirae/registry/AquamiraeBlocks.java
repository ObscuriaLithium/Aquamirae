
package com.obscuria.aquamirae.registry;

import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.common.blocks.*;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class AquamiraeBlocks {
	public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, AquamiraeMod.MODID);
	public static final RegistryObject<Block> PAINTING_ANGLERFISH = REGISTRY.register("painting_anglerfish", CollectiblePaintingBlock::new);
	public static final RegistryObject<Block> PAINTING_OXYGELIUM = REGISTRY.register("painting_oxygelium", CollectiblePaintingBlock::new);
	public static final RegistryObject<Block> PAINTING_TORTURED_SOUL = REGISTRY.register("painting_tortured_soul", CollectiblePaintingBlock::new);
	public static final RegistryObject<Block> PAINTING_AURORA = REGISTRY.register("painting_aurora", CollectiblePaintingBlock::new);
	public static final RegistryObject<Block> GOLDEN_MOTH_IN_A_JAR = REGISTRY.register("golden_moth_in_a_jar", JarBlock::new);
	public static final RegistryObject<Block> FROZEN_CHEST = REGISTRY.register("frozen_chest", FrozenChestBlock::new);
	public static final RegistryObject<Block> LUMINESCENT_LAMP = REGISTRY.register("luminescent_lamp", LuminescentLampBlock::new);
	public static final RegistryObject<Block> ELODEA = REGISTRY.register("elodea", ElodeaBlock::new);
	public static final RegistryObject<Block> OXYGELIUM = REGISTRY.register("oxygelium", OxygeliumBlock::new);
	public static final RegistryObject<Block> LUMINESCENT_BUBBLE = REGISTRY.register("luminescent_bubble", LuminescentBubbleBlock::new);
	public static final RegistryObject<Block> WISTERIA_NIVEIS = REGISTRY.register("wisteria_niveis", WisteriaNiveisBlock::new);

	@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
	public static class Client {
		@SubscribeEvent
		public static void clientSetup(FMLClientSetupEvent event) {
			RenderTypeLookup.setRenderLayer(AquamiraeBlocks.GOLDEN_MOTH_IN_A_JAR.get(), renderType -> renderType == RenderType.cutoutMipped());
			RenderTypeLookup.setRenderLayer(AquamiraeBlocks.FROZEN_CHEST.get(), renderType -> renderType == RenderType.cutoutMipped());
			RenderTypeLookup.setRenderLayer(AquamiraeBlocks.LUMINESCENT_LAMP.get(), renderType -> renderType == RenderType.cutoutMipped());
			RenderTypeLookup.setRenderLayer(AquamiraeBlocks.LUMINESCENT_BUBBLE.get(), renderType -> renderType == RenderType.cutoutMipped());
			RenderTypeLookup.setRenderLayer(AquamiraeBlocks.OXYGELIUM.get(), renderType -> renderType == RenderType.cutoutMipped());
			RenderTypeLookup.setRenderLayer(AquamiraeBlocks.ELODEA.get(), renderType -> renderType == RenderType.cutoutMipped());
			RenderTypeLookup.setRenderLayer(AquamiraeBlocks.WISTERIA_NIVEIS.get(), renderType -> renderType == RenderType.cutoutMipped());
		}
	}
}
