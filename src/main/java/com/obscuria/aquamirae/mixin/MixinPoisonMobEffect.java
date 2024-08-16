package com.obscuria.aquamirae.mixin;

import com.obscuria.aquamirae.compat.AquamiraeCompats;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.theillusivec4.curios.api.CuriosApi;

@Mixin(targets = "net.minecraft.world.effect.PoisonMobEffect")
public abstract class MixinPoisonMobEffect {

    @Inject(method = "applyEffectTick", at = @At("HEAD"))
    void onTick(LivingEntity entity, int amplifier, CallbackInfo ci) {
        if (entity instanceof Player player) {
            final var item = AquamiraeItems.ICE_MAZE_RING.get();
            if (AquamiraeCompats.CURIOS_API
                    .safeGet(() -> () -> CuriosApi.getCuriosInventory(player)
                            .resolve()
                            .flatMap(handler -> handler.findFirstCurio(item))
                            .map(result -> !player.getCooldowns().isOnCooldown(item))
                            .orElse(false)).orElse(false)) {
                player.getCooldowns().addCooldown(item, 200 + amplifier * 200);
                player.removeEffect(MobEffects.POISON);
            }
        }
    }
}
