package com.obscuria.aquamirae.common.item;

import com.obscuria.aquamirae.registry.AquamiraeBlocks;
import com.obscuria.core.api.annotation.SimpleLore;
import net.minecraft.world.item.BlockItem;

@SimpleLore("lore.aquamirae.cursed_moth_in_a_jar")
public class CursedMothJarItem extends BlockItem {
    public CursedMothJarItem() {
        super(AquamiraeBlocks.CURSED_MOTH_IN_A_JAR.get(), new Properties());
    }
}
