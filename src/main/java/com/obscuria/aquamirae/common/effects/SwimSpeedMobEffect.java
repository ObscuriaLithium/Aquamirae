
package com.obscuria.aquamirae.common.effects;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.client.extensions.common.IClientMobEffectExtensions;
import net.minecraftforge.common.ForgeMod;
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
	public void initializeClient(java.util.function.Consumer<IClientMobEffectExtensions> consumer) {
		consumer.accept(new IClientMobEffectExtensions() {
			@Override
			public boolean isVisibleInInventory(MobEffectInstance effect) {
				return false;
			}

			@Override
			public boolean renderInventoryText(MobEffectInstance instance, EffectRenderingInventoryScreen<?> screen, GuiGraphics context, int x, int y, int blitOffset) {
				return false;
			}

			@Override
			public boolean isVisibleInGui(MobEffectInstance effect) {
				return false;
			}
		});
	}
}
