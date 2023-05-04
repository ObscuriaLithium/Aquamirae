package com.obscuria.aquamirae.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.obscureapi.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.LerpingBossEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public final class AquamiraeBossBars {
    private static final ResourceLocation CORNELIA = new ResourceLocation(Aquamirae.MODID, "textures/gui/bossbars/cornelia.png");

    public static void cornelia(Minecraft minecraft, PoseStack pose, int x, int y, LerpingBossEvent bossEvent, Component name) {
        RenderUtils.screen(CORNELIA, () -> GuiComponent.blit(pose, x - 37, y - 20, 0, 0, 256, 64, 256, 64));
    }
}
