package com.obscuria.aquamirae;

import com.obscuria.aquamirae.common.entities.ShipGraveyardEntity;
import net.minecraft.entity.Entity;

public final class AquamiraeUtils {

    public static boolean isShipGraveyardEntity(Entity entity) {
        return entity.getClass().isAnnotationPresent(ShipGraveyardEntity.class);
    }

    public static boolean isInIceMaze(Entity entity) {
        return entity.getWorld().getBiome(entity.getBlockPos()).isIn(Aquamirae.ICE_MAZE);
    }
}
