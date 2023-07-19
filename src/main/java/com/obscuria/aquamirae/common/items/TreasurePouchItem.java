
package com.obscuria.aquamirae.common.items;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.registry.AquamiraeSounds;
import com.obscuria.obscureapi.api.utils.PlayerUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;

public class TreasurePouchItem extends Item {
	public TreasurePouchItem(Settings settings) {
		super(settings);
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		final TypedActionResult<ItemStack> result = super.use(world, user, hand);
		final ItemStack stack = result.getValue();
		user.swingHand(hand);
		if (!world.isClient()) {
			world.playSound(null, user.getBlockPos().up(), AquamiraeSounds.ITEM_TREASURE_POUCH_OPEN, SoundCategory.PLAYERS, 1, 1);
			final List<ItemStack> loot = Aquamirae.SetBuilder.rare();
			PlayerUtils.giveItem(user, loot.get(user.getRandom().nextBetween(0, loot.size()-1)));
			final MinecraftServer minecraftServer = user.getServer();
			if (minecraftServer != null && user.getWorld() instanceof ServerWorld server) {
				final LootContextParameterSet context = new LootContextParameterSet.Builder(server)
						.add(LootContextParameters.THIS_ENTITY, user)
						.add(LootContextParameters.ORIGIN, user.getPos())
						.build(LootContextTypes.GIFT);
				final LootTable treasure = minecraftServer.getLootManager().getLootTable(new Identifier(Aquamirae.MODID, "gameplay/treasure_pouch"));
				treasure.generateLoot(context).forEach(item -> PlayerUtils.giveItem(user, item));
				if (Math.random() <= 0.1f) PlayerUtils.giveItem(user, Aquamirae.createStructureMap(user.getRandom().nextBoolean()
						? Aquamirae.SHIP : Aquamirae.OUTPOST, server, user));
			}
		}
		stack.decrement(1);
		return result;
	}
}
