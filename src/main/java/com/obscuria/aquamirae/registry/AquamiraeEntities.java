
package com.obscuria.aquamirae.registry;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.common.entities.*;
import com.obscuria.aquamirae.common.entities.projectiles.MazeRose;
import com.obscuria.aquamirae.common.entities.projectiles.PoisonedChakra;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class AquamiraeEntities {

    public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Aquamirae.MODID);

    public static final RegistryObject<EntityType<GoldenMoth>> GOLDEN_MOTH = register("golden_moth",
            EntityType.Builder.of(GoldenMoth::new, MobCategory.AMBIENT)
                    .setShouldReceiveVelocityUpdates(true)
                    .sized(0.5f, 0.2f)
                    .setTrackingRange(128)
                    .setUpdateInterval(3));
    public static final RegistryObject<EntityType<Maw>> MAW = register("maw",
            EntityType.Builder.of(Maw::new, MobCategory.MONSTER)
                    .setShouldReceiveVelocityUpdates(true)
                    .sized(1.2f, 1.2f)
                    .setTrackingRange(64)
                    .setUpdateInterval(3));
    public static final RegistryObject<EntityType<Anglerfish>> ANGLERFISH = register("anglerfish",
            EntityType.Builder.of(Anglerfish::new, MobCategory.WATER_CREATURE)
                    .setShouldReceiveVelocityUpdates(true)
                    .sized(2f, 2.6f)
                    .setTrackingRange(64)
                    .setUpdateInterval(3));
    public static final RegistryObject<EntityType<MazeMother>> MAZE_MOTHER = register("maze_mother",
            EntityType.Builder.of(MazeMother::new, MobCategory.MONSTER)
                    .setShouldReceiveVelocityUpdates(true)
                    .sized(8f, 3f)
                    .setTrackingRange(128)
                    .setUpdateInterval(3));
    public static final RegistryObject<EntityType<CaptainCornelia>> CAPTAIN_CORNELIA = register("captain_cornelia",
            EntityType.Builder.of(CaptainCornelia::new, MobCategory.MONSTER)
                    .setShouldReceiveVelocityUpdates(true)
                    .sized(0.6f, 2.3f)
                    .setTrackingRange(64)
                    .setUpdateInterval(3)
                    .fireImmune());
    public static final RegistryObject<EntityType<PillagersPatrol>> PILLAGERS_PATROL = register("pillagers_patrol",
            EntityType.Builder.of(PillagersPatrol::new, MobCategory.MONSTER)
                    .setShouldReceiveVelocityUpdates(false)
                    .sized(1f, 1f)
                    .setTrackingRange(8)
                    .setUpdateInterval(3)
                    .fireImmune());
    public static final RegistryObject<EntityType<TorturedSoul>> TORTURED_SOUL = register("tortured_soul",
            EntityType.Builder.of(TorturedSoul::new, MobCategory.MONSTER)
                    .setShouldReceiveVelocityUpdates(true)
                    .sized(0.6f, 1.8f)
                    .setTrackingRange(64)
                    .setUpdateInterval(3));
    public static final RegistryObject<EntityType<Eel>> EEL = register("eel",
            EntityType.Builder.of(Eel::new, MobCategory.MONSTER)
                    .setShouldReceiveVelocityUpdates(true)
                    .sized(2.4f, 3.4f)
                    .setTrackingRange(64)
                    .setUpdateInterval(3));
    public static final RegistryObject<EntityType<MazeRose>> MAZE_ROSE = register("maze_rose",
            EntityType.Builder.of(MazeRose::new, MobCategory.MISC)
                    .setShouldReceiveVelocityUpdates(true)
                    .sized(1.3f, 0.15f)
                    .setTrackingRange(128)
                    .setUpdateInterval(3));
    public static final RegistryObject<EntityType<PoisonedChakra>> POISONED_CHAKRA = register("poisoned_chakra",
            EntityType.Builder.of(PoisonedChakra::new, MobCategory.MISC)
                    .setShouldReceiveVelocityUpdates(true)
                    .sized(1.3f, 0.15f)
                    .setTrackingRange(128)
                    .setUpdateInterval(3));
    public static final RegistryObject<EntityType<Spinefish>> SPINEFISH = register("spinefish",
            EntityType.Builder.of(Spinefish::new, MobCategory.WATER_AMBIENT)
                    .setShouldReceiveVelocityUpdates(true)
                    .sized(0.7f, 0.7f)
                    .setTrackingRange(128)
                    .setUpdateInterval(3));
    public static final RegistryObject<EntityType<AbyssalScyphoid>> ABYSSAL_SCYPHOID = register("abyssal_scyphoid",
            EntityType.Builder.of(AbyssalScyphoid::new, MobCategory.WATER_AMBIENT)
                    .setShouldReceiveVelocityUpdates(true)
                    .sized(0.6f, 1.2f)
                    .setTrackingRange(128)
                    .setUpdateInterval(3));

    private static <T extends Entity> RegistryObject<EntityType<T>> register(String name, EntityType.Builder<T> builder) {
        return REGISTRY.register(name, () -> builder.build(name));
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(GOLDEN_MOTH.get(), GoldenMoth.createAttributes().build());
        event.put(MAW.get(), Maw.createAttributes().build());
        event.put(ANGLERFISH.get(), Anglerfish.createAttributes().build());
        event.put(MAZE_MOTHER.get(), MazeMother.createAttributes().build());
        event.put(CAPTAIN_CORNELIA.get(), CaptainCornelia.createAttributes().build());
        event.put(PILLAGERS_PATROL.get(), Mob.createMobAttributes().build());
        event.put(TORTURED_SOUL.get(), TorturedSoul.createAttributes().build());
        event.put(EEL.get(), Eel.createAttributes().build());
        event.put(SPINEFISH.get(), Mob.createMobAttributes().build());
        event.put(ABYSSAL_SCYPHOID.get(), Mob.createMobAttributes().build());
        event.put(POISONED_CHAKRA.get(), LivingEntity.createLivingAttributes().build());
        event.put(MAZE_ROSE.get(), LivingEntity.createLivingAttributes().build());
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void registerSpawns(SpawnPlacementRegisterEvent event) {
        event.register(GOLDEN_MOTH.get(),
                SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                GoldenMoth::checkSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(SPINEFISH.get(),
                SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Spinefish::checkSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ABYSSAL_SCYPHOID.get(),
                SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                AbyssalScyphoid::checkSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ANGLERFISH.get(),
                SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Anglerfish::checkSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(MAW.get(),
                SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Maw::checkSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(TORTURED_SOUL.get(),
                SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                TorturedSoul::checkSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(PILLAGERS_PATROL.get(),
                SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                PillagersPatrol::checkSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
    }
}
