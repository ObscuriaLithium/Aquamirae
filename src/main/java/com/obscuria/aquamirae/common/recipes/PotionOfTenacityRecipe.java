
package com.obscuria.aquamirae.common.recipes;

import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.common.brewing.IBrewingRecipe;

import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;

import com.obscuria.aquamirae.registry.AquamiraeItems;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class PotionOfTenacityRecipe implements IBrewingRecipe {

	@SubscribeEvent
	public static void init(FMLCommonSetupEvent event) {
		//event.enqueueWork(() -> BrewingRecipeRegistry.addRecipe(new PotionOfTenacityRecipe()));
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
	public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
		//if (isInput(input) && isIngredient(ingredient)) return PotionUtils.setPotion(new ItemStack(Items.POTION), AquamiraePotions.POTION_OF_TENACITY.get());
		return ItemStack.EMPTY;
	}
}
