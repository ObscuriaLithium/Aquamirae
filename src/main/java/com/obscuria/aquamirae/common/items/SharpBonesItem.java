
package com.obscuria.aquamirae.common.items;

import com.obscuria.aquamirae.Aquamirae;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class SharpBonesItem extends Item {
	public SharpBonesItem() {
		super(new Item.Properties().tab(Aquamirae.TAB).stacksTo(64).rarity(Rarity.COMMON)
				.food((new FoodProperties.Builder()).nutrition(1).saturationMod(0f).meat().build()));
	}

	@Override
	public int getUseDuration(@NotNull ItemStack itemstack) {
		return 24;
	}

	@Override
	public @NotNull ItemStack finishUsingItem(@NotNull ItemStack itemstack, @NotNull Level world, LivingEntity entity) {
		entity.hurt(DamageSource.STARVE, 1);
		return super.finishUsingItem(itemstack, world, entity);
	}
}
