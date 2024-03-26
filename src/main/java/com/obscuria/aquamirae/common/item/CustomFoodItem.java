package com.obscuria.aquamirae.common.item;

import com.machinezoo.noexception.optional.OptionalSupplier;
import com.obscuria.core.api.util.PlayerUtil;
import com.obscuria.core.common.item.BuffedFoodItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class CustomFoodItem extends BuffedFoodItem {
    private final OptionalSupplier<Item> resultItem;
    private final int useDuration;
    private final boolean isFoil;

    public CustomFoodItem(OptionalSupplier<Item> resultItem, int useDuration, boolean isFoil, Properties properties) {
        super(properties);
        this.resultItem = resultItem;
        this.useDuration = useDuration;
        this.isFoil = isFoil;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return this.useDuration;
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return this.isFoil;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        super.finishUsingItem(stack, level, entity);
        if (entity instanceof Player player && !player.getAbilities().instabuild)
            this.resultItem.get().ifPresent(item -> {
                final var result = item.getDefaultInstance();
                PlayerUtil.giveItem(player, result);
            });
        return stack;
    }
}
