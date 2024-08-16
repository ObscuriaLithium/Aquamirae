package com.obscuria.aquamirae.mixin;

import com.obscuria.aquamirae.common.item.armor.AbyssalArmor;
import com.obscuria.aquamirae.compat.curios.CuriosCompat;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public final class MixinLivingEntity implements CuriosCompat.LivingEntityExtension {
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

    @Inject(method = "checkTotemDeathProtection",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/InteractionHand;values()[Lnet/minecraft/world/InteractionHand;",
                    shift = At.Shift.BEFORE),
            cancellable = true)
    private void checkDeathProtection(DamageSource source, CallbackInfoReturnable<Boolean> cir) {
        final var entity = (LivingEntity) (Object) this;
        if (entity instanceof Player player
                && AbyssalArmor.Heaume.checkDeathProtection(player))
            cir.setReturnValue(true);
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
