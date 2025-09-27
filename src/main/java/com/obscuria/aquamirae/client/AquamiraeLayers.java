
package com.obscuria.aquamirae.client;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.client.models.*;
import com.obscuria.aquamirae.client.models.armor.ModelAbyssalArmor;
import com.obscuria.aquamirae.client.models.armor.ModelTerribleArmor;
import com.obscuria.aquamirae.client.models.armor.ModelThreeBoltArmor;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})
public class AquamiraeLayers {

	public static final ModelLayerLocation GOLDEN_MOTH = register("golden_moth");
	public static final ModelLayerLocation TORTURED_SOUL = register("tortured_soul");
	public static final ModelLayerLocation EEL = register("eel");
	public static final ModelLayerLocation MAZE_MOTHER = register("maze_mother");
	public static final ModelLayerLocation CAPTAIN_CORNELIA = register("captain_cornelia");
	public static final ModelLayerLocation ANGLERFISH = register("anglerfish");
	public static final ModelLayerLocation MAW = register("maw");
	public static final ModelLayerLocation SPINEFISH = register("spinefish");
	public static final ModelLayerLocation LUMINOUS_JELLY = register("luminous_jelly");

	public static final ModelLayerLocation MAZE_ROSE = register("maze_rose");
	public static final ModelLayerLocation POISONED_CHAKRA = register("poisoned_chakra");

	public static final ModelLayerLocation TERRIBLE_ARMOR = register("terrible_armor");
	public static final ModelLayerLocation ABYSSAL_ARMOR = register("abyssal_armor");
	public static final ModelLayerLocation THREE_BOLT_ARMOR = register("three_bolt_armor");

	@SubscribeEvent
	public static void registerLayerDefinitions(EntityRenderersEvent.@NotNull RegisterLayerDefinitions event) {
		event.registerLayerDefinition(GOLDEN_MOTH, ModelGoldenMoth::createBodyLayer);
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
		event.registerLayerDefinition(LUMINOUS_JELLY, ModelLuminousJelly::createBodyLayer);
	}

	@Contract("_ -> new")
	private static @NotNull ModelLayerLocation register(String name) {
		return new ModelLayerLocation(new ResourceLocation(Aquamirae.MODID, name), "main");
	}
}
