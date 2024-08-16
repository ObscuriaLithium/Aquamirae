package com.obscuria.aquamirae.common.item;

import com.obscuria.core.common.item.Lore;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@Lore("lore.aquamirae.oxygen_generator")
public class OxygenGeneratorItem extends Item implements OxygenContainer {

    public OxygenGeneratorItem() {
        super(new Properties().stacksTo(1).rarity(Rarity.EPIC));
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean flag) {
        if (entity.tickCount % 20 != 0 || OxygenContainer.isFull(stack)) return;
        OxygenContainer.addOxygen(stack, 1);
    }

    @Override
    public int getOxygenCapacity() {
        return 2000;
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
