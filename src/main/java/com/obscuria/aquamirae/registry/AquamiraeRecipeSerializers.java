package com.obscuria.aquamirae.registry;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.common.recipe.OxygenTankFillingRecipe;
import com.obscuria.core.registry.RegistryHandler;
import com.obscuria.core.registry.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.NonExtendable
public interface AquamiraeRecipeSerializers {
    RegistryHandler<RecipeSerializer<?>> HANDLER = RegistryHandler.create(Registries.RECIPE_SERIALIZER, Aquamirae.MODID);

    RegistrySupplier<RecipeSerializer<OxygenTankFillingRecipe>> OXYGEN_TANK_FILLING = simple("oxygen_tank_filling",
            OxygenTankFillingRecipe::new);

    private static <V extends CraftingRecipe> RegistrySupplier<RecipeSerializer<V>> simple(String key, SimpleCraftingRecipeSerializer.Factory<V> factory) {
        return HANDLER.register(key, () -> new SimpleCraftingRecipeSerializer<>(factory));
    }
}
