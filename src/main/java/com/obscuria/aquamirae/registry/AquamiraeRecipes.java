
package com.obscuria.aquamirae.registry;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.common.recipes.RuneSmithingRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AquamiraeRecipes {

	public static final DeferredRegister<RecipeSerializer<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Aquamirae.MODID);

	public static final RegistryObject<RuneSmithingRecipe.Serializer> RUNE_SMITHING = REGISTRY.register("rune_smithing", RuneSmithingRecipe.Serializer::new);
}
