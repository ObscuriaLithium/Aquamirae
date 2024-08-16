package com.obscuria.aquamirae.compat.curios.item;

import com.google.common.collect.Multimap;
import com.obscuria.core.common.item.Lore;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.common.ForgeMod;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.UUID;

@Lore("lore.aquamirae.ship_graveyard_ring")
public class ShipGraveyardRingItem extends CurioCompatItem {

    public ShipGraveyardRingItem() {
        super(new Properties().stacksTo(1).rarity(Rarity.UNCOMMON));
    }

    public static class Curio implements ICurioItem {
        @Override
        public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext context, UUID uuid, ItemStack stack) {
            final var multimap = ICurioItem.super.getAttributeModifiers(context, uuid, stack);
            multimap.put(ForgeMod.SWIM_SPEED.get(), new AttributeModifier(uuid, "Bonus",
                    0.15, AttributeModifier.Operation.MULTIPLY_BASE));
            return multimap;
        }
    }
}
