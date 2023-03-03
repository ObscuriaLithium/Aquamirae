
package com.obscuria.aquamirae.common.items;

import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.client.AquamiraeAmbient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

public class ShipGraveyardEchoItem extends Item {
	public ShipGraveyardEchoItem() {
		super(new Item.Properties().tab(AquamiraeMod.TAB).stacksTo(64).rarity(Rarity.UNCOMMON));
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean isFoil(@Nonnull ItemStack itemstack) {
		return true;
	}

	@Override
	public void inventoryTick(@Nonnull ItemStack itemstack, @Nonnull World world, @Nonnull Entity entity, int slot, boolean selected) {
		super.inventoryTick(itemstack, world, entity, slot, selected);
		if (selected && entity instanceof PlayerEntity && entity.level.isClientSide() && Math.random() <= 0.01)
			AquamiraeAmbient.playAmbientSounds((PlayerEntity) entity, false);
	}
}