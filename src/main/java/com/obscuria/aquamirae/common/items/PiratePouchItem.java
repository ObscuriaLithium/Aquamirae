
package com.obscuria.aquamirae.common.items;

import com.obscuria.aquamirae.AquamiraeMod;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;

import java.util.Random;
import java.util.List;

import com.obscuria.aquamirae.registry.AquamiraeSounds;
import org.jetbrains.annotations.NotNull;

public class PiratePouchItem extends Item {
	public PiratePouchItem() {
		super(new Item.Properties().tab(AquamiraeMod.TAB).stacksTo(16).rarity(Rarity.COMMON));
	}

	@Override
	public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level world, @NotNull Player entity, @NotNull InteractionHand hand) {
		InteractionResultHolder<ItemStack> ar = super.use(world, entity, hand);
		ItemStack sourceStack = ar.getObject();
		entity.swing(hand);
		if (entity.getLevel() instanceof ServerLevel level)
			level.playSound(null, new BlockPos(entity.getX(), entity.getY() + 1, entity.getZ()), AquamiraeSounds.ITEM_POUCH_OPEN.get(),
					SoundSource.PLAYERS, 1, 1);
		final List<ItemStack> loot = AquamiraeMod.SetBuilder.common();
		entity.addItem(loot.get(new Random().nextInt(0, loot.size() - 1)));
		sourceStack.shrink(1);
		return ar;
	}
}
