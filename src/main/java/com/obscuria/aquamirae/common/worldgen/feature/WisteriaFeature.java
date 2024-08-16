
package com.obscuria.aquamirae.common.worldgen.feature;

import com.obscuria.aquamirae.common.block.WisteriaNiveisBlock;
import com.obscuria.aquamirae.registry.AquamiraeBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class WisteriaFeature extends Feature<NoneFeatureConfiguration> {

	public WisteriaFeature() {
		super(NoneFeatureConfiguration.CODEC);
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
		var anyPlaced = false;
		final var level = context.level();
		final var count = context.random().nextInt(4, 16);
		for (var i = 0; i <= count; i++) {
			final var x = (int) (context.origin().getX() + context.random().triangle(0, 6));
			final var z = (int) (context.origin().getZ() + context.random().triangle(0, 6));
			final var pos = new BlockPos(x, level.getHeight(Heightmap.Types.WORLD_SURFACE_WG, x, z), z);
			if (!WisteriaNiveisBlock.canBePlacedOn(level, pos.below())) continue;
			WisteriaNiveisBlock.placeAt(level, this.wisteria(), pos, Block.UPDATE_ALL);
			anyPlaced = true;
		}
		return anyPlaced;
	}

	private BlockState wisteria() {
		return AquamiraeBlocks.WISTERIA_NIVEIS.get().defaultBlockState()
				.setValue(WisteriaNiveisBlock.NATURAL, true);
	}
}
