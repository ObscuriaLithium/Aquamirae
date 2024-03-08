package com.obscuria.aquamirae.compat.curios.item;

import com.obscuria.aquamirae.compat.AquamiraeCompats;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CurioItem extends Item {
    public CurioItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        AquamiraeCompats.CURIOS_API.warnIfMissing(tooltip::add);
    }
}
