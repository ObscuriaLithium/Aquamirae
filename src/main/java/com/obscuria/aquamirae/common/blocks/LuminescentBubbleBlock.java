
package com.obscuria.aquamirae.common.blocks;

import com.obscuria.aquamirae.registry.AquamiraeItems;
import com.obscuria.obscureapi.api.utils.PlayerUtils;
import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

@SuppressWarnings("deprecation")
public class LuminescentBubbleBlock extends Block implements Waterloggable {
	public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
	private static final VoxelShape SHAPE = createCuboidShape(3.3, 1, 3.3, 12.7, 15, 12.7);

	public LuminescentBubbleBlock() {
		super(Settings.create()
				.mapColor(MapColor.BLUE)
				.sounds(BlockSoundGroup.CORAL)
				.strength(0.1f, 0.5f)
				.luminance(s -> 14)
				.noCollision()
				.nonOpaque()
				.velocityMultiplier(0.8f)
				.jumpVelocityMultiplier(0.8f)
				.postProcess((a, b, c) -> true)
				.emissiveLighting((a, b, c) -> true)
				.solidBlock((a, b, c) -> false)
				.dynamicBounds()
				.offset(OffsetType.XYZ));
		setDefaultState(getStateManager().getDefaultState().with(WATERLOGGED, false));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(WATERLOGGED);
	}

	@Override
	public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
		super.onPlaced(world, pos, state, placer, itemStack);
		world.scheduleBlockTick(pos, this, 20);
	}

	@Override
	public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		super.scheduledTick(state, world, pos, random);
		world.scheduleBlockTick(pos, this, 20);
		final Vec3d center = new Vec3d(pos.getX(), pos.getY(), pos.getZ());
		world.getEntitiesByClass(PlayerEntity.class, new Box(center, center).expand(8), e -> true)
				.forEach(player -> {
					if (player.isSubmergedInWater()) player.addStatusEffect(new StatusEffectInstance(StatusEffects.DOLPHINS_GRACE,
							80, 1, false, true));
				});
	}

	private VoxelShape getShape(BlockState state, BlockView world, BlockPos pos) {
		final Vec3d offset = state.getModelOffset(world, pos);
		return SHAPE.offset(offset.x, offset.y, offset.z);
	}

	public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return this.getShape(state, world, pos);
	}

	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return this.getShape(state, world, pos);
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
	public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
		return AquamiraeItems.LUMINESCENT_BUBBLE.getDefaultStack();
	}

	@Override
	public List<ItemStack> getDroppedStacks(BlockState state, LootContextParameterSet.Builder builder) {
		return Collections.singletonList(AquamiraeItems.LUMINESCENT_BUBBLE.getDefaultStack());
	}

	@Override
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
		super.randomDisplayTick(state, world, pos, random);
		for (int l = 0; l < 3; ++l) {
			double x0 = pos.getX() + random.nextFloat();
			double y0 = pos.getY() + random.nextFloat();
			double z0 = pos.getZ() + random.nextFloat();
			double dx = (random.nextFloat() - 0.5D) * 0.16D;
			double dy = (random.nextFloat() - 0.5D) * 0.16D;
			double dz = (random.nextFloat() - 0.5D) * 0.16D;
			world.addParticle(ParticleTypes.BUBBLE, x0, y0, z0, dx, dy, dz);
		}
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		super.onUse(state, world, pos, player, hand, hit);
		world.breakBlock(pos, false);
		PlayerUtils.giveItem(player, AquamiraeItems.LUMINESCENT_BUBBLE.getDefaultStack());
		return ActionResult.SUCCESS;
	}
}
