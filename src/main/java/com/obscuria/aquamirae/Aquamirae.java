package com.obscuria.aquamirae;

import com.obscuria.aquamirae.common.AquamiraeHooks;
import com.obscuria.aquamirae.common.AquamiraeTags;
import com.obscuria.aquamirae.common.DeadSeaCurse;
import com.obscuria.aquamirae.common.entity.IceMazeEntity;
import com.obscuria.aquamirae.common.entity.ShipGraveyardEntity;
import com.obscuria.aquamirae.common.item.armor.ThreeBoltArmor;
import com.obscuria.aquamirae.compat.AquamiraeCompats;
import com.obscuria.aquamirae.compat.curios.CuriosCompat;
import com.obscuria.aquamirae.datagen.AquamiraeData;
import com.obscuria.aquamirae.network.ScrollEffectPacket;
import com.obscuria.aquamirae.network.StackCursedPacket;
import com.obscuria.aquamirae.registry.*;
import com.obscuria.core.ObscureAPI;
import com.obscuria.core.client.graphic.Icons;
import com.obscuria.core.common.Splitter;
import com.obscuria.core.common.signal.RuntimeSignals;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
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

@Mod(Aquamirae.MODID)
public class Aquamirae {
	public static final String MODID = "aquamirae";
	public static final SimpleChannel CHANNEL = ChannelBuilder.named(key("main")).simpleChannel();

	public static ResourceLocation key(String key) {
		return new ResourceLocation(MODID, key);
	}

	public static boolean isInIceMaze(Entity entity) {
		return entity.level().getBiome(entity.blockPosition()).is(AquamiraeTags.ICE_MAZE);
	}

	public static boolean isIceMazeEntity(Entity entity) {
		return entity.getClass().isAnnotationPresent(IceMazeEntity.class);
	}

	public static boolean isShipGraveyardEntity(Entity entity) {
		return entity.getClass().isAnnotationPresent(ShipGraveyardEntity.class);
	}

	public static boolean isWinterEvent() {
		final var month = Calendar.getInstance().get(Calendar.MONTH);
		return month == Calendar.DECEMBER || month == Calendar.JANUARY;
	}

	public static void setAttribute(LivingEntity entity, Attribute attribute, double amount) {
		final var instance = entity.getAttribute(attribute);
		if (instance != null) instance.setBaseValue(amount);
		if (attribute == Attributes.MAX_HEALTH)
			entity.setHealth(entity.getMaxHealth());
	}

	public static void addIconTooltip(Icons icon, Component component, List<Component> tooltip) {
		tooltip.add(Component.empty()
				.append(icon.component())
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
		AquamiraeItems.HANDLER.register(bus);
		AquamiraeSensors.HANDLER.register(bus);
		AquamiraeMemoryModules.HANDLER.register(bus);
		AquamiraeEntityTypes.HANDLER.register(bus);
		AquamiraeMobEffects.HANDLER.register(bus);
		AquamiraePotions.HANDLER.register(bus);
		AquamiraeParticleTypes.HANDLER.register(bus);
		AquamiraeAttributes.HANDLER.register(bus);
		AquamiraeRecipeSerializers.HANDLER.register(bus);
		AquamiraeLootPoolEntryTypes.HANDLER.register(bus);
		AquamiraeStructureProcessors.HANDLER.register(bus);
		AquamiraeConfig.register();

		bus.addListener(this::onAttributeModification);
		bus.addListener(AquamiraeData::onGatherData);
		bus.addListener(AquamiraeEntityTypes::registerAttributes);
		bus.addListener(AquamiraeEntityTypes::registerSpawns);
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
		ObscureAPI.ARMOR_BONUS_REQUESTED.connect(ThreeBoltArmor.Helmet::onBonusRequest);
		DeadSeaCurse.registerModifiers();

		AquamiraeCompats.CURIOS_API.safeRun(() -> () -> CuriosCompat.setup(bus));

		if (FMLEnvironment.dist.isClient())
			AquamiraeClient.setup(bus);
	}

	private void onMobSpawn(MobSpawnEvent event) {
		AquamiraeHooks.onMobSpawn(event.getEntity());
	}

	private void onPlayerTick(TickEvent.PlayerTickEvent event) {
		if (event.phase == TickEvent.Phase.END)
			AquamiraeHooks.onPlayerTick(event.player);
	}

	private void onEntityHurted(LivingHurtEvent event) {
		event.setAmount(AquamiraeHooks.modifyHurtDamage(event.getEntity(), event.getAmount()));
	}

	private void onItemConsumed(LivingEntityUseItemEvent.Finish event) {
		AquamiraeHooks.onItemUsed(event.getEntity(), event.getItem());
	}

	private void onEntityDeath(LivingDeathEvent event) {
		AquamiraeHooks.onLivingDeath(event.getEntity(), event.getSource());
	}

	private void onPlayerBreathe(LivingBreatheEvent event) {
		event.setCanBreathe(AquamiraeHooks.canBreathe(event.getEntity(), event.canBreathe()));
	}

	private void onTooltip(ItemTooltipEvent event) {
		AquamiraeHooks.onTooltip(event.getItemStack(), event.getToolTip());
	}

	private void onAttributeModification(EntityAttributeModificationEvent event) {
		AquamiraeHooks.acceptCustomAttributes(event::add);
	}
}
