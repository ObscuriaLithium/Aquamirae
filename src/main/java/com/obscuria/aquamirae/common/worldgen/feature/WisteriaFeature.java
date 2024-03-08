
package com.obscuria.aquamirae.common.worldgen.feature;

import com.obscuria.aquamirae.common.block.WisteriaNiveisBlock;
import com.obscuria.aquamirae.registry.AquamiraeBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
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
		final var count = context.random().nextInt(4, 12);
		for (int i = 0; i <= count; i++) {
			final var x = (int) (context.origin().getX() + context.random().triangle(0, 5));
			final var z = (int) (context.origin().getZ() + context.random().triangle(0, 5));
			final var pos = new BlockPos(x, context.level().getHeight(Heightmap.Types.WORLD_SURFACE_WG, x, z), z);
			if (((WisteriaNiveisBlock) AquamiraeBlocks.WISTERIA_NIVEIS.get()).canBePlacedOn(context.level(), pos.below())) {
				context.level().setBlock(pos, AquamiraeBlocks.WISTERIA_NIVEIS.get().defaultBlockState()
						.setValue(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER), 3);
				context.level().setBlock(pos.above(), AquamiraeBlocks.WISTERIA_NIVEIS.get().defaultBlockState()
						.setValue(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.UPPER), 3);
				anyPlaced = true;
			}
		}
		return anyPlaced;
	}
}
