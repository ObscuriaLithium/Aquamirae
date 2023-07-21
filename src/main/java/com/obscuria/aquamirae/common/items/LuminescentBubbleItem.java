
package com.obscuria.aquamirae.common.items;

import com.obscuria.aquamirae.registry.AquamiraeBlocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class LuminescentBubbleItem extends Item {
	public LuminescentBubbleItem(Settings settings) {
		super(settings);
	}

	@SuppressWarnings("deprecation")
	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		final ItemStack stack = user.getStackInHand(hand);
		if (world.getBlockState(user.getBlockPos().up()).isLiquid()) {
			world.setBlockState(user.getBlockPos().up(), AquamiraeBlocks.LUMINESCENT_BUBBLE.getDefaultState()
					.with(Properties.WATERLOGGED, true), 3);
			if (world instanceof ServerWorld server) server.playSound(null, user.getBlockPos().up(),
					SoundEvents.BLOCK_BUBBLE_COLUMN_BUBBLE_POP, SoundCategory.BLOCKS, 2, 1);
			stack.decrement(1);
			return TypedActionResult.success(stack);
		}
		return TypedActionResult.fail(stack);
	}
}
