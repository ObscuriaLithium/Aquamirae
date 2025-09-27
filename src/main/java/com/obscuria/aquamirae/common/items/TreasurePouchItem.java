
package com.obscuria.aquamirae.common.items;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.registry.AquamiraeSounds;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootParams;
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
			world.playSound(player, player.blockPosition().above(),
					AquamiraeSounds.ITEM_TREASURE_POUCH_OPEN.get(), SoundSource.PLAYERS, 1, 1);
			final List<ItemStack> loot = Aquamirae.SetBuilder.rare();
			player.addItem(loot.get(player.getRandom().nextInt(0, loot.size() - 1)));
			final MinecraftServer minecraftServer = player.level().getServer();
			if (minecraftServer != null && player.level() instanceof ServerLevel server) {
				LootParams lootContext = new LootParams.Builder(server)
						.withParameter(LootContextParams.THIS_ENTITY, player)
						.withParameter(LootContextParams.ORIGIN, player.position())
						.create(LootContextParamSets.GIFT);
				LootTable treasure = minecraftServer.getLootData().getLootTable(new ResourceLocation(Aquamirae.MODID, "gameplay/treasure_pouch"));
				treasure.getRandomItems(lootContext).forEach(player::addItem);
				if (Math.random() <= 0.1f)
					player.addItem(Aquamirae.getStructureMap(player.getRandom().nextBoolean() ? Aquamirae.SHIP : Aquamirae.OUTPOST, server, player));
			}
		}
		stack.shrink(1);
		return resultHolder;
	}
}
