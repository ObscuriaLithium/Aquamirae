
package com.obscuria.aquamirae.common.items;

import com.obscuria.aquamirae.common.blocks.WisteriaNiveisBlock;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;

public class WisteriaNiveisItem extends Item {
	public WisteriaNiveisItem(Settings settings) {
		super(settings);
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		final BlockPos pos = context.getBlockPos();
		if (WisteriaNiveisBlock.tryPlace(context.getWorld(), pos, false)) {
			context.getWorld().playSound(context.getPlayer(), context.getBlockPos(), SoundEvents.BLOCK_WEEPING_VINES_PLACE, SoundCategory.BLOCKS, 1F, 1F);
			context.getStack().decrement(1);
			return ActionResult.SUCCESS;
		}
		return ActionResult.FAIL;
	}
}
