
package com.obscuria.aquamirae.common.item.weapon;

import com.obscuria.aquamirae.AquamiraeConfig;
import com.obscuria.aquamirae.common.entity.CaptainCornelia;
import com.obscuria.aquamirae.registry.AquamiraeAttributes;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import com.obscuria.aquamirae.registry.AquamiraeSounds;
import com.obscuria.aquamirae.registry.AquamiraeTiers;
import com.obscuria.core.common.item.Lore;
import com.obscuria.core.common.item.ability.*;
import com.obscuria.core.common.item.ability.event.AbilityEvent;
import com.obscuria.core.common.item.ability.event.AttackAbilityEvent;
import com.obscuria.core.common.bundle.ItemBundle;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;

import java.util.Optional;

@Lore("lore.aquamirae.terrible_sword")
public class TerribleSword extends SwordItem implements IAbilitable {
	public static final String BONUS_CHANCE;
	public static final String DEBUFF_CHANCE;
	public static final String DEBUFF_DAMAGE;
	public static final String DEFEAT_MONSTERS_GOAL;
	public static final String DEFEAT_CORNELIA_GOAL;

	public static final Ability ABILITY;

	public TerribleSword() {
		super(AquamiraeTiers.TERRIBLE_SWORD, 9, -3f, new Properties());
	}

	@Override
	public Optional<Ability> getAbility(ItemStack stack) {
		return Optional.of(ABILITY);
	}

	@Override
	public void onAbilityEvent(AbilityEvent rawEvent) {
		if (rawEvent instanceof AttackAbilityEvent event
				&& event.slot == EquipmentSlot.MAINHAND
				&& event.context.player().level() instanceof ServerLevel level) {
			final var context = event.context;
			final var player = context.player();
			if (player.getRandom().nextInt(99) > context.get(BONUS_CHANCE)) return;
			level.playSound(null, player,
					AquamiraeSounds.ITEM_TERRIBLE_SWORD_CRIT.get(), SoundSource.PLAYERS,
					1f, 0.9f + player.getRandom().nextFloat() * 0.2f);
			event.addDamage(event.getDamage());
		}
	}

	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity entity, LivingEntity user) {
		if (user instanceof Player player) {
			if (entity.isAlive()) {
				final var context = ABILITY.setupContext(stack, player);
				if (player.getRandom().nextInt(99) <= context.get(DEBUFF_CHANCE))
					player.hurt(player.damageSources().magic(), context.get(DEBUFF_DAMAGE));
			} else {
				if (entity instanceof Monster)
					AbilityHelper.addCustomProgress(stack, DEFEAT_MONSTERS_GOAL, 1);
				if (entity instanceof CaptainCornelia)
					AbilityHelper.addCustomProgress(stack, DEFEAT_CORNELIA_GOAL, 1);
			}
		}
		return super.hurtEnemy(stack, entity, user);
	}

	static {
		BONUS_CHANCE = "BONUS_CHANCE";
		DEBUFF_CHANCE = "DEBUFF_CHANCE";
		DEBUFF_DAMAGE = "DEBUFF_DAMAGE";
		DEFEAT_MONSTERS_GOAL = "defeat_monsters";
		DEFEAT_CORNELIA_GOAL = "defeat_cornelia";
		final var description = Component.translatable("ability.aquamirae.terrible_sword");
		final var defeatMonstersHint = Component.translatable("ability_goal.aquamirae.defeat_monsters");
		final var defeatCorneliaHint = Component.translatable("ability_goal.aquamirae.defeat_cornelia");
		ABILITY = Ability.create(AbilityStyles.BLUE_GEM)
				.setRelatedItems(ItemBundle.direct(AquamiraeItems.TERRIBLE_SWORD))
				.addTier(AbilityTier.create(description)
						.with(BONUS_CHANCE, Variable.create(AquamiraeConfig.TerribleSword.bonusChanceTier1).withSuffix(Variable.PERCENT))
						.with(DEBUFF_DAMAGE, Variable.create(AquamiraeConfig.TerribleSword.debuffDamageTier1))
						.with(DEBUFF_CHANCE, Variable.create(AquamiraeConfig.TerribleSword.debuffChanceTier1).withSuffix(Variable.PERCENT)
								.inverted().clamp(50, 100).withMultiplyFactor(0.2).withTrackedAttribute(AquamiraeAttributes.DEPTHS_FURY)))
				.addTier(AbilityTier.create(description)
						.with(BONUS_CHANCE, Variable.create(AquamiraeConfig.TerribleSword.bonusChanceTier2).withSuffix(Variable.PERCENT))
						.with(DEBUFF_DAMAGE, Variable.create(AquamiraeConfig.TerribleSword.debuffDamageTier2))
						.with(DEBUFF_CHANCE, Variable.create(AquamiraeConfig.TerribleSword.debuffChanceTier2).withSuffix(Variable.PERCENT)
								.inverted().clamp(50, 100).withMultiplyFactor(0.2).withTrackedAttribute(AquamiraeAttributes.DEPTHS_FURY))
						.requiring(DEFEAT_MONSTERS_GOAL, AbilityGoal.custom(150, defeatMonstersHint)))
				.addTier(AbilityTier.create(description)
						.with(BONUS_CHANCE, Variable.create(AquamiraeConfig.TerribleSword.bonusChanceTier3).withSuffix(Variable.PERCENT))
						.with(DEBUFF_DAMAGE, Variable.create(AquamiraeConfig.TerribleSword.debuffDamageTier3))
						.with(DEBUFF_CHANCE, Variable.create(AquamiraeConfig.TerribleSword.debuffChanceTier3).withSuffix(Variable.PERCENT)
								.inverted().clamp(25, 100).withMultiplyFactor(0.2).withTrackedAttribute(AquamiraeAttributes.DEPTHS_FURY))
						.requiring(DEFEAT_MONSTERS_GOAL, AbilityGoal.custom(500, defeatMonstersHint))
						.requiring(DEFEAT_CORNELIA_GOAL, AbilityGoal.custom(1, defeatCorneliaHint)))
				.build();
	}
}
