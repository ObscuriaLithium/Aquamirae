
package com.obscuria.aquamirae.common.items;

import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import com.obscuria.aquamirae.registry.AquamiraeSounds;
import com.obscuria.aquamirae.common.events.ScrollEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class DeadSeaScrollItem extends Item {
	public DeadSeaScrollItem() {
		super(new Item.Properties().tab(AquamiraeMod.TAB).stacksTo(8).rarity(Rarity.UNCOMMON));
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean isFoil(ItemStack stack) {
		return Minecraft.getInstance().player != null && Minecraft.getInstance().player.getMainHandItem().getItem() == AquamiraeItems.DEAD_SEA_SCROLL.get();
	}

	@Override
	public ActionResult<ItemStack> use(World level, PlayerEntity player, Hand hand) {
		ActionResult<ItemStack> resultHolder = super.use(level, player, hand);
		if (level.isClientSide) Minecraft.getInstance().gameRenderer.displayItemActivation(resultHolder.getObject().copy());
		level.playLocalSound(player.getX(), player.getY(), player.getZ(), AquamiraeSounds.ITEM_SCROLL_USE.get(), SoundCategory.PLAYERS, 1, 1, false);
		player.getCooldowns().addCooldown(resultHolder.getObject().getItem(), 100);
		resultHolder.getObject().shrink(1); player.swing(hand);
		ScrollEffects.create(player);
		return resultHolder;
	}
}
