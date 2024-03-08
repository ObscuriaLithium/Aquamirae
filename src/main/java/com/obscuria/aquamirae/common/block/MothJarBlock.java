
package com.obscuria.aquamirae.common.block;

import com.obscuria.aquamirae.common.entity.AbstractMoth;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.Supplier;

@SuppressWarnings("deprecation")
public class MothJarBlock extends Block implements SimpleWaterloggedBlock {
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	protected static final VoxelShape SHAPE = Shapes.join(
			Shapes.or(
					box(3, 0, 3, 13, 12, 13),
					box(4, 12, 4, 12, 14, 12)),
			box(3.1, 0.1, 3.1, 12.9, 10.5, 12.9),
			BooleanOp.ONLY_FIRST);
	protected final Supplier<EntityType<? extends AbstractMoth>> mothType;

	public MothJarBlock(Supplier<EntityType<? extends AbstractMoth>> mothType) {
		super(BlockBehaviour.Properties.of()
				.mapColor(MapColor.GOLD)
				.sound(SoundType.GLASS)
				.strength(1f, 10f)
				.lightLevel(s -> 8)
				.requiresCorrectToolForDrops()
				.noOcclusion()
				.isRedstoneConductor((bs, br, bp) -> false)
				.noLootTable());
		this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, false));
		this.mothType = mothType;
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
		return state.getFluidState().isEmpty();
	}

	@Override
	public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
		return 0;
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(WATERLOGGED);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		boolean flag = context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER;
		return this.defaultBlockState().setValue(WATERLOGGED, flag);
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	public BlockState updateShape(BlockState state,Direction facing, BlockState facingState, LevelAccessor world,
										   BlockPos currentPos, BlockPos facingPos) {
		if (state.getValue(WATERLOGGED)) world.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
		return super.updateShape(state, facing, facingState, world, currentPos, facingPos);
	}

	@SuppressWarnings("all")
	@Override
	public void setPlacedBy(Level world, BlockPos pos, BlockState blockstate, LivingEntity entity, ItemStack itemstack) {
		super.setPlacedBy(world, pos, blockstate, entity, itemstack);
		if (world instanceof ServerLevel server) {
			final var moth = this.mothType.get().create(server);
			if (moth == null) return;
			moth.moveTo(pos.getX() + 0.5, pos.getY() + 0.3, pos.getZ() + 0.5, 0, 0);
			moth.setPersistenceRequired();
			moth.finalizeSpawn(server, world.getCurrentDifficultyAt(moth.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
			world.addFreshEntity(moth);
		}
	}
}
