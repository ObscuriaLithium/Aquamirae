
package com.obscuria.aquamirae.common.items;

import com.obscuria.aquamirae.common.blocks.OxygeliumBlock;
import com.obscuria.aquamirae.registry.AquamiraeBlocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.Direction;

public class OxygeliumItem extends Item {
	public OxygeliumItem(Settings settings) {
		super(settings);
	}

	@SuppressWarnings("deprecation")
	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		if (context.getSide() == Direction.UP) {
			if (context.getWorld().getBlockState(context.getBlockPos()).isSolid() &&
					context.getWorld().getBlockState(context.getBlockPos().up()).isLiquid()) {
				context.getWorld().setBlockState(context.getBlockPos().up(), AquamiraeBlocks.OXYGELIUM.getDefaultState()
						.with(OxygeliumBlock.TYPE, OxygeliumBlock.Type.EMPTY_BUD).with(Properties.WATERLOGGED, Boolean.TRUE), 3);
				context.getStack().decrement(1);
				return ActionResult.SUCCESS;
			}
			if (context.getWorld().getBlockState(context.getBlockPos().up()).isLiquid()
					&& (context.getWorld().getBlockState(context.getBlockPos()).getBlock() instanceof OxygeliumBlock)) {
				context.getWorld().setBlockState(context.getBlockPos(), AquamiraeBlocks.OXYGELIUM.getDefaultState()
						.with(Properties.WATERLOGGED, Boolean.TRUE), 3);
				context.getWorld().setBlockState(context.getBlockPos().up(), AquamiraeBlocks.OXYGELIUM.getDefaultState()
						.with(OxygeliumBlock.TYPE, OxygeliumBlock.Type.EMPTY_BUD).with(Properties.WATERLOGGED, Boolean.TRUE), 3);
				context.getStack().decrement(1);
				return ActionResult.SUCCESS;
			}
		}
		return ActionResult.FAIL;
	}
}
