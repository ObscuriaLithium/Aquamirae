package com.obscuria.aquamirae.common.item;

import com.obscuria.aquamirae.common.entity.projectile.AbyssalArrow;
import com.obscuria.core.common.item.Lore;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;

@Lore("lore.aquamirae.abyssal_arrow")
public class AbyssalArrowItem extends ArrowItem {

    public AbyssalArrowItem() {
        super(new Properties().rarity(Rarity.EPIC));
    }

    @Override
    public AbstractArrow createArrow(Level level, ItemStack stack, LivingEntity entity) {
        return new AbyssalArrow(level, entity, stack.copyWithCount(1));
    }
}
