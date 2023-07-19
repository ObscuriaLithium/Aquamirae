
package com.obscuria.aquamirae.registry;

import com.obscuria.aquamirae.Aquamirae;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;

public interface AquamiraeSounds {
	SoundEvent ENTITY_GOLDEN_MOTH_CATCH = SoundEvent.of(Aquamirae.key("entity.golden_moth.catch"));
	SoundEvent ENTITY_GOLDEN_MOTH_AMBIENT = SoundEvent.of(Aquamirae.key("entity.golden_moth.ambient"));
	SoundEvent BLOCK_FROZEN_CHEST_UNLOCK = SoundEvent.of(Aquamirae.key("block.frozen_chest.unlock"));
	SoundEvent EFFECT_OXYGEN = SoundEvent.of(Aquamirae.key("effect.oxygen"));
	SoundEvent AMBIENT_SHIP_HORN = SoundEvent.of(Aquamirae.key("ambient.ship_horn"));
	SoundEvent ITEM_SHELL_HORN_USE = SoundEvent.of(Aquamirae.key("item.shell_horn.use"));
	SoundEvent ITEM_TERRIBLE_SWORD = SoundEvent.of(Aquamirae.key("item.terrible_sword"));
	SoundEvent ITEM_TREASURE_POUCH_OPEN = SoundEvent.of(Aquamirae.key("item.treasure_pouch.open"));
	SoundEvent ITEM_POUCH_OPEN = SoundEvent.of(Aquamirae.key("item.pouch.open"));
	SoundEvent MUSIC_FORSAKEN_DROWNAGE = SoundEvent.of(Aquamirae.key("music.forsaken_drownage"));
	SoundEvent ENTITY_CAPTAIN_CORNELIA_HORN = SoundEvent.of(Aquamirae.key("entity.captain_cornelia.horn"));
	SoundEvent ENTITY_CAPTAIN_CORNELIA_ATTACK_1 = SoundEvent.of(Aquamirae.key("entity.captain_cornelia.attack_1"));
	SoundEvent ENTITY_CAPTAIN_CORNELIA_ATTACK_2 = SoundEvent.of(Aquamirae.key("entity.captain_cornelia.attack_2"));
	SoundEvent ENTITY_DEEP_AMBIENT = SoundEvent.of(Aquamirae.key("entity.deep_ambient"));
	SoundEvent ENTITY_DEEP_HURT = SoundEvent.of(Aquamirae.key("entity.deep_hurt"));
	SoundEvent ENTITY_DEEP_DEATH = SoundEvent.of(Aquamirae.key("entity.deep_death"));
	SoundEvent ENTITY_CAPTAIN_CORNELIA_AMBIENT = SoundEvent.of(Aquamirae.key("entity.captain_cornelia.ambient"));
	SoundEvent EFFECT_MYSTERY = SoundEvent.of(Aquamirae.key("effect.mystery"));
	SoundEvent ITEM_SCROLL_USE = SoundEvent.of(Aquamirae.key("item.scroll.use"));
	SoundEvent ENTITY_CAPTAIN_CORNELIA_HURT = SoundEvent.of(Aquamirae.key("entity.captain_cornelia.hurt"));
	SoundEvent ENTITY_CAPTAIN_CORNELIA_DEATH = SoundEvent.of(Aquamirae.key("entity.captain_cornelia.death"));
	SoundEvent ENTITY_EEL_BITE = SoundEvent.of(Aquamirae.key("entity.eel.bite"));
	SoundEvent ENTITY_EEL_ROAR = SoundEvent.of(Aquamirae.key("entity.eel.roar"));
	SoundEvent ENTITY_CAPTAIN_CORNELIA_RAGE = SoundEvent.of(Aquamirae.key("entity.captain_cornelia.rage"));
	SoundEvent MUSIC_ICE_MAZE_THEME = SoundEvent.of(Aquamirae.key("music.ice_maze_theme"));
	SoundEvent RECORD_HORIZON = SoundEvent.of(Aquamirae.key("record.horizon"));
	SoundEvent RECORD_FORSAKEN_DROWNAGE = SoundEvent.of(Aquamirae.key("record.forsaken_drownage"));
	
