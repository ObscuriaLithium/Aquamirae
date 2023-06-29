
package com.obscuria.aquamirae.common.blocks;

import com.obscuria.aquamirae.registry.AquamiraeItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
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
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LuminescentBubbleBlock extends Block implements SimpleWaterloggedBlock {
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	public LuminescentBubbleBlock() {
		super(BlockBehaviour.Properties.of().mapColor(MapColor.WATER).sound(SoundType.CORAL_BLOCK).strength(0.1f, 0.5f)
				.lightLevel(s -> 14).noCollission().speedFactor(0.8f).jumpFactor(0.8f).noOcclusion()
				.hasPostProcess((bs, br, bp) -> true).emissiveRendering((bs, br, bp) -> true).isRedstoneConductor((bs, br, bp) -> false)
				.dynamicShape().offsetType(Block.OffsetType.XYZ));
		this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, false));
	}

	@Override
	public void onPlace(@NotNull BlockState blockstate, @NotNull Level world, @NotNull BlockPos pos, @NotNull BlockState oldState, boolean moving) {
		super.onPlace(blockstate, world, pos, oldState, moving);
		world.scheduleTick(pos, this, 20);
	}

	@Override
	public void tick(@NotNull BlockState blockstate, @NotNull ServerLevel world, @NotNull BlockPos pos, @NotNull RandomSource random) {
		super.tick(blockstate, world, pos, random);
		world.scheduleTick(pos, this, 20);
		final Vec3 center = new Vec3(pos.getX(), pos.getY(), pos.getZ());
		List<Player> list = world.getEntitiesOfClass(Player.class, new AABB(center, center).inflate(8), e -> true).stream()
				.sorted(Comparator.comparingDouble(ent -> ent.distanceToSqr(center))).toList();
		list.forEach(player -> {if (player.isInWater()) player.addEffect(new MobEffectInstance(MobEffects.DOLPHINS_GRACE, 80, 1, false, true)); });
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state, @NotNull BlockGetter reader, @NotNull BlockPos pos) {
		return state.getFluidState().isEmpty();
	}

	@Override
	public int getLightBlock(@NotNull BlockState state, @NotNull BlockGetter worldIn, @NotNull BlockPos pos) {
		return 0;
	}

	@Override
	public @NotNull VoxelShape getShape(BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull CollisionContext context) {
		Vec3 offset = state.getOffset(world, pos);
		return box(3.3, 1, 3.3, 12.7, 15, 12.7).move(offset.x, offset.y, offset.z);
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
	public @NotNull FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	public @NotNull BlockState updateShape(BlockState state, @NotNull Direction facing, @NotNull BlockState facingState, @NotNull LevelAccessor world, @NotNull BlockPos currentPos,
										   @NotNull BlockPos facingPos) {
		if (state.getValue(WATERLOGGED)) world.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
		return super.updateShape(state, facing, facingState, world, currentPos, facingPos);
	}

	@Override
	public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter world, BlockPos pos, Player player) {
		return AquamiraeItems.LUMINESCENT_BUBBLE.get().getDefaultInstance();
	}

	@Override
	public BlockPathTypes getBlockPathType(BlockState state, BlockGetter world, BlockPos pos, Mob entity) {
		return BlockPathTypes.OPEN;
	}

	@Override
	public @NotNull List<ItemStack> getDrops(@NotNull BlockState state, LootParams.@NotNull Builder builder) {
		return Collections.singletonList(AquamiraeItems.LUMINESCENT_BUBBLE.get().getDefaultInstance());
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void animateTick(@NotNull BlockState blockstate, @NotNull Level world, @NotNull BlockPos pos, @NotNull RandomSource random) {
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
	public @NotNull InteractionResult use(@NotNull BlockState blockstate, @NotNull Level world, @NotNull BlockPos pos, @NotNull Player entity, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
		super.use(blockstate, world, pos, entity, hand, hit);
		world.destroyBlock(pos, false);
		ItemStack stack = new ItemStack(AquamiraeItems.LUMINESCENT_BUBBLE.get(), 1);
		ItemHandlerHelper.giveItemToPlayer(entity, stack);
		return InteractionResult.SUCCESS;
	}
}
