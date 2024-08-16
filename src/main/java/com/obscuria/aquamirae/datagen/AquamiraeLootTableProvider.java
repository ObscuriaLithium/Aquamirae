package com.obscuria.aquamirae.datagen;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.common.block.WisteriaNiveisBlock;
import com.obscuria.aquamirae.registry.AquamiraeBlocks;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.*;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithLootingCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;

public class AquamiraeLootTableProvider extends LootTableProvider {

    public AquamiraeLootTableProvider(PackOutput output) {
        super(output, Set.of(), List.of(
                new SubProviderEntry(EntityLoot::new, LootContextParamSets.ENTITY),
                new SubProviderEntry(BlockLoot::new, LootContextParamSets.BLOCK)));
    }

    private static class BlockLoot implements LootTableSubProvider {

        @Override
        public void generate(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {

            consumer.accept(Aquamirae.key("blocks/wisteria_niveis"), LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1))
                            .when(ExplosionCondition.survivesExplosion())
                            .when(LootItemBlockStatePropertyCondition
                                    .hasBlockStateProperties(AquamiraeBlocks.WISTERIA_NIVEIS.get())
                                    .setProperties(StatePropertiesPredicate.Builder.properties()
                                            .hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER)))
                            .add(LootItem.lootTableItem(AquamiraeItems.WISTERIA_NIVEIS.get())))
                    .withPool(LootPool.lootPool()
                            .setRolls(UniformGenerator.between(1, 3))
                            .when(ExplosionCondition.survivesExplosion())
                            .when(LootItemBlockStatePropertyCondition
                                    .hasBlockStateProperties(AquamiraeBlocks.WISTERIA_NIVEIS.get())
                                    .setProperties(StatePropertiesPredicate.Builder.properties()
                                            .hasProperty(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER)
                                            .hasProperty(WisteriaNiveisBlock.NATURAL, true)))
                            .add(LootItem.lootTableItem(Items.STICK).setWeight(1))
                            .add(LootItem.lootTableItem(Items.SNOWBALL).setWeight(2))));
        }
    }

    private static class EntityLoot implements LootTableSubProvider {

        @Override
        public void generate(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {

            consumer.accept(Aquamirae.key("entities/golden_moth"), LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .setRolls(UniformGenerator.between(1, 2))
                            .add(LootItem.lootTableItem(Items.GOLD_NUGGET).setWeight(19)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3))))
                            .add(LootItem.lootTableItem(Items.GOLD_INGOT))));

            consumer.accept(Aquamirae.key("entities/anglerfish"), LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(AquamiraeItems.ESCA.get())))
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(AquamiraeItems.FIN.get()).setWeight(9))
                            .add(LootItem.lootTableItem(AquamiraeItems.ANGLERS_FANG.get()).setWeight(1)))
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1))
                            .when(LootItemKilledByPlayerCondition.killedByPlayer())
                            .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.05f, 0.1f))
                            .add(LootItem.lootTableItem(AquamiraeItems.SHIP_GRAVEYARD_ECHO.get())))
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1))
                            .when(LootItemKilledByPlayerCondition.killedByPlayer())
                            .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0, 0.05f))
                            .add(LootItem.lootTableItem(AquamiraeItems.ANGLERS_FANG.get()))));
        }
    }
}
