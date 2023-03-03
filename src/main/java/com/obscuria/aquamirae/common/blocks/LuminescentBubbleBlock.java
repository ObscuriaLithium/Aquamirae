
package com.obscuria.aquamirae.common.blocks;

import com.obscuria.aquamirae.registry.AquamiraeItems;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class LuminescentBubbleBlock extends Block implements IWaterLoggable {
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	public LuminescentBubbleBlock() {
		super(AbstractBlock.Properties.of(Material.WATER_PLANT, MaterialColor.COLOR_BROWN).sound(SoundType.CORAL_BLOCK).strength(0.1f, 0.5f)
				.lightLevel(s -> 14).noCollission().speedFactor(0.8f).jumpFactor(0.8f).noOcclusion()
				.hasPostProcess((bs, br, bp) -> true).emissiveRendering((bs, br, bp) -> true).isRedstoneConductor((bs, br, bp) -> false)
				.dynamicShape());
		this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, false));
	}

	@Override
	public @Nonnull OffsetType getOffsetType() {
		return OffsetType.XYZ;
	}

	@Override
	public void onPlace(@Nonnull BlockState blockstate, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull BlockState oldState, boolean moving) {
		super.onPlace(blockstate, world, pos, oldState, moving);
		world.getBlockTicks().scheduleTick(pos, this, 20);
	}

	@Override
	public void tick(@Nonnull BlockState blockstate, @Nonnull ServerWorld world, @Nonnull BlockPos pos, @Nonnull Random random) {
		super.tick(blockstate, world, pos, random);
		world.getBlockTicks().scheduleTick(pos, this, 20);
		final Vector3d center = new Vector3d(pos.getX(), pos.getY(), pos.getZ());
		List<PlayerEntity> list = world.getEntitiesOfClass(PlayerEntity.class, new AxisAlignedBB(center, center).inflate(8), e -> true).stream()
				.sorted(Comparator.comparingDouble(ent -> ent.distanceToSqr(center))).collect(Collectors.toList());
		list.forEach(player -> {if (player.isInWater()) player.addEffect(new EffectInstance(Effects.DOLPHINS_GRACE, 80, 1, false, true)); });
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
	public @Nonnull VoxelShape getShape(BlockState state, @Nonnull IBlockReader world, @Nonnull BlockPos pos, @Nonnull ISelectionContext context) {
		Vector3d offset = state.getOffset(world, pos);
		return box(3.3, 1, 3.3, 12.7, 15, 12.7).move(offset.x, offset.y, offset.z);
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
	@Nonnull
	public ItemStack getCloneItemStack(@Nonnull IBlockReader world, @Nonnull BlockPos pos, @Nonnull BlockState state) {
		return AquamiraeItems.LUMINESCENT_BUBBLE.get().getDefaultInstance();
	}

	@Override
	public PathNodeType getAiPathNodeType(BlockState state, IBlockReader level, BlockPos pos, @Nullable MobEntity entity) {
		return PathNodeType.OPEN;
	}

	@Override
	public @Nonnull List<ItemStack> getDrops(@Nonnull BlockState state, @Nonnull LootContext.Builder builder) {
		return Collections.singletonList(AquamiraeItems.LUMINESCENT_BUBBLE.get().getDefaultInstance());
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void animateTick(@Nonnull BlockState blockstate, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull Random random) {
		super.animateTick(blockstate, world, pos, random);
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
	public @Nonnull ActionResultType use(@Nonnull BlockState blockstate, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull PlayerEntity entity, @Nonnull Hand hand, @Nonnull BlockRayTraceResult hit) {
		super.use(blockstate, world, pos, entity, hand, hit);
		world.destroyBlock(pos, false);
		ItemStack stack = new ItemStack(AquamiraeItems.LUMINESCENT_BUBBLE.get(), 1);
		ItemHandlerHelper.giveItemToPlayer(entity, stack);
		return ActionResultType.SUCCESS;
	}
}
