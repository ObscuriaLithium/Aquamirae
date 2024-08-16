package com.obscuria.aquamirae.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.registry.AquamiraeEntityTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(AbstractArrow.class)
public abstract class MixinAbstractArrow {
    @Shadow protected abstract SoundEvent getDefaultHitGroundSoundEvent();

    @WrapOperation(method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/projectile/AbstractArrow;isInWater()Z"))
    private boolean suppressWaterLogic(AbstractArrow instance, Operation<Boolean> original) {
        return !aquamirae$isSpine(this) && original.call(instance);
    }

    @ModifyArg(method = "onHitEntity",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/Entity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"),
            index = 1)
    private float modifyDamage(float damage, @Local(ordinal = 0) Entity entity) {
        if (aquamirae$isSpine(this) && Aquamirae.isShipGraveyardEntity(entity)) return damage * 3;
        if (aquamirae$isAbyssal(this) && Aquamirae.isIceMazeEntity(entity)) return damage * 3;
        return damage;
    }

    @ModifyArg(method = "onHitBlock",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/projectile/AbstractArrow;playSound(Lnet/minecraft/sounds/SoundEvent;FF)V"))
    private SoundEvent modifySound(SoundEvent event) {
        return this.getDefaultHitGroundSoundEvent();
    }

    @Unique
    private static boolean aquamirae$isSpine(MixinAbstractArrow arrow) {
        return ((AbstractArrow)(Object)arrow).getType() == AquamiraeEntityTypes.SPINE_ARROW.get();
    }

    @Unique
    private static boolean aquamirae$isAbyssal(MixinAbstractArrow arrow) {
        return ((AbstractArrow)(Object)arrow).getType() == AquamiraeEntityTypes.ABYSSAL_ARROW.get();
    }
}
