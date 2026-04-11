package com.obscuria.aquamirae.compat;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import com.obscuria.aquamirae.registry.AquamiraePotions;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.recipe.vanilla.IJeiBrewingRecipe;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;

import java.util.List;

@JeiPlugin
public final class JEIAquamiraePlugin implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return Aquamirae.identifier("jei");
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(RecipeTypes.BREWING, List.of(
                recipe("poison_immunity", 2, Potions.AWKWARD, AquamiraeItems.JELLYFISH_JELLY.get(), AquamiraePotions.POISON_IMMUNITY.get()),
                recipe("long_poison_immunity", 3, AquamiraePotions.POISON_IMMUNITY.get(), AquamiraeItems.WISTERIA_NIVEIS.get(), AquamiraePotions.LONG_POISON_IMMUNITY.get())));
    }

    private static IJeiBrewingRecipe recipe(String id, int steps, Potion input, Item ingredient, Potion output) {
        return new SimpleBrewingRecipe(Aquamirae.identifier(id), input, ingredient, output, steps);
    }

    private record SimpleBrewingRecipe(
            ResourceLocation id,
            Potion input,
            Item ingredient,
            Potion output,
            int steps
    ) implements IJeiBrewingRecipe {

        @Override
        public List<ItemStack> getPotionInputs() {
            return List.of(PotionUtils.setPotion(new ItemStack(Items.POTION), input));
        }

        @Override
        public List<ItemStack> getIngredients() {
            return List.of(new ItemStack(ingredient));
        }

        @Override
        public ItemStack getPotionOutput() {
            return PotionUtils.setPotion(new ItemStack(Items.POTION), output);
        }

        @Override
        public int getBrewingSteps() {
            return steps;
        }

        @Override
        public ResourceLocation getUid() {
            return id;
        }
    }
}
