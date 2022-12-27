
package com.obscuria.aquamirae.world.blocks;

import com.obscuria.aquamirae.registry.AquamiraeParticleTypes;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class LuminescentLampBlock extends Block implements IWaterLoggable {
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	public LuminescentLampBlock() {
		super(AbstractBlock.Properties.of(Material.WOOD).sound(SoundType.WOOD).strength(1.4f, 6f).lightLevel(s -> 15).noOcclusion()
				.isRedstoneConductor((bs, br, bp) -> false));
		this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, false));
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state, @Nonnull IBlockReader reader, @Nonnull BlockPos pos) {
		return state.getFluidState().isEmpty();
	}

	@Override
	public int getLightBlock(@Nonnull BlockState state, @Nonnull IBlockReader worldIn, @Nonnull BlockPos pos) {
		return 0;
	}

	@Override
	public @Nonnull VoxelShape getShape(@Nonnull BlockState state, @Nonnull IBlockReader world, @Nonnull BlockPos pos, @Nonnull ISelectionContext context) {
		return box(5, 0, 5, 11, 32, 11);
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
	public @Nonnull List<ItemStack> getDrops(@Nonnull BlockState state, @Nonnull LootContext.Builder builder) {
		return Collections.singletonList(new ItemStack(this));
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void animateTick(@Nonnull BlockState blockstate, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull Random random) {
		super.animateTick(blockstate, world, pos, random);
		if (random.nextFloat() < 0.2) world.addParticle(AquamiraeParticleTypes.GHOST_SHINE.get(),
					pos.getX() + 0.5 + (random.nextFloat() - 0.5) * 0.05D, pos.getY() + 1.6 + (random.nextFloat() - 0.5) * 0.05D,
					pos.getZ() + 0.5 + (random.nextFloat() - 0.5) * 0.05D, 0, 0, 0);
	}
}
