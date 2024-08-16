package com.obscuria.aquamirae.common.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import com.obscuria.aquamirae.registry.AquamiraeLootPoolEntryTypes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryType;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import java.util.List;
import java.util.function.Consumer;

public class DoubloonLoot extends LootPoolSingletonContainer {
    public static final Codec<DoubloonLoot> CODEC = RecordCodecBuilder.create(builder ->
            singletonFields(builder).apply(builder, DoubloonLoot::new));

    private DoubloonLoot(int weight, int quality, List<LootItemCondition> conditions, List<LootItemFunction> functions) {
        super(weight, quality, conditions, functions);
    }

    @Override
    protected void createItemStack(Consumer<ItemStack> consumer, LootContext context) {
        consumer.accept(AquamiraeItems.DOUBLOON.get().getDefaultInstance());
    }

    @Override
    public LootPoolEntryType getType() {
        return AquamiraeLootPoolEntryTypes.DOUBLOON.get();
    }
}
