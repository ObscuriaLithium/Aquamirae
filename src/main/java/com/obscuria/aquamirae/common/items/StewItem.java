
package com.obscuria.aquamirae.common.items;

import com.obscuria.obscureapi.api.utils.PlayerUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;

public class StewItem extends Item {
	public StewItem(Settings settings) {
		super(settings);
	}

	@Override
	public int getMaxUseTime(ItemStack stack) {
		return 40;
	}

	@Override
	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
		final ItemStack bowl = new ItemStack(Items.BOWL);
		super.finishUsing(stack, world, user);
		if (stack.isEmpty()) return bowl;
		else if (user instanceof PlayerEntity player && !player.getAbilities().creativeMode)
			PlayerUtils.giveItem(player, bowl);
		return stack;
	}
}
