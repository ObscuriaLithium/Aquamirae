package com.obscuria.aquamirae.world.blocks;

import com.obscuria.obscureapi.utils.TextHelper;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraftforge.common.util.ForgeSoundType;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

public class CollectiblePaintingBlock extends Block implements IWaterLoggable {
    public static final DirectionProperty FACING = HorizontalFaceBlock.FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public CollectiblePaintingBlock() {
        super(AbstractBlock.Properties.of(Material.WOOD)
                .sound(new ForgeSoundType(1.0f, 1.0f, () -> SoundEvents.PAINTING_BREAK,
                        () -> SoundEvents.WOOL_STEP,
                        () -> SoundEvents.PAINTING_PLACE,
                        () -> SoundEvents.WOOD_HIT,
                        () -> SoundEvents.WOOD_FALL))
                .strength(1f).requiresCorrectToolForDrops().noCollission().noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false));
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, IBlockReader world, @Nonnull List<ITextComponent> list, @Nonnull ITooltipFlag flag) {
        super.appendHoverText(stack, world, list, flag);
        list.add(TextHelper.component(TextFormatting.GRAY + TextHelper.translation(this.getDescriptionId() + "_desc")));
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
        switch (state.getValue(FACING)) {
            default : return box(-8, 0, 0, 24, 16, 1);
            case NORTH : return box(-8, 0, 15, 24, 16, 16);
            case EAST : return box(0, 0, -8, 1, 16, 24);
            case WEST : return box(15, 0, -8, 16, 16, 24);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        boolean flag = context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER;
        if (context.getClickedFace().getAxis() == Direction.Axis.Y)
            return this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, flag);
        return this.defaultBlockState().setValue(FACING, context.getClickedFace()).setValue(WATERLOGGED, flag);
    }

    public @Nonnull BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    public @Nonnull BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
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
    public boolean canHarvestBlock(BlockState state, IBlockReader world, BlockPos pos, PlayerEntity player) {
        return true;
    }

    @Override
    public @Nonnull List<ItemStack> getDrops(@Nonnull BlockState state, @Nonnull LootContext.Builder builder) {
        return Collections.singletonList(new ItemStack(this, 1));
    }
}
