
package com.obscuria.aquamirae.registry;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.common.entities.*;
import com.obscuria.aquamirae.common.entities.projectiles.MazeRose;
import com.obscuria.aquamirae.common.entities.projectiles.PoisonedChakra;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.world.Heightmap;

@SuppressWarnings("all")
public interface AquamiraeEntities {
	EntityType<GoldenMothEntity> GOLDEN_MOTH = FabricEntityTypeBuilder.create(SpawnGroup.AMBIENT, GoldenMothEntity::new).dimensions(EntityDimensions.fixed(0.5f, 0.2f)).trackRangeBlocks(128).fireImmune().build();
	EntityType<MawEntity> MAW = FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, MawEntity::new).dimensions(EntityDimensions.fixed(1.2f, 1.2f)).build();
	EntityType<AnglerfishEntity> ANGLERFISH = FabricEntityTypeBuilder.create(SpawnGroup.WATER_CREATURE, AnglerfishEntity::new).dimensions(EntityDimensions.fixed(2f, 2.6f)).build();
	EntityType<MazeMotherEntity> MAZE_MOTHER = FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, MazeMotherEntity::new).dimensions(EntityDimensions.fixed(8f, 3f)).build();
	EntityType<CaptainCorneliaEntity> CAPTAIN_CORNELIA = FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, CaptainCorneliaEntity::new).dimensions(EntityDimensions.fixed(0.6f, 2.3f)).fireImmune().build();
	EntityType<PillagersPatrolSpawner> PILLAGERS_PATROL = FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, PillagersPatrolSpawner::new).dimensions(EntityDimensions.fixed(1f, 1f)).fireImmune().build();
	EntityType<TorturedSoulEntity> TORTURED_SOUL = FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, TorturedSoulEntity::new).dimensions(EntityDimensions.fixed(0.6f, 1.8f)).build();
	EntityType<EelEntity> EEL = FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, EelEntity::new).dimensions(EntityDimensions.fixed(2.4f, 3.4f)).build();
	EntityType<MazeRose> MAZE_ROSE = FabricEntityTypeBuilder.create(SpawnGroup.MISC, MazeRose::new).dimensions(EntityDimensions.fixed(1.3f, 0.15f)).build();
	EntityType<PoisonedChakra> POISONED_CHAKRA = FabricEntityTypeBuilder.create(SpawnGroup.MISC, PoisonedChakra::new).dimensions(EntityDimensions.fixed(1.3f, 0.15f)).build();
	EntityType<SpinefishEntity> SPINEFISH = FabricEntityTypeBuilder.create(SpawnGroup.WATER_AMBIENT, SpinefishEntity::new).dimensions(EntityDimensions.fixed(0.7f, 0.7f)).build();

	static void register() {
		Registry.register(Registries.ENTITY_TYPE, Aquamirae.key("golden_moth"), GOLDEN_MOTH);
		Registry.register(Registries.ENTITY_TYPE, Aquamirae.key("maw"), MAW);
		Registry.register(Registries.ENTITY_TYPE, Aquamirae.key("anglerfish"), ANGLERFISH);
		Registry.register(Registries.ENTITY_TYPE, Aquamirae.key("maze_mother"), MAZE_MOTHER);
		Registry.register(Registries.ENTITY_TYPE, Aquamirae.key("captain_cornelia"), CAPTAIN_CORNELIA);
		Registry.register(Registries.ENTITY_TYPE, Aquamirae.key("pillagers_patrol"), PILLAGERS_PATROL);
		Registry.register(Registries.ENTITY_TYPE, Aquamirae.key("tortured_soul"), TORTURED_SOUL);
		Registry.register(Registries.ENTITY_TYPE, Aquamirae.key("eel"), EEL);
		Registry.register(Registries.ENTITY_TYPE, Aquamirae.key("maze_rose"), MAZE_ROSE);
		Registry.register(Registries.ENTITY_TYPE, Aquamirae.key("poisoned_chakra"), POISONED_CHAKRA);
		Registry.register(Registries.ENTITY_TYPE, Aquamirae.key("spinefish"), SPINEFISH);

		FabricDefaultAttributeRegistry.register(GOLDEN_MOTH, GoldenMothEntity.createAttributes());
		FabricDefaultAttributeRegistry.register(MAW, MawEntity.createAttributes());
		FabricDefaultAttributeRegistry.register(ANGLERFISH, AnglerfishEntity.createAttributes());
		FabricDefaultAttributeRegistry.register(MAZE_MOTHER, MazeMotherEntity.createAttributes());
		FabricDefaultAttributeRegistry.register(CAPTAIN_CORNELIA, CaptainCorneliaEntity.createAttributes());
		FabricDefaultAttributeRegistry.register(PILLAGERS_PATROL, PillagersPatrolSpawner.createHostileAttributes());
		FabricDefaultAttributeRegistry.register(TORTURED_SOUL, TorturedSoulEntity.createAttributes());
		FabricDefaultAttributeRegistry.register(EEL, EelEntity.createAttributes());
		FabricDefaultAttributeRegistry.register(MAZE_ROSE, MazeRose.createLivingAttributes());
		FabricDefaultAttributeRegistry.register(POISONED_CHAKRA, PoisonedChakra.createLivingAttributes());
		FabricDefaultAttributeRegistry.register(SPINEFISH, SpinefishEntity.createFishAttributes());

		SpawnRestriction.register(GOLDEN_MOTH, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, GoldenMothEntity.getSpawnRules());
		SpawnRestriction.register(SPINEFISH, SpawnRestriction.Location.IN_WATER, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, SpinefishEntity.getSpawnRules());
		SpawnRestriction.register(ANGLERFISH, SpawnRestriction.Location.IN_WATER, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnglerfishEntity.getSpawnRules());
		SpawnRestriction.register(MAW, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MawEntity.getSpawnRules());
		SpawnRestriction.register(TORTURED_SOUL, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, TorturedSoulEntity.getSpawnRules());
		SpawnRestriction.register(PILLAGERS_PATROL, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, PillagersPatrolSpawner.getSpawnRules());

		BiomeModifications.addSpawn(AquamiraeEntities::inIceMaze, SpawnGroup.WATER_CREATURE, ANGLERFISH, 100, 1, 2);
		BiomeModifications.addSpawn(AquamiraeEntities::inIceMaze, SpawnGroup.WATER_AMBIENT, SPINEFISH, 100, 3, 9);
		BiomeModifications.addSpawn(AquamiraeEntities::inIceMaze, SpawnGroup.MONSTER, TORTURED_SOUL, 100, 1, 4);
		BiomeModifications.addSpawn(AquamiraeEntities::inIceMaze, SpawnGroup.MONSTER, PILLAGERS_PATROL, 5, 1, 1);
	}

	static boolean inIceMaze(BiomeSelectionContext context) {
		return context.getBiomeRegistryEntry().isIn(Aquamirae.ICE_MAZE);
	}
}
