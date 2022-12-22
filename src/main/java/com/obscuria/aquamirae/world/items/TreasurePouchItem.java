
package com.obscuria.aquamirae.world.items;

import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.registry.AquamiraeSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TreasurePouchItem extends Item {
	public TreasurePouchItem() {
		super(new Item.Properties().stacksTo(16).rarity(Rarity.UNCOMMON));
	}

	@Override
	public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level world, @NotNull Player player, @NotNull InteractionHand hand) {
		InteractionResultHolder<ItemStack> resultHolder = super.use(world, player, hand);
		ItemStack stack = resultHolder.getObject();
		player.swing(hand);
		if (!world.isClientSide) {
			world.playSound(player, new BlockPos(player.getX(), player.getY() + 1, player.getZ()), AquamiraeSounds.ITEM_TREASURE_POUCH_OPEN.get(), SoundSource.PLAYERS, 1, 1);
			final List<ItemStack> loot = AquamiraeMod.LootBuilder.rare();
			player.addItem(loot.get(player.getRandom().nextInt(0, loot.size() - 1)));
			//
			final MinecraftServer minecraftServer = player.level.getServer();
			if (minecraftServer != null && player.level instanceof ServerLevel server) {
				ResourceLocation location = new ResourceLocation(AquamiraeMod.MODID, "gameplay/treasure_pouch");
				LootTable lootTable = minecraftServer.getLootTables().get(location);
				LootContext.Builder lootContext$Builder = new LootContext.Builder(server).withRandom(player.getRandom())
						.withParameter(LootContextParams.THIS_ENTITY, player).withParameter(LootContextParams.ORIGIN, player.position())
						.withParameter(LootContextParams.DAMAGE_SOURCE, DamageSource.OUT_OF_WORLD);
				LootContext lootContext = lootContext$Builder.create(LootContextParamSets.ENTITY);
				lootTable.getRandomItems(lootContext).forEach(player::addItem);
			}
		}
		//
		stack.shrink(1);
		return resultHolder;
	}
}
