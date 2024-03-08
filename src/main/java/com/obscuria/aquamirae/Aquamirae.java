package com.obscuria.aquamirae;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.obscuria.aquamirae.common.AquamiraeHooks;
import com.obscuria.aquamirae.common.DeadSeaCurse;
import com.obscuria.aquamirae.common.entity.IceMazeEntity;
import com.obscuria.aquamirae.common.item.armor.AbyssalArmor;
import com.obscuria.aquamirae.common.item.armor.ThreeBoltArmor;
import com.obscuria.aquamirae.compat.AquamiraeCompats;
import com.obscuria.aquamirae.compat.curios.CuriosCompat;
import com.obscuria.aquamirae.network.ScrollEffectPacket;
import com.obscuria.aquamirae.network.StackCursedPacket;
import com.obscuria.aquamirae.registry.*;
import com.obscuria.core.api.util.signal.RuntimeSignals;
import com.obscuria.core.api.ObscureAPI;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.saveddata.maps.MapDecoration;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.SimpleChannel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

@Mod(Aquamirae.MODID)
public class Aquamirae {
	public static final String MODID = "aquamirae";
	public static final SimpleChannel CHANNEL = ChannelBuilder.named(key("main")).simpleChannel();
	public static final TagKey<Biome> BIOME_ICE_MAZE = TagKey.create(Registries.BIOME, key("ice_maze"));
	public static final TagKey<Structure> STRUCTURE_SHIP = TagKey.create(Registries.STRUCTURE, key("ship"));
	public static final TagKey<Structure> STRUCTURE_OUTPOST = TagKey.create(Registries.STRUCTURE, key("outpost"));
	public static final TagKey<Structure> STRUCTURE_SHELTER = TagKey.create(Registries.STRUCTURE, key("shelter"));
	public static final TagKey<Block> EEL_CAN_DIG = BlockTags.create(key("eel_can_dig"));
	public static final TagKey<Block> MAZE_MOTHER_CAN_DESTROY = BlockTags.create(key("maze_mother_can_destroy"));
	public static final TagKey<Block> SCROLL_CAN_DESTROY = BlockTags.create(key("scroll_can_destroy"));

	public static ResourceLocation key(String key) {
		return new ResourceLocation(MODID, key);
	}

	public static boolean isInIceMaze(Entity entity) {
		return entity.level().getBiome(entity.blockPosition()).is(BIOME_ICE_MAZE);
	}

	public static boolean isIceMazeEntity(Entity entity) {
		return entity.getClass().isAnnotationPresent(IceMazeEntity.class);
	}

	public static boolean isWinterEvent() {
		final var month = Calendar.getInstance().get(Calendar.MONTH);
		return month == Calendar.DECEMBER || month == Calendar.JANUARY;
	}

	public static void setAttribute(LivingEntity entity, Attribute attribute, double amount) {
		final var instance = entity.getAttribute(attribute);
		if (instance != null) instance.setBaseValue(amount);
		if (attribute == Attributes.MAX_HEALTH) entity.setHealth(entity.getMaxHealth());
	}

