package com.obscuria.aquamirae;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.obscuria.aquamirae.common.AquamiraeHooks;
import com.obscuria.aquamirae.common.DeadSeaCurse;
import com.obscuria.aquamirae.common.entity.IceMazeEntity;
import com.obscuria.aquamirae.common.item.armor.ThreeBoltArmor;
import com.obscuria.aquamirae.compat.AquamiraeCompats;
import com.obscuria.aquamirae.compat.curios.CuriosCompat;
import com.obscuria.aquamirae.network.ScrollEffectPacket;
import com.obscuria.aquamirae.network.StackCursedPacket;
import com.obscuria.aquamirae.registry.*;
import com.obscuria.core.api.ObscureAPI;
import com.obscuria.core.api.graphic.Icons;
import com.obscuria.core.api.util.Splitter;
import com.obscuria.core.api.util.signal.RuntimeSignals;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.SimpleChannel;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

@Mod(Aquamirae.MODID)
public class Aquamirae {
	public static final String MODID = "aquamirae";
	public static final SimpleChannel CHANNEL = ChannelBuilder.named(key("main")).simpleChannel();
	public static final TagKey<Biome> ICE_MAZE = TagKey.create(Registries.BIOME, key("ice_maze"));
	public static final TagKey<Structure> SHIP = TagKey.create(Registries.STRUCTURE, key("ship"));
	public static final TagKey<Structure> OUTPOST = TagKey.create(Registries.STRUCTURE, key("outpost"));
	public static final TagKey<Structure> SHELTER = TagKey.create(Registries.STRUCTURE, key("shelter"));
	public static final TagKey<Block> EEL_CAN_DIG = BlockTags.create(key("eel_can_dig"));
	public static final TagKey<Block> MAZE_MOTHER_CAN_DESTROY = BlockTags.create(key("maze_mother_can_destroy"));
	public static final TagKey<Block> SCROLL_CAN_DESTROY = BlockTags.create(key("scroll_can_destroy"));

	public static ResourceLocation key(String key) {
		return new ResourceLocation(MODID, key);
	}

	public static boolean isInIceMaze(Entity entity) {
		return entity.level().getBiome(entity.blockPosition()).is(ICE_MAZE);
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

	public static void addIconTooltip(Icons icon, Component component, List<Component> tooltip) {
		tooltip.add(Component.empty()
				.append(icon.toComponent())
				.append(Component.literal(" "))
				.append(component));
	}

	public static void addTooltip(Component component, List<Component> tooltip) {
		tooltip.addAll(Splitter.of(component)
				.setStyle(Style.EMPTY.applyFormat(ChatFormatting.GRAY))
				.setLineLength(32)
				.build());
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
		AquamiraeLootPoolEntryTypes.HANDLER.register(bus);
		AquamiraeConfig.register();

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
		MinecraftForge.EVENT_BUS.addListener(this::onEntityDeath);
		MinecraftForge.EVENT_BUS.addListener(this::onPlayerBreathe);

		RuntimeSignals.ON_PLAYER_TICK.connect(ThreeBoltArmor.Helmet::onPlayerTick);
		ObscureAPI.KEY_ARMOR_BONUS_PRESSED.connect(ThreeBoltArmor.Helmet::onArmorBonusPressed);
		DeadSeaCurse.registerModifiers();

		AquamiraeCompats.CURIOS_API.safeRun(() -> () -> CuriosCompat.setup(bus));

		if (FMLEnvironment.dist.isClient())
			AquamiraeClient.setup(bus);
	}

	public static Multimap<Attribute, AttributeModifier> addAttribute(Multimap<Attribute, AttributeModifier> multimap,
																	  Attribute attribute,  UUID uuid, AttributeModifier.Operation operation,
																	  double amount, boolean condition) {
		if (!condition) return multimap;
		return ImmutableMultimap.<Attribute, AttributeModifier>builder().putAll(multimap).put(attribute,
						new AttributeModifier(uuid, "Bonus", amount, operation)).build();
	}

	private void onMobSpawn(MobSpawnEvent event) {
		AquamiraeHooks.onMobSpawn(event.getEntity());
	}

	private void onPlayerTick(TickEvent.PlayerTickEvent event) {
		if (event.phase == TickEvent.Phase.END)
			AquamiraeHooks.onPlayerTick(event.player);
	}

	private void onEntityHurted(LivingHurtEvent event) {
		event.setAmount(AquamiraeHooks.getLivingDamage(event.getEntity(), event.getAmount(), event.getSource()));
		if (event.getAmount() <= 0) event.setCanceled(true);
	}

	private void onItemConsumed(LivingEntityUseItemEvent.Finish event) {
		if (event.getItem().isEdible())
			AquamiraeHooks.onFoodEaten(event.getEntity(), event.getItem());
	}

	private void onEntityDeath(LivingDeathEvent event) {
		//Abyssal Armor
//		if (event != null && event.getEntity() != null) {
//			final LivingEntity entity = event.getEntity();
//			final int TOTAL = 0;//ItemUtils.getArmorPieces(entity, AbyssalArmor.class);
//			if (TOTAL >= 4 && !entity.hasEffect(AquamiraeMobEffects.CRYSTALLIZATION.get())) {
//				final AbyssalArmor item = (AbyssalArmor) getArmor(entity, AbyssalArmor.class).getItem();
//				if (!entity.getPersistentData().getBoolean("crystallization")) {
//					event.setCanceled(true);
////					entity.addEffect(new MobEffectInstance(AquamiraeMobEffects.CRYSTALLIZATION.get(),
////							20 * item.ABILITY_FULLSET_1.getVariable(entity, 1), 0, true, true));
//					entity.setHealth(entity.getMaxHealth());
//					if (entity.level() instanceof ServerLevel level) level.playSound(null, entity.blockPosition().above(), SoundEvents.TOTEM_USE, SoundSource.PLAYERS, 1, 1);
//					final ItemStack head = entity.getItemBySlot(EquipmentSlot.HEAD);
//					final ItemStack chest = entity.getItemBySlot(EquipmentSlot.CHEST);
//					final ItemStack legs = entity.getItemBySlot(EquipmentSlot.LEGS);
//					final ItemStack feet = entity.getItemBySlot(EquipmentSlot.FEET);
//					if (head.hurt(50, entity.getRandom(), null)) { head.shrink(1); head.setDamageValue(0); }
//					if (chest.hurt(50, entity.getRandom(), null)) { chest.shrink(1); chest.setDamageValue(0); }
//					if (legs.hurt(50, entity.getRandom(), null)) { legs.shrink(1); legs.setDamageValue(0); }
//					if (feet.hurt(50, entity.getRandom(), null)) { feet.shrink(1); feet.setDamageValue(0); }
//				}
//			}
//		}
	}

	private void onPlayerBreathe(LivingBreatheEvent event) {
		if (event.getEntity() instanceof Player player)
			event.setCanBreathe(AquamiraeHooks.canBreathe(player, event.canBreathe()));
	}

	private void onTooltip(ItemTooltipEvent event) {
		AquamiraeHooks.onTooltip(event.getItemStack(), event.getToolTip());
	}

	private void onAttributeModification(EntityAttributeModificationEvent event) {
		event.add(EntityType.PLAYER, AquamiraeAttributes.DEPTHS_FURY.get(), 0);
	}
}
