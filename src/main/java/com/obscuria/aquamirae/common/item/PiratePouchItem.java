
package com.obscuria.aquamirae.common.item;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.registry.AquamiraeSounds;
import com.obscuria.core.api.annotation.SimpleLore;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;

@SimpleLore("lore.aquamirae.pirate_pouch")
public class PiratePouchItem extends Item {
	public PiratePouchItem() {
		super(new Item.Properties().stacksTo(16).rarity(Rarity.COMMON));
	}

	@Override
	public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level world, @NotNull Player entity, @NotNull InteractionHand hand) {
		InteractionResultHolder<ItemStack> ar = super.use(world, entity, hand);
		ItemStack sourceStack = ar.getObject();
		entity.swing(hand);
		if (entity.level() instanceof ServerLevel level)
			level.playSound(null, entity.blockPosition().above(), AquamiraeSounds.ITEM_PIRATE_POUCH_OPEN.get(),
					SoundSource.PLAYERS, 1, 1);
		final List<ItemStack> loot = Aquamirae.SetBuilder.common();
		entity.addItem(loot.get(new Random().nextInt(0, loot.size() - 1)));
		sourceStack.shrink(1);
		return ar;
	}
}
