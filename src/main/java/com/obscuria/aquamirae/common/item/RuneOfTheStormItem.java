
package com.obscuria.aquamirae.common.item;

import com.obscuria.aquamirae.common.DeadSeaCurse;
import com.obscuria.aquamirae.common.entity.StormChakram;
import com.obscuria.aquamirae.registry.AquamiraeAttributes;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import com.obscuria.core.api.ability.AbilityStyles;
import com.obscuria.core.api.ability.*;
import com.obscuria.core.common.item.ObscureRarity;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

@DeadSeaCurse.ByDefault
public class RuneOfTheStormItem extends Item implements IAbilitable {
	public static final int CAPACITY = 1200;
	public static final Component RANGE_PREFIX = Component.literal("1-");
	public static final Ability ABILITY = Ability.create(AbilityStyles.PURPLE_GEM)
			.addTier(AbilityTier.create()
					.setDescription(Component.translatable("ability.aquamirae.rune_of_the_storm"))
					.addVariable(Variable.create(3).withTrackedAttribute(AquamiraeAttributes.DEPTHS_FURY).withPrefix(RANGE_PREFIX))
					.addVariable(Variable.create(4)))
			.addTier(AbilityTier.create()
					.setDescription(Component.translatable("ability.aquamirae.rune_of_the_storm"))
					.addVariable(Variable.create(5).withTrackedAttribute(AquamiraeAttributes.DEPTHS_FURY).withPrefix(RANGE_PREFIX))
					.addVariable(Variable.create(3))
					.addGoal(AbilityGoal.custom("deal_damage", 1500,
							Component.translatable("ability_goal.aquamirae.deal_damage")))
					.addGoal(AbilityGoal.appliedAstralDust(1)))
			.addTier(AbilityTier.create()
					.setDescription(Component.translatable("ability.aquamirae.rune_of_the_storm"))
					.addVariable(Variable.create(7).withTrackedAttribute(AquamiraeAttributes.DEPTHS_FURY).withPrefix(RANGE_PREFIX))
					.addVariable(Variable.create(2))
					.addGoal(AbilityGoal.custom("deal_damage", 4500,
							Component.translatable("ability_goal.aquamirae.deal_damage")))
					.addGoal(AbilityGoal.appliedAstralDust(2)))
			.addTier(AbilityTier.create()
					.setDescription(Component.translatable("ability.aquamirae.rune_of_the_storm"))
					.addVariable(Variable.create(9).withTrackedAttribute(AquamiraeAttributes.DEPTHS_FURY).withPrefix(RANGE_PREFIX))
					.addVariable(Variable.create(1))
					.addGoal(AbilityGoal.custom("deal_damage", 13500,
							Component.translatable("ability_goal.aquamirae.deal_damage")))
					.addGoal(AbilityGoal.appliedAstralDust(3)))
			.build();

	public static float getChakramDamage(ItemStack stack, Player player) {
		if (!stack.is(AquamiraeItems.RUNE_OF_THE_STORM.get())) return 1;
		final var context = ABILITY.setupContext(stack, player);
		return context.getVariable(1);
	}

	public static void depleteCharges(ItemStack stack, Player player) {
		if (!stack.is(AquamiraeItems.RUNE_OF_THE_STORM.get())) return;
		final var context = ABILITY.setupContext(stack, player);
		addCharges(stack, -context.getVariable(2));
	}

	public static void setCharges(ItemStack stack, int amount) {
		stack.getOrCreateTag().putInt("chargesUsed",
				Mth.clamp(CAPACITY - amount, 0, CAPACITY));
	}

	public static int getCharges(ItemStack stack) {
		return Mth.clamp(CAPACITY - stack.getOrCreateTag()
				.getInt("chargesUsed"), 0, CAPACITY);
	}

	public static void addCharges(ItemStack stack, int amount) {
		setCharges(stack, getCharges(stack) + amount);
	}

	public RuneOfTheStormItem() {
		super(new Item.Properties().stacksTo(1).fireResistant().rarity(ObscureRarity.MYTHIC));
	}

	@Override
	public Optional<Ability> getAbility(ItemStack stack) {
		return Optional.of(ABILITY);
	}

	public int getUseDuration(ItemStack stack) {
		return 72000;
	}

	public UseAnim getUseAnimation(ItemStack stack) {
		return UseAnim.SPEAR;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		return ItemUtils.startUsingInstantly(level, player, hand);
	}

	@Override
	public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int remainingTicks) {
		if (entity instanceof Player player) {
			final var tick = this.getUseDuration(stack) - remainingTicks;
			if (tick < 4) return;
			if (level instanceof ServerLevel serverLevel) {
				final var chakram = StormChakram.create(serverLevel, player, stack);
				chakram.setOwner(player);
				chakram.setPos(player.getEyePosition());
				chakram.shootFromRotation(player, player.getXRot(), player.getYRot(),
						0.0F, Math.min(8f, tick / 4f), 1.0F);
				serverLevel.addFreshEntity(chakram);
				stack.shrink(1);
				serverLevel.playSound(null, entity, SoundEvents.TRIDENT_THROW,
						SoundSource.PLAYERS, 1f, 1f);
			}
		}
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
		tooltip.add(Component.literal("§7Charges: %s§8/%s".formatted(getCharges(stack), CAPACITY)));
	}

	@Override
	public boolean isBarVisible(ItemStack stack) {
		return getCharges(stack) < CAPACITY;
	}

	@Override
	public int getBarWidth(ItemStack stack) {
		return Math.round(13f * (getCharges(stack) / 1f / CAPACITY));
	}

	@Override
	public int getBarColor(ItemStack stack) {
		return 0xFF80CCFF;
	}

	@Override
	public boolean isFoil(ItemStack stack) {
		return getCharges(stack) > 0;
	}
}