	public Aquamirae() {
		final var bus = FMLJavaModLoadingContext.get().getModEventBus();

		AquamiraeCreativeTabs.HANDLER.register(bus);
		AquamiraeFeatures.HANDLER.register(bus);
		AquamiraeSounds.HANDLER.register(bus);
		AquamiraeBlocks.HANDLER.register(bus);
		AquamiraeEntities.HANDLER.register(bus);
		AquamiraeItems.HANDLER.register(bus);
		AquamiraeMobEffects.HANDLER.register(bus);
		AquamiraePotions.HANDLER.register(bus);
		AquamiraeParticles.HANDLER.register(bus);
		AquamiraeAttributes.HANDLER.register(bus);
		AquamiraeRecipeSerializers.HANDLER.register(bus);
		AquamiraeConfig.register();

		bus.addListener(this::commonSetup);
		bus.addListener(this::onAttributeModification);
		bus.addListener(AquamiraeEntities::registerAttributes);
		bus.addListener(AquamiraeEntities::registerSpawns);
		bus.addListener(ScrollEffectPacket::register);
		bus.addListener(StackCursedPacket::register);

		MinecraftForge.EVENT_BUS.addListener(this::onTooltip);
		MinecraftForge.EVENT_BUS.addListener(this::onMobSpawn);
		MinecraftForge.EVENT_BUS.addListener(this::onPlayerTick);
		MinecraftForge.EVENT_BUS.addListener(this::onEntityHurted);
		MinecraftForge.EVENT_BUS.addListener(this::onItemConsumed);
		MinecraftForge.EVENT_BUS.addListener(this::onEntityHurt);
		MinecraftForge.EVENT_BUS.addListener(this::onEntityDeath);
		MinecraftForge.EVENT_BUS.addListener(this::onPlayerBreathe);

		RuntimeSignals.ON_PLAYER_TICK.connect(ThreeBoltArmor.Helmet::onPlayerTick);
		ObscureAPI.KEY_ARMOR_BONUS_PRESSED.connect(ThreeBoltArmor.Helmet::onArmorBonusPressed);
		DeadSeaCurse.registerModifiers();

		AquamiraeCompats.CURIOS_API.safeRun(() -> () -> CuriosCompat.setup(bus));

		if (FMLEnvironment.dist.isClient())
			AquamiraeClient.setup(bus);
	}

	private void commonSetup(FMLCommonSetupEvent event) {
//		ItemUtil.addLore("aquamirae:sea_casserole");
//		ItemUtil.addLore("aquamirae:sea_stew");
//		ItemUtil.addLore("aquamirae:poseidons_breakfast");
//		ItemUtil.addLore("aquamirae:luminescent_lamp");
//		ItemUtil.addLore("aquamirae:frozen_key");
//		ItemUtil.addLore("aquamirae:golden_moth_in_a_jar");
	}

	public static Multimap<Attribute, AttributeModifier> addAttribute(Multimap<Attribute, AttributeModifier> multimap,
																	  Attribute attribute,  UUID uuid, AttributeModifier.Operation operation,
																	  double amount, boolean condition) {
		if (!condition) return multimap;
		return ImmutableMultimap.<Attribute, AttributeModifier>builder().putAll(multimap).put(attribute,
						new AttributeModifier(uuid, "Bonus", amount, operation)).build();
	}

	public static ItemStack getStructureMap(TagKey<Structure> tag, ServerLevel server, Entity source) {
		BlockPos pos = server.findNearestMapStructure(tag, source.blockPosition(), 100, false);
		if (pos != null) {
			final Component name = tag == STRUCTURE_SHIP ? Component.translatable("filled_map.aquamirae.ship")
					: tag == STRUCTURE_OUTPOST ? Component.translatable("filled_map.aquamirae.outpost")
					: tag == STRUCTURE_SHELTER ? Component.translatable("filled_map.aquamirae.shelter")
					: Component.translatable("filled_map.buried_treasure");
			final ItemStack map = MapItem.create(server, pos.getX(), pos.getZ(), (byte) 2, true, true);
			MapItem.renderBiomePreviewMap(server, map);
			MapItemSavedData.addTargetDecoration(map, pos, "+", MapDecoration.Type.RED_X);
			map.setHoverName(name);
			final CompoundTag display = map.getOrCreateTag().getCompound("display");
			final ListTag lore = new ListTag();
			lore.add(StringTag.valueOf("{\"text\":\"§7x: %s\"}".formatted(pos.getX())));
			lore.add(StringTag.valueOf("{\"text\":\"§7z: %s\"}".formatted(pos.getZ())));
			display.put("Lore", lore);
			map.addTagElement("display", display);
			return map;
		}
		return ItemStack.EMPTY;
	}

	private void onMobSpawn(MobSpawnEvent event) {
		AquamiraeHooks.onMobSpawn(event.getEntity());
	}

