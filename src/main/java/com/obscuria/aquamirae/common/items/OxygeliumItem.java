
package com.obscuria.aquamirae.common.items;

import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.registry.AquamiraeBlocks;
import com.obscuria.aquamirae.common.blocks.OxygeliumBlock;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Rarity;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

public class OxygeliumItem extends Item {
	public OxygeliumItem() {
		super(new Item.Properties().tab(AquamiraeMod.TAB).stacksTo(64).rarity(Rarity.COMMON));
	}

	@Override
	public ActionResultType useOn(ItemUseContext context) {
		if (context.getClickedFace() == Direction.UP) {
			if (context.getLevel().getBlockState(context.getClickedPos()).getMaterial().isSolid() &&
					context.getLevel().getBlockState(new BlockPos(context.getClickedPos().getX(), context.getClickedPos().getY() + 1,
									context.getClickedPos().getZ())).getMaterial().isLiquid()) {
				context.getLevel().setBlock(
						new BlockPos(context.getClickedPos().getX(), context.getClickedPos().getY() + 1, context.getClickedPos().getZ()),
						AquamiraeBlocks.OXYGELIUM.get().defaultBlockState().setValue(OxygeliumBlock.TYPE, OxygeliumBlock.Type.EMPTY_BUD)
								.setValue(BlockStateProperties.WATERLOGGED, Boolean.TRUE), 3);
				context.getItemInHand().shrink(1);
				return ActionResultType.SUCCESS;
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
				return ActionResultType.SUCCESS;
			}
		}
		return ActionResultType.FAIL;
	}
}
