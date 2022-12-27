
package com.obscuria.aquamirae.world.blocks;

import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import com.obscuria.aquamirae.registry.AquamiraeParticleTypes;
import com.obscuria.aquamirae.registry.AquamiraeSounds;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

public class FrozenChestBlock extends Block implements IWaterLoggable {
	public static final DirectionProperty FACING = HorizontalFaceBlock.FACING;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	public FrozenChestBlock() {
		super(AbstractBlock.Properties.of(Material.WOOD).sound(SoundType.WOOD).strength(-1, 3600000).noOcclusion()
				.isRedstoneConductor((bs, br, bp) -> false));
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false));
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
		return box(0.9, 0, 0.9, 15.1, 14.1, 15.1);
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(FACING, WATERLOGGED);
	}

	public @Nonnull BlockState rotate(BlockState state, Rotation rot) {
		return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
	}

	public @Nonnull BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		boolean flag = context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER;;
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(WATERLOGGED, flag);
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
	public @Nonnull PushReaction getPistonPushReaction(@Nonnull BlockState state) {
		return PushReaction.BLOCK;
	}

	@Override
	public @Nonnull List<ItemStack> getDrops(@Nonnull BlockState state, @Nonnull LootContext.Builder builder) {
		List<ItemStack> dropsOriginal = super.getDrops(state, builder);
		if (!dropsOriginal.isEmpty())
			return dropsOriginal;
		return Collections.singletonList(new ItemStack(this, 1));
	}

	@Override
	public boolean canHarvestBlock(BlockState state, IBlockReader level, BlockPos pos, PlayerEntity player) {
		return false;
	}

	@Override
	public boolean canEntityDestroy(BlockState state, IBlockReader level, BlockPos pos, Entity entity) {
		return false;
	}

	@Override
	public @Nonnull ActionResultType use(@Nonnull BlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull PlayerEntity player, @Nonnull Hand hand, @Nonnull BlockRayTraceResult hit) {
		final ItemStack stack = player.getItemInHand(hand);
		if (stack.getItem() == AquamiraeItems.FROZEN_KEY.get()) {
			stack.shrink(1);
			final BlockState chest = Blocks.CHEST.defaultBlockState().setValue(FACING, state.getValue(FACING));
			world.setBlock(pos, chest, 3);
			LockableLootTileEntity.setLootTable(world, player.getRandom(), pos,
					new ResourceLocation(AquamiraeMod.MODID, "chests/frozen_chest"));
			world.playSound(player, pos, AquamiraeSounds.BLOCK_FROZEN_CHEST_UNLOCK.get(), SoundCategory.BLOCKS, 1f, 1f);
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
				world.addParticle(AquamiraeParticleTypes.SHINE.get(), d0, d1, d2, 0, 0.05, 0);
			}
			return ActionResultType.SUCCESS;
		}
		return ActionResultType.FAIL;
	}
}
