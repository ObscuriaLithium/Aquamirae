
package com.obscuria.aquamirae.registry;

import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.world.features.*;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class AquamiraeStructureFeatures {
	public static final DeferredRegister<Structure<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, AquamiraeMod.MODID);
	public static final RegistryObject<Structure<NoFeatureConfig>> ICE_MAZE_ARCH = REGISTRY.register("ice_maze_arch", IceMazeArchFeature::new);
	public static final RegistryObject<Structure<NoFeatureConfig>> ICE_MAZE_COMMON = REGISTRY.register("ice_maze_common", IceMazeCommonFeature::new);
	public static final RegistryObject<Structure<NoFeatureConfig>> ICE_MAZE_SPIRAL = REGISTRY.register("ice_maze_spiral", IceMazeSpiralFeature::new);
	public static final RegistryObject<Structure<NoFeatureConfig>> SHIP = REGISTRY.register("ship", ShipFeature::new);
	public static final RegistryObject<Structure<NoFeatureConfig>> OUTPOST = REGISTRY.register("outpost", OutpostFeature::new);
	public static final RegistryObject<Structure<NoFeatureConfig>> SHELTER = REGISTRY.register("shelter", ShelterFeature::new);
}
