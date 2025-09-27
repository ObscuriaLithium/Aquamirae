
package com.obscuria.aquamirae.common.items;

import com.obscuria.aquamirae.registry.AquamiraeItems;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class SpinefishItem extends Item {
	public SpinefishItem(Item.Properties properties) {
		super(properties);
	}

	@Override
	public @NotNull ItemStack finishUsingItem(@NotNull ItemStack itemstack, @NotNull Level world, @NotNull LivingEntity entity) {
		ItemStack bone = new ItemStack(AquamiraeItems.SHARP_BONES.get());
		super.finishUsingItem(itemstack, world, entity);
		if (itemstack.isEmpty()) return bone;
		else if (entity instanceof Player player && !player.getAbilities().instabuild)
			if (!player.getInventory().add(bone)) player.drop(bone, false);
		return itemstack;
	}
}
