package com.obscuria.aquamirae.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {

    @Shadow
    protected abstract void renderOverlay(DrawContext context, Identifier texture, float opacity);

    @Inject(method = "render", at = @At("HEAD"))
    private void render(DrawContext context, float tickDelta, CallbackInfo ci) {
        final MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;
        if (client.player.getEquippedStack(EquipmentSlot.HEAD).isOf(AquamiraeItems.THREE_BOLT_HELMET)
                && client.options.getPerspective().isFirstPerson()) {
            RenderSystem.enableBlend();
            renderOverlay(context, Aquamirae.key("textures/gui/three_bolt_overlay.png"), 1f);
        }
    }
}
