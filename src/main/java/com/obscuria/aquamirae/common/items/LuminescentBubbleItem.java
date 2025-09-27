
package com.obscuria.aquamirae.common.items;

import com.obscuria.aquamirae.registry.AquamiraeBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.NotNull;

public class LuminescentBubbleItem extends Item {
	public LuminescentBubbleItem() {
		super(new Item.Properties().stacksTo(16).rarity(Rarity.COMMON));
	}

	@Override
	public @NotNull InteractionResultHolder<ItemStack> use(Level world, Player entity, @NotNull InteractionHand hand) {
		ItemStack stack = entity.getItemInHand(hand);
		if (world.getBlockState(new BlockPos(entity.getBlockX(), entity.getBlockY() + 1, entity.getBlockZ())).liquid()) {
			stack.shrink(1);
			world.setBlock(new BlockPos(entity.getBlockX(), entity.getBlockY() + 1, entity.getBlockZ()),
					AquamiraeBlocks.LUMINESCENT_BUBBLE.get().defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true), 3);
			if (world instanceof ServerLevel level) level.playSound(null, entity.blockPosition().above(),
						SoundEvents.BUBBLE_COLUMN_BUBBLE_POP, SoundSource.BLOCKS, 2, 1);
			return InteractionResultHolder.success(stack);
		}
		return InteractionResultHolder.fail(stack);
	}
}
