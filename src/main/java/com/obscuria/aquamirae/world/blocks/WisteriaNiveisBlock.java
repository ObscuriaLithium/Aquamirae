
package com.obscuria.aquamirae.world.blocks;

import com.obscuria.aquamirae.registry.AquamiraeBlocks;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WisteriaNiveisBlock extends DoublePlantBlock {

	public static final BooleanProperty LOOT = BooleanProperty.create("loot");

	public WisteriaNiveisBlock() {
		super(Properties.of(Material.LEAVES).sound(SoundType.WEEPING_VINES).strength(1f, 10f).noCollission()
				.isRedstoneConductor((bs, br, bp) -> false));
		this.registerDefaultState(this.stateDefinition.any().setValue(LOOT, true));
	}

	@Override
	@Nonnull
	public OffsetType getOffsetType() {
		return OffsetType.XYZ;
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(LOOT);
	}

	@Override
	@Nonnull
	public ItemStack getCloneItemStack(@Nonnull IBlockReader world,@Nonnull BlockPos pos,@Nonnull BlockState state) {
		return AquamiraeItems.WISTERIA_NIVEIS.get().getDefaultInstance();
	}

	@Override
	protected boolean mayPlaceOn(@Nonnull BlockState state, @Nonnull IBlockReader level, @Nonnull BlockPos pos) {
		return state.is(Blocks.SNOW_BLOCK) || super.mayPlaceOn(state, level, pos);
	}

	public boolean canBePlacedOn(World level, BlockPos pos) {
		return mayPlaceOn(level.getBlockState(pos), level, pos) && level.isEmptyBlock(pos.above(1)) && level.isEmptyBlock(pos.above(2));
	}

	public boolean canBePlacedOn(IWorldReader level, BlockPos pos) {
		return mayPlaceOn(level.getBlockState(pos), level, pos) && level.isEmptyBlock(pos.above(1)) && level.isEmptyBlock(pos.above(2));
	}

	@Override
	public @Nonnull List<ItemStack> getDrops(@Nonnull BlockState state,@Nonnull LootContext.Builder builder) {
		return state.getValue(HALF) == DoubleBlockHalf.LOWER ? state.getValue(LOOT) ? super.getDrops(state, builder)
				: Collections.singletonList(AquamiraeItems.WISTERIA_NIVEIS.get().getDefaultInstance()) : new ArrayList<>();
	}

	@Override
	public void onPlace(@Nonnull BlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull BlockState oldState, boolean moving) {
		if (state.getValue(HALF) == DoubleBlockHalf.LOWER && world.isEmptyBlock(pos.above()))
			world.setBlock(pos.above(), AquamiraeBlocks.WISTERIA_NIVEIS.get().defaultBlockState().setValue(HALF, DoubleBlockHalf.UPPER).setValue(LOOT, false), 3);
	}
}
