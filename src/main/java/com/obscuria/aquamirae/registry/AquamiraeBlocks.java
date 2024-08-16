
package com.obscuria.aquamirae.registry;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.common.block.*;
import com.obscuria.core.registry.RegistrySupplier;
import com.obscuria.core.registry.RegistryHandler;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.ApiStatus;

import java.util.function.Supplier;

@ApiStatus.NonExtendable
public interface AquamiraeBlocks {
	RegistryHandler<Block> HANDLER = RegistryHandler.create(Registries.BLOCK, Aquamirae.MODID);

	RegistrySupplier<Block> PAINTING_ANGLERFISH = simple("painting_anglerfish", CollectiblePaintingBlock::new);
	RegistrySupplier<Block> PAINTING_OXYGELIUM = simple("painting_oxygelium", CollectiblePaintingBlock::new);
	RegistrySupplier<Block> PAINTING_TORTURED_SOUL = simple("painting_tortured_soul", CollectiblePaintingBlock::new);
	RegistrySupplier<Block> PAINTING_AURORA = simple("painting_aurora", CollectiblePaintingBlock::new);
	RegistrySupplier<Block> FROZEN_CHEST = simple("frozen_chest", FrozenChestBlock::new);
	RegistrySupplier<Block> LUMINESCENT_LAMP = simple("luminescent_lamp", LuminescentLampBlock::new);
	RegistrySupplier<Block> ELODEA = simple("elodea", ElodeaBlock::new);
	RegistrySupplier<Block> OXYGELIUM = simple("oxygelium", OxygeliumBlock::new);
	RegistrySupplier<Block> LUMINESCENT_BUBBLE = simple("luminescent_bubble", LuminescentBubbleBlock::new);
	RegistrySupplier<Block> WISTERIA_NIVEIS = simple("wisteria_niveis", WisteriaNiveisBlock::new);
	RegistrySupplier<Block> GOLDEN_MOTH_IN_A_JAR = simple("golden_moth_in_a_jar",
			() -> new MothJarBlock(AquamiraeEntityTypes.GOLDEN_MOTH::get));
	RegistrySupplier<Block> CURSED_MOTH_IN_A_JAR = simple("cursed_moth_in_a_jar",
			() -> new MothJarBlock(AquamiraeEntityTypes.CURSED_MOTH::get));

	private static RegistrySupplier<Block> simple(String key, Supplier<Block> supplier) {
		return HANDLER.register(key, supplier);
	}
}
