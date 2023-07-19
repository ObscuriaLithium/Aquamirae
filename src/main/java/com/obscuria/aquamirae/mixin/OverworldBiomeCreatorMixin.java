package com.obscuria.aquamirae.mixin;

import net.minecraft.world.biome.OverworldBiomeCreator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(OverworldBiomeCreator.class)
public abstract class OverworldBiomeCreatorMixin {

    @ModifyVariable(method = "createFrozenOcean", at = @At("LOAD"), name = "f")
    private static float modifyFrozenOcean(float temperature) {
        return 0f;
    }
}
