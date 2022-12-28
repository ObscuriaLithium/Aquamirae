package com.obscuria.aquamirae.client;

import com.obscuria.aquamirae.AquamiraeConfig;
import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.registry.AquamiraeSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.BackgroundMusicSelector;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber(value = {Dist.CLIENT})
public class AquamiraeAmbient {
	private static int biomeMusic = 0;
	private static int corneliaMusic = 0;

	@SubscribeEvent
	public static void tick(TickEvent.ClientTickEvent event) {
		if (event.phase != TickEvent.Phase.END || Minecraft.getInstance().player == null || Minecraft.getInstance().isPaused()) return;
		if (biomeMusic > 0) biomeMusic--;
		if (corneliaMusic > 0) corneliaMusic--;
		final PlayerEntity player = Minecraft.getInstance().player;
		if (AquamiraeMod.ICE_MAZE.contains(player.level.getBiome(player.blockPosition()).getRegistryName())) {
			playAmbientSounds(player, true);
			playBiomeMusic(player);
			spawnParticles(player);
		}
	}

	public static void spawnParticles(PlayerEntity player) {
		if (AquamiraeConfig.Client.partictes.get()) player.level.addParticle(ParticleTypes.WHITE_ASH, false,
				player.getX() - 6D + 12D * player.getRandom().nextDouble(), player.getY() + 4 - 3D * player.getRandom().nextDouble(),
				player.getZ() - 6D + 12D * player.getRandom().nextDouble(), 0, 100, 0);
	}

	public static void playAmbientSounds(PlayerEntity player, boolean config) {
		if (config) { if (AquamiraeConfig.Client.ambientSounds.get() && Math.random() <= 0.01 && Math.random() <= 0.1)
				player.level.playLocalSound(player.getX() - 10 + 20 * Math.random(), player.getY(),
						player.getZ() - 10 + 20 * Math.random(),
						AquamiraeSounds.AMBIENT_SHIP_HORN.get(), SoundCategory.AMBIENT, 1, 1, false);
		} else player.level.playLocalSound(player.getX() - 10 + 20 * Math.random(), player.getY(),
				player.getZ() - 10 + 20 * Math.random(),
				AquamiraeSounds.AMBIENT_SHIP_HORN.get(), SoundCategory.AMBIENT, 1, 1, false);
	}

	public static void playCorneliaMusic(PlayerEntity player) {
		if (AquamiraeConfig.Client.bossMusic.get() && player.isAlive() && corneliaMusic <= 0) {
			corneliaMusic = 20 * 150;
			Minecraft.getInstance().getMusicManager().stopPlaying();
			Minecraft.getInstance().getMusicManager().startPlaying(
					new BackgroundMusicSelector(AquamiraeSounds.MUSIC_FORSAKEN_DROWNAGE.get(), 10, 100, true));
		}
	}

	private static void playBiomeMusic(PlayerEntity player) {
		if (AquamiraeConfig.Client.biomeMusic.get() && player.isAlive() && biomeMusic <= 0 && corneliaMusic <= 0) {
			biomeMusic = 20 * (180 + new Random().nextInt(300));
			Minecraft.getInstance().getMusicManager().stopPlaying();
			Minecraft.getInstance().getMusicManager().startPlaying(
					new BackgroundMusicSelector(AquamiraeSounds.MUSIC_ICE_MAZE_THEME.get(), 10, 100, true));
		}
	}
}
