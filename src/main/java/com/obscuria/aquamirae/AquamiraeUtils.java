package com.obscuria.aquamirae;

import com.obscuria.aquamirae.api.ShipGraveyardEntity;
import net.minecraft.world.entity.Entity;

public final class AquamiraeUtils {

    public static boolean isShipGraveyardEntity(Entity entity) {
        return entity.getClass().isAnnotationPresent(ShipGraveyardEntity.class);
    }

    public static boolean isInIceMaze(Entity entity) {
        return entity.getLevel().getBiome(entity.blockPosition()).is(AquamiraeMod.ICE_MAZE);
    }
}
