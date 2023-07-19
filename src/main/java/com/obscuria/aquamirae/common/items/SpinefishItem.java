package com.obscuria.aquamirae.common.items;

import com.obscuria.aquamirae.registry.AquamiraeItems;
import com.obscuria.obscureapi.api.utils.PlayerUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class SpinefishItem extends Item {
	public SpinefishItem(Item.Settings settings) {
		super(settings);
	}

	@Override
	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
		final ItemStack bone = new ItemStack(AquamiraeItems.SHARP_BONES);
		super.finishUsing(stack, world, user);
		if (stack.isEmpty()) return bone;
		else if (user instanceof PlayerEntity player && !player.getAbilities().creativeMode)
			PlayerUtils.giveItem(player, bone);
		return stack;
	}
}
