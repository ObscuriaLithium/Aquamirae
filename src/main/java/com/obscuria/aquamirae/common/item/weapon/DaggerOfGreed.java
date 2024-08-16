
package com.obscuria.aquamirae.common.item.weapon;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.AquamiraeConfig;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import com.obscuria.aquamirae.registry.AquamiraeTiers;
import com.obscuria.core.common.item.Lore;
import com.obscuria.core.common.item.ability.*;
import com.obscuria.core.common.bundle.ItemBundle;
import com.obscuria.core.util.WorldUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;

import java.util.Optional;

@Lore(value = "lore.aquamirae.dagger_of_greed", prefix = "§c")
public class DaggerOfGreed extends SwordItem implements IAbilitable {
	public static final String CHANCE;
	public static final String COUNT;
	public static final String OBTAIN_EMERALDS_GOAL;
	public static final Ability ABILITY;
	public static final TagKey<EntityType<?>> PROTECT_EMERALDS;
	public static final TagKey<EntityType<?>> PROTECT_OFFERS;

	public DaggerOfGreed() {
		super(AquamiraeTiers.DAGGER_OF_GREED, 3, -2f,
				new Properties().fireResistant().rarity(Rarity.UNCOMMON).setNoRepair());
	}

	@Override
	public Optional<Ability> getAbility(ItemStack stack) {
		return Optional.of(ABILITY);
	}

	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity entity, LivingEntity source) {
		final var result = super.hurtEnemy(stack, entity, source);
		if (result && entity instanceof AbstractVillager villager
				&& !villager.getType().is(PROTECT_EMERALDS)
				&& source instanceof ServerPlayer player
				&& ABILITY.canBeUsedBy(player)) {
			final var context = ABILITY.setupContext(stack, player);
			final var random = player.getRandom();
			final var maxCount = context.get(COUNT) + 1;

			if (random.nextInt(100) <= context.get(CHANCE)) {
				final var count = random.nextInt(1, maxCount);
				WorldUtil.drop(player.level(), entity.getEyePosition(),
						new ItemStack(Items.EMERALD, count));
				AbilityHelper.addCustomProgress(stack, OBTAIN_EMERALDS_GOAL, count);
			} else if (AbilityHelper.getTierOf(stack, player) >= 2
					&& !villager.getType().is(PROTECT_OFFERS)) {
				final var offers = villager.getOffers().stream().filter(offer ->
						!offer.isOutOfStock() && !offer.getResult().is(Items.EMERALD)).toList();
				if (!offers.isEmpty()) WorldUtil.drop(player.level(), entity.getEyePosition(),
						offers.get(random.nextInt(offers.size())).assemble());
			}
		}
		return result;
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
		if (enchantment == Enchantments.MENDING) return false;
		if (enchantment == Enchantments.UNBREAKING) return false;
		return super.canApplyAtEnchantingTable(stack, enchantment);
	}

	@Override
	public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
		return EnchantedBookItem.getEnchantments(book).stream().noneMatch(tag ->
				tag instanceof CompoundTag compound
						&& (compound.getString("id").equals("minecraft:mending")
						|| compound.getString("id").equals("minecraft:unbreaking")));
	}

	@Override
	public boolean isRepairable(ItemStack stack) {
		return false;
	}

	@Override
	public boolean isFoil(ItemStack stack) {
		return true;
	}

	static {
		CHANCE = "CHANCE";
		COUNT = "COUNT";
		OBTAIN_EMERALDS_GOAL = "obtain_emeralds";
		final var descriptionTier1 = Component.translatable("ability.aquamirae.dagger_of_greed_tier_1");
		final var descriptionTier2 = Component.translatable("ability.aquamirae.dagger_of_greed_tier_2");
		final var obtainEmeraldsHint = Component.translatable("ability_goal.aquamirae.obtain_emeralds");
		final var rangePrefix = Component.literal("1-");
		ABILITY = Ability.create(AbilityStyles.PURPLE_GEM)
				.setRelatedItems(ItemBundle.direct(AquamiraeItems.DAGGER_OF_GREED))
				.addTier(AbilityTier.create(descriptionTier1)
						.with(CHANCE, Variable.create(AquamiraeConfig.DaggerOfGreed.chanceTier1).withSuffix(Variable.PERCENT))
						.with(COUNT, Variable.create(AquamiraeConfig.DaggerOfGreed.countTier1).withPrefix(rangePrefix)))
				.addTier(AbilityTier.create(descriptionTier2)
						.with(CHANCE, Variable.create(AquamiraeConfig.DaggerOfGreed.chanceTier2).withSuffix(Variable.PERCENT))
						.with(COUNT, Variable.create(AquamiraeConfig.DaggerOfGreed.countTier2).withPrefix(rangePrefix))
						.requiring(OBTAIN_EMERALDS_GOAL, AbilityGoal.custom(64, obtainEmeraldsHint))
						.requiringDust(1))
				.build();
		PROTECT_EMERALDS = TagKey.create(Registries.ENTITY_TYPE, Aquamirae.key("dagger_of_greed_protect_emeralds"));
		PROTECT_OFFERS = TagKey.create(Registries.ENTITY_TYPE, Aquamirae.key("dagger_of_greed_protect_offers"));
	}
}
