
package com.obscuria.aquamirae.common.item;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.common.entity.CaptainCornelia;
import com.obscuria.aquamirae.registry.AquamiraeEntities;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import com.obscuria.aquamirae.registry.AquamiraeSounds;
import com.obscuria.core.api.graphic.Icons;
import com.obscuria.core.api.util.signal.RuntimeSignals;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ShellHornItem extends Item {
	public ShellHornItem() {
		super(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON));
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
		Aquamirae.addIconTooltip(Icons.SKULL, Component.translatable("lore.aquamirae.common.summons_cornelia")
				.withStyle(ChatFormatting.LIGHT_PURPLE), tooltip);
		Aquamirae.addTooltip(Component.translatable("lore.aquamirae.shell_horn"), tooltip);
	}

	@Override
	public UseAnim getUseAnimation(ItemStack stack) {
		return UseAnim.TOOT_HORN;
	}

	@Override
	public int getUseDuration(ItemStack stack) {
		return 60;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		return ItemUtils.startUsingInstantly(level, player, hand);
	}

	@Override
	public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int tick) {
		if (level instanceof ServerLevel serverLevel && entity instanceof Player player) {
			if (tick == 55) {
				player.getCooldowns().addCooldown(AquamiraeItems.SHELL_HORN.get(), 120);
				player.level().playSound(null, player.blockPosition().above(),
						AquamiraeSounds.ITEM_SHELL_HORN_USE.get(),
						SoundSource.PLAYERS, 3, 1);
			} else if (tick == 1) {
				final var pos = searchPosition(level, player.blockPosition());
				if (pos != BlockPos.ZERO) {
					if (!player.getAbilities().instabuild) stack.shrink(1);
					player.awardStat(Stats.ITEM_USED.get(this));
				}
				Timer.create(player, serverLevel, pos);
			}
		}
	}

	private BlockPos searchPosition(Level level, BlockPos origin) {
		for (int x = -9; x <= 9; x++)
			for (int z = -9; z <= 9; z++) {
				final var ox = origin.getX();
				final var oz = origin.getZ();
				final var pos = new BlockPos(ox + x, level.getHeight(Heightmap.Types.WORLD_SURFACE, ox, oz), oz + z);
				if (isWaterIn(level, pos.below())) return pos;
			}
		return BlockPos.ZERO;
	}

	private boolean isWaterIn(Level level, BlockPos pos) {
		for (int x = -1; x <= 1; x++)
			for (int y = -4; y <= 0; y++)
				for (int z = -1; z <= 1; z++)
					if (!level.isWaterAt(pos.offset(x, y, z)))
						return false;
		return true;
	}

	private record Timer(Player player, ServerLevel level, BlockPos pos, AtomicInteger ticks) {
		private static final int DELAY = 20;

		private static void create(Player player, ServerLevel level, BlockPos pos) {
			final var timer = new Timer(player, level, pos, new AtomicInteger(0));
			RuntimeSignals.ON_SERVER_TICK.connect(timer, timer::tick);
		}

		private void tick() {
			if (this.ticks.addAndGet(1) >= DELAY) {
				if (pos == BlockPos.ZERO) {
					player.sendSystemMessage(Component.empty().withStyle(ChatFormatting.RED)
							.append(Icons.INFO.toComponent())
							.append(Component.literal(" "))
							.append(Component.translatable("info.aquamirae.nothing_happened")));
				} else {
					final var cornelia = new CaptainCornelia(AquamiraeEntities.CAPTAIN_CORNELIA.get(), level);
					cornelia.moveTo(pos.getX() + 0.5, pos.getY() - 4, pos.getZ() + 0.5,
							level.getRandom().nextFloat() * 360F, 0);
					cornelia.finalizeSpawn(level, level.getCurrentDifficultyAt(cornelia.blockPosition()),
							MobSpawnType.MOB_SUMMONED, null, null);
					level.addFreshEntity(cornelia);
					for (var player : level.getEntitiesOfClass(Player.class, new AABB(pos).inflate(64)))
						player.sendSystemMessage(Component.empty().withStyle(ChatFormatting.LIGHT_PURPLE)
								.append(Icons.SKULL.toComponent())
								.append(Component.literal(" "))
								.append(Component.translatable("info.aquamirae.cornelia_awakened")));
				}
				RuntimeSignals.ON_SERVER_TICK.disconnect(this);
			}
		}
	}
}
