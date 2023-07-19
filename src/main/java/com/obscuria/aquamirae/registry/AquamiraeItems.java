
package com.obscuria.aquamirae.registry;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.common.items.StewItem;
import com.obscuria.aquamirae.common.items.*;
import com.obscuria.aquamirae.common.items.armor.AbyssalArmorItem;
import com.obscuria.aquamirae.common.items.armor.TerribleArmorItem;
import com.obscuria.aquamirae.common.items.armor.ThreeBoltArmorItem;
import com.obscuria.aquamirae.common.items.weapon.*;
import com.obscuria.obscureapi.common.entities.items.ObscureRarity;
import com.obscuria.obscureapi.registry.ObscureAPIEnchantments;
import com.obscuria.obscureapi.registry.ObscureAPIMobEffects;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Rarity;

import java.util.ArrayList;
import java.util.List;

public interface AquamiraeItems {
	RuneOfTheStormItem RUNE_OF_THE_STORM = new RuneOfTheStormItem(new Item.Settings().maxCount(1).fireproof().rarity(ObscureRarity.MYTHIC));
	SpawnEggItem GOLDEN_MOTH_SPAWN_EGG = new SpawnEggItem(AquamiraeEntities.GOLDEN_MOTH, -3381760, -205, new Item.Settings().rarity(Rarity.UNCOMMON));
	TerribleSwordItem TERRIBLE_SWORD = new TerribleSwordItem(new Item.Settings());
	FinCutterItem FIN_CUTTER = new FinCutterItem(new Item.Settings());
	TerribleArmorItem.Helmet TERRIBLE_HELMET = new TerribleArmorItem.Helmet(new Item.Settings());
	TerribleArmorItem.Chestplate TERRIBLE_CHESTPLATE = new TerribleArmorItem.Chestplate(new Item.Settings());
	TerribleArmorItem.Leggings TERRIBLE_LEGGINGS = new TerribleArmorItem.Leggings(new Item.Settings());
	TerribleArmorItem.Boots TERRIBLE_BOOTS = new TerribleArmorItem.Boots(new Item.Settings());
	ShipGraveyardEchoItem SHIP_GRAVEYARD_ECHO = new ShipGraveyardEchoItem(new Item.Settings().maxCount(64).rarity(Rarity.UNCOMMON));
	SpawnEggItem MAW_SPAWN_EGG = new SpawnEggItem(AquamiraeEntities.MAW, -16764109, -16737895, new Item.Settings());
	SpawnEggItem ANGLERFISH_SPAWN_EGG = new SpawnEggItem(AquamiraeEntities.ANGLERFISH, -12963033, -4942746, new Item.Settings());
	DividerItem DIVIDER = new DividerItem(new Item.Settings().fireproof().rarity(Rarity.EPIC));
	WhisperOfTheAbyssItem WHISPER_OF_THE_ABYSS = new WhisperOfTheAbyssItem(new Item.Settings().fireproof().rarity(Rarity.EPIC));
	AbyssalArmorItem.Heaume ABYSSAL_HEAUME = new AbyssalArmorItem.Heaume(new Item.Settings().rarity(Rarity.EPIC));
	AbyssalArmorItem.Brigantine ABYSSAL_BRIGANTINE = new AbyssalArmorItem.Brigantine(new Item.Settings().rarity(Rarity.EPIC));
	AbyssalArmorItem.Leggings ABYSSAL_LEGGINGS = new AbyssalArmorItem.Leggings(new Item.Settings().rarity(Rarity.EPIC));
	AbyssalArmorItem.Boots ABYSSAL_BOOTS = new AbyssalArmorItem.Boots(new Item.Settings().rarity(Rarity.EPIC));
	AbyssalArmorItem.Tiara ABYSSAL_TIARA = new AbyssalArmorItem.Tiara(new Item.Settings().rarity(Rarity.EPIC));
	SpawnEggItem MAZE_MOTHER_SPAWN_EGG = new SpawnEggItem(AquamiraeEntities.MAZE_MOTHER, -10092442, -39169, new Item.Settings());
	SpawnEggItem CAPTAIN_CORNELIA_SPAWN_EGG = new SpawnEggItem(AquamiraeEntities.CAPTAIN_CORNELIA, -7760229, -13355980, new Item.Settings().rarity(Rarity.EPIC));
	RemnantsSaberItem REMNANTS_SABER = new RemnantsSaberItem(new Item.Settings());
	PoisonedBladeItem POISONED_BLADE = new PoisonedBladeItem(new Item.Settings());
	ThreeBoltArmorItem THREE_BOLT_HELMET = new ThreeBoltArmorItem(ArmorItem.Type.HELMET, new Item.Settings().rarity(Rarity.EPIC));
	ThreeBoltArmorItem THREE_BOLT_SUIT = new ThreeBoltArmorItem(ArmorItem.Type.CHESTPLATE, new Item.Settings().rarity(Rarity.UNCOMMON));
	ThreeBoltArmorItem THREE_BOLT_LEGGINGS = new ThreeBoltArmorItem(ArmorItem.Type.LEGGINGS, new Item.Settings().rarity(Rarity.UNCOMMON));
	ThreeBoltArmorItem THREE_BOLT_BOOTS = new ThreeBoltArmorItem(ArmorItem.Type.BOOTS, new Item.Settings().rarity(Rarity.COMMON));
	DeadSeaScrollItem DEAD_SEA_SCROLL = new DeadSeaScrollItem(new Item.Settings().maxCount(8).rarity(Rarity.UNCOMMON));
	SpawnEggItem PILLAGERS_PATROL_SPAWN_EGG = new SpawnEggItem(AquamiraeEntities.PILLAGERS_PATROL, -16751002, -16711681, new Item.Settings());
	SpawnEggItem TORTURED_SOUL_SPAWN_EGG = new SpawnEggItem(AquamiraeEntities.TORTURED_SOUL, -13421773, -16724737, new Item.Settings());
	CoralLanceItem CORAL_LANCE = new CoralLanceItem(new Item.Settings().fireproof().rarity(ObscureRarity.MYTHIC));
	DaggerOfGreedItem DAGGER_OF_GREED = new DaggerOfGreedItem(new Item.Settings().fireproof().rarity(Rarity.UNCOMMON));
	ShellHornItem SHELL_HORN = new ShellHornItem(new Item.Settings().maxCount(1).rarity(Rarity.UNCOMMON));
	Item FROZEN_KEY = new Item(new Item.Settings().maxCount(1).rarity(Rarity.UNCOMMON));
	SpawnEggItem EEL_SPAWN_EGG = new SpawnEggItem(AquamiraeEntities.EEL, -16764109, -16737844, new Item.Settings());
	SpawnEggItem SPINEFISH_SPAWN_EGG = new SpawnEggItem(AquamiraeEntities.SPINEFISH,  1589067, 11451069, new Item.Settings());
	SweetLanceItem SWEET_LANCE = new SweetLanceItem(new Item.Settings().fireproof().rarity(ObscureRarity.MYTHIC)
			.food(new FoodComponent.Builder().hunger(2).saturationModifier(0.3f).build()));
	PoisonedChakraItem POISONED_CHAKRA = new PoisonedChakraItem(new Item.Settings());
	MazeRoseItem MAZE_ROSE = new MazeRoseItem(new Item.Settings().rarity(Rarity.UNCOMMON));
	PiratePouchItem PIRATE_POUCH = new PiratePouchItem(new Item.Settings().maxCount(16).rarity(Rarity.COMMON));
	TreasurePouchItem TREASURE_POUCH = new TreasurePouchItem(new Item.Settings().maxCount(16).rarity(Rarity.UNCOMMON));
	MusicDiscItem MUSIC_DISC_HORIZON = new MusicDiscItem(6, AquamiraeSounds.RECORD_HORIZON, new Item.Settings().maxCount(1).rarity(Rarity.RARE), 74);
	MusicDiscItem MUSIC_DISC_FORSAKEN_DROWNAGE = new MusicDiscItem(12, AquamiraeSounds.RECORD_FORSAKEN_DROWNAGE, new Item.Settings().maxCount(1).rarity(ObscureRarity.MYTHIC), 154);
	Item FIN = new Item(new Item.Settings().maxCount(64).rarity(Rarity.COMMON)
			.food(new FoodComponent.Builder().hunger(1).saturationModifier(0f).meat().build()));
	Item ESCA = new Item(new Item.Settings().maxCount(64).rarity(Rarity.COMMON)
			.food(new FoodComponent.Builder().hunger(6).saturationModifier(0.2f)
					.statusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 200, 0), 1).build()));
	Item ANGLERS_FANG = new Item(new Item.Settings().maxCount(64).rarity(Rarity.UNCOMMON));
	Item ABYSSAL_AMETHYST = new Item(new Item.Settings().maxCount(64).rarity(Rarity.UNCOMMON));
	SharpBonesItem SHARP_BONES = new SharpBonesItem(new Item.Settings().maxCount(64).rarity(Rarity.COMMON)
			.food(new FoodComponent.Builder().hunger(1).saturationModifier(0f).meat().build()));
	BlockItem PAINTING_ANGLERFISH = new BlockItem(AquamiraeBlocks.PAINTING_ANGLERFISH, new Item.Settings().rarity(Rarity.UNCOMMON));
	BlockItem PAINTING_OXYGELIUM = new BlockItem(AquamiraeBlocks.PAINTING_OXYGELIUM, new Item.Settings().rarity(Rarity.UNCOMMON));
	BlockItem PAINTING_TORTURED_SOUL = new BlockItem(AquamiraeBlocks.PAINTING_TORTURED_SOUL, new Item.Settings().rarity(Rarity.UNCOMMON));
	BlockItem PAINTING_AURORA = new BlockItem(AquamiraeBlocks.PAINTING_AURORA, new Item.Settings().rarity(Rarity.UNCOMMON));
	BlockItem GOLDEN_MOTH_IN_A_JAR = new BlockItem(AquamiraeBlocks.GOLDEN_MOTH_IN_A_JAR, new Item.Settings());
	OxygeliumItem OXYGELIUM = new OxygeliumItem(new Item.Settings().maxCount(64).rarity(Rarity.COMMON));
	WisteriaNiveisItem WISTERIA_NIVEIS = new WisteriaNiveisItem(new Item.Settings().maxCount(64).rarity(Rarity.COMMON));
	Item OXYGEN_TANK = new Item(new Item.Settings().maxCount(1).rarity(Rarity.COMMON));
	BlockItem FROZEN_CHEST = new BlockItem(AquamiraeBlocks.FROZEN_CHEST, new Item.Settings().rarity(Rarity.UNCOMMON));
	LuminescentBubbleItem LUMINESCENT_BUBBLE = new LuminescentBubbleItem(new Item.Settings().maxCount(16).rarity(Rarity.COMMON));
	BlockItem LUMINESCENT_LAMP = new BlockItem(AquamiraeBlocks.LUMINESCENT_LAMP, new Item.Settings());
	Item SEA_CASSEROLE = new Item(new Item.Settings().maxCount(64).rarity(Rarity.COMMON)
			.food(new FoodComponent.Builder().hunger(6).saturationModifier(0.4f).alwaysEdible()
					.statusEffect(new StatusEffectInstance(ObscureAPIMobEffects.RUSH, 1800, 1), 1)
					.statusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 600, 0), 1).build()));
	EntityBucketItem SPINEFISH_BUCKET = new EntityBucketItem(AquamiraeEntities.SPINEFISH, Fluids.WATER, SoundEvents.ITEM_BUCKET_EMPTY_FISH, new Item.Settings().maxCount(1));
	SpinefishItem SPINEFISH = new SpinefishItem(new Item.Settings().maxCount(64).rarity(Rarity.COMMON)
			.food(new FoodComponent.Builder().hunger(2).saturationModifier(0f).meat().build()));
	SpinefishItem COOKED_SPINEFISH = new SpinefishItem(new Item.Settings().maxCount(64).rarity(Rarity.COMMON)
			.food(new FoodComponent.Builder().hunger(6).saturationModifier(0.4f).meat().build()));
	StewItem SEA_STEW = new StewItem(new Item.Settings().maxCount(1).rarity(Rarity.UNCOMMON)
			.food(new FoodComponent.Builder().hunger(12).saturationModifier(0.8f).alwaysEdible()
					.statusEffect(new StatusEffectInstance(ObscureAPIMobEffects.FURY, 2400, 2), 1)
					.statusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 2400, 0), 1).build()));
	StewItem POSEIDONS_BREAKFAST = new StewItem(new Item.Settings().maxCount(1).rarity(Rarity.EPIC)
			.food(new FoodComponent.Builder().hunger(20).saturationModifier(1f).alwaysEdible()
					.statusEffect(new StatusEffectInstance(ObscureAPIMobEffects.FURY, 3600, 9), 1)
					.statusEffect(new StatusEffectInstance(ObscureAPIMobEffects.RUSH, 3600, 4), 1).build())) {
		@Override
		public boolean hasGlint(ItemStack stack) {
			return true;
		}
	};
	BlockItem ELODEA = new BlockItem(AquamiraeBlocks.ELODEA, new Item.Settings());

	ItemGroup AQUAMIRAE_GROUP = ItemGroup.create(ItemGroup.Row.TOP, 1)
			.displayName(Text.translatable("itemGroup." + Aquamirae.MODID))
			.icon(RUNE_OF_THE_STORM::getDefaultStack)
			.entries((context, entries) -> {
				entries.add(AquamiraeItems.RUNE_OF_THE_STORM);
				entries.add(AquamiraeItems.GOLDEN_MOTH_SPAWN_EGG);
				entries.add(AquamiraeItems.TERRIBLE_SWORD);
				entries.add(AquamiraeItems.FIN_CUTTER);
				entries.add(AquamiraeItems.TERRIBLE_HELMET);
				entries.add(AquamiraeItems.TERRIBLE_CHESTPLATE);
				entries.add(AquamiraeItems.TERRIBLE_LEGGINGS);
				entries.add(AquamiraeItems.TERRIBLE_BOOTS);
				entries.add(AquamiraeItems.SHIP_GRAVEYARD_ECHO);

				entries.add(AquamiraeItems.MAW_SPAWN_EGG);
				entries.add(AquamiraeItems.ANGLERFISH_SPAWN_EGG);
				entries.add(AquamiraeItems.DIVIDER);
				entries.add(AquamiraeItems.WHISPER_OF_THE_ABYSS);
				entries.add(AquamiraeItems.ABYSSAL_HEAUME);
				entries.add(AquamiraeItems.ABYSSAL_BRIGANTINE);
				entries.add(AquamiraeItems.ABYSSAL_LEGGINGS);
				entries.add(AquamiraeItems.ABYSSAL_BOOTS);
				entries.add(AquamiraeItems.ABYSSAL_TIARA);

				entries.add(AquamiraeItems.MAZE_MOTHER_SPAWN_EGG);
				entries.add(AquamiraeItems.CAPTAIN_CORNELIA_SPAWN_EGG);
				entries.add(AquamiraeItems.REMNANTS_SABER);
				entries.add(AquamiraeItems.POISONED_BLADE);
				entries.add(AquamiraeItems.THREE_BOLT_HELMET);
				entries.add(AquamiraeItems.THREE_BOLT_SUIT);
				entries.add(AquamiraeItems.THREE_BOLT_LEGGINGS);
				entries.add(AquamiraeItems.THREE_BOLT_BOOTS);
				entries.add(AquamiraeItems.DEAD_SEA_SCROLL);

				entries.add(AquamiraeItems.PILLAGERS_PATROL_SPAWN_EGG);
				entries.add(AquamiraeItems.TORTURED_SOUL_SPAWN_EGG);
				entries.add(AquamiraeItems.CORAL_LANCE.getDefaultStack());
				entries.add(AquamiraeItems.DAGGER_OF_GREED);
				entries.add(AquamiraeItems.SHELL_HORN);
				entries.add(AquamiraeItems.FROZEN_KEY);
				entries.addAll(logBooks());

				entries.add(AquamiraeItems.EEL_SPAWN_EGG);
				entries.add(AquamiraeItems.SPINEFISH_SPAWN_EGG);
				if (Aquamirae.isWinterEvent()) entries.add(AquamiraeItems.SWEET_LANCE.getDefaultStack());
				entries.addAll(poisonedChakras());
				entries.addAll(mazeRoses());

				entries.add(AquamiraeItems.PIRATE_POUCH);
				entries.add(AquamiraeItems.TREASURE_POUCH);
				entries.add(AquamiraeItems.MUSIC_DISC_HORIZON);
				entries.add(AquamiraeItems.MUSIC_DISC_FORSAKEN_DROWNAGE);
				entries.add(AquamiraeItems.FIN);
				entries.add(AquamiraeItems.ESCA);
				entries.add(AquamiraeItems.ANGLERS_FANG);
				entries.add(AquamiraeItems.ABYSSAL_AMETHYST);
				entries.add(AquamiraeItems.SHARP_BONES);
				entries.add(AquamiraeItems.PAINTING_ANGLERFISH);
				entries.add(AquamiraeItems.PAINTING_OXYGELIUM);
				entries.add(AquamiraeItems.PAINTING_TORTURED_SOUL);
				entries.add(AquamiraeItems.PAINTING_AURORA);
				entries.add(AquamiraeItems.GOLDEN_MOTH_IN_A_JAR);
				entries.add(AquamiraeItems.OXYGELIUM);
				entries.add(AquamiraeItems.WISTERIA_NIVEIS);
				entries.add(AquamiraeItems.OXYGEN_TANK);
				entries.add(AquamiraeItems.FROZEN_CHEST);
				entries.add(AquamiraeItems.LUMINESCENT_BUBBLE);
				entries.add(AquamiraeItems.LUMINESCENT_LAMP);
				entries.add(AquamiraeItems.SEA_CASSEROLE);
				entries.add(AquamiraeItems.SPINEFISH_BUCKET);
				entries.add(AquamiraeItems.SPINEFISH);
				entries.add(AquamiraeItems.COOKED_SPINEFISH);
				entries.add(AquamiraeItems.SEA_STEW);
				entries.add(AquamiraeItems.POSEIDONS_BREAKFAST);
				entries.add(AquamiraeItems.ELODEA);
				entries.addAll(Aquamirae.SetBuilder.common());
				entries.addAll(Aquamirae.SetBuilder.rare());
			}).build();

	static void register() {
		Registry.register(Registries.ITEM, Aquamirae.key("rune_of_the_storm"), RUNE_OF_THE_STORM);
		Registry.register(Registries.ITEM, Aquamirae.key("golden_moth_spawn_egg"), GOLDEN_MOTH_SPAWN_EGG);
		Registry.register(Registries.ITEM, Aquamirae.key("terrible_sword"), TERRIBLE_SWORD);
		Registry.register(Registries.ITEM, Aquamirae.key("fin_cutter"), FIN_CUTTER);
		Registry.register(Registries.ITEM, Aquamirae.key("terrible_helmet"), TERRIBLE_HELMET);
		Registry.register(Registries.ITEM, Aquamirae.key("terrible_chestplate"), TERRIBLE_CHESTPLATE);
		Registry.register(Registries.ITEM, Aquamirae.key("terrible_leggings"), TERRIBLE_LEGGINGS);
		Registry.register(Registries.ITEM, Aquamirae.key("terrible_boots"), TERRIBLE_BOOTS);
		Registry.register(Registries.ITEM, Aquamirae.key("ship_graveyard_echo"), SHIP_GRAVEYARD_ECHO);
		Registry.register(Registries.ITEM, Aquamirae.key("maw_spawn_egg"), MAW_SPAWN_EGG);
		Registry.register(Registries.ITEM, Aquamirae.key("anglerfish_spawn_egg"), ANGLERFISH_SPAWN_EGG);
		Registry.register(Registries.ITEM, Aquamirae.key("divider"), DIVIDER);
		Registry.register(Registries.ITEM, Aquamirae.key("whisper_of_the_abyss"), WHISPER_OF_THE_ABYSS);
		Registry.register(Registries.ITEM, Aquamirae.key("abyssal_heaume"), ABYSSAL_HEAUME);
		Registry.register(Registries.ITEM, Aquamirae.key("abyssal_brigantine"), ABYSSAL_BRIGANTINE);
		Registry.register(Registries.ITEM, Aquamirae.key("abyssal_leggings"), ABYSSAL_LEGGINGS);
		Registry.register(Registries.ITEM, Aquamirae.key("abyssal_boots"), ABYSSAL_BOOTS);
		Registry.register(Registries.ITEM, Aquamirae.key("abyssal_tiara"), ABYSSAL_TIARA);
		Registry.register(Registries.ITEM, Aquamirae.key("maze_mother_spawn_egg"), MAZE_MOTHER_SPAWN_EGG);
		Registry.register(Registries.ITEM, Aquamirae.key("captain_cornelia_spawn_egg"), CAPTAIN_CORNELIA_SPAWN_EGG);
		Registry.register(Registries.ITEM, Aquamirae.key("remnants_saber"), REMNANTS_SABER);
		Registry.register(Registries.ITEM, Aquamirae.key("poisoned_blade"), POISONED_BLADE);
		Registry.register(Registries.ITEM, Aquamirae.key("three_bolt_helmet"), THREE_BOLT_HELMET);
		Registry.register(Registries.ITEM, Aquamirae.key("three_bolt_suit"), THREE_BOLT_SUIT);
		Registry.register(Registries.ITEM, Aquamirae.key("three_bolt_leggings"), THREE_BOLT_LEGGINGS);
		Registry.register(Registries.ITEM, Aquamirae.key("three_bolt_boots"), THREE_BOLT_BOOTS);
		Registry.register(Registries.ITEM, Aquamirae.key("dead_sea_scroll"), DEAD_SEA_SCROLL);
		Registry.register(Registries.ITEM, Aquamirae.key("pillagers_patrol_spawn_egg"), PILLAGERS_PATROL_SPAWN_EGG);
		Registry.register(Registries.ITEM, Aquamirae.key("tortured_soul_spawn_egg"), TORTURED_SOUL_SPAWN_EGG);
		Registry.register(Registries.ITEM, Aquamirae.key("coral_lance"), CORAL_LANCE);
		Registry.register(Registries.ITEM, Aquamirae.key("dagger_of_greed"), DAGGER_OF_GREED);
		Registry.register(Registries.ITEM, Aquamirae.key("shell_horn"), SHELL_HORN);
		Registry.register(Registries.ITEM, Aquamirae.key("frozen_key"), FROZEN_KEY);
		Registry.register(Registries.ITEM, Aquamirae.key("eel_spawn_egg"), EEL_SPAWN_EGG);
		Registry.register(Registries.ITEM, Aquamirae.key("spinefish_spawn_egg"), SPINEFISH_SPAWN_EGG);
		Registry.register(Registries.ITEM, Aquamirae.key("sweet_lance"), SWEET_LANCE);
		Registry.register(Registries.ITEM, Aquamirae.key("poisoned_chakra"), POISONED_CHAKRA);
		Registry.register(Registries.ITEM, Aquamirae.key("maze_rose"), MAZE_ROSE);
		Registry.register(Registries.ITEM, Aquamirae.key("pirate_pouch"), PIRATE_POUCH);
		Registry.register(Registries.ITEM, Aquamirae.key("treasure_pouch"), TREASURE_POUCH);
		Registry.register(Registries.ITEM, Aquamirae.key("music_disc_horizon"), MUSIC_DISC_HORIZON);
		Registry.register(Registries.ITEM, Aquamirae.key("music_disc_forsaken_drownage"), MUSIC_DISC_FORSAKEN_DROWNAGE);
		Registry.register(Registries.ITEM, Aquamirae.key("fin"), FIN);
		Registry.register(Registries.ITEM, Aquamirae.key("esca"), ESCA);
		Registry.register(Registries.ITEM, Aquamirae.key("anglers_fang"), ANGLERS_FANG);
		Registry.register(Registries.ITEM, Aquamirae.key("abyssal_amethyst"), ABYSSAL_AMETHYST);
		Registry.register(Registries.ITEM, Aquamirae.key("sharp_bones"), SHARP_BONES);
		Registry.register(Registries.ITEM, Aquamirae.key("painting_anglerfish"), PAINTING_ANGLERFISH);
		Registry.register(Registries.ITEM, Aquamirae.key("painting_oxygelium"), PAINTING_OXYGELIUM);
		Registry.register(Registries.ITEM, Aquamirae.key("painting_tortured_soul"), PAINTING_TORTURED_SOUL);
		Registry.register(Registries.ITEM, Aquamirae.key("painting_aurora"), PAINTING_AURORA);
		Registry.register(Registries.ITEM, Aquamirae.key("golden_moth_in_a_jar"), GOLDEN_MOTH_IN_A_JAR);
		Registry.register(Registries.ITEM, Aquamirae.key("oxygelium"), OXYGELIUM);
		Registry.register(Registries.ITEM, Aquamirae.key("wisteria_niveis"), WISTERIA_NIVEIS);
		Registry.register(Registries.ITEM, Aquamirae.key("oxygen_tank"), OXYGEN_TANK);
		Registry.register(Registries.ITEM, Aquamirae.key("frozen_chest"), FROZEN_CHEST);
		Registry.register(Registries.ITEM, Aquamirae.key("luminescent_bubble"), LUMINESCENT_BUBBLE);
		Registry.register(Registries.ITEM, Aquamirae.key("luminescent_lamp"), LUMINESCENT_LAMP);
		Registry.register(Registries.ITEM, Aquamirae.key("sea_casserole"), SEA_CASSEROLE);
		Registry.register(Registries.ITEM, Aquamirae.key("spinefish_bucket"), SPINEFISH_BUCKET);
		Registry.register(Registries.ITEM, Aquamirae.key("spinefish"), SPINEFISH);
		Registry.register(Registries.ITEM, Aquamirae.key("cooked_spinefish"), COOKED_SPINEFISH);
		Registry.register(Registries.ITEM, Aquamirae.key("sea_stew"), SEA_STEW);
		Registry.register(Registries.ITEM, Aquamirae.key("poseidons_breakfast"), POSEIDONS_BREAKFAST);
		Registry.register(Registries.ITEM, Aquamirae.key("elodea"), ELODEA);
		Registry.register(Registries.ITEM_GROUP, Aquamirae.key("aquamirae"), AQUAMIRAE_GROUP);
	}

	static List<ItemStack> poisonedChakras() {
		final List<ItemStack> list = new ArrayList<>();
		final ItemStack stack1 = AquamiraeItems.POISONED_CHAKRA.getDefaultStack();
		final ItemStack stack2 = AquamiraeItems.POISONED_CHAKRA.getDefaultStack();
		stack1.addEnchantment(ObscureAPIEnchantments.DISTANCE, ObscureAPIEnchantments.DISTANCE.getMaxLevel());
		stack1.addEnchantment(ObscureAPIEnchantments.FAST_SPIN, ObscureAPIEnchantments.FAST_SPIN.getMaxLevel());
		stack2.addEnchantment(ObscureAPIEnchantments.MIRROR, ObscureAPIEnchantments.MIRROR.getMaxLevel());
		list.add(stack1); list.add(stack2);
		return list;
	}

	static List<ItemStack> mazeRoses() {
		final List<ItemStack> list = new ArrayList<>();
		final ItemStack stack1 = AquamiraeItems.MAZE_ROSE.getDefaultStack();
		final ItemStack stack2 = AquamiraeItems.MAZE_ROSE.getDefaultStack();
		stack1.addEnchantment(ObscureAPIEnchantments.DISTANCE, ObscureAPIEnchantments.DISTANCE.getMaxLevel());
		stack1.addEnchantment(ObscureAPIEnchantments.FAST_SPIN, ObscureAPIEnchantments.FAST_SPIN.getMaxLevel());
		stack2.addEnchantment(ObscureAPIEnchantments.MIRROR, ObscureAPIEnchantments.MIRROR.getMaxLevel());
		list.add(stack1); list.add(stack2);
		return list;
	}

	static List<ItemStack> logBooks() {
		final List<ItemStack> list = new ArrayList<>();
		final ItemStack book1 = Items.WRITTEN_BOOK.getDefaultStack();
		final ItemStack book2 = Items.WRITTEN_BOOK.getDefaultStack();
		final ItemStack book3 = Items.WRITTEN_BOOK.getDefaultStack();
		final NbtList pages1 = new NbtList();
		final NbtList pages2 = new NbtList();
		final NbtList pages3 = new NbtList();

		pages1.add(NbtString.of("{\"text\":\"Entry #12\n\nLast afternoon, Captain Cornelia went to the seabed in search of any additional resources. There are rumors among the crew that the real subject of her expedition is something else.\"}"));
		pages1.add(NbtString.of("{\"text\":\"More than a day has passed since the captain's dive. She was wearing the only diving suit we had, so we couldn't help her in any way. Worst of all, she had the master key with her. As our hopes of rescue dwindle, the threat of rebellion grows.\"}"));
		book1.getOrCreateNbt().putString("title", "Pillagers Ship Logbook");
		book1.getOrCreateNbt().putString("author", "Unknown");
		book1.getOrCreateNbt().putInt("generation", 3);
		book1.getOrCreateNbt().putBoolean("resolved", true);
		book1.getOrCreateNbt().put("pages", pages1);

		pages2.add(NbtString.of("{\"text\":\"Entry #34\n\nThese beasts from the depths prowl for someone to devour... One of those critters has already eaten our midshipman... Swallowed him whole along with his crossbow.\"}"));
		pages2.add(NbtString.of("{\"text\":\"Entry #35\n\nWe are waiting in vain for rescue... No one will come to help us, we have to survive on our own. Today the construction of the fort from parts of the ship was completed. In any case, she could no longer plow the seas ever again.\"}"));
		pages2.add(NbtString.of("{\"extra\":[{\"text\":\"Entry #36\n\nWe are no longer able to heat the fort, resources are running out. \"},{\"color\":\"dark_green\",\"hoverEvent\":{\"action\":\"show_text\",\"contents\":{\"extra\":[{\"color\":\"green\",\"text\":\"Underground shelter\"},{\"text\":\"\n  There are more structures\n  underneath the ice... Looks\n  like I have to go down to\n  these terrible fish...\"}],\"text\":\"\"}},\"text\":\"Part of the crew began to dig a shelter underground\"},{\"text\":\". It is the only way we have a chance not to freeze to death.\"}],\"text\":\"\"}"));
		pages2.add(NbtString.of("{\"text\":\"Entry #37\n\nWe hid our supplies and valuable cargo underground. But Poseidon cursed us, and during the storm the lieutenant washed away into the sea... Along with master keys.\"}"));
		pages2.add(NbtString.of("{\"extra\":[{\"text\":\"The hope remains that we could possibly find \"},{\"color\":\"dark_green\",\"hoverEvent\":{\"action\":\"show_text\",\"contents\":{\"extra\":[{\"color\":\"green\",\"text\":\"Cargo keys\"},{\"text\":\"\n  Without the keys, I will not\n  be able to receive the\n  most valuable cargo. I need\n  to find other ships nearby.\"}],\"text\":\"\"}},\"text\":\"similar keys on other ships of our fleet\"},{\"text\":\", which have suffered the same fate as us.\"}],\"text\":\"\"}"));
		book2.getOrCreateNbt().putString("title", "Pillagers Outpost Logbook");
		book2.getOrCreateNbt().putString("author", "Unknown");
		book2.getOrCreateNbt().putInt("generation", 3);
		book2.getOrCreateNbt().putBoolean("resolved", true);
		book2.getOrCreateNbt().put("pages", pages2);

		pages3.add(NbtString.of("{\"text\":\"Entry #41\n\nIn these cursed lands there you can't find shelter even under the ice and the thickness of the earth... Some creatures that look like eels crawled out of the depths of the underworld, depriving us of the opportunity \"}"));
		pages3.add(NbtString.of("{\"text\":\"to return back to the surface.\n\nOnly the mistress of the moon knows how long shall we last... At all events, our hopes of seeing sunlight had all but vanished. This place will become our grave, our eternal tomb. For us and that damned rune...\"}"));
		pages3.add(NbtString.of("{\"text\":\"Last Entry\n\nIt whispers to me, whispers inside my head the secrets of ice and snow... Ice... Now we are chained in ice forever...\n\nI suppose a similar fate awaits the crew that set out in search of the fire rune... I wonder if they made it\"}"));
		pages3.add(NbtString.of("{\"text\":\"to the Great Dark Forest valley. Though it doesn't really matter now...\"}"));
		book3.getOrCreateNbt().putString("title", "Pillagers Shelter Logbook");
		book3.getOrCreateNbt().putString("author", "Unknown");
		book3.getOrCreateNbt().putInt("generation", 3);
		book3.getOrCreateNbt().putBoolean("resolved", true);
		book3.getOrCreateNbt().put("pages", pages3);

		list.add(book1); list.add(book2); list.add(book3);
		return list;
	}
}
