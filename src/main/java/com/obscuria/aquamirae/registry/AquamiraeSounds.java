
package com.obscuria.aquamirae.registry;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.core.api.registry.RegistryHandler;
import com.obscuria.core.api.registry.RegistrySupplier;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEvent;
import org.jetbrains.annotations.ApiStatus;

@SuppressWarnings("unused")
@ApiStatus.NonExtendable
public interface AquamiraeSounds {
	RegistryHandler<SoundEvent> HANDLER = RegistryHandler.create(Registries.SOUND_EVENT, Aquamirae.MODID);

	RegistrySupplier<SoundEvent> AMBIENT_SHIP_HORN = variable("ambient.ship_horn");
	RegistrySupplier<SoundEvent> BLOCK_FROZEN_CHEST_UNLOCK = variable("block.frozen_chest.unlock");
	RegistrySupplier<SoundEvent> ENTITY_MOTH_CATCH = variable("entity.moth.catch");
	RegistrySupplier<SoundEvent> ENTITY_CHAKRAM_HIT = variable("entity.chakram.hit");
	RegistrySupplier<SoundEvent> ENTITY_CAPTAIN_CORNELIA_AMBIENT = variable("entity.captain_cornelia.ambient");
	RegistrySupplier<SoundEvent> ENTITY_CAPTAIN_CORNELIA_HORN = variable("entity.captain_cornelia.horn");
	RegistrySupplier<SoundEvent> ENTITY_CAPTAIN_CORNELIA_RAGE = variable("entity.captain_cornelia.rage");
	RegistrySupplier<SoundEvent> ENTITY_CAPTAIN_CORNELIA_ATTACK_1 = variable("entity.captain_cornelia.attack_1");
	RegistrySupplier<SoundEvent> ENTITY_CAPTAIN_CORNELIA_ATTACK_2 = variable("entity.captain_cornelia.attack_2");
	RegistrySupplier<SoundEvent> ENTITY_CAPTAIN_CORNELIA_HURT = variable("entity.captain_cornelia.hurt");
	RegistrySupplier<SoundEvent> ENTITY_CAPTAIN_CORNELIA_DEATH = variable("entity.captain_cornelia.death");
	RegistrySupplier<SoundEvent> ENTITY_EEL_BITE = variable("entity.eel.bite");
	RegistrySupplier<SoundEvent> ENTITY_EEL_ROAR = variable("entity.eel.roar");
	RegistrySupplier<SoundEvent> ENTITY_DEEP_AMBIENT = variable("entity.deep.ambient");
	RegistrySupplier<SoundEvent> ENTITY_DEEP_HURT = variable("entity.deep.hurt");
	RegistrySupplier<SoundEvent> ENTITY_DEEP_DEATH = variable("entity.deep.death");
	RegistrySupplier<SoundEvent> ITEM_THREE_BOLT_OXYGEN = variable("item.three_bolt.oxygen");
	RegistrySupplier<SoundEvent> ITEM_SHELL_HORN_USE = variable("item.shell_horn.use");
	RegistrySupplier<SoundEvent> ITEM_TERRIBLE_SWORD_CRIT = variable("item.terrible_sword.crit");
	RegistrySupplier<SoundEvent> ITEM_TREASURE_POUCH_OPEN = variable("item.treasure_pouch.open");
	RegistrySupplier<SoundEvent> ITEM_PIRATE_POUCH_OPEN = variable("item.pirate_pouch.open");
	RegistrySupplier<SoundEvent> ITEM_SCROLL_MYSTERY = variable("item.scroll.mystery");
	RegistrySupplier<SoundEvent> ITEM_SCROLL_USE = variable("item.scroll.use");
	RegistrySupplier<SoundEvent> RECORD_HORIZON = variable("record.horizon");
	RegistrySupplier<SoundEvent> RECORD_FORSAKEN_DROWNAGE = variable("record.forsaken_drownage");
	RegistrySupplier<SoundEvent> MUSIC_FORSAKEN_DROWNAGE = variable("music.forsaken_drownage");
	RegistrySupplier<SoundEvent> MUSIC_ICE_MAZE = variable("music.ice_maze");
	RegistrySupplier<SoundEvent> MUSIC_SHIP_GRAVEYARD = variable("music.ship_graveyard");

	Music GAME_MUSIC_ICE_MAZE = music(MUSIC_ICE_MAZE, 6000, 12000, false);
	Music GAME_MUSIC_SHIP_GRAVEYARD = music(MUSIC_SHIP_GRAVEYARD, 1200, 2400, false);
	Music GAME_MUSIC_FORSAKEN_DROWNAGE = music(MUSIC_FORSAKEN_DROWNAGE, 0, 0, true);

	private static RegistrySupplier<SoundEvent> variable(String key) {
		return HANDLER.register(key, () -> SoundEvent.createVariableRangeEvent(Aquamirae.key(key)));
	}

	private static Music music(RegistrySupplier<SoundEvent> sound, int minDelay, int maxDelay, boolean playImmediately) {
		return new Music(Holder.direct(sound.get()), minDelay, maxDelay, playImmediately);
	}
}
