package com.obscuria.aquamirae.common.item;

import com.obscuria.aquamirae.registry.AquamiraeBlocks;
import com.obscuria.core.api.annotation.SimpleLore;
import net.minecraft.world.item.BlockItem;

@SimpleLore("lore.aquamirae.luminescent_lamp")
public class LuminescentLampItem extends BlockItem {
    public LuminescentLampItem() {
        super(AquamiraeBlocks.LUMINESCENT_LAMP.get(), new Properties());
    }
}
