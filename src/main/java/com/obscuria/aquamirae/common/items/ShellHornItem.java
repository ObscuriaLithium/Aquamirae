package com.obscuria.aquamirae.common.items;

import com.obscuria.aquamirae.AquamiraeUtils;
import com.obscuria.aquamirae.common.DelayedEvents;
import com.obscuria.aquamirae.registry.AquamiraeSounds;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ShellHornItem extends Item {
	public ShellHornItem(Settings settings) {
		super(settings);
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		if (world instanceof ServerWorld server)
			server.playSound(null, user.getBlockPos().up(), AquamiraeSounds.ITEM_SHELL_HORN_USE, SoundCategory.PLAYERS, 3, 1);
		final ItemStack stack = user.getStackInHand(hand);
		user.swingHand(hand);
		user.getItemCooldownManager().set(stack.getItem(), 120);
		boolean summon = false;
		BlockPos pos = BlockPos.ORIGIN;
		searchWater: for (int ix = -6; ix <= 6; ix++) {
			final int sx = user.getBlockX() + ix;
			for (int iz = -6; iz <= 6; iz++) {
				final int sz = user.getBlockZ() + iz;
				if (AquamiraeUtils.isInIceMaze(user)) {
					if (world.getBlockState(new BlockPos(sx, 62, sz)).getBlock() == Blocks.WATER
							&& (user.getWorld().getBlockState(new BlockPos(sx, 58, sz))).getBlock() == Blocks.WATER
							&& (user.getWorld().getBlockState(new BlockPos(sx - 1, 62, sz))).getBlock() == Blocks.WATER
							&& (user.getWorld().getBlockState(new BlockPos(sx + 1, 62, sz))).getBlock() == Blocks.WATER
							&& (user.getWorld().getBlockState(new BlockPos(sx, 62, sz - 1))).getBlock() == Blocks.WATER
							&& (user.getWorld().getBlockState(new BlockPos(sx, 62, sz + 1))).getBlock() == Blocks.WATER) {
						summon = true;
						pos = new BlockPos(sx, 58, sz);
						stack.decrement(1);
						break searchWater;
					}
				}
			}
		}
		if (!world.isClient()) DelayedEvents.createCorneliaEvent(user, pos, summon);
		return summon ? TypedActionResult.consume(stack) : TypedActionResult.success(stack, !world.isClient());
	}
}
