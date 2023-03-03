
package com.obscuria.aquamirae.common.blocks;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ElodeaBlock extends SeaGrassBlock implements IWaterLoggable {
	public ElodeaBlock() {
		super(AbstractBlock.Properties.of(Material.WATER_PLANT, MaterialColor.TERRACOTTA_CYAN).sound(SoundType.WET_GRASS).strength(0.4f, 0.5f)
				.noCollission().speedFactor(0.7f).jumpFactor(0.7f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
		this.registerDefaultState(this.stateDefinition.any().setValue(BlockStateProperties.WATERLOGGED, false));
	}

	@Override
	protected void createBlockStateDefinition(@Nonnull StateContainer.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(BlockStateProperties.WATERLOGGED);
	}

	@Override
	public @Nonnull List<ItemStack> getDrops(@Nonnull BlockState state, @Nonnull LootContext.Builder builder) {
		return Collections.singletonList(new ItemStack(this, 1));
	}

	@Override
	public boolean isValidBonemealTarget(@Nonnull IBlockReader blockGetter, @Nonnull BlockPos pos, @Nonnull BlockState state, boolean flag) {
		return false;
	}

	public boolean isBonemealSuccess(@Nonnull World level, @Nonnull Random random, @Nonnull BlockPos pos, @Nonnull BlockState state) {
		return false;
	}
}
