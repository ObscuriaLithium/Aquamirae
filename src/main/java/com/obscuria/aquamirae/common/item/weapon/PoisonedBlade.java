
package com.obscuria.aquamirae.common.item.weapon;

import com.obscuria.aquamirae.AquamiraeConfig;
import com.obscuria.aquamirae.registry.AquamiraeAttributes;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import com.obscuria.aquamirae.registry.AquamiraeTiers;
import com.obscuria.core.common.item.ability.*;
import com.obscuria.core.common.bundle.ItemBundle;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;

import java.util.Optional;

public class PoisonedBlade extends SwordItem implements IAbilitable {
	public static final String DURATION;
	public static final String COOLDOWN;
	public static final Ability ABILITY;

	public PoisonedBlade() {
		super(AquamiraeTiers.POISONED_BLADE, 4, -1f, new Properties());
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
			final var cooldown = 20 * context.get(COOLDOWN);
			entity.addEffect(new MobEffectInstance(MobEffects.POISON, duration, 0));
			context.forceCooldown(cooldown);
		}
		return super.hurtEnemy(stack, entity, user);
	}

	static {
		DURATION = "DURATION";
		COOLDOWN = "COOLDOWN";
		final var description = Component.translatable("ability.aquamirae.poisoned_blade");
		ABILITY = Ability.create(AbilityStyles.BLUE_GEM)
				.setRelatedItems(ItemBundle.direct(AquamiraeItems.POISONED_BLADE))
				.addTier(AbilityTier.create(description)
						.with(DURATION, Variable.create(AquamiraeConfig.PoisonedBlade.durationTier1)
								.withTrackedAttribute(AquamiraeAttributes.DEPTHS_FURY).withSuffix(Variable.SECONDS))
						.with(COOLDOWN, Variable.create(AquamiraeConfig.PoisonedBlade.durationTier2).inverted()
								.withTrackedAttribute(AquamiraeAttributes.DEPTHS_FURY).withSuffix(Variable.SECONDS)))
				.addTier(AbilityTier.create(description)
						.with(DURATION, Variable.create(AquamiraeConfig.PoisonedBlade.cooldownTier1)
								.withTrackedAttribute(AquamiraeAttributes.DEPTHS_FURY).withSuffix(Variable.SECONDS))
						.with(COOLDOWN, Variable.create(AquamiraeConfig.PoisonedBlade.cooldownTier2).inverted()
								.withTrackedAttribute(AquamiraeAttributes.DEPTHS_FURY).withSuffix(Variable.SECONDS))
						.requiringDust(4))
				.build();
	}
}
