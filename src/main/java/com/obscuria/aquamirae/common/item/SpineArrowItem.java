package com.obscuria.aquamirae.common.item;

import com.obscuria.aquamirae.common.entity.projectile.SpineArrow;
import com.obscuria.core.common.item.Lore;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;

@Lore("lore.aquamirae.spine_arrow")
public class SpineArrowItem extends ArrowItem {

    public SpineArrowItem() {
        super(new Properties().rarity(Rarity.UNCOMMON));
    }

    @Override
    public AbstractArrow createArrow(Level level, ItemStack stack_43238_, LivingEntity entity) {
        return new SpineArrow(level, entity, stack_43238_.copyWithCount(1));
    }
}
