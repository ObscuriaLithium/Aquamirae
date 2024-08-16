
package com.obscuria.aquamirae.common.item;

import com.google.common.base.Suppliers;
import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.common.entity.projectile.IceShard;
import com.obscuria.aquamirae.registry.AquamiraeAttributes;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import com.obscuria.core.ObscureAPI;
import com.obscuria.core.common.Splitter;
import com.obscuria.core.common.item.ObscureRarity;
import com.obscuria.core.common.item.ability.*;
import com.obscuria.core.common.item.perk.PerkHelper;
import com.obscuria.core.common.item.perk.PerkTooltip;
import com.obscuria.core.common.item.perk.PerkType;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class RuneOfTheStormItem extends Item {
	public static final Component DESCRIPTION;
	public static final Component PERK_DESCRIPTION;
	public static final Supplier<ItemStack> PERK_ICON;
	public static final PerkType PERK;
	public static final int CAPACITY = 1200;
	public static final String DAMAGE = "damage";
	public static final String COST = "cost";
	public static final Component RANGE_PREFIX = Component.literal("1-");
	public static final Ability ABILITY = Ability.create(AbilityStyles.PURPLE_GEM)
			.addTier(AbilityTier.create(Component.translatable("ability.aquamirae.rune_of_the_storm"))
					.with(DAMAGE, Variable.create(3).withTrackedAttribute(AquamiraeAttributes.DEPTHS_FURY).withPrefix(RANGE_PREFIX))
					.with(COST, Variable.create(4)))
			.addTier(AbilityTier.create(Component.translatable("ability.aquamirae.rune_of_the_storm"))
					.with(DAMAGE, Variable.create(5).withTrackedAttribute(AquamiraeAttributes.DEPTHS_FURY).withPrefix(RANGE_PREFIX))
					.with(COST, Variable.create(3))
					.requiring("deal_damage", AbilityGoal.custom( 1500, Component.translatable("ability_goal.aquamirae.deal_damage")))
					.requiringDust(1))
			.addTier(AbilityTier.create(Component.translatable("ability.aquamirae.rune_of_the_storm"))
					.with(DAMAGE, Variable.create(7).withTrackedAttribute(AquamiraeAttributes.DEPTHS_FURY).withPrefix(RANGE_PREFIX))
					.with(DAMAGE, Variable.create(2))
					.requiring("deal_damage", AbilityGoal.custom( 4500, Component.translatable("ability_goal.aquamirae.deal_damage")))
					.requiringDust(2))
			.addTier(AbilityTier.create(Component.translatable("ability.aquamirae.rune_of_the_storm"))
					.with(DAMAGE, Variable.create(9).withTrackedAttribute(AquamiraeAttributes.DEPTHS_FURY).withPrefix(RANGE_PREFIX))
					.with(COST, Variable.create(1))
					.requiring("deal_damage", AbilityGoal.custom(13500, Component.translatable("ability_goal.aquamirae.deal_damage")))
					.requiringDust(3))
			.build();

	public static float getChakramDamage(ItemStack stack, Player player) {
		if (!stack.is(AquamiraeItems.RUNE_OF_THE_STORM.get())) return 1;
		final var context = ABILITY.setupContext(stack, player);
		return context.get(DAMAGE);
	}

	public static void depleteCharges(ItemStack stack, Player player) {
		if (!stack.is(AquamiraeItems.RUNE_OF_THE_STORM.get())) return;
		final var context = ABILITY.setupContext(stack, player);
		addCharges(stack, -context.get(COST));
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

	public static void onPerkTrigger(ServerLevel level, Player player, LivingEntity entity) {
		final var random = player.getRandom();
		final var origin = entity.getEyePosition();
		final var amount = Math.min(12, entity.getMaxHealth() / 6);
		for (var i = 0; i < amount; i++) {
			final var motion = player.position()
					.vectorTo(origin).normalize()
					.scale(random.triangle(0.4, 0.2))
					.xRot((float) random.triangle(0, 0.75))
					.yRot((float) random.triangle(0, 0.75))
					.zRot((float) random.triangle(0, 0.75));
			IceShard.create(level, player, origin, motion);
		}
		level.playSound(null, entity,
				SoundEvents.AMETHYST_BLOCK_PLACE, SoundSource.PLAYERS,
				1f, 1f);
	}

	@Override
	public boolean overrideStackedOnOther(ItemStack self, Slot slot, ClickAction action, Player player) {
		if (!slot.mayPickup(player)) return false;
		if (action != ClickAction.SECONDARY) return false;
		final var stack = slot.getItem();
		if (stack.getItem() instanceof TieredItem && !PerkHelper.has(stack, PERK)) {
			PerkHelper.set(stack, PERK, 1);
			player.playSound(SoundEvents.AMETHYST_BLOCK_RESONATE, 2,
					(float) player.getRandom().triangle(1, 0.2));
			player.playSound(SoundEvents.ENCHANTMENT_TABLE_USE, 2,
					(float) player.getRandom().triangle(1.4, 0.1));
			if (!player.getAbilities().instabuild)
				self.shrink(1);
			return true;
		}
		return false;
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
		tooltip.addAll(Splitter.of(DESCRIPTION).setStyle(Style.EMPTY.withColor(ChatFormatting.WHITE)).build());
		tooltip.addAll(Splitter.of(PERK_DESCRIPTION).setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)).setPrefix("   ").build());
	}

	@Override
	public boolean isFoil(ItemStack stack) {
		return getCharges(stack) > 0;
	}

	static {
		DESCRIPTION = Component.translatable("lore.aquamirae.rune_of_the_storm");
		PERK_DESCRIPTION = Component.translatable("perk.aquamirae.rune_of_the_storm");
		PERK_ICON = Suppliers.memoize(() -> AquamiraeItems.RUNE_OF_THE_STORM.get().getDefaultInstance());
		PERK = ObscureAPI.ITEM_PERKS.register(Aquamirae.key("rune_of_the_storm"),
				Component.literal("Stormy"),
				(stack, type, level) -> PerkTooltip.create(PERK_ICON.get(), PERK_DESCRIPTION));
	}
}
