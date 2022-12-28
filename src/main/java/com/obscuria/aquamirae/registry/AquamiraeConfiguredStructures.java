package com.obscuria.aquamirae.registry;

import com.obscuria.aquamirae.AquamiraeMod;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.FlatGenerationSettings;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;


public class AquamiraeConfiguredStructures {

    public static final StructureFeature<?, ?> CONFIGURED_ICE_MAZE = AquamiraeStructures.ICE_MAZE.get().configured(IFeatureConfig.NONE);
    public static final StructureFeature<?, ?> CONFIGURED_SHIP = AquamiraeStructures.SHIP.get().configured(IFeatureConfig.NONE);
    public static final StructureFeature<?, ?> CONFIGURED_OUTPOST = AquamiraeStructures.OUTPOST.get().configured(IFeatureConfig.NONE);
    public static final StructureFeature<?, ?> CONFIGURED_SHELTER = AquamiraeStructures.SHELTER.get().configured(IFeatureConfig.NONE);

    public static void register() {
        final Registry<StructureFeature<?, ?>> REGISTRY = WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE;

        Registry.register(REGISTRY, new ResourceLocation(AquamiraeMod.MODID, "configured_ice_maze"), CONFIGURED_ICE_MAZE);
        Registry.register(REGISTRY, new ResourceLocation(AquamiraeMod.MODID, "configured_ship"), CONFIGURED_SHIP);
        Registry.register(REGISTRY, new ResourceLocation(AquamiraeMod.MODID, "configured_outpost"), CONFIGURED_OUTPOST);
        Registry.register(REGISTRY, new ResourceLocation(AquamiraeMod.MODID, "configured_shelter"), CONFIGURED_SHELTER);

        FlatGenerationSettings.STRUCTURE_FEATURES.put(AquamiraeStructures.ICE_MAZE.get(), CONFIGURED_ICE_MAZE);
        FlatGenerationSettings.STRUCTURE_FEATURES.put(AquamiraeStructures.SHIP.get(), CONFIGURED_SHIP);
        FlatGenerationSettings.STRUCTURE_FEATURES.put(AquamiraeStructures.OUTPOST.get(), CONFIGURED_OUTPOST);
        FlatGenerationSettings.STRUCTURE_FEATURES.put(AquamiraeStructures.SHELTER.get(), CONFIGURED_SHELTER);
    }
}
