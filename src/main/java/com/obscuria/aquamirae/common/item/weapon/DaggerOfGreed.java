
package com.obscuria.aquamirae.common.item.weapon;

import com.obscuria.aquamirae.registry.AquamiraeItems;
import com.obscuria.aquamirae.registry.AquamiraeTiers;
import com.obscuria.core.api.ability.AbilityHelper;
import com.obscuria.core.api.ability.AbilityStyles;
import com.obscuria.core.api.ability.*;
import com.obscuria.core.api.util.bundle.ItemBundle;
import com.obscuria.core.api.annotation.SimpleLore;
import com.obscuria.core.api.util.WorldUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;

import java.util.Optional;

@SimpleLore(value = "lore.aquamirae.dagger_of_greed", prefix = "§c")
public class DaggerOfGreed extends SwordItem implements IAbilitable {
	public static final Ability ABILITY = Ability.create(AbilityStyles.PURPLE_GEM)
			.setRelatedItems(ItemBundle.direct(AquamiraeItems.DAGGER_OF_GREED))
			.addTier(AbilityTier.create()
					.setDescription(Component.translatable("ability.aquamirae.dagger_of_greed"))
					.addVariable(Variable.create(50).withSuffix(Variable.PERCENT)))
			.addTier(AbilityTier.create()
					.setDescription(Component.translatable("ability.aquamirae.dagger_of_greed_tier_2"))
					.addVariable(Variable.create(75).withSuffix(Variable.PERCENT))
					.addGoal(AbilityGoal.custom("obtain_emeralds", 64,
							Component.translatable("ability_goal.aquamirae.obtain_emeralds")))
					.addGoal(AbilityGoal.appliedAstralDust(1)))
			.build();

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
				&& source instanceof ServerPlayer player
				&& ABILITY.canBeUsedBy(player)) {
			final var context = ABILITY.setupContext(stack, player);
			final var random = player.getRandom();

			if (random.nextInt(100) <= context.getVariable(1)) {
				final var count = random.nextInt(1, 4);
				WorldUtil.dropItem(player.level(), entity.getEyePosition(),
						new ItemStack(Items.EMERALD, count));
				AbilityHelper.addCustomProgress(stack, "obtain_emeralds", count);
			} else if (AbilityHelper.getTierOf(stack, player) >= 2) {
				final var offers = villager.getOffers().stream().filter(offer ->
						!offer.isOutOfStock() && !offer.getResult().is(Items.EMERALD)).toList();
				if (!offers.isEmpty()) WorldUtil.dropItem(player.level(), entity.getEyePosition(),
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
}
