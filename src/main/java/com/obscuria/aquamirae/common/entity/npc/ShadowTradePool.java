package com.obscuria.aquamirae.common.entity.npc;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.minecraft.util.RandomSource;

import java.util.HashMap;
import java.util.Set;

public record ShadowTradePool(HashMap<ShadowTrade, Integer> trades, int amount) {

    public static Builder create(int amount) {
        return new Builder(amount);
    }

    public Set<ShadowTrade> randomize(RandomSource random) {
        if (amount >= trades.size()) return trades.keySet();
        final var result = Sets.<ShadowTrade>newHashSet();
        final var totalWeight = trades.values().stream().mapToInt(i -> i).sum();

        while (result.size() < amount) {
            var randomWeight = random.nextInt(totalWeight);
            var currentWeight = 0;

            for (var entry : trades.entrySet()) {
                currentWeight += entry.getValue();
                if (randomWeight < currentWeight && !result.contains(entry.getKey())) {
                    result.add(entry.getKey());
                    break;
                }
            }
        }

        return result;
    }

    public static class Builder {
        private final HashMap<ShadowTrade, Integer> trades = Maps.newHashMap();
        private final int amount;

        private Builder(int amount) {
            this.amount = amount;
        }

        public Builder withTrade(int weight, ShadowTrade trade) {
            trades.put(trade, weight);
            return this;
        }

        public ShadowTradePool build() {
            return new ShadowTradePool(this.trades, this.amount);
        }
    }
}
