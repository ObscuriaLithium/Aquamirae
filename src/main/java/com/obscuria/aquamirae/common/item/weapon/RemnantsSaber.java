
package com.obscuria.aquamirae.common.item.weapon;

import com.obscuria.aquamirae.registry.AquamiraeAttributes;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import com.obscuria.aquamirae.registry.AquamiraeTiers;
import com.obscuria.core.api.ability.*;
import com.obscuria.core.api.extension.item.AttributeCollector;
import com.obscuria.core.api.util.bundle.ItemBundle;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;

import java.util.Optional;

public class RemnantsSaber extends SwordItem implements IAbilitable {

	public static final Ability ABILITY = Ability.create(AbilityStyles.BLUE_GEM)
			.setRelatedItems(ItemBundle.direct(AquamiraeItems.REMNANTS_SABER))
			.addTier(AbilityTier.create()
					.setDescription(Component.translatable("ability.aquamirae.remnants_saber"))
					.addVariable(Variable.create(100).withTrackedAttribute(AquamiraeAttributes.DEPTHS_FURY).withSuffix(Variable.PERCENT)))
			.addTier(AbilityTier.create()
					.setDescription(Component.translatable("ability.aquamirae.remnants_saber"))
					.addVariable(Variable.create(140).withTrackedAttribute(AquamiraeAttributes.DEPTHS_FURY).withSuffix(Variable.PERCENT))
					.addGoal(AbilityGoal.custom("defeat_monsters", 30,
							Component.translatable("ability_goal.aquamirae.defeat_monsters"))))
			.build();

	public RemnantsSaber() {
		super(AquamiraeTiers.REMNANTS_SABER, 3, -2f, new Properties());
	}

	@Override
	public Optional<Ability> getAbility(ItemStack stack) {
		return Optional.of(ABILITY);
	}

	public double getBonusDamage(ItemStack stack, Entity entity) {
		return entity instanceof Player player && player.isInWater()
				? ABILITY.setupContext(stack, player).getVariable(1) * 0.01 : 0;
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
