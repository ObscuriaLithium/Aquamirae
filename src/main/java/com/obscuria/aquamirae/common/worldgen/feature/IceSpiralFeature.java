package com.obscuria.aquamirae.common.worldgen.feature;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.obscuria.aquamirae.registry.AquamiraeBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;

import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;

public class IceSpiralFeature extends Feature<NoneFeatureConfiguration> {
    public IceSpiralFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        final var level = context.level();
        final var origin = context.origin()
                .offset(8, 0, 8)
                .atY(level.getHeight(Heightmap.Types.OCEAN_FLOOR_WG,
                        context.origin().getX(),
                        context.origin().getZ()) - 30);

        final var grottoArea = generateGrotto(level, origin);
        final var grottoFloor = grottoArea.stream()
                .filter(pos -> level.isWaterAt(pos.above())
                        && !grottoArea.contains(pos.below()))
                .map(BlockPos::below)
                .toList();

        generateGrottoOuter(level, origin, grottoArea);
        generateMudPatches(level, grottoFloor);
        generatePillars(level, grottoFloor);
        placeShipwreck(level, context.random(), origin);
        return true;
    }

    private List<BlockPos> generateGrotto(WorldGenLevel level, BlockPos origin) {
        final var grottoArea = Lists.<BlockPos>newLinkedList();
        forEachInCube(origin, 18, (offset, pos) -> {
            final var maxDistance = offset.getY() < 0
                    ? 18f - Math.pow(Math.abs(offset.getY()) * 1.12f, 2)
                    : 18f - Math.pow(Math.abs(offset.getY()) * 0.12f, 2);
            final var distance = origin.getCenter().distanceTo(pos.getCenter());
            if (distance > maxDistance) return;
            setBlock(level, pos, Blocks.WATER.defaultBlockState());
            grottoArea.add(pos);
        });
        return grottoArea;
    }

    private void generateGrottoOuter(WorldGenLevel level, BlockPos origin, List<BlockPos> area) {
        for (var center : area)
            forEachInCube(center, 1, (offset, pos) -> {
                if (area.contains(pos)) return;
                if (pos.getY() < origin.getY()) {
                    setBlock(level, pos, level.getRandom().nextBoolean()
                            ? Blocks.BASALT.defaultBlockState()
                            : Blocks.DEEPSLATE.defaultBlockState());
                } else {
                    setBlock(level, pos, Blocks.SMOOTH_BASALT.defaultBlockState());
                }
            });
    }

    private void generateMudPatches(WorldGenLevel level, List<BlockPos> floor) {
        final var random = level.getRandom();
        for (int i = 0; i <= random.nextInt(6, 13); i++) {
            final var center = floor.get(random.nextInt(0, floor.size()));
            final var size = random.nextInt(2, 6);
            forEachInSphere(center, size, (offset, pos) -> {
                if (!floor.contains(pos)) return;
                setBlock(level, pos, Blocks.MUD.defaultBlockState());
            });
            forEachInSphere(center, size * 2, (offset, pos) -> {
                if (!floor.contains(pos)) return;
                if (!level.getBlockState(pos).is(Blocks.BASALT)) return;
                setBlock(level, pos, Blocks.MUD.defaultBlockState());
                setBlock(level, pos.above(), AquamiraeBlocks.ELODEA.get().defaultBlockState());
            });
        }
    }

    private void generatePillars(WorldGenLevel level, List<BlockPos> floor) {
        final var random = level.getRandom();
        for (int i = 0; i <= random.nextInt(6, 13); i++) {
            final var size = random.nextInt(2, 6);
            var pillarEdge = floor.get(random.nextInt(0, floor.size())).above();
            final var start = pillarEdge.getY() - 1;
            final var end = pillarEdge.getY() + 16;

            for (int y = start; y <= end; y++) {
                final var center = pillarEdge.atY(y);
                final var cubic = getCubicValue(y, start, end);
                forEachInSphere(center, Math.round(1f + cubic * size),
                        (offset, pos) -> {
                            if (pos.getY() != center.getY()) return;
                            setBlock(level, pos, Blocks.BASALT.defaultBlockState());
                        });
            }
            forEachInSphere(pillarEdge.atY(start - 1), 1 + size, (offset, pos) -> {
                if (offset.getY() > 0) return;
                setBlock(level, pos, Blocks.BASALT.defaultBlockState());
            });
            forEachInSphere(pillarEdge.atY(end + 1), 1 + size, (offset, pos) -> {
                if (offset.getY() < 0) return;
                setBlock(level, pos, Blocks.BASALT.defaultBlockState());
            });
        }
    }

    private void placeShipwreck(WorldGenLevel level, RandomSource random, BlockPos origin) {
        final var pos = origin.offset(-5, -4, -12);
        level.getServer().getStructureManager()
                .get(new ResourceLocation("minecraft:shipwreck/rightsideup_full"))
                .ifPresent(template -> {
                    final var settings = new StructurePlaceSettings()
                            .clearProcessors().addProcessor(BlockIgnoreProcessor.STRUCTURE_BLOCK);
                    template.placeInWorld(level, pos, pos, settings, random, 2);
                });
    }

    private void forEachInCube(BlockPos center, int radius, BiConsumer<BlockPos, BlockPos> consumer) {
        for (int x = -radius; x <= radius; x++)
            for (int y = -radius; y <= radius; y++)
                for (int z = -radius; z <= radius; z++)
                    consumer.accept(new BlockPos(x, y, z), center.offset(x, y, z));
    }

    private void forEachInSphere(BlockPos center, int radius, BiConsumer<BlockPos, BlockPos> consumer) {
        forEachInCube(center, radius, (offset, pos) -> {
            if (pos.getCenter().distanceTo(center.getCenter()) > radius) return;
            consumer.accept(offset, pos);
        });
    }

    private float getCubicValue(int index, int start, int end) {
        var ratio = (index - start) / 1f / (end - start);
        ratio = ratio <= 0.5f ? 1f - ratio * 2f : (ratio - 0.5f) * 2f;
        return (float) Math.pow(ratio, 2);
    }

    private void fragment(WorldGenLevel level, BlockPos center, float size) {
        for (int x = (int) -size; x <= size; x++)
            for (int y = (int) -size; y <= size; y++)
                for (int z = (int) -size; z <= size; z++) {
                    final var pos = center.offset(x, y, z);
                    final var distance = center.getCenter().distanceTo(pos.getCenter());
                    if (distance > size) continue;
                    setBlock(level, pos, Blocks.ICE.defaultBlockState());
                }
    }
}
