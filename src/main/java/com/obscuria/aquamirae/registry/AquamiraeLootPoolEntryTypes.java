package com.obscuria.aquamirae.registry;

import com.mojang.serialization.Codec;
import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.common.loot.PirateArmorLoot;
import com.obscuria.core.api.registry.RegistryHandler;
import com.obscuria.core.api.registry.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryType;
import org.jetbrains.annotations.ApiStatus;

import java.util.function.Supplier;

@ApiStatus.NonExtendable
public interface AquamiraeLootPoolEntryTypes {
    RegistryHandler<LootPoolEntryType> HANDLER = RegistryHandler.create(Registries.LOOT_POOL_ENTRY_TYPE, Aquamirae.MODID);

    RegistrySupplier<LootPoolEntryType> PIRATE_ARMOR = simple("pirate_armor", () -> PirateArmorLoot.CODEC);

    private static RegistrySupplier<LootPoolEntryType> simple(String key, Supplier<Codec<? extends LootPoolEntryContainer>> codecSupplier) {
        return HANDLER.register(key, () -> new LootPoolEntryType(codecSupplier.get()));
    }
}
