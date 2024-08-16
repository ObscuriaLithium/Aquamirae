package com.obscuria.aquamirae;

import com.obscuria.aquamirae.client.AquamiraeBossBars;
import com.obscuria.aquamirae.client.AquamiraeLayers;
import com.obscuria.aquamirae.client.AquamiraeRenderers;
import com.obscuria.aquamirae.client.screen.DeadSeaCurseToast;
import com.obscuria.aquamirae.common.entity.CaptainCornelia;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import com.obscuria.aquamirae.registry.AquamiraeSounds;
import com.obscuria.core.ObscureAPIClient;
import com.obscuria.core.common.signal.RuntimeSignals;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public final class AquamiraeClient {

    public static @Nullable Player getLocalPlayer() {
        return Minecraft.getInstance().player;
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
        ObscureAPIClient.MUSIC_EXTENSION.suggest(200, () -> AquamiraeSounds.GAME_MUSIC_FORSAKEN_DROWNAGE);
    }

    static void setup(IEventBus bus) {
        bus.addListener(AquamiraeClient::onClientSetup);
        bus.addListener(AquamiraeLayers::register);
        bus.addListener(AquamiraeRenderers::registerEntityRenderers);
        bus.addListener(AquamiraeRenderers::registerParticles);

        ObscureAPIClient.BOSS_BAR_EXTENSION.register(Aquamirae.key("captain_cornelia"), new AquamiraeBossBars.CaptainCornelia());
        ObscureAPIClient.CREATIVE_TAB_EXTENSION.register(Aquamirae.key("aquamirae"),
                () -> Optional.of(Aquamirae.key("textures/gui/creative/background.png")),
                () -> Optional.of(Aquamirae.key("textures/gui/creative/tabs.png")));

        RuntimeSignals.ON_CLIENT_TICK.connect(AquamiraeClient::onClientTick);
    }

    private static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> ItemProperties.register(AquamiraeItems.SHELL_HORN.get(), Aquamirae.key("tooting"),
                (stack, level, entity, i) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1 : 0));
    }

    private static void onClientTick() {
        final var player = Minecraft.getInstance().player;
        if (player == null) return;
        if (Aquamirae.isInIceMaze(player)) {
            ObscureAPIClient.MUSIC_EXTENSION.suggest(() -> player.isUnderWater()
                    ? AquamiraeSounds.GAME_MUSIC_SHIP_GRAVEYARD
                    : AquamiraeSounds.GAME_MUSIC_ICE_MAZE);
            if (Minecraft.getInstance().isPaused()) return;
            spawnBiomeParticles(player);
            if (AquamiraeConfig.Client.ambientSounds.get() &&
                    player.getRandom().nextInt(2000) <= 1)
                playShipHornSound(player);
        }
    }
}
