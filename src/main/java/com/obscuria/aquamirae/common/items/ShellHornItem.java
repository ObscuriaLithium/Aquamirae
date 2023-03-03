
package com.obscuria.aquamirae.common.items;

import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.registry.AquamiraeEntities;
import com.obscuria.aquamirae.registry.AquamiraeSounds;
import com.obscuria.aquamirae.common.entities.CaptainCornelia;
import com.obscuria.obscureapi.utils.EventHelper;
import com.obscuria.obscureapi.utils.TextHelper;
import net.minecraft.block.Blocks;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
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
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ShellHornItem extends Item {
	public ShellHornItem() {
		super(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON).tab(AquamiraeMod.TAB));
	}

	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity entity, Hand hand) {
		ActionResult<ItemStack> ar = super.use(world, entity, hand);
		if (entity.level instanceof ServerWorld)
			entity.level.playSound(null, new BlockPos(entity.getX(), entity.getY() + 1, entity.getZ()),
					AquamiraeSounds.ITEM_SHELL_HORN_USE.get(), SoundCategory.PLAYERS, 3, 1);
		ItemStack stack = ar.getObject();
		entity.swing(Hand.MAIN_HAND, true);
		entity.getCooldowns().addCooldown(stack.getItem(), 120);
		boolean summon = false;
		BlockPos pos = new BlockPos(0, 0, 0);
		waterSearch : for (int ix = -6; ix <= 6; ix++) {
			final int sx = entity.blockPosition().getX() + ix;
			for (int iz = -6; iz <= 6; iz++) {
				final int sz = entity.blockPosition().getZ() + iz;
				if (AquamiraeMod.ICE_MAZE.contains(entity.level.getBiome(new BlockPos(sx, entity.level.getSeaLevel() - 5, sz)).getRegistryName())) {
					if ((entity.level.getBlockState(new BlockPos(sx, entity.level.getSeaLevel() - 1, sz))).getBlock() == Blocks.WATER
							&& (entity.level.getBlockState(new BlockPos(sx, entity.level.getSeaLevel() - 5, sz))).getBlock() == Blocks.WATER
							&& (entity.level.getBlockState(new BlockPos(sx - 1, entity.level.getSeaLevel() - 1, sz))).getBlock() == Blocks.WATER
							&& (entity.level.getBlockState(new BlockPos(sx + 1, entity.level.getSeaLevel() - 1, sz))).getBlock() == Blocks.WATER
							&& (entity.level.getBlockState(new BlockPos(sx, entity.level.getSeaLevel() - 1, sz - 1))).getBlock() == Blocks.WATER
							&& (entity.level.getBlockState(new BlockPos(sx, entity.level.getSeaLevel() - 1, sz + 1))).getBlock() == Blocks.WATER) {
						summon = true;
						pos = new BlockPos(sx, 58, sz);
						stack.shrink(1);
						entity.inventory.setChanged();
						break waterSearch;
					}
				}
			}
		}
		new Object() {
			private int ticks = 0; private float waitTicks; private PlayerEntity summoner; private BlockPos pos; private boolean summon;

			public void start(int waitTicks, PlayerEntity summoner, BlockPos pos, boolean summon) {
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
						} else if (!summoner.level.isClientSide()) {
							EventHelper.sendMessage(summoner, TextHelper.translation("icon.boss") + ""
									+ TextHelper.translation("info.captain_spawn_fail"));
						}
						MinecraftForge.EVENT_BUS.unregister(this);
					}
				}
			}

			private void spawn() {
				if (summoner.level instanceof ServerWorld) {
					final ServerWorld server = (ServerWorld) summoner.level;
					MobEntity cornelia = new CaptainCornelia(AquamiraeEntities.CAPTAIN_CORNELIA.get(), server);
					cornelia.moveTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, summoner.level.getRandom().nextFloat() * 360F, 0);
					cornelia.finalizeSpawn(server, summoner.level.getCurrentDifficultyAt(cornelia.blockPosition()), SpawnReason.MOB_SUMMONED,
							null, null);
					summoner.level.addFreshEntity(cornelia);
				}
				if (!summoner.level.isClientSide()) {
					EventHelper.sendMessage(summoner,
							TextHelper.translation("icon.boss") + "" + TextHelper.translation("info.captain_spawn"));
				}
			}
		}.start(60, entity, pos, summon);
		return ar;
	}
}
