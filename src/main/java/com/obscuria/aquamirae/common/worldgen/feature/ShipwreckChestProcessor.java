package com.obscuria.aquamirae.common.worldgen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.obscuria.aquamirae.registry.AquamiraeStructureProcessors;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import javax.annotation.Nullable;

public class ShipwreckChestProcessor extends StructureProcessor {
    public static final Codec<ShipwreckChestProcessor> CODEC = RecordCodecBuilder.create(builder -> builder.group(
                    ResourceLocation.CODEC.fieldOf("loot_table").forGetter(processor -> processor.lootTable),
                    Codec.DOUBLE.fieldOf("chance").forGetter(processor -> processor.chance))
            .apply(builder, ShipwreckChestProcessor::new));
    private final ResourceLocation lootTable;
    private final double chance;

    public ShipwreckChestProcessor(ResourceLocation lootTable, double chance) {
        this.lootTable = lootTable;
        this.chance = chance;
    }

    @Nullable
    @Override
    @SuppressWarnings("all")
    public StructureTemplate.StructureBlockInfo processBlock(LevelReader reader, BlockPos pos1, BlockPos pos2,
                                                             StructureTemplate.StructureBlockInfo info1,
                                                             StructureTemplate.StructureBlockInfo info2,
                                                             StructurePlaceSettings settings) {
        if (!info2.state().is(Blocks.CHEST) || info2.nbt() == null) return info2;
        if (RandomSource.create().nextDouble() > chance) return null;
        info2.nbt().putString("LootTable", lootTable.toString());
        return info2;
    }

    @Override
    protected StructureProcessorType<ShipwreckChestProcessor> getType() {
        return AquamiraeStructureProcessors.SHIPWRECK_CHEST.get();
    }
}
