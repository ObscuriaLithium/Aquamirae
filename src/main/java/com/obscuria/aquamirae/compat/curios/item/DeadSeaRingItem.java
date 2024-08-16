package com.obscuria.aquamirae.compat.curios.item;

import com.google.common.collect.Multimap;
import com.obscuria.aquamirae.registry.AquamiraeAttributes;
import com.obscuria.core.common.item.Lore;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.UUID;

@Lore("lore.aquamirae.dead_sea_ring")
public class DeadSeaRingItem extends CurioCompatItem {

    public DeadSeaRingItem() {
        super(new Properties().stacksTo(1).rarity(Rarity.EPIC));
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }

    public static class Curio implements ICurioItem {

        @Override
        public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext context, UUID uuid, ItemStack stack) {
            final var multimap = ICurioItem.super.getAttributeModifiers(context, uuid, stack);
            multimap.put(AquamiraeAttributes.DEPTHS_FURY.get(), new AttributeModifier(
                    uuid, "Bonus", 0.25, AttributeModifier.Operation.MULTIPLY_BASE));
            multimap.put(Attributes.MAX_HEALTH, new AttributeModifier(
                    uuid, "Curse", -0.25, AttributeModifier.Operation.MULTIPLY_BASE));
            return multimap;
        }
    }
}
