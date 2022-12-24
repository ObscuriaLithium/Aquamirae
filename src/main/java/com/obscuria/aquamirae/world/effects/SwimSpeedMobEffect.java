
package com.obscuria.aquamirae.world.effects;

import net.minecraft.client.gui.GuiComponent;
import net.minecraftforge.client.EffectRenderer;
import net.minecraftforge.common.ForgeMod;

import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;

import com.mojang.blaze3d.vertex.PoseStack;
import org.jetbrains.annotations.NotNull;

public class SwimSpeedMobEffect extends MobEffect {
	public SwimSpeedMobEffect() {
		super(MobEffectCategory.BENEFICIAL, -16737844);
		addAttributeModifier(ForgeMod.SWIM_SPEED.get(), "5D6F0BA2-1186-46AC-B896-C61C5CAE99CC", 0.1D, AttributeModifier.Operation.MULTIPLY_BASE);
	}

	@Override
	public @NotNull String getDescriptionId() {
		return "effect.aquamirae.swim_speed";
	}

	@Override
	public boolean isDurationEffectTick(int duration, int amplifier) {
		return true;
	}

	@Override
	public void initializeClient(java.util.function.Consumer<net.minecraftforge.client.EffectRenderer> consumer) {
		consumer.accept(new EffectRenderer() {
			@Override
			public boolean shouldRenderHUD(MobEffectInstance effect) {
				return false;
			}

			@Override
			public void renderInventoryEffect(MobEffectInstance effect, EffectRenderingInventoryScreen<?> gui, PoseStack mStack, int x, int y, float z) {
			}

			@Override
			public void renderHUDEffect(MobEffectInstance effect, GuiComponent gui, PoseStack mStack, int x, int y, float z, float alpha) {
			}
		});
	}
}
