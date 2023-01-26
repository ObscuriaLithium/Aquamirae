package com.obscuria.aquamirae;

import com.obscuria.aquamirae.registry.*;
import com.obscuria.aquamirae.world.events.AquamiraeEvents;
import com.obscuria.obscureapi.ObscureAPI;
import com.obscuria.obscureapi.api.classes.ObscureClass;
import com.obscuria.obscureapi.registry.ObscureAPIAttributes;
import com.obscuria.obscureapi.utils.ItemUtils;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
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
	public static final ObscureClass SEA_WOLF = ObscureAPI.Classes.register(new ObscureClass(AquamiraeMod.MODID, "sea_wolf"));
	public static final TagKey<Biome> ICE_MAZE = TagKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(AquamiraeMod.MODID, "ice_maze"));
	public static final TagKey<Block> EEL_MOVE = BlockTags.create(new ResourceLocation(MODID, "eel_move"));
	public static final TagKey<Block> MAZE_MOTHER_DESTROY = BlockTags.create(new ResourceLocation(MODID, "maze_mother_destroy"));
	public static final TagKey<Block> SCROLL_DESTROY = BlockTags.create(new ResourceLocation(MODID, "scroll_destroy"));
	public static final CreativeModeTab TAB = new CreativeModeTab("aquamirae") {
		@Override public @NotNull ItemStack makeIcon() {
			return AquamiraeItems.RUNE_OF_THE_STORM.get().getDefaultInstance();
		}
	};

	public AquamiraeMod() {
		final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

		AquamiraeConfig.load();
		AquamiraeFeatures.REGISTRY.register(bus);
		AquamiraeSounds.REGISTRY.register(bus);
		AquamiraeBlocks.REGISTRY.register(bus);
		AquamiraeEntities.REGISTRY.register(bus);
		AquamiraeItems.REGISTRY.register(bus);
		AquamiraeMobEffects.REGISTRY.register(bus);
		AquamiraePotions.REGISTRY.register(bus);
		AquamiraeParticleTypes.REGISTRY.register(bus);

		MinecraftForge.EVENT_BUS.register(this);
		bus.addListener(this::commonSetup);
		MinecraftForge.EVENT_BUS.addListener(AquamiraeEvents::onPlayerTick);
		MinecraftForge.EVENT_BUS.addListener(AquamiraeEvents::onEntityAttacked);
		MinecraftForge.EVENT_BUS.addListener(AquamiraeEvents::onEntityHurt);
		MinecraftForge.EVENT_BUS.addListener(AquamiraeEvents::onEntityDeath);
	}

	private void commonSetup(final FMLCommonSetupEvent event) {
		ItemUtils.addLore("aquamirae:sea_casserole");
		ItemUtils.addLore("aquamirae:sea_stew");
		ItemUtils.addLore("aquamirae:poseidons_breakfast");
		ItemUtils.addLore("aquamirae:ship_graveyard_echo");
		ItemUtils.addLore("aquamirae:pirate_pouch");
		ItemUtils.addLore("aquamirae:treasure_pouch");
		ItemUtils.addLore("aquamirae:luminescent_bubble");
		ItemUtils.addLore("aquamirae:luminescent_lamp");
		ItemUtils.addLore("aquamirae:shell_horn");
		ItemUtils.addLore("aquamirae:dead_sea_scroll");
		ItemUtils.addLore("aquamirae:frozen_key");
		ItemUtils.addLore("aquamirae:wisteria_niveis");
		ItemUtils.addLore("aquamirae:golden_moth_in_a_jar");
		ItemUtils.addLore("aquamirae:rune_of_the_storm");
		ItemUtils.addLore("aquamirae:oxygelium");
	}

	public static void loadFromConfig(LivingEntity entity, Attribute attribute, double amount) {
		final AttributeInstance attributeInstance = entity.getAttribute(attribute);
		if (attributeInstance != null) attributeInstance.setBaseValue(amount);
		if (attribute == Attributes.MAX_HEALTH) entity.setHealth(entity.getMaxHealth());
	}

	public static boolean winterEvent() {
		return Calendar.getInstance().get(Calendar.MONTH) == Calendar.DECEMBER || Calendar.getInstance().get(Calendar.MONTH) == Calendar.JANUARY;
	}

	public static <T> void addNetworkMessage(Class<T> messageType, BiConsumer<T, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, T> decoder, BiConsumer<T, Supplier<NetworkEvent.Context>> messageConsumer) {
		PACKET_HANDLER.registerMessage(messageID, messageType, encoder, decoder, messageConsumer); messageID++;
	}

	public static class LootBuilder {

		public static List<ItemStack> common() {
			final int c1 = getColor(10, 220, 160);
			final int c2 = getColor(10, 190, 220);
			final int c3 = getColor(10, 130, 220);
			List<ItemStack> list = new ArrayList<>();
			add(list, Items.LEATHER_HELMET, EquipmentSlot.HEAD, 1, 1, c1, "dead_sea_hat", Attributes.ATTACK_DAMAGE);
			add(list, Items.LEATHER_HELMET, EquipmentSlot.HEAD, 1, 1, c2, "twilight_grotto_hat", ObscureAPIAttributes.PENETRATION.get());
			add(list, Items.LEATHER_HELMET, EquipmentSlot.HEAD, 1, 1, c3, "sea_tramps_hat", ObscureAPIAttributes.CRITICAL_HIT.get());
			add(list, Items.LEATHER_CHESTPLATE, EquipmentSlot.CHEST, 1, 3, c1, "dead_sea_doublet", Attributes.ATTACK_DAMAGE);
			add(list, Items.LEATHER_CHESTPLATE, EquipmentSlot.CHEST, 1, 3, c2, "twilight_grotto_doublet", ObscureAPIAttributes.PENETRATION.get());
			add(list, Items.LEATHER_CHESTPLATE, EquipmentSlot.CHEST, 1, 3, c3, "sea_tramps_doublet", ObscureAPIAttributes.CRITICAL_HIT.get());
			add(list, Items.LEATHER_LEGGINGS, EquipmentSlot.LEGS, 1, 2, c1, "dead_sea_pants", Attributes.ATTACK_DAMAGE);
			add(list, Items.LEATHER_LEGGINGS, EquipmentSlot.LEGS, 1, 2, c2, "twilight_grotto_pants", ObscureAPIAttributes.PENETRATION.get());
			add(list, Items.LEATHER_LEGGINGS, EquipmentSlot.LEGS, 1, 2, c3, "sea_tramps_pants", ObscureAPIAttributes.CRITICAL_HIT.get());
			add(list, Items.LEATHER_BOOTS, EquipmentSlot.FEET, 1, 1, c1, "dead_sea_boots", Attributes.ATTACK_DAMAGE);
			add(list, Items.LEATHER_BOOTS, EquipmentSlot.FEET, 1, 1, c2, "twilight_grotto_boots", ObscureAPIAttributes.PENETRATION.get());
			add(list, Items.LEATHER_BOOTS, EquipmentSlot.FEET, 1, 1, c3, "sea_tramps_boots", ObscureAPIAttributes.CRITICAL_HIT.get());
			return list;
		}

		public static List<ItemStack> rare() {
			List<ItemStack> list = new ArrayList<>();
			add(list, Items.IRON_HELMET, EquipmentSlot.HEAD, 2, 2, 0, "dead_sea_helmet", Attributes.ATTACK_DAMAGE);
			add(list, Items.IRON_HELMET, EquipmentSlot.HEAD, 2, 2, 0, "twilight_grotto_helmet", ObscureAPIAttributes.PENETRATION.get());
			add(list, Items.IRON_HELMET, EquipmentSlot.HEAD, 2, 2, 0, "sea_tramps_helmet", ObscureAPIAttributes.CRITICAL_HIT.get());
			add(list, Items.IRON_CHESTPLATE, EquipmentSlot.CHEST, 2, 6, 0, "dead_sea_chestplate", Attributes.ATTACK_DAMAGE);
			add(list, Items.IRON_CHESTPLATE, EquipmentSlot.CHEST, 2, 6, 0, "twilight_grotto_chestplate", ObscureAPIAttributes.PENETRATION.get());
			add(list, Items.IRON_CHESTPLATE, EquipmentSlot.CHEST, 2, 6, 0, "sea_tramps_chestplate", ObscureAPIAttributes.CRITICAL_HIT.get());
			add(list, Items.IRON_LEGGINGS, EquipmentSlot.LEGS, 2, 5, 0, "dead_sea_leggings", Attributes.ATTACK_DAMAGE);
			add(list, Items.IRON_LEGGINGS, EquipmentSlot.LEGS, 2, 5, 0, "twilight_grotto_leggings", ObscureAPIAttributes.PENETRATION.get());
			add(list, Items.IRON_LEGGINGS, EquipmentSlot.LEGS, 2, 5, 0, "sea_tramps_leggings", ObscureAPIAttributes.CRITICAL_HIT.get());
			add(list, Items.IRON_BOOTS, EquipmentSlot.FEET, 2, 2, 0, "dead_sea_boots", Attributes.ATTACK_DAMAGE);
			add(list, Items.IRON_BOOTS, EquipmentSlot.FEET, 2, 2, 0, "twilight_grotto_boots", ObscureAPIAttributes.PENETRATION.get());
			add(list, Items.IRON_BOOTS, EquipmentSlot.FEET, 2, 2, 0, "sea_tramps_boots", ObscureAPIAttributes.CRITICAL_HIT.get());
			return list;
		}

		public static void add(List<ItemStack> list, Item item, EquipmentSlot slot, int mod, int armor, int color, String name, Attribute attribute) {
			for (int i = 1; i <= 5; i++) {
				final ItemStack stack = new ItemStack(item);
				stack.addAttributeModifier(Attributes.ARMOR, new AttributeModifier("base_armor", armor, AttributeModifier.Operation.ADDITION), slot);
				stack.addAttributeModifier(attribute, new AttributeModifier("base_bonus", mod * i * 0.01, AttributeModifier.Operation.MULTIPLY_TOTAL), slot);
				stack.getOrCreateTagElement("display").putString("Name", Component.Serializer.toJson(Component.translatable("set.aquamirae." + name)));
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