	private void onPlayerTick(TickEvent.PlayerTickEvent event) {
		if (event.phase != TickEvent.Phase.END || event.player.level().isClientSide || event.player.isCreative() || event.player.isSpectator()) return;
		if (isInIceMaze(event.player))
			if (event.player.isInWaterOrBubble() && event.player.getTicksFrozen() <= event.player.getTicksRequiredToFreeze() * 3)
				if (ThreeBoltArmor.ABILITY.setupContext(event.player.getItemBySlot(EquipmentSlot.HEAD), event.player).getTier() < 2)
					event.player.setTicksFrozen(event.player.getTicksFrozen() + 4);
	}

	private void onEntityHurted(LivingHurtEvent event) {
		event.setAmount(AquamiraeHooks.onLivingHurt(event.getEntity(), event.getAmount(), event.getSource()));
		if (event.getAmount() <= 0) event.setCanceled(true);

//		//Fin Cutter
//		if (event.getSource().getEntity() instanceof LivingEntity source && source.getMainHandItem().getItem() instanceof FinCutter)
//			event.setAmount(event.getAmount() + FinCutter.calculateDamageBonus(source, source.getMainHandItem(), event.getAmount()));
//		//Terrible Armor
//		if (event.getEntity() instanceof Player player) {
//			final int TOTAL = ItemUtil.getArmorPieces(player, TerribleArmor.class);
//			if (TOTAL >= 2) {
//				final ItemStack piece = getArmor(player, TerribleArmor.class);
//				if (player.isInWater() && !player.getCooldowns().isOnCooldown(piece.getItem()) && piece.getItem() instanceof TerribleArmor item) {
//					player.addEffect(new MobEffectInstance(AquamiraeMobEffects.SWIM_SPEED.get(), 20 * item.ABILITY_HALFSET.getVariable(player, 2),
//							Math.min(19, item.ABILITY_HALFSET.getVariable(player, 1) / 10 - 1), false, false));
//					final int cooldown = 20 * item.ABILITY_HALFSET.getCost(player);
//					cooldown(player, TerribleArmor.class, cooldown);
//				}
//				if (TOTAL >= 4 && event.getSource().getEntity() instanceof LivingEntity source && piece.getItem() instanceof TerribleArmor item) {
//					source.addEffect(new MobEffectInstance(MobEffects.POISON, 20 * item.ABILITY_FULLSET.getVariable(player, 1), 1, false, false));
//				}
//			}
//		}
	}

	private void onItemConsumed(LivingEntityUseItemEvent.Finish event) {
		if (event.getItem().isEdible()) AquamiraeHooks.onFoodEaten(event.getEntity(), event.getItem());
	}

	private void onEntityHurt(LivingHurtEvent event) {
//		final LivingEntity source = event.getSource().getEntity() instanceof LivingEntity living ? living : null;
//		if (source == null) return;
//		final ItemStack stack = source.getMainHandItem();
//
//		if (stack.getItem() instanceof RemnantsSaber)
//			event.setAmount(event.getAmount() + RemnantsSaber.calculateDamageBonus(source, stack, event.getAmount()));
//		if (stack.getItem() instanceof CoralLance)
//			event.setAmount(event.getAmount() + CoralLance.calculateDamageBonus(event.getEntity(), source, stack, event.getAmount()));
	}

