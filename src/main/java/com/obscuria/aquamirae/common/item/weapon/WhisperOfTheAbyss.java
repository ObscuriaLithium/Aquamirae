
package com.obscuria.aquamirae.common.item.weapon;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.obscuria.aquamirae.AquamiraeConfig;
import com.obscuria.aquamirae.common.DeadSeaCurse;
import com.obscuria.aquamirae.registry.AquamiraeAttributes;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import com.obscuria.aquamirae.registry.AquamiraeMobEffects;
import com.obscuria.aquamirae.registry.AquamiraeTiers;
import com.obscuria.core.common.item.ability.*;
import com.obscuria.core.common.bundle.ItemBundle;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SwordItem;
import net.minecraftforge.common.ForgeMod;

import java.util.Optional;
import java.util.UUID;

@DeadSeaCurse.ByDefault
public class WhisperOfTheAbyss extends SwordItem implements IAbilitable {
	public static final String STRENGTH;
	public static final String DURATION;
	public static final String STACK;
	public static final String APPLY_EFFECT_GOAL;
	public static final Ability ABILITY;

	public WhisperOfTheAbyss() {
		super(AquamiraeTiers.WHISPER_OF_tHE_ABYSS, 11, -3.2f,
				new Properties().rarity(Rarity.EPIC).fireResistant());
	}

	@Override
	public Optional<Ability> getAbility(ItemStack stack) {
		return Optional.of(ABILITY);
	}

	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity entity, LivingEntity user) {
		if (user instanceof Player player && ABILITY.canBeUsedBy(player)) {
			final var context = ABILITY.setupContext(stack, player);
			final var duration = 20 * context.get(DURATION);
			final var maxAmplifier = context.get(STACK) - 1;
			final var currentAmplifier = Optional.ofNullable(entity
							.getEffect(AquamiraeMobEffects.WHISPER_OF_THE_ABYSS.get()))
					.map(MobEffectInstance::getAmplifier).orElse(-1);
			entity.addEffect(new MobEffectInstance(AquamiraeMobEffects.WHISPER_OF_THE_ABYSS.get(),
					duration, Math.min(currentAmplifier + 1, maxAmplifier)));
			context.forceCooldown(20);
			AbilityHelper.addCustomProgress(stack, APPLY_EFFECT_GOAL, 1);
		}
		return super.hurtEnemy(stack, entity, user);
	}

	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
		if (slot == EquipmentSlot.MAINHAND)
			return ImmutableMultimap.<Attribute, AttributeModifier>builder()
					.putAll(super.getAttributeModifiers(slot, stack))
					.put(ForgeMod.ENTITY_REACH.get(),
							new AttributeModifier(UUID.fromString("AB3F54D3-645C-4F36-A497-9C11A33DB5CF"),
									"Weapon modifier", 0.33, AttributeModifier.Operation.MULTIPLY_BASE))
					.build();
		return super.getAttributeModifiers(slot, stack);
	}

	static {
		STRENGTH = "STRENGTH";
		DURATION = "DURATION";
		STACK = "STACK";
		APPLY_EFFECT_GOAL = "apply_effect";
		final var description = Component.translatable("ability.aquamirae.whisper_of_the_abyss");
		final var applyEffectHint = Component.translatable("ability_goal.aquamirae.apply_effect");
		ABILITY = Ability.create(AbilityStyles.PURPLE_GEM)
				.setRelatedItems(ItemBundle.direct(AquamiraeItems.WHISPER_OF_THE_ABYSS))
				.addTier(AbilityTier.create(description)
						.with(STRENGTH, Variable.create(8).withSuffix(Variable.PERCENT))
						.with(DURATION, Variable.create(AquamiraeConfig.WhisperOfTheAbyss.durationTier1)
								.withTrackedAttribute(AquamiraeAttributes.DEPTHS_FURY).withSuffix(Variable.SECONDS))
						.with(STACK, Variable.create(AquamiraeConfig.WhisperOfTheAbyss.stackTier1)))
				.addTier(AbilityTier.create(description)
						.with(STRENGTH, Variable.create(8).withSuffix(Variable.PERCENT))
						.with(DURATION, Variable.create(AquamiraeConfig.WhisperOfTheAbyss.durationTier2)
								.withTrackedAttribute(AquamiraeAttributes.DEPTHS_FURY).withSuffix(Variable.SECONDS))
						.with(STACK, Variable.create(AquamiraeConfig.WhisperOfTheAbyss.stackTier2))
						.requiring(APPLY_EFFECT_GOAL, AbilityGoal.custom(300, applyEffectHint))
						.requiringDust(1))
				.addTier(AbilityTier.create(description)
						.with(STRENGTH, Variable.create(8).withSuffix(Variable.PERCENT))
						.with(DURATION, Variable.create(AquamiraeConfig.WhisperOfTheAbyss.durationTier3)
								.withTrackedAttribute(AquamiraeAttributes.DEPTHS_FURY).withSuffix(Variable.SECONDS))
						.with(STACK, Variable.create(AquamiraeConfig.WhisperOfTheAbyss.stackTier3))
						.requiring(APPLY_EFFECT_GOAL, AbilityGoal.custom(900, applyEffectHint))
						.requiringDust(2))
				.build();
	}
}
