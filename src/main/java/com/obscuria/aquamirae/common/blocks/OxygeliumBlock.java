
package com.obscuria.aquamirae.common.blocks;

import com.obscuria.aquamirae.registry.AquamiraeBlocks;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class OxygeliumBlock extends Block implements SimpleWaterloggedBlock {
	public static final EnumProperty<OxygeliumBlock.Type> TYPE = EnumProperty.create("type", OxygeliumBlock.Type.class);
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	public OxygeliumBlock() {
		super(Properties.of().mapColor(MapColor.WATER).sound(SoundType.CORAL_BLOCK).strength(0.8f, 1f).randomTicks()
						.requiresCorrectToolForDrops().noCollission().speedFactor(0.8f).jumpFactor(0.8f).isRedstoneConductor((bs, br, bp) -> false)
						.hasPostProcess((bs, br, bp) -> isGlowing(bs)).emissiveRendering((bs, br, bp) -> isGlowing(bs)).lightLevel(s -> isGlowing(s) ? 12 : 0).noLootTable());
		this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, false).setValue(TYPE, Type.STEM));
	}

	private static boolean isGlowing(BlockState state) {
		return state.getValue(TYPE) == Type.BUD || state.getValue(TYPE) == Type.RARE_BUD;
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state, @NotNull BlockGetter reader, @NotNull BlockPos pos) {
		return state.getFluidState().isEmpty();
	}

	@Override
	public @NotNull List<ItemStack> getDrops(@NotNull BlockState state, LootParams.@NotNull Builder builder) {
		return Collections.singletonList(AquamiraeItems.OXYGELIUM.get().getDefaultInstance());
	}

	@Override
	public int getLightBlock(@NotNull BlockState state, @NotNull BlockGetter worldIn, @NotNull BlockPos pos) {
		return 0;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(TYPE, WATERLOGGED);
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
		return new ItemStack(AquamiraeItems.OXYGELIUM.get());
	}

	@Override
	public BlockPathTypes getBlockPathType(BlockState state, BlockGetter world, BlockPos pos, Mob entity) {
		return BlockPathTypes.OPEN;
	}

	@Override
	public boolean canHarvestBlock(BlockState state, BlockGetter world, BlockPos pos, Player player) {
		return super.canHarvestBlock(state, world, pos, player) &&
				(player.getInventory().getSelected().getItem() instanceof TieredItem tieredItem && tieredItem.getTier().getLevel() >= 1);
	}

	@Override
	public void neighborChanged(@NotNull BlockState blockstate, @NotNull Level world, @NotNull BlockPos pos, @NotNull Block neighborBlock, @NotNull BlockPos fromPos, boolean moving) {
		super.neighborChanged(blockstate, world, pos, neighborBlock, fromPos, moving);
		if (world.getBlockState(pos.below()).canOcclude()) return;
		if (world.getBlockState(pos.below()).hasProperty(TYPE) && world.getBlockState(pos.below()).getValue(TYPE) == Type.STEM) return;
		Block.dropResources(world.getBlockState(pos), world, pos, null);
		world.destroyBlock(pos, false);
	}

	@Override
	public void tick(@NotNull BlockState state, @NotNull ServerLevel world, @NotNull BlockPos pos, @NotNull RandomSource random) {
		if (state.getValue(TYPE) != Type.EMPTY_BUD) return;
		if (Math.random() < 0.1) {
			if (!world.isClientSide()) world.playSound(null, pos, SoundEvents.BUBBLE_COLUMN_BUBBLE_POP, SoundSource.BLOCKS, 5, 1);
			if (Math.random() <= 0.98) {
				world.sendParticles(ParticleTypes.BUBBLE, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 10, 0.5, 0.5, 0.5, 0.2);
				world.setBlock(pos, AquamiraeBlocks.OXYGELIUM.get().defaultBlockState().setValue(TYPE, Type.BUD).setValue(WATERLOGGED, state.getValue(WATERLOGGED)), 3);
			} else {
				world.sendParticles(ParticleTypes.HAPPY_VILLAGER, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 5, 0.5, 0.5, 0.5, 0.2);
				world.setBlock(pos, AquamiraeBlocks.OXYGELIUM.get().defaultBlockState().setValue(TYPE, Type.RARE_BUD).setValue(WATERLOGGED, state.getValue(WATERLOGGED)), 3);
			}
		}
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void animateTick(@NotNull BlockState state, @NotNull Level world, @NotNull BlockPos pos, @NotNull RandomSource random) {
		super.animateTick(state, world, pos, random);
		if (state.getValue(TYPE) != Type.BUD && state.getValue(TYPE) != Type.RARE_BUD) return;
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		if (world.getBlockState(pos.above()).getBlock() == Blocks.WATER)
			for (int l = 0; l < 2; ++l) {
				double x0 = x + 0.5 + (random.nextFloat() - 0.5) * 2D;
				double y0 = y + 1.2 + (random.nextFloat() - 0.5) * 2D;
				double z0 = z + 0.5 + (random.nextFloat() - 0.5) * 2D;
				world.addParticle(ParticleTypes.BUBBLE, x0, y0, z0, 0, 0, 0);
			}
	}

	@Override
	public void attack(@NotNull BlockState state, @NotNull Level world, @NotNull BlockPos pos, @NotNull Player entity) {
		super.attack(state, world, pos, entity);
		if (state.getValue(TYPE) == Type.BUD) useBud(state, world, pos, entity);
		else if (state.getValue(TYPE) == Type.RARE_BUD) useRareBud(state, world, pos, entity);
	}

	@Override
	public void entityInside(@NotNull BlockState state, @NotNull Level world, @NotNull BlockPos pos, @NotNull Entity entity) {
		super.entityInside(state, world, pos, entity);
		if (entity instanceof LivingEntity living && state.getValue(TYPE) == Type.BUD) useBud(state, world, pos, living);
		else if (entity instanceof LivingEntity living && state.getValue(TYPE) == Type.RARE_BUD) useRareBud(state, world, pos, living);
	}

	@Override
	public @NotNull InteractionResult use(@NotNull BlockState state, @NotNull Level world, @NotNull BlockPos pos, @NotNull Player entity, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
		if (state.getValue(TYPE) == Type.BUD) return useBud(state, world, pos, entity);
		else if (state.getValue(TYPE) == Type.RARE_BUD) return useRareBud(state, world, pos, entity);
		else return super.use(state, world, pos, entity, hand, hit);
	}

	private @NotNull InteractionResult useBud(BlockState state, Level world, BlockPos pos, LivingEntity entity) {
		if (!world.isClientSide()) world.playSound(null, pos, SoundEvents.BUBBLE_COLUMN_BUBBLE_POP, SoundSource.BLOCKS, 5, 1);
		world.setBlock(pos, AquamiraeBlocks.OXYGELIUM.get().defaultBlockState().setValue(TYPE, Type.EMPTY_BUD).setValue(WATERLOGGED, state.getValue(WATERLOGGED)), 3);
		if (world instanceof ServerLevel server) server.sendParticles(ParticleTypes.BUBBLE,
				pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 12, 0.5, 0.5, 0.5, 0.01);
		entity.setAirSupply(0);
		entity.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING,
				(entity.hasEffect(MobEffects.WATER_BREATHING) ? Objects.requireNonNull(entity.getEffect(MobEffects.WATER_BREATHING)).getDuration() : 0) + 200, 0));

		if (!world.isClientSide() && entity.hasEffect(MobEffects.WATER_BREATHING) && Objects.requireNonNull(entity.getEffect(MobEffects.WATER_BREATHING)).getDuration() > 1200) {
			world.playSound(null, pos, SoundEvents.AMBIENT_CAVE.get(), SoundSource.BLOCKS, 1, 1);
			entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 1));
		}
		return InteractionResult.SUCCESS;
	}

	private @NotNull InteractionResult useRareBud(BlockState state, Level world, BlockPos pos, LivingEntity entity) {
		if (!world.isClientSide()) {
			world.playSound(null, pos, SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.BLOCKS, 0.6F, 1);
			world.addFreshEntity(new ExperienceOrb(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 8));
		}
		world.setBlock(pos, AquamiraeBlocks.OXYGELIUM.get().defaultBlockState().setValue(TYPE, Type.EMPTY_BUD).setValue(WATERLOGGED, state.getValue(WATERLOGGED)), 3);
		return InteractionResult.SUCCESS;
	}

	public enum Type implements StringRepresentable {
		STEM("stem"),
		EMPTY_BUD("empty_bud"),
		BUD("bud"),
		RARE_BUD("rare_bud");

		private final String NAME;

		Type(String name) {
			this.NAME = name;
		}

		public @NotNull String getSerializedName() {
			return this.NAME;
		}
	}
}
