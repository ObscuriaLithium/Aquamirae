
package com.obscuria.aquamirae.common.item.weapon;

import com.obscuria.aquamirae.AquamiraeConfig;
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

public class RemnantsSaber extends SwordItem implements IAbilitable, IDynamicAttributes {
	public static final String BONUS;
	public static final String DEFEAT_MONSTERS_GOAL;
	public static final Ability ABILITY;

	public RemnantsSaber() {
		super(AquamiraeTiers.REMNANTS_SABER, 4, -2f, new Properties());
	}

	@Override
	public Optional<Ability> getAbility(ItemStack stack) {
		return Optional.of(ABILITY);
	}

	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity entity, LivingEntity user) {
		if (!entity.isAlive() && entity instanceof Monster)
			AbilityHelper.addCustomProgress(stack, DEFEAT_MONSTERS_GOAL, 1);
		return super.hurtEnemy(stack, entity, user);
	}

	@Override
	public boolean isAttributesChanged(ItemStack itemStack, ItemStack itemStack1) {
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
		return entity instanceof Player player && player.isInWater()
				? ABILITY.setupContext(stack, player).get(BONUS) * 0.01 : 0;
	}

	static {
		BONUS = "BONUS";
		DEFEAT_MONSTERS_GOAL = "defeat_monsters";
		final var description = Component.translatable("ability.aquamirae.remnants_saber");
		final var defeatMonstersHint = Component.translatable("ability_goal.aquamirae.defeat_monsters");
		ABILITY = Ability.create(AbilityStyles.BLUE_GEM)
				.setRelatedItems(ItemBundle.direct(AquamiraeItems.REMNANTS_SABER))
				.addTier(AbilityTier.create(description)
						.with(BONUS, Variable.create(AquamiraeConfig.RemnantsSaber.bonusTier1)
								.withTrackedAttribute(AquamiraeAttributes.DEPTHS_FURY).withSuffix(Variable.PERCENT)))
				.addTier(AbilityTier.create(description)
						.with(BONUS, Variable.create(AquamiraeConfig.RemnantsSaber.bonusTier2)
								.withTrackedAttribute(AquamiraeAttributes.DEPTHS_FURY).withSuffix(Variable.PERCENT))
						.requiring(DEFEAT_MONSTERS_GOAL, AbilityGoal.custom(30, defeatMonstersHint)))
				.build();
	}
}
