package com.obscuria.aquamirae.common;

import com.obscuria.aquamirae.Aquamirae;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.Structure;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.NonExtendable
public interface AquamiraeTags {
    TagKey<Biome> ICE_MAZE = TagKey.create(Registries.BIOME, Aquamirae.key("ice_maze"));
    TagKey<Structure> SHIP = TagKey.create(Registries.STRUCTURE, Aquamirae.key("ship"));
    TagKey<Structure> OUTPOST = TagKey.create(Registries.STRUCTURE, Aquamirae.key("outpost"));
    TagKey<Structure> SHELTER = TagKey.create(Registries.STRUCTURE, Aquamirae.key("shelter"));
    TagKey<Block> EEL_CAN_DIG = BlockTags.create(Aquamirae.key("eel_can_dig"));
    TagKey<Block> MAZE_MOTHER_CAN_DESTROY = BlockTags.create(Aquamirae.key("maze_mother_can_destroy"));
    TagKey<Block> SCROLL_CAN_DESTROY = BlockTags.create(Aquamirae.key("scroll_can_destroy"));
}
