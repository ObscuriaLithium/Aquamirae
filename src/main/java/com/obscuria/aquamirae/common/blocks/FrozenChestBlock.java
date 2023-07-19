
package com.obscuria.aquamirae.common.blocks;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import com.obscuria.aquamirae.registry.AquamiraeParticles;
import com.obscuria.aquamirae.registry.AquamiraeSounds;
import net.minecraft.block.*;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

@SuppressWarnings("deprecation")
public class FrozenChestBlock extends Block implements Waterloggable {
	public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
	public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
	private static final VoxelShape SHAPE = createCuboidShape(0.9, 0, 0.9, 15.1, 14.1, 15.1);

	public FrozenChestBlock() {
		super(Settings.create()
				.mapColor(MapColor.BROWN)
				.sounds(BlockSoundGroup.WOOD)
				.strength(-1, 3600000)
				.nonOpaque()
				.dropsNothing()
				.pistonBehavior(PistonBehavior.BLOCK)
				.solidBlock((a, b, c) -> false));
		setDefaultState(getStateManager().getDefaultState().with(FACING, Direction.NORTH).with(WATERLOGGED, false));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(FACING, WATERLOGGED);
	}

	public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return SHAPE;
	}

	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return SHAPE;
	}

	@Override
	public BlockState rotate(BlockState state, BlockRotation rotation) {
		return state.with(FACING, rotation.rotate(state.get(FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, BlockMirror mirror) {
		return state.rotate(mirror.getRotation(state.get(FACING)));
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		final boolean inWater = ctx.getWorld().getFluidState(ctx.getBlockPos()).getFluid() == Fluids.WATER;
		return getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite()).with(WATERLOGGED, inWater);
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		if (state.get(WATERLOGGED)) world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
		return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		final ItemStack stack = player.getStackInHand(hand);
		if (stack.getItem() == AquamiraeItems.FROZEN_KEY) {
			stack.decrement(1);
			final BlockState chest = Blocks.CHEST.getDefaultState().with(FACING, state.get(FACING));
			world.setBlockState(pos, chest, 3);
			LootableContainerBlockEntity.setLootTable(world, player.getRandom(), pos, new Identifier(Aquamirae.MODID, "chests/frozen_chest"));
			world.playSound(player, pos, AquamiraeSounds.BLOCK_FROZEN_CHEST_UNLOCK, SoundCategory.BLOCKS, 1f, 1f);
			for(int i = 0; i < 12; ++i) {
				double d0 = pos.getX() - 0.1D + 1.2D * player.getRandom().nextDouble();
				double d1 = pos.getY() - 0.1D + 1.2D * player.getRandom().nextDouble();
				double d2 = pos.getZ() - 0.1D + 1.2D * player.getRandom().nextDouble();
				world.addParticle(ParticleTypes.CLOUD, d0, d1, d2, 0, 0.01, 0);
			}
			for(int i = 0; i < 12; ++i) {
				double d0 = pos.getX() - 0.2D + 1.4D * player.getRandom().nextDouble();
				double d1 = pos.getY() - 0.2D + 1.4D * player.getRandom().nextDouble();
				double d2 = pos.getZ() - 0.2D + 1.4D * player.getRandom().nextDouble();
				world.addParticle(AquamiraeParticles.SHINE, d0, d1, d2, 0, 0.05, 0);
			}
			return ActionResult.SUCCESS;
		}
		return ActionResult.FAIL;
	}
}
