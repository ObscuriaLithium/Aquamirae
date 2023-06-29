package com.obscuria.aquamirae;

import com.obscuria.aquamirae.common.entities.ShipGraveyardEntity;
import net.minecraft.world.entity.Entity;

public final class AquamiraeUtils {

    public static boolean isShipGraveyardEntity(Entity entity) {
        return entity.getClass().isAnnotationPresent(ShipGraveyardEntity.class);
    }

    public static boolean isInIceMaze(Entity entity) {
        return entity.level().getBiome(entity.blockPosition()).is(Aquamirae.ICE_MAZE);
    }
}
