package com.obscuria.aquamirae.common.blocks;

import net.minecraft.block.*;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

@SuppressWarnings("deprecation")
public class CollectiblePaintingBlock extends Block implements Waterloggable {
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    private static final VoxelShape SOUTH_SHAPE;
    private static final VoxelShape NORTH_SHAPE;
    private static final VoxelShape EAST_SHAPE;
    private static final VoxelShape WEST_SHAPE;

    public CollectiblePaintingBlock() {
        super(Settings.create()
                .mapColor(MapColor.BROWN)
                .sounds(BlockSoundGroup.WOOD)
                .sounds(new BlockSoundGroup(1f, 1f,
                        SoundEvents.ENTITY_PAINTING_BREAK,
                        SoundEvents.BLOCK_WOOD_STEP,
                        SoundEvents.ENTITY_PAINTING_PLACE,
                        SoundEvents.BLOCK_WOOD_HIT,
                        SoundEvents.BLOCK_WOOD_FALL))
                .strength(1f)
                .noCollision()
                .nonOpaque()
                .solidBlock((a, b, c) -> false));
        setDefaultState(getStateManager().getDefaultState().with(FACING, Direction.NORTH).with(WATERLOGGED, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        super.appendTooltip(stack, world, tooltip, options);
        tooltip.add(Text.literal(Formatting.GRAY + Text.translatable(this.getTranslationKey() + "_desc").getString()));
    }

    private VoxelShape getShape(BlockState state) {
        return switch (state.get(FACING)) {
            default -> SOUTH_SHAPE;
            case NORTH -> NORTH_SHAPE;
            case EAST -> EAST_SHAPE;
            case WEST -> WEST_SHAPE;
        };
    }

    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return this.getShape(state);
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return this.getShape(state);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        final boolean inWater = ctx.getWorld().getFluidState(ctx.getBlockPos()).getFluid() == Fluids.WATER;
        if (ctx.getSide().getAxis() == Direction.Axis.Y)
            return getDefaultState().with(FACING, Direction.NORTH).with(WATERLOGGED, inWater);
        return getDefaultState().with(FACING, ctx.getSide()).with(WATERLOGGED, inWater);
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
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
    public List<ItemStack> getDroppedStacks(BlockState state, LootContextParameterSet.Builder builder) {
        return Collections.singletonList(new ItemStack(this, 1));
    }

    static {
        SOUTH_SHAPE = createCuboidShape(-8, 0, 0, 24, 16, 1);
        NORTH_SHAPE = createCuboidShape(-8, 0, 15, 24, 16, 16);
        EAST_SHAPE = createCuboidShape(0, 0, -8, 1, 16, 24);
        WEST_SHAPE = createCuboidShape(15, 0, -8, 16, 16, 24);
    }
}
