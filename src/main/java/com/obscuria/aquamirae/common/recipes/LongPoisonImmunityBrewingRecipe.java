package com.obscuria.aquamirae.common.recipes;

import com.obscuria.aquamirae.registry.AquamiraeItems;
import com.obscuria.aquamirae.registry.AquamiraePotions;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.common.brewing.IBrewingRecipe;

public final class LongPoisonImmunityBrewingRecipe implements IBrewingRecipe {

    @Override
    public boolean isInput(ItemStack input) {
        return PotionUtils.getPotion(input) == AquamiraePotions.POISON_IMMUNITY.get();
    }

    @Override
    public boolean isIngredient(ItemStack ingredient) {
        return ingredient.is(AquamiraeItems.WISTERIA_NIVEIS.get());
    }

    @Override
    public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
        if (!isInput(input) || !isIngredient(ingredient)) return ItemStack.EMPTY;
        return PotionUtils.setPotion(input.copy(), AquamiraePotions.LONG_POISON_IMMUNITY.get());
    }
}
