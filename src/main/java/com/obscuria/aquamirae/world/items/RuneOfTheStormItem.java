
package com.obscuria.aquamirae.world.items;

import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.obscureapi.utils.ItemHelper;
import com.obscuria.obscureapi.world.classes.ObscureRarity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;

@Mod.EventBusSubscriber
public class RuneOfTheStormItem extends Item {
	public RuneOfTheStormItem() {
		super(new Item.Properties().tab(AquamiraeMod.TAB).stacksTo(1).fireResistant().rarity(ObscureRarity.MYTHIC));
	}

	@SubscribeEvent
	public static void onLivingDamage(LivingDamageEvent event) {
		final LivingEntity source = event.getSource().getEntity() instanceof LivingEntity ? (LivingEntity) event.getSource().getEntity() : null;
		if (source == null) return;
		final ItemStack weapon = source.getMainHandItem();
		if (ItemHelper.hasPerk(weapon, "rune_of_the_storm") && source.level.getBiome(source.blockPosition()).getBaseTemperature() * 100f <= 0) {
			event.setAmount(event.getAmount() * 1.33F);
		}
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean isFoil(@Nonnull ItemStack itemstack) {
		return true;
	}

	@Override
	@Nonnull
	public ActionResult<ItemStack> use(@Nonnull World world, PlayerEntity entity, @Nonnull Hand hand) {
		final ItemStack stack = entity.getItemInHand(hand);
		final ItemStack offhand = entity.getItemInHand(Hand.OFF_HAND);
		if (hand != Hand.MAIN_HAND)
			return ActionResult.fail(stack);
		if (offhand.getItem() instanceof SwordItem && !ItemHelper.hasPerk(offhand, "rune_of_the_storm")) {
			if (world instanceof ServerWorld) world.playSound(null, entity.blockPosition(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 2, 1);
			entity.swing(hand);
			stack.shrink(1);
			ItemHelper.addPerk(offhand, "rune_of_the_storm", 1);
			return ActionResult.success(stack);
		}
		return ActionResult.fail(stack);
	}
}
