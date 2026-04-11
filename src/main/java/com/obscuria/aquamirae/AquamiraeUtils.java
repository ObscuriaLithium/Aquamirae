package com.obscuria.aquamirae;

import com.obscuria.aquamirae.common.entities.ShipGraveyardEntity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public final class AquamiraeUtils {

    public static boolean isShipGraveyardEntity(Entity entity) {
        return entity.getClass().isAnnotationPresent(ShipGraveyardEntity.class);
    }

    public static boolean isInIceMaze(Entity entity) {
        return entity.level().getBiome(entity.blockPosition()).is(Aquamirae.ICE_MAZE);
    }

    public static boolean doPoison(LivingEntity attacker, Entity entity, int duration, int amplifier) {
        if (!(entity instanceof LivingEntity living)) return false;
        if (living instanceof Player player && (player.isCreative() || player.isSpectator())) return false;
        living.addEffect(new MobEffectInstance(MobEffects.POISON, duration, amplifier));
        living.setLastHurtByMob(attacker);
        return true;
    }
}
