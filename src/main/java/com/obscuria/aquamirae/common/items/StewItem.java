
package com.obscuria.aquamirae.common.items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;

public class StewItem extends Item {
	public StewItem(Item.Properties properties) {
		super(properties);
	}

	@Override
	public int getUseDuration(ItemStack itemstack) {
		return 40;
	}

	@Override
	public ItemStack finishUsingItem(ItemStack itemstack, World world, LivingEntity entity) {
		ItemStack bowl = new ItemStack(Items.BOWL);
		super.finishUsingItem(itemstack, world, entity);
		if (itemstack.isEmpty()) return bowl;
		else if (entity instanceof PlayerEntity) {
			final PlayerEntity player = (PlayerEntity) entity;
			if (!player.abilities.instabuild && !player.inventory.add(bowl)) player.drop(bowl, false);
		}
		return itemstack;
	}
}
