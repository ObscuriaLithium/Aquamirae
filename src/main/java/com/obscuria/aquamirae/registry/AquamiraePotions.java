
package com.obscuria.aquamirae.registry;

import com.obscuria.aquamirae.Aquamirae;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.BrewingRecipeRegistry;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public interface AquamiraePotions {
	Potion SPECTRAL_POTION = new Potion(new StatusEffectInstance(StatusEffects.GLOWING, 2400, 0, false, true));
	Potion POTION_OF_TENACITY = new Potion(new StatusEffectInstance(StatusEffects.STRENGTH, 2400, 0, false, true),
					new StatusEffectInstance(StatusEffects.ABSORPTION, 2400, 2, false, true),
					new StatusEffectInstance(StatusEffects.NAUSEA, 400, 1, false, true));

	static void register() {
		Registry.register(Registries.POTION, Aquamirae.key("spectral_potion"), SPECTRAL_POTION);
		Registry.register(Registries.POTION, Aquamirae.key("potion_of_tenacity"), POTION_OF_TENACITY);
		BrewingRecipeRegistry.registerPotionRecipe(Potions.THICK, AquamiraeItems.ESCA, SPECTRAL_POTION);
		BrewingRecipeRegistry.registerPotionRecipe(Potions.THICK, AquamiraeItems.WISTERIA_NIVEIS, POTION_OF_TENACITY);
	}
}
