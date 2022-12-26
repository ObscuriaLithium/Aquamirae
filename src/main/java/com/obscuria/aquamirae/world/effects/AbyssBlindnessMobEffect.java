package com.obscuria.aquamirae.world.effects;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.DisplayEffectsScreen;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;

public class AbyssBlindnessMobEffect extends Effect {
	public AbyssBlindnessMobEffect() {
		super(EffectType.HARMFUL, -6750055);
		addAttributeModifier(Attributes.MOVEMENT_SPEED, "5D6F0BA2-1286-46AC-B896-C61C5CAE91DA", -0.5D, AttributeModifier.Operation.MULTIPLY_BASE);
	}

	@Override
	public String getDescriptionId() {
		return "effect.aquamirae.abyss_blindness";
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
