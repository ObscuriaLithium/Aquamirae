
package com.obscuria.aquamirae.world.blocks;

import com.obscuria.aquamirae.registry.AquamiraeBlocks;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class WisteriaNiveisBlock extends DoublePlantBlock {

	public static final BooleanProperty LOOT = BooleanProperty.create("loot");

	public WisteriaNiveisBlock() {
		super(Properties.of(Material.LEAVES).sound(SoundType.WEEPING_VINES).strength(1f, 10f).noCollission()
				.isRedstoneConductor((bs, br, bp) -> false));
		this.registerDefaultState(this.stateDefinition.any().setValue(LOOT, true));
	}

	@Override
	public @NotNull OffsetType getOffsetType() {
		return OffsetType.XYZ;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(LOOT);
	}

	@Override
	public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter world, BlockPos pos, Player player) {
		return AquamiraeItems.WISTERIA_NIVEIS.get().getDefaultInstance();
	}

	@Override
	protected boolean mayPlaceOn(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos) {
		return state.is(BlockTags.SNOW) || super.mayPlaceOn(state, level, pos);
	}

	public boolean canBePlacedOn(Level level, BlockPos pos) {
		return mayPlaceOn(level.getBlockState(pos), level, pos) && level.isEmptyBlock(pos.above(1)) && level.isEmptyBlock(pos.above(2));
	}

	public boolean canBePlacedOn(WorldGenLevel level, BlockPos pos) {
		return mayPlaceOn(level.getBlockState(pos), level, pos) && level.isEmptyBlock(pos.above(1)) && level.isEmptyBlock(pos.above(2));
	}

	@Override
	public @NotNull List<ItemStack> getDrops(@NotNull BlockState state, LootContext.@NotNull Builder builder) {
		return state.getValue(HALF) == DoubleBlockHalf.LOWER ? state.getValue(LOOT) ? super.getDrops(state, builder)
				: List.of(AquamiraeItems.WISTERIA_NIVEIS.get().getDefaultInstance()) : new ArrayList<>();
	}

	@Override
	public void onPlace(@NotNull BlockState state, @NotNull Level world, @NotNull BlockPos pos, @NotNull BlockState oldState, boolean moving) {
		if (state.getValue(HALF) == DoubleBlockHalf.LOWER && world.isEmptyBlock(pos.above()))
			world.setBlock(pos.above(), AquamiraeBlocks.WISTERIA_NIVEIS.get().defaultBlockState().setValue(HALF, DoubleBlockHalf.UPPER).setValue(LOOT, false), 3);
	}
}
