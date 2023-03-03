
package com.obscuria.aquamirae.common.items;

import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.registry.AquamiraeSounds;
import com.obscuria.aquamirae.registry.AquamiraeStructures;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.LootTable;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nonnull;
import java.util.List;

public class TreasurePouchItem extends Item {
	public TreasurePouchItem() {
		super(new Item.Properties().tab(AquamiraeMod.TAB).stacksTo(16).rarity(Rarity.UNCOMMON));
	}

	@Override
	@Nonnull
	public ActionResult<ItemStack> use(@Nonnull World world, @Nonnull PlayerEntity player, @Nonnull Hand hand) {
		ActionResult<ItemStack> resultHolder = super.use(world, player, hand);
		ItemStack stack = resultHolder.getObject();
		player.swing(hand);
		if (!world.isClientSide) {
			world.playSound(player, new BlockPos(player.getX(), player.getY() + 1, player.getZ()),
					AquamiraeSounds.ITEM_TREASURE_POUCH_OPEN.get(), SoundCategory.PLAYERS, 1, 1);
			final List<ItemStack> loot = AquamiraeMod.SetBuilder.rare();
			player.addItem(loot.get(player.getRandom().nextInt(loot.size() - 1)));
			final MinecraftServer minecraftServer = player.level.getServer();
			if (minecraftServer != null && player.level instanceof ServerWorld) {
				final ServerWorld server = (ServerWorld) player.level;
				LootContext lootContext = new LootContext.Builder(server)
						.withRandom(player.getRandom())
						.withParameter(LootParameters.THIS_ENTITY, player)
						.withParameter(LootParameters.ORIGIN, player.position())
						.create(LootParameterSets.GIFT);
				LootTable treasure = minecraftServer.getLootTables().get(new ResourceLocation(AquamiraeMod.MODID, "gameplay/treasure_pouch"));
				treasure.getRandomItems(lootContext).forEach(player::addItem);
				if (Math.random() <= 0.1f) player.addItem(AquamiraeMod.getStructureMap(player.getRandom().nextBoolean() ?
						AquamiraeStructures.SHIP.get() : AquamiraeStructures.OUTPOST.get(), server, player));
			}
		}
		stack.shrink(1);
		return resultHolder;
	}
}
