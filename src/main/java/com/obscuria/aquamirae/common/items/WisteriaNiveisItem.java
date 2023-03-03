
package com.obscuria.aquamirae.common.items;

import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.registry.AquamiraeBlocks;
import com.obscuria.aquamirae.common.blocks.WisteriaNiveisBlock;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Rarity;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;

public class WisteriaNiveisItem extends Item {
	public WisteriaNiveisItem() {
		super(new Item.Properties().tab(AquamiraeMod.TAB).stacksTo(64).rarity(Rarity.COMMON));
	}

	@Override
	public ActionResultType useOn(ItemUseContext context) {
		final BlockPos pos = context.getClickedPos();
		if (context.getClickedFace() == Direction.UP && AquamiraeBlocks.WISTERIA_NIVEIS.get() instanceof WisteriaNiveisBlock &&
				((WisteriaNiveisBlock)AquamiraeBlocks.WISTERIA_NIVEIS.get()).canBePlacedOn(context.getLevel(), pos)) {
			context.getLevel().playSound(context.getPlayer(), context.getClickedPos(), SoundEvents.WEEPING_VINES_PLACE, SoundCategory.BLOCKS, 1F, 1F);
			context.getLevel().setBlock(pos.above(), AquamiraeBlocks.WISTERIA_NIVEIS.get().defaultBlockState()
					.setValue(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER).setValue(WisteriaNiveisBlock.LOOT, false), 3);
			context.getItemInHand().shrink(1);
			return ActionResultType.SUCCESS;
		}
		return ActionResultType.FAIL;
	}
}
