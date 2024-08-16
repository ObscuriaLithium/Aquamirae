package com.obscuria.aquamirae.common.entity.npc;

import com.google.common.collect.Lists;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffers;

import java.util.List;

public record ShadowTrades(List<ShadowTradePool> pools) {
    public static final ShadowTrades DEFAULT;

    public static Builder create() {
        return new Builder();
    }

    public void apply(ShadowMerchant merchant, MerchantOffers offers) {
        final var random = merchant.getRandom();
        for (var pool : pools) {
            final var trades = pool.randomize(random);
            trades.forEach(trade -> offers.add(trade.getOffer(merchant, random)));
        }
    }

    public static class Builder {
        private final List<ShadowTradePool> pools = Lists.newArrayList();

        public Builder addPool(ShadowTradePool.Builder pool) {
            pools.add(pool.build());
            return this;
        }

        public ShadowTrades build() {
            return new ShadowTrades(pools);
        }
    }

    static {
        DEFAULT = ShadowTrades.create()
                .addPool(ShadowTradePool.create(1)
                        .withTrade(5, ShadowTrade.ItemForDoubloon.Of(AquamiraeItems.ANGLERS_FANG, 1, 1, 16))
                        .withTrade(3, ShadowTrade.ItemForDoubloon.Of(AquamiraeItems.SHIP_GRAVEYARD_ECHO, 1, 6, 10, 8))
                        .withTrade(2, ShadowTrade.ItemForDoubloon.Of(AquamiraeItems.ABYSSAL_AMETHYST, 1, 12, 16, 4)))
                .addPool(ShadowTradePool.create(2)
                        .withTrade(3, ShadowTrade.ItemForDoubloon.Of(AquamiraeItems.PIRATE_POUCH, 1, 1, 64))
                        .withTrade(2, ShadowTrade.ItemForDoubloon.Of(AquamiraeItems.TREASURE_POUCH, 1, 2, 16))
                        .withTrade(1, ShadowTrade.ItemForDoubloon.Of(AquamiraeItems.ENCHANTED_POUCH, 1, 8, 12, 1))
                        .withTrade(1, ShadowTrade.ItemForDoubloon.Of(AquamiraeItems.SPINE_ARROW, 32, 2, 4, 8))
                        .withTrade(1, ShadowTrade.ItemForDoubloon.Of(AquamiraeItems.ABYSSAL_ARROW, 16, 2, 4, 8)))
                .addPool(ShadowTradePool.create(6)
                        .withTrade(1, ShadowTrade.ItemForDoubloon.Of(AquamiraeItems.OXYGEN_TANK, 1, 2, 4, 1))
                        .withTrade(1, ShadowTrade.ItemForDoubloon.Of(AquamiraeItems.OXYGEN_GENERATOR, 1, 22, 28, 1))
                        .withTrade(1, ShadowTrade.ItemForDoubloon.Of(AquamiraeItems.PAINTING_ANGLERFISH, 1, 16, 24, 1))
                        .withTrade(1, ShadowTrade.ItemForDoubloon.Of(AquamiraeItems.PAINTING_OXYGELIUM, 1, 16, 24, 1))
                        .withTrade(1, ShadowTrade.ItemForDoubloon.Of(AquamiraeItems.PAINTING_AURORA, 1, 16, 24, 1))
                        .withTrade(1, ShadowTrade.ItemForDoubloon.Of(AquamiraeItems.PAINTING_TORTURED_SOUL, 1, 16, 24, 1))
                        .withTrade(3, ShadowTrade.ItemForDoubloon.Of(AquamiraeItems.DEAD_SEA_SCROLL, 1, 8, 12, 1))
                        .withTrade(3, ShadowTrade.ItemForDoubloon.Of(AquamiraeItems.SHIP_GRAVEYARD_RING, 1, 20, 24, 1))
                        .withTrade(3, ShadowTrade.ItemForDoubloon.Of(AquamiraeItems.ICE_MAZE_RING, 1, 8, 12, 1))
                        .withTrade(3, ShadowTrade.ItemForDoubloon.Of(AquamiraeItems.SHOE_SPIKES, 1, 8, 12, 1))
                        .withTrade(3, ShadowTrade.ItemForDoubloon.Of(AquamiraeItems.SHELL_HORN, 1, 12, 16, 1))
                        .withTrade(3, ShadowTrade.ItemForDoubloon.Of(AquamiraeItems.DAGGER_OF_GREED, 1, 16, 32, 1))
                        .withTrade(1, ShadowTrade.ServiceForDoubloon.Of(
                                () -> Items.DIAMOND_AXE, 1,
                                AquamiraeItems.AXE_OF_FROSTFIRE, 1,
                                26, 32, 1)))
                .addPool(ShadowTradePool.create(1)
                        .withTrade(1, ShadowTrade.ServiceForDoubloon.Of(
                                AquamiraeItems.TERRIBLE_UPGRADE_SMITHING_TEMPLATE, 4,
                                AquamiraeItems.ABYSSAL_UPGRADE_SMITHING_TEMPLATE, 1,
                                16, 24, 2)))
                .build();
    }
}
