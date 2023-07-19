
package com.obscuria.aquamirae.client;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.client.models.*;
import com.obscuria.aquamirae.client.models.armor.ModelAbyssalArmor;
import com.obscuria.aquamirae.client.models.armor.ModelTerribleArmor;
import com.obscuria.aquamirae.client.models.armor.ModelThreeBoltArmor;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Contract;

public final class AquamiraeLayers {
	public static final EntityModelLayer GOLDEN_MOTH = create("golden_moth");
	public static final EntityModelLayer TORTURED_SOUL = create("tortured_soul");
	public static final EntityModelLayer EEL = create("eel");
	public static final EntityModelLayer MAZE_MOTHER = create("maze_mother");
	public static final EntityModelLayer CAPTAIN_CORNELIA = create("captain_cornelia");
	public static final EntityModelLayer ANGLERFISH = create("anglerfish");
	public static final EntityModelLayer MAW = create("maw");
	public static final EntityModelLayer SPINEFISH = create("spinefish");
	public static final EntityModelLayer MAZE_ROSE = create("maze_rose");
	public static final EntityModelLayer POISONED_CHAKRA = create("poisoned_chakra");
	public static final EntityModelLayer TERRIBLE_ARMOR = create("terrible_armor");
	public static final EntityModelLayer ABYSSAL_ARMOR = create("abyssal_armor");
	public static final EntityModelLayer THREE_BOLT_ARMOR = create("three_bolt_armor");

	public static void register() {
		EntityModelLayerRegistry.registerModelLayer(GOLDEN_MOTH, ModelGoldenMoth::createModelData);
		EntityModelLayerRegistry.registerModelLayer(TORTURED_SOUL, ModelTorturedSoul::createModelData);
		EntityModelLayerRegistry.registerModelLayer(EEL, ModelEel::createModelData);
		EntityModelLayerRegistry.registerModelLayer(TERRIBLE_ARMOR, ModelTerribleArmor::createModelData);
		EntityModelLayerRegistry.registerModelLayer(MAZE_MOTHER, ModelMazeMother::createModelData);
		EntityModelLayerRegistry.registerModelLayer(MAZE_ROSE, ModelMazeRose::createModelData);
		EntityModelLayerRegistry.registerModelLayer(POISONED_CHAKRA, ModelPoisonedChakra::createModelData);
		EntityModelLayerRegistry.registerModelLayer(CAPTAIN_CORNELIA, ModelCaptainCornelia::createModelData);
		EntityModelLayerRegistry.registerModelLayer(ABYSSAL_ARMOR, ModelAbyssalArmor::createModelData);
		EntityModelLayerRegistry.registerModelLayer(ANGLERFISH, ModelAnglerfish::createModelData);
		EntityModelLayerRegistry.registerModelLayer(MAW, ModelMaw::createModelData);
		EntityModelLayerRegistry.registerModelLayer(THREE_BOLT_ARMOR, ModelThreeBoltArmor::createModelData);
		EntityModelLayerRegistry.registerModelLayer(SPINEFISH, ModelSpinefish::createModelData);
	}

	@Contract("_ -> new")
	private static EntityModelLayer create(String name) {
		return new EntityModelLayer(new Identifier(Aquamirae.MODID, name), "main");
	}
}
