package com.obscuria.aquamirae;

import com.obscuria.aquamirae.common.events.features.OxygeliumFeature;
import com.obscuria.aquamirae.common.events.features.WisteriaFeature;
import com.obscuria.aquamirae.registry.*;
import com.obscuria.aquamirae.common.events.AquamiraeEvents;
import com.obscuria.obscureapi.ObscureAPI;
import com.obscuria.obscureapi.registry.ObscureAPIAttributes;
import com.obscuria.obscureapi.world.classes.ObscureClass;
import com.obscuria.obscureapi.world.classes.TooltipHandler;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.MapData;
import net.minecraft.world.storage.MapDecoration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Mod(AquamiraeMod.MODID)
public class AquamiraeMod {
	public static final Logger LOGGER = LogManager.getLogger(AquamiraeMod.class);
	public static final String MODID = "aquamirae";
	private static final String PROTOCOL_VERSION = "1";
	public static final SimpleChannel PACKET_HANDLER = NetworkRegistry.newSimpleChannel(new ResourceLocation(MODID, "main"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);
	private static int messageID = 0;

	public static final ObscureClass SEA_WOLF = ObscureAPI.Classes.register(new ObscureClass(MODID, "sea_wolf"));
	public static final List<ResourceLocation> ICE_MAZE = Arrays.asList(Biomes.DEEP_FROZEN_OCEAN.location(), Biomes.FROZEN_OCEAN.location());
	public static final Tags.IOptionalNamedTag<Block> EEL_MOVE = BlockTags.createOptional(new ResourceLocation(MODID, "eel_move"));
	public static final Tags.IOptionalNamedTag<Block> MAZE_MOTHER_DESTROY = BlockTags.createOptional(new ResourceLocation(MODID, "maze_mother_destroy"));
	public static final Tags.IOptionalNamedTag<Block> SCROLL_DESTROY = BlockTags.createOptional(new ResourceLocation(MODID, "scroll_destroy"));
	public static final ItemGroup TAB = new ItemGroup(MODID) {
		@Override
		public @NotNull ItemStack makeIcon() {
			return AquamiraeItems.RUNE_OF_THE_STORM.get().getDefaultInstance();
		}

		@Override public boolean hasSearchBar() {
			return false;
		}
	};

	public AquamiraeMod() {
		final IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

		AquamiraeConfig.load();
		AquamiraeFeatures.REGISTRY.register(eventBus);
		AquamiraeStructures.REGISTRY.register(eventBus);
		AquamiraeSounds.REGISTRY.register(eventBus);
		AquamiraeBlocks.REGISTRY.register(eventBus);
		AquamiraeEntities.REGISTRY.register(eventBus);
		AquamiraeParticles.REGISTRY.register(eventBus);
		AquamiraeItems.REGISTRY.register(eventBus);
		AquamiraeMobEffects.REGISTRY.register(eventBus);
		AquamiraePotions.REGISTRY.register(eventBus);

		eventBus.addListener(this::commonSetup);

		MinecraftForge.EVENT_BUS.addListener(this::modifyBiomes);
		MinecraftForge.EVENT_BUS.addListener(AquamiraeEvents::onEntityAttacked);
		MinecraftForge.EVENT_BUS.addListener(AquamiraeEvents::onEntityHurt);
		MinecraftForge.EVENT_BUS.addListener(AquamiraeEvents::onEntityDeath);
	}

	private void commonSetup(final FMLCommonSetupEvent event) {
		AquamiraeStructures.register();
		AquamiraeConfiguredStructures.register();
		ObscureAPI.collectionMod(AquamiraeMod.MODID, "ob-aquamirae");

		TooltipHandler.Lore.add("aquamirae:sea_casserole");
		TooltipHandler.Lore.add("aquamirae:sea_stew");
		TooltipHandler.Lore.add("aquamirae:poseidons_breakfast");
		TooltipHandler.Lore.add("aquamirae:ship_graveyard_echo");
		TooltipHandler.Lore.add("aquamirae:pirate_pouch");
		TooltipHandler.Lore.add("aquamirae:treasure_pouch");
		TooltipHandler.Lore.add("aquamirae:luminescent_bubble");
		TooltipHandler.Lore.add("aquamirae:luminescent_lamp");
		TooltipHandler.Lore.add("aquamirae:shell_horn");
		TooltipHandler.Lore.add("aquamirae:dead_sea_scroll");
		TooltipHandler.Lore.add("aquamirae:frozen_key");
		TooltipHandler.Lore.add("aquamirae:wisteria_niveis");
		TooltipHandler.Lore.add("aquamirae:golden_moth_in_a_jar");
		TooltipHandler.Lore.add("aquamirae:rune_of_the_storm");
		TooltipHandler.Lore.add("aquamirae:oxygelium");
	}

	private void modifyBiomes(final @NotNull BiomeLoadingEvent event) {
		if (AquamiraeUtils.isIceMaze(event.getName())) {
			event.getGeneration().getStructures().add(() -> AquamiraeConfiguredStructures.CONFIGURED_ICE_MAZE);
			event.getGeneration().getStructures().add(() -> AquamiraeConfiguredStructures.CONFIGURED_SHIP);
			event.getGeneration().getStructures().add(() -> AquamiraeConfiguredStructures.CONFIGURED_OUTPOST);
			event.getGeneration().getStructures().add(() -> AquamiraeConfiguredStructures.CONFIGURED_SHELTER);

			event.getGeneration().getFeatures(GenerationStage.Decoration.LOCAL_MODIFICATIONS).add(() -> WisteriaFeature.CONFIGURED_FEATURE);
			event.getGeneration().getFeatures(GenerationStage.Decoration.RAW_GENERATION).add(() -> OxygeliumFeature.CONFIGURED_FEATURE);
			event.getGeneration().getFeatures(GenerationStage.Decoration.RAW_GENERATION).add(() -> Features.KELP_COLD);
			event.getGeneration().getFeatures(GenerationStage.Decoration.RAW_GENERATION).add(() -> Features.SEAGRASS_DEEP_COLD);
			event.getGeneration().getFeatures(GenerationStage.Decoration.RAW_GENERATION).add(() -> Features.SEAGRASS_NORMAL);

			event.getSpawns().addSpawn(EntityClassification.WATER_CREATURE, new MobSpawnInfo.Spawners(AquamiraeEntities.ANGLERFISH.get(), 100, 1, 2));
			event.getSpawns().addSpawn(EntityClassification.WATER_AMBIENT, new MobSpawnInfo.Spawners(AquamiraeEntities.SPINEFISH.get(), 500, 3, 9));
			event.getSpawns().addSpawn(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(AquamiraeEntities.TORTURED_SOUL.get(), 20, 1, 4));
			event.getSpawns().addSpawn(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(AquamiraeEntities.PILLAGERS_PATROL.get(), 5, 1, 1));
		}
	}

	public static void loadFromConfig(@NotNull LivingEntity entity, Attribute attribute, double amount) {
		final ModifiableAttributeInstance attributeInstance = entity.getAttribute(attribute);
		if (attributeInstance != null) attributeInstance.setBaseValue(amount);
		if (attribute == Attributes.MAX_HEALTH) entity.setHealth(entity.getMaxHealth());
	}

	public static ItemStack getStructureMap(@NotNull Structure<?> structure, @NotNull ServerWorld server, @NotNull Entity source) {
		BlockPos pos = server.findNearestMapFeature(structure, source.blockPosition(), 100, false);
		if (pos != null) {
			final TextComponent name = structure == AquamiraeStructures.SHIP.get() ? new TranslationTextComponent("filled_map.aquamirae.ship")
					: structure == AquamiraeStructures.OUTPOST.get() ? new TranslationTextComponent("filled_map.aquamirae.outpost")
					: structure == AquamiraeStructures.SHELTER.get() ? new TranslationTextComponent("filled_map.aquamirae.shelter")
					: new TranslationTextComponent("filled_map.buried_treasure");
			final ItemStack map = FilledMapItem.create(server, pos.getX(), pos.getZ(), (byte) 2, true, true);
			FilledMapItem.renderBiomePreviewMap(server, map);
			MapData.addTargetDecoration(map, pos, "+", MapDecoration.Type.RED_X);
			map.setHoverName(name);
			final CompoundNBT display = map.getOrCreateTag().getCompound("display");
			final ListNBT lore = new ListNBT();
			lore.add(StringNBT.valueOf("{\"text\":\"§7x: " + pos.getX() + "\"}"));
			lore.add(StringNBT.valueOf("{\"text\":\"§7z: " + pos.getZ() + "\"}"));
			display.put("Lore", lore);
			map.addTagElement("display", display);
			return map;
		}
		return ItemStack.EMPTY;
	}

	public static boolean winterEvent() {
		return Calendar.getInstance().get(Calendar.MONTH) == Calendar.DECEMBER || Calendar.getInstance().get(Calendar.MONTH) == Calendar.JANUARY;
	}

	public static <T> void addNetworkMessage(Class<T> messageType, BiConsumer<T, PacketBuffer> encoder, Function<PacketBuffer, T> decoder, BiConsumer<T, Supplier<NetworkEvent.Context>> messageConsumer) {
		PACKET_HANDLER.registerMessage(messageID, messageType, encoder, decoder, messageConsumer); messageID++;
	}

	public static class SetBuilder {

		public static @NotNull List<ItemStack> common() {
			final int c1 = getColor(10, 220, 160);
			final int c2 = getColor(10, 190, 220);
			final int c3 = getColor(10, 130, 220);
			List<ItemStack> list = new ArrayList<>();
			add(list, Items.LEATHER_HELMET, EquipmentSlotType.HEAD, 1, 1, c1, "dead_sea_hat", Attributes.ATTACK_DAMAGE);
			add(list, Items.LEATHER_HELMET, EquipmentSlotType.HEAD, 1, 1, c2, "twilight_grotto_hat", ObscureAPIAttributes.PENETRATION.get());
			add(list, Items.LEATHER_HELMET, EquipmentSlotType.HEAD, 1, 1, c3, "sea_tramps_hat", ObscureAPIAttributes.CRITICAL_HIT.get());
			add(list, Items.LEATHER_CHESTPLATE, EquipmentSlotType.CHEST, 1, 3, c1, "dead_sea_doublet", Attributes.ATTACK_DAMAGE);
			add(list, Items.LEATHER_CHESTPLATE, EquipmentSlotType.CHEST, 1, 3, c2, "twilight_grotto_doublet", ObscureAPIAttributes.PENETRATION.get());
			add(list, Items.LEATHER_CHESTPLATE, EquipmentSlotType.CHEST, 1, 3, c3, "sea_tramps_doublet", ObscureAPIAttributes.CRITICAL_HIT.get());
			add(list, Items.LEATHER_LEGGINGS, EquipmentSlotType.LEGS, 1, 2, c1, "dead_sea_pants", Attributes.ATTACK_DAMAGE);
			add(list, Items.LEATHER_LEGGINGS, EquipmentSlotType.LEGS, 1, 2, c2, "twilight_grotto_pants", ObscureAPIAttributes.PENETRATION.get());
			add(list, Items.LEATHER_LEGGINGS, EquipmentSlotType.LEGS, 1, 2, c3, "sea_tramps_pants", ObscureAPIAttributes.CRITICAL_HIT.get());
			add(list, Items.LEATHER_BOOTS, EquipmentSlotType.FEET, 1, 1, c1, "dead_sea_boots", Attributes.ATTACK_DAMAGE);
			add(list, Items.LEATHER_BOOTS, EquipmentSlotType.FEET, 1, 1, c2, "twilight_grotto_boots", ObscureAPIAttributes.PENETRATION.get());
			add(list, Items.LEATHER_BOOTS, EquipmentSlotType.FEET, 1, 1, c3, "sea_tramps_boots", ObscureAPIAttributes.CRITICAL_HIT.get());
			return list;
		}

		public static @NotNull List<ItemStack> rare() {
			List<ItemStack> list = new ArrayList<>();
			add(list, Items.IRON_HELMET, EquipmentSlotType.HEAD, 2, 2, 0, "dead_sea_helmet", Attributes.ATTACK_DAMAGE);
			add(list, Items.IRON_HELMET, EquipmentSlotType.HEAD, 2, 2, 0, "twilight_grotto_helmet", ObscureAPIAttributes.PENETRATION.get());
			add(list, Items.IRON_HELMET, EquipmentSlotType.HEAD, 2, 2, 0, "sea_tramps_helmet", ObscureAPIAttributes.CRITICAL_HIT.get());
			add(list, Items.IRON_CHESTPLATE, EquipmentSlotType.CHEST, 2, 6, 0, "dead_sea_chestplate", Attributes.ATTACK_DAMAGE);
			add(list, Items.IRON_CHESTPLATE, EquipmentSlotType.CHEST, 2, 6, 0, "twilight_grotto_chestplate", ObscureAPIAttributes.PENETRATION.get());
			add(list, Items.IRON_CHESTPLATE, EquipmentSlotType.CHEST, 2, 6, 0, "sea_tramps_chestplate", ObscureAPIAttributes.CRITICAL_HIT.get());
			add(list, Items.IRON_LEGGINGS, EquipmentSlotType.LEGS, 2, 5, 0, "dead_sea_leggings", Attributes.ATTACK_DAMAGE);
			add(list, Items.IRON_LEGGINGS, EquipmentSlotType.LEGS, 2, 5, 0, "twilight_grotto_leggings", ObscureAPIAttributes.PENETRATION.get());
			add(list, Items.IRON_LEGGINGS, EquipmentSlotType.LEGS, 2, 5, 0, "sea_tramps_leggings", ObscureAPIAttributes.CRITICAL_HIT.get());
			add(list, Items.IRON_BOOTS, EquipmentSlotType.FEET, 2, 2, 0, "dead_sea_boots", Attributes.ATTACK_DAMAGE);
			add(list, Items.IRON_BOOTS, EquipmentSlotType.FEET, 2, 2, 0, "twilight_grotto_boots", ObscureAPIAttributes.PENETRATION.get());
			add(list, Items.IRON_BOOTS, EquipmentSlotType.FEET, 2, 2, 0, "sea_tramps_boots", ObscureAPIAttributes.CRITICAL_HIT.get());
			return list;
		}

		public static void add(List<ItemStack> list, Item item, EquipmentSlotType slot, int mod, int armor, int color, String name, Attribute attribute) {
			for (int i = 1; i <= 5; i++) {
				final ItemStack stack = new ItemStack(item);
				stack.addAttributeModifier(Attributes.ARMOR, new AttributeModifier("base_armor", armor, AttributeModifier.Operation.ADDITION), slot);
				stack.addAttributeModifier(attribute, new AttributeModifier("base_bonus", mod * i * 0.01, AttributeModifier.Operation.MULTIPLY_TOTAL), slot);
				stack.getOrCreateTagElement("display").putString("Name", ITextComponent.Serializer.toJson(new TranslationTextComponent("set.aquamirae." + name)));
				if (i == 5) stack.enchant(Enchantments.UNBREAKING, mod);
				if (color > 0) stack.getOrCreateTagElement("display").putInt("color", color);
				list.add(stack);
			}
		}

		public static int getColor(int r, int g, int b) {
			return r * 65536 + g * 256 + b;
		}
	}
}
