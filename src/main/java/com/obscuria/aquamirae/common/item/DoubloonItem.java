package com.obscuria.aquamirae.common.item;

import com.obscuria.core.common.item.Lore;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.enchantment.Enchantments;

@Lore("lore.aquamirae.doubloon")
public class DoubloonItem extends Item {

    public DoubloonItem() {
        super(new Item.Properties().stacksTo(32).rarity(Rarity.UNCOMMON));
    }

    @Override
    public ItemStack getDefaultInstance() {
        final var stack = super.getDefaultInstance();
        stack.enchant(Enchantments.VANISHING_CURSE, 1);
        return stack;
    }
}
