package com.obscuria.aquamirae.world.effects;

import net.minecraft.client.gui.GuiComponent;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraftforge.client.EffectRenderer;
import org.jetbrains.annotations.NotNull;

public class AbyssBlindnessMobEffect extends MobEffect {
	public AbyssBlindnessMobEffect() {
		super(MobEffectCategory.HARMFUL, -6750055);
		addAttributeModifier(Attributes.MOVEMENT_SPEED, "5D6F0BA2-1286-46AC-B896-C61C5CAE91DA", -0.5D, AttributeModifier.Operation.MULTIPLY_BASE);
	}

	@Override
	public @NotNull String getDescriptionId() {
		return "effect.aquamirae.abyss_blindness";
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
