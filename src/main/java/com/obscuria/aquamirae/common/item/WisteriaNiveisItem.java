
package com.obscuria.aquamirae.common.item;

import com.obscuria.aquamirae.common.block.WisteriaNiveisBlock;
import com.obscuria.aquamirae.registry.AquamiraeBlocks;
import com.obscuria.core.common.item.Lore;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

@Lore("lore.aquamirae.wisteria_niveis")
public class WisteriaNiveisItem extends Item {
	public WisteriaNiveisItem() {
		super(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON));
	}

	@Override
	public @NotNull InteractionResult useOn(UseOnContext context) {
		if (context.getClickedFace() != Direction.UP) return InteractionResult.FAIL;
		final var level = context.getLevel();
		final var pos = context.getClickedPos();
		if (!WisteriaNiveisBlock.canBePlacedOn(level, pos)) return InteractionResult.FAIL;
		level.playSound(context.getPlayer(), pos, SoundEvents.WEEPING_VINES_PLACE, SoundSource.BLOCKS, 1f, 1f);
		WisteriaNiveisBlock.placeAt(level, this.wisteria(), pos.above(), Block.UPDATE_ALL);
		context.getItemInHand().shrink(1);
		return InteractionResult.SUCCESS;
	}

	private BlockState wisteria() {
		return AquamiraeBlocks.WISTERIA_NIVEIS.get().defaultBlockState()
				.setValue(WisteriaNiveisBlock.NATURAL, false);
	}
}
