package com.obscuria.aquamirae;

import com.obscuria.aquamirae.common.DelayedEvents;
import com.obscuria.aquamirae.common.items.RuneOfTheStormItem;
import com.obscuria.aquamirae.common.items.armor.AbyssalArmorItem;
import com.obscuria.aquamirae.common.items.armor.TerribleArmorItem;
import com.obscuria.aquamirae.common.items.weapon.CoralLanceItem;
import com.obscuria.aquamirae.common.items.weapon.FinCutterItem;
import com.obscuria.aquamirae.common.items.weapon.RemnantsSaberItem;
import com.obscuria.aquamirae.registry.*;
import com.obscuria.obscureapi.api.ClassRegistry;
import com.obscuria.obscureapi.api.utils.ItemUtils;
import com.obscuria.obscureapi.common.classes.GameClass;
import com.obscuria.obscureapi.registry.ObscureAPIAttributes;
import com.obscuria.obscureapi.registry.ObscureAPIEnchantments;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.networking.v1.EntityTrackingEvents;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PillagerEntity;
import net.minecraft.entity.mob.VindicatorEntity;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.map.MapIcon;
import net.minecraft.item.map.MapState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.structure.Structure;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public final class Aquamirae implements ModInitializer {
	public static final String MODID = "aquamirae";
	public static final String SEA_WOLF_ID = "aquamirae:sea_wolf";
	public static final GameClass SEA_WOLF = ClassRegistry.register(Aquamirae.MODID, "sea_wolf");
	public static final TagKey<Biome> ICE_MAZE = TagKey.of(RegistryKeys.BIOME, key("ice_maze"));
	public static final TagKey<Structure> SHIP = TagKey.of(RegistryKeys.STRUCTURE, key("ship"));
	public static final TagKey<Structure> OUTPOST = TagKey.of(RegistryKeys.STRUCTURE, key("outpost"));
	public static final TagKey<Structure> SHELTER = TagKey.of(RegistryKeys.STRUCTURE, key("shelter"));
	public static final TagKey<Block> EEL_MOVE = TagKey.of(RegistryKeys.BLOCK, key("eel_move"));
	public static final TagKey<Block> MAZE_MOTHER_DESTROY = TagKey.of(RegistryKeys.BLOCK, key("maze_mother_destroy"));
	public static final TagKey<Block> SCROLL_DESTROY = TagKey.of(RegistryKeys.BLOCK, key("scroll_destroy"));

	public void onInitialize() {
		AquamiraeBlocks.register();
		AquamiraeItems.register();
		AquamiraeEntities.register();
		AquamiraeSounds.register();
		AquamiraeFeatures.register();
		AquamiraeParticles.register();
		AquamiraeEffects.register();
		AquamiraePotions.register();
		DelayedEvents.register();

		ObscureAPIEnchantments.registerDistance();
		ObscureAPIEnchantments.registerMirror();
		ObscureAPIEnchantments.registerFastSpin();

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

		EntityTrackingEvents.START_TRACKING.register((entity, player) -> {
			if (entity instanceof PillagerEntity pillager) modifyLootTable(pillager, "entities/maze_pillager");
			if (entity instanceof VindicatorEntity vindicator) modifyLootTable(vindicator, "entities/maze_vindicator");
		});

		ServerLivingEntityEvents.ALLOW_DEATH.register((entity, source, damage) ->
				!(ItemUtils.countArmorPieces(entity, AbyssalArmorItem.class) >= 4 && AbyssalArmorItem.tryAvoidDeath(entity)));
	}

	public static Identifier key(String key) {
		return new Identifier(MODID, key);
	}

	public static ItemStack createStructureMap(TagKey<Structure> tag, ServerWorld server, Entity source) {
		final BlockPos pos = server.locateStructure(tag, source.getBlockPos(), 100, false);
		if (pos != null) {
			final Text name = tag == SHIP ? Text.translatable("filled_map.aquamirae.ship")
					: tag == OUTPOST ? Text.translatable("filled_map.aquamirae.outpost")
					: tag == SHELTER ? Text.translatable("filled_map.aquamirae.shelter")
					: Text.translatable("filled_map.buried_treasure");
			final ItemStack map = FilledMapItem.createMap(server, pos.getX(), pos.getZ(), (byte) 2, true, true);
			FilledMapItem.fillExplorationMap(server, map);
			MapState.addDecorationsNbt(map, pos, "+", MapIcon.Type.RED_X);
			map.setCustomName(name);
			final NbtCompound display = map.getOrCreateNbt().getCompound("display");
			final NbtList lore = new NbtList();
			lore.add(NbtString.of("{\"text\":\"§7x: " + pos.getX() + "\"}"));
			lore.add(NbtString.of("{\"text\":\"§7z: " + pos.getZ() + "\"}"));
			display.put("Lore", lore);
			map.setSubNbt("display", display);
			return map;
		}
		return ItemStack.EMPTY;
	}

	public static boolean isWinterEvent() {
		return Calendar.getInstance().get(Calendar.MONTH) == Calendar.DECEMBER || Calendar.getInstance().get(Calendar.MONTH) == Calendar.JANUARY;
	}

	public static void modifyLootTable(MobEntity mob, String loot) {
		if (!AquamiraeUtils.isInIceMaze(mob) || mob.getLootTable().toString().endsWith("captain")) return;
		final NbtCompound data = new NbtCompound();
		mob.writeCustomDataToNbt(data);
		data.putString("DeathLootTable", Aquamirae.MODID + ":" + loot);
		mob.readCustomDataFromNbt(data);
	}

	public static void onEntityDamage(LivingEntity entity, DamageSource source) {
		final int pieces = ItemUtils.countArmorPieces(entity, TerribleArmorItem.class);
		if (pieces >= 2) TerribleArmorItem.halfSetEffect(entity);
		if (pieces >= 4) TerribleArmorItem.fullSetEffect(entity, source);
	}

	public static float modifyDamage(LivingEntity entity, DamageSource source, float amount) {
		if (source.getAttacker() instanceof LivingEntity attacker) {
			final Item item = attacker.getMainHandStack().getItem();
			if (ItemUtils.hasPerk(attacker.getMainHandStack(), key("rune_of_the_storm")))
				amount += RuneOfTheStormItem.calculateDamageBonus(attacker, amount);
			if (item instanceof FinCutterItem) amount += FinCutterItem.calculateDamageBonus(attacker, amount);
			if (item instanceof RemnantsSaberItem) amount += RemnantsSaberItem.calculateDamageBonus(attacker, amount);
			if (item instanceof CoralLanceItem) amount += CoralLanceItem.calculateDamageBonus(attacker, entity, amount);
		}
		return amount;
	}

	public static class SetBuilder {

		public static @NotNull List<ItemStack> common() {
			final int c1 = getColor(10, 220, 160);
			final int c2 = getColor(10, 190, 220);
			final int c3 = getColor(10, 130, 220);
			List<ItemStack> list = new ArrayList<>();
			add(list, Items.LEATHER_HELMET, EquipmentSlot.HEAD, 1, 1, c1, "dead_sea_hat", EntityAttributes.GENERIC_ATTACK_DAMAGE);
			add(list, Items.LEATHER_HELMET, EquipmentSlot.HEAD, 1, 1, c2, "twilight_grotto_hat", ObscureAPIAttributes.PENETRATION);
			add(list, Items.LEATHER_HELMET, EquipmentSlot.HEAD, 1, 1, c3, "sea_tramps_hat", ObscureAPIAttributes.CRITICAL_HIT);
			add(list, Items.LEATHER_CHESTPLATE, EquipmentSlot.CHEST, 1, 3, c1, "dead_sea_doublet", EntityAttributes.GENERIC_ATTACK_DAMAGE);
			add(list, Items.LEATHER_CHESTPLATE, EquipmentSlot.CHEST, 1, 3, c2, "twilight_grotto_doublet", ObscureAPIAttributes.PENETRATION);
			add(list, Items.LEATHER_CHESTPLATE, EquipmentSlot.CHEST, 1, 3, c3, "sea_tramps_doublet", ObscureAPIAttributes.CRITICAL_HIT);
			add(list, Items.LEATHER_LEGGINGS, EquipmentSlot.LEGS, 1, 2, c1, "dead_sea_pants", EntityAttributes.GENERIC_ATTACK_DAMAGE);
			add(list, Items.LEATHER_LEGGINGS, EquipmentSlot.LEGS, 1, 2, c2, "twilight_grotto_pants", ObscureAPIAttributes.PENETRATION);
			add(list, Items.LEATHER_LEGGINGS, EquipmentSlot.LEGS, 1, 2, c3, "sea_tramps_pants", ObscureAPIAttributes.CRITICAL_HIT);
			add(list, Items.LEATHER_BOOTS, EquipmentSlot.FEET, 1, 1, c1, "dead_sea_boots", EntityAttributes.GENERIC_ATTACK_DAMAGE);
			add(list, Items.LEATHER_BOOTS, EquipmentSlot.FEET, 1, 1, c2, "twilight_grotto_boots", ObscureAPIAttributes.PENETRATION);
			add(list, Items.LEATHER_BOOTS, EquipmentSlot.FEET, 1, 1, c3, "sea_tramps_boots", ObscureAPIAttributes.CRITICAL_HIT);
			return list;
		}

		public static List<ItemStack> rare() {
			List<ItemStack> list = new ArrayList<>();
			add(list, Items.IRON_HELMET, EquipmentSlot.HEAD, 2, 2, 0, "dead_sea_helmet", EntityAttributes.GENERIC_ATTACK_DAMAGE);
			add(list, Items.IRON_HELMET, EquipmentSlot.HEAD, 2, 2, 0, "twilight_grotto_helmet", ObscureAPIAttributes.PENETRATION);
			add(list, Items.IRON_HELMET, EquipmentSlot.HEAD, 2, 2, 0, "sea_tramps_helmet", ObscureAPIAttributes.CRITICAL_HIT);
			add(list, Items.IRON_CHESTPLATE, EquipmentSlot.CHEST, 2, 6, 0, "dead_sea_chestplate", EntityAttributes.GENERIC_ATTACK_DAMAGE);
			add(list, Items.IRON_CHESTPLATE, EquipmentSlot.CHEST, 2, 6, 0, "twilight_grotto_chestplate", ObscureAPIAttributes.PENETRATION);
			add(list, Items.IRON_CHESTPLATE, EquipmentSlot.CHEST, 2, 6, 0, "sea_tramps_chestplate", ObscureAPIAttributes.CRITICAL_HIT);
			add(list, Items.IRON_LEGGINGS, EquipmentSlot.LEGS, 2, 5, 0, "dead_sea_leggings", EntityAttributes.GENERIC_ATTACK_DAMAGE);
			add(list, Items.IRON_LEGGINGS, EquipmentSlot.LEGS, 2, 5, 0, "twilight_grotto_leggings", ObscureAPIAttributes.PENETRATION);
			add(list, Items.IRON_LEGGINGS, EquipmentSlot.LEGS, 2, 5, 0, "sea_tramps_leggings", ObscureAPIAttributes.CRITICAL_HIT);
			add(list, Items.IRON_BOOTS, EquipmentSlot.FEET, 2, 2, 0, "dead_sea_boots", EntityAttributes.GENERIC_ATTACK_DAMAGE);
			add(list, Items.IRON_BOOTS, EquipmentSlot.FEET, 2, 2, 0, "twilight_grotto_boots", ObscureAPIAttributes.PENETRATION);
			add(list, Items.IRON_BOOTS, EquipmentSlot.FEET, 2, 2, 0, "sea_tramps_boots", ObscureAPIAttributes.CRITICAL_HIT);
			return list;
		}

		public static void add(List<ItemStack> list, Item item, EquipmentSlot slot, int mod, int armor, int color, String name, EntityAttribute attribute) {
			for (int i = 1; i <= 5; i++) {
				final ItemStack stack = new ItemStack(item);
				stack.addAttributeModifier(EntityAttributes.GENERIC_ARMOR, new EntityAttributeModifier("base_armor",
						armor, EntityAttributeModifier.Operation.ADDITION), slot);
				stack.addAttributeModifier(attribute, new EntityAttributeModifier("base_bonus",
						mod * i * 0.01, EntityAttributeModifier.Operation.MULTIPLY_TOTAL), slot);
				stack.setCustomName(Text.translatable("set.aquamirae." + name));
				if (i == 5) stack.addEnchantment(Enchantments.UNBREAKING, mod);
				if (color > 0) stack.getOrCreateSubNbt("display").putInt("color", color);
				list.add(stack);
			}
		}

		public static int getColor(int r, int g, int b) {
			return r * 65536 + g * 256 + b;
		}
	}
}
