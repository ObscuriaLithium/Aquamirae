
package com.obscuria.aquamirae.world.blocks;

import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.SeaGrassBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SeagrassBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.storage.loot.LootContext;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ElodeaBlock extends SeaGrassBlock implements IWaterLoggable {
	public ElodeaBlock() {
		super(BlockBehaviour.Properties.of(Material.WATER_PLANT, MaterialColor.TERRACOTTA_CYAN).sound(SoundType.WET_GRASS).strength(0.4f, 0.5f)
				.noCollission().speedFactor(0.7f).jumpFactor(0.7f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
		this.registerDefaultState(this.stateDefinition.any().setValue(BlockStateProperties.WATERLOGGED, false));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(BlockStateProperties.WATERLOGGED);
	}

	@Override
	public @NotNull List<ItemStack> getDrops(@NotNull BlockState state, LootContext.@NotNull Builder builder) {
		return Collections.singletonList(new ItemStack(this, 1));
	}

	@Override
	public boolean isValidBonemealTarget(@NotNull BlockGetter blockGetter, @NotNull BlockPos pos, @NotNull BlockState state, boolean flag) {
		return false;
	}

	public boolean isBonemealSuccess(@NotNull Level level, @NotNull Random random, @NotNull BlockPos pos, @NotNull BlockState state) {
		return false;
	}
}
