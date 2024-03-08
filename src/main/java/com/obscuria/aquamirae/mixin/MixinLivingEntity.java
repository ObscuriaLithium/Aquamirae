package com.obscuria.aquamirae.mixin;

import com.obscuria.aquamirae.compat.curios.CuriosCompat;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public final class MixinLivingEntity implements CuriosCompat.EntityExtension {
    @Unique
    private int aquamirae$shoeSpikesTick = 0;

    @Override
    public void aquamirae$shoeSpikesTick() {
        this.aquamirae$shoeSpikesTick = 4;
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        if (this.aquamirae$shoeSpikesTick > 0)
            this.aquamirae$shoeSpikesTick--;
    }

    @ModifyVariable(
            method = "travel",
            at = @At(value = "STORE"),
            slice = @Slice(
                    from = @At(
                            value = "INVOKE",
                            target = "Lnet/minecraft/world/entity/LivingEntity;getBlockPosBelowThatAffectsMyMovement()Lnet/minecraft/core/BlockPos;",
                            ordinal = 0),
                    to = @At(
                            value = "INVOKE",
                            target = "Lnet/minecraft/world/entity/LivingEntity;handleRelativeFrictionAndCalculateMovement(Lnet/minecraft/world/phys/Vec3;F)Lnet/minecraft/world/phys/Vec3;")
            ),
            ordinal = 0,
            require = 0)
    private float modifyFriction(float friction) {
        return this.aquamirae$shoeSpikesTick > 0 && friction > 0.6f ? 0.6f : friction;
    }
}
