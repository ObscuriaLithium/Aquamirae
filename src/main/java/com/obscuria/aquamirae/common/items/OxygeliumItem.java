
package com.obscuria.aquamirae.common.items;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.common.blocks.OxygeliumBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item;
import net.minecraft.world.InteractionResult;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import com.obscuria.aquamirae.registry.AquamiraeBlocks;
import org.jetbrains.annotations.NotNull;

public class OxygeliumItem extends Item {
	public OxygeliumItem() {
		super(new Item.Properties().tab(Aquamirae.TAB).stacksTo(64).rarity(Rarity.COMMON));
	}

	@Override
	public @NotNull InteractionResult useOn(UseOnContext context) {
		if (context.getClickedFace() == Direction.UP) {
			if (context.getLevel().getBlockState(context.getClickedPos()).getMaterial().isSolid() &&
					context.getLevel().getBlockState(new BlockPos(context.getClickedPos().getX(), context.getClickedPos().getY() + 1,
									context.getClickedPos().getZ())).getMaterial().isLiquid()) {
				context.getLevel().setBlock(
						new BlockPos(context.getClickedPos().getX(), context.getClickedPos().getY() + 1, context.getClickedPos().getZ()),
						AquamiraeBlocks.OXYGELIUM.get().defaultBlockState().setValue(OxygeliumBlock.TYPE, OxygeliumBlock.Type.EMPTY_BUD)
								.setValue(BlockStateProperties.WATERLOGGED, Boolean.TRUE), 3);
				context.getItemInHand().shrink(1);
				return InteractionResult.SUCCESS;
			}
			if (context.getLevel()
					.getBlockState(new BlockPos(context.getClickedPos().getX(), context.getClickedPos().getY() + 1, context.getClickedPos().getZ()))
					.getMaterial().isLiquid()
					&& (context.getLevel().getBlockState(context.getClickedPos()).getBlock() == AquamiraeBlocks.OXYGELIUM.get())) {
				context.getLevel().setBlock(
						new BlockPos(context.getClickedPos().getX(), context.getClickedPos().getY(), context.getClickedPos().getZ()),
						AquamiraeBlocks.OXYGELIUM.get().defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, Boolean.TRUE), 3);
				context.getLevel().setBlock(
						new BlockPos(context.getClickedPos().getX(), context.getClickedPos().getY() + 1, context.getClickedPos().getZ()),
						AquamiraeBlocks.OXYGELIUM.get().defaultBlockState().setValue(OxygeliumBlock.TYPE, OxygeliumBlock.Type.EMPTY_BUD)
								.setValue(BlockStateProperties.WATERLOGGED, Boolean.TRUE), 3);
				context.getItemInHand().shrink(1);
				return InteractionResult.SUCCESS;
			}
		}
		return InteractionResult.FAIL;
	}
}
