package com.obscuria.aquamirae.common.effect;

import com.obscuria.aquamirae.compat.AquamiraeCompats;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import top.theillusivec4.curios.api.CuriosApi;

public class HydrophobiaModEffect extends MobEffect {
    public HydrophobiaModEffect() {
        super(MobEffectCategory.HARMFUL, -16737844);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int tick, int amplifier) {
        return true;
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity instanceof Player player) {
            final var item = AquamiraeItems.SHIP_GRAVEYARD_RING.get();
            if (AquamiraeCompats.CURIOS_API
                    .safeGet(() -> () -> CuriosApi.getCuriosInventory(player)
                            .resolve()
                            .flatMap(handler -> handler.findFirstCurio(item))
                            .map(result -> !player.getCooldowns().isOnCooldown(item))
                            .orElse(false)).orElse(false)) {
                player.getCooldowns().addCooldown(item, 200);
                player.removeEffect(this);
            }
            if (player.isCreative() || player.isSpectator()) return;
        }
        if (entity.isUnderWater())
            entity.setAirSupply(Math.max(0, entity.getAirSupply() - 2 + amplifier * 2));
    }
}
