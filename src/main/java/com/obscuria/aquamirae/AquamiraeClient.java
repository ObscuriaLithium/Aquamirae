package com.obscuria.aquamirae;

import com.obscuria.aquamirae.client.AquamiraeLayers;
import com.obscuria.aquamirae.client.AquamiraeRenderers;
import com.obscuria.aquamirae.network.ScrollUsePacket;
import com.obscuria.aquamirae.registry.AquamiraeSounds;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.MusicSound;
import net.minecraft.sound.SoundCategory;

import java.util.Random;

public final class AquamiraeClient implements ClientModInitializer {
    private static int biomeMusic = 0;
    private static int corneliaMusic = 0;

    @Override
    public void onInitializeClient() {
        AquamiraeLayers.register();
        AquamiraeRenderers.register();

        ClientPlayNetworking.registerGlobalReceiver(ScrollUsePacket.TYPE, (packet, player, responseSender) -> scrollSound());

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (MinecraftClient.getInstance().player == null || MinecraftClient.getInstance().isPaused()) return;
            if (biomeMusic > 0) biomeMusic--;
            if (corneliaMusic > 0) corneliaMusic--;
            final PlayerEntity player = MinecraftClient.getInstance().player;
            if (AquamiraeUtils.isInIceMaze(player)) {
                playAmbientSounds(player, true);
                playBiomeMusic(player);
                spawnParticles(player);
            }
        });
    }

    public static boolean isStackHeld(ItemStack stack) {
        return MinecraftClient.getInstance().player != null && MinecraftClient.getInstance().player.getMainHandStack() == stack;
    }

    public static void showFloatingItem(ItemStack stack) {
        MinecraftClient.getInstance().gameRenderer.showFloatingItem(stack);
    }

    public static void scrollSound() {
        if (MinecraftClient.getInstance().player == null) return;
        MinecraftClient.getInstance().player.playSound(AquamiraeSounds.EFFECT_MYSTERY, 1f, 1f);
    }

    public static void spawnParticles(PlayerEntity player) {
       player.getWorld().addParticle(ParticleTypes.WHITE_ASH, false,
                player.getX() - 6D + 12D * player.getRandom().nextDouble(), player.getY() + 4 - 3D * player.getRandom().nextDouble(),
                player.getZ() - 6D + 12D * player.getRandom().nextDouble(), 0, 100, 0);
    }

    public static void playAmbientSounds(PlayerEntity player, boolean random) {
        if (!random || (Math.random() <= 0.01 && Math.random() <= 0.1))
            player.getWorld().playSound(
                    player.getBlockX() - 10 + 20*Math.random(),
                    player.getBlockY(),
                    player.getBlockZ() - 10 + 20*Math.random(),
                    AquamiraeSounds.AMBIENT_SHIP_HORN, SoundCategory.AMBIENT, 1, 1, false);
    }

    public static void playCorneliaMusic(PlayerEntity player) {
        if (player.isAlive() && corneliaMusic <= 0) {
            corneliaMusic = 20 * 150;
            MinecraftClient.getInstance().getMusicTracker().stop();
            MinecraftClient.getInstance().getMusicTracker().play(new MusicSound(RegistryEntry.of(
                    AquamiraeSounds.MUSIC_FORSAKEN_DROWNAGE), 10, 100, true));
        }
    }

    private static void playBiomeMusic(PlayerEntity player) {
        if (player.isAlive() && biomeMusic <= 0 && corneliaMusic <= 0) {
            biomeMusic = 20 * new Random().nextInt(180, 300);
            MinecraftClient.getInstance().getMusicTracker().stop();
            MinecraftClient.getInstance().getMusicTracker().play(new MusicSound(RegistryEntry.of(
                    AquamiraeSounds.MUSIC_ICE_MAZE_THEME), 10, 100, true));
        }
    }
}
