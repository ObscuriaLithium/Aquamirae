package com.obscuria.aquamirae.common.item;

import com.obscuria.core.common.item.Lore;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

@Lore("lore.aquamirae.frozen_key")
public class FrozenKeyItem extends Item {

    public FrozenKeyItem() {
        super(new Properties().stacksTo(1).rarity(Rarity.UNCOMMON));
    }
}
