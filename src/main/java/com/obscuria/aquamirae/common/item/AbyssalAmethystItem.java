package com.obscuria.aquamirae.common.item;

import com.obscuria.aquamirae.common.DeadSeaCurse;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

@DeadSeaCurse.ByDefault
public class AbyssalAmethystItem extends Item {
    public AbyssalAmethystItem() {
        super(new Properties().stacksTo(64).rarity(Rarity.UNCOMMON));
    }
}
