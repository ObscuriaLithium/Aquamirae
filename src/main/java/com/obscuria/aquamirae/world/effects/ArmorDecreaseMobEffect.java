
package com.obscuria.aquamirae.world.effects;

import net.minecraftforge.client.extensions.common.IClientMobEffectExtensions;

import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffect;
import org.jetbrains.annotations.NotNull;

public class ArmorDecreaseMobEffect extends MobEffect {
	public ArmorDecreaseMobEffect() {
		super(MobEffectCategory.NEUTRAL, -6750055);
		addAttributeModifier(Attributes.ARMOR, "5D6F0BA2-1186-46AC-B896-C61C5CEE99CC", -0.1D, AttributeModifier.Operation.MULTIPLY_TOTAL);
	}

	@Override
	public @NotNull String getDescriptionId() {
		return "effect.aquamirae.armor_decrease";
	}

	@Override
	public boolean isDurationEffectTick(int duration, int amplifier) {
		return true;
	}

	@Override
	public void initializeClient(java.util.function.Consumer<IClientMobEffectExtensions> consumer) {
		consumer.accept(new IClientMobEffectExtensions() {
			@Override
			public boolean isVisibleInGui(MobEffectInstance effect) {
				return false;
			}
		});
	}
}
