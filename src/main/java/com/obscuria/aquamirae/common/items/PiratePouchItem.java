
package com.obscuria.aquamirae.common.items;

import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.registry.AquamiraeSounds;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

public class PiratePouchItem extends Item {
	public PiratePouchItem() {
		super(new Item.Properties().tab(AquamiraeMod.TAB).stacksTo(16).rarity(Rarity.COMMON));
	}

	@Override
	@Nonnull
	public ActionResult<ItemStack> use(@Nonnull World world, @Nonnull PlayerEntity entity, @Nonnull Hand hand) {
		ActionResult<ItemStack> ar = super.use(world, entity, hand);
		ItemStack sourceStack = ar.getObject();
		entity.swing(hand);
		if (entity.level instanceof ServerWorld)
			entity.level.playSound(null, new BlockPos(entity.getX(), entity.getY() + 1, entity.getZ()), AquamiraeSounds.ITEM_POUCH_OPEN.get(),
					SoundCategory.PLAYERS, 1, 1);
		final List<ItemStack> loot = AquamiraeMod.SetBuilder.common();
		entity.addItem(loot.get(new Random().nextInt(loot.size() - 1)));
		sourceStack.shrink(1);
		return ar;
	}
}
