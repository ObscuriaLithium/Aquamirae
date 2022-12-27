
package com.obscuria.aquamirae.world.blocks;

import com.obscuria.aquamirae.registry.AquamiraeEntities;
import com.obscuria.aquamirae.world.entities.GoldenMoth;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nonnull;

public class JarBlock extends Block implements IWaterLoggable {
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	public JarBlock() {
		super(AbstractBlock.Properties.of(Material.GLASS, MaterialColor.GOLD).sound(SoundType.GLASS).strength(1f, 10f).lightLevel(s -> 8)
				.requiresCorrectToolForDrops().noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
		this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, false));
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state, @Nonnull IBlockReader reader, @Nonnull BlockPos pos) {
		return state.getFluidState().isEmpty();
	}

	@Override
	public int getLightBlock(@Nonnull BlockState state, @Nonnull IBlockReader world, @Nonnull BlockPos pos) {
		return 0;
	}

	@Override
	@Nonnull
	public VoxelShape getShape(@Nonnull BlockState state, @Nonnull IBlockReader world, @Nonnull BlockPos pos, @Nonnull ISelectionContext context) {
		return VoxelShapes.join(
				VoxelShapes.or(
						box(3, 0, 3, 13, 12, 13),
						box(4, 12, 4, 12, 14, 12)),
				box(3.1, 0.1, 3.1, 12.9, 10.5, 12.9),
				IBooleanFunction.ONLY_FIRST);
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(WATERLOGGED);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		boolean flag = context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER;
		return this.defaultBlockState().setValue(WATERLOGGED, flag);
	}

	@Override
	public @Nonnull FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	public @Nonnull BlockState updateShape(BlockState state, @Nonnull Direction facing, @Nonnull BlockState facingState, @Nonnull IWorld world, @Nonnull BlockPos currentPos,
										   @Nonnull BlockPos facingPos) {
		if (state.getValue(WATERLOGGED)) world.getLiquidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
		return super.updateShape(state, facing, facingState, world, currentPos, facingPos);
	}

	@Override
	public void setPlacedBy(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull BlockState blockstate, LivingEntity entity, @Nonnull ItemStack itemstack) {
		super.setPlacedBy(world, pos, blockstate, entity, itemstack);
		if (world instanceof ServerWorld) {
			final ServerWorld server = (ServerWorld) world;
			MobEntity moth = new GoldenMoth(AquamiraeEntities.GOLDEN_MOTH.get(), server);
			moth.moveTo(pos.getX() + 0.5, pos.getY() + 0.3, pos.getZ() + 0.5, 0, 0);
			moth.setPersistenceRequired();
			moth.finalizeSpawn(server, world.getCurrentDifficultyAt(moth.blockPosition()), SpawnReason.MOB_SUMMONED, null, null);
			world.addFreshEntity(moth);
		}
	}
}
