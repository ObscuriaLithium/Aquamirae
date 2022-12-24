
package com.obscuria.aquamirae.world.features;

import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.registry.AquamiraeBlocks;
import com.obscuria.aquamirae.world.blocks.WisteriaNiveisBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.List;

public class WisteriaFeature extends Feature<NoneFeatureConfiguration> {

	public static WisteriaFeature FEATURE = null;
	public static Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> CONFIGURED_FEATURE = null;
	public static Holder<PlacedFeature> PLACED_FEATURE = null;
	public WisteriaFeature() {
		super(NoneFeatureConfiguration.CODEC);
	}

	public static Feature<?> feature() {
		FEATURE = new WisteriaFeature();
		CONFIGURED_FEATURE = FeatureUtils.register("aquamirae:wisteria", FEATURE, FeatureConfiguration.NONE);
		PLACED_FEATURE = PlacementUtils.register("aquamirae:wisteria", CONFIGURED_FEATURE, List.of());
		return FEATURE;
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
		if (context.random().nextInt(1, 4) != 1) return false;
		boolean placed = false;
		final int count = context.random().nextInt(4, 12);
		for (int i = 0; i <= count; i++) {
			final int x = (int) (context.origin().getX() + context.random().nextDouble(-5, 5));
			final int z = (int) (context.origin().getZ() + context.random().nextDouble(-5, 5));
			final BlockPos pos = new BlockPos(x, context.level().getHeight(Heightmap.Types.WORLD_SURFACE_WG, x, z), z);
			if (((WisteriaNiveisBlock) AquamiraeBlocks.WISTERIA_NIVEIS.get()).canBePlacedOn(context.level(), pos.below())) {
				context.level().setBlock(pos, AquamiraeBlocks.WISTERIA_NIVEIS.get().defaultBlockState()
						.setValue(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER), 3);
				context.level().setBlock(pos.above(), AquamiraeBlocks.WISTERIA_NIVEIS.get().defaultBlockState()
						.setValue(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.UPPER), 3);
				placed = true;
			}
		}
		return placed;
	}
}
