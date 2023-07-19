
package com.obscuria.aquamirae.common.worldgen.feature;

import com.mojang.serialization.Codec;
import com.obscuria.aquamirae.common.blocks.WisteriaNiveisBlock;
import com.obscuria.aquamirae.registry.AquamiraeBlocks;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class WisteriaFeature extends Feature<DefaultFeatureConfig> {
	public WisteriaFeature(Codec<DefaultFeatureConfig> codec) {
		super(codec);
	}

	@Override
	public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
		boolean placed = false;
		final int count = context.getRandom().nextBetween(4, 12);
		for (int i = 0; i <= count; i++) {
			final int x = (int) (context.getOrigin().getX() + context.getRandom().nextTriangular(0, 5));
			final int z = (int) (context.getOrigin().getZ() + context.getRandom().nextTriangular(0, 5));
			final BlockPos pos = new BlockPos(x, context.getWorld().getTopY(Heightmap.Type.WORLD_SURFACE_WG, x, z), z);
			if (WisteriaNiveisBlock.canGenerateAt(context.getWorld(), pos.down())) {
				context.getWorld().setBlockState(pos, AquamiraeBlocks.WISTERIA_NIVEIS.getDefaultState()
						.with(Properties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER), 3);
				context.getWorld().setBlockState(pos.up(), AquamiraeBlocks.WISTERIA_NIVEIS.getDefaultState()
						.with(Properties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.UPPER), 3);
				placed = true;
			}
		}
		return placed;
	}
}
