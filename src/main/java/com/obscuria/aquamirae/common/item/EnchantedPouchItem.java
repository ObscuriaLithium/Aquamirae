package com.obscuria.aquamirae.common.item;

import com.obscuria.aquamirae.registry.AquamiraeItems;
import com.obscuria.core.api.ability.AbilityStyles;
import com.obscuria.core.api.ability.*;
import com.obscuria.core.api.util.bundle.ItemBundle;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.enchantment.Enchantments;

import java.util.Optional;

public class EnchantedPouchItem extends Item implements IAbilitable {
    public static final Ability ABILITY = Ability.create(AbilityStyles.PURPLE_GEM)
            .setRelatedItems(ItemBundle.direct(AquamiraeItems.ENCHANTED_POUCH))
            .addTier(AbilityTier.create()
                    .setDescription(Component.translatable("ability.aquamirae.enchanted_pouch_tier_1")))
            .addTier(AbilityTier.create()
                    .setDescription(Component.translatable("ability.aquamirae.enchanted_pouch_tier_2"))
                    .addGoal(AbilityGoal.custom("pouch_1", 1, Component.literal("Some goal"))))
            .addTier(AbilityTier.create()
                    .setDescription(Component.translatable("ability.aquamirae.enchanted_pouch_tier_3"))
                    .addGoal(AbilityGoal.custom("pouch_2", 1, Component.literal("Some goal"))))
            .addTier(AbilityTier.create()
                    .setDescription(Component.translatable("ability.aquamirae.enchanted_pouch_tier_4"))
                    .addGoal(AbilityGoal.custom("pouch_3", 1, Component.literal("Some goal"))))
            .build();

    public EnchantedPouchItem() {
        super(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1));
    }

    @Override
    public ItemStack getDefaultInstance() {
        final var result = super.getDefaultInstance();
        result.enchant(Enchantments.VANISHING_CURSE, 1);
        return result;
    }

    @Override
    public Optional<Ability> getAbility(ItemStack stack) {
        return Optional.of(ABILITY);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }
}
