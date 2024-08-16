package com.obscuria.aquamirae.compat.curios.item;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;

public class StormGlovesItem extends CurioCompatItem {

    public StormGlovesItem() {
        super(new Properties().stacksTo(1).rarity(Rarity.EPIC));
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }
}
