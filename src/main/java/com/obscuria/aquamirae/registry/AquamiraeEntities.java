
package com.obscuria.aquamirae.registry;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.common.entity.*;
import com.obscuria.aquamirae.common.entity.projectile.MazeRose;
import com.obscuria.aquamirae.common.entity.projectile.PoisonedChakra;
import com.obscuria.core.api.registry.RegistryHandler;
import com.obscuria.core.api.registry.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import org.jetbrains.annotations.ApiStatus;

import java.util.function.Function;

@ApiStatus.NonExtendable
public interface AquamiraeEntities {
	RegistryHandler<EntityType<?>> HANDLER = RegistryHandler.create(Registries.ENTITY_TYPE, Aquamirae.MODID);

	RegistrySupplier<EntityType<GoldenMoth>> GOLDEN_MOTH = simple("golden_moth", GoldenMoth::new, MobCategory.AMBIENT,
			builder -> builder.setTrackingRange(64).fireImmune().sized(0.5f, 0.2f));
	RegistrySupplier<EntityType<Maw>> MAW = simple("maw", Maw::new, MobCategory.MONSTER,
			builder -> builder.setTrackingRange(64).sized(1.2f, 1.2f));
	RegistrySupplier<EntityType<Anglerfish>> ANGLERFISH = simple("anglerfish", Anglerfish::new, MobCategory.WATER_CREATURE,
			builder -> builder.setTrackingRange(64).sized(2f, 2.6f));
	RegistrySupplier<EntityType<MazeMother>> MAZE_MOTHER = simple("maze_mother", MazeMother::new, MobCategory.MONSTER,
			builder -> builder.setTrackingRange(128).sized(8f, 3f));
	RegistrySupplier<EntityType<CaptainCornelia>> CAPTAIN_CORNELIA = simple("captain_cornelia", CaptainCornelia::new, MobCategory.MONSTER,
			builder -> builder.setTrackingRange(64).fireImmune().sized(0.6f, 2.3f));
	RegistrySupplier<EntityType<PillagersPatrol>> PILLAGERS_PATROL = simple("pillagers_patrol", PillagersPatrol::new, MobCategory.MONSTER,
			builder -> builder.setTrackingRange(8).fireImmune().sized(1f, 1f));
	RegistrySupplier<EntityType<TorturedSoul>> TORTURED_SOUL = simple("tortured_soul", TorturedSoul::new, MobCategory.MONSTER,
			builder -> builder.setTrackingRange(64).sized(0.6f, 1.8f));
	RegistrySupplier<EntityType<Eel>> EEL = simple("eel", Eel::new, MobCategory.MONSTER,
			builder -> builder.setTrackingRange(64).sized(2.4f, 3.4f));
	RegistrySupplier<EntityType<MazeRose>> MAZE_ROSE = simple("maze_rose", MazeRose::new, MobCategory.MISC,
			builder -> builder.setTrackingRange(128).sized(1.3f, 0.15f));
	RegistrySupplier<EntityType<PoisonedChakra>> POISONED_CHAKRA = simple("poisoned_chakra", PoisonedChakra::new, MobCategory.MISC,
			builder -> builder.setTrackingRange(128).sized(1.3f, 0.15f));
	RegistrySupplier<EntityType<Spinefish>> SPINEFISH = simple("spinefish", Spinefish::new, MobCategory.WATER_AMBIENT,
			builder -> builder.setTrackingRange(128).sized(0.7f, 0.7f));
	RegistrySupplier<EntityType<Pycnogonida>> PYCNOGONIDA = simple("pycnogonida", Pycnogonida::new, MobCategory.MONSTER,
			builder -> builder.sized(0.6f, 0.75f).setTrackingRange(32));
	RegistrySupplier<EntityType<CursedMoth>> CURSED_MOTH = simple("cursed_moth", CursedMoth::new, MobCategory.AMBIENT,
			builder -> builder.setTrackingRange(64).fireImmune().sized(0.5f, 0.2f));
	RegistrySupplier<EntityType<StormChakram>> STORM_CHAKRAM = simple("storm_chakram", StormChakram::new, MobCategory.MISC,
			builder -> builder.setTrackingRange(64).fireImmune().sized(1.5f, 0.2f));

	private static <T extends Entity> RegistrySupplier<EntityType<T>> simple(String key, EntityType.EntityFactory<T> factory, MobCategory category,
																			 Function<EntityType.Builder<T> , EntityType.Builder<T> > function) {
		return HANDLER.register(key, () -> function.apply(EntityType.Builder.of(factory, category)).build(key));
	}

	static void registerAttributes(EntityAttributeCreationEvent event) {
		event.put(GOLDEN_MOTH.get(), AbstractMoth.createAttributes().build());
		event.put(MAW.get(), Maw.createAttributes().build());
		event.put(ANGLERFISH.get(), Anglerfish.createAttributes().build());
		event.put(MAZE_MOTHER.get(), MazeMother.createAttributes().build());
		event.put(CAPTAIN_CORNELIA.get(), CaptainCornelia.createAttributes().build());
		event.put(PILLAGERS_PATROL.get(), Mob.createMobAttributes().build());
		event.put(TORTURED_SOUL.get(), TorturedSoul.createAttributes().build());
		event.put(EEL.get(), Eel.createAttributes().build());
		event.put(SPINEFISH.get(), Mob.createMobAttributes().build());
		event.put(PYCNOGONIDA.get(), Mob.createMobAttributes().build());
		event.put(CURSED_MOTH.get(), AbstractMoth.createAttributes().build());
	}

	static void registerSpawns(SpawnPlacementRegisterEvent event) {
		event.register(GOLDEN_MOTH.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				AbstractMoth.getSpawnRules(), SpawnPlacementRegisterEvent.Operation.REPLACE);
		event.register(SPINEFISH.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				Spinefish.getSpawnRules(), SpawnPlacementRegisterEvent.Operation.REPLACE);
		event.register(ANGLERFISH.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				Anglerfish.getSpawnRules(), SpawnPlacementRegisterEvent.Operation.REPLACE);
		event.register(MAW.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				Maw.getSpawnRules(), SpawnPlacementRegisterEvent.Operation.REPLACE );
		event.register(TORTURED_SOUL.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				TorturedSoul.getSpawnRules(), SpawnPlacementRegisterEvent.Operation.REPLACE );
		event.register(PILLAGERS_PATROL.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				PillagersPatrol.getSpawnRules(), SpawnPlacementRegisterEvent.Operation.REPLACE);
	}
}
