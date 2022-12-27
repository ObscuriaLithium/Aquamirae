
package com.obscuria.aquamirae.world.blocks;

import com.obscuria.aquamirae.registry.AquamiraeBlocks;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TieredItem;
import net.minecraft.loot.LootContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class OxygeliumBlock extends Block implements IWaterLoggable {
	public static final EnumProperty<Type> TYPE = EnumProperty.create("type", OxygeliumBlock.Type.class);
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	public OxygeliumBlock() {
		super(Properties.of(Material.WATER_PLANT, MaterialColor.COLOR_CYAN).sound(SoundType.CORAL_BLOCK).strength(0.8f, 1f).randomTicks()
						.requiresCorrectToolForDrops().noCollission().speedFactor(0.8f).jumpFactor(0.8f).isRedstoneConductor((bs, br, bp) -> false)
						.hasPostProcess((bs, br, bp) -> isGlowing(bs)).emissiveRendering((bs, br, bp) -> isGlowing(bs)).lightLevel(s -> isGlowing(s) ? 12 : 0));
		this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, false).setValue(TYPE, Type.STEM));
	}

	private static boolean isGlowing(BlockState state) {
		return state.getValue(TYPE) == Type.BUD || state.getValue(TYPE) == Type.RARE_BUD;
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state, @Nonnull IBlockReader reader, @Nonnull BlockPos pos) {
		return state.getFluidState().isEmpty();
	}

	@Override
	public @Nonnull List<ItemStack> getDrops(@Nonnull BlockState state, @Nonnull LootContext.Builder builder) {
		return Collections.singletonList(AquamiraeItems.OXYGELIUM.get().getDefaultInstance());
	}

	@Override
	public int getLightBlock(@Nonnull BlockState state, @Nonnull IBlockReader worldIn, @Nonnull BlockPos pos) {
		return 0;
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(TYPE, WATERLOGGED);
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
		return AquamiraeItems.OXYGELIUM.get().getDefaultInstance();
	}


	@Override
	public PathNodeType getAiPathNodeType(BlockState state, IBlockReader level, BlockPos pos, @Nullable MobEntity entity) {
		return PathNodeType.OPEN;
	}

	@Override
	public boolean canHarvestBlock(BlockState state, IBlockReader world, BlockPos pos, PlayerEntity player) {
		return super.canHarvestBlock(state, world, pos, player) &&
				(player.inventory.getSelected().getItem() instanceof TieredItem && ((TieredItem)player.inventory.getSelected().getItem()).getTier().getLevel() >= 1);
	}

	@Override
	public void neighborChanged(@Nonnull BlockState blockstate, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull Block neighborBlock, @Nonnull BlockPos fromPos, boolean moving) {
		super.neighborChanged(blockstate, world, pos, neighborBlock, fromPos, moving);
		if (world.getBlockState(pos.below()).canOcclude()) return;
		if (world.getBlockState(pos.below()).hasProperty(TYPE) && world.getBlockState(pos.below()).getValue(TYPE) == Type.STEM) return;
		Block.dropResources(world.getBlockState(pos), world, pos, null);
		world.destroyBlock(pos, false);
	}

	@Override
	public void tick(@Nonnull BlockState state, @Nonnull ServerWorld world, @Nonnull BlockPos pos, @Nonnull Random random) {
		if (state.getValue(TYPE) != Type.EMPTY_BUD) return;
		if (Math.random() < 0.1) {
			if (!world.isClientSide()) world.playSound(null, pos, SoundEvents.BUBBLE_COLUMN_BUBBLE_POP, SoundCategory.BLOCKS, 5, 1);
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
	public void animateTick(@Nonnull BlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull Random random) {
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
	public void attack(@Nonnull BlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull PlayerEntity entity) {
		super.attack(state, world, pos, entity);
		if (state.getValue(TYPE) == Type.BUD) useBud(state, world, pos, entity);
		else if (state.getValue(TYPE) == Type.RARE_BUD) useRareBud(state, world, pos, entity);
	}

	@Override
	public void entityInside(@Nonnull BlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull Entity entity) {
		super.entityInside(state, world, pos, entity);
		if (entity instanceof LivingEntity && state.getValue(TYPE) == Type.BUD) useBud(state, world, pos, (LivingEntity) entity);
		else if (entity instanceof LivingEntity && state.getValue(TYPE) == Type.RARE_BUD) useRareBud(state, world, pos, (LivingEntity) entity);
	}

	@Override
	public @Nonnull ActionResultType use(@Nonnull BlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull PlayerEntity entity, @Nonnull Hand hand, @Nonnull BlockRayTraceResult hit) {
		if (state.getValue(TYPE) == Type.BUD) return useBud(state, world, pos, entity);
		else if (state.getValue(TYPE) == Type.RARE_BUD) return useRareBud(state, world, pos, entity);
		else return super.use(state, world, pos, entity, hand, hit);
	}

	private @Nonnull ActionResultType useBud(BlockState state, World world, BlockPos pos, LivingEntity entity) {
		if (!world.isClientSide()) world.playSound(null, pos, SoundEvents.BUBBLE_COLUMN_BUBBLE_POP, SoundCategory.BLOCKS, 5, 1);
		world.setBlock(pos, AquamiraeBlocks.OXYGELIUM.get().defaultBlockState().setValue(TYPE, Type.EMPTY_BUD).setValue(WATERLOGGED, state.getValue(WATERLOGGED)), 3);
		if (world instanceof ServerWorld) ((ServerWorld) world).sendParticles(ParticleTypes.BUBBLE,
				pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 12, 0.5, 0.5, 0.5, 0.01);
		entity.setAirSupply(0);
		entity.addEffect(new EffectInstance(Effects.WATER_BREATHING,
				(entity.hasEffect(Effects.WATER_BREATHING) ? Objects.requireNonNull(entity.getEffect(Effects.WATER_BREATHING)).getDuration() : 0) + 200, 0));

		if (!world.isClientSide() && entity.hasEffect(Effects.WATER_BREATHING) && Objects.requireNonNull(entity.getEffect(Effects.WATER_BREATHING)).getDuration() > 1200) {
			world.playSound(null, pos, SoundEvents.AMBIENT_CAVE, SoundCategory.BLOCKS, 1, 1);
			entity.addEffect(new EffectInstance(Effects.CONFUSION, 200, 1));
		}
		return ActionResultType.SUCCESS;
	}

	private @Nonnull ActionResultType useRareBud(BlockState state, World world, BlockPos pos, LivingEntity entity) {
		if (!world.isClientSide()) {
			world.playSound(null, pos, SoundEvents.EXPERIENCE_ORB_PICKUP, SoundCategory.BLOCKS, 0.6F, 1);
			world.addFreshEntity(new ExperienceOrbEntity(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 8));
		}
		world.setBlock(pos, AquamiraeBlocks.OXYGELIUM.get().defaultBlockState().setValue(TYPE, Type.EMPTY_BUD).setValue(WATERLOGGED, state.getValue(WATERLOGGED)), 3);
		return ActionResultType.SUCCESS;
	}

	public enum Type implements IStringSerializable {
		STEM("stem"),
		EMPTY_BUD("empty_bud"),
		BUD("bud"),
		RARE_BUD("rare_bud");

		private final String NAME;

		Type(String name) {
			this.NAME = name;
		}

		public @Nonnull String getSerializedName() {
			return this.NAME;
		}
	}
}
