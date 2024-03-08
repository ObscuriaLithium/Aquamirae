
package com.obscuria.aquamirae.common.item.weapon;

import com.obscuria.aquamirae.registry.AquamiraeItems;
import com.obscuria.aquamirae.registry.AquamiraeTiers;
import com.obscuria.core.api.ability.*;
import com.obscuria.core.api.extension.item.AttributeCollector;
import com.obscuria.core.api.util.bundle.ItemBundle;
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

public class FinCutter extends SwordItem implements IAbilitable {

	public static final Ability ABILITY = Ability.create(AbilityStyles.BLUE_GEM)
			.setRelatedItems(ItemBundle.direct(AquamiraeItems.FIN_CUTTER))
			.addTier(AbilityTier.create()
					.setDescription(Component.translatable("ability.aquamirae.fin_cutter"))
					.addVariable(Variable.create(15).withSuffix(Variable.PERCENT))
					.addVariable(Variable.create(150).withSuffix(Variable.PERCENT)))
			.addTier(AbilityTier.create()
					.setDescription(Component.translatable("ability.aquamirae.fin_cutter"))
					.addVariable(Variable.create(25).withSuffix(Variable.PERCENT))
					.addVariable(Variable.create(250).withSuffix(Variable.PERCENT))
					.addGoal(AbilityGoal.custom("defeat_monsters_low_hp", 50,
							Component.translatable("ability_goal.aquamirae.defeat_monsters_low_hp"))))
			.addTier(AbilityTier.create()
					.setDescription(Component.translatable("ability.aquamirae.fin_cutter"))
					.addVariable(Variable.create(35).withSuffix(Variable.PERCENT))
					.addVariable(Variable.create(350).withSuffix(Variable.PERCENT))
					.addGoal(AbilityGoal.custom("defeat_monsters_low_hp", 150,
							Component.translatable("ability_goal.aquamirae.defeat_monsters_low_hp")))
					.addGoal(AbilityGoal.appliedAstralDust(1)))
			.build();

	public FinCutter() {
		super(AquamiraeTiers.FIN_CUTTER, 3, -2f, new Properties());
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
			AbilityHelper.addCustomProgress(stack, "defeat_monsters_low_hp", 1);
		return super.hurtEnemy(stack, entity, user);
	}

	public double getBonusDamage(ItemStack stack, Entity entity) {
		if (entity instanceof Player player) {
			final var context = ABILITY.setupContext(stack, player);
			final var emptyHearts = Math.floor((player.getMaxHealth() - player.getHealth()) / 2);
			return Math.min(
					context.getVariable(1) * 0.01 * emptyHearts,
					context.getVariable(2) * 0.01);
		}
		return 0;
	}

	@SuppressWarnings("all")
	public void collectAttributeModifiers(AttributeCollector collector) {
		collector.forceUpdate(true);
		collector.append(EquipmentSlot.MAINHAND, Attributes.ATTACK_DAMAGE,
				context -> getBonusDamage(context.stack(), context.entity()) > 0,
				context -> new AttributeModifier(context.uuidFor(EquipmentSlot.MAINHAND),
						"Ability Bonus", getBonusDamage(context.stack(), context.entity()),
						AttributeModifier.Operation.MULTIPLY_BASE));
	}
}
