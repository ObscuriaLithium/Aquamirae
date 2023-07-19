package com.obscuria.aquamirae.mixin;

import com.obscuria.aquamirae.AquamiraeUtils;
import com.obscuria.aquamirae.common.items.armor.ThreeBoltArmorItem;
import com.obscuria.obscureapi.api.utils.ItemUtils;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin {

    @Inject(method = "playerTick", at = @At("HEAD"))
    private void tick(CallbackInfo ci) {
        final ServerPlayerEntity player = (ServerPlayerEntity)(Object)this;
        if (AquamiraeUtils.isInIceMaze(player) && !player.isCreative() && !player.isSpectator())
            if (player.isInsideWaterOrBubbleColumn() && player.getFrozenTicks() <= player.getMinFreezeDamageTicks() * 3)
                if (ItemUtils.countArmorPieces(player, ThreeBoltArmorItem.class) < 4)
                    player.setFrozenTicks(player.getFrozenTicks() + 4);
    }
}
