package com.obscuria.aquamirae.common.items;

import com.mojang.datafixers.util.Pair;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class FoodItem extends Item {

    private final @Nullable Supplier<Item> remainder;
    private final boolean isFoil;

    public FoodItem(Properties properties) {
        this(null, false, properties);
    }

    public FoodItem(@Nullable Supplier<Item> remainder, Properties properties) {
        this(remainder, false, properties);
    }

    public FoodItem(boolean isFoil, Properties properties) {
        this(null, isFoil, properties);
    }

    public FoodItem(@Nullable Supplier<Item> remainder, boolean isFoil, Properties properties) {
        super(properties);
        this.remainder = remainder;
        this.isFoil = isFoil;
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return isFoil || super.isFoil(stack);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entity) {
        var original = super.finishUsingItem(stack, world, entity);
        if (remainder == null) return original;

        var result = remainder.get().getDefaultInstance();
        if (stack.isEmpty()) return result;
        if (entity instanceof Player player
                && !player.getAbilities().instabuild
                && !player.getInventory().add(result)) {
            player.drop(result, false);
        }
        return stack;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        @Nullable var foodProperties = stack.getFoodProperties(null);
        if (foodProperties == null) return;
        var foodEffects = foodProperties.getEffects().stream().map(Pair::getFirst).toList();
        PotionUtils.addPotionTooltip(foodEffects, tooltip, 1F);
    }
}
