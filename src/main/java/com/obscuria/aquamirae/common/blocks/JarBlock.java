
package com.obscuria.aquamirae.common.blocks;

import com.obscuria.aquamirae.common.entities.GoldenMothEntity;
import com.obscuria.aquamirae.registry.AquamiraeEntities;
import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class JarBlock extends Block implements Waterloggable {
	public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
	private static final VoxelShape SHAPE;

	public JarBlock() {
		super(Settings.create()
				.mapColor(MapColor.GOLD)
				.sounds(BlockSoundGroup.GLASS)
				.strength(1f, 10f)
				.luminance(s -> 8)
				.nonOpaque()
				.solidBlock((a, b, c) -> false)
				.dropsNothing());
		setDefaultState(getStateManager().getDefaultState().with(WATERLOGGED, false));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(WATERLOGGED);
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return SHAPE;
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return SHAPE;
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		final boolean inWater = ctx.getWorld().getFluidState(ctx.getBlockPos()).getFluid() == Fluids.WATER;
		return getDefaultState().with(WATERLOGGED, inWater);
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
	public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
		super.onPlaced(world, pos, state, placer, stack);
		if (world instanceof ServerWorld server) {
			MobEntity moth = new GoldenMothEntity(AquamiraeEntities.GOLDEN_MOTH, server);
			moth.setPos(pos.getX() + 0.5, pos.getY() + 0.3, pos.getZ() + 0.5);
			moth.setPersistent();
			moth.initialize(server, world.getLocalDifficulty(pos), SpawnReason.MOB_SUMMONED, null, null);
			world.spawnEntity(moth);
		}
	}

	static {
		SHAPE = VoxelShapes.combine(
				VoxelShapes.union(createCuboidShape(3, 0, 3, 13, 12, 13),
						createCuboidShape(4, 12, 4, 12, 14, 12)),
				createCuboidShape(3.1, 0.1, 3.1, 12.9, 10.5, 12.9),
				BooleanBiFunction.ONLY_FIRST);

	}
}
