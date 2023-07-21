
package com.obscuria.aquamirae.common.items;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.registry.AquamiraeSounds;
import com.obscuria.obscureapi.api.utils.PlayerUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;

public class PiratePouchItem extends Item {
	public PiratePouchItem(Settings settings) {
		super(settings);
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		final TypedActionResult<ItemStack> result = super.use(world, user, hand);
		final ItemStack stack = result.getValue();
		user.swingHand(hand);
		if (world instanceof ServerWorld server) server.playSound(null, user.getBlockPos().up(),
				AquamiraeSounds.ITEM_POUCH_OPEN, SoundCategory.PLAYERS, 1, 1);
		final List<ItemStack> loot = Aquamirae.SetBuilder.common();
		PlayerUtils.giveItem(user, loot.get(user.getRandom().nextBetween(0, loot.size()-1)));
		stack.decrement(1);
		return TypedActionResult.success(stack);
	}
}
