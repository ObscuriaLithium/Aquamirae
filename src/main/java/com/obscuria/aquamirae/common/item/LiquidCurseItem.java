package com.obscuria.aquamirae.common.item;

import com.obscuria.aquamirae.common.DeadSeaCurse;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import com.obscuria.core.common.item.Lore;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;

@Lore("lore.aquamirae.liquid_curse")
public class LiquidCurseItem extends Item {

    public LiquidCurseItem() {
        super(new Properties().rarity(Rarity.UNCOMMON).stacksTo(8));
    }

    @Override
    public boolean overrideStackedOnOther(ItemStack self, Slot slot, ClickAction action, Player player) {
        if (!slot.mayPickup(player)) return false;
        if (action != ClickAction.SECONDARY) return false;
        final var stack = slot.getItem();
        if (stack.is(AquamiraeItems.ABYSSAL_AMETHYST.get())) return false;
        if (DeadSeaCurse.isCursed(stack)) {
            player.playSound(SoundEvents.BOTTLE_EMPTY, 1,
                    (float) player.getRandom().triangle(1, 0.2));
            DeadSeaCurse.makeSuppressed(stack);
            self.shrink(1);
            return true;
        }
        return false;
    }
}
