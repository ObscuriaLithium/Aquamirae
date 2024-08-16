package com.obscuria.aquamirae.common.worldgen.feature;

import com.obscuria.aquamirae.Aquamirae;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;

public class IceSpiralFeature extends Feature<NoneFeatureConfiguration> {
    public IceSpiralFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        final var level = context.level();
        final var center = context.origin()
                .offset(8, 0, 8)
                .atY(level.getHeight(
                        Heightmap.Types.OCEAN_FLOOR_WG,
                        context.origin().getX() + 8,
                        context.origin().getZ() + 8));
        placeShipwreck(level, context.random(), center);
        return true;
    }

    private void placeShipwreck(WorldGenLevel level, RandomSource random, BlockPos center) {
        final var pos = center.offset(-5, -4, -12);
        final var server = level.getServer();
        if (server == null) return;
        server.getStructureManager()
                .get(new ResourceLocation("minecraft:shipwreck/rightsideup_full"))
                .ifPresent(template -> {
                    final var settings = new StructurePlaceSettings()
                            .addProcessor(BlockIgnoreProcessor.STRUCTURE_BLOCK)
                            .addProcessor(new ShipwreckChestProcessor(Aquamirae.key("chests/shipwreck"), 0.5));
                    template.placeInWorld(level, pos, pos, settings, random, 2);
                });
    }
}
