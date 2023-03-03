
package com.obscuria.aquamirae.common.items;

import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.registry.AquamiraeBlocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nonnull;

public class LuminescentBubbleItem extends Item {
	public LuminescentBubbleItem() {
		super(new Item.Properties().tab(AquamiraeMod.TAB).stacksTo(16).rarity(Rarity.COMMON));
	}

	@Override
	@Nonnull
	public ActionResult<ItemStack> use(World world, PlayerEntity entity, @Nonnull Hand hand) {
		ItemStack stack = entity.getItemInHand(hand);
		if (world.getBlockState(new BlockPos(entity.getX(), entity.getY() + 1, entity.getZ())).getMaterial().isLiquid()) {
			stack.shrink(1);
			world.setBlock(new BlockPos(entity.getX(), entity.getY() + 1, entity.getZ()),
					AquamiraeBlocks.LUMINESCENT_BUBBLE.get().defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true), 3);
			if (world instanceof ServerWorld) world.playSound(null, new BlockPos(entity.getX(), entity.getY() + 1, entity.getZ()),
						SoundEvents.BUBBLE_COLUMN_BUBBLE_POP, SoundCategory.BLOCKS, 2, 1);
			return ActionResult.success(stack);
		}
		return ActionResult.fail(stack);
	}
}
