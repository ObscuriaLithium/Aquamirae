
package com.obscuria.aquamirae.common.items;

import com.obscuria.aquamirae.registry.AquamiraeItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class SpinefishItem extends Item {
	public SpinefishItem(Item.Properties properties) {
		super(properties);
	}

	@Override
	public ItemStack finishUsingItem(ItemStack itemstack, World world, LivingEntity entity) {
		ItemStack bone = new ItemStack(AquamiraeItems.SHARP_BONES.get());
		super.finishUsingItem(itemstack, world, entity);
		if (itemstack.isEmpty()) return bone;
		else if (entity instanceof PlayerEntity) {
			final PlayerEntity player = (PlayerEntity) entity;
			if (!player.abilities.instabuild && !player.inventory.add(bone)) player.drop(bone, false);
		}
		return itemstack;
	}
}
