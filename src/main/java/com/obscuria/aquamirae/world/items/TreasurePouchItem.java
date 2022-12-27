
package com.obscuria.aquamirae.world.items;

import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.registry.AquamiraeSounds;
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
			world.playSound(player, new BlockPos(player.getX(), player.getY() + 1, player.getZ()), AquamiraeSounds.ITEM_TREASURE_POUCH_OPEN.get(), SoundCategory.PLAYERS, 1, 1);
			final List<ItemStack> loot = AquamiraeMod.LootBuilder.rare();
			player.addItem(loot.get(player.getRandom().nextInt(0, loot.size() - 1)));
			//
			final MinecraftServer minecraftServer = player.level.getServer();
			if (minecraftServer != null && player.level instanceof ServerWorld) {
				final ServerWorld server = (ServerWorld) player.level;
				ResourceLocation location = new ResourceLocation(AquamiraeMod.MODID, "gameplay/treasure_pouch");
				LootTable lootTable = minecraftServer.getLootTables().get(location);
				LootContext.Builder lootContext$Builder = new LootContext.Builder(server).withRandom(player.getRandom())
						.withParameter(LootParameters.THIS_ENTITY, player).withParameter(LootParameters.ORIGIN, player.position())
						.withParameter(LootParameters.DAMAGE_SOURCE, DamageSource.OUT_OF_WORLD);
				LootContext lootContext = lootContext$Builder.create(LootParameterSets.ENTITY);
				lootTable.getRandomItems(lootContext).forEach(player::addItem);
			}
		}
		//
		stack.shrink(1);
		return resultHolder;
	}
}
