package com.obscuria.aquamirae.client.screen;

import com.obscuria.aquamirae.AquamiraeConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class ConfigScreen extends Screen {

    private final Screen PARENT;
    private CycleButton<Boolean> overlay;
    private CycleButton<Boolean> particles;
    private CycleButton<Boolean> ambientSounds;
    private CycleButton<Boolean> biomeMusic;
    private CycleButton<Boolean> bossMusic;
    private CycleButton<Boolean> stylizedBossbar;
    private CycleButton<Boolean> notifications;

    public ConfigScreen(Screen parent) {
        super(Component.literal("Aquamirae Settings"));
        this.PARENT = parent;
    }

    @Override
    protected void init() {
        super.init();
        this.overlay = CycleButton.onOffBuilder(AquamiraeConfig.Client.overlay.get())
                .create(this.width / 2 - 155, this.height / 6 + 24 - 6,
                310, 20, Component.literal("Helmet Overlay"));
        this.particles = CycleButton.onOffBuilder(AquamiraeConfig.Client.particles.get())
                .create(this.width / 2 - 155, this.height / 6 + 48 - 6,
                        150, 20, Component.literal("Biome Particles"));
        this.ambientSounds = CycleButton.onOffBuilder(AquamiraeConfig.Client.ambientSounds.get())
                .create(this.width / 2 + 5, this.height / 6 + 48 - 6,
                        150, 20, Component.literal("Ambient Sounds"));
        this.biomeMusic = CycleButton.onOffBuilder(AquamiraeConfig.Client.biomeMusic.get())
                .create(this.width / 2 - 155, this.height / 6 + 72 - 6,
                        150, 20, Component.literal("Biome Music"));
        this.bossMusic = CycleButton.onOffBuilder(AquamiraeConfig.Client.bossMusic.get())
                .create(this.width / 2 + 5, this.height / 6 + 72 - 6,
                        150, 20, Component.literal("Boss Music"));
        this.stylizedBossbar = CycleButton.onOffBuilder(AquamiraeConfig.Client.stylizedBossbar.get())
                .create(this.width / 2 - 155, this.height / 6 + 108 - 6,
                        150, 20, Component.literal("Stylized Boss Bar"));
        this.notifications = CycleButton.onOffBuilder(AquamiraeConfig.Common.notifications.get())
                .create(this.width / 2 + 5, this.height / 6 + 108 - 6,
                        150, 20, Component.literal("Chat Notifications"));
        this.addRenderableWidget(overlay);
        this.addRenderableWidget(particles);
        this.addRenderableWidget(ambientSounds);
        this.addRenderableWidget(biomeMusic);
        this.addRenderableWidget(bossMusic);
        this.addRenderableWidget(stylizedBossbar);
        this.addRenderableWidget(notifications);

        this.addRenderableWidget(new Button.Builder(Component.literal("Reset"), b -> {
            AquamiraeConfig.Client.overlay.set(AquamiraeConfig.Client.overlay.getDefault());
            AquamiraeConfig.Client.particles.set(AquamiraeConfig.Client.particles.getDefault());
            AquamiraeConfig.Client.ambientSounds.set(AquamiraeConfig.Client.ambientSounds.getDefault());
            AquamiraeConfig.Client.biomeMusic.set(AquamiraeConfig.Client.biomeMusic.getDefault());
            AquamiraeConfig.Client.bossMusic.set(AquamiraeConfig.Client.bossMusic.getDefault());
            AquamiraeConfig.Client.stylizedBossbar.set(AquamiraeConfig.Client.stylizedBossbar.getDefault());
            AquamiraeConfig.Common.notifications.set(AquamiraeConfig.Common.notifications.getDefault());
            this.overlay.setValue(AquamiraeConfig.Client.overlay.get());
            this.particles.setValue(AquamiraeConfig.Client.particles.get());
            this.ambientSounds.setValue(AquamiraeConfig.Client.ambientSounds.get());
            this.biomeMusic.setValue(AquamiraeConfig.Client.biomeMusic.get());
            this.bossMusic.setValue(AquamiraeConfig.Client.bossMusic.get());
            this.stylizedBossbar.setValue(AquamiraeConfig.Client.stylizedBossbar.get());
            this.notifications.setValue(AquamiraeConfig.Common.notifications.get());
        }).pos(this.width / 2 - 155, this.height / 6 + 24 * 7 - 6).size(100, 20).build());
        this.addRenderableWidget(new Button.Builder(CommonComponents.GUI_DONE, b -> {
            this.save();
            Minecraft.getInstance().setScreen(this.PARENT);
        }).pos(this.width / 2 - 45, this.height / 6 + 24 * 7 - 6).size(200, 20).build());
    }

    @Override
    public void render(@NotNull GuiGraphics context, int mouseX, int mouseY, float partialTicks) {
        this.renderDirtBackground(context);
        context.drawCenteredString(this.font, this.title, this.width / 2, 15, 16777215);
        super.render(context, mouseX, mouseY, partialTicks);
    }

    public void save() {
        AquamiraeConfig.Client.overlay.set(this.overlay.getValue());
        AquamiraeConfig.Client.particles.set(this.particles.getValue());
        AquamiraeConfig.Client.ambientSounds.set(this.ambientSounds.getValue());
        AquamiraeConfig.Client.biomeMusic.set(this.biomeMusic.getValue());
        AquamiraeConfig.Client.bossMusic.set(this.bossMusic.getValue());
        AquamiraeConfig.Client.stylizedBossbar.set(this.stylizedBossbar.getValue());
        AquamiraeConfig.Common.notifications.set(this.notifications.getValue());
    }

    @Override
    public void onClose() {
        this.save();
        if (this.minecraft != null && this.PARENT != null) this.minecraft.setScreen(this.PARENT);
        else super.onClose();
    }
}
