
package com.obscuria.aquamirae.registry;

import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.common.entities.*;
import com.obscuria.aquamirae.common.entities.chakras.MazeRose;
import com.obscuria.aquamirae.common.entities.chakras.PoisonedChakra;
import net.minecraft.entity.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.fish.AbstractFishEntity;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class AquamiraeEntities {
	public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.ENTITIES, AquamiraeMod.MODID);
	public static final RegistryObject<EntityType<GoldenMoth>> GOLDEN_MOTH = register("golden_moth",
			EntityType.Builder.<GoldenMoth>of(GoldenMoth::new, EntityClassification.AMBIENT).setShouldReceiveVelocityUpdates(true).setTrackingRange(128)
					.setUpdateInterval(3).setCustomClientFactory(GoldenMoth::new).fireImmune().sized(0.5f, 0.2f));
	public static final RegistryObject<EntityType<Maw>> MAW = register("maw",
			EntityType.Builder.<Maw>of(Maw::new, EntityClassification.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)
					.setCustomClientFactory(Maw::new).sized(1.2f, 1.2f));
	public static final RegistryObject<EntityType<Anglerfish>> ANGLERFISH = register("anglerfish",
			EntityType.Builder.<Anglerfish>of(Anglerfish::new, EntityClassification.WATER_CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(64)
					.setUpdateInterval(3).setCustomClientFactory(Anglerfish::new).sized(2f, 2.6f));
	public static final RegistryObject<EntityType<MazeMother>> MAZE_MOTHER = register("maze_mother",
			EntityType.Builder.<MazeMother>of(MazeMother::new, EntityClassification.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(128)
					.setUpdateInterval(3).setCustomClientFactory(MazeMother::new).sized(8f, 3f));
	public static final RegistryObject<EntityType<CaptainCornelia>> CAPTAIN_CORNELIA = register("captain_cornelia",
			EntityType.Builder.<CaptainCornelia>of(CaptainCornelia::new, EntityClassification.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64)
					.setUpdateInterval(3).setCustomClientFactory(CaptainCornelia::new).fireImmune().sized(0.6f, 2.3f));
	public static final RegistryObject<EntityType<PillagersPatrol>> PILLAGERS_PATROL = register("pillagers_patrol",
			EntityType.Builder.<PillagersPatrol>of(PillagersPatrol::new, EntityClassification.MONSTER).setShouldReceiveVelocityUpdates(false).setTrackingRange(8)
					.setUpdateInterval(3).setCustomClientFactory(PillagersPatrol::new).fireImmune().sized(1f, 1f));
	public static final RegistryObject<EntityType<TorturedSoul>> TORTURED_SOUL = register("tortured_soul",
			EntityType.Builder.<TorturedSoul>of(TorturedSoul::new, EntityClassification.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64)
					.setUpdateInterval(3).setCustomClientFactory(TorturedSoul::new).sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<Eel>> EEL = register("eel",
			EntityType.Builder.<Eel>of(Eel::new, EntityClassification.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)
					.setCustomClientFactory(Eel::new).sized(2.4f, 3.4f));
	public static final RegistryObject<EntityType<MazeRose>> MAZE_ROSE = register("maze_rose",
			EntityType.Builder.<MazeRose>of(MazeRose::new, EntityClassification.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(128)
					.setUpdateInterval(3).setCustomClientFactory(MazeRose::new).sized(0.7f, 0.125f));
	public static final RegistryObject<EntityType<PoisonedChakra>> POISONED_CHAKRA = register("poisoned_chakra",
			EntityType.Builder.<PoisonedChakra>of(PoisonedChakra::new, EntityClassification.MISC).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(128).setUpdateInterval(3).setCustomClientFactory(PoisonedChakra::new).sized(0.7f, 0.125f));
	public static final RegistryObject<EntityType<Spinefish>> SPINEFISH = register("spinefish",
			EntityType.Builder.<Spinefish>of(Spinefish::new, EntityClassification.WATER_AMBIENT).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(128).setUpdateInterval(3).setCustomClientFactory(Spinefish::new).sized(0.7f, 0.7f));

	private static <T extends Entity> RegistryObject<EntityType<T>> register(String registryname, EntityType.Builder<T> entityTypeBuilder) {
		return REGISTRY.register(registryname, () -> entityTypeBuilder.build(registryname));
	}

	@SubscribeEvent
	public static void registerAttributes(EntityAttributeCreationEvent event) {
		event.put(GOLDEN_MOTH.get(), GoldenMoth.createAttributes().build());
		event.put(MAW.get(), Maw.createAttributes().build());
		event.put(ANGLERFISH.get(), Anglerfish.createAttributes().build());
		event.put(MAZE_MOTHER.get(), MazeMother.createAttributes().build());
		event.put(CAPTAIN_CORNELIA.get(), CaptainCornelia.createAttributes().build());
		event.put(PILLAGERS_PATROL.get(), MobEntity.createMobAttributes().build());
		event.put(TORTURED_SOUL.get(), TorturedSoul.createAttributes().build());
		event.put(EEL.get(), Eel.createAttributes().build());
		event.put(SPINEFISH.get(), MobEntity.createMobAttributes().build());
		event.put(POISONED_CHAKRA.get(), MobEntity.createMobAttributes().build());
		event.put(MAZE_ROSE.get(), MobEntity.createMobAttributes().build());
	}

	@SubscribeEvent
	public static void registerSpawns(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			EntitySpawnPlacementRegistry.register(AquamiraeEntities.GOLDEN_MOTH.get(), EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, GoldenMoth::checkGoldenMothSpawnRules);
			EntitySpawnPlacementRegistry.register(AquamiraeEntities.SPINEFISH.get(), EntitySpawnPlacementRegistry.PlacementType.IN_WATER, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AbstractFishEntity::checkFishSpawnRules);
			EntitySpawnPlacementRegistry.register(AquamiraeEntities.MAW.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, Maw::checkMawSpawnRules);
			EntitySpawnPlacementRegistry.register(AquamiraeEntities.ANGLERFISH.get(), EntitySpawnPlacementRegistry.PlacementType.IN_WATER, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::checkMonsterSpawnRules);
			EntitySpawnPlacementRegistry.register(AquamiraeEntities.PILLAGERS_PATROL.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, Maw::checkMawSpawnRules);
			EntitySpawnPlacementRegistry.register(AquamiraeEntities.TORTURED_SOUL.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::checkMonsterSpawnRules);
		});
	}
}
