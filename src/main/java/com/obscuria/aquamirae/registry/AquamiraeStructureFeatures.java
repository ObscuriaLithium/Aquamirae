
package com.obscuria.aquamirae.registry;

import com.obscuria.aquamirae.AquamiraeMod;
import com.obscuria.aquamirae.world.features.*;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AquamiraeStructureFeatures {
	public static final DeferredRegister<StructureFeature<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, AquamiraeMod.MODID);
	public static final RegistryObject<StructureFeature<?>> ICE_MAZE_ARCH = REGISTRY.register("ice_maze_arch", IceMazeArchFeature::new);
	public static final RegistryObject<StructureFeature<?>> ICE_MAZE_COMMON = REGISTRY.register("ice_maze_common", IceMazeCommonFeature::new);
	public static final RegistryObject<StructureFeature<?>> ICE_MAZE_SPIRAL = REGISTRY.register("ice_maze_spiral", IceMazeSpiralFeature::new);
	public static final RegistryObject<StructureFeature<?>> SHIP = REGISTRY.register("ship", ShipFeature::new);
	public static final RegistryObject<StructureFeature<?>> OUTPOST = REGISTRY.register("outpost", OutpostFeature::new);
	public static final RegistryObject<StructureFeature<?>> SHELTER = REGISTRY.register("shelter", ShelterFeature::new);
}
