
package com.obscuria.aquamirae.common.item.weapon;

import com.obscuria.aquamirae.common.DeadSeaCurse;
import com.obscuria.aquamirae.registry.AquamiraeAttributes;
import com.obscuria.aquamirae.registry.AquamiraeMobEffects;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import com.obscuria.aquamirae.registry.AquamiraeTiers;
import com.obscuria.core.api.ability.AbilityHelper;
import com.obscuria.core.api.ability.AbilityStyles;
import com.obscuria.core.api.ability.*;
import com.obscuria.core.api.util.bundle.ItemBundle;
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
	public static final Ability ABILITY = Ability.create(AbilityStyles.PURPLE_GEM)
			.setRelatedItems(ItemBundle.direct(AquamiraeItems.DIVIDER))
			.addTier(AbilityTier.create()
					.setDescription(Component.translatable("ability.aquamirae.divider"))
					.addVariable(Variable.create(3).withSuffix(Variable.PERCENT))
					.addVariable(Variable.create(6).withTrackedAttribute(AquamiraeAttributes.DEPTHS_FURY).withSuffix(Variable.SECONDS))
					.addVariable(Variable.create(6)))
			.addTier(AbilityTier.create()
					.setDescription(Component.translatable("ability.aquamirae.divider"))
					.addVariable(Variable.create(3).withSuffix(Variable.PERCENT))
					.addVariable(Variable.create(9).withTrackedAttribute(AquamiraeAttributes.DEPTHS_FURY).withSuffix(Variable.SECONDS))
					.addVariable(Variable.create(8))
					.addGoal(AbilityGoal.custom("apply_effect", 500,
							Component.translatable("ability_goal.aquamirae.apply_effect")))
					.addGoal(AbilityGoal.appliedAstralDust(1)))
			.addTier(AbilityTier.create()
					.setDescription(Component.translatable("ability.aquamirae.divider"))
					.addVariable(Variable.create(3).withSuffix(Variable.PERCENT))
					.addVariable(Variable.create(12).withTrackedAttribute(AquamiraeAttributes.DEPTHS_FURY).withSuffix(Variable.SECONDS))
					.addVariable(Variable.create(10))
					.addGoal(AbilityGoal.custom("apply_effect", 1500,
							Component.translatable("ability_goal.aquamirae.apply_effect")))
					.addGoal(AbilityGoal.appliedAstralDust(2)))
			.build();

	public Divider() {
		super(AquamiraeTiers.DIVIDER, 3, -2.6f, new Properties().fireResistant().rarity(Rarity.EPIC));
	}

	@Override
	public Optional<Ability> getAbility(ItemStack stack) {
		return Optional.of(ABILITY);
	}

	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity entity, LivingEntity user) {
		if (user instanceof Player player && ABILITY.canBeUsedBy(player)) {
			final var context = ABILITY.setupContext(stack, player);
			final var duration = 20 * context.getVariable(2);
			final var maxAmplifier = context.getVariable(3) - 1;
			final var currentAmplifier = Optional.ofNullable(entity
							.getEffect(AquamiraeMobEffects.DIVIDER.get()))
					.map(MobEffectInstance::getAmplifier).orElse(-1);
			entity.addEffect(new MobEffectInstance(AquamiraeMobEffects.DIVIDER.get(),
					duration, Math.min(currentAmplifier + 1, maxAmplifier)));
			context.forceCustomCooldown(20);
			AbilityHelper.addCustomProgress(stack, "apply_effect", 1);
		}
		return super.hurtEnemy(stack, entity, user);
	}
}
