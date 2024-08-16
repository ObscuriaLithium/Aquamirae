package com.obscuria.aquamirae.common.recipe;

import com.obscuria.aquamirae.common.item.OxygenContainer;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import com.obscuria.aquamirae.registry.AquamiraeRecipeSerializers;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import org.apache.commons.compress.utils.Lists;

public class OxygenTankFillingRecipe extends CustomRecipe {

    public OxygenTankFillingRecipe(CraftingBookCategory category) {
        super(category);
    }

    @Override
    public boolean matches(CraftingContainer container, Level level) {
        final var holders = Lists.<ItemStack>newArrayList();
        final var oxygelium = Lists.<ItemStack>newArrayList();
        for (var stack : container.getItems())
            if (OxygenContainer.is(stack)) {
                holders.add(stack);
            } else if (stack.is(AquamiraeItems.OXYGELIUM.get())) {
                oxygelium.add(stack);
            } else if (!stack.isEmpty()) {
                return false;
            }
        if (holders.size() != 1) return false;
        if (oxygelium.size() == 0) return false;
        return !OxygenContainer.isFull(holders.get(0));
    }

    @Override
    public ItemStack assemble(CraftingContainer container, RegistryAccess access) {
        var holder = ItemStack.EMPTY;
        final var oxygelium = Lists.<ItemStack>newArrayList();
        for (var stack : container.getItems())
            if (OxygenContainer.is(stack)) {
                holder = stack.copy();
            } else if (stack.is(AquamiraeItems.OXYGELIUM.get())) {
                oxygelium.add(stack);
            }
        for (var ignored : oxygelium)
            OxygenContainer.addOxygen(holder, 20);
        return holder;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return AquamiraeRecipeSerializers.OXYGEN_TANK_FILLING.get();
    }
}
