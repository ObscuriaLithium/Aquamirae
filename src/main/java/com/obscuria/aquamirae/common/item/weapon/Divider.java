
package com.obscuria.aquamirae.common.item.weapon;

import com.obscuria.aquamirae.AquamiraeConfig;
import com.obscuria.aquamirae.common.DeadSeaCurse;
import com.obscuria.aquamirae.registry.AquamiraeAttributes;
import com.obscuria.aquamirae.registry.AquamiraeMobEffects;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import com.obscuria.aquamirae.registry.AquamiraeTiers;
import com.obscuria.core.common.item.ability.*;
import com.obscuria.core.common.bundle.ItemBundle;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SwordItem;

import java.util.Optional;

@DeadSeaCurse.ByDefault
public class Divider extends SwordItem implements IAbilitable {
	public static final String STRENGTH;
	public static final String DURATION;
	public static final String STACK;
	public static final String APPLY_EFFECT_GOAL;
	public static final Ability ABILITY;

	public Divider() {
		super(AquamiraeTiers.DIVIDER, 6, -2.6f,
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
			final var currentAmplifier = Optional
					.ofNullable(entity.getEffect(AquamiraeMobEffects.DIVIDER.get()))
					.map(MobEffectInstance::getAmplifier)
					.orElse(-1);
			entity.addEffect(new MobEffectInstance(AquamiraeMobEffects.DIVIDER.get(),
					duration, Math.min(currentAmplifier + 1, maxAmplifier)));
			context.forceCooldown(20);
			AbilityHelper.addCustomProgress(stack, APPLY_EFFECT_GOAL, 1);
		}
		return super.hurtEnemy(stack, entity, user);
	}

	static {
		STRENGTH = "STRENGTH";
		DURATION = "DURATION";
		STACK = "STACK";
		APPLY_EFFECT_GOAL = "apply_effect";
		final var description = Component.translatable("ability.aquamirae.divider");
		final var applyEffectHint = Component.translatable("ability_goal.aquamirae.apply_effect");
		ABILITY = Ability.create(AbilityStyles.PURPLE_GEM)
				.setRelatedItems(ItemBundle.direct(AquamiraeItems.DIVIDER))
				.addTier(AbilityTier.create(description)
						.with(STRENGTH, Variable.create(4).withSuffix(Variable.PERCENT))
						.with(DURATION, Variable.create(AquamiraeConfig.Divider.durationTier1)
								.withTrackedAttribute(AquamiraeAttributes.DEPTHS_FURY).withSuffix(Variable.SECONDS))
						.with(STACK, Variable.create(AquamiraeConfig.Divider.stackTier1)))
				.addTier(AbilityTier.create(description)
						.with(STRENGTH, Variable.create(4).withSuffix(Variable.PERCENT))
						.with(DURATION, Variable.create(AquamiraeConfig.Divider.durationTier2)
								.withTrackedAttribute(AquamiraeAttributes.DEPTHS_FURY).withSuffix(Variable.SECONDS))
						.with(STACK, Variable.create(AquamiraeConfig.Divider.stackTier2))
						.requiring(APPLY_EFFECT_GOAL, AbilityGoal.custom(500, applyEffectHint))
						.requiringDust(1))
				.addTier(AbilityTier.create(description)
						.with(STRENGTH, Variable.create(4).withSuffix(Variable.PERCENT))
						.with(DURATION, Variable.create(AquamiraeConfig.Divider.durationTier3)
								.withTrackedAttribute(AquamiraeAttributes.DEPTHS_FURY).withSuffix(Variable.SECONDS))
						.with(STACK, Variable.create(AquamiraeConfig.Divider.stackTier3))
						.requiring(APPLY_EFFECT_GOAL, AbilityGoal.custom(1500, applyEffectHint))
						.requiringDust(2))
				.build();
	}
}
