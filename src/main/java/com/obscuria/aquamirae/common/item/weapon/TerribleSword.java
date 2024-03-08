
package com.obscuria.aquamirae.common.item.weapon;

import com.obscuria.aquamirae.common.entity.CaptainCornelia;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import com.obscuria.aquamirae.registry.AquamiraeSounds;
import com.obscuria.aquamirae.registry.AquamiraeTiers;
import com.obscuria.core.api.ability.AbilityHelper;
import com.obscuria.core.api.ability.AbilityStyles;
import com.obscuria.core.api.ability.*;
import com.obscuria.core.api.util.bundle.ItemBundle;
import com.obscuria.core.api.annotation.SimpleLore;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;

import java.util.Optional;

@SimpleLore(value = "lore.aquamirae.terrible_sword")
public class TerribleSword extends SwordItem implements IAbilitable {
	public static final Ability ABILITY = Ability.create(AbilityStyles.BLUE_GEM)
			.setRelatedItems(ItemBundle.direct(AquamiraeItems.TERRIBLE_SWORD))
			.addTier(AbilityTier.create()
					.setDescription(Component.translatable("ability.aquamirae.terrible_sword"))
					.addVariable(Variable.create(100).withSuffix(Variable.PERCENT))
					.addVariable(Variable.create(3)))
			.addTier(AbilityTier.create()
					.setDescription(Component.translatable("ability.aquamirae.terrible_sword"))
					.addVariable(Variable.create(100).withSuffix(Variable.PERCENT))
					.addVariable(Variable.create(2))
					.addGoal(AbilityGoal.custom("defeat_monsters", 150,
							Component.translatable("ability_goal.aquamirae.defeat_monsters"))))
			.addTier(AbilityTier.create()
					.setDescription(Component.translatable("ability.aquamirae.terrible_sword"))
					.addVariable(Variable.create(40).withSuffix(Variable.PERCENT))
					.addVariable(Variable.create(2))
					.addGoal(AbilityGoal.custom("defeat_monsters", 500,
							Component.translatable("ability_goal.aquamirae.defeat_monsters")))
					.addGoal(AbilityGoal.custom("defeat_cornelia", 1,
							Component.translatable("ability_goal.aquamirae.defeat_cornelia"))))
			.build();

	public static float getDamageBonus(Player player, float amount) {
		if (player.getRandom().nextBoolean()) return 0;
		if (player.level() instanceof ServerLevel level)
			level.playSound(null, player, AquamiraeSounds.ITEM_TERRIBLE_SWORD_CRIT.get(),
					SoundSource.PLAYERS, 1f, (float) player.getRandom().triangle(1, 0.2));
		return amount;
	}

	public TerribleSword() {
		super(AquamiraeTiers.TERRIBLE_SWORD, 9, -2f, new Properties());
	}

	@Override
	public Optional<Ability> getAbility(ItemStack stack) {
		return Optional.of(ABILITY);
	}

	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity entity, LivingEntity user) {
		if (user instanceof Player player) {
			if (entity.isAlive()) {
				final var context = ABILITY.setupContext(stack, player);
				final var chance = context.getVariable(1);
				final var damage = context.getVariable(2);
				if (player.getRandom().nextInt(99) <= chance)
					player.hurt(player.damageSources().magic(), damage);
			} else {
				if (entity instanceof Monster)
					AbilityHelper.addCustomProgress(stack, "defeat_monsters", 1);
				if (entity instanceof CaptainCornelia)
					AbilityHelper.addCustomProgress(stack, "defeat_cornelia", 1);
			}
		}
		return super.hurtEnemy(stack, entity, user);
	}
}
