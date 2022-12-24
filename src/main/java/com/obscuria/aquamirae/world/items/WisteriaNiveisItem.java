
package com.obscuria.aquamirae.world.items;

import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.registry.AquamiraeBlocks;
import com.obscuria.aquamirae.world.blocks.WisteriaNiveisBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import org.jetbrains.annotations.NotNull;

public class WisteriaNiveisItem extends Item {
	public WisteriaNiveisItem() {
		super(new Item.Properties().tab(AquamiraeMod.TAB).stacksTo(64).rarity(Rarity.COMMON));
	}

	@Override
	public @NotNull InteractionResult useOn(UseOnContext context) {
		final BlockPos pos = context.getClickedPos();
		if (context.getClickedFace() == Direction.UP && AquamiraeBlocks.WISTERIA_NIVEIS.get() instanceof WisteriaNiveisBlock wisteriaBlock &&
				wisteriaBlock.canBePlacedOn(context.getLevel(), pos)) {
			context.getLevel().playSound(context.getPlayer(), context.getClickedPos(), SoundEvents.WEEPING_VINES_PLACE, SoundSource.BLOCKS, 1F, 1F);
			context.getLevel().setBlock(pos.above(), AquamiraeBlocks.WISTERIA_NIVEIS.get().defaultBlockState()
					.setValue(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER).setValue(WisteriaNiveisBlock.LOOT, false), 3);
			context.getItemInHand().shrink(1);
			return InteractionResult.SUCCESS;
		}
		return InteractionResult.FAIL;
	}
}
