
package com.obscuria.aquamirae.common.item.weapon;

import com.obscuria.aquamirae.registry.AquamiraeAttributes;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import com.obscuria.aquamirae.registry.AquamiraeTiers;
import com.obscuria.core.common.item.ability.*;
import com.obscuria.core.common.bundle.ItemBundle;
import com.obscuria.core.common.extension.item.AttributeCollector;
import com.obscuria.core.common.extension.item.IDynamicAttributes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;

import java.util.Optional;

public class FinCutter extends SwordItem implements IAbilitable, IDynamicAttributes {
	public static final String BONUS;
	public static final String MAX_BONUS;
	public static final String DEFEAT_MONSTERS_LOW_HP_GOAL;
	public static final Ability ABILITY;

	public FinCutter() {
		super(AquamiraeTiers.FIN_CUTTER, 5, -2f, new Properties());
	}

	@Override
	public Optional<Ability> getAbility(ItemStack stack) {
		return Optional.of(ABILITY);
	}

	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity entity, LivingEntity user) {
		if (user instanceof Player player
				&& entity instanceof Monster
				&& entity.isDeadOrDying()
				&& player.getHealth() <= 6)
			AbilityHelper.addCustomProgress(stack, DEFEAT_MONSTERS_LOW_HP_GOAL, 1);
		return super.hurtEnemy(stack, entity, user);
	}

	@Override
	public boolean isAttributesChanged(ItemStack first, ItemStack second) {
		return true;
	}

	@Override
	public void collectDynamicAttributes(AttributeCollector collector) {
		collector.append(EquipmentSlot.MAINHAND, Attributes.ATTACK_DAMAGE,
				context -> getAbilityBonus(context.stack(), context.entity()) > 0,
				context -> new AttributeModifier(context.uuidFor(EquipmentSlot.MAINHAND),
						"Ability Bonus", getAbilityBonus(context.stack(), context.entity()),
						AttributeModifier.Operation.MULTIPLY_BASE));
	}

	private double getAbilityBonus(ItemStack stack, Entity entity) {
		if (entity instanceof Player player) {
			final var context = ABILITY.setupContext(stack, player);
			final var emptyHearts = Math.floor((player.getMaxHealth() - player.getHealth()) / 2);
			return Math.min(
					context.get(BONUS) * 0.01 * emptyHearts,
					context.get(MAX_BONUS) * 0.01);
		}
		return 0;
	}

	static {
		BONUS = "BONUS";
		MAX_BONUS = "MAX_BONUS";
		DEFEAT_MONSTERS_LOW_HP_GOAL = "defeat_monsters_low_hp";
		final var description = Component.translatable("ability.aquamirae.fin_cutter");
		final var defeatMonstersLowHpHint = Component.translatable("ability_goal.aquamirae.defeat_monsters_low_hp");
		ABILITY = Ability.create(AbilityStyles.BLUE_GEM)
				.setRelatedItems(ItemBundle.direct(AquamiraeItems.FIN_CUTTER))
				.addTier(AbilityTier.create(description)
						.with(BONUS, Variable.create(15).clamp(1, 30).withAdditionFactor(1).withMultiplyFactor(0.5)
								.withTrackedAttribute(AquamiraeAttributes.DEPTHS_FURY).withSuffix(Variable.PERCENT))
						.with(MAX_BONUS, Variable.create(150).clamp(10, 300).withAdditionFactor(10).withMultiplyFactor(0.5)
								.withTrackedAttribute(AquamiraeAttributes.DEPTHS_FURY).withSuffix(Variable.PERCENT)))
				.addTier(AbilityTier.create(description)
						.with(BONUS, Variable.create(25).clamp(1, 50).withAdditionFactor(1).withMultiplyFactor(0.5)
								.withTrackedAttribute(AquamiraeAttributes.DEPTHS_FURY).withSuffix(Variable.PERCENT))
						.with(MAX_BONUS, Variable.create(250).clamp(10, 500).withAdditionFactor(10).withMultiplyFactor(0.5)
								.withTrackedAttribute(AquamiraeAttributes.DEPTHS_FURY).withSuffix(Variable.PERCENT))
						.requiring(DEFEAT_MONSTERS_LOW_HP_GOAL, AbilityGoal.custom(50, defeatMonstersLowHpHint)))
				.addTier(AbilityTier.create(description)
						.with(BONUS, Variable.create(35).clamp(1, 70).withAdditionFactor(1).withMultiplyFactor(0.5)
								.withTrackedAttribute(AquamiraeAttributes.DEPTHS_FURY).withSuffix(Variable.PERCENT))
						.with(MAX_BONUS, Variable.create(350).clamp(10, 700).withAdditionFactor(10).withMultiplyFactor(0.5)
								.withTrackedAttribute(AquamiraeAttributes.DEPTHS_FURY).withSuffix(Variable.PERCENT))
						.requiring(DEFEAT_MONSTERS_LOW_HP_GOAL, AbilityGoal.custom(150, defeatMonstersLowHpHint))
						.requiringDust(1))
				.build();
	}
}
