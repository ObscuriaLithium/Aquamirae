package com.obscuria.aquamirae.common.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class OxygenTankItem extends Item implements OxygenContainer {

    public OxygenTankItem() {
        super(new Properties().stacksTo(1).rarity(Rarity.COMMON));
    }

    @Override
    public int getOxygenCapacity() {
        return 400;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.literal(OxygenContainer.getState(stack)));
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return OxygenContainer.getOxygen(stack) < getOxygenCapacity();
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        return Math.round(13f * ((float) OxygenContainer.getOxygen(stack) / (float) getOxygenCapacity()));
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return 0xFF1FA3FF;
    }
}
