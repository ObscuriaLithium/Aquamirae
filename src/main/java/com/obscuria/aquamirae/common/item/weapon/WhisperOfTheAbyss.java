
package com.obscuria.aquamirae.common.item.weapon;

import com.google.common.collect.Multimap;
import com.obscuria.aquamirae.Aquamirae;
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
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SwordItem;
import net.minecraftforge.common.ForgeMod;

import java.util.Optional;
import java.util.UUID;

@DeadSeaCurse.ByDefault
public class WhisperOfTheAbyss extends SwordItem implements IAbilitable {
	public static final Ability ABILITY = Ability.create(AbilityStyles.PURPLE_GEM)
			.setRelatedItems(ItemBundle.direct(AquamiraeItems.WHISPER_OF_THE_ABYSS))
			.addTier(AbilityTier.create()
					.setDescription(Component.translatable("ability.aquamirae.whisper_of_the_abyss"))
					.addVariable(Variable.create(8).withSuffix(Variable.PERCENT))
					.addVariable(Variable.create(6).withTrackedAttribute(AquamiraeAttributes.DEPTHS_FURY).withSuffix(Variable.SECONDS))
					.addVariable(Variable.create(3)))
			.addTier(AbilityTier.create()
					.setDescription(Component.translatable("ability.aquamirae.whisper_of_the_abyss"))
					.addVariable(Variable.create(8).withSuffix(Variable.PERCENT))
					.addVariable(Variable.create(9).withTrackedAttribute(AquamiraeAttributes.DEPTHS_FURY).withSuffix(Variable.SECONDS))
					.addVariable(Variable.create(4))
					.addGoal(AbilityGoal.custom("apply_effect", 300,
							Component.translatable("ability_goal.aquamirae.apply_effect")))
					.addGoal(AbilityGoal.appliedAstralDust(1)))
			.addTier(AbilityTier.create()
					.setDescription(Component.translatable("ability.aquamirae.whisper_of_the_abyss"))
					.addVariable(Variable.create(8).withSuffix(Variable.PERCENT))
					.addVariable(Variable.create(12).withTrackedAttribute(AquamiraeAttributes.DEPTHS_FURY).withSuffix(Variable.SECONDS))
					.addVariable(Variable.create(5))
					.addGoal(AbilityGoal.custom("apply_effect", 900,
							Component.translatable("ability_goal.aquamirae.apply_effect")))
					.addGoal(AbilityGoal.appliedAstralDust(2)))
			.build();

	public WhisperOfTheAbyss() {
		super(AquamiraeTiers.WHISPER_OF_tHE_ABYSS, 3, -3.2f, new Properties().fireResistant().rarity(Rarity.EPIC));
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
							.getEffect(AquamiraeMobEffects.WHISPER_OF_THE_ABYSS.get()))
					.map(MobEffectInstance::getAmplifier).orElse(-1);
			entity.addEffect(new MobEffectInstance(AquamiraeMobEffects.WHISPER_OF_THE_ABYSS.get(),
					duration, Math.min(currentAmplifier + 1, maxAmplifier)));
			context.forceCooldown(20);
			AbilityHelper.addCustomProgress(stack, "apply_effect", 1);
		}
		return super.hurtEnemy(stack, entity, user);
	}

	@Override
	public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
		return Aquamirae.addAttribute(super.getDefaultAttributeModifiers(slot), ForgeMod.ENTITY_REACH.get(),
				UUID.fromString("AB3F54D3-645C-4F36-A497-9C11A33DB5CF"), AttributeModifier.Operation.MULTIPLY_TOTAL,
				0.33, slot == EquipmentSlot.MAINHAND);
	}
}
