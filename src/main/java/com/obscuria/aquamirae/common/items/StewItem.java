
package com.obscuria.aquamirae.common.items;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class StewItem extends Item {
	public StewItem(Item.Properties properties) {
		super(properties);
	}

	@Override
	public int getUseDuration(@NotNull ItemStack itemstack) {
		return 40;
	}

	@Override
	public @NotNull ItemStack finishUsingItem(@NotNull ItemStack itemstack, @NotNull Level world, @NotNull LivingEntity entity) {
		ItemStack bowl = new ItemStack(Items.BOWL);
		super.finishUsingItem(itemstack, world, entity);
		if (itemstack.isEmpty()) return bowl;
		else if (entity instanceof Player player && !player.getAbilities().instabuild)
			if (!player.getInventory().add(bowl)) player.drop(bowl, false);
		return itemstack;
	}
}
