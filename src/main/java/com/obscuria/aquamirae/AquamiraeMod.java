package com.obscuria.aquamirae;

import com.obscuria.aquamirae.registry.*;
import com.obscuria.aquamirae.world.events.AquamiraeEvents;
import com.obscuria.obscureapi.ObscureAPI;
import com.obscuria.obscureapi.registry.ObscureAPIAttributes;
import com.obscuria.obscureapi.world.classes.ObscureClass;
import com.obscuria.obscureapi.world.classes.TooltipHandler;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.biome.Biomes;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
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
		@Override @Nonnull
		public ItemStack makeIcon() {
			return AquamiraeItems.RUNE_OF_THE_STORM.get().getDefaultInstance();
		}

		@Override public boolean hasSearchBar() {
			return false;
		}
	};

	public AquamiraeMod() {
		final IEventBus MOD_EVENT_BUS = FMLJavaModLoadingContext.get().getModEventBus();
		final IEventBus EVENT_BUS = MinecraftForge.EVENT_BUS;

		AquamiraeConfig.load();
		AquamiraeFeatures.REGISTRY.register(MOD_EVENT_BUS);
		AquamiraeStructures.REGISTRY.register(MOD_EVENT_BUS);
		AquamiraeSounds.REGISTRY.register(MOD_EVENT_BUS);
		AquamiraeBlocks.REGISTRY.register(MOD_EVENT_BUS);
		AquamiraeEntities.REGISTRY.register(MOD_EVENT_BUS);
		AquamiraeParticles.REGISTRY.register(MOD_EVENT_BUS);
		AquamiraeItems.REGISTRY.register(MOD_EVENT_BUS);
		AquamiraeMobEffects.REGISTRY.register(MOD_EVENT_BUS);
		AquamiraePotions.REGISTRY.register(MOD_EVENT_BUS);

		EVENT_BUS.register(this);
		MOD_EVENT_BUS.addListener(this::commonSetup);
		EVENT_BUS.addListener(this::addStructures);
		EVENT_BUS.addListener(AquamiraeEvents::onEntityAttacked);
		EVENT_BUS.addListener(AquamiraeEvents::onEntityHurt);
		EVENT_BUS.addListener(AquamiraeEvents::onEntityDeath);
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

	private void addStructures(final BiomeLoadingEvent event) {
		if (event.getName() != null && ICE_MAZE.contains(event.getName())) {
			event.getGeneration().getStructures().add(() -> AquamiraeConfiguredStructures.CONFIGURED_ICE_MAZE);
			event.getGeneration().getStructures().add(() -> AquamiraeConfiguredStructures.CONFIGURED_SHIP);
			event.getGeneration().getStructures().add(() -> AquamiraeConfiguredStructures.CONFIGURED_OUTPOST);
		}
	}

	public static void loadFromConfig(LivingEntity entity, Attribute attribute, double amount) {
		final ModifiableAttributeInstance attributeInstance = entity.getAttribute(attribute);
		if (attributeInstance != null) attributeInstance.setBaseValue(amount);
		if (attribute == Attributes.MAX_HEALTH) entity.setHealth(entity.getMaxHealth());
	}

	public static boolean winterEvent() {
		return Calendar.getInstance().get(Calendar.MONTH) == Calendar.DECEMBER || Calendar.getInstance().get(Calendar.MONTH) == Calendar.JANUARY;
	}

	public static <T> void addNetworkMessage(Class<T> messageType, BiConsumer<T, PacketBuffer> encoder, Function<PacketBuffer, T> decoder, BiConsumer<T, Supplier<NetworkEvent.Context>> messageConsumer) {
		PACKET_HANDLER.registerMessage(messageID, messageType, encoder, decoder, messageConsumer); messageID++;
	}

	public static class LootBuilder {

		public static List<ItemStack> common() {
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

		public static List<ItemStack> rare() {
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
