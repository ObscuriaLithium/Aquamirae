package com.obscuria.aquamirae.common.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class PirateDoubloonItem extends Item {

    public PirateDoubloonItem() {
        super(new Item.Properties().stacksTo(32).rarity(Rarity.UNCOMMON));
    }
}
