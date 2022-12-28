
package com.obscuria.aquamirae.world.events.features;

import com.obscuria.aquamirae.registry.AquamiraeBlocks;
import com.obscuria.aquamirae.world.blocks.WisteriaNiveisBlock;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;

import javax.annotation.Nonnull;
import java.util.Random;

public class WisteriaFeature extends Feature<NoFeatureConfig> {

	public static WisteriaFeature FEATURE = new WisteriaFeature();
	public static ConfiguredFeature<?, ?> CONFIGURED_FEATURE = FEATURE.configured(IFeatureConfig.NONE).decorated(Placement.NOPE.configured(IPlacementConfig.NONE));

	public WisteriaFeature() {
		super(NoFeatureConfig.CODEC);
	}

	public static WisteriaFeature feature() {
		return FEATURE;
	}

	@Override
	public boolean place(@Nonnull ISeedReader seedReader,@Nonnull ChunkGenerator chunkGenerator,@Nonnull Random random,@Nonnull BlockPos origin,@Nonnull NoFeatureConfig config) {
		if (random.nextInt(4) != 1) return false;
		boolean placed = false;
		final int count = 4 + random.nextInt(8);
		for (int i = 0; i <= count; i++) {
			final int x = origin.getX() - 5 + random.nextInt(10);
			final int z = origin.getZ() - 5 + random.nextInt(10);
			final BlockPos pos = new BlockPos(x, seedReader.getHeight(Heightmap.Type.WORLD_SURFACE_WG, x, z), z);
			if (((WisteriaNiveisBlock) AquamiraeBlocks.WISTERIA_NIVEIS.get()).canBePlacedOn(seedReader, pos.below())) {
				seedReader.setBlock(pos, AquamiraeBlocks.WISTERIA_NIVEIS.get().defaultBlockState()
						.setValue(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER), 3);
				seedReader.setBlock(pos.above(), AquamiraeBlocks.WISTERIA_NIVEIS.get().defaultBlockState()
						.setValue(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.UPPER), 3);
				placed = true;
			}
		}
		return placed;
	}
}
