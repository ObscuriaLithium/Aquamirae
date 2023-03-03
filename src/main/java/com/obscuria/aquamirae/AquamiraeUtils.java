package com.obscuria.aquamirae;

import net.minecraft.util.ResourceLocation;

public final class AquamiraeUtils {
    public static boolean isIceMaze(ResourceLocation biome) {
        return AquamiraeMod.ICE_MAZE.contains(biome);
    }
}
