
package com.obscuria.aquamirae.registry;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.common.blocks.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public interface AquamiraeBlocks {
	CollectiblePaintingBlock PAINTING_ANGLERFISH = new CollectiblePaintingBlock();
	CollectiblePaintingBlock PAINTING_OXYGELIUM = new CollectiblePaintingBlock();
	CollectiblePaintingBlock PAINTING_TORTURED_SOUL = new CollectiblePaintingBlock();
	CollectiblePaintingBlock PAINTING_AURORA = new CollectiblePaintingBlock();
	JarBlock GOLDEN_MOTH_IN_A_JAR = new JarBlock();
	FrozenChestBlock FROZEN_CHEST = new FrozenChestBlock();
	LuminescentLampBlock LUMINESCENT_LAMP = new LuminescentLampBlock();
	ElodeaBlock ELODEA = new ElodeaBlock();
	OxygeliumBlock OXYGELIUM = new OxygeliumBlock();
	LuminescentBubbleBlock LUMINESCENT_BUBBLE = new LuminescentBubbleBlock();
	WisteriaNiveisBlock WISTERIA_NIVEIS = new WisteriaNiveisBlock();

	static void register() {
		Registry.register(Registries.BLOCK, Aquamirae.key("painting_anglerfish"), PAINTING_ANGLERFISH);
		Registry.register(Registries.BLOCK, Aquamirae.key("painting_oxygelium"), PAINTING_OXYGELIUM);
		Registry.register(Registries.BLOCK, Aquamirae.key("painting_tortured_soul"), PAINTING_TORTURED_SOUL);
		Registry.register(Registries.BLOCK, Aquamirae.key("painting_aurora"), PAINTING_AURORA);
		Registry.register(Registries.BLOCK, Aquamirae.key("golden_moth_in_a_jar"), GOLDEN_MOTH_IN_A_JAR);
		Registry.register(Registries.BLOCK, Aquamirae.key("frozen_chest"), FROZEN_CHEST);
		Registry.register(Registries.BLOCK, Aquamirae.key("luminescent_lamp"), LUMINESCENT_LAMP);
		Registry.register(Registries.BLOCK, Aquamirae.key("elodea"), ELODEA);
		Registry.register(Registries.BLOCK, Aquamirae.key("oxygelium"), OXYGELIUM);
		Registry.register(Registries.BLOCK, Aquamirae.key("luminescent_bubble"), LUMINESCENT_BUBBLE);
		Registry.register(Registries.BLOCK, Aquamirae.key("wisteria_niveis"), WISTERIA_NIVEIS);
	}
}
