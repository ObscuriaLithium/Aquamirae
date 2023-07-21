package com.obscuria.aquamirae.common.items;

import com.obscuria.aquamirae.AquamiraeClient;
import com.obscuria.aquamirae.common.DelayedEvents;
import com.obscuria.aquamirae.registry.AquamiraeSounds;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class DeadSeaScrollItem extends Item {
	public DeadSeaScrollItem(Settings settings) {
		super(settings);
	}

	@Override
	public boolean hasGlint(ItemStack stack) {
		return AquamiraeClient.isStackHeld(stack);
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		final ItemStack stack = user.getStackInHand(hand);
		if (world.isClient()) AquamiraeClient.showFloatingItem(stack);
		world.playSound(user.getBlockX(), user.getBlockY(), user.getBlockZ(), AquamiraeSounds.ITEM_SCROLL_USE, SoundCategory.PLAYERS, 1, 1, false);
		user.getItemCooldownManager().set(stack.getItem(), 100);
		user.swingHand(hand);
		if (!world.isClient()) DelayedEvents.createScrollEvent(user);
		stack.decrement(1);
		return TypedActionResult.success(stack);
	}
}
