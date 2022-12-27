
package com.obscuria.aquamirae.world.recipes;

import com.obscuria.aquamirae.registry.AquamiraeItems;
import com.obscuria.aquamirae.registry.AquamiraePotions;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.common.brewing.IBrewingRecipe;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import javax.annotation.Nonnull;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class PotionOfTenacityRecipe implements IBrewingRecipe {
	@SubscribeEvent
	public static void init(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> BrewingRecipeRegistry.addRecipe(new PotionOfTenacityRecipe()));
	}

	@Override
	public boolean isInput(ItemStack input) {
		return input.getItem() == Items.HONEY_BOTTLE;
	}

	@Override
	public boolean isIngredient(ItemStack ingredient) {
		return ingredient.getItem() == AquamiraeItems.WISTERIA_NIVEIS.get();
	}

	@Override
	@Nonnull
	public ItemStack getOutput(@Nonnull ItemStack input, @Nonnull ItemStack ingredient) {
		if (isInput(input) && isIngredient(ingredient)) {
			return PotionUtils.setPotion(new ItemStack(Items.POTION), AquamiraePotions.POTION_OF_TENACITY.get());
		}
		return ItemStack.EMPTY;
	}
}
