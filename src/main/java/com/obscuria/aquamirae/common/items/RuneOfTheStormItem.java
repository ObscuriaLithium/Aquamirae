
package com.obscuria.aquamirae.common.items;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.obscureapi.api.utils.ItemUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class RuneOfTheStormItem extends Item {
	public RuneOfTheStormItem(Settings settings) {
		super(settings);
	}

	@Override
	public boolean hasGlint(ItemStack stack) {
		return true;
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		final ItemStack stack = user.getStackInHand(hand);
		final ItemStack offhand = user.getStackInHand(Hand.OFF_HAND);
		if (hand != Hand.MAIN_HAND)
			return TypedActionResult.fail(stack);
		if (offhand.getItem() instanceof SwordItem && !ItemUtils.hasPerk(offhand, new Identifier(Aquamirae.MODID, "rune_of_the_storm"))) {
			if (world instanceof ServerWorld server) server.playSound(null, user.getBlockPos(),
					SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 2, 1);
			ItemUtils.addPerk(offhand, new Identifier(Aquamirae.MODID, "rune_of_the_storm"), 1);
			user.swingHand(hand);
			stack.decrement(1);
			return TypedActionResult.success(stack);
		}
		return TypedActionResult.fail(stack);
	}

	public static float calculateDamageBonus(LivingEntity entity, float damage) {
		return entity.getWorld().getBiome(entity.getBlockPos()).value().isCold(entity.getBlockPos()) ? damage * 0.33f : 0;
	}
}
