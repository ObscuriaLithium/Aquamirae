package com.obscuria.aquamirae;

import com.obscuria.aquamirae.client.AquamiraeBossBars;
import com.obscuria.aquamirae.client.AquamiraeLayers;
import com.obscuria.aquamirae.client.AquamiraeRenderers;
import com.obscuria.aquamirae.client.screen.ConfigScreen;
import com.obscuria.aquamirae.client.screen.DeadSeaCurseToast;
import com.obscuria.aquamirae.common.entity.CaptainCornelia;
import com.obscuria.aquamirae.registry.AquamiraeSounds;
import com.obscuria.core.api.util.signal.RuntimeSignals;
import com.obscuria.core.api.ObscureAPIClient;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import org.jetbrains.annotations.ApiStatus;

import java.util.Optional;
import java.util.function.Supplier;

public final class AquamiraeClient {

    @ApiStatus.Internal
    public static void setup(IEventBus bus) {
        bus.addListener(AquamiraeLayers::register);
        bus.addListener(AquamiraeRenderers::registerEntityRenderers);
        bus.addListener(AquamiraeRenderers::registerParticles);

        ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, getConfig());
        ObscureAPIClient.BOSS_BAR_STYLES.register(Aquamirae.key("captain_cornelia"), new AquamiraeBossBars.CaptainCornelia());
        ObscureAPIClient.CREATIVE_TAB_STYLES.register(Aquamirae.key("aquamirae"),
                () -> Optional.of(Aquamirae.key("textures/gui/creative/background.png")),
                () -> Optional.of(Aquamirae.key("textures/gui/creative/tabs.png")));

        RuntimeSignals.ON_CLIENT_TICK.connect(AquamiraeClient::onClientTick);
    }

    public static Supplier<ConfigScreenHandler.ConfigScreenFactory> getConfig() {
        return () -> new ConfigScreenHandler.ConfigScreenFactory((minecraft, screen) -> new ConfigScreen(screen));
    }

    public static boolean isLocalPlayer(Player player) {
        return Minecraft.getInstance().player == player;
    }

    public static void showCurseToast(ItemStack stack) {
        Minecraft.getInstance().getToasts().addToast(new DeadSeaCurseToast(stack));
    }

    public static void spawnBiomeParticles(Player player) {
        if (!AquamiraeConfig.Client.particles.get()) return;
        player.level().addParticle(ParticleTypes.WHITE_ASH, false,
                player.getX() + player.getRandom().triangle(0, 6),
                player.getY() + player.getRandom().triangle(2.5, 1.5),
                player.getZ() + player.getRandom().triangle(0, 6),
                0, 100, 0);
    }

    public static void playScrollSound() {
        if (Minecraft.getInstance().player == null) return;
        Minecraft.getInstance().player.playSound(AquamiraeSounds.ITEM_SCROLL_MYSTERY.get());
    }

    public static void playShipHornSound(Player player) {
        final var random = player.getRandom();
        player.level().playLocalSound(
                player.getBlockX() + random.triangle(0, 10), player.getBlockY(),
                player.getBlockZ() + random.triangle(0, 10),
                AquamiraeSounds.AMBIENT_SHIP_HORN.get(), SoundSource.AMBIENT, 1, 1, false);
    }

    public static void playCorneliaMusic(CaptainCornelia entity) {
        final var player = Minecraft.getInstance().player;
        if (player == null) return;
        if (player.position().distanceTo(entity.position()) > 128) return;
        ObscureAPIClient.GAME_MUSICS.suggest(200, () -> AquamiraeSounds.GAME_MUSIC_FORSAKEN_DROWNAGE);
    }

    private static void onClientTick() {
        final var player = Minecraft.getInstance().player;
        if (player == null) return;
        if (Aquamirae.isInIceMaze(player)) {
            ObscureAPIClient.GAME_MUSICS.suggest(() -> player.isUnderWater()
                    ? AquamiraeSounds.GAME_MUSIC_SHIP_GRAVEYARD
                    : AquamiraeSounds.GAME_MUSIC_ICE_MAZE);
            if (Minecraft.getInstance().isPaused()) return;
            spawnBiomeParticles(player);
            if (AquamiraeConfig.Client.ambientSounds.get() &&
                    player.getRandom().nextInt(1200) <= 1)
                playShipHornSound(player);
        }
    }
}
