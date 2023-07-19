
package com.obscuria.aquamirae.common.blocks;

import com.obscuria.aquamirae.registry.AquamiraeBlocks;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ExperienceOrbEntity;
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
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("deprecation")
public class OxygeliumBlock extends Block implements Waterloggable {
	public static final EnumProperty<Type> TYPE = EnumProperty.of("type", OxygeliumBlock.Type.class);
	public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

	public OxygeliumBlock() {
		super(Settings.create()
				.mapColor(MapColor.BLUE)
				.sounds(BlockSoundGroup.CORAL)
				.strength(0.8f, 1f)
				.ticksRandomly()
				.requiresTool()
				.noCollision()
				.velocityMultiplier(0.8f)
				.jumpVelocityMultiplier(0.8f)
				.solidBlock((a, b, c) -> false)
				.postProcess((a, b, c) -> isGlowing(a))
				.emissiveLighting((a, b, c) -> isGlowing(a))
				.luminance(s -> isGlowing(s) ? 12 : 0));
		setDefaultState(getStateManager().getDefaultState().with(WATERLOGGED, false).with(TYPE, Type.STEM));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(TYPE, WATERLOGGED);
	}

	private static boolean isGlowing(BlockState state) {
		return state.get(TYPE) == Type.BUD || state.get(TYPE) == Type.RARE_BUD;
	}


	@Override
	public List<ItemStack> getDroppedStacks(BlockState state, LootContextParameterSet.Builder builder) {
		return Collections.singletonList(AquamiraeItems.OXYGELIUM.getDefaultStack());
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
		return AquamiraeItems.OXYGELIUM.getDefaultStack();
	}

	@Override
	public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
		super.neighborUpdate(state, world, pos, sourceBlock, sourcePos, notify);
		if (world.getBlockState(pos.down()).isSideSolid(world, pos, Direction.UP, SideShapeType.FULL)) return;
		if (world.getBlockState(pos.down()).contains(TYPE) && world.getBlockState(pos.down()).get(TYPE) == Type.STEM) return;
		Block.dropStacks(world.getBlockState(pos), world, pos, null);
		world.breakBlock(pos, false);
	}

	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if (state.get(TYPE) != Type.EMPTY_BUD) return;
		if (Math.random() < 0.1) {
			world.playSound(null, pos, SoundEvents.BLOCK_BUBBLE_COLUMN_BUBBLE_POP, SoundCategory.BLOCKS, 5, 1);
			if (Math.random() <= 0.98) {
				world.spawnParticles(ParticleTypes.BUBBLE, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 10, 0.5, 0.5, 0.5, 0.2);
				world.setBlockState(pos, AquamiraeBlocks.OXYGELIUM.getDefaultState().with(TYPE, Type.BUD).with(WATERLOGGED, state.get(WATERLOGGED)), 3);
			} else {
				world.spawnParticles(ParticleTypes.HAPPY_VILLAGER, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 5, 0.5, 0.5, 0.5, 0.2);
				world.setBlockState(pos, AquamiraeBlocks.OXYGELIUM.getDefaultState().with(TYPE, Type.RARE_BUD).with(WATERLOGGED, state.get(WATERLOGGED)), 3);
			}
		}
	}

	@Override
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
		super.randomDisplayTick(state, world, pos, random);
		if (state.get(TYPE) != Type.BUD && state.get(TYPE) != Type.RARE_BUD) return;
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		if (world.getBlockState(pos.up()).getBlock() == Blocks.WATER)
			for (int l = 0; l < 2; ++l) {
				double x0 = x + 0.5 + (random.nextFloat() - 0.5) * 2D;
				double y0 = y + 1.2 + (random.nextFloat() - 0.5) * 2D;
				double z0 = z + 0.5 + (random.nextFloat() - 0.5) * 2D;
				world.addParticle(ParticleTypes.BUBBLE, x0, y0, z0, 0, 0, 0);
			}
	}

	@Override
	public void onBlockBreakStart(BlockState state, World world, BlockPos pos, PlayerEntity player) {
		super.onBlockBreakStart(state, world, pos, player);
		if (state.get(TYPE) == Type.BUD) useBud(state, world, pos, player);
		else if (state.get(TYPE) == Type.RARE_BUD) useRareBud(state, world, pos);
	}

	@Override
	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		super.onEntityCollision(state, world, pos, entity);
		if (entity instanceof LivingEntity living && state.get(TYPE) == Type.BUD) useBud(state, world, pos, living);
		else if (entity instanceof LivingEntity && state.get(TYPE) == Type.RARE_BUD) useRareBud(state, world, pos);
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (state.get(TYPE) == Type.BUD) return useBud(state, world, pos, player);
		else if (state.get(TYPE) == Type.RARE_BUD) return useRareBud(state, world, pos);
		else return super.onUse(state, world, pos, player, hand, hit);
	}

	private ActionResult useBud(BlockState state, World world, BlockPos pos, LivingEntity entity) {
		if (!world.isClient()) world.playSound(null, pos, SoundEvents.BLOCK_BUBBLE_COLUMN_BUBBLE_POP, SoundCategory.BLOCKS, 5, 1);
		world.setBlockState(pos, AquamiraeBlocks.OXYGELIUM.getDefaultState().with(TYPE, Type.EMPTY_BUD).with(WATERLOGGED, state.get(WATERLOGGED)), 3);
		if (world instanceof ServerWorld server) server.spawnParticles(ParticleTypes.BUBBLE, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
				12, 0.5, 0.5, 0.5, 0.01);
		entity.setAir(0);
		entity.addStatusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING,
				(entity.hasStatusEffect(StatusEffects.WATER_BREATHING) ? Objects.requireNonNull(
						entity.getStatusEffect(StatusEffects.WATER_BREATHING)).getDuration() : 0) + 200, 0));

		if (!world.isClient() && entity.hasStatusEffect(StatusEffects.WATER_BREATHING) && Objects.requireNonNull(
				entity.getStatusEffect(StatusEffects.WATER_BREATHING)).getDuration() > 1200) {
			world.playSound(null, pos, SoundEvents.AMBIENT_CAVE.value(), SoundCategory.BLOCKS, 1, 1);
			entity.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 200, 1));
		}
		return ActionResult.SUCCESS;
	}

	private ActionResult useRareBud(BlockState state, World world, BlockPos pos) {
		if (!world.isClient()) {
			world.playSound(null, pos, SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.BLOCKS, 0.6F, 1);
			world.spawnEntity(new ExperienceOrbEntity(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 8));
		}
		world.setBlockState(pos, AquamiraeBlocks.OXYGELIUM.getDefaultState().with(TYPE, Type.EMPTY_BUD).with(WATERLOGGED, state.get(WATERLOGGED)), 3);
		return ActionResult.SUCCESS;
	}

	public enum Type implements StringIdentifiable {
		STEM("stem"),
		EMPTY_BUD("empty_bud"),
		BUD("bud"),
		RARE_BUD("rare_bud");

		private final String NAME;

		Type(String name) {
			this.NAME = name;
		}

		@Override
		public String asString() {
			return NAME;
		}
	}
}
