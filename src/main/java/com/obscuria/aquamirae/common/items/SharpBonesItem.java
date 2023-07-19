
package com.obscuria.aquamirae.common.items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class SharpBonesItem extends Item {
	public SharpBonesItem(Settings settings) {
		super(settings);
	}

	@Override
	public int getMaxUseTime(ItemStack stack) {
		return 24;
	}

	@Override
	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
		user.damage(user.getDamageSources().starve(), 1);
		return super.finishUsing(stack, world, user);
	}
}
