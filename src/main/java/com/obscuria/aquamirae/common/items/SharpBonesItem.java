
package com.obscuria.aquamirae.common.items;

import com.obscuria.aquamirae.AquamiraeMod;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class SharpBonesItem extends Item {
	public SharpBonesItem() {
		super(new Item.Properties().tab(AquamiraeMod.TAB).stacksTo(64).rarity(Rarity.COMMON)
				.food((new Food.Builder()).nutrition(1).saturationMod(0f).meat().build()));
	}

	@Override
	public int getUseDuration(ItemStack itemstack) {
		return 24;
	}

	@Override
	public ItemStack finishUsingItem(ItemStack itemstack, World world, LivingEntity entity) {
		entity.hurt(DamageSource.STARVE, 1);
		return super.finishUsingItem(itemstack, world, entity);
	}
}
