
package com.obscuria.aquamirae.client;

import com.obscuria.aquamirae.client.models.*;
import com.obscuria.aquamirae.client.models.armor.ModelAbyssalArmor;
import com.obscuria.aquamirae.client.models.armor.ModelTerribleArmor;
import com.obscuria.aquamirae.client.models.armor.ModelThreeBoltArmor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})
public class AquamiraeModels {

	@SubscribeEvent
	public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(ModelGoldenMoth.LAYER_LOCATION, ModelGoldenMoth::createBodyLayer);
		event.registerLayerDefinition(ModelTorturedSoul.LAYER_LOCATION, ModelTorturedSoul::createBodyLayer);
		event.registerLayerDefinition(ModelEel.LAYER_LOCATION, ModelEel::createBodyLayer);
		event.registerLayerDefinition(ModelTerribleArmor.LAYER_LOCATION, ModelTerribleArmor::createBodyLayer);
		event.registerLayerDefinition(ModelMazeMother.LAYER_LOCATION, ModelMazeMother::createBodyLayer);
		event.registerLayerDefinition(ModelMazeRose.LAYER_LOCATION, ModelMazeRose::createBodyLayer);
		event.registerLayerDefinition(ModelPoisonedChakra.LAYER_LOCATION, ModelPoisonedChakra::createBodyLayer);
		event.registerLayerDefinition(ModelCaptainCornelia.LAYER_LOCATION, ModelCaptainCornelia::createBodyLayer);
		event.registerLayerDefinition(ModelAbyssalArmor.LAYER_LOCATION, ModelAbyssalArmor::createBodyLayer);
		event.registerLayerDefinition(ModelAnglerfish.LAYER_LOCATION, ModelAnglerfish::createBodyLayer);
		event.registerLayerDefinition(ModelMaw.LAYER_LOCATION, ModelMaw::createBodyLayer);
		event.registerLayerDefinition(ModelCaptainCornelia.LAYER_LOCATION, ModelCaptainCornelia::createBodyLayer);
		event.registerLayerDefinition(ModelThreeBoltArmor.LAYER_LOCATION, ModelThreeBoltArmor::createBodyLayer);
		event.registerLayerDefinition(ModelSpinefish.LAYER_LOCATION, ModelSpinefish::createBodyLayer);
		event.registerLayerDefinition(ModelLuminousJelly.LAYER_LOCATION, ModelLuminousJelly::createBodyLayer);
	}
}
