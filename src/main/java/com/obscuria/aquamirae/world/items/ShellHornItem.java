
package com.obscuria.aquamirae.world.items;

import com.obscuria.aquamirae.registry.AquamiraeEntities;
import com.obscuria.aquamirae.registry.AquamiraeSounds;
import com.obscuria.aquamirae.world.entities.CaptainCornelia;
import com.obscuria.obscureapi.utils.EventHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

public class ShellHornItem extends Item {
	public ShellHornItem() {
		super(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON));
	}

	@Override
	public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level world, @NotNull Player entity, @NotNull InteractionHand hand) {
		InteractionResultHolder<ItemStack> ar = super.use(world, entity, hand);
		if (entity.getLevel() instanceof ServerLevel level)
			level.playSound(null, new BlockPos(entity.getX(), entity.getY() + 1, entity.getZ()),
					AquamiraeSounds.ITEM_SHELL_HORN_USE.get(), SoundSource.PLAYERS, 3, 1);
		ItemStack stack = ar.getObject();
		entity.swing(InteractionHand.MAIN_HAND, true);
		entity.getCooldowns().addCooldown(stack.getItem(), 120);
		boolean summon = false;
		BlockPos pos = new BlockPos(0, 0, 0);
		waterSearch : for (int ix = -6; ix <= 6; ix++) {
			final int sx = entity.getBlockX() + ix;
			for (int iz = -6; iz <= 6; iz++) {
				final int sz = entity.getBlockZ() + iz;
				if (entity.getLevel().getBiome(new BlockPos(sx, 58, sz)).is(new ResourceLocation("deep_frozen_ocean"))) {
					if ((entity.getLevel().getBlockState(new BlockPos(sx, 62, sz))).getBlock() == Blocks.WATER
							&& (entity.getLevel().getBlockState(new BlockPos(sx, 58, sz))).getBlock() == Blocks.WATER
							&& (entity.getLevel().getBlockState(new BlockPos(sx - 1, 62, sz))).getBlock() == Blocks.WATER
							&& (entity.getLevel().getBlockState(new BlockPos(sx + 1, 62, sz))).getBlock() == Blocks.WATER
							&& (entity.getLevel().getBlockState(new BlockPos(sx, 62, sz - 1))).getBlock() == Blocks.WATER
							&& (entity.getLevel().getBlockState(new BlockPos(sx, 62, sz + 1))).getBlock() == Blocks.WATER) {
						summon = true;
						pos = new BlockPos(sx, 58, sz);
						stack.shrink(1);
						entity.getInventory().setChanged();
						break waterSearch;
					}
				}
			}
		}
		new Object() {
			private int ticks = 0; private float waitTicks; private Player summoner; private BlockPos pos; private boolean summon;

			public void start(int waitTicks, Player summoner, BlockPos pos, boolean summon) {
				this.waitTicks = waitTicks;
				this.summoner = summoner;
				this.pos = pos;
				this.summon = summon;
				MinecraftForge.EVENT_BUS.register(this);
			}

			@SubscribeEvent
			public void tick(TickEvent.ServerTickEvent event) {
				if (event.phase == TickEvent.Phase.END) {
					this.ticks += 1;
					if (this.ticks >= this.waitTicks) {
						if (summon) { spawn();
						} else if (!summoner.getLevel().isClientSide()) {
							EventHelper.sendMessage(summoner, Component.translatable("icon.boss").getString() + ""
									+ Component.translatable("info.captain_spawn_fail").getString());
						}
						MinecraftForge.EVENT_BUS.unregister(this);
					}
				}
			}

			private void spawn() {
				if (summoner.getLevel() instanceof ServerLevel server) {
					Mob cornelia = new CaptainCornelia(AquamiraeEntities.CAPTAIN_CORNELIA.get(), server);
					cornelia.moveTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, summoner.getLevel().getRandom().nextFloat() * 360F, 0);
					cornelia.finalizeSpawn(server, summoner.getLevel().getCurrentDifficultyAt(cornelia.blockPosition()), MobSpawnType.MOB_SUMMONED,
							null, null);
					summoner.getLevel().addFreshEntity(cornelia);
				}
				if (!summoner.getLevel().isClientSide()) {
					EventHelper.sendMessage(summoner,
							Component.translatable("icon.boss").getString() + "" + Component.translatable("info.captain_spawn").getString());
				}
			}
		}.start(60, entity, pos, summon);
		return ar;
	}
}
