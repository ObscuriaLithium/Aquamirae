
package com.obscuria.aquamirae.world.items;

import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.obscureapi.utils.ItemUtils;
import com.obscuria.obscureapi.world.items.ObscureRarity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

@Mod.EventBusSubscriber
public class RuneOfTheStormItem extends Item {
	public RuneOfTheStormItem() {
		super(new Item.Properties().tab(AquamiraeMod.TAB).stacksTo(1).fireResistant().rarity(ObscureRarity.MYTHIC));
	}

	@SubscribeEvent
	public static void onLivingDamage(@NotNull LivingDamageEvent event) {
		final LivingEntity source = event.getSource().getEntity() instanceof LivingEntity living ? living : null;
		if (source == null) return;
		final ItemStack weapon = source.getMainHandItem();
		if (ItemUtils.hasPerk(weapon, new ResourceLocation("aquamirae", "rune_of_the_storm"))
				&& source.getLevel().getBiome(source.blockPosition()).value().getBaseTemperature() * 100f <= 0) {
			event.setAmount(event.getAmount() * 1.33F);
		}
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean isFoil(@NotNull ItemStack itemstack) {
		return true;
	}

	@Override
	public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level world, Player entity, @NotNull InteractionHand hand) {
		final ItemStack stack = entity.getItemInHand(hand);
		final ItemStack offhand = entity.getItemInHand(InteractionHand.OFF_HAND);
		if (hand != InteractionHand.MAIN_HAND)
			return InteractionResultHolder.fail(stack);
		if (offhand.getItem() instanceof SwordItem && !ItemUtils.hasPerk(offhand, new ResourceLocation("aquamirae", "rune_of_the_storm"))) {
			if (world instanceof ServerLevel level)
				level.playSound(null, entity.blockPosition(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 2, 1);
			entity.swing(hand);
			stack.shrink(1);
			ItemUtils.addPerk(offhand, new ResourceLocation("aquamirae", "rune_of_the_storm"), 1);
			return InteractionResultHolder.success(stack);
		}
		return InteractionResultHolder.fail(stack);
	}
}
