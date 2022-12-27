
package com.obscuria.aquamirae.world.items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class StewItem extends Item {
	public StewItem(Item.Properties properties) {
		super(properties);
	}

	@Override
	public int getUseDuration(@Nonnull ItemStack itemstack) {
		return 40;
	}

	@Override
	@Nonnull
	public ItemStack finishUsingItem(@Nonnull ItemStack itemstack, @Nonnull World world, @Nonnull LivingEntity entity) {
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
