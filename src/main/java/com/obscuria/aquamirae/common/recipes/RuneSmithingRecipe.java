package com.obscuria.aquamirae.common.recipes;

import com.google.gson.JsonObject;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import com.obscuria.aquamirae.registry.AquamiraeRecipes;
import com.obscuria.obscureapi.util.ItemUtils;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public final class RuneSmithingRecipe implements SmithingRecipe {

    private final ResourceLocation id;

    public RuneSmithingRecipe(ResourceLocation id) {
        this.id = id;
    }

    @Override
    public boolean isTemplateIngredient(ItemStack stack) {
        return stack.isEmpty();
    }

    @Override
    public boolean isBaseIngredient(ItemStack stack) {
        return stack.getItem() instanceof SwordItem;
    }

    @Override
    public boolean isAdditionIngredient(ItemStack stack) {
        return stack.is(AquamiraeItems.RUNE_OF_THE_STORM.get());
    }

    @Override
    public boolean matches(Container container, Level level) {
        return isTemplateIngredient(container.getItem(0))
                && isBaseIngredient(container.getItem(1))
                && isAdditionIngredient(container.getItem(2));
    }

    @Override
    public ItemStack assemble(Container container, RegistryAccess access) {
        final var result = container.getItem(1).copy();
        ItemUtils.addPerk(result, new ResourceLocation("aquamirae", "rune_of_the_storm"), 1);
        return result;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess access) {
        return ItemStack.EMPTY;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public Serializer getSerializer() {
        return AquamiraeRecipes.RUNE_SMITHING.get();
    }

    public static class Serializer implements RecipeSerializer<RuneSmithingRecipe> {

        public RuneSmithingRecipe fromJson(ResourceLocation id, JsonObject object) {
            return new RuneSmithingRecipe(id);
        }

        public RuneSmithingRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            return new RuneSmithingRecipe(id);
        }

        public void toNetwork(FriendlyByteBuf buf, RuneSmithingRecipe recipe) {}
    }
}