	static void register() {
		Registry.register(Registries.SOUND_EVENT, EFFECT_OXYGEN.getId(), EFFECT_OXYGEN);
		Registry.register(Registries.SOUND_EVENT, EFFECT_MYSTERY.getId(), EFFECT_MYSTERY);
		Registry.register(Registries.SOUND_EVENT, ITEM_SHELL_HORN_USE.getId(), ITEM_SHELL_HORN_USE);
		Registry.register(Registries.SOUND_EVENT, ITEM_TERRIBLE_SWORD.getId(), ITEM_TERRIBLE_SWORD);
		Registry.register(Registries.SOUND_EVENT, ITEM_TREASURE_POUCH_OPEN.getId(), ITEM_TREASURE_POUCH_OPEN);
		Registry.register(Registries.SOUND_EVENT, ITEM_POUCH_OPEN.getId(), ITEM_POUCH_OPEN);
		Registry.register(Registries.SOUND_EVENT, ITEM_SCROLL_USE.getId(), ITEM_SCROLL_USE);
		Registry.register(Registries.SOUND_EVENT, BLOCK_FROZEN_CHEST_UNLOCK.getId(), BLOCK_FROZEN_CHEST_UNLOCK);
		Registry.register(Registries.SOUND_EVENT, ENTITY_GOLDEN_MOTH_CATCH.getId(), ENTITY_GOLDEN_MOTH_CATCH);
		Registry.register(Registries.SOUND_EVENT, ENTITY_GOLDEN_MOTH_AMBIENT.getId(), ENTITY_GOLDEN_MOTH_AMBIENT);
		Registry.register(Registries.SOUND_EVENT, ENTITY_CAPTAIN_CORNELIA_HORN.getId(), ENTITY_CAPTAIN_CORNELIA_HORN);
		Registry.register(Registries.SOUND_EVENT, ENTITY_CAPTAIN_CORNELIA_ATTACK_1.getId(), ENTITY_CAPTAIN_CORNELIA_ATTACK_1);
		Registry.register(Registries.SOUND_EVENT, ENTITY_CAPTAIN_CORNELIA_ATTACK_2.getId(), ENTITY_CAPTAIN_CORNELIA_ATTACK_2);
		Registry.register(Registries.SOUND_EVENT, ENTITY_CAPTAIN_CORNELIA_AMBIENT.getId(), ENTITY_CAPTAIN_CORNELIA_AMBIENT);
		Registry.register(Registries.SOUND_EVENT, ENTITY_CAPTAIN_CORNELIA_HURT.getId(), ENTITY_CAPTAIN_CORNELIA_HURT);
		Registry.register(Registries.SOUND_EVENT, ENTITY_CAPTAIN_CORNELIA_DEATH.getId(), ENTITY_CAPTAIN_CORNELIA_DEATH);
		Registry.register(Registries.SOUND_EVENT, ENTITY_CAPTAIN_CORNELIA_RAGE.getId(), ENTITY_CAPTAIN_CORNELIA_RAGE);
		Registry.register(Registries.SOUND_EVENT, ENTITY_DEEP_AMBIENT.getId(), ENTITY_DEEP_AMBIENT);
		Registry.register(Registries.SOUND_EVENT, ENTITY_DEEP_HURT.getId(), ENTITY_DEEP_HURT);
		Registry.register(Registries.SOUND_EVENT, ENTITY_DEEP_DEATH.getId(), ENTITY_DEEP_DEATH);
		Registry.register(Registries.SOUND_EVENT, ENTITY_EEL_BITE.getId(), ENTITY_EEL_BITE);
		Registry.register(Registries.SOUND_EVENT, ENTITY_EEL_ROAR.getId(), ENTITY_EEL_ROAR);
		Registry.register(Registries.SOUND_EVENT, MUSIC_FORSAKEN_DROWNAGE.getId(), MUSIC_FORSAKEN_DROWNAGE);
		Registry.register(Registries.SOUND_EVENT, RECORD_HORIZON.getId(), RECORD_HORIZON);
		Registry.register(Registries.SOUND_EVENT, RECORD_FORSAKEN_DROWNAGE.getId(), RECORD_FORSAKEN_DROWNAGE);
		Registry.register(Registries.SOUND_EVENT, AMBIENT_SHIP_HORN.getId(), AMBIENT_SHIP_HORN);
	}
}
