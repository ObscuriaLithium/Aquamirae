
package com.obscuria.aquamirae.common.effects;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.DisplayEffectsScreen;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraftforge.common.ForgeMod;

import javax.annotation.Nonnull;

public class SwimSpeedMobEffect extends Effect {
	public SwimSpeedMobEffect() {
		super(EffectType.BENEFICIAL, -16737844);
		addAttributeModifier(ForgeMod.SWIM_SPEED.get(), "5D6F0BA2-1186-46AC-B896-C61C5CAE99CC", 0.1D, AttributeModifier.Operation.MULTIPLY_BASE);
	}

	@Override
	@Nonnull
	public String getDescriptionId() {
		return "effect.aquamirae.swim_speed";
	}

	@Override
	public boolean isDurationEffectTick(int duration, int amplifier) {
		return true;
	}

	@Override
	public boolean shouldRenderHUD(EffectInstance effect) {
		return false;
	}

	@Override
	public void renderInventoryEffect(EffectInstance effect, DisplayEffectsScreen<?> gui, MatrixStack mStack, int x, int y, float z) {
	}

	@Override
	public void renderHUDEffect(EffectInstance effect, AbstractGui gui, MatrixStack mStack, int x, int y, float z, float alpha) {
	}
}