	private void onEntityDeath(LivingDeathEvent event) {
		//Abyssal Armor
		if (event != null && event.getEntity() != null) {
			final LivingEntity entity = event.getEntity();
			final int TOTAL = 0;//ItemUtils.getArmorPieces(entity, AbyssalArmor.class);
			if (TOTAL >= 4 && !entity.hasEffect(AquamiraeMobEffects.CRYSTALLIZATION.get())) {
				final AbyssalArmor item = (AbyssalArmor) getArmor(entity, AbyssalArmor.class).getItem();
				if (!entity.getPersistentData().getBoolean("crystallization")) {
					event.setCanceled(true);
//					entity.addEffect(new MobEffectInstance(AquamiraeMobEffects.CRYSTALLIZATION.get(),
//							20 * item.ABILITY_FULLSET_1.getVariable(entity, 1), 0, true, true));
					entity.setHealth(entity.getMaxHealth());
					if (entity.level() instanceof ServerLevel level) level.playSound(null, entity.blockPosition().above(), SoundEvents.TOTEM_USE, SoundSource.PLAYERS, 1, 1);
					final ItemStack head = entity.getItemBySlot(EquipmentSlot.HEAD);
					final ItemStack chest = entity.getItemBySlot(EquipmentSlot.CHEST);
					final ItemStack legs = entity.getItemBySlot(EquipmentSlot.LEGS);
					final ItemStack feet = entity.getItemBySlot(EquipmentSlot.FEET);
					if (head.hurt(50, entity.getRandom(), null)) { head.shrink(1); head.setDamageValue(0); }
					if (chest.hurt(50, entity.getRandom(), null)) { chest.shrink(1); chest.setDamageValue(0); }
					if (legs.hurt(50, entity.getRandom(), null)) { legs.shrink(1); legs.setDamageValue(0); }
					if (feet.hurt(50, entity.getRandom(), null)) { feet.shrink(1); feet.setDamageValue(0); }
				}
			}
		}
	}

	private void onPlayerBreathe(LivingBreatheEvent event) {
		if (event.getEntity() instanceof Player player)
			event.setCanBreathe(AquamiraeHooks.onPlayerBreathe(player, event.canBreathe()));
	}

	private void onTooltip(ItemTooltipEvent event) {
		AquamiraeHooks.onTooltip(event.getItemStack(), event.getToolTip());
	}

	private void onAttributeModification(EntityAttributeModificationEvent event) {
		event.add(EntityType.PLAYER, AquamiraeAttributes.DEPTHS_FURY.get(), 0);
	}

	private ItemStack getArmor(LivingEntity entity, Class<?> armor) {
		return armor.isAssignableFrom(entity.getItemBySlot(EquipmentSlot.HEAD).getItem().getClass()) ?
				entity.getItemBySlot(EquipmentSlot.HEAD) :
				armor.isAssignableFrom(entity.getItemBySlot(EquipmentSlot.CHEST).getItem().getClass()) ?
						entity.getItemBySlot(EquipmentSlot.CHEST) :
						armor.isAssignableFrom(entity.getItemBySlot(EquipmentSlot.LEGS).getItem().getClass()) ?
								entity.getItemBySlot(EquipmentSlot.LEGS) :
								armor.isAssignableFrom(entity.getItemBySlot(EquipmentSlot.FEET).getItem().getClass()) ?
										entity.getItemBySlot(EquipmentSlot.FEET) : ItemStack.EMPTY;
	}

	public static class SetBuilder {

		public static List<ItemStack> common() {
			final int c1 = getColor(10, 220, 160);
			final int c2 = getColor(10, 190, 220);
			final int c3 = getColor(10, 130, 220);
			List<ItemStack> list = new ArrayList<>();
			add(list, Items.LEATHER_HELMET, EquipmentSlot.HEAD, 1, 1, c1, "dead_sea_hat", Attributes.ATTACK_DAMAGE);
			add(list, Items.LEATHER_HELMET, EquipmentSlot.HEAD, 1, 1, c2, "twilight_grotto_hat", Attributes.ATTACK_SPEED);
			add(list, Items.LEATHER_HELMET, EquipmentSlot.HEAD, 1, 1, c3, "sea_tramps_hat", Attributes.MOVEMENT_SPEED);
			add(list, Items.LEATHER_CHESTPLATE, EquipmentSlot.CHEST, 1, 3, c1, "dead_sea_doublet", Attributes.ATTACK_DAMAGE);
			add(list, Items.LEATHER_CHESTPLATE, EquipmentSlot.CHEST, 1, 3, c2, "twilight_grotto_doublet", Attributes.ATTACK_SPEED);
			add(list, Items.LEATHER_CHESTPLATE, EquipmentSlot.CHEST, 1, 3, c3, "sea_tramps_doublet", Attributes.MOVEMENT_SPEED);
			add(list, Items.LEATHER_LEGGINGS, EquipmentSlot.LEGS, 1, 2, c1, "dead_sea_pants", Attributes.ATTACK_DAMAGE);
			add(list, Items.LEATHER_LEGGINGS, EquipmentSlot.LEGS, 1, 2, c2, "twilight_grotto_pants", Attributes.ATTACK_SPEED);
			add(list, Items.LEATHER_LEGGINGS, EquipmentSlot.LEGS, 1, 2, c3, "sea_tramps_pants", Attributes.MOVEMENT_SPEED);
			add(list, Items.LEATHER_BOOTS, EquipmentSlot.FEET, 1, 1, c1, "dead_sea_boots", Attributes.ATTACK_DAMAGE);
			add(list, Items.LEATHER_BOOTS, EquipmentSlot.FEET, 1, 1, c2, "twilight_grotto_boots", Attributes.ATTACK_SPEED);
			add(list, Items.LEATHER_BOOTS, EquipmentSlot.FEET, 1, 1, c3, "sea_tramps_boots", Attributes.MOVEMENT_SPEED);
			return list;
		}

