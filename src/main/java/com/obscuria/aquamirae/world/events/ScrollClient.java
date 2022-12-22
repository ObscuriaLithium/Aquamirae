package com.obscuria.aquamirae.world.events;

import com.obscuria.aquamirae.registry.AquamiraeSounds;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ScrollClient {
    public static void effect(int type) {
        if (Minecraft.getInstance().player == null) return;
        Minecraft.getInstance().player.playSound(AquamiraeSounds.EFFECT_MYSTERY.get());
    }
}
