
package com.obscuria.aquamirae.common.item;

import com.obscuria.aquamirae.common.ScrollEffects;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import com.obscuria.aquamirae.registry.AquamiraeSounds;
import com.obscuria.core.api.graphic.Icons;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DeadSeaScrollItem extends Item {
	public DeadSeaScrollItem() {
		super(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON));
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
		tooltip.add(Component.empty().withStyle(ChatFormatting.LIGHT_PURPLE)
				.append(Icons.SKULL.toComponent())
				.append(Component.literal(" "))
				.append(Component.translatable("lore.aquamirae.dead_sea_scroll")));
	}

	@Override
	public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
		InteractionResultHolder<ItemStack> resultHolder = super.use(level, player, hand);
		if (level.isClientSide) Minecraft.getInstance().gameRenderer.displayItemActivation(resultHolder.getObject().copy());
		level.playLocalSound(player.getBlockX(), player.getBlockY(), player.getBlockZ(), AquamiraeSounds.ITEM_SCROLL_USE.get(), SoundSource.PLAYERS, 1, 1, false);
		player.getCooldowns().addCooldown(resultHolder.getObject().getItem(), 100);
		resultHolder.getObject().shrink(1); player.swing(hand);
		ScrollEffects.create(player);
		return resultHolder;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean isFoil(@NotNull ItemStack stack) {
		return Minecraft.getInstance().player != null && Minecraft.getInstance().player.getMainHandItem().getItem() == AquamiraeItems.DEAD_SEA_SCROLL.get();
	}
}
