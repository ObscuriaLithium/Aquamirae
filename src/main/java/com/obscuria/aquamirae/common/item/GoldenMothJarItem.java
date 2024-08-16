package com.obscuria.aquamirae.common.item;

import com.obscuria.aquamirae.registry.AquamiraeBlocks;
import com.obscuria.core.common.item.Lore;
import net.minecraft.world.item.BlockItem;

@Lore("lore.aquamirae.golden_moth_in_a_jar")
public class GoldenMothJarItem extends BlockItem {

    public GoldenMothJarItem() {
        super(AquamiraeBlocks.GOLDEN_MOTH_IN_A_JAR.get(), new Properties());
    }
}
