
package com.obscuria.aquamirae.common.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.MapColor;
import net.minecraft.block.SeagrassBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

import java.util.Collections;
import java.util.List;

@SuppressWarnings("deprecation")
public class ElodeaBlock extends SeagrassBlock {
	public ElodeaBlock() {
		super(Settings.create()
				.mapColor(MapColor.BLUE)
				.sounds(BlockSoundGroup.WET_GRASS)
				.strength(0.4f, 0.5f)
				.noCollision()
				.nonOpaque()
				.velocityMultiplier(0.7f)
				.jumpVelocityMultiplier(0.7f)
				.solidBlock((a, b, c) -> false));
	}

	@Override
	public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state, boolean isClient) {
		return false;
	}

	@Override
	public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
		return false;
	}

	@Override
	public List<ItemStack> getDroppedStacks(BlockState state, LootContextParameterSet.Builder builder) {
		return Collections.singletonList(new ItemStack(this, 1));
	}
}
