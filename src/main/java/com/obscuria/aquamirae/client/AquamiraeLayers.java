
package com.obscuria.aquamirae.client;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.client.model.*;
import com.obscuria.aquamirae.client.model.armor.ModelAbyssalArmor;
import com.obscuria.aquamirae.client.model.armor.ModelTerribleArmor;
import com.obscuria.aquamirae.client.model.armor.ModelThreeBoltArmor;
import com.obscuria.aquamirae.client.model.projectile.ModelShard;
import net.minecraft.client.model.VillagerModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;

@OnlyIn(Dist.CLIENT)
@ApiStatus.NonExtendable
public interface AquamiraeLayers {
	ModelLayerLocation MOTH = mainLayer("moth");
	ModelLayerLocation TORTURED_SOUL = mainLayer("tortured_soul");
	ModelLayerLocation EEL = mainLayer("eel");
	ModelLayerLocation MAZE_MOTHER = mainLayer("maze_mother");
	ModelLayerLocation CAPTAIN_CORNELIA = mainLayer("captain_cornelia");
	ModelLayerLocation ANGLERFISH = mainLayer("anglerfish");
	ModelLayerLocation MAW = mainLayer("maw");
	ModelLayerLocation SPINEFISH = mainLayer("spinefish");
	ModelLayerLocation PYCNOGONIDA = mainLayer("pycnogonida");
	ModelLayerLocation RUNE_OF_THE_STORM = mainLayer("rune_of_the_storm");
	ModelLayerLocation MAZE_ROSE = mainLayer("maze_rose");
	ModelLayerLocation POISONED_CHAKRA = mainLayer("poisoned_chakra");
	ModelLayerLocation SHADOW_MERCHANT = mainLayer("shadow_merchant");
	ModelLayerLocation ICE_SHARD = mainLayer("ice_shard");
	ModelLayerLocation ABYSSAL_SHARD = mainLayer("abyssal_shard");
	ModelLayerLocation TERRIBLE_ARMOR = mainLayer("terrible_armor");
	ModelLayerLocation ABYSSAL_ARMOR = mainLayer("abyssal_armor");
	ModelLayerLocation THREE_BOLT_ARMOR = mainLayer("three_bolt_armor");

	static void register(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(MOTH, ModelMoth::createBodyLayer);
		event.registerLayerDefinition(TORTURED_SOUL, ModelTorturedSoul::createBodyLayer);
		event.registerLayerDefinition(EEL, ModelEel::createBodyLayer);
		event.registerLayerDefinition(TERRIBLE_ARMOR, ModelTerribleArmor::createBodyLayer);
		event.registerLayerDefinition(MAZE_MOTHER, ModelMazeMother::createBodyLayer);
		event.registerLayerDefinition(MAZE_ROSE, ModelMazeRose::createBodyLayer);
		event.registerLayerDefinition(POISONED_CHAKRA, ModelPoisonedChakra::createBodyLayer);
		event.registerLayerDefinition(CAPTAIN_CORNELIA, ModelCaptainCornelia::createBodyLayer);
		event.registerLayerDefinition(ABYSSAL_ARMOR, ModelAbyssalArmor::createBodyLayer);
		event.registerLayerDefinition(ANGLERFISH, ModelAnglerfish::createBodyLayer);
		event.registerLayerDefinition(MAW, ModelMaw::createBodyLayer);
		event.registerLayerDefinition(THREE_BOLT_ARMOR, ModelThreeBoltArmor::createBodyLayer);
		event.registerLayerDefinition(SPINEFISH, ModelSpinefish::createBodyLayer);
		event.registerLayerDefinition(PYCNOGONIDA, ModelPycnogonida::createBodyLayer);
		event.registerLayerDefinition(ICE_SHARD, ModelShard::createBodyLayer);
		event.registerLayerDefinition(ABYSSAL_SHARD, ModelShard::createBodyLayer);
		event.registerLayerDefinition(RUNE_OF_THE_STORM, ModelStormChakram::createBodyLayer);
		event.registerLayerDefinition(SHADOW_MERCHANT, () ->
				LayerDefinition.create(VillagerModel.createBodyModel(), 64, 64));
	}

	@Contract("_ -> new")
	private static ModelLayerLocation mainLayer(String key) {
		return new ModelLayerLocation(Aquamirae.key(key), "main");
	}
}
