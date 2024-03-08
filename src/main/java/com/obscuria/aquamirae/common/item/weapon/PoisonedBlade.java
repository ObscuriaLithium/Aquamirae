
package com.obscuria.aquamirae.common.item.weapon;

import com.obscuria.aquamirae.registry.AquamiraeAttributes;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import com.obscuria.aquamirae.registry.AquamiraeTiers;
import com.obscuria.core.api.ability.AbilityStyles;
import com.obscuria.core.api.ability.*;
import com.obscuria.core.api.util.bundle.ItemBundle;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;

import java.util.Optional;

public class PoisonedBlade extends SwordItem implements IAbilitable {
	public static final Ability ABILITY = Ability.create(AbilityStyles.BLUE_GEM)
			.setRelatedItems(ItemBundle.direct(AquamiraeItems.POISONED_BLADE))
			.addTier(AbilityTier.create()
					.setDescription(Component.translatable("ability.aquamirae.poisoned_blade"))
					.addVariable(Variable.create(5).withTrackedAttribute(AquamiraeAttributes.DEPTHS_FURY).withSuffix(Variable.SECONDS)))
			.addTier(AbilityTier.create()
					.setDescription(Component.translatable("ability.aquamirae.poisoned_blade"))
					.addVariable(Variable.create(10).withTrackedAttribute(AquamiraeAttributes.DEPTHS_FURY).withSuffix(Variable.SECONDS))
					.addGoal(AbilityGoal.appliedAstralDust(4)))
			.build();

	public PoisonedBlade() {
		super(AquamiraeTiers.POISONED_BLADE, 3, -1f, new Properties());
	}

	@Override
	public Optional<Ability> getAbility(ItemStack stack) {
		return Optional.of(ABILITY);
	}
}
