package com.obscuria.aquamirae.datagen;

import net.minecraftforge.data.event.GatherDataEvent;

public final class AquamiraeData {

    public static void onGatherData(GatherDataEvent event) {
        final var generator = event.getGenerator();
        generator.addProvider(event.includeServer(), new AquamiraeLootTableProvider(generator.getPackOutput()));
    }
}
