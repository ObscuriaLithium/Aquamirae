package com.obscuria.aquamirae;

import com.obscuria.aquamirae.registry.*;
import com.obscuria.aquamirae.world.events.AquamiraeEvents;
import com.obscuria.obscureapi.ObscureAPI;
import com.obscuria.obscureapi.registry.ObscureAPIAttributes;
import com.obscuria.obscureapi.utils.ItemHelper;
import com.obscuria.obscureapi.world.classes.ObscureClass;
import com.obscuria.obscureapi.world.classes.TooltipHandler;
import net.minecraft.core.registries.Registries;
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
	private static final IEventBus MOD_EVENT_BUS = FMLJavaModLoadingContext.get().getModEventBus();
	private static final IEventBus EVENT_BUS = MinecraftForge.EVENT_BUS;
	public static final ObscureClass SEA_WOLF = ObscureAPI.Classes.register(new ObscureClass(AquamiraeMod.MODID, "sea_wolf"));
	public static final TagKey<Biome> ICE_MAZE = TagKey.create(Registries.BIOME, new ResourceLocation(AquamiraeMod.MODID, "ice_maze"));
	public static final TagKey<Block> EEL_MOVE = BlockTags.create(new ResourceLocation(MODID, "eel_move"));
	public static final TagKey<Block> MAZE_MOTHER_DESTROY = BlockTags.create(new ResourceLocation(MODID, "maze_mother_destroy"));
	public static final TagKey<Block> SCROLL_DESTROY = BlockTags.create(new ResourceLocation(MODID, "scroll_destroy"));

	public AquamiraeMod() {
		EVENT_BUS.register(this);

		AquamiraeConfig.load();
		AquamiraeFeatures.REGISTRY.register(MOD_EVENT_BUS);
		AquamiraeSounds.REGISTRY.register(MOD_EVENT_BUS);
		AquamiraeBlocks.REGISTRY.register(MOD_EVENT_BUS);
		AquamiraeEntities.REGISTRY.register(MOD_EVENT_BUS);
		AquamiraeItems.REGISTRY.register(MOD_EVENT_BUS);
		AquamiraeMobEffects.REGISTRY.register(MOD_EVENT_BUS);
		AquamiraePotions.REGISTRY.register(MOD_EVENT_BUS);
		AquamiraeParticleTypes.REGISTRY.register(MOD_EVENT_BUS);

		MOD_EVENT_BUS.addListener(this::commonSetup);
		MOD_EVENT_BUS.addListener(AquamiraeCreativeTab::registerTab);
		EVENT_BUS.addListener(AquamiraeEvents::onPlayerTick);
		EVENT_BUS.addListener(AquamiraeEvents::onEntityAttacked);
		EVENT_BUS.addListener(AquamiraeEvents::onEntityHurt);
		EVENT_BUS.addListener(AquamiraeEvents::onEntityDeath);
	}

	private void commonSetup(final FMLCommonSetupEvent event) {
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
