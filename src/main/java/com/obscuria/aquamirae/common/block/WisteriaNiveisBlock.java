
package com.obscuria.aquamirae.common.block;

import com.obscuria.aquamirae.registry.AquamiraeBlocks;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

public class WisteriaNiveisBlock extends DoublePlantBlock implements BonemealableBlock {
	public static final BooleanProperty NATURAL = BooleanProperty.create("natural");

	public WisteriaNiveisBlock() {
		super(Properties.of()
				.noCollission().mapColor(MapColor.PLANT)
				.sound(SoundType.WEEPING_VINES).strength(1f, 10f)
				.isRedstoneConductor((state, level, pos) -> false)
				.offsetType(OffsetType.XYZ));
		this.registerDefaultState(this.stateDefinition.any().setValue(NATURAL, true));
	}

	public static void placeAt(LevelAccessor level, BlockState state, BlockPos pos, int update) {
		final var above = pos.above();
		level.setBlock(pos, copyWaterloggedFrom(level, pos, state.setValue(HALF, DoubleBlockHalf.LOWER)), update);
		level.setBlock(above, copyWaterloggedFrom(level, above, state.setValue(HALF, DoubleBlockHalf.UPPER)), update);
	}

	@SuppressWarnings("all")
	public static boolean canBePlacedOn(BlockGetter level, BlockPos pos) {
		final var instance = (WisteriaNiveisBlock) AquamiraeBlocks.WISTERIA_NIVEIS.get();
		return instance.mayPlaceOn(level.getBlockState(pos), level, pos)
				&& level.getBlockState(pos.above(1)).isAir()
				&& level.getBlockState(pos.above(2)).isAir();
	}

	@Override
	public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
		return true;
	}

	@Override
	public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState State) {
		return random.nextFloat() <= 0.1f;
	}

	@Override
	public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
		popResource(level, pos, AquamiraeItems.WISTERIA_NIVEIS.get().getDefaultInstance());
	}

	@Override
	public ItemStack getCloneItemStack(BlockState state, HitResult target, LevelReader world, BlockPos pos, Player player) {
		return AquamiraeItems.WISTERIA_NIVEIS.get().getDefaultInstance();
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(NATURAL);
	}

	@Override
	protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
		return state.isFaceSturdy(level, pos, Direction.UP)
				&& (state.is(BlockTags.SNOW) || super.mayPlaceOn(state, level, pos));
	}
}
