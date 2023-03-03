package com.obscuria.aquamirae;

import com.obscuria.aquamirae.api.ShipGraveyardEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public final class AquamiraeUtils {

    public static boolean isShipGraveyardEntity(Entity entity) {
        return entity.getClass().isAnnotationPresent(ShipGraveyardEntity.class);
    }

    public static boolean isIceMaze(ResourceLocation biome) {
        return AquamiraeMod.ICE_MAZE.contains(biome);
    }
}
