
package com.obscuria.aquamirae.common.blocks;

import com.obscuria.aquamirae.registry.AquamiraeBlocks;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import net.minecraft.block.*;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("deprecation")
public class WisteriaNiveisBlock extends TallPlantBlock {
	public static final BooleanProperty LOOT = BooleanProperty.of("loot");

	public WisteriaNiveisBlock() {
		super(Settings.create()
				.mapColor(MapColor.PALE_GREEN)
				.sounds(BlockSoundGroup.WEEPING_VINES)
				.strength(1f, 10f)
				.noCollision()
				.solidBlock((a, b, c) -> false)
				.offset(OffsetType.XYZ));
		setDefaultState(getStateManager().getDefaultState().with(LOOT, true));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		super.appendProperties(builder);
		builder.add(LOOT);
	}

	@Override
	public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
		return AquamiraeItems.WISTERIA_NIVEIS.getDefaultStack();
	}

	@Override
	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		if (state.get(HALF) != DoubleBlockHalf.UPPER) {
			return world.getBlockState(pos.down()).isIn(BlockTags.SNOW);
		} else {
			final BlockState lower = world.getBlockState(pos.down());
			return lower.isOf(this) && lower.get(HALF) == DoubleBlockHalf.LOWER;
		}
	}

	public static boolean canGenerateAt(StructureWorldAccess world, BlockPos pos) {
		final BlockState state = world.getBlockState(pos);
		return state.isSideSolid(world, pos, Direction.UP, SideShapeType.FULL) && state.isIn(BlockTags.SNOW)
				&& world.isAir(pos.up(1)) && world.isAir(pos.up(2));
	}

	public static boolean canGenerateAt(World world, BlockPos pos) {
		final BlockState state = world.getBlockState(pos);
		return state.isSideSolid(world, pos, Direction.UP, SideShapeType.FULL) && state.isIn(BlockTags.SNOW)
				&& world.isAir(pos.up(1)) && world.isAir(pos.up(2));
	}

	@Override
	public List<ItemStack> getDroppedStacks(BlockState state, LootContextParameterSet.Builder builder) {
		return state.get(HALF) == DoubleBlockHalf.LOWER ? state.get(LOOT) ? super.getDroppedStacks(state, builder)
				: List.of(AquamiraeItems.WISTERIA_NIVEIS.getDefaultStack()) : new ArrayList<>();
	}

	public static boolean tryPlace(World world, BlockPos pos, boolean natural) {
		if (!canGenerateAt(world, pos)) return false;
		world.setBlockState(pos.up(1), AquamiraeBlocks.WISTERIA_NIVEIS.getDefaultState().with(HALF, DoubleBlockHalf.LOWER).with(LOOT, natural), 3);
		world.setBlockState(pos.up(2), AquamiraeBlocks.WISTERIA_NIVEIS.getDefaultState().with(HALF, DoubleBlockHalf.UPPER).with(LOOT, natural), 3);
		return true;
	}
}