		public static List<ItemStack> rare() {
			List<ItemStack> list = new ArrayList<>();
			add(list, Items.IRON_HELMET, EquipmentSlot.HEAD, 2, 2, 0, "dead_sea_helmet", Attributes.ATTACK_DAMAGE);
			add(list, Items.IRON_HELMET, EquipmentSlot.HEAD, 2, 2, 0, "twilight_grotto_helmet", Attributes.ATTACK_SPEED);
			add(list, Items.IRON_HELMET, EquipmentSlot.HEAD, 2, 2, 0, "sea_tramps_helmet", Attributes.MOVEMENT_SPEED);
			add(list, Items.IRON_CHESTPLATE, EquipmentSlot.CHEST, 2, 6, 0, "dead_sea_chestplate", Attributes.ATTACK_DAMAGE);
			add(list, Items.IRON_CHESTPLATE, EquipmentSlot.CHEST, 2, 6, 0, "twilight_grotto_chestplate", Attributes.ATTACK_SPEED);
			add(list, Items.IRON_CHESTPLATE, EquipmentSlot.CHEST, 2, 6, 0, "sea_tramps_chestplate", Attributes.MOVEMENT_SPEED);
			add(list, Items.IRON_LEGGINGS, EquipmentSlot.LEGS, 2, 5, 0, "dead_sea_leggings", Attributes.ATTACK_DAMAGE);
			add(list, Items.IRON_LEGGINGS, EquipmentSlot.LEGS, 2, 5, 0, "twilight_grotto_leggings", Attributes.ATTACK_SPEED);
			add(list, Items.IRON_LEGGINGS, EquipmentSlot.LEGS, 2, 5, 0, "sea_tramps_leggings", Attributes.MOVEMENT_SPEED);
			add(list, Items.IRON_BOOTS, EquipmentSlot.FEET, 2, 2, 0, "dead_sea_boots", Attributes.ATTACK_DAMAGE);
			add(list, Items.IRON_BOOTS, EquipmentSlot.FEET, 2, 2, 0, "twilight_grotto_boots", Attributes.ATTACK_SPEED);
			add(list, Items.IRON_BOOTS, EquipmentSlot.FEET, 2, 2, 0, "sea_tramps_boots", Attributes.MOVEMENT_SPEED);
			return list;
		}

		public static void add(List<ItemStack> list, Item item, EquipmentSlot slot, int mod, int armor, int color, String name, Attribute attribute) {
			for (int i = 1; i <= 5; i++) {
				final ItemStack stack = new ItemStack(item);
				stack.addAttributeModifier(Attributes.ARMOR, new AttributeModifier("base_armor", armor, AttributeModifier.Operation.ADDITION), slot);
				stack.addAttributeModifier(attribute, new AttributeModifier("base_bonus", mod * i * 0.01, AttributeModifier.Operation.MULTIPLY_TOTAL), slot);
				stack.setHoverName(Component.translatable("set.aquamirae." + name));
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
