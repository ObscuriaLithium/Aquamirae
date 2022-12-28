package com.obscuria.aquamirae.world.features;

import com.google.common.collect.ImmutableList;
import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.registry.AquamiraeEntities;
import net.minecraft.block.BlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.jigsaw.JigsawManager;
import net.minecraft.world.gen.feature.structure.*;
import net.minecraft.world.gen.feature.template.TemplateManager;

import javax.annotation.Nonnull;
import java.util.List;

public class IceMazeSpiralFeature extends Structure<NoFeatureConfig> {

   public IceMazeSpiralFeature() {
      super(NoFeatureConfig.CODEC);
   }

   @Override
   @Nonnull
   public  IStartFactory<NoFeatureConfig> getStartFactory() {
      return IceMazeSpiralFeature.Start::new;
   }

   @Override
   @Nonnull
   public GenerationStage.Decoration step() {
      return GenerationStage.Decoration.LAKES;
   }

   private static final List<MobSpawnInfo.Spawners> STRUCTURE_MONSTERS = ImmutableList.of(
           new MobSpawnInfo.Spawners(AquamiraeEntities.MAW.get(), 100, 1, 4),
           new MobSpawnInfo.Spawners(AquamiraeEntities.TORTURED_SOUL.get(), 5, 1, 4)
   );

   @Override
   public List<MobSpawnInfo.Spawners> getDefaultSpawnList() {
      return STRUCTURE_MONSTERS;
   }

   @Override
   protected boolean isFeatureChunk(ChunkGenerator chunkGenerator, @Nonnull BiomeProvider biomeSource, long seed, @Nonnull SharedSeedRandom chunkRandom, int chunkX,
                                    int chunkZ, @Nonnull Biome biome, @Nonnull ChunkPos chunkPos, @Nonnull NoFeatureConfig featureConfig) {
      BlockPos centerOfChunk = new BlockPos(chunkX * 16, 0, chunkZ * 16);
      int landHeight = chunkGenerator.getFirstOccupiedHeight(centerOfChunk.getX(), centerOfChunk.getZ(), Heightmap.Type.WORLD_SURFACE_WG);
      IBlockReader columnOfBlocks = chunkGenerator.getBaseColumn(centerOfChunk.getX(), centerOfChunk.getZ());
      BlockState topBlock = columnOfBlocks.getBlockState(centerOfChunk.above(landHeight));
      return topBlock.getMaterial().isLiquid();
   }

   public static class Start extends StructureStart<NoFeatureConfig> {
      public Start(Structure<NoFeatureConfig> structureIn, int chunkX, int chunkZ, MutableBoundingBox mutableBoundingBox, int referenceIn, long seedIn) {
         super(structureIn, chunkX, chunkZ, mutableBoundingBox, referenceIn, seedIn);
      }

      @Override
      public void generatePieces(@Nonnull DynamicRegistries dynamicRegistryManager, @Nonnull ChunkGenerator chunkGenerator, @Nonnull TemplateManager templateManagerIn,
                                 int chunkX, int chunkZ, @Nonnull Biome biomeIn, @Nonnull NoFeatureConfig config) {
         int x = chunkX * 16;
         int z = chunkZ * 16;

         BlockPos centerPos = new BlockPos(x, 0, z);

         JigsawManager.addPieces(dynamicRegistryManager,
                 new VillageConfig(() -> dynamicRegistryManager.registryOrThrow(Registry.TEMPLATE_POOL_REGISTRY).get(new ResourceLocation(AquamiraeMod.MODID, "ice_maze/spiral")), 10),
                 AbstractVillagePiece::new, chunkGenerator, templateManagerIn, centerPos, this.pieces, this.random, false, true);

         this.pieces.forEach(piece -> piece.move(0, -1, 0));

         Vector3i structureCenter = this.pieces.get(0).getBoundingBox().getCenter();
         int xOffset = centerPos.getX() - structureCenter.getX();
         int zOffset = centerPos.getZ() - structureCenter.getZ();
         for(StructurePiece structurePiece : this.pieces){
            structurePiece.move(xOffset, 0, zOffset);
         }

         this.calculateBoundingBox();
      }
   }
}