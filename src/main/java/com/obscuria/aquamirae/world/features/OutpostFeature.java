package com.obscuria.aquamirae.world.features;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.structure.BuiltinStructureSets;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.PostPlacementProcessor;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGenerator;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class OutpostFeature extends Structure<NoFeatureConfig> {

   public static final Codec<JigsawConfiguration> CODEC = RecordCodecBuilder.create((codec) -> codec.group(
           StructureTemplatePool.CODEC.fieldOf("start_pool").forGetter(JigsawConfiguration::startPool),
           Codec.intRange(0, 30).fieldOf("size").forGetter(JigsawConfiguration::maxDepth)
   ).apply(codec, JigsawConfiguration::new));

   public OutpostFeature() {
      super(CODEC, OutpostFeature::createPiecesGenerator, PostPlacementProcessor.NONE);
   }

   @Override
   public GenerationStep.@NotNull Decoration step() {
      return GenerationStep.Decoration.SURFACE_STRUCTURES;
   }

   private static boolean isFeatureChunk(PieceGeneratorSupplier.Context<JigsawConfiguration> context) {
      ChunkPos chunkpos = context.chunkPos();
      return !context.chunkGenerator().hasFeatureChunkInRange(BuiltinStructureSets.OCEAN_MONUMENTS, context.seed(), chunkpos.x, chunkpos.z, 5);
   }

   public static Optional<PieceGenerator<JigsawConfiguration>> createPiecesGenerator(PieceGeneratorSupplier.Context<JigsawConfiguration> context) {

      if (!OutpostFeature.isFeatureChunk(context)) {
         return Optional.empty();
      }
      BlockPos blockpos = context.chunkPos().getMiddleBlockPosition(0);
      blockpos = blockpos.below(0);
      return JigsawPlacement.addPieces(context, PoolElementStructurePiece::new, blockpos, false, true );
   }
}