
package com.obscuria.aquamirae.registry;

import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.world.blocks.*;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

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
}
