
package com.obscuria.aquamirae.common.items;

import com.obscuria.aquamirae.AquamiraeClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class ShipGraveyardEchoItem extends Item {
	public ShipGraveyardEchoItem(Settings settings) {
		super(settings);
	}

	@Override
	public boolean hasGlint(ItemStack itemstack) {
		return true;
	}

	@Override
	public void inventoryTick(@NotNull ItemStack itemstack, @NotNull World world, @NotNull Entity entity, int slot, boolean selected) {
		super.inventoryTick(itemstack, world, entity, slot, selected);
		if (selected && entity instanceof PlayerEntity player && player.getWorld().isClient() && Math.random() <= 0.01)
			AquamiraeClient.playAmbientSounds(player, false);
	}
}
