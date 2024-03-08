package com.obscuria.aquamirae.common.item;

import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;

public interface OxygenHolder {

    static boolean is(ItemStack stack) {
        return stack.getItem() instanceof OxygenHolder;
    }

    static int getCapacity(ItemStack stack) {
        return stack.getItem() instanceof OxygenHolder holder
                ? holder.getOxygenCapacity() : 0;
    }

    static void setOxygen(ItemStack stack, int amount) {
        if (!is(stack)) return;
        final var capacity = getCapacity(stack);
        stack.getOrCreateTag().putInt("oxygenUsed",
                Mth.clamp(capacity - amount, 0, capacity));
    }

    static int getOxygen(ItemStack stack) {
        if (!is(stack)) return 0;
        final var capacity = getCapacity(stack);
        return Mth.clamp(capacity - stack.getOrCreateTag()
                .getInt("oxygenUsed"), 0, capacity);
    }

    static void addOxygen(ItemStack stack, int amount) {
        setOxygen(stack, getOxygen(stack) + amount);
    }

    static int takeOxygen(ItemStack stack, int amount) {
        if (!is(stack)) return 0;
        final var available = getOxygen(stack);
        if (available >= amount) {
            addOxygen(stack, -amount);
            return amount;
        } else {
            setOxygen(stack, 0);
            return available;
        }
    }

    static boolean isFull(ItemStack stack) {
        if (!is(stack)) return false;
        return getOxygen(stack) >= getCapacity(stack);
    }

    static boolean isEmpty(ItemStack stack) {
        if (!is(stack)) return true;
        return getOxygen(stack) <= 0;
    }

    static String getState(ItemStack stack) {
        final var capacity = getCapacity(stack);
        final var amount = getOxygen(stack);
        return String.format("§7%s§8/%s %s%%", amount, capacity,
                Math.round(((float) amount / (float) capacity) * 100));
    }

    int getOxygenCapacity();
}
